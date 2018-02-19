package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ControllerSubsystem extends Subsystem{

	int layoutInt;
	double[] restingTriggerVals = new double[2];
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
				System.out.println("Gamepad");
				OI.driveStickZero.setXChannel(0);
				OI.driveStickZero.setTwistChannel(3);
				OI.driveStickZero.setThrottleChannel(4);
				OI.driveStickZero.setZChannel(2);
				restingTriggerVals = triggerCalibrate();
				break;
		}
	}
	
	public double getTrigerThrottle(double leftT, double rightT){
		double fThrottle = 0;
		if(rightT > restingTriggerVals[0]+.1){ //forward input will always take priority over reverse input
			fThrottle = (rightT*(1/1.6)+.47);
		}else if(leftT > restingTriggerVals[1]){
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
	
	public double[] triggerCalibrate(){
		double [] callVal = new double[2];
		callVal[0] = OI.driveStickZero.getTwist(); //left trigger
		callVal[1] = OI.driveStickZero.getThrottle(); //right trigger
		System.out.println("Left Trigger: " + callVal[0]);
		System.out.println("Right Trigger: " + callVal[1]);
		return callVal;
	}
}
