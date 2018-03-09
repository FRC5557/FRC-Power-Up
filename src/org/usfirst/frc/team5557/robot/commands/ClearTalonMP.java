package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

public class ClearTalonMP extends Command{

	int completeFlag = 0;
	
	@Override
	protected void initialize(){
		
		/*
		 * it's generally a good idea to put motor controllers back into a known
		 * state when robot is disabled. That way when you enable the robot
		 * doesn't just continue doing what it was doing before. BUT if that's
		 * what the application/testing requires than modify this accordingly
		 */
		Robot.mp.clearMPState();
		completeFlag = 1;
	}
	
	@Override
	protected boolean isFinished() {
		if(!(completeFlag==0)) {
			System.out.println("MP state cleared from talons");
			return true;
		}else{
			return false;
		}
	}

}
