package com.github.adambots.stronghold2016.camera;

import java.util.ArrayList;
import java.util.Iterator;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.*;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera {
	
	private static final int IMAGE_WRITE_ITERATIONS = 50;
	
	
	public static final int IMAGE_HEIGHT = 240;
	public static final int IMAGE_WIDTH = 320;
	private static final int PROP_IMAGE_HEIGHT = 4;
	private static final int PROP_IMAGE_WIDTH = 3;
	private static final int WEB_CAM = 0;
	//HSV Filter constants
	private static final int V_MAX = 255;
	private static final int V_MIN = 150;//122;
	private static final int S_MAX = 255;
	private static final int S_MIN = 20;//99;
	private static final int H_MAX = 45;///180 * 255;
	private static final int H_MIN = 10;///180 * 255;

	//Contour filtering constants
	private static final double MIN_ASPECT_RATIO = 0;
	private static final double MAX_ASPECT_RATIO = 0;
	private static final int MIN_CONTOUR_WIDTH = 40;
	private static final int MIN_CONTOUR_HEIGHT = 20;
	private static final int MAX_CONTOUR_WIDTH = 70;
	private static final int MAX_CONTOUR_HEIGHT = 120;
	private static final int MAX_AREA = -1;
	private static final int MIN_AREA = -1;


	private static final Scalar 
	//Color constants
	RED = new Scalar(0, 0, 255),
	BLUE = new Scalar(255, 0, 0),
	GREEN = new Scalar(0, 255, 0),
	BLACK = new Scalar(0,0,0),
	YELLOW = new Scalar(0, 255, 255),
	//Threshold Values in order
	SCALAR_LOWER_BOUNDS = new Scalar(H_MIN,S_MIN,V_MIN),
	SCALAR_UPPER_BOUNDS = new Scalar(H_MAX,S_MAX,V_MAX);

	//Different Matrices for Img Proc
	private static Mat matOriginal, matHSV, matThresh, matHeirarchy;

	//Camera stream
	public static VideoCapture videoCapture;

	//	the height to the top of the target in first stronghold is 97 inches	
	public static final int TOP_TARGET_HEIGHT = 97;
	//	the physical height of the camera lens
	//TODO: find TOP_CAMERA_HEIGHT
	public static final int TOP_CAMERA_HEIGHT = 0;
	//Camera specs
	//TODO: find camera specs
	public static final double VERTICAL_FOV  = 0;
	public static final double HORIZONTAL_FOV  = 0;
	public static final double CAMERA_ANGLE = 0;
	
	private static int iterator = 1;

	
	public static void init(){
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		matOriginal = new Mat();
		matHSV = new Mat();
		matThresh = new Mat();
		matHeirarchy = new Mat();
		//Opens Camera System
		videoCapture = new VideoCapture();
		openStream();
		SmartDashboard.putBoolean("CAPTURE IMAGE", false);
	}

	public static boolean openStream(){
//TODO:Testing from previous images		
		videoCapture.open(WEB_CAM);
		
//		videoCapture.open("/Users/robinonsay/Documents/Java-workspace/adambots-stronghold-2016/tesy.png");
		
		videoCapture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, IMAGE_WIDTH);
		videoCapture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, IMAGE_HEIGHT);
//		videoCapture.set(Videoio.CV_CAP_PROP_XI_MANUAL_WB , 900);
		videoCapture.set(Videoio.CAP_PROP_SATURATION, 10);
		videoCapture.set(Videoio.CAP_PROP_BRIGHTNESS, 0);
//		videoCapture.set(Videoio.CAP_PROP_EXPOSURE, 10);
		return videoCapture.isOpened();
	}

	public static boolean closeStream(){
		videoCapture.release();
		return !videoCapture.isOpened();
	}

	/**
	 * Main Method JUST FOR TESTING
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		System.out.println(openStream());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Target target = getTarget();
		System.out.println(target.getCenterX() + " "+ target.getCenterY());
		System.out.println(target.getHeight());
		System.out.println(target.getWidth());

	}

	public static Target getTarget() {
		MatOfPoint bestContour = getBestContour();
		if(bestContour != null){
			Rect rec = Imgproc.boundingRect(bestContour);
			Imgproc.rectangle(matOriginal, rec.br(), rec.tl(), RED);
			int centerX = rec.width/2 + rec.x;
			int centerY = rec.height/2 + rec.y;
			double y = rec.br().y + rec.height / 2;
			y= -((2 * (y / matOriginal.height())) - 1);
			double distance = (TOP_TARGET_HEIGHT - TOP_CAMERA_HEIGHT) / 
					Math.tan((y * VERTICAL_FOV / 2.0 + CAMERA_ANGLE) * Math.PI / 180);
			//TODO: TEST CODE---
			Point center = new Point(rec.br().x-rec.width / 2 - 15,rec.br().y - rec.height / 2);
			Point centerw = new Point(rec.br().x-rec.width / 2 - 15,rec.br().y - rec.height / 2 - 20);
			Imgproc.putText(matOriginal, centerX + " : "+ centerY, center, Core.FONT_HERSHEY_PLAIN, 1, RED);
			Imgproc.putText(matOriginal, "", centerw, Core.FONT_HERSHEY_PLAIN, 1, RED);
			
//			if(IMAGE_WRITE_ITERATIONS % iterator == 0){
//				Imgcodecs.imwrite("/home/lvuser/output.png", matOriginal);
//			}
			
			//---
			return new Target(centerX, centerY, rec.width * rec.height, distance, rec.height, rec.width);
		}
		
		return new Target(-1, -1, -1, -1, -1, -1);
		
	}
	
	
	public static ArrayList<MatOfPoint> getContours(){
		System.out.println("Starting Contours");
		// Grabs image from stream
//		Mat last = matOriginal;
//		for(int i = 0; i < 1000 && !matOriginal.empty(); i++){
//			last = matOriginal.clone();
//			videoCapture.read(matOriginal);
//		}
//		matOriginal = last;
		
		for(int i = 0; i < 30; i++){
			videoCapture.read(matOriginal);
		}
//		System.out.println("IS STREAM CLOSED?: "+closeStream());
//		videoCapture = null;
		
//		Core.flip(matOriginal, matOriginal, -1);
		//Converts image to HSV
		Imgproc.cvtColor(matOriginal,matHSV,Imgproc.COLOR_RGB2HSV);
		Core.inRange(matHSV, SCALAR_LOWER_BOUNDS, SCALAR_UPPER_BOUNDS, matThresh);
		//TODO:TEST CODE---
//		if(SmartDashboard.getBoolean("CAPTURE IMAGE")){
//			Imgcodecs.imwrite("/home/lvuser/original.png", matOriginal);
//			Imgcodecs.imwrite("/home/lvuser/thresh.png", matThresh);
//		}
		//---
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		contours.clear();

		//Finds contours stores in matHeiarchy
		Imgproc.findContours(matThresh, contours, matHeirarchy, Imgproc.RETR_EXTERNAL, 
				Imgproc.CHAIN_APPROX_SIMPLE);
		//		make sure the contours that are detected are at least 20x20 
		//		pixels with an area of 400 and an aspect ration greater then 1
		//TODO:TEST CODE---
		//---
		for (Iterator<MatOfPoint> iterator = contours.iterator(); iterator.hasNext();) {

			MatOfPoint contour = (MatOfPoint) iterator.next();
			Rect rec = Imgproc.boundingRect(contour);

			if(isNotTarget(rec)){
				iterator.remove();
				System.out.println("Remove");
				rec = null;
//				continue;
			}
			if(iterator.hasNext() && rec != null){
				System.out.println("width : height-> "+rec.width + " : "+rec.height + " "+ rec.x + " : " + rec.y);
			}
		}
		System.out.println("DONE WITH CONTOURS");
		iterator++;
		return contours;
	}
	
	private static boolean isNotTarget(Rect rec){
		boolean isTarget = rec.height < MIN_CONTOUR_HEIGHT || rec.width < MIN_CONTOUR_WIDTH;
		isTarget = isTarget || rec.height > MAX_CONTOUR_HEIGHT || rec.width > MAX_CONTOUR_WIDTH;
//		isTarget = is
		return isTarget;
	}
	
	public static MatOfPoint getBestContour() {

		ArrayList<MatOfPoint> contours = getContours();
		MatOfPoint bestContour = (!contours.isEmpty())?contours.remove(0):null;
		MatOfPoint lastContour = bestContour;

		for(MatOfPoint mop : contours){
			Rect rec = Imgproc.boundingRect(mop);
			Rect lastRec = Imgproc.boundingRect(lastContour);
			float thisAspect = (float)rec.width/(float)rec.height;
			float lastAspect = (float)lastRec.width/(float)lastRec.height;

			if(thisAspect >= lastAspect){
				bestContour = mop;
			}
			lastContour = mop;
		}
		return bestContour;

	}




}
