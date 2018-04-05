package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;
import org.usfirst.frc.team5557.robot.subsystems.DriveSubSystem;
import org.usfirst.frc.team5557.robot.subsystems.SensorSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

//
//Basic autonomous turning using Encoders
//
public class TurnForAngleCommand extends Command {
	
	private SensorSubsystem sensors = SensorSubsystem.getInstance();
	private DriveSubSystem drive = DriveSubSystem.getInstance();
	
	private double angleInTicks;
	private boolean pastF1;
	long millis;
	int timeToRun;

	public TurnForAngleCommand(double angle) {
		requires(drive);
		//angleInTicks = angle > 0 ? ((((RobotMap.ROBOT_DIAMETER*Math.PI)/360)*(angle+90)+30)/RobotMap.WHEEL_CIRC)*4096 : ((((RobotMap.ROBOT_DIAMETER*Math.PI)/360)*(angle-90)-30)/RobotMap.WHEEL_CIRC)*4096;
		System.out.println(angleInTicks);
		timeToRun = 2000;
	}

	
	
	@Override
	protected void initialize() {
		millis = System.currentTimeMillis();
		sensors.resetEncoders();
	}

	
	/*Positive angles will make the robot turn to the right. Negative angles
	 will do the opposite
	 */
	 
	@Override
	protected void execute() {
		drive.computerDrive(0, -.2);
	/*	if (angleInTicks > 0){
			if(drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() >= angleInTicks) {
				pastF1 = true;
				drive.computerDrive(0, -.5);
			}else{drive.computerDrive(0,.7);}
		} else {
			if(drive.getTalonSensorC(MotorType.kRearLeft).getQuadraturePosition() >= -angleInTicks) {
				pastF1 = true;
				drive.computerDrive(0, .5);
			}else{drive.computerDrive(0,-.7);}	
		}*/
	}

	 /*Needs to be updated Checks if distance is greater than wheel
	 circumference times fraction of full revolution Circumference of Mecanum
	 wheels on robot is 8pi*/
	 
/*	@Override
	protected boolean isFinished() {
		System.out.println(drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() + ", " + angleInTicks);
		if (angleInTicks > 0){
			if(drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() >= 3000){
				return true;
			}
			if (1*(drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition()) <= -angleInTicks) {
				return true;
			}
			else{ 
				return false;
			}
		}else {
			if (drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() <= -3000) {
				return true;
			}
		}
		return false;
	}*/
	
/*	@Override
	protected boolean isFinished() {
		System.out.println(drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() + ", " + -angleInTicks);
		if(angleInTicks>0){
			if (pastF1 && drive.getTalonSensorC(MotorType.kFrontRight).getQuadraturePosition() >= -angleInTicks-5) {
				return true;
			}
		}else{
			if (pastF1 && drive.getTalonSensorC(MotorType.kRearLeft).getQuadraturePosition() <= -angleInTicks+5) {
				return true;
			}
		}
		return false;
	}

		
	*/
	
	@Override
	protected boolean isFinished() {
		if(System.currentTimeMillis() - millis >= timeToRun ){
			System.out.println("Turn finished");
			return true;
		}
		
		return false;
	}

	@Override
	protected void end() {
		drive.stop();
	}
}