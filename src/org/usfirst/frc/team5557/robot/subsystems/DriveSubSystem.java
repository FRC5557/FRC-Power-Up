package org.usfirst.frc.team5557.robot.subsystems;
import org.usfirst.frc.team5557.robot.OI;
import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;
import org.usfirst.frc.team5557.robot.commands.ManualDriveCommand;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;


public class DriveSubSystem extends Subsystem{

	private WPI_TalonSRX leftFrontTal = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR);
	private WPI_TalonSRX leftRearTal = new WPI_TalonSRX(RobotMap.LEFT_REAR_MOTOR);
	private WPI_TalonSRX rightFrontTal = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR);
	private WPI_TalonSRX rightRearTal = new WPI_TalonSRX(RobotMap.RIGHT_REAR_MOTOR);

	SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFrontTal, leftRearTal);
	SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFrontTal, rightRearTal);
	DifferentialDrive difDrive = new DifferentialDrive(leftGroup, rightGroup);
	
	public double left, right;
	int layoutInt;

	public DriveSubSystem() {
		
		// Set up Talon SRX controllers

		for (MotorType m : MotorType.values()) {
			//calls config on the CANTalon object getTalon returns
			getTalon(m).configNominalOutputForward(+RobotMap.NOMINAL_OUTPUT_VOLTAGE, 0); 
			getTalon(m).configNominalOutputReverse(-RobotMap.NOMINAL_OUTPUT_VOLTAGE, 0);
			getTalon(m).configPeakOutputForward(+RobotMap.PEAK_OUTPUT_VOLTAGE, 0);
			getTalon(m).configPeakOutputReverse(-RobotMap.PEAK_OUTPUT_VOLTAGE, 0);
		}

		// This suppresses the "Output not frequent enough" message
		difDrive.setSafetyEnabled(false);
	}

	
	public SensorCollection getTalonSensorC(MotorType m) {
		switch (m) {
		case kFrontLeft:
			return new SensorCollection(leftFrontTal);
		case kRearLeft:
			return new SensorCollection(leftRearTal);
		case kFrontRight:
			return new SensorCollection(rightFrontTal);
		case kRearRight:
			return new SensorCollection(rightRearTal);
		default:
			return new SensorCollection(leftFrontTal);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ManualDriveCommand());
		Robot.control.setController(1);
	}
	
	public void drive() {
		//checkLayout();
		layoutInt = Robot.control.getLayoutInt();
		double turn = 0;
		double throttle = 0;
		//Controller Drive
		turn = OI.driveStickZero.getX() > 0 ? OI.driveStickZero.getX()*1 : OI.driveStickOne.getX()*1;
		throttle = Robot.control.getTrigerThrottle(OI.driveStickZero.getTwist(), OI.driveStickZero.getThrottle());
		difDrive.arcadeDrive(throttle,turn);
	}
	
	
	public void computerDrive(double speed, double rotate) {
		difDrive.arcadeDrive(speed, rotate);
	}

    public void stop() {
    	computerDrive(0,0);
		
	}


	public WPI_TalonSRX getTalon(MotorType m) {
		// TODO Auto-generated method stub		
		switch (m) {
		case kFrontLeft:
			return leftFrontTal;
		case kRearLeft:
			return leftRearTal;
		case kFrontRight:
			return rightFrontTal;
		case kRearRight:
			return rightRearTal;
		default:
			return leftFrontTal;
		}

	}
	
	public void autonTalonInit(NeutralMode mode){
		for (MotorType m : MotorType.values()) {
			//calls config on the CANTalon object getTalon returns
			getTalonSensorC(m).setQuadraturePosition(0, 1);
			getTalon(m).setNeutralMode(mode);
		}
	}


		

}
