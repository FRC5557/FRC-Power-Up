
package org.usfirst.frc.team5557.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import res.GeneratedMotionProfile;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5557.robot.commands.SwapDriveComand;
import org.usfirst.frc.team5557.robot.commands.autogroups.MiddleAutoLine;
import org.usfirst.frc.team5557.robot.commands.autogroups.RightAutoLine;
import org.usfirst.frc.team5557.robot.commands.autogroups.SwitchOnSameSide;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5557.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;
import org.usfirst.frc.team5557.robot.subsystems.MotionProfileSubsystem;
import org.usfirst.frc.team5557.robot.subsystems.SensorSubsystem;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import utils.ADIS16448_IMU;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static final DriveSubSystem drive = new DriveSubSystem();
	public static final SensorSubsystem sensors = new SensorSubsystem();
	public static final ArmSubsystem arm = new ArmSubsystem();
	public static final ControllerSubsystem control = new ControllerSubsystem();
	
/*	TalonSRX _talon = new TalonSRX(RobotMap.RIGHT_FRONT_MOTOR);
	TalonSRX _talon2 = new TalonSRX(RobotMap.LEFT_REAR_MOTOR);
	
	*//** some example logic on how one can manage an MP *//*
	MotionProfileSubsystem _example = new MotionProfileSubsystem(_talon, GeneratedMotionProfile.motionProfilePointsRight, GeneratedMotionProfile.numPointsRight);
	MotionProfileSubsystem _exampleButTheOtherOne = new MotionProfileSubsystem(_talon2, GeneratedMotionProfile.motionProfilePointsLeft, GeneratedMotionProfile.numPointsLeft);
*/
	
	public static OI oi;
	public static Preferences prefs = Preferences.getInstance();

	Command autonomousCommand;
	
	SendableChooser<Command> autonObjectiveChooser = new SendableChooser<Command>();
	
	  public static final ADIS16448_IMU imu = new ADIS16448_IMU();
	  



	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		autonObjectiveChooser.addDefault("Default Auto", new RightAutoLine());
		autonObjectiveChooser.addObject("My Auto", new MiddleAutoLine());
		SmartDashboard.putData(autonObjectiveChooser);
		
		//buttons
		SmartDashboard.putData("Flight Sticks: ", new SwapDriveComand("STICKS"));
		SmartDashboard.putData("Game Pad: ", new SwapDriveComand("CONTROLLER"));
		
		sensors.resetEncoders();
		 new Thread(() -> {
             UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
             camera.setResolution(640, 480);
             camera.setFPS(25);
             camera.setExposureAuto();
             
             CvSink cvSink = CameraServer.getInstance().getVideo();
             CvSource outputStream = CameraServer.getInstance().putVideo("Main Camera", 640, 480);
             
             Mat source = new Mat();
             Mat output = new Mat();
;             
             while(!Thread.interrupted()) {
                 cvSink.grabFrame(source);
                 Imgproc.cvtColor(source, output, Imgproc.COLOR_RGB2GRAY);
                 outputStream.putFrame(output);
             }
         }).start();
		
	}
	
	
	//Put println() here
	@Override
	public void robotPeriodic() {
		super.robotPeriodic();
		SmartDashboard.putNumber("Ultra (mm): ", sensors.getUltraWithVoltage());
		SmartDashboard.putNumber("Encoder Right (cm): ", sensors.getDis(MotorType.kFrontRight));
		SmartDashboard.putNumber("Encoder Left (cm): ", sensors.getDis(MotorType.kRearLeft));
		SmartDashboard.putNumber("Left Stick", OI.driveStickZero.getY());
		SmartDashboard.putNumber("Right Stick", OI.driveStickOne.getY());
		SmartDashboard.putNumber("Left encoder ticks: ", drive.getTalonSensorC(MotorType.kRearLeft).getQuadraturePosition());
		SmartDashboard.putNumber("Right encoder ticks: ", drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition());
		prefs.getDouble("ArmUpVoltage", 0);
		
		
	    SmartDashboard.putNumber("Gyro-X", imu.getAngleX());
	    
	    SmartDashboard.putNumber("Temperature: ", imu.getTemperature()); 
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		/*
		 * it's generally a good idea to put motor controllers back into a known
		 * state when robot is disabled. That way when you enable the robot
		 * doesn't just continue doing what it was doing before. BUT if that's
		 * what the application/testing requires than modify this accordingly
		 
		_talon.set(ControlMode.PercentOutput, 0);
		_talon2.set(ControlMode.PercentOutput, 0);
		 clear our buffer and put everything into a known state 
		_example.reset();
		_exampleButTheOtherOne.reset();*/
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		imu.reset();
		//autonomousCommand = autonObjectiveChooser.getSelected();
		//drive.autonTalonInit(NeutralMode.Brake);
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData != null){
			if(gameData.charAt(0) == 'R'){
				System.out.println("Caught FMS data Right");
				autonomousCommand = new SwitchOnSameSide();
			} else if(gameData.charAt(0) == 'L'){
				autonomousCommand = new RightAutoLine();
			}else{
				autonomousCommand = new RightAutoLine();
			}
		}
		if(autonomousCommand != null){
				autonomousCommand.start();
		}
		
/*		_talon.setInverted(true);
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		_talon.setSensorPhase(true);  keep sensor and motor in phase 
		_talon.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

		_talon.config_kF(0, 0.076, RobotMap.kTimeoutMs);
		_talon.config_kP(0, 2.000, RobotMap.kTimeoutMs);
		_talon.config_kI(0, 0.0, RobotMap.kTimeoutMs);
		_talon.config_kD(0, 20.0, RobotMap.kTimeoutMs);

		 Our profile uses 10ms timing 
		_talon.configMotionProfileTrajectoryPeriod(10, RobotMap.kTimeoutMs); 
		
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.kTimeoutMs);
		
		_talon2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		_talon2.setSensorPhase(true);  keep sensor and motor in phase 
		_talon2.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

		_talon2.config_kF(0, 0.076, RobotMap.kTimeoutMs);
		_talon2.config_kP(0, 2.000, RobotMap.kTimeoutMs);
		_talon2.config_kI(0, 0.0, RobotMap.kTimeoutMs);
		_talon2.config_kD(0, 20.0, RobotMap.kTimeoutMs);

		 Our profile uses 10ms timing 
		_talon2.configMotionProfileTrajectoryPeriod(10, RobotMap.kTimeoutMs); 
		
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 
		_talon2.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.kTimeoutMs);*/
		
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		/*
		 * call this periodically, and catch the output. Only apply it if user
		 * wants to run MP.
		 
		_example.control();
		_exampleButTheOtherOne.control();
		
		_example.reset();
		_exampleButTheOtherOne.reset();
		
		SetValueMotionProfile setOutput = _example.getSetValue();

		_talon.set(ControlMode.MotionProfile, setOutput.value);
		_talon2.set(ControlMode.MotionProfile, setOutput.value);
		
		_example.startMotionProfile();
		_exampleButTheOtherOne.startMotionProfile();*/
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		drive.autonTalonInit(NeutralMode.Coast);
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		arm.raise(OI.driveStickZero.getZ());
		arm.wrist.set(arm.wristPower);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
