package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ControllerSubsystem extends Subsystem{

	int layoutInt;
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	//sets the channels for each type of controller
	public void setController(int layout){
		switch(layout){
			case 0: //Sticks
				OI.driveStickZero.setTwistChannel(2);
				OI.driveStickZero.setYChannel(1);
				break;
			case 1: //Controller
				OI.driveStickZero.setXChannel(0);
				OI.driveStickZero.setTwistChannel(3);
				OI.driveStickZero.setThrottleChannel(4);
				break;
		}
	}
	
	public double getTrigerThrottle(double leftT, double rightT){
		double fThrottle = 0;
		if(rightT > -.73){ //forward input will always take priority over reverse input
			fThrottle = (rightT*(1/1.6)+.47);
		}else if(leftT > -.74){
			fThrottle = -(leftT*(1/1.6)+.55); //backward has slightly higher offset because motors are slower backwards
		}
		return fThrottle;
	}
	
	public int getLayoutInt(){
		return layoutInt;
	}
	
	public void setLayoutInt(int l) {
		layoutInt = l;
	}
	
}
