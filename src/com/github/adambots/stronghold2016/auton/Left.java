package com.github.adambots.stronghold2016.auton;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Sensors;

import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Left {
	static double turnamountL = 40, turnamountR = 40, driveDistance = 48, finishDistance = 60, allotedError = 60;
	static Position_Optimizer optimizer;
	static boolean turned = false, iPosition = false, turned2 = false, finished = false, reset = false;
	static int iteration = 0;

	public Left() {
		optimizer = new Position_Optimizer();

	}

	public static void go() {
		if (!turned) {
			Left.LTurn();
		} else if (turned && !iPosition) {
			SmartDashboard.putBoolean("turned Check", turned);
			iPosition = Forward.go(24.0);
		}
		// Drive.driveWithPID(0, 0);
		if (iteration > 0) {

			if (Math.abs(Actuators.getLeftDriveMotor().getError()) < allotedError
					|| Math.abs(Actuators.getRightDriveMotor().getError()) < allotedError) {

				reset = true;
			}
			if (reset == true) {
				Actuators.getLeftDriveMotor().setEncPosition(0);
				Actuators.getRightDriveMotor().setEncPosition(0);
				reset = false;
				turned = true;
			}
		}
		iteration++;

	}
	public static boolean LTurn(){
		turned = false;
		if (iteration > 0) {
			if (Math.abs(Actuators.getLeftDriveMotor().getError()) < allotedError
					|| Math.abs(Actuators.getRightDriveMotor().getError()) < allotedError) {

				reset = true;
			}
		}
		Drive.driveWithPID(0 - turnamountL, turnamountR);
		if (reset == true) {
			Actuators.getLeftDriveMotor().setEncPosition(0);
			Actuators.getRightDriveMotor().setEncPosition(0);
			reset = false;
			turned = true;
		}
		return turned;
		
	}
}