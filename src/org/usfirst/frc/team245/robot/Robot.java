
package org.usfirst.frc.team245.robot;

import org.opencv.core.Core;

import com.github.adambots.stronghold2016.arm.Arm;
import com.github.adambots.stronghold2016.auton.*;
import com.github.adambots.stronghold2016.camera.AutoTarget;
import com.github.adambots.stronghold2016.camera.TargetingMain;
import com.github.adambots.stronghold2016.dash.DashCamera;
import com.github.adambots.stronghold2016.dash.DashStringPotentiometer;
//import com.github.adambots.stronghold2016.camera.AutoTarget;
//import com.github.adambots.stronghold2016.camera.Target;
import com.github.adambots.stronghold2016.drive.Drive;
import com.github.adambots.stronghold2016.shooter.Shooter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	static{
		System.load("/usr/local/share/OpenCV/java/libopencv_java310.so");
	}
	Command autonomousCommand;
	SendableChooser chooser;
	Compressor compressor;
	Command autonomousBarrier;
	SendableChooser barrierChooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		try{
		Actuators.init();
		chooser = new SendableChooser();
		compressor = new Compressor();
		
		Sensors.init();
		Shooter.init();

		Drive.init();// does not have anything
		
		AutoTarget.init();

		
		Actuators.getRingLight().set(true);
		
		
		System.out.println("Finished HW Init");
		
		chooser.addObject("None", new DoNothing());
		chooser.addDefault("Forward Over Defense", new ForwardOverChassis());
		chooser.addObject("Forward Shooting", new ForwardShoot());
		chooser.addObject("Forward To Ramp", new ForwardToRamp());
		chooser.addObject("Spy Shoot", new AutonShootSpyBox());
		chooser.addObject("Smart Shoot", new SmartShoot());
		chooser.addObject("Delay Forward" + DelayForward.delay+ " Seconds", new DelayForward());
		chooser.addObject("Cheval", new Barrier_ChevalDeFrise());
		
		SmartDashboard.putData("Auto mode", chooser);
		
		System.out.println("Finished Sendable Chooser Init");
		
		}catch (Exception e){
			System.out.println("Something went wrong in init");
		}
		System.out.println("Finished ALL of Init");

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
//		Actuators.getRingLight().set(false);
		TargetingMain.running = false;
	}

	public void disabledPeriodic() {
		LiveWindow.run();
		SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
		SmartDashboard.putBoolean("Catapult limit switch", !Sensors.getCatapultLimitSwitch().get());
		SmartDashboard.putNumber("Left Encoder", Actuators.getLeftDriveMotor().getEncPosition());
		SmartDashboard.putNumber("Right Encoder", Actuators.getRightDriveMotor().getEncPosition());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings commands.
	 */
	public void autonomousInit() {
//		DashCamera.camerasClose();
		Actuators.getLeftDriveMotor().setEncPosition(0);
		Actuators.getRightDriveMotor().setEncPosition(0);
		autonomousCommand = (Command) chooser.getSelected();
		
		Actuators.teleopInit();
		
		DashCamera.camerasClose();
		TargetingMain.init();
		TargetingMain.running = true;
		

		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		SmartDashboard.getDouble("Average Drive Current", Drive.averageDriveCurrent());
//		AutoTarget.centerTarget();
		Scheduler.getInstance().run();

	}
	private boolean pastShift;
	//private boolean toggled;

	public void teleopInit() {

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		pastShift = false;
		
		TargetingMain.running = false;
		Arm.init();
		
//		TargetingMain.cameraThread.stop();
		
		while(TargetingMain.cameraThread.isAlive()){
			
		}
		DashCamera.camerasInit();
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
		SmartDashboard.putNumber("Average Drive Current", Drive.averageDriveCurrent());
		Actuators.getUnderGlow1().set(true);
		Actuators.getUnderGlow1().set(true);
		
		if(Gamepad.primary.getX()){
			AutoTarget.centerTarget();
		}else{
			if (Gamepad.primary.getY()) {
				Drive.drive(Gamepad.primary.getTriggers() / 2, Gamepad.primary.getLeftX() / 2);
			} else {
				Drive.drive(Gamepad.primary.getTriggers(), Gamepad.primary.getLeftX());
			}
		}
		
		
		
		DashStringPotentiometer.stringArmAngleMotorDash();
		
		// Drive.drive(Gamepad.primary.getTriggers(),
		// Gamepad.primary.getLeftX());

		
		if (Gamepad.primary.getB() && pastShift == false) {
			Drive.shift();
			pastShift = Gamepad.primary.getB();
		} else if (!Gamepad.primary.getB()) {
			pastShift = Gamepad.primary.getB();
		}

		Arm.moveArm(Gamepad.secondary.getLeftY());
		SmartDashboard.putBoolean("MAX ARM LIMIT", Sensors.getArmMaxLimitSwitch().get());
		SmartDashboard.putBoolean("MIN ARM LIMIT", Sensors.getArmMinLimitSwitch().get());

		SmartDashboard.putData("Max Limit Switch", Sensors.getArmMaxLimitSwitch());
		SmartDashboard.putData("Min Limit Switch", Sensors.getArmMinLimitSwitch());
		SmartDashboard.putNumber("Left Encoder", Actuators.getLeftDriveMotor().getEncPosition());
		SmartDashboard.putNumber("Right Encoder", Actuators.getRightDriveMotor().getEncPosition());

		//CAMERA
		DashCamera.cameras(Gamepad.secondary.getX());

		// TODO: Check joystick mapping
		// Scheduler.getInstance().run();
		// TODO: TEST ARM CODE
		//
		Arm.rollers(Gamepad.secondary.getA(), Gamepad.secondary.getB());
		//
//		if (Gamepad.secondary.getRB() && toggled == false) {
//			Arm.release();

//			toggled = Gamepad.secondary.getRB();
//		} else if (!Gamepad.secondary.getRB()) {
//			toggled = Gamepad.secondary.getRB();
//		}

		if (Gamepad.secondary.getY()) {
			 Arm.climb(Gamepad.secondary.getY());
		} else {
			 Arm.climb(Gamepad.secondary.getRightY());
		}

		// TEST CODE
		// *****************************************************************

		// ***************************************************************************
		if (Gamepad.primary.getBack()) {
			Shooter.stopLoadShooter();
		}
		Shooter.shoot(Gamepad.primary.getA());
		if (Gamepad.primary.getBack()) {
			Shooter.stopLoadShooter();
		}

		SmartDashboard.putBoolean("Catapult limit switch", Sensors.getCatapultLimitSwitch().get());
		SmartDashboard.putBoolean("Gear: ", Actuators.getDriveShiftPneumatic().get());
		String gear;
		if (Actuators.getDriveShiftPneumatic().get()) {
			gear = "High";
		} else {
			gear = "Low";
		}
		SmartDashboard.putString("Gear: ", gear);
	}

	@Override
	public void testInit() {
		// TODO Auto-generated method stub
		super.testInit();
		DashCamera.camerasClose();
		TargetingMain.init();
		TargetingMain.running = true;
	}
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		Actuators.getRingLight().set(true);
		LiveWindow.run();
		if(Gamepad.primary.getStart()){
			AutoTarget.centerTarget();
		}
		SmartDashboard.putBoolean("Pressure Switch", compressor.getPressureSwitchValue());
	}
	
}
