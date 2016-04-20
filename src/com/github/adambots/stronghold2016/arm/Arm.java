package com.github.adambots.stronghold2016.arm;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Gamepad;
import org.usfirst.frc.team245.robot.Sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * All arm code
 *
 */
public class Arm {
	static double MAX_ARM_POSITION = 5;
	static double MIN_ARM_POSITION = 0;

	/**
	 * Initializes arm
	 */
	public static void init() {
		Actuators.getWinchRatchetPneumatic().set(false);
	}

	/**
	 * Runs intake
	 * 
	 * @param intake
	 * @param putout
	 */
	public static void rollers(boolean intake, boolean putout) {
		if (intake == putout) {
			Actuators.getBoulderIntakeMotor().set(Actuators.STOP_MOTOR);
		} else if (intake == true) {
			Actuators.getBoulderIntakeMotor().set(Actuators.MAX_MOTOR_SPEED);
		} else if (putout == true) {
			Actuators.getBoulderIntakeMotor().set(Actuators.MIN_MOTOR_SPEED);
		}

	}

	/**
	 * moves arm within range
	 * 
	 * @param speed
	 */
	public static void moveArm(double speed) {
		SmartDashboard.putNumber("Arm Position", Actuators.getArmAngleMotor().getPosition());
		if (Gamepad.secondary.getBack()) {
			Actuators.getArmAngleMotor().set(speed);
		} else {
			if ((Actuators.getArmAngleMotor().getPosition() < MAX_ARM_POSITION && Sensors.getArmMinLimitSwitch().get())
					&& speed > 0) {
				Actuators.getArmAngleMotor().set(speed / 2);
			} else if ((Actuators.getArmAngleMotor().getPosition() > MIN_ARM_POSITION
					&& Sensors.getArmMaxLimitSwitch().get()) && speed < 0) {
				Actuators.getArmAngleMotor().set(speed);
			} else {
				Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);

			}
			// speed = -speed;
			SmartDashboard.putNumber("Arm Position", Actuators.getArmAngleMotor().getPosition());
			if (Gamepad.secondary.getBack()) {
				Actuators.getArmAngleMotor().set(speed);
			} else {
				if ((Sensors.getArmMaxLimitSwitch().get()) && speed < 0) {
					Actuators.getArmAngleMotor().set(speed);
				} else if ((Sensors.getArmMinLimitSwitch().get()) && speed > 0) {
					Actuators.getArmAngleMotor().set(speed);
				} else {
					Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);

				}
				// if (!(/*
				// * Actuators.getArmAngleMotor().getPosition() >
				// * MAX_ARM_POSITION &&
				// */
				// Sensors.getArmMaxLimitSwitch().get()) && speed > 0) {
				// Actuators.getArmAngleMotor().set(speed);
				// } else if (!(/*
				// * Actuators.getArmAngleMotor().getPosition() <
				// * MIN_ARM_POSITION &&
				// */
				// Sensors.getArmMinLimitSwitch().get()) && speed < 0) {
				// Actuators.getArmAngleMotor().set(speed);
				// } else {
				// Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);
				//
				// }
			}
		}
	}

	/**
	 * Runs winch to climb
	 * 
	 * @param button
	 */
	public static void climb(boolean button) {
		if (!Actuators.getWinchRatchetPneumatic().get() && button) {
			Actuators.getWinchRatchetPneumatic().set(true);
			Actuators.getArmWinchMotor1().set(Actuators.MAX_MOTOR_SPEED);
			Actuators.getArmWinchMotor2().set(-Actuators.MAX_MOTOR_SPEED);
		} else if (Actuators.getWinchRatchetPneumatic().get() && button) {
			Actuators.getArmWinchMotor1().set(Actuators.MAX_MOTOR_SPEED);
			Actuators.getArmWinchMotor2().set(-Actuators.MAX_MOTOR_SPEED);
		} else {
			Actuators.getArmWinchMotor1().set(Actuators.STOP_MOTOR);
			Actuators.getArmWinchMotor2().set(Actuators.STOP_MOTOR);
			Actuators.getWinchRatchetPneumatic().set(false);
		}
	}

	public static void release() {
		Actuators.getWinchRatchetPneumatic().set(!Actuators.getWinchRatchetPneumatic().get());
	}

	public static void climb(double speed) {
		// if (!Actuators.getWinchRatchetPneumatic().get() && speed > 0) {
		// Actuators.getWinchRatchetPneumatic().set(true);
		// Actuators.getArmWinchMotor1().set(-Actuators.MAX_MOTOR_SPEED);
		// Actuators.getArmWinchMotor2().set(-Actuators.MAX_MOTOR_SPEED);
		if (speed > 0) {
			Actuators.getArmWinchMotor1().set(speed);
			Actuators.getArmWinchMotor2().set(-speed);
			// }else if (!Actuators.getWinchRatchetPneumatic().get() && speed <
			// 0) {
			// Actuators.getWinchRatchetPneumatic().set(true);
			// Actuators.getArmWinchMotor1().set(-Actuators.MIN_MOTOR_SPEED);
			// Actuators.getArmWinchMotor2().set(-Actuators.MIN_MOTOR_SPEED);
		} else if (speed < 0) {
			Actuators.getArmWinchMotor1().set(speed);
			Actuators.getArmWinchMotor2().set(-speed);
		} else {
			Actuators.getArmWinchMotor1().set(Actuators.STOP_MOTOR);
			Actuators.getArmWinchMotor2().set(Actuators.STOP_MOTOR);
			// Actuators.getWinchRatchetPneumatic().set(false);
		}
	}

}
