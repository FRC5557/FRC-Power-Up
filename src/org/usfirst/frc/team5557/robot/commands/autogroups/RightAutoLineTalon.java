package org.usfirst.frc.team5557.robot.commands.autogroups;

import org.usfirst.frc.team5557.robot.Robot;
import org.usfirst.frc.team5557.robot.RobotMap;
import org.usfirst.frc.team5557.robot.commands.ClearTalonMP;
import org.usfirst.frc.team5557.robot.commands.ConfigTalonForMP;
import org.usfirst.frc.team5557.robot.subsystems.MotionProfileSubsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Robot.mp.StartMotionProfile;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import res.GeneratedMotionProfile;

public class RightAutoLineTalon extends CommandGroup{
	
	/** some example logic on how one can manage an MP */
	MotionProfileSubsystem _example = new MotionProfileSubsystem(new GeneratedMotionProfile());

	
	
	public RightAutoLineTalon() {
		addSequential(new ClearTalonMP());
		addSequential(new ConfigTalonForMP());
		addSequential(new StartMotionProfile());

	}
	

}
