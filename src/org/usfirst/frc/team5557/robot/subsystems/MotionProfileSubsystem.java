/**
 * Example logic for firing and managing motion profiles.
 * This example sends MPs, waits for them to finish
 * Although this code uses a CANTalon, nowhere in this module do we changeMode() or call set() to change the output.
 * This is done in Robot.java to demonstrate how to change control modes on the fly.
 * 
 * The only routines we call on Talon are....
 * 
 * changeMotionControlFramePeriod
 * 
 * getMotionProfileStatus		
 * clearMotionProfileHasUnderrun     to get status and potentially clear the error flag.
 * 
 * pushMotionProfileTrajectory
 * clearMotionProfileTrajectories
 * processMotionProfileBuffer,   to push/clear, and process the trajectory points.
 * 
 * getControlMode, to check if we are in Motion Profile Control mode.
 * 
 * Example of advanced features not demonstrated here...
 * [1] Calling pushMotionProfileTrajectory() continuously while the Talon executes the motion profile, thereby keeping it going indefinitely.
 * [2] Instead of setting the sensor position to zero at the start of each MP, the program could offset the MP's position based on current position. 
 */
package org.usfirst.frc.team5557.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import res.GeneratedMotionProfile;
import utils.MotionProfileReporter;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

public class MotionProfileSubsystem extends Subsystem{
	
	/**
	 * The status of the motion profile executer and buffer inside the Talon.
	 * Instead of creating a new one every time we call getMotionProfileStatus,
	 * keep one copy.
	 */
	private MotionProfileStatus _status = new MotionProfileStatus();
	private double[][] pointsRight;
	private double[][] pointsLeft;
	private int numPointsRight;
	private int numPointsLeft;

	/** additional cache for holding the active trajectory point */
	double _pos=0,_vel=0,_heading=0;

	/**
	 * reference to the talon we plan on manipulating. We will not changeMode()
	 * or call set(), just get motion profile status and make decisions based on
	 * motion profile.
	 */

	public WPI_TalonSRX _talon = Robot.drive.getTalon(MotorType.kFrontRight);
	public WPI_TalonSRX _talon2 = Robot.drive.getTalon(MotorType.kRearLeft);
	/**
	 * State machine to make sure we let enough of the motion profile stream to
	 * talon before we fire it.
	 */
	private int _state = 0;
	/**
	 * Any time you have a state machine that waits for external events, its a
	 * good idea to add a timeout. Set to -1 to disable. Set to nonzero to count
	 * down to '0' which will print an error message. Counting loops is not a
	 * very accurate method of tracking timeout, but this is just conservative
	 * timeout. Getting time-stamps would certainly work too, this is just
	 * simple (no need to worry about timer overflows).
	 */
	private int _loopTimeout = -1;
	/**
	 * If start() gets called, this flag is set and in the control() we will
	 * service it.
	 */
	private boolean _bStart = false;

	/**
	 * Since the CANTalon.set() routine is mode specific, deduce what we want
	 * the set value to be and let the calling module apply it whenever we
	 * decide to switch to MP mode.
	 */
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;
	/**
	 * How many trajectory points do we wait for before firing the motion
	 * profile.
	 */
	private static final int kMinPointsInTalon = 5;
	/**
	 * Just a state timeout to make sure we don't get stuck anywhere. Each loop
	 * is about 20ms.
	 */
	private static final int kNumLoopsTimeout = 10;
	
	/**
	 * Lets create a periodic task to funnel our trajectory points into our talon.
	 * It doesn't need to be very accurate, just needs to keep pace with the motion
	 * profiler executer.  Now if you're trajectory points are slow, there is no need
	 * to do this, just call _talon.processMotionProfileBuffer() in your teleop loop.
	 * Generally speaking you want to call it at least twice as fast as the duration
	 * of your trajectory points.  So if they are firing every 20ms, you should call 
	 * every 10ms.
	 */
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {  
	    	_talon.processMotionProfileBuffer();
	    	_talon2.processMotionProfileBuffer();
	    
	    }
	}
	Notifier _notifer = new Notifier(new PeriodicRunnable());
	

	/**
	 * C'tor
	 * 
	 * @param talon
	 *            reference to Talon object to fetch motion profile status from.
	 * @param points 
	 *			  reference to 2D array containing motion profile points
	 */
	public MotionProfileSubsystem(GeneratedMotionProfile motionProfile) {
		this.pointsRight = motionProfile.motionProfilePointsRight;
		this.pointsLeft = motionProfile.motionProfilePointsLeft;
		this.numPointsRight = motionProfile.numPointsRight;
		this.numPointsLeft = motionProfile.numPointsLeft;
		/*
		 * since our MP is 10ms per point, set the control frame rate and the
		 * notifer to half that
		 */
		_talon.changeMotionControlFramePeriod(5);
		_notifer.startPeriodic(0.005);
	}

	/**
	 * Called to clear Motion profile buffer and reset state info during
	 * disabled and when Talon is not in MP control mode.
	 */
	public void reset() {
		/*
		 * Let's clear the buffer just in case user decided to disable in the
		 * middle of an MP, and now we have the second half of a profile just
		 * sitting in memory.
		 */
		_talon.clearMotionProfileTrajectories();
		/* When we do re-enter motionProfile control mode, stay disabled. */
		_setValue = SetValueMotionProfile.Disable;
		/* When we do start running our state machine start at the beginning. */
		_state = 0;
		_loopTimeout = -1;
		/*
		 * If application wanted to start an MP before, ignore and wait for next
		 * button press
		 */
		_bStart = false;
	}

	/**
	 * Called every loop.
	 */
	public void control() {
		/* Get the motion profile status every loop */
		_talon.getMotionProfileStatus(_status);
		/*
		 * track time, this is rudimentary but that's okay, we just want to make
		 * sure things never get stuck.
		 */
		if (_loopTimeout < 0) {
			/* do nothing, timeout is disabled */
		} else {
			/* our timeout is nonzero */
			if (_loopTimeout == 0) {
				/*
				 * something is wrong. Talon is not present, unplugged, breaker
				 * tripped
				 */
				MotionProfileReporter.OnNoProgress();
			} else {
				--_loopTimeout;
			}
		}
		/* first check if we are in MP mode */
		if (_talon.getControlMode() != ControlMode.MotionProfile) {
			/*
			 * we are not in MP mode. We are probably driving the robot around
			 * using gamepads or some other mode.
			 */
			_state = 0;
			_loopTimeout = -1;
			System.out.println("Talon control mode was not MotionProfile");
		} else {
			/*
			 * we are in MP control mode. That means: starting Mps, checking Mp
			 * progress, and possibly interrupting MPs if thats what you want to
			 * do.
			 */
			switch (_state) {
				case 0: /* wait for application to tell us to start an MP */
					if (_bStart) {
						System.out.println("bStart change picked up");
						_bStart = false;
	
						_setValue = SetValueMotionProfile.Disable;
						startFilling();
						/*
						 * MP is being sent to CAN bus, wait a small amount of time
						 */
						_state = 1;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 1: /*
						 * wait for MP to stream to Talon, really just the first few
						 * points
						 */
					/* do we have a minimum numberof points in Talon */
					if (_status.btmBufferCnt > kMinPointsInTalon) {
						/* start (once) the motion profile */
						_setValue = SetValueMotionProfile.Enable;
						/* MP will start once the control frame gets scheduled */
						_state = 2;
						_loopTimeout = kNumLoopsTimeout;
					}
					break;
				case 2: /* check the status of the MP */
					/*
					 * if talon is reporting things are good, keep adding to our
					 * timeout. Really this is so that you can unplug your talon in
					 * the middle of an MP and react to it.
					 */
					if (_status.isUnderrun == false) {
						_loopTimeout = kNumLoopsTimeout;
					}
					/*
					 * If we are executing an MP and the MP finished, start loading
					 * another. We will go into hold state so robot servo's
					 * position.
					 */
					if (_status.activePointValid && _status.isLast) {
						/*
						 * because we set the last point's isLast to true, we will
						 * get here when the MP is done
						 */
						_setValue = SetValueMotionProfile.Hold;
						_state = 0;
						_loopTimeout = -1;
					}
					break;
			}

			/* Get the motion profile status every loop */
			_talon.getMotionProfileStatus(_status);
			_heading = _talon.getActiveTrajectoryHeading();
			_pos = _talon.getActiveTrajectoryPosition();
			_vel = _talon.getActiveTrajectoryVelocity();

			/* printfs and/or logging */
			MotionProfileReporter.process(_status, _pos, _vel, _heading);
		}
	}
	/**
	 * Find enum value if supported.
	 * @param durationMs
	 * @return enum equivalent of durationMs
	 */
	private TrajectoryDuration GetTrajectoryDuration(int durationMs)
	{	 
		/* create return value */
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
		retval = retval.valueOf(durationMs);
		/* check that it is valid */
		if (retval.value != durationMs) {
			DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);		
		}
		/* pass to caller */
		return retval;
	}
	/** Start filling the MPs to all of the involved Talons. */
	private void startFilling() {
		/* since this example only has one talon, just update that one */
		startFilling(pointsRight, numPointsRight);
	}

	private void startFilling(double[][] profile, int totalCnt) {

		/* create an empty point */
		TrajectoryPoint pointRight = new TrajectoryPoint();
		TrajectoryPoint pointLeft = new TrajectoryPoint();

		/* did we get an underrun condition since last time we checked ? */
		if (_status.hasUnderrun) {
			/* better log it so we know about it */
			MotionProfileReporter.OnUnderrun();
			/*
			 * clear the error. This flag does not auto clear, this way 
			 * we never miss logging it.
			 */
			_talon.clearMotionProfileHasUnderrun(0);
		}
		/*
		 * just in case we are interrupting another MP and there is still buffer
		 * points in memory, clear it.
		 */
		_talon.clearMotionProfileTrajectories();

		/* set the base trajectory period to zero, use the individual trajectory period below */
		_talon.configMotionProfileTrajectoryPeriod(RobotMap.kBaseTrajPeriodMs, RobotMap.kTimeoutMs);
		
		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
			double positionRotRight = pointsRight[i][0];
			double velocityRPMRight = pointsRight[i][1];
			/* for each point, fill our structure and pass it to API */
			pointRight.position = positionRotRight * RobotMap.TICKS_PER_ROTATION; //Convert Revolutions to Units
			pointRight.velocity = velocityRPMRight * RobotMap.TICKS_PER_ROTATION / 600.0; //Convert RPM to Units/100ms
			pointRight.headingDeg = 0; /* future feature - not used in this example*/
			pointRight.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			pointRight.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
			pointRight.timeDur = GetTrajectoryDuration((int)pointsRight[i][2]);
			pointRight.zeroPos = false;
			if (i == 0)
				pointRight.zeroPos = true; /* set this to true on the first point */

			pointRight.isLastPoint = false;
			if ((i + 1) == totalCnt)
				pointRight.isLastPoint = true; /* set this to true on the last point  */
			
			double positionRotLeft = pointsLeft[i][0];
			double velocityRPMLeft = pointsLeft[i][1];
			/* for each point, fill our structure and pass it to API */
			pointLeft.position = positionRotLeft * RobotMap.TICKS_PER_ROTATION; //Convert Revolutions to Units
			pointLeft.velocity = velocityRPMLeft * RobotMap.TICKS_PER_ROTATION / 600.0; //Convert RPM to Units/100ms
			pointLeft.headingDeg = 0; /* future feature - not used in this example*/
			pointLeft.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			pointLeft.profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
			pointLeft.timeDur = GetTrajectoryDuration((int)pointsLeft[i][2]);
			pointLeft.zeroPos = false;
			if (i == 0)
				pointLeft.zeroPos = true; /* set this to true on the first point */

			pointLeft.isLastPoint = false;
			if ((i + 1) == totalCnt)
				pointLeft.isLastPoint = true; /* set this to true on the last point  */

			_talon.pushMotionProfileTrajectory(pointRight);
			_talon2.pushMotionProfileTrajectory(pointLeft);
		}
	}
	/**
	 * Called by application to signal Talon to start the buffered MP (when it's
	 * able to).
	 */
	public void startMotionProfile() {
		System.out.println("Staring Profile");
		_bStart = true;
	}

	/**
	 * 
	 * @return the output value to pass to Talon's set() routine. 0 for disable
	 *         motion-profile output, 1 for enable motion-profile, 2 for hold
	 *         current motion profile trajectory point.
	 */
	public SetValueMotionProfile getSetValue() {
		return _setValue;
	}
	
	public void clearMPState() {
		_talon.set(ControlMode.PercentOutput, 0);
		_talon2.set(ControlMode.PercentOutput, 0);
		/* clear our buffer and put everything into a known state */
		reset();
	}
	
	public void configTalonForProfile() {
		_talon.setInverted(true);
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		_talon.setSensorPhase(true); /* keep sensor and motor in phase */
		_talon.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

		_talon.config_kF(0, 0.071, RobotMap.kTimeoutMs);
		_talon.config_kP(0, 2.000, RobotMap.kTimeoutMs);
		_talon.config_kI(0, 0.0, RobotMap.kTimeoutMs);
		_talon.config_kD(0, 20.0, RobotMap.kTimeoutMs);

		/* Our profile uses 10ms timing */
		_talon.configMotionProfileTrajectoryPeriod(10, RobotMap.kTimeoutMs); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.kTimeoutMs);
		
		_talon2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		_talon2.setSensorPhase(true); /* keep sensor and motor in phase */
		_talon2.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

		_talon2.config_kF(0, 0.071, RobotMap.kTimeoutMs);
		_talon2.config_kP(0, 2.000, RobotMap.kTimeoutMs);
		_talon2.config_kI(0, 0.0, RobotMap.kTimeoutMs);
		_talon2.config_kD(0, 20.0, RobotMap.kTimeoutMs);

		/* Our profile uses 10ms timing */
		_talon2.configMotionProfileTrajectoryPeriod(10, RobotMap.kTimeoutMs); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		_talon2.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.kTimeoutMs);
		System.out.println("Talons Ready for MP");
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}