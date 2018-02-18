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
	SpeedControllerGroup wrist = new SpeedControllerGroup(wristMotortLeft, wristMotortRight);
	
	WPI_TalonSRX intaketMotortLeft = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_LEFT);
	WPI_TalonSRX intakeMotortRight = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_RIGHT);
	SpeedControllerGroup intake = new SpeedControllerGroup(intaketMotortLeft, intakeMotortRight);

	DigitalInput[] limSwitches = new DigitalInput[10];
	
	@Override
	protected void initDefaultCommand() {
		//initialize all 9 DIO pins as limit switches
		for(int i=0; i<10; i++){
			limSwitches[i]= new DigitalInput(i);
		}
		
		lowerLeft.setInverted(true);
		wristMotortLeft.setInverted(true);
		intaketMotortLeft.setInverted(true);
		
	}

	public void raise(double speed){
		System.out.println("raise");
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
		double speed = Robot.prefs.getDouble("WristUpVoltage", 0);
		wrist.set(1);
	}
	
	public void retract(){
		double speed = Robot.prefs.getDouble("WristUpVoltage", 0);
		wrist.set(-.7);
	}
	
	public void expell(){
		double speed = Robot.prefs.getDouble("IntakeUpVoltage", 0);
		intake.set(-1);
	}
	
	public void intake(){
		double speed = Robot.prefs.getDouble("IntakeUpVoltage", 0);
		intake.set(1);
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
