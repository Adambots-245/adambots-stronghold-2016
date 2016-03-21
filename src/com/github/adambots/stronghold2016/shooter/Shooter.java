package com.github.adambots.stronghold2016.shooter;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Sensors;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;

/**
 * All shooter logic
 *
 */
public class Shooter {
	private static final double LOAD_POSITION = 0.0;
	// TODO: get PID values
	private static final double Kp = 1;
	private static final double Ki = 1;
	private static final double Kd = 1;
	private static PIDController armPID;

	/**
	 * Initializes PID for arm and checks if shooter is cocked
	 */
	public static void init() {
	//	armPID = new PIDController(Kp, Ki, Kd, Sensors.getArmPot(), Actuators.getArmAngleMotor());
	}

	/**
	 * Stops the loading the shooter
	 */
	public static void stopLoadShooter() {
		Actuators.getCatapultMotor().set(Actuators.STOP_MOTOR);
//		Actuators.getArmAngleMotor().disable();
	}

	/**
	 * Loads shooter using CANTalon PID
	 * 
	 * @return boolean - is shooter loaded
	 */
	public static boolean loadShooterWithoutPIDs() {
		boolean isShooterLoaded = false;
		double loadSpeed = 1;
		if (Sensors.getIntakeArmPhotoEye().get()) {
			Actuators.getArmAngleMotor().changeControlMode(TalonControlMode.Position);
			Actuators.getArmAngleMotor().set(LOAD_POSITION);
			Actuators.getArmAngleMotor().enable();
		}

		if (Actuators.getArmAngleMotor().getError() == 0) {
			Actuators.getBoulderIntakeMotor().set(loadSpeed);
		}

		if (Sensors.getBoulderCanLaunchPhotoEye().get()) {
			Actuators.getArmAngleMotor().changeControlMode(TalonControlMode.PercentVbus);
			Actuators.getBoulderIntakeMotor().set(Actuators.STOP_MOTOR);
			isShooterLoaded = true;
		}
		return isShooterLoaded;
	}

	/**
	 * Loads shooter using PID on RoboRio
	 * 
	 * @return boolean - is shooter loaded
	 */
	public static boolean loadShooterWithPIDs() {
		boolean isShooterLoaded = false;
		double loadSpeed = 1;
		// TODO: Calibrate Tolerance
		final double TOLERANCE = 0;
		if (Sensors.getIntakeArmPhotoEye().get()) {
			// If we can't set position using string pot
			armPID.setAbsoluteTolerance(TOLERANCE);
			armPID.setSetpoint(LOAD_POSITION);
			armPID.enable();
		}

		if (armPID.onTarget()) {
			Actuators.getBoulderIntakeMotor().set(loadSpeed);
		}

		if (Sensors.getBoulderCanLaunchPhotoEye().get()) {
			armPID.disable();
			Actuators.getBoulderIntakeMotor().set(Actuators.STOP_MOTOR);
			isShooterLoaded = true;
		}
		return isShooterLoaded;
	}

	/**
	 * Shoots ball, and loads shooter if it is not loaded
	 * 
	 * @return boolean - did robot shoot
	 */
	public static boolean shoot(boolean shouldShoot) {
		double speed = -Actuators.MAX_MOTOR_SPEED;
		boolean isLoaded = !Sensors.getCatapultLimitSwitch().get();
		if (shouldShoot && isLoaded) {
			Actuators.getCatapultMotor().set(speed);
		} else if (!isLoaded) {
			Actuators.getCatapultMotor().set(speed);
		} else if (isLoaded) {
			Actuators.getCatapultMotor().set(Actuators.STOP_MOTOR);
		}
		return isLoaded;
	}
}
