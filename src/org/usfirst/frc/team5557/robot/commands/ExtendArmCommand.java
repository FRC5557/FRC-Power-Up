package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendArmCommand extends Command{

	
	@Override
	protected void initialize() {
		//requires(Robot.arm);
	}

	@Override
	protected void execute() {
		Robot.arm.extend();
		
	} 
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.arm.stop();
	}
	
}
