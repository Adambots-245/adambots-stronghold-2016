package com.github.adambots.stronghold2016.auton;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Sensors;

import com.github.adambots.stronghold2016.drive.Drive;
import com.github.adambots.stronghold2016.shooter.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonShootSpyBox extends Command {
	static double finishDistance = -24, allotedError = 60;
	static boolean reset = false, isDone;
	boolean shouldShoot = true;
	public AutonShootSpyBox() {
		// TODO Auto-generated constructor stub
	}

	public AutonShootSpyBox(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public AutonShootSpyBox(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public AutonShootSpyBox(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if(!Actuators.getDriveShiftPneumatic().get()){
			Actuators.getDriveShiftPneumatic().set(true);
		}
		if(!Sensors.getArmMinLimitSwitch().get()){
			Actuators.getArmAngleMotor().set(-0.35);
		}else{
			Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);
		}
		
		if(Sensors.getArmMaxLimitSwitch().get()){
			Actuators.getArmAngleMotor().set(Actuators.STOP_MOTOR);
		}
		
		Drive.driveWithPID(finishDistance, finishDistance);
		Actuators.getLeftDriveMotor().setEncPosition(Actuators.getRightDriveMotor().getEncPosition());
		SmartDashboard.putNumber("LEFT_ERROR", Actuators.getLeftDriveMotor().getError());
		SmartDashboard.putNumber("RIGHT_ERROR", Actuators.getRightDriveMotor().getError());
		if(Actuators.getLeftDriveMotor().getError() < allotedError && Actuators.getRightDriveMotor().getError() < allotedError){
			Shooter.shoot(shouldShoot);
			shouldShoot = false;
		}

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
