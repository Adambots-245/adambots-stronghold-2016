package com.github.adambots.stronghold2016.camera;

import java.util.Iterator;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Target {
	//CONSTANTS
	public static final String NETWORK_TABLE_NAME = "targetData";
	public static final String TABLE_CENTER_X = "centerX";
	public static final String TABLE_DISTANCE = "distance";
	public static final String TABLE_AREA = "area";
	public static final String TABLE_CENTER_Y = "centerY";
	public static final String TABLE_WIDTH = "width";
	public static final String TABLE_HEIGHT = "height";
	//INSTANCE VARIABLES
	private int centerX;
	private int centerY;
	private int area;
	private double distance;
	private int height;
	private int width;
	
	public Target(int centerX, int centerY, int area, double distance, int height, int width) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.area = area;
		this.distance = distance;
		this.height = height;
		this.width = width;
	}
	public void publishTarget(){
		NetworkTable table = NetworkTable.getTable(NETWORK_TABLE_NAME);
		table.putNumber(TABLE_CENTER_X, this.centerX);
		table.putNumber(TABLE_CENTER_Y, this.centerY);
		table.putNumber(TABLE_AREA, this.area);
		table.putNumber(TABLE_DISTANCE, this.distance);
		table.putNumber(TABLE_HEIGHT, this.height);
		table.putNumber(TABLE_WIDTH, this.width);
	}
	/**
	 * @return the centerX
	 */
	public double getCenterX() {
		return centerX;
	}

	/**
	 * @return the centerY
	 */
	public double getCenterY() {
		return centerY;
	}

	/**
	 * @return the area
	 */
	public double getArea() {
		return area;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
 
	
	
	
}
