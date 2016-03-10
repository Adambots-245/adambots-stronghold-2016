package com.github.adambots.stronghold2016.dash;

import org.usfirst.frc.team245.robot.Sensors;

public class SensorDio {
	public static boolean getLimitSwitchU(){
		return Sensors.getArmMaxLimitSwitch().get();
	}
	public static boolean getLimitSwitchL(){
		return Sensors.getArmMinLimitSwitch().get();
	}
	public static boolean getLimitSwitchC(){
		return Sensors.getCatapultLimitSwitch().get();
	}
	public static boolean getPhotoEyeB(){
		return Sensors.getBoulderCanLaunchPhotoEye().get();
	}
	public static boolean getPhotoEyeI(){
		return Sensors.getIntakeArmPhotoEye().get();
	}
	//public static double getArmPot(){
	//	return Sensors.getArmPot().get();
	//}
	public static double getRobotGyro(){
		return Sensors.getRobotGyro().getAngle();
	}
	
	public static double getWinchEncoder(){
		return Sensors.getWinchEncoder().get();
	}
	
	
	
	
	
}
