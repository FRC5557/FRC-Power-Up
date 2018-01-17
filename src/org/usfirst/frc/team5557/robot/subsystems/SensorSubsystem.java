package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem containing all sensors for the robot
 */
public class SensorSubsystem extends Subsystem {
	public AnalogInput ultra = new AnalogInput(RobotMap.ULTRA_ANALOG);

	public SensorSubsystem() {
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
	 * For MaxBotix Ultrasonic
	 */
	public double getUltra() {
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
		// double UL = Robot.drive.getTalon("UL").getEncPosition();
		double BL = Math.abs(Robot.drive.getTalon(MotorType.kRearLeft).getEncPosition());
		// double UR = Robot.drive.getTalon("UR").getEncPosition();
		// double BR = Robot.drive.getTalon("BR").getEncPosition();
		double avgDis = (BL)/RobotMap.ENCODER_CONVERSION;
		return avgDis;
	}

	public double getDis(MotorType m) {
		return Robot.drive.getTalon(m).getEncPosition();
	}

	public double getSpeed(MotorType m) {
		return Robot.drive.getTalon(m).getEncVelocity();
	}

}
