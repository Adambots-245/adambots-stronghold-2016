package org.usfirst.frc.team245.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * All Robot sensors
 *
 */
public class Sensors {
	private static Encoder winchEncoder;
	private static DigitalInput intakeArmPhotoEye;
	private static DigitalInput boulderCanLaunchPhotoEye;
	private static DigitalInput catapultLimitSwitch;
	private static DigitalInput armMaxLimitSwitch;
	private static DigitalInput armMinLimitSwitch;
//	private static Solenoid ringLight;
	// Analog
	// private static AnalogPotentiometer armPot;
	private static AnalogGyro robotGyro;
	private static double stringPotArmDist;
	private static double stringPotChassisDist;

	/**
	 * Initializes all sensors
	 */
	public static void init() {
		// Digital
		winchEncoder = new Encoder(0, 1);
		try {
			intakeArmPhotoEye = new DigitalInput(5);
			boulderCanLaunchPhotoEye = new DigitalInput(6);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		catapultLimitSwitch = new DigitalInput(4);
//		ringLight = new Solenoid(3);
		// Analog
		// armPot = new AnalogPotentiometer(1, 360, 0);
		// robotGyro = new AnalogGyro(0);
		armMaxLimitSwitch = new DigitalInput(3);
		armMinLimitSwitch = new DigitalInput(2);
	}

	/**
	 * @return the armMaxLimitSwitch (invert when using .get())
	 */
	public static DigitalInput getArmMaxLimitSwitch() {
		return armMaxLimitSwitch;
	}

	/**
	 * @return the armMinLimitSwitch (invert when using .get())
	 */
	public static DigitalInput getArmMinLimitSwitch() {
		return armMinLimitSwitch;
	}

	/**
	 * @return the winchEncoder
	 */
	public static Encoder getWinchEncoder() {
		return winchEncoder;
	}

	/**
	 * @return the intakeArmPhotoEye
	 */
	public static DigitalInput getIntakeArmPhotoEye() {
		return intakeArmPhotoEye;
	}

	/**
	 * @return the boulderCanLaunchPhotoEye
	 */
	public static DigitalInput getBoulderCanLaunchPhotoEye() {
		return boulderCanLaunchPhotoEye;
	}

	/**
	 * @return the catapultLimitSwitch
	 */
	public static DigitalInput getCatapultLimitSwitch() {
		return catapultLimitSwitch;
	}

	/**
	 * @return the armPot
	 */
	// public static AnalogPotentiometer getArmPot() {
	// return armPot;
	// }

	/**
	 * @return the robotGyro
	 */
	public static AnalogGyro getRobotGyro() {
		return robotGyro;
	}

	/**
	 *
	 * @return the arm angle using the string pot
	 */
	// public static double getStringPotArmAngle() {
	// TODO: calibrate slope cm per degree
	// double slope = 0;
	// double displace = armPot.get() * slope;
	// return Math.acos((Math.pow(stringPotArmDist, 2) +
	// Math.pow(stringPotChassisDist, 2) - Math.pow(displace, 2))
	// / (2 * stringPotArmDist * stringPotChassisDist));
	// }

}
