package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.commands.DriveForDistanceCommand;
import org.usfirst.frc.team5557.robot.commands.TurnForAngleCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftAutoLine extends CommandGroup{
	
	int startingPosition;
	
	public LeftAutoLine(int startingPosition) {
		this.startingPosition = startingPosition; 	
	}

	public void LeftAutoCrossing(){
		;// Drive forward for 10 ft
		
		// starting left
		if(startingPosition == 0) {
			
			addSequential(new DriveForDistanceCommand(649.32));
			
		} // starting at middle
		else if(startingPosition == 1) {
			
			addSequential(new TurnForAngleCommand(-90)); 
			addSequential(new DriveForDistanceCommand(198.12));
			addSequential(new TurnForAngleCommand(90));
			addSequential(new DriveForDistanceCommand(304.8 + 152.4));
			
		} // going to  right
		else if(startingPosition == 2) {
			
//			addSequential(new TurnForAngleCommand(-90)); 
//			addSequential(new DriveForDistanceCommand(198.12));
//			addSequential(new TurnForAngleCommand(90));
//			addSequential(new DriveForDistanceCommand(304.8 + 152.4));
//			
			
		}
		
		
		 //to turn right - angle is positive, to turn left - angle is negative
	
	}
	
}
