/**
 * 
 */
package com.github.adambots.stronghold2016.auton;

import java.util.Timer;

import org.usfirst.frc.team245.robot.Actuators;

import com.github.adambots.stronghold2016.camera.AutoTarget;
import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Robotics
 *
 */
public class SmartShoot extends Command {
	static double finishDistance = -100, allotedError = 60;
	static boolean reset = false, isDone;
	static boolean atPosition = false;
	static Timer timer = new Timer();
	int time = 0;

	public SmartShoot() {
		// TODO Auto-generated constructor stub
	}

	public SmartShoot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SmartShoot(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public SmartShoot(String name, double timeout) {
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
//		if (!Actuators.getDriveShiftPneumatic().get()) {
//			Actuators.getDriveShiftPneumatic().set(true);
//		}
//		if (!Actuators.getRightDriveMotor().isAlive() && !Actuators.getLeftDriveMotor().isAlive()) {
//			timer.scheduleAtFixedRate(new TimerTask() {
//				public void run() {
//					if (time < 10) {
//						Drive.drive(.5);
//					} else {
//						Drive.drive(0);
//					}
//					time++;
//				}
//			}, 20, 20);
//			return;
//		}
		/*
		 * if (Sensors.getArmMinLimitSwitch().get()) {
		 * Actuators.getArmAngleMotor().set(0.35); } else {
		 * Actuators.getArmAngleMotor().set(0); }
		 */
		if (!atPosition) {
			if (!Actuators.getDriveShiftPneumatic().get()) {
				Actuators.getDriveShiftPneumatic().set(true);
			}
			System.out.println("Stage 1");
			Drive.driveWithPID(finishDistance, finishDistance);
			if(Math.abs(Actuators.getLeftDriveMotor().getError()) < allotedError 
					&& Math.abs(Actuators.getRightDriveMotor().getError()) < allotedError){
				atPosition = true;
				Actuators.getLeftDriveMotor().setPosition(0);
				Actuators.getRightDriveMotor().setPosition(0);
				Drive.drive(0);
			}
		}else{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AutoTarget.centerTarget();
		}
//		if (Actuators.getRightDriveMotor().isAlive() && !Actuators.getLeftDriveMotor().isAlive())
//			Actuators.getLeftDriveMotor().setEncPosition(Actuators.getRightDriveMotor().getEncPosition());
//		if (!Actuators.getRightDriveMotor().isAlive() && Actuators.getLeftDriveMotor().isAlive())
//			Actuators.getRightDriveMotor().setEncPosition(Actuators.getLeftDriveMotor().getEncPosition());
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
		timer.cancel();
		Drive.drive(0);
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}
