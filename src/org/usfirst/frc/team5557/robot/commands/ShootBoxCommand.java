package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class ShootBoxCommand extends Command{
//	WPI_TalonSRX intaketMotortLeft = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_LEFT);
//	WPI_TalonSRX intakeMotortRight = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR_RIGHT);
//	SpeedControllerGroup intake = new SpeedControllerGroup(intaketMotortLeft, intakeMotortRight);
	
	
	@Override
	protected void initialize() {
		requires(Robot.arm);
	}

	@Override
	protected void execute() {
		Robot.arm.shoot(RobotMap.INTAKE_MOTOR_LEFT);
		Robot.arm.shoot(RobotMap.INTAKE_MOTOR_RIGHT);
		
	} 
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.arm.wristPower = -.1;
	}
	
}
