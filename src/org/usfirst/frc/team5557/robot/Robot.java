
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
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import res.RightAutoLineCubeMotionProfile;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5557.robot.commands.SwapDriveComand;
import org.usfirst.frc.team5557.robot.commands.autogroups.LeftAutoLine;
import org.usfirst.frc.team5557.robot.commands.autogroups.LeftRightSwitch;
import org.usfirst.frc.team5557.robot.commands.autogroups.LeftSwitch;
import org.usfirst.frc.team5557.robot.commands.autogroups.MiddleAutoLine;
import org.usfirst.frc.team5557.robot.commands.autogroups.AutoLine;
import org.usfirst.frc.team5557.robot.commands.autogroups.MiddleLeftSwitch;
import org.usfirst.frc.team5557.robot.commands.autogroups.MiddleRightSwitch;
import org.usfirst.frc.team5557.robot.commands.autogroups.SwitchOnSameSide;
import org.usfirst.frc.team5557.robot.commands.autogroups.RightAutoLineTalon;
import org.usfirst.frc.team5557.robot.commands.autogroups.RightLeftSwitch;
import org.usfirst.frc.team5557.robot.commands.autogroups.RightSwitch;
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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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
	

	public static final MotionProfileSubsystem mp = new MotionProfileSubsystem(new RightAutoLineCubeMotionProfile());
	
	public static OI oi;
	public static Preferences prefs = Preferences.getInstance();

	public static final ADIS16448_IMU imu = new ADIS16448_IMU();

	Command autonomousCommand;
	
	SendableChooser<Integer> autonObjectiveChooser = new SendableChooser<Integer>();
	
	SendableChooser<Integer> autonPositionChooser = new SendableChooser<Integer>();
	
	int aPerFlag = 0;
	
	int startingPosition = 0;
	  


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		autonPositionChooser.addDefault("Right", new Integer(0));
		autonPositionChooser.addObject("Middle", new Integer(1));
		autonPositionChooser.addObject("Left", new Integer(2));
		SmartDashboard.putData(autonPositionChooser);
		
		autonObjectiveChooser.addDefault("Autoline", new Integer(0));
		autonObjectiveChooser.addObject("Switch", new Integer(1));
		autonObjectiveChooser.addObject("Scale", new Integer(2));
		SmartDashboard.putData(autonObjectiveChooser);
		
		sensors.resetEncoders();
		 new Thread(() -> {
             UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
             camera.setResolution(352, 240);
             camera.setFPS(30);
             camera.setExposureAuto();
             
             CvSink cvSink = CameraServer.getInstance().getVideo();
             CvSource outputStream = CameraServer.getInstance().putVideo("Main Camera", 352, 288);
             
             Mat source = new Mat();
             Mat output = new Mat();
;             
             while(!Thread.interrupted()) {
                 cvSink.grabFrame(source);
                 Imgproc.cvtColor(source, output, Imgproc.COLOR_RGB2GRAY);
                 outputStream.setFPS(30);
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
		mp.clearMPState();
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
		
		//TODO: ADD INPUT / OTHER WAY TO DETERMINE OR ENTER STARTING POSITION ON startingPosition
		
		imu.reset();
		//autonomousCommand = autonObjectiveChooser.getSelected();
		//drive.autonTalonInit(NeutralMode.Brake)
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData != null){
			if(autonObjectiveChooser.getSelected().intValue() == 0){ //autoline
				if(gameData.charAt(0) == 'R'){
					System.out.println("Caught FMS data Right");
					autonomousCommand = new AutoLine();
				} else if(gameData.charAt(0) == 'L'){
					autonomousCommand = new AutoLine();
				}
			}else if(autonObjectiveChooser.getSelected().intValue() == 1){ //switch
				if(gameData.charAt(0) == 'R'){
					System.out.println("Caught FMS data Right");
					autonomousCommand = new SwitchOnSameSide();
				} else if(gameData.charAt(0) == 'L'){
					autonomousCommand = new SwitchOnSameSide();
				}
			}else if(autonObjectiveChooser.getSelected().intValue() == 2){ //scale
				if(gameData.charAt(0) == 'R'){
					System.out.println("Caught FMS data Right");
					autonomousCommand = new RightAutoLineTalon();
				} else if(gameData.charAt(0) == 'L'){
					autonomousCommand = new RightAutoLineTalon();
				}
			}
		}
		if(autonomousCommand != null){
				autonomousCommand.start();
		}
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SetValueMotionProfile setOutput = mp.getSetValue();

		mp._talon.set(ControlMode.MotionProfile, setOutput.value);
		mp._talon2.set(ControlMode.MotionProfile, setOutput.value);
		mp.control();
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
		
		System.out.println("Objective Chosen: "+ autonObjectiveChooser.getSelected());
		System.out.println("Position Chosen: "+ autonPositionChooser.getSelected());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
