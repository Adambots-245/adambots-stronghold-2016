package com.github.adambots.stronghold2016.camera;

public class TargetingMain extends Thread{
	public static boolean running = false;
	public static void init(){
		(new Thread( new TargetingMain())).start();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Camera.init();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Camera.openStream();
		while(running){
			Target target = Camera.getTarget();
			if(target != null){
				System.out.println();
				System.out.println(target.getCenterX() + " "+ target.getCenterY());
				System.out.println(target.getHeight());
				System.out.println(target.getWidth());
				target.publishTarget();
			}
		}
		
		
		Camera.closeStream();
	}
	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();
		Camera.closeStream();
	}
	
	
	
}
