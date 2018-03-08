package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExpellCommand extends Command {

	int motor;
	
	public ExpellCommand(int motor) {
		this.motor = motor;
	}
	
	@Override
	protected void initialize() {
		//requires(Robot.arm);
	}

	@Override
	protected void execute() {
		Robot.arm.expell(motor);
		
	
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
