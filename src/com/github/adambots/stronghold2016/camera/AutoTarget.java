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
	private static final double THRESHOLD_ERROR_X = 5;
	private static final double THRESHOLD_ERROR_Y = 5;
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
			boolean isAtTarget = false;
			double errorX = centerX - TARGET_CENTER_X;
			double errorY = TARGET_CENTER_Y - centerY;
			
			boolean isYAligned = Math.abs(errorY) >= THRESHOLD_ERROR_Y;
			boolean isXAligned = Math.abs(errorX) >= THRESHOLD_ERROR_X; 
			if(isYAligned && isXAligned){
				Drive.drive(Actuators.STOP_MOTOR);
			}else if(isYAligned){
				int turningSpeed = (errorX > 0)? 1:-1;
				Drive.drive(0, turningSpeed);
			}else{
				int speed = (errorY > 0)? 1:-1;
				Drive.drive(speed);
			}
			return isAtTarget;
		}
		Drive.drive(Actuators.STOP_MOTOR);
		return false;
		
	}
	
		
}
