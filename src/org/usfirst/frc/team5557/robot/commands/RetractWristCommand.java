package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RetractWristCommand extends Command {

	private ArmSubsystem arm = ArmSubsystem.getInstance();
	
	@Override
	protected void initialize() {
		//requires(Robot.arm);
	}

	@Override
	protected void execute() {
		arm.retract();
		
	
	} 
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		arm.wristPower = -.1;
	}

}
