package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/*Driving autonomously in a straight line up until certain distance using
MaxBotix Ultrasonic
*/
public class SonicDriveCommand extends Command {
	private double dis;
	private double speed;
	public SonicDriveCommand(double my_dis, double mspeed) {
		dis = my_dis;
		speed = mspeed;
		requires(Robot.sensors);
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		Robot.drive.computerDrive(speed, 0);
	}

	@Override
	protected boolean isFinished() {
		if (Robot.sensors.getUltraWithVoltage() <= dis) {
			return true;
		}
		return false;
	}

	@Override
	protected void interrupted() {
	}

	@Override

	protected void end() {
		Robot.drive.stop();
	}
}