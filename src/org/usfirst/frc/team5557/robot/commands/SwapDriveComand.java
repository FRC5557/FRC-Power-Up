package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwapDriveComand extends Command {

	int layoutInt;
	int eee=0;
	
	public SwapDriveComand(String layout){
		super("SwapDrive");
		switch(layout) {
		case "STICKS":
			layoutInt = 0;
			break;
		case "CONTROLLER":
			layoutInt = 1;
		}
		System.out.println(layout + "fired");
	}
	
	public void initialize(){
	
	}
	
	public void execute() {
		Robot.control.setLayoutInt(layoutInt);
		eee = 1;
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
