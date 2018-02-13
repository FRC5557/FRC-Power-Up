package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem{
	
	WPI_TalonSRX lowerLeft = new WPI_TalonSRX(RobotMap.LOWER_ARM_MOTOR_LEFT);
	WPI_TalonSRX lowerRight = new WPI_TalonSRX(RobotMap.LOWER_ARM_MOTOR_RIGHT);
	SpeedControllerGroup lower = new SpeedControllerGroup(lowerLeft, lowerRight);
	
	WPI_TalonSRX WristMotortLeft = new WPI_TalonSRX(RobotMap.WRIST_MOTOR_LEFT);
	WPI_TalonSRX WristMotortRight = new WPI_TalonSRX(RobotMap.WRIST_MOTOR_RIGHT);
	SpeedControllerGroup wrist = new SpeedControllerGroup(WristMotortLeft, WristMotortRight);
	
	WPI_TalonSRX IntaketMotortLeft = new WPI_TalonSRX(RobotMap.WRIST_MOTOR_LEFT);
	WPI_TalonSRX IntakeMotortRight = new WPI_TalonSRX(RobotMap.WRIST_MOTOR_RIGHT);
	SpeedControllerGroup intake = new SpeedControllerGroup(WristMotortLeft, WristMotortRight);

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		lowerLeft.setInverted(true);
		WristMotortLeft.setInverted(true);

		
	}

	public void raise(){
		System.out.println("raise");
		double speed = Robot.prefs.getDouble("ArmUpVoltage", 0);
		speed = (OI.driveStickOne.getY() > 0) ? speed : -1*speed;
		lower.set(-1*speed);
		
	}
	
	public void extend(){
		double speed = Robot.prefs.getDouble("WristUpVoltage", 0);
		wrist.set(speed);
		
	}
	
	public void expell(){
		double speed = Robot.prefs.getDouble("IntakeUpVoltage", 0);
		intake.set(speed);
	}
	
	public void intake(){
		double speed = Robot.prefs.getDouble("IntakeUpVoltage", 0);
		intake.set(-speed);
	}

	public void stop() {
		lower.set(0);
		wrist.set(0);
	}
}
