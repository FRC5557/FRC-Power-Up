package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseArmCommand extends Command{

	private ArmSubsystem arm = ArmSubsystem.getInstance();
	
	long millis;
	int timeToRun;
	public RaiseArmCommand(int timeInMs) {
		timeToRun = timeInMs > 3000 ? 2900: timeInMs;
	}
	
	@Override
	protected void initialize() {
		//requires(arm);
		millis = System.currentTimeMillis();
	}

	@Override
	protected void execute() {
		arm.raise(.5);
		
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
		arm.stop();
	}

}
