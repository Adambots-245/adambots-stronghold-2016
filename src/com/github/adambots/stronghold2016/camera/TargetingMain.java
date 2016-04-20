package com.github.adambots.stronghold2016.camera;

public class TargetingMain extends Thread{
	public static boolean running = false;
	public static Thread cameraThread = new Thread( new TargetingMain());
	public static void init(){
		cameraThread.start();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("OPENCV CODE RUNNING");
//		if(Camera.videoCapture.isOpened()){
		Camera.init();
//		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Camera.openStream();
//		running = true;
		while(running){
			System.out.println("IS OPENED?:"+ Camera.videoCapture.isOpened());
			if(Camera.videoCapture.isOpened()){
				Target target = Camera.getTarget();
				if(target != null){
					System.out.println();
					System.out.println(target.getCenterX() + " "+ target.getCenterY());
					System.out.println(target.getHeight());
					System.out.println(target.getWidth());
					target.publishTarget();
				}
			}
		}
		
		
		Camera.closeStream();
	}
	
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		Camera.closeStream();
	}
	
	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();
		Camera.closeStream();
	}
	
	
}
