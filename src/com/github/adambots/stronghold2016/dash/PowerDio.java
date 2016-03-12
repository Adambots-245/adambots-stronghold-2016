package com.github.adambots.stronghold2016.dash;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PowerDio {
	 static PowerDistributionPanel powerDP= new PowerDistributionPanel();
	 /**
	  * 
	  * @return an array of doubles of current being sent to each channel
	  */
public static double[] getAllPDPCurrent(){
	
	return  new double[] {powerDP.getCurrent(0),powerDP.getCurrent(1),powerDP.getCurrent(2),powerDP.getCurrent(3),powerDP.getCurrent(4),powerDP.getCurrent(5),powerDP.getCurrent(6),powerDP.getCurrent(7),powerDP.getCurrent(8),powerDP.getCurrent(9),powerDP.getCurrent(10),powerDP.getCurrent(11),powerDP.getCurrent(12),powerDP.getCurrent(13),powerDP.getCurrent(14),powerDP.getCurrent(15)
		};
	
}
/**
 * 
 * @param channel which channel you want to check
 * @return current of channel 
 */
public static double getPDPCurrent(int channel){
	
	return  powerDP.getCurrent(channel);
	
}
}
