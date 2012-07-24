package net.baguajie.web.utils;

import org.springframework.stereotype.Component;

import net.baguajie.constants.ApplicationConstants;

@Component
public class MapUtil {
	
	public int getMetricByZoom(int zoom){
		switch(zoom){
			case 0 :
			case 1 :
			case 2 :
			case 3 :
			case 4 :
			case 5 : {
				return ApplicationConstants.DISTANCE_NATION;
			}
			case 6 : 
			case 7 :
			case 8 :{
				return ApplicationConstants.DISTANCE_PROVINCE;
			}
			case 9 :
			case 10 :
			case 11 :
			case 12 :{
				return ApplicationConstants.DISTANCE_CITY;
			}
			case 13 :
			case 14 :{
				return ApplicationConstants.DISTANCE_DISTRICT;
			}
			case 15 :
			case 16 :
			case 17 :{
				return ApplicationConstants.DISTANCE_STREET;
			}
			default :
				return ApplicationConstants.DISTANCE_CITY;
		}
	}
}
