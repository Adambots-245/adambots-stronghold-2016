package com.github.adambots.stronghold2016.auton;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Sensors;

import com.github.adambots.stronghold2016.drive.Drive;

public class Right {
	static double turnamountL = 40, turnamountR = 40, driveDistance = 48, finishDistance = 60;
	Position_Optimizer optimizer;

	public Right() {
		optimizer = new Position_Optimizer();
	}

	public static void go() {
boolean turned = false, iPosition = false, turned2 = false, finished = false;
		
		
		

		if (!turned){
			Drive.driveWithPID(turnamountR, -turnamountL);
			if(Math.abs(Actuators.getLeftDriveMotor().getError()) <20 && Math.abs(Actuators.getRightDriveMotor().getError()) < 20) {
				turned = true;
			}
		}
		if (turned && !iPosition){
			Drive.driveWithPID(driveDistance, driveDistance);
			iPosition = true;
		}

		if (iPosition && !turned2){
			Drive.driveWithPID(-turnamountR, turnamountL);
			turned2 = true;
		}
		if (turned2 && !finished){
			Drive.driveWithPID(finishDistance, finishDistance);
			finished = true;
		}

	}
}
