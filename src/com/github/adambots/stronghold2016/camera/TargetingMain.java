package com.github.adambots.stronghold2016.camera;

public class TargetingMain extends Thread{
	public static void init(){
		(new Thread( new TargetingMain())).start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Camera.getTarget().publishTarget();
		}
	}
	
	
	
}
