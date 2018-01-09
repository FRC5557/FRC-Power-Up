package org.usfirst.frc.team5557.robot;

import com.ctre.CANTalon.FeedbackDevice;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	// Driver input devices are mapped here
		public static int JOYSTICK_DRIVE = 0;
		public static int AUTOMATIC_GEAR_PLACEMENT_BUTTON = 10;
		public static int SHOOTER_BUTTON = 1;
		public static int CLIMBER_BUTTON = 7;
		public static int COLLECTOR_BUTTON = 9;
		public static int REVERSE_CLIMBER_BUTTON = 8;
		public static int EMERGENCY_STOP_BUTTON = 12;
		public static int CAMERA_SWITCH = 4;
			
		// Motor controllers are mapped here
		public static final int LEFT_FRONT_MOTOR = 1;
		public static final int LEFT_REAR_MOTOR = 3;
		public static final int RIGHT_FRONT_MOTOR = 7;
		public static final int RIGHT_REAR_MOTOR = 4;
		
		public static final int COLLECTOR_MOTOR = 0;
		public static final int ARM_MOTOR = 0;
		public static final int CLAW_MOTOR = 0;
}
