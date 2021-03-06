package com.github.adambots.stronghold2016.auton;

import edu.wpi.first.wpilibj.command.Command;

//import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Gamepad;
import org.usfirst.frc.team245.robot.Sensors;

import com.github.adambots.stronghold2016.arm.Arm;
import com.github.adambots.stronghold2016.drive.Drive;
import com.github.adambots.stronghold2016.shooter.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class ForwardShoot extends Command {
	static double finishDistance = -210, allotedError = 60;
	static boolean reset = false, isDone;
	static Timer timer = new Timer();
	static Timer rollerDuration = new Timer();
	static int errorThresh = 120;

	int time = 0;
	static double rollerTime = 5;
	static boolean driveDone = false;

	public ForwardShoot() {
		// TODO Auto-generated constructor stub
	}

	public ForwardShoot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ForwardShoot(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public ForwardShoot(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Actuators.getLeftDriveMotor().setPosition(0);
		Actuators.getRightDriveMotor().setPosition(0);
		Drive.drive(0);

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		// optimizer.forwardClassCode(finishDistance);

		// double done = Actuators.getLeftDriveMotor().getError();
		// if (!Actuators.getDriveShiftPneumatic().get()) {
		// Actuators.getDriveShiftPneumatic().set(true);
		// }
		// if (!Actuators.getRightDriveMotor().isAlive() &&
		// !Actuators.getLeftDriveMotor().isAlive()) {
		// timer.scheduleAtFixedRate(new TimerTask() {
		// public void run() {
		// if (time < 10) {
		// Drive.drive(.5);
		// } else {
		// Drive.drive(0);
		// }
		// time++;
		// }
		// }, 20, 20);
		// return;
		// }
		/*
		 * if (Sensors.getArmMinLimitSwitch().get()) {
		 * Actuators.getArmAngleMotor().set(0.35); } else {
		 * Actuators.getArmAngleMotor().set(0); }
		 */
if(!driveDone){
		Drive.driveWithPID(finishDistance, finishDistance);
		if(Math.abs(Actuators.getLeftDriveMotor().getError()) < errorThresh){
			driveDone = true;
		}
		// if (Actuators.getRightDriveMotor().isAlive() &&
		// !Actuators.getLeftDriveMotor().isAlive())
		// Actuators.getLeftDriveMotor().setEncPosition(Actuators.getRightDriveMotor().getEncPosition());
		// if (!Actuators.getRightDriveMotor().isAlive() &&
		// Actuators.getLeftDriveMotor().isAlive())
		// Actuators.getRightDriveMotor().setEncPosition(Actuators.getLeftDriveMotor().getEncPosition());
		SmartDashboard.putNumber("LEFT_ERROR", Actuators.getLeftDriveMotor().getError());
		SmartDashboard.putNumber("RIGHT_ERROR", Actuators.getRightDriveMotor().getError());
}else{
	

		Shooter.shoot(false);
		Arm.armPID(Arm.loadToCatapultPosition); // move arm to position
//		if (Actuators.getArmAngleMotor().getError() <= 100) {
//			rollerDuration.start();
//			if(rollerDuration.get() < rollerTime){
//			Arm.rollers(true, false);
//			} else {
//				rollerDuration.stop();
//			}
//			
//		}
//		if (rollerDuration.get() >= rollerTime) {
//			Shooter.shoot(true);
//		}

	}
}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {

		timer.stop();
		Drive.drive(0);
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
