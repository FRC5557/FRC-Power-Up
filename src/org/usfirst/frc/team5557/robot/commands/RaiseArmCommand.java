package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseArmCommand extends Command{

	@Override
	protected void initialize() {
		//requires(Robot.arm);
	}

	@Override
	protected void execute() {
		Robot.arm.raise(1);
		
	} 
	
	@Override
	protected boolean isFinished() {
		if( Robot.arm.getLimSwitchStatus(0)){
			System.out.println("Lim Switch triggered");
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void end() {
		Robot.arm.stop();
	}

}
