package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
* This class in a Singleton, this means that it can only be instanced once.
* This is done in order to prevent clashes in access to things like sensor values and motor outputs.
* To instantiated this class you must call its static getInstance() method.
* 	The first time getInstance is called it will return a new instance of this class,
* 	every time after that it will return the same instance as the first time
* This class can not be instantiated using new because the constructor is private
* 
* This class handles getting data from sensors
* 	Ultrasonics, Encoders, and limit switches
* 
*/

public class SensorSubsystem extends Subsystem {
	
	private static SensorSubsystem instance = null;
	private DriveSubSystem drive = DriveSubSystem.getInstance();
	
	public AnalogInput ultra = new AnalogInput(RobotMap.ULTRA_ANALOG);

	public static SensorSubsystem getInstance() {
		if(instance == null) {
			instance = new SensorSubsystem();
		}
		return instance;
	}
	
	private SensorSubsystem() {
		System.out.println("SensorSubsystem instantiated");
	}

	@Override
	protected void initDefaultCommand() {
	}

	/*
	 * For MaxBotix Ultrasonic
	 * Converts Voltage receieved from ultrasonic sensor into millimeters
	 */
	public double getUltraWithVoltage() {
		return (((ultra.getVoltage()*1000)/4.88)/2);
	}
	


	public void resetEncoders() {
		for (MotorType m : MotorType.values()) {
			drive.getTalonSensorC(m).setQuadraturePosition(0, 0);
		}
	}

	/**
	 * Gets the position values from each Talon Feedback Device and averages
	 * them
	 */
	public double getDis() { //don't use this on the testing bot one of its gear boxes is slower than the other
		//10.71:1 ration cim to hex shaft
		//1:1 for hex shaft to wheels

		int BR = -1*(drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition());
		int FL = drive.getTalonSensorC(MotorType.kFrontLeft).getQuadraturePosition();
		
		System.out.printf("Front Left %d, Back Right: %d \n", FL, BR);
		
		double averageRote = (BR+FL)/2;
		return (averageRote/RobotMap.WHEEL_SIZE);
	}
	
 
	public double getDis(MotorType m) {
		int encoder = drive.getTalonSensorC(m).getQuadraturePosition();
		return (encoder/4096)*RobotMap.WHEEL_CIRC;
	}

	public double getSpeed(MotorType m) {
		return drive.getTalonSensorC(m).getQuadratureVelocity();
	}

}
