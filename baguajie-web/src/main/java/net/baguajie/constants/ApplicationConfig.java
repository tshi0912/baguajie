package net.baguajie.constants;

public class ApplicationConfig {

	public static String base;
	public static String uploadTempRepository = 
			"resources/spot";
	public static String uploadTempRefer = 
			"/resources/spot";
	public static String imageRefer = 
			"/images/spots";
	public static String avatarRefer = 
			"/images/avatars";
	public static String staticRefer = 
			"/resources";
	public static int pinImageWidth = 192;
	public static String httpProxyHost = "127.0.0.1";
	public static int httpProxyPort = 80;
	public static String defaultCityPinyin = "quanguo";
	public static String defaultBy = "web";
	public static int masonryPageSize = 15;
	public static int mapPageSize = 15;
	public static int listPageSize = 15;
	public static int pinCmtPageSize = 6;
	public static int masonryThumbPageSize = 9;
	public static int categoryThumbPageSize = 12;
	public static String[] defaultCategories = new String[]{
			"时事热点","本地新闻","美食风暴","娱乐花边","购物时尚"};
	public static String defaultPreferenceId = "-1";
}
