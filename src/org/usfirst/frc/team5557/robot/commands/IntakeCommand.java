package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command{

	@Override
	protected void initialize() {
		//requires(Robot.arm);
	}

	@Override
	protected void execute() {
		Robot.arm.intake();
		
	
	} 
	
	@Override
	protected boolean isFinished() {
		if(!(Robot.arm.getLimSwitchStatus(RobotMap.INTAKE_LIMIT_SWITCH))){
			return true;
		}
		return false;
	}
	
	@Override
	protected void end() {
		Robot.arm.stop();
	}
}
