package org.usfirst.frc.team5557.robot.subsystems;
import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.RobotMap;
import org.usfirst.frc.team5557.robot.commands.ManualDriveCommand;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class DriveSubSystem extends Subsystem{

	private VictorSP leftFront = new VictorSP(RobotMap.LEFT_FRONT_MOTOR);
	private VictorSP leftRear = new VictorSP(RobotMap.LEFT_REAR_MOTOR);
	private VictorSP rightFront = new VictorSP(RobotMap.RIGHT_FRONT_MOTOR);
	private VictorSP rightRear = new VictorSP(RobotMap.RIGHT_REAR_MOTOR);
	
	/*private WPI_TalonSRX leftFront = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR);
	private WPI_TalonSRX leftRear = new WPI_TalonSRX(RobotMap.LEFT_REAR_MOTOR);
	private WPI_TalonSRX rightFront = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR);
	private WPI_TalonSRX rightRear = new WPI_TalonSRX(RobotMap.RIGHT_REAR_MOTOR);*/

	SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFront, leftRear);
	SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFront, rightRear);
	DifferentialDrive difDrive = new DifferentialDrive(leftGroup, rightGroup);

	public DriveSubSystem() {
		
		// Set up Talon SRX controllers

		/*for (MotorType m : MotorType.values()) {
			//calls config on the CANTalon object getTalon returns
			getTalon(m).configNominalOutputForward(+RobotMap.NOMINAL_OUTPUT_VOLTAGE, 0); 
			getTalon(m).configNominalOutputReverse(-RobotMap.NOMINAL_OUTPUT_VOLTAGE, 0);
			getTalon(m).configPeakOutputForward(+RobotMap.PEAK_OUTPUT_VOLTAGE, 0);
			getTalon(m).configPeakOutputReverse(-RobotMap.PEAK_OUTPUT_VOLTAGE, 0);
		}*/

		// This suppresses the "Output not frequent enough" message
		difDrive.setSafetyEnabled(false);
	}

	
/*	TalonSRX getTalon(MotorType m) {
		switch (m) {
		case kFrontLeft:
			return leftFront;
		case kRearLeft:
			return leftRear;
		case kFrontRight:
			return rightFront;
		case kRearRight:
			return rightRear;
		default:
			return leftFront;
		}
	}*/
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManualDriveCommand());
	}
	
	public void drive(int layout) {
		double X1 = -OI.driveStickZero.getX();
		double Y1 = -OI.driveStickZero.getY();
		double rotation1 = OI.driveStickZero.getTwist();
		
		double X2 = -OI.driveStickOne.getX();
		double Y2 = -OI.driveStickOne.getY();
		double rotation2 = OI.driveStickOne.getTwist();
		switch(layout) {
		    case 1:
		    	difDrive.arcadeDrive(rotation1,Y1);
		    	break;
		    case -1:
		    	difDrive.tankDrive(Y1, Y2);
		    	break;
		    	
		}
		
	}
	
	

}
