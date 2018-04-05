package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendWristForTime extends Command {
	
	private ArmSubsystem arm = ArmSubsystem.getInstance();
	
	long millis;
	int timeToRun;
	public ExtendWristForTime() {
		timeToRun = 500;
	}
	
	@Override
	protected void initialize() {
		//requires(Robot.arm);
		millis = System.currentTimeMillis();
	}

	@Override
	protected void execute() {
		arm.wrist.set(.6);
		
	} 
	
	@Override
	protected boolean isFinished() {
		if(System.currentTimeMillis() - millis >= timeToRun ){
			System.out.println("wrist extended");
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void end() {
		arm.stop();
	}

}
