
package org.usfirst.frc.team245.robot;

import com.github.adambots.stronghold2016.arm.Arm;
import com.github.adambots.stronghold2016.auton.AutonMain;
import com.github.adambots.stronghold2016.auton.Barrier_ChevalDeFrise;
import com.github.adambots.stronghold2016.auton.Barrier_Drawbridge;
import com.github.adambots.stronghold2016.auton.Barrier_RoughTerrain;
import com.github.adambots.stronghold2016.auton.Default;
import com.github.adambots.stronghold2016.auton.FarLeft;
import com.github.adambots.stronghold2016.auton.FarRight;
import com.github.adambots.stronghold2016.auton.Forward;
import com.github.adambots.stronghold2016.auton.Left;
import com.github.adambots.stronghold2016.auton.Right;
import com.github.adambots.stronghold2016.auton.SuperRight;
import com.github.adambots.stronghold2016.dash.DashCamera;
import com.github.adambots.stronghold2016.dash.DashStringPotentiometer;
import com.github.adambots.stronghold2016.dash.PowerDio;
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
		Actuators.init();
		chooser = new SendableChooser();
		// barrierChooser = new SendableChooser();
		compressor = new Compressor();
		chooser.addDefault("None", new Default());
		chooser.addObject("left two positions", new FarLeft());
		chooser.addObject("left one positions", new Left());
		chooser.addObject("right one positions", new Right());
		chooser.addObject("right two positions", new FarRight());
		chooser.addObject("right three positions", new SuperRight());
		chooser.addObject("Forward", new Forward());
		// TODO: Uncomment inits
		Sensors.init();
		Shooter.init();

		Drive.init();// does not have anything
		// AutoTarget.init();//does not contain anything

		SmartDashboard.putData("Auto mode", chooser);

		/*
		 * barrierChooser.addDefault("ChevalDeFrise", new
		 * Barrier_ChevalDeFrise()); barrierChooser.addObject("Drawbridge", new
		 * Barrier_Drawbridge()); barrierChooser.addObject("RoughTerrain", new
		 * Barrier_RoughTerrain());
		 */
		// Barrier activeB = (Barrier) barrierChooser.getSelected();
		// SmartDashboard.putData("Barrier mode", barrierChooser);
		// SmartDashboard.putBoolean("barrier working", activeB.running());
		DashCamera.camerasInit();
		Actuators.getRingLight().set(true);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
//		Actuators.getRingLight().set(false);
	}

	public void disabledPeriodic() {
		LiveWindow.run();
		DashCamera.cameras(Gamepad.secondary.getX());
		SmartDashboard.putBoolean("Catapult limit switch", Sensors.getCatapultLimitSwitch().get());
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
		Actuators.getLeftDriveMotor().setEncPosition(0);
		Actuators.getRightDriveMotor().setEncPosition(0);
		// autonomousCommand = (Command) chooser.getSelected();
		Actuators.teleopInit();
		
		 String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 switch(autoSelected) {
		 	case "My Auto": 
		 		//autonomousCommand = new MyAutoCommand(); 
		 		break;
		 		case "Default Auto":
		 			
		 		default:
		 			//autonomousCommand = new ExampleCommand(); 
		 			break;
		}
		 

		// schedule the autonomous command (example)
		// if (autonomousCommand != null)
		// autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		Forward.go();
		// autonomousCommand.start();
		// AutonMain.test();

	}

	private boolean pastShift;
	private boolean toggled;

	public void teleopInit() {

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		//Arm.init();
		pastShift = false;
		
		// if (autonomousCommand != null)
		// autonomousCommand.cancel();
		Arm.init();
		// pastShift = false;

		// TODO:TEST CODE

		Actuators.teleopInit();

	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		DashStringPotentiometer.stringArmAngleMotorDash();
		if (Gamepad.primary.getY()) {
			Drive.drive(Gamepad.primary.getTriggers() / 2, Gamepad.primary.getLeftX() / 2);
		} else {
			Drive.drive(Gamepad.primary.getTriggers(), Gamepad.primary.getLeftX());
		}
		// Drive.drive(Gamepad.primary.getTriggers(),
		// Gamepad.primary.getLeftX());

		Drive.drive(Gamepad.primary.getTriggers(), Gamepad.primary.getLeftX());
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
		DashCamera.cameras(Gamepad.secondary.getX());

		// TODO: Check joystick mapping
		// Scheduler.getInstance().run();
		// TODO: TEST ARM CODE
		//
		Arm.rollers(Gamepad.secondary.getA(), Gamepad.secondary.getB());
		//
		if (Gamepad.secondary.getRB() && toggled == false) {
			Arm.release();

			toggled = Gamepad.secondary.getRB();
		} else if (!Gamepad.secondary.getRB()) {
			toggled = Gamepad.secondary.getRB();
		}

		if (Gamepad.secondary.getY()) {
			// Arm.climb(Gamepad.secondary.getY());
		} else {
			// Arm.climb(Gamepad.secondary.getRightY());
		}

		// TEST CODE
		// *****************************************************************

		// ***************************************************************************
		if (Gamepad.primary.getBack() && !Gamepad.primary.getA()) {
			Shooter.stopLoadShooter();
		}
		Shooter.shoot(Gamepad.primary.getA());
		if (Gamepad.primary.getBack() && !Gamepad.primary.getA()) {
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

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
		SmartDashboard.putDouble("PDP Channel 0:", PowerDio.getPDPCurrent(0));
		SmartDashboard.putDouble("PDP Channel 1:", PowerDio.getPDPCurrent(1));
		SmartDashboard.putDouble("PDP Channel 2:", PowerDio.getPDPCurrent(2));
		SmartDashboard.putDouble("PDP Channel 3:", PowerDio.getPDPCurrent(3));
		SmartDashboard.putDouble("PDP Channel 4:", PowerDio.getPDPCurrent(4));
		SmartDashboard.putDouble("PDP Channel 5:", PowerDio.getPDPCurrent(5));
		SmartDashboard.putDouble("PDP Channel 6:", PowerDio.getPDPCurrent(6));
		SmartDashboard.putDouble("PDP Channel 7:", PowerDio.getPDPCurrent(7));
		SmartDashboard.putDouble("PDP Channel 8:", PowerDio.getPDPCurrent(8));
		SmartDashboard.putDouble("PDP Channel 9:", PowerDio.getPDPCurrent(9));
		SmartDashboard.putDouble("PDP Channel 10:", PowerDio.getPDPCurrent(10));
		SmartDashboard.putDouble("PDP Channel 11:", PowerDio.getPDPCurrent(11));
		SmartDashboard.putDouble("PDP Channel 12:", PowerDio.getPDPCurrent(12));
		SmartDashboard.putDouble("PDP Channel 13:", PowerDio.getPDPCurrent(13));
		SmartDashboard.putDouble("PDP Channel 14:", PowerDio.getPDPCurrent(14));
		SmartDashboard.putDouble("PDP Channel 15:", PowerDio.getPDPCurrent(15));
		DashCamera.cameras(Gamepad.secondary.getX());
	}
}
