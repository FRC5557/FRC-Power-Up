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

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		lowerLeft.setInverted(true);

		
	}

	public void raise(){
		System.out.println("raise");
		double speed = Robot.prefs.getDouble("ArmUpVoltage", 0);
		speed = (OI.driveStickOne.getY() > 0) ? speed : -1*speed;
		lower.set(-1*speed);
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		lower.set(0);
	}
}
