package org.usfirst.frc.team5557.robot.commands;

import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ConfigTalonForMP extends Command {
	int completeFlag = 0;
	
	@Override
	protected void initialize(){
		Robot.mp.configTalonForProfile();
		completeFlag = 1;
	}
	
	@Override
	protected boolean isFinished() {
		if(!(completeFlag==0)) {
			return true;
		}else{
			return false;
		}
	}

}
