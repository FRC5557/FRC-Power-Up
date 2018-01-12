package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwapDriveComand extends Command {

	private static int layout = -1;
	private int eee = 0;
	public SwapDriveComand(){
		super("SwapDrive");
		requires(Robot.drive);
	}
	
	public void initialize(){
		System.out.println("Swap Pressed");
		switch(layout*-1){
			case 1:
				System.out.println("Swapping to Single Stick Drive");
				break;
			case -1:
				System.out.println("Swapping to Two Stick Drive");
				break;
		}
		layout *= -1;
		System.out.println(layout);
	}
	
	public void execute() {
		System.out.println("execute has" + layout);
		Robot.drive.drive(layout);
		eee =1;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if(eee==1){
			return true;
		}
		return false;
	}

}
