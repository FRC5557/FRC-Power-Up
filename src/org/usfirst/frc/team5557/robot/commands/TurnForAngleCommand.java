package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

//
//Basic autonomous turning using Encoders
//
public class TurnForAngleCommand extends Command {
	private double angleInTicks;

	public TurnForAngleCommand(double angle) {
		requires(Robot.drive);
		angleInTicks = ((((61*3.14159265)/360)*angle)/95.76)*4096;
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
		if (angleInTicks > 0){
		Robot.drive.computerDrive(0,.6);
		} else {
		Robot.drive.computerDrive(0, -.6);	
		}
	}

	 /*Needs to be updated Checks if distance is greater than wheel
	 circumference times fraction of full revolution Circumference of Mecanum
	 wheels on robot is 8pi*/
	 
	@Override
	protected boolean isFinished() {
		System.out.println(Robot.drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() + ", " + angleInTicks);
		if (angleInTicks < 0){
			if (1*(Robot.drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition()) <= -angleInTicks) {
			return true;}
			else { return false; }
		}else {
			if (-1*(Robot.drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition()) >= angleInTicks) {
				return true;
			} else {
			return false;}}}
		//	}
		//}
		
	

	@Override
	protected void end() {
		Robot.drive.stop();
	}
}