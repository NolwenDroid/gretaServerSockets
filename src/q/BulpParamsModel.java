package q;

public class 
	BulpParamsModel {
	boolean power;
	int bright;
	int colorTemperature;
	int rgb;//
	int hue;//
	int sat;//
	int colorMode;
	int delay;
	int flowParams;//
	boolean isMusicOn;//
	String name;//
	boolean isBackgroundOn;
	boolean isBackgroundFlowing;
	int backgroundColorTemperature;
	int backgroundMode;
	int backgroundBright;
	int backgroundColor;
	int backgroundHue;
	int backgroundSat;
	int nightBright;
	boolean isMoonLight;
	
	public static boolean getPowerState() {
		return false;
	}

	public void update(BulpPropsResponse bulpPropsResponse) {
		for (int i = 0; i < bulpPropsResponse.result.size(); i++) {
			
			
		}

	}

	
	
}
