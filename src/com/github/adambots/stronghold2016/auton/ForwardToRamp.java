package com.github.adambots.stronghold2016.auton;

import org.usfirst.frc.team245.robot.Actuators;

import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ForwardToRamp extends Command {
	static double finishDistance = 48, allotedError = 60;
	static boolean reset = false, isDone;
	public ForwardToRamp() {
		// TODO Auto-generated constructor stub
	}

	public ForwardToRamp(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ForwardToRamp(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public ForwardToRamp(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		// optimizer.forwardClassCode(finishDistance);

				// double done = Actuators.getLeftDriveMotor().getError();
		if(!Actuators.getDriveShiftPneumatic().get()){
			Actuators.getDriveShiftPneumatic().set(true);
		}
		/*if (Sensors.getArmMinLimitSwitch().get()) {
			Actuators.getArmAngleMotor().set(0.35);
		} else {
			Actuators.getArmAngleMotor().set(0);
		}*/

		Drive.driveWithPID(finishDistance, finishDistance);
		Actuators.getLeftDriveMotor().setEncPosition(Actuators.getRightDriveMotor().getEncPosition());
		SmartDashboard.putNumber("LEFT_ERROR", Actuators.getLeftDriveMotor().getError());
		SmartDashboard.putNumber("RIGHT_ERROR", Actuators.getRightDriveMotor().getError());

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
