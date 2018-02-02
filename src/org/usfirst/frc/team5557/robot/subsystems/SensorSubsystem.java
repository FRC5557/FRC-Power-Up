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
	 * For MaxBotix Ultrasonic
	 * Converts the bits returned from the ultrasonic sensor into millimeters
	 */
	public double getUltraWithBits() {
		//return ultra.getRangeMM();
		//return ultra.getVoltage() * RobotMap.MAXBOTIX_VOLTAGE_CONSTANT_MM; 
		
		return ultra.getAverageBits() * 5;
		
	}
	
	/*
	 * For MaxBotix Ultrasonic
	 * Converts Voltage receieved from ultrasonic sensor into millimeters
	 */
	
	public double getUltraWithVoltage() {
		//double mV = (ultra.getVoltage() / 1024) * 1000; 
		double mV = (ultra.getVoltage() / 1000) * 1024;
		
		//double mV = ultra.getVoltage() * 1024;
		
		// (MM / 5) * 4.88 mV
		// 4.88 mV = 5mm
		double voltageInMM = (mV / 4.88) * 5;
		
		System.out.println("V" + ultra.getVoltage());
		
		return voltageInMM; 
		
		
		
	}
	
	

	public void resetEncoders() {
		for (MotorType m : MotorType.values()) {
			Robot.drive.getTalon(m).setQuadraturePosition(0, 1000);
		}
	}

	/**
	 * Gets the position values from each Talon Feedback Device and averages
	 * them
	 */
	public double getDis() { //don't use this on the testing bot one of its gear boxes is slower than the other
		//10.71:1 ration cim to hex shaft
		//1:1 for hex shaft to wheels

		int BR = -1*(Robot.drive.getTalon(MotorType.kRearRight).getQuadraturePosition());
		int FL = Robot.drive.getTalon(MotorType.kFrontLeft).getQuadraturePosition();
		
		System.out.printf("Front Left %d, Back Right: %d \n", FL, BR);
		
		double averageRote = (BR+FL)/2;
		return averageRote/RobotMap.WHEEL_SIZE;
	}
	
	public double getDisBalanced(){ //one side of the robot is slower than the other so this gets a 
		int BL = -1*(Robot.drive.getTalon(MotorType.kFrontLeft).getQuadraturePosition());
		int BR = Robot.drive.getTalon(MotorType.kFrontRight).getQuadraturePosition();
		
		
		double averageRote = (BL+BR)/2;
		return averageRote*RobotMap.WHEEL_SIZE;
	}
 
	public double getDis(MotorType m) {
		return Robot.drive.getTalon(m).getQuadraturePosition();
	}

	public double getSpeed(MotorType m) {
		return Robot.drive.getTalon(m).getQuadratureVelocity();
	}

}
