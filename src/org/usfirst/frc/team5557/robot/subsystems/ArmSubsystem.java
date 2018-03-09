package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem{
	
	WPI_TalonSRX lowerLeft = new WPI_TalonSRX(RobotMap.LOWER_ARM_MOTOR_LEFT);
	WPI_TalonSRX lowerRight = new WPI_TalonSRX(RobotMap.LOWER_ARM_MOTOR_RIGHT);
	SpeedControllerGroup lower = new SpeedControllerGroup(lowerLeft, lowerRight);
	
	WPI_TalonSRX wristMotortLeft = new WPI_TalonSRX(RobotMap.WRIST_MOTOR_LEFT);
	WPI_TalonSRX wristMotortRight = new WPI_TalonSRX(RobotMap.WRIST_MOTOR_RIGHT);
	public SpeedControllerGroup wrist = new SpeedControllerGroup(wristMotortLeft, wristMotortRight);
	
	WPI_TalonSRX intaketMotortLeft = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_LEFT);
	WPI_TalonSRX intakeMotortRight = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_RIGHT);
	SpeedControllerGroup intake = new SpeedControllerGroup(intaketMotortLeft, intakeMotortRight);

	DigitalInput[] limSwitches = new DigitalInput[10];
	public static double wristPower = -.1;
	
	@Override
	protected void initDefaultCommand() {
		//initialize all 9 DIO pins as limit switches
		for(int i=0; i<10; i++){
			limSwitches[i]= new DigitalInput(i);
		}
		
		lowerLeft.setInverted(true);
		wristMotortRight.setInverted(true);
		intaketMotortLeft.setInverted(true);
		
	}

	public void raise(double speed){
		//double speed = Robot.prefs.getDouble("ArmUpVoltage", 0);
		//double speed = OI.driveStickZero.getZ();
		lower.set(speed);
		
	}
	
	public void lowerArm(){
		//double speed = Robot.prefs.getDouble("ArmUpVoltage", 0);
		double speed = OI.driveStickZero.getZ();
		lower.set(-1);
		
	}
	
	public void extend(){
		wristPower = .8;
	}
	
	public void retract(){
		wristPower = -1;
	}
	
	public void expell(int motor){
		double speed = -.6;
		switch(motor){
		case 8:
			intaketMotortLeft.set(speed);
			break;
		case 9:
			intakeMotortRight.set(speed);
			break;
		}
	}
	
	public void intake(int motor){
		double speed = .2;
		switch(motor){
		case 8:
			intaketMotortLeft.set(speed);
			break;
		case 9:
			intakeMotortRight.set(speed);
			break;
		}
	}

	public boolean getLimSwitchStatus(int switchNumber){
		return limSwitches[switchNumber].get();
	}
	
	public void stop() {
		lower.set(0);
		wrist.set(0);
		intake.set(0);
	}
}
