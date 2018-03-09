package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseArmCommand extends Command{

	long millis;
	int timeToRun;
	public RaiseArmCommand(int timeInMs) {
		timeToRun = timeInMs > 3000 ? 2900: timeInMs;
	}
	
	@Override
	protected void initialize() {
		//requires(Robot.arm);
		millis = System.currentTimeMillis();
	}

	@Override
	protected void execute() {
		Robot.arm.raise(.5);
		
	} 
	
	@Override
	protected boolean isFinished() {
		if(System.currentTimeMillis() - millis >= timeToRun ){
			System.out.println("arm raised");
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void end() {
		Robot.arm.stop();
	}

}
