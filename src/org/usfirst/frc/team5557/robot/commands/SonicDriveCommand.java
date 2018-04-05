package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;
import org.usfirst.frc.team5557.robot.subsystems.SensorSubsystem;

import edu.wpi.first.wpilibj.command.Command;


/*Driving autonomously in a straight line up until certain distance using
MaxBotix Ultrasonic
*/
public class SonicDriveCommand extends Command {
	
	private SensorSubsystem sensors = SensorSubsystem.getInstance();
	private DriveSubSystem drive = DriveSubSystem.getInstance();
	
	private double dis;
	private double speed;
	public SonicDriveCommand(double my_dis, double mspeed) {
		dis = my_dis;
		speed = mspeed;
		requires(sensors);
		requires(drive);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		drive.computerDrive(.5,0);
	}

	@Override
	protected boolean isFinished() {
		if (sensors.getUltraWithVoltage() <= dis) {
			return true;
		}
		return false;
	}

	@Override
	protected void interrupted() {
	}

	@Override

	protected void end() {
		drive.stop();
	}
}