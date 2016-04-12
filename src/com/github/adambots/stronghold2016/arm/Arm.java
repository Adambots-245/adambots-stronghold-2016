package com.github.adambots.stronghold2016.arm;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Gamepad;
import org.usfirst.frc.team245.robot.Sensors;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * All arm code
 *
 */
public class Arm {
	static double MAX_ARM_POSITION = 5;
	static double MIN_ARM_POSITION = 0;
	private static double kP = 0.5;
	private static double kI = 0.02;
	private static double kD = 0.01;
	private static double maxPos = 3.337;
	private static double minPos = 2.659;
	public static double intakePosition = 0;
	public static double loadToCatapultPosition = 0.15;
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
		SmartDashboard.putNumber("Arm Max value",maxPos);
		SmartDashboard.putNumber("Arm Min value",minPos);
		Actuators.getArmAngleMotor().changeControlMode(TalonControlMode.PercentVbus);	//CHECK THIS SCOTT
		SmartDashboard.putNumber("Percent of Arm Angle", unMapPosition(Actuators.getArmAngleMotor().getPosition()));	//CHECK THIS SCOTT
		SmartDashboard.putNumber("Arm Position", Actuators.getArmAngleMotor().getPosition());
		SmartDashboard.putNumber("THeoreticalPID position", mapPosition(unMapPosition(Actuators.getArmAngleMotor().getPosition())));
		if (Gamepad.secondary.getBack()) {
			Actuators.getArmAngleMotor().set(speed);
		} else {
			if ((Actuators.getArmAngleMotor().getPosition() < MAX_ARM_POSITION && Sensors.getArmMinLimitSwitch().get())
					&& speed > 0) {
				Actuators.getArmAngleMotor().set(speed / 2);
			} else if ((Actuators.getArmAngleMotor().getPosition() > MIN_ARM_POSITION
					&& Sensors.getArmMaxLimitSwitch().get()) && speed < 0) {
				Actuators.getArmAngleMotor().set(speed);
			} else if (!Sensors.getArmMaxLimitSwitch().get()){
				Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);				//CHECK THIS SCOTT
				maxPos = Actuators.getArmAngleMotor().getPosition();		//CHECK THIS SCOTT
			} else if (!Sensors.getArmMinLimitSwitch().get()){						//CHECK THIS SCOTT
				Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);				//CHECK THIS SCOTT
				minPos = Actuators.getArmAngleMotor().getPosition();		//CHECK THIS SCOTT
			}
			else {
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
				} else if (!Sensors.getArmMaxLimitSwitch().get()){
					Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);				//CHECK THIS SCOTT
					maxPos = Actuators.getArmAngleMotor().getPosition();		//CHECK THIS SCOTT
				} else if (!Sensors.getArmMinLimitSwitch().get()){						//CHECK THIS SCOTT
					Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);				//CHECK THIS SCOTT
					minPos = Actuators.getArmAngleMotor().getPosition();		//CHECK THIS SCOTT
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
	public static void armPID(double position) {										//CHECK THIS SCOTT
		
		//if(Actuators.getArmAngleMotor().getControlMode() != TalonControlMode )
		if( Sensors.getArmMinLimitSwitch().get()) {
			/*Actuators.getArmAngleMotor().changeControlMode(TalonControlMode.Position);		//CHECK THIS SCOTT
			Actuators.getArmAngleMotor().setPID(kP, kI, kD);								//CHECK THIS SCOTT
			Actuators.getArmAngleMotor().set(mapPosition(position));	*/					//CHECK THIS SCOTT
			if(Actuators.getArmAngleMotor().getPosition()>mapPosition(position)) {
				Actuators.getArmAngleMotor().set(-.1);
			}
			else {
				Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);
			}
		}
		else {
			Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);
		}
		
	}
	
	private static double mapPosition(double position) { 									//CHECK THIS SCOTT
		double positionMap = (position*(maxPos - minPos) + minPos);					//CHECK THIS SCOTT
		return positionMap;																//CHECK THIS SCOTT
	}
	private static double unMapPosition(double position) {									//CHECK THIS SCOTT
		double unmapPosition = (double) (position-minPos)/(maxPos-minPos);						//CHECK THIS SCOTT
		return unmapPosition;															//CHECK THIS SCOTT
	}
}
