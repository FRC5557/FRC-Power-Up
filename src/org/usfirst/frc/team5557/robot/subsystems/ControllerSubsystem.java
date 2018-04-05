package org.usfirst.frc.team5557.robot.subsystems;

import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
* This class in a Singleton, this means that it can only be instanced once.
* This is done in order to prevent clashes in access to things like sensor values and motor outputs.
* To instantiated this class you must call its static getInstance() method.
* 	The first time getInstance is called it will return a new instance of this class,
* 	every time after that it will return the same instance as the first time
* This class can not be instantiated using new because the constructor is private
* 
* This class handles processing the data coming off of the ps4 controller
* This does not handle actually getting the input data coming off the controller
* 	that is the job of the OI class
* 
*/

public class ControllerSubsystem extends Subsystem{

	private static ControllerSubsystem instance = null;
	
	int layoutInt;
	double[] restingTriggerVals = new double[2];
	
	public static ControllerSubsystem getInstance() {
		if(instance == null) {
			instance = new ControllerSubsystem();
		}
		return instance;
	}
	
	private ControllerSubsystem() {
		System.out.println("ControllerSubsystem instantiated");
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	//sets the channels for each type of controller
	public void setController(int layout){
		System.out.println("Gamepad");
		OI.driveStickZero.setXChannel(0);
		OI.driveStickZero.setTwistChannel(3);
		OI.driveStickZero.setThrottleChannel(4);
		OI.driveStickZero.setZChannel(2);
		restingTriggerVals = triggerCalibrate();
	}
	
	public double getTrigerThrottle(double leftT, double rightT){
		double fThrottle = 0;
		if(rightT > restingTriggerVals[0]+.1){ //forward input will always take priority over reverse input
			fThrottle = (rightT*(1/1.6)+.30);
		}else if(leftT > restingTriggerVals[1]){
			fThrottle = -(leftT*(1/1.6)+.45); //backward has slightly higher offset because motors are slower backwards
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
