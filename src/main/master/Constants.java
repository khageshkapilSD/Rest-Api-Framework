package main.master;

public class Constants {

	public static final String API_RELATIVE_PATH = "api/config";
	
	public static final String GLOBAL_CONFIG = "global.properties";
	
	public static final String PATH_TO_MODELS = "api/model";
	
	public String getPath() {
		return this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	}
}
