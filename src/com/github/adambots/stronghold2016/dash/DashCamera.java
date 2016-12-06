package com.github.adambots.stronghold2016.dash;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.FlipAxis;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Line;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ROI;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class DashCamera {
	private static int currSession;
	private static int sessionfront;
	private static int sessionback;
	private static Image frame;

	public static void camerasInit() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		try {
			sessionfront = NIVision.IMAQdxOpenCamera("cam1",
					NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sessionback = NIVision.IMAQdxOpenCamera("cam0",
					NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		currSession = sessionfront;

		try {
			NIVision.IMAQdxConfigureGrab(currSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void camerasClose(){
		if(sessionfront != 0 && sessionback != 0){
			NIVision.IMAQdxCloseCamera(sessionfront);
			NIVision.IMAQdxCloseCamera(sessionback);
		}
		sessionfront = 0;
		sessionback = 0;
	}
	public static void cameras(boolean toggle) {
//		Point upLeft = new Point(10, 10);
//		Point upRight = new Point(100,10);
//		Point downLeft = new Point(10,50);
//		Point downRight = new Point(100,50);
//		Line top = new Line(upLeft, upRight);
//		Line right = new Line(upRight, downRight);
//		Line bottom = new Line(downRight, downLeft);
//		Line left = new Line(downLeft, upLeft);
		Rect rect = new Rect(170, 320, 190, 100);
		Rect rect2 = new Rect(176, 319, 192, 102);
		if (toggle) {
			if (currSession != 0 && currSession == sessionfront) {
				try {
					NIVision.IMAQdxStopAcquisition(currSession);
					currSession = sessionback;
					NIVision.IMAQdxConfigureGrab(currSession);
					rect.write();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else if (currSession != 0 && currSession == sessionback) {
				try {
					NIVision.IMAQdxStopAcquisition(currSession);
					currSession = sessionfront;
					NIVision.IMAQdxConfigureGrab(currSession);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			
//			
//			NIVision.imaqFlip(frame, frame, FlipAxis.HORIZONTAL_AXIS);
			NIVision.IMAQdxGrab(currSession, frame, 1);
			if(currSession == sessionfront){
//				NIVision.imaqFlip(frame, frame, FlipAxis.HORIZONTAL_AXIS);
//				NIVision.imaqFlip(frame, frame, FlipAxis.VERTICAL_AXIS);
//				NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 10);
//				NIVision.imaqDrawShapeOnImage(frame, frame, rect2, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 10);
			}
			if(currSession == sessionback){
				NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 10);
				NIVision.imaqDrawShapeOnImage(frame, frame, rect2, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 10);
				NIVision.imaqFlip(frame, frame, FlipAxis.HORIZONTAL_AXIS);
				NIVision.imaqFlip(frame, frame, FlipAxis.VERTICAL_AXIS);

			}
//			CameraServer.getInstance().setQuality(5);
			CameraServer.getInstance().setImage(frame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
