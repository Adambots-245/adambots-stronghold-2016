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
	private static final double TARGET_CENTER_Y = 61;
	private static final double MAX_CENTER_Y = 240;
	private static final double THRESHOLD_ERROR_X = 20;
	private static final double THRESHOLD_ERROR_Y = 40;
	private static final double DEFAULT_VALUE = -1;
	
	private static double centerX;
	private static double centerY;
	private static double height;
	private static double width;
	
	private static int iterator = 0; 
	
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
		iterator++;
		getTargetData();
		boolean isAtTarget = false;
		double errorX = centerX - TARGET_CENTER_X;
		double errorY = TARGET_CENTER_Y - centerY;
		boolean isXAligned = Math.abs(errorX) <= THRESHOLD_ERROR_X; 
		System.out.println("IS X ALIGEDD" + isXAligned);
		
//		return isAtTarget;
			
		if(isXAligned){
			Drive.drive(Actuators.STOP_MOTOR);
			isAtTarget = true;
		}else if(centerX == -1 && centerY == -1){
			double turningSpeed = 0.4;
			Drive.drive(0, turningSpeed);
		}else if(iterator != 10){
			iterator = 0;
			double turningSpeed = (errorX > 0)? 0.3:-0.3;
			Drive.drive(0, turningSpeed);
			
		}
		return isAtTarget;
//		Drive.drive(Actuators.STOP_MOTOR);
	}
	
	public static boolean centerTargetA(){
		getTargetData();
		if(centerX != -1 && centerY != -1){
			boolean isAtTarget = false;
			double errorX = centerX - TARGET_CENTER_X;
			double errorY = TARGET_CENTER_Y - centerY;
			
			boolean isYAligned = Math.abs(errorY) <= THRESHOLD_ERROR_Y;
			System.out.println("IS Y ALIGNED" + isYAligned);
			boolean isXAligned = Math.abs(errorX) <= THRESHOLD_ERROR_X; 
			System.out.println("IS X ALIGEDD" + isXAligned);
			if(isYAligned && isXAligned){
				Drive.drive(Actuators.STOP_MOTOR);
				isAtTarget = true;
			}else if(isXAligned){
				double speed = (errorY > 0)? 0.3:-0.3;
				Drive.drive(speed);
			}else{
				double turningSpeed = (errorX > 0)? 0.3:-0.3;
				Drive.drive(0, turningSpeed);
				
			}
			return isAtTarget;
		}
		Drive.drive(Actuators.STOP_MOTOR);
		return false;
		
	}
	
		
}
