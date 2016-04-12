package com.github.adambots.stronghold2016.drive;

import org.usfirst.frc.team245.robot.Gamepad;
import org.usfirst.frc.team245.robot.Sensors;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team245.robot.Actuators;

/**
 * All robot drive code
 *
 */
public class Drive {
	private static double current;
	private static final double INCHES_PER_REV = 8.25 * Math.PI;
	private static double turnScale = 0.9;
	/**
	 * Initializes all drive (currently does nothing)
	 */
	public static void init(){
		
	}
	
	/**
	 * Drives robot with a linear and rotational component
	 * @param speed
	 * @param turningSpeed
	 */
	public static void drive(double speed, double turningSpeed) {
		
		Actuators.getLeftDriveMotor().changeControlMode(TalonControlMode.PercentVbus);
		Actuators.getRightDriveMotor().changeControlMode(TalonControlMode.PercentVbus);
		
		double leftSpeed = Math.min(Actuators.MAX_MOTOR_SPEED, speed - turnScale*turningSpeed);
		leftSpeed = Math.max(Actuators.MIN_MOTOR_SPEED, leftSpeed);
		double rightSpeed = Math.min(Actuators.MAX_MOTOR_SPEED, speed + turnScale*turningSpeed);
		rightSpeed = Math.max(Actuators.MIN_MOTOR_SPEED, rightSpeed);
		
		Actuators.getLeftDriveMotor().set(leftSpeed);
		Actuators.getRightDriveMotor().set(rightSpeed);
	}
	/**
	 * Drives robot with only linear component
	 * @param speed
	 */
	public static void drive(double speed) {
		Actuators.getLeftDriveMotor().changeControlMode(TalonControlMode.PercentVbus);
		Actuators.getRightDriveMotor().changeControlMode(TalonControlMode.PercentVbus);
		
		double leftSpeed = Math.min(Actuators.MAX_MOTOR_SPEED, speed);
		leftSpeed = Math.max(Actuators.MIN_MOTOR_SPEED, leftSpeed);
		double rightSpeed = Math.min(Actuators.MAX_MOTOR_SPEED, speed);
		rightSpeed = Math.max(Actuators.MIN_MOTOR_SPEED, rightSpeed);
		
		Actuators.getLeftDriveMotor().set(leftSpeed);
		Actuators.getRightDriveMotor().set(rightSpeed);
	}
	
	
	/**
	 * 
	 * @param leftDistance - The distance the left side should travel in inches
	 * @param rightDistance - The distance the right side should travel in inches
	 */
	public static void driveWithPID(double leftDistance, double rightDistance){
		leftDistance /= INCHES_PER_REV;
		rightDistance /= INCHES_PER_REV;
		
		Actuators.getLeftDriveMotor().changeControlMode(TalonControlMode.Position);
		Actuators.getLeftDriveMotor().set(-leftDistance);
		Actuators.getLeftDriveMotor().enable();
		
		Actuators.getRightDriveMotor().changeControlMode(TalonControlMode.Position);
		Actuators.getRightDriveMotor().set(rightDistance);
		Actuators.getRightDriveMotor().enable();
		SmartDashboard.putNumber("LEFT_ERROR", Actuators.getLeftDriveMotor().getError());
		SmartDashboard.putNumber("Right_ERROR", Actuators.getRightDriveMotor().getError());
	}
	
	
	public static void shift(){
		
		if(Actuators.getDriveShiftPneumatic().get()){
			Actuators.getDriveShiftPneumatic().set(false);
		}else{
			Actuators.getDriveShiftPneumatic().set(true);
		}
		
	}
	public static double averageDriveCurrent()	{
		current = (Actuators.getLeftDriveMotor().getOutputCurrent() + Actuators.getLeftDriveMotor2().getOutputCurrent() +Actuators.getRightDriveMotor().getOutputCurrent() + Actuators.getRightDriveMotor2().getOutputCurrent())/4;
		return current;
	}
}

