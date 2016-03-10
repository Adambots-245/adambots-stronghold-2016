package com.github.adambots.stronghold2016.camera;


import org.usfirst.frc.team245.robot.Actuators;

import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * All Auto-targeting code
 *
 */
public class AutoTarget {
	
	private static final double TARGET_CENTER_X = 0.0;
	private static final double MAX_CENTER_X = 0.0;
	private static final double TARGET_CENTER_Y = 0.0;
	private static final double MAX_CENTER_Y = 0.0;
	private static final double THRESHOLD_RATIO = 0.0;
	private static final double THRESHOLD_ERROR = 0.0;
	private static final double[] DEFAULT_VALUE = new double[0];
	
	private static double[] centerX;
	private static double[] centerY;
	private static double[] height;
	private static double[] width;
	
	
	private static NetworkTable targetTable;
	/**
	 * Initializes all AutoTarget instance variables
	 */
	public static void init(){
		targetTable = NetworkTable.getTable("GRIP/"+Target.NETWORK_TABLE_NAME);
	}
	
	/**
	 * Retrieves target data from network table
	 */
	private static void getTargetData(){
		centerX = targetTable.getNumberArray(Target.TABLE_CENTER_X, DEFAULT_VALUE);
		centerY = targetTable.getNumberArray(Target.TABLE_CENTER_Y, DEFAULT_VALUE);
		height = targetTable.getNumberArray(Target.TABLE_HEIGHT, DEFAULT_VALUE);
		width = targetTable.getNumberArray(Target.TABLE_WIDTH, DEFAULT_VALUE);
	}
	
	/**
	 * Centers target based on distance and rotation, not on translational-z
	 * @return whether robot is centered or not
	 */
	public static boolean centerTarget(){
		double ratio = 0;
		ratio = height[0]/width[0];
		int indexOfBestTarget = 0;
		for(int i= 0; i<height.length && i<width.length; i++){
			if(height[i]/width[i]<=ratio){
				ratio = height[i]/width[i];
				indexOfBestTarget = i;
			}
		}
		if(!(THRESHOLD_RATIO < ratio)){
			double currentX = centerX[indexOfBestTarget];
			double errorX = TARGET_CENTER_X - currentX;
			errorX /= MAX_CENTER_X;
			double kPX = 1;
			boolean isAtTargetX = THRESHOLD_ERROR >= errorX;
			
			double currentY = centerY[indexOfBestTarget];
			double errorY = -TARGET_CENTER_Y + currentY;
			errorY /= MAX_CENTER_Y;
			double kPY = 1;
			boolean isAtTargetY = THRESHOLD_ERROR >= errorY;
			
			boolean isAtTarget = isAtTargetX && isAtTargetY;
			
			if(isAtTarget){
				Drive.drive(Actuators.STOP_MOTOR);
			}else if(isAtTargetX){
				double speed = kPY*errorY;
				Drive.drive(speed);
			}else if(isAtTargetY){
				double speed = kPX*errorX;
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
		return false;
	}
	
		
}
