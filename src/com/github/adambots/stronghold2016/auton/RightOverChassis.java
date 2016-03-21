package com.github.adambots.stronghold2016.auton;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Sensors;

import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RightOverChassis extends Command {
	static double turnamountL = 40, turnamountR = 40, driveDistance = -48, finishDistance = -200, allotedError = 157,
			iteration = 0, wait = 0;
	static boolean rightTurned = false, inPosition = false, leftTurned = false, finished = false, reset = false,
			turned = false, turned2 = false;

	public RightOverChassis() {
		turnamountL = 40;
		turnamountR = 40;
		driveDistance = -48;
		finishDistance = -200;
		allotedError = 157;
		iteration = 0;
		wait = 0;
		rightTurned = false;
		inPosition = false;
		leftTurned = false;
		finished = false;
		reset = false;
		turned = false;
		turned2 = false;
		// TODO Auto-generated constructor stub
	}

	public RightOverChassis(String name) {
		super(name);
		turnamountL = 40;
		turnamountR = 40;
		driveDistance = -48;
		finishDistance = -200;
		allotedError = 157;
		iteration = 0;
		wait = 0;
		rightTurned = false;
		inPosition = false;
		leftTurned = false;
		finished = false;
		reset = false;
		turned = false;
		turned2 = false;
		// TODO Auto-generated constructor stub
	}

	public RightOverChassis(double timeout) {
		super(timeout);
		turnamountL = 40;
		turnamountR = 40;
		driveDistance = -48;
		finishDistance = -200;
		allotedError = 157;
		iteration = 0;
		wait = 0;
		rightTurned = false;
		inPosition = false;
		leftTurned = false;
		finished = false;
		reset = false;
		turned = false;
		turned2 = false;
		// TODO Auto-generated constructor stub
	}

	public RightOverChassis(String name, double timeout) {
		super(name, timeout);
		turnamountL = 40;
		turnamountR = 40;
		driveDistance = -48;
		finishDistance = -200;
		allotedError = 157;
		iteration = 0;
		wait = 0;
		rightTurned = false;
		inPosition = false;
		leftTurned = false;
		finished = false;
		reset = false;
		turned = false;
		turned2 = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		turnamountL = 40;
		turnamountR = 40;
		driveDistance = -48;
		finishDistance = -200;
		allotedError = 157;
		iteration = 0;
		wait = 0;
		rightTurned = false;
		inPosition = false;
		leftTurned = false;
		finished = false;
		reset = false;
		turned = false;
		turned2 = false;
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		// optimizer.forwardClassCode(finishDistance);

		// double done = Actuators.getLeftDriveMotor().getError();

		/*
		 * if (Sensors.getArmMinLimitSwitch().get()) {
		 * Actuators.getArmAngleMotor().set(0.35); } else {
		 * Actuators.getArmAngleMotor().set(0); }
		 */
		if (!turned) {
			if (!Actuators.getDriveShiftPneumatic().get()) {
				Actuators.getDriveShiftPneumatic().set(true);
			}
			System.out.println("Stage 1");
			Drive.driveWithPID(turnamountL, -turnamountR);
			if (iteration > 1) {
				if (Math.abs(Actuators.getLeftDriveMotor().getError()) < allotedError) {
					leftTurned = true;
				}
				if (Math.abs(Actuators.getRightDriveMotor().getError()) < allotedError) {
					rightTurned = true;
				}
				if (leftTurned && rightTurned) {
					turned = true;
					leftTurned = false;
					rightTurned = false;
					iteration = 0;
					Actuators.getLeftDriveMotor().setPosition(0);
					Actuators.getRightDriveMotor().setPosition(0);
					Drive.drive(0);
				}
			}
		} else if (turned && !inPosition) {
			Drive.driveWithPID(driveDistance, driveDistance);
			System.out.println("Stage 2");
			if (Math.abs(Actuators.getLeftDriveMotor().getError()) < allotedError
					&& Math.abs(Actuators.getRightDriveMotor().getError()) < allotedError && iteration > 1) {
				inPosition = true;
				iteration = 0;
				Actuators.getLeftDriveMotor().setPosition(0);
				Actuators.getRightDriveMotor().setPosition(0);
				Drive.drive(0);
			}
		} else if (inPosition && !turned2) {
			System.out.println("Stage 3");
			if (wait > 90){
			Drive.driveWithPID(-(turnamountR-10), (turnamountL-10));
			if (iteration > 1) {
				if (Math.abs(Actuators.getLeftDriveMotor().getError()) < allotedError) {
					leftTurned = true;
				}
				if (Math.abs(Actuators.getRightDriveMotor().getError()) < allotedError) {
					rightTurned = true;
				}
				if (leftTurned && rightTurned) {
					turned2 = true;
					leftTurned = false;
					rightTurned = false;
					Actuators.getLeftDriveMotor().setPosition(0);
					Actuators.getRightDriveMotor().setPosition(0);
					Drive.drive(0);
				}
			}
			}else {
				wait ++;
			}
		} else if (turned2) {
			System.out.println("Stage 4");
			if (!Actuators.getDriveShiftPneumatic().get()) {
				Actuators.getDriveShiftPneumatic().set(true);
			}
			Drive.driveWithPID(finishDistance, finishDistance);
		}
		SmartDashboard.putNumber("LEFT_ERROR", Actuators.getLeftDriveMotor().getError());
		SmartDashboard.putNumber("RIGHT_ERROR", Actuators.getRightDriveMotor().getError());
		iteration++;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		Drive.drive(0);
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
