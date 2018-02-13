package org.usfirst.frc.team5557.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * 
 * Also contains some constants that were using 
 *     ie. wheel size, voltage constants
 * 
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
		public static int JOYSTICK_DRIVE_ONE = 0;
		public static int JOYSTICK_DRIVE_TWO = 1;
		public static int AUTOMATIC_GEAR_PLACEMENT_BUTTON = 10;
		public static int SHOOTER_BUTTON = 1;
		public static int CLIMBER_BUTTON = 7;
		public static int COLLECTOR_BUTTON = 9;
		public static int REVERSE_CLIMBER_BUTTON = 8;
		public static int EMERGENCY_STOP_BUTTON = 10;
		public static int CAMERA_SWITCH = 11;
		public static final int RAISE_ARM_BUTTON = 1;
			
		// Motor controllers are mapped here
		public static final int LEFT_REAR_MOTOR = 4;
		public static final int LEFT_FRONT_MOTOR = 3; //Encoder on this one
		public static final int RIGHT_FRONT_MOTOR = 2;//Encoder on this one
		public static final int RIGHT_REAR_MOTOR = 1; 
		
		//Arm motors
		public static final int LOWER_ARM_MOTOR_LEFT = 6;
		public static final int LOWER_ARM_MOTOR_RIGHT = 5;
		public static final int WRIST_MOTOR_LEFT = 7;
		public static final int WRIST_MOTOR_RIGHT = 8;
		public static final int COLLECTOR_MOTOR_LEFT = 9;
		public static final int COLLECTOR_MOTOR_RIGHT = 10;
		
		// Constants for encoders connected to Talon SRX
		public static final float PEAK_OUTPUT_VOLTAGE = 10f;
		public static final float NOMINAL_OUTPUT_VOLTAGE = 0f;
		public static final int ENCODER_CODES_PER_REV = 40;
		public static final FeedbackDevice TALON_FEEDBACK_DEVICE = FeedbackDevice.QuadEncoder;
		public static final int ENCODER_PROFILE = 0;
		public static final double PID_FEEDFORWARD = 0;
		public static final double PID_PROPORTIONAL = 0.1;
		public static final double PID_INTEGRAL = 0;
		public static final double PID_DERIVATIVE = 0;
		public static final double CLOSED_LOOP_RAMP_RATE = 0;
		public static final double INTEGRAL_ZONE = 0;
		public static final double WHEEL_SIZE = 15.24; //wheel size in cm
		public static final double WHEEL_CIRC = 95.76; //wheel circumference in cm
		public static final int ULTRA_ANAL = 0;
		public static final float MAXBOTIX_VOLTAGE_CONSTANT_MM = 1024f;
		
		

}
