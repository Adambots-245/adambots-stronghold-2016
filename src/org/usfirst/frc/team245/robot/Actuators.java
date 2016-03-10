package org.usfirst.frc.team245.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
/**
 * All of our robot's actuators including motors and pneumatics
 */
public class Actuators {
	
	
	//CONSTANTS
	
	//The PID constants for the arm
	private static final double ARM_ANGLE_KP = 1;
	private static final double ARM_ANGLE_KI = 1;
	private static final double ARM_ANGLE_KD = 1;
	private static final int ARM_ACCEPTABLE_EROR = 0;
	private static final int ARM_POT_TURNS_PER_REV = 7;
	private static final boolean ARM_REVERSE_SENSOR = false;
	public static final double ARM_MAX_MOTOR_POSITION= 0.0;
	public static final double ARM_MIN_MOTOR_POSITION= 0.0;
	
	//CANTalon constants for motors
	private static final float NOMINAL_REVERSE_VOLTAGE = -0f;
	private static final float NOMINAL_FORWARD_VOLTAGE = +0f;
	private static final int DRIVE_MOTOR_ACCEPTABLE_ERROR = 0;
	private static final float PEAK_REVERSE_VOLTAGE = -12f;
	private static final float PEAK_FORWARD_VOLTAGE = +12f;
	private static final int CODES_PER_REV = (int) (630);//620
	
	//The PID constants for right drive motors
	private static final double RIGHT_DRIVE_KP = 1;
	private static final double RIGHT_DRIVE_KI = 0;
	private static final double RIGHT_DRIVE_KD = 0.025;
	private static final boolean RIGHT_DRIVE_REVERSE_SENSOR = true; //ADAMBOTS ONLY
	
	
	//The PID constants for left drive motors
	private static final double LEFT_DRIVE_KP = 1;
	private static final double LEFT_DRIVE_KI = 0;
	private static final double LEFT_DRIVE_KD = 0.025;
	private static final boolean LEFT_DRIVE_REVERSE_SENSOR = false;
	
	//Maximum and minimum motor speed constants and constant to stop motor
	public static final double MAX_MOTOR_SPEED = 1;
	public static final double MIN_MOTOR_SPEED = -1;
	public static final double STOP_MOTOR = 0;
	
	// Motors
	private static CANTalon rightDriveMotor;
	private static CANTalon rightDriveMotor2;

	private static CANTalon leftDriveMotor;
	private static CANTalon leftDriveMotor2;

	private static VictorSP armWinchMotor1;
	private static VictorSP armWinchMotor2;

	private static CANTalon armAngleMotor;

	private static VictorSP boulderIntakeMotor;

	private static VictorSP catapultMotor;

	// Pneumatics
	private static Solenoid driveShiftPneumatic;

	private static Solenoid winchRatchetPneumatic;
	
	private static Solenoid ringLight;

	/**
	 * Initializes all actuators
	 */
	public static void init() {
		// Motors
		rightDriveMotor = new CANTalon(2);
		rightDriveMotor = initCANTalon(rightDriveMotor, FeedbackDevice.QuadEncoder, RIGHT_DRIVE_REVERSE_SENSOR, 
				CODES_PER_REV, DRIVE_MOTOR_ACCEPTABLE_ERROR, RIGHT_DRIVE_KP, RIGHT_DRIVE_KI, RIGHT_DRIVE_KD);
		rightDriveMotor.setInverted(true);
		rightDriveMotor2 = new CANTalon(3);
		rightDriveMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightDriveMotor2.setInverted(true);
		rightDriveMotor2.set(rightDriveMotor.getDeviceID());

		leftDriveMotor = new CANTalon(0);
		leftDriveMotor = initCANTalon(leftDriveMotor, FeedbackDevice.QuadEncoder, LEFT_DRIVE_REVERSE_SENSOR, 
				CODES_PER_REV, DRIVE_MOTOR_ACCEPTABLE_ERROR, LEFT_DRIVE_KP, LEFT_DRIVE_KI, LEFT_DRIVE_KD);
//		leftDriveMotor.setInverted(true);
		leftDriveMotor2 = new CANTalon(1);
		leftDriveMotor2.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftDriveMotor2.setInverted(true);
		leftDriveMotor2.set(leftDriveMotor.getDeviceID());

		armWinchMotor1 = new VictorSP(1);
		armWinchMotor2 = new VictorSP(0);
		armWinchMotor2.setInverted(true);

		armAngleMotor = new CANTalon(4);
		armAngleMotor = initCANTalon(armAngleMotor, FeedbackDevice.AnalogPot, ARM_REVERSE_SENSOR, 
				ARM_POT_TURNS_PER_REV, ARM_ACCEPTABLE_EROR, ARM_ANGLE_KP, ARM_ANGLE_KI, ARM_ANGLE_KD);
		//TODO: Use string pot with CANTalon

		boulderIntakeMotor = new VictorSP(2);

		catapultMotor = new VictorSP(3);

		// Solenoids
		driveShiftPneumatic = new Solenoid(0);
		winchRatchetPneumatic = new Solenoid(1);
		ringLight = new Solenoid(3);
	}
	
	/**
	 * All initialization for teleop
	 */
	public static void teleopInit(){
		
		rightDriveMotor = initCANTalon(rightDriveMotor, FeedbackDevice.QuadEncoder, RIGHT_DRIVE_REVERSE_SENSOR, 
				CODES_PER_REV, DRIVE_MOTOR_ACCEPTABLE_ERROR, RIGHT_DRIVE_KP, RIGHT_DRIVE_KI, RIGHT_DRIVE_KD);
		
		leftDriveMotor = initCANTalon(leftDriveMotor, FeedbackDevice.QuadEncoder, LEFT_DRIVE_REVERSE_SENSOR, 
				CODES_PER_REV, DRIVE_MOTOR_ACCEPTABLE_ERROR, LEFT_DRIVE_KP, LEFT_DRIVE_KI, LEFT_DRIVE_KD);
		
		armAngleMotor = initCANTalon(armAngleMotor, FeedbackDevice.AnalogPot, ARM_REVERSE_SENSOR, 
				ARM_POT_TURNS_PER_REV, ARM_ACCEPTABLE_EROR, ARM_ANGLE_KP, ARM_ANGLE_KI, ARM_ANGLE_KD);
	}

	/**
	 * @return the rightDrive
	 */
	public static CANTalon getRightDriveMotor() {
		return rightDriveMotor;
	}

	//needed for Dashboard
	 public static CANTalon getRightDriveMotor2() {
	 return rightDriveMotor2;
	 }

	/**
	 * @return the leftDrive
	 */
	public static CANTalon getLeftDriveMotor() {
		return leftDriveMotor;
	}

	
	//needed for Dashboard
	 public static CANTalon getLeftDriveMotor2() {
	 return leftDriveMotor2;
	 }

	/**
	 * @return the armWinch1
	 */
	public static VictorSP getArmWinchMotor1() {
		return armWinchMotor1;
	}

	/**
	 * @return the armWinch2
	 */
	public static VictorSP getArmWinchMotor2() {
		return armWinchMotor2;
	}

	/**
	 * @return the armAngle
	 */
	public static CANTalon getArmAngleMotor() {
		return armAngleMotor;
	}

	/**
	 * @return the boulderIntake
	 */
	public static VictorSP getBoulderIntakeMotor() {
		return boulderIntakeMotor;
	}

	/**
	 * @return the catapultMotor
	 */
	public static VictorSP getCatapultMotor() {
		return catapultMotor;
	}

	public static Solenoid getDriveShiftPneumatic() {
		return driveShiftPneumatic;
	}

	public static Solenoid getWinchRatchetPneumatic() {
		return winchRatchetPneumatic;
	}
	
	/**
	 * @return the ringLight
	 */
	public static Solenoid getRingLight() {
		return ringLight;
	}

	private static CANTalon initCANTalon(CANTalon talon, FeedbackDevice device, boolean reverseSensor, int codesPerRev, int acceptableErr, 
			double rightDriveKp, double rightDriveKi, double rightDriveKd){
		//talon.reset();
		talon.enableZeroSensorPositionOnIndex(true, true);
		talon.setPosition(0);
		talon.setFeedbackDevice(device);
		talon.reverseSensor(reverseSensor);
		
		if(FeedbackDevice.QuadEncoder.equals(device))
			talon.configEncoderCodesPerRev(codesPerRev);
		else if(FeedbackDevice.AnalogPot.equals(device))
			talon.configPotentiometerTurns(codesPerRev);
		
		talon.configNominalOutputVoltage(NOMINAL_FORWARD_VOLTAGE, NOMINAL_REVERSE_VOLTAGE);
		talon.configPeakOutputVoltage(PEAK_FORWARD_VOLTAGE, PEAK_REVERSE_VOLTAGE);
		
		talon.setAllowableClosedLoopErr(acceptableErr);
		talon.setPID(rightDriveKp, rightDriveKi, rightDriveKd);
		talon.changeControlMode(TalonControlMode.PercentVbus);
		return talon;
	}
	
}
