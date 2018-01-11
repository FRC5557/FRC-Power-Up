package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwapDriveComand extends Command {

	private static int layout = -1;
	
	public SwapDriveComand(){
		super("");
		layout *= -1;
	}
	
	public void execute() {
		Robot.drive.drive(layout);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
