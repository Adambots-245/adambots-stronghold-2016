package com.github.adambots.stronghold2016.camera;


import org.usfirst.frc.team245.robot.Actuators;

import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * All Auto-targeting code
 *
 */
public class AutoTarget {
	
	private static final double TARGET_CENTER_X = 244;
	private static final double MAX_CENTER_X = 320;
	private static final double TARGET_CENTER_Y = 101;
	private static final double MAX_CENTER_Y = 240;
	private static final double THRESHOLD_ERROR = 10;
	private static final double DEFAULT_VALUE = -1;
	
	private static double centerX;
	private static double centerY;
	private static double height;
	private static double width;
	
	
	private static NetworkTable targetTable;
	/**
	 * Initializes all AutoTarget instance variables
	 */
	public static void init(){
		targetTable = NetworkTable.getTable("/"+Target.NETWORK_TABLE_NAME);
	}
	
	/**
	 * Retrieves target data from network table
	 */
	private static void getTargetData(){
		centerX = targetTable.getNumber(Target.TABLE_CENTER_X, DEFAULT_VALUE);
		centerY = targetTable.getNumber(Target.TABLE_CENTER_Y, DEFAULT_VALUE);
		height = targetTable.getNumber(Target.TABLE_HEIGHT, DEFAULT_VALUE);
		width = targetTable.getNumber(Target.TABLE_WIDTH, DEFAULT_VALUE);
	}
	
	/**
	 * Centers target based on distance and rotation, not on translational-z
	 * @return whether robot is centered or not
	 */
	public static boolean centerTarget(){
		getTargetData();
		if(centerX != -1 && centerY != -1){
			double currentX = centerX;
			double errorX = TARGET_CENTER_X - currentX;
			errorX /= MAX_CENTER_X;
			System.out.println("errorX: " + errorX);
			double kPX = 1;
			boolean isAtTargetX = THRESHOLD_ERROR >= errorX;

			double currentY = centerY;
			double errorY = -TARGET_CENTER_Y + currentY;
			errorY /= MAX_CENTER_Y;
			
			System.out.println("errorY: " + errorY);
			double kPY = 1;
			boolean isAtTargetY = THRESHOLD_ERROR >= errorY;

			boolean isAtTarget = isAtTargetX && isAtTargetY;

			if(isAtTarget){
				Drive.drive(Actuators.STOP_MOTOR);
			}else if(isAtTargetX){
				double speed = kPY*errorY;
				speed = Math.max(0.2, speed);
				Drive.drive(speed);
			}else if(isAtTargetY){
				double speed = kPX*errorX;
				speed = Math.max(0.2, speed);
				Drive.drive(0, speed);
			}else{
				//TODO: Test if you can adjust both x & y
				//				double speedX = kPX*errorX*1/2;
				//				double speedY = kPY*errorY*1/2;
				//				Actuators.getRightDriveMotor().set(speedY-speedX);
				//				Actuators.getLeftDriveMotor().set(speedY+speedX);
				double speed = kPX*errorX;
				Drive.drive(0, speed);
			}
			
			return isAtTarget;
		}
		
		Drive.drive(Actuators.STOP_MOTOR);
		
		return false;
		
	}
	
		
}
