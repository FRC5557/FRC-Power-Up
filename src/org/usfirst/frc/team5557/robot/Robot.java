
package org.usfirst.frc.team5557.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
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

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5557.robot.commands.SwapDriveComand;
import org.usfirst.frc.team5557.robot.commands.autogroups.MiddleAutoLine;
import org.usfirst.frc.team5557.robot.commands.autogroups.RightAutoLine;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team5557.robot.subsystems.ControllerSubsystem;
import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;
import org.usfirst.frc.team5557.robot.subsystems.SensorSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

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
	
	public static OI oi;
	public static Preferences prefs = Preferences.getInstance();

	Command autonomousCommand;
	
	SendableChooser<Command> autonChooser = new SendableChooser<Command>();
	
	SendableChooser<Command> controlChooser = new SendableChooser<Command>();
	
	  public static final ADIS16448_IMU imu = new ADIS16448_IMU();
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		autonChooser.addDefault("Default Auto", new RightAutoLine());
		autonChooser.addObject("My Auto", new MiddleAutoLine());
		
		//buttons
		SmartDashboard.putData("Flight Sticks: ", new SwapDriveComand("STICKS"));
		SmartDashboard.putData("Game Pad: ", new SwapDriveComand("CONTROLLER"));
		
		sensors.resetEncoders();
		 new Thread(() -> {
             UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
             camera.setResolution(640, 480);
             camera.setExposureAuto();
             
             CvSink cvSink = CameraServer.getInstance().getVideo();
             CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
             outputStream.setFPS(25);
             
             Mat source = new Mat();
             Mat output = new Mat();
             
             while(!Thread.interrupted()) {
                 cvSink.grabFrame(source);
                 Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
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
		autonomousCommand = autonChooser.getSelected();
		drive.autonTalonInit(NeutralMode.Brake);
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'R')
		{
			autonomousCommand = new RightAutoLine();
		} else {
			autonomousCommand = new MiddleAutoLine();
			
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
		if(!(arm.getLimSwitchStatus(0))){
			//System.out.println("Raising: " + OI.driveStickZero.getZ()+.10);
			arm.raise(-1*(OI.driveStickZero.getZ())+.10);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
