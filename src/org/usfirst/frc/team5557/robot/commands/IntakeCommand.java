package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;
import org.usfirst.frc.team5557.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command{

	private ArmSubsystem arm = ArmSubsystem.getInstance();
	
	int motor;
	
	public IntakeCommand(int motor) {
		this.motor = motor;
	}
	
	@Override
	protected void initialize() {
		//requires(Robot.arm);
	}

	@Override
	protected void execute() {
		arm.intake(motor);
	} 
	
	@Override
	protected boolean isFinished() {
		if(!(arm.getLimSwitchStatus(RobotMap.INTAKE_LIMIT_SWITCH))){
			return true;
		}
		return false;
	}
	
	@Override
	protected void end() {
		arm.stop();
	}
}
