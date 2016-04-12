package com.github.adambots.stronghold2016.auton;

import org.usfirst.frc.team245.robot.Actuators;

import com.github.adambots.stronghold2016.drive.Drive;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonMain {

	private static final float NOMINAL_REVERSE_VOLTAGE = -0f;
	private static final float NOMINAL_FORWARD_VOLTAGE = +0f;
	private static final int ACCEPTABLE_ERROR = 0;
	private static final float PEAK_REVERSE_VOLTAGE = -12f;
	private static final float PEAK_FORWARD_VOLTAGE = +12f;
	private static final int CODES_PER_REV = 720;
	private static final double driveKP = 0.5;
	private static final double driveKI = 0.0;
	private static final double driveKD = 0.0;

	public static void init() {
		Actuators.getLeftDriveMotor().reset();
		Actuators.getLeftDriveMotor().enableZeroSensorPositionOnIndex(true, true);
		Actuators.getLeftDriveMotor().setPosition(0);
		Actuators.getLeftDriveMotor().setFeedbackDevice(FeedbackDevice.QuadEncoder);
		Actuators.getLeftDriveMotor().reverseSensor(true);

		Actuators.getLeftDriveMotor().configEncoderCodesPerRev(CODES_PER_REV);

		Actuators.getLeftDriveMotor().configNominalOutputVoltage(NOMINAL_FORWARD_VOLTAGE, NOMINAL_REVERSE_VOLTAGE);
		Actuators.getLeftDriveMotor().configPeakOutputVoltage(PEAK_FORWARD_VOLTAGE, PEAK_REVERSE_VOLTAGE);

		Actuators.getLeftDriveMotor().setAllowableClosedLoopErr(ACCEPTABLE_ERROR);
		Actuators.getLeftDriveMotor().setPID(driveKP, driveKI, driveKD);
		
		Actuators.getRightDriveMotor().reset();
		Actuators.getRightDriveMotor().enableZeroSensorPositionOnIndex(true, true);
		Actuators.getRightDriveMotor().setPosition(0);
		Actuators.getRightDriveMotor().setFeedbackDevice(FeedbackDevice.QuadEncoder);
		Actuators.getRightDriveMotor().reverseSensor(true);

		Actuators.getRightDriveMotor().configEncoderCodesPerRev(CODES_PER_REV);

		Actuators.getRightDriveMotor().configNominalOutputVoltage(NOMINAL_FORWARD_VOLTAGE, NOMINAL_REVERSE_VOLTAGE);
		Actuators.getRightDriveMotor().configPeakOutputVoltage(PEAK_FORWARD_VOLTAGE, PEAK_REVERSE_VOLTAGE);

		Actuators.getRightDriveMotor().setAllowableClosedLoopErr(ACCEPTABLE_ERROR);
		Actuators.getRightDriveMotor().setPID(driveKP, driveKI, driveKD);

	}

	/**
	 * 
	 * @param dBCI
	 *            array of chars from the drive station telling what category of
	 *            barrier is in what spot on the field this is for auton barrier
	 *            class
	 * @param dBCNI
	 *            tells which number of the category is in that position for
	 *            auton barrier class
	 * @param cPN
	 *            current position after the positioning code is done for auton
	 *            barrier class
	 */
	public static void go(char[] dBCI, int[] dBCNI, int cPN, int start, int end) {
		Auton_Barrier.autonBarrierGo(dBCI, dBCNI, cPN);
	}

	public static void test() {
		
		  SmartDashboard.putNumber("LEFT_ERROR",
		  Actuators.getLeftDriveMotor().getError());
		  SmartDashboard.putNumber("RIGHT_ERROR",
		  Actuators.getRightDriveMotor().getError());
		 /*Actuators.getLeftDriveMotor().changeControlMode(TalonControlMode.
		  Position); 
		 final int testTarget = 1;
		  Actuators.getLeftDriveMotor().set(-testTarget);
		  
		  
		  Actuators.getRightDriveMotor().changeControlMode(TalonControlMode.
		  Position); Actuators.getRightDriveMotor().set(testTarget);
		   */
//		 Left.go();
		 //Actuators.getLeftDriveMotor().enable();
		 //Actuators.getRightDriveMotor().enable();
		 
		 /* Actuators.getLeftDriveMotor().changeControlMode(TalonControlMode.PercentVbus);
		  Actuators.getRightDriveMotor().changeControlMode(TalonControlMode.PercentVbus);
		  Actuators.getRightDriveMotor().set(.5);
		  Actuators.getLeftDriveMotor().set(.5);
		  */
	}

}
