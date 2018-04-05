package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ExpellCommand extends Command {

	private ArmSubsystem arm = ArmSubsystem.getInstance();
	
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
		arm.expell(motor);
		
	
	} 
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		arm.stop();
	}
}
