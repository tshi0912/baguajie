package net.baguajie.constants;

public interface ApplicationConstants {

	static final String SESSION_SIGNIN_USER = "signInUser";
	static final String SESSION_SIGNIN_USER_PREFER = "signInUserPrefer";
	static final String SESSION_LAST_VISITED_URL = "lastVisitedUrl";
	static final String SESSION_SELECTED_CITY_META = "selectedCityMeta";
	static final String SESSION_BY = "by";
	static final String HEADER_CITY = "city";
	static final String HEADER_BY = "by";
	
	static final String NATION = "nation";
	static final String PROVINCE = "province";
	static final String CITY = "city";
	static final String DISTRICT = "district";
	static final String STREET = "street";
	static final String ZIP_CODE = "zipCode";
	static final String FULL_ADDR = "fullAddr";
	
	static final int DISTANCE_NATION = 4000;
	static final int DISTANCE_PROVINCE = 500;
	static final int DISTANCE_CITY = 100;
	static final int DISTANCE_DISTRICT = 10;
	static final int DISTANCE_STREET = 3;
	
	static final String SUCCESS = "s";
	
	static final int FOLLOWSHIP_NORMAL = 0;
	static final int FOLLOWSHIP_DISABLED = 1;
	static final int TRACKSHIP_NORMAL = 0;
	static final int TRACKSHIP_DISABLED = 1;
	static final int FORWARDSHIP_NORMAL = 0;
	static final int FORWARDSHIP_DISABLED = 1;
}
