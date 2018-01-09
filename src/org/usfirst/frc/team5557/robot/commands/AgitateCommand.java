package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AgitateCommand extends Command {

    public AgitateCommand() {
    	//This doesn't require Shooter subsystem at the moment because then we can't run shooting and agitating simultaneously
    }

    protected void execute() {
    	Robot.shooter.agitate(.3);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.shooter.stopCollector();
    }

}
