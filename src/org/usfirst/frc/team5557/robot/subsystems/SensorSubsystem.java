package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Subsystem containing all sensors for the robot
 */
public class SensorSubsystem extends Subsystem {
	public AnalogInput ultra = new AnalogInput(RobotMap.ULTRA_ANAL);
	//private Ultrasonic ultra = new Ultrasonic(5,RobotMap.ULTRA_ANAL);

	public SensorSubsystem() {
		//ultra.setAutomaticMode(true);
		
		// Correctly initialize and set up encoders
		/*for (MotorType m : MotorType.values()) {
			Robot.drive.getTalon(m).setFeedbackDevice(RobotMap.TALON_FEEDBACK_DEVICE);
			Robot.drive.getTalon(m).configEncoderCodesPerRev(RobotMap.ENCODER_CODES_PER_REV);
			Robot.drive.getTalon(m).reverseSensor(false);
			Robot.drive.getTalon(m).setProfile(RobotMap.ENCODER_PROFILE);
			Robot.drive.getTalon(m).setF(RobotMap.PID_FEEDFORWARD);
			Robot.drive.getTalon(m).setPID(RobotMap.PID_PROPORTIONAL, RobotMap.PID_INTEGRAL, RobotMap.PID_DERIVATIVE);
			Robot.drive.getTalon(m).setCloseLoopRampRate(RobotMap.CLOSED_LOOP_RAMP_RATE);
			//Robot.drive.getTalon(m).setIzone(RobotMap.INTEGRAL_ZONE);
		}*/
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * For Vex UltraSonic
	 * TODO make it for MaxBotix Ultrasonic
	 */
	public double getUltra() {
		//return ultra.getRangeMM();
		return ultra.getVoltage() * RobotMap.MAXBOTIX_VOLTAGE_CONSTANT_MM;
		
	}

	public void resetEncoders() {
		/*for (MotorType m : MotorType.values()) {
			Robot.drive.getTalon(m).setPosition(0);
		}*/
	}

	/**
	 * Gets the position values from each Talon Feedback Device and averages
	 * them
	 */
	public double getDis() {
		
		int FL = Robot.drive.getTalon(MotorType.kFrontLeft).getQuadraturePosition();
		int BL = Math.abs(Robot.drive.getTalon(MotorType.kRearLeft).getQuadraturePosition());
		int FR = Robot.drive.getTalon(MotorType.kFrontRight).getQuadraturePosition();
		int BR = Robot.drive.getTalon(MotorType.kRearRight).getQuadraturePosition();
		
		double avgDis = (FL+BL+FR+BR)/4;
		return avgDis;
	}

	public double getDis(MotorType m) {
		return Robot.drive.getTalon(m).getQuadraturePosition();
	}

	public double getSpeed(MotorType m) {
		return Robot.drive.getTalon(m).getQuadratureVelocity();
	}

}
