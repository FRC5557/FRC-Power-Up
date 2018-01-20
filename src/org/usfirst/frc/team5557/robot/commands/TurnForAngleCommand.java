package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

//
//Basic autonomous turning using Encoders
//
public class TurnForAngleCommand extends Command {
	private double angle;

	public TurnForAngleCommand(double angle) {
		requires(Robot.drive);
		this.angle = angle;
	}

	@Override
	protected void initialize() {
		Robot.sensors.resetEncoders();
	}

	
	/*Positive angles will make the robot turn to the right. Negative angles
	 will do the opposite
	 */
	 
	@Override
	protected void execute() {
		Robot.drive.computerDrive(0,.3*(angle/Math.abs(angle)));
	}

	 /*Needs to be updated Checks if distance is greater than wheel
	 circumference times fraction of full revolution Circumference of Mecanum
	 wheels on robot is 8pi*/
	 
	@Override
	protected boolean isFinished() {
		if (Robot.sensors.getDis(MotorType.kFrontLeft) >= ((Math.abs(angle) / 360) * (8 * Math.PI))) {
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		Robot.drive.stop();
	}
}