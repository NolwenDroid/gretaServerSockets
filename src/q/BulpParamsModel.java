package q;

import java.util.ArrayList;

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
	
	public  boolean getPowerState() {
		return power;
	}
	public  boolean isMoonLight() {
		return isMoonLight;
	}


	public void update(BulpPropsResponse bulpPropsResponse) {
			if (bulpPropsResponse !=null && bulpPropsResponse.result!=null && bulpPropsResponse.result.size()==21) {
				ArrayList<String> params = bulpPropsResponse.result;
				String power = params.get(0);
				this.power = power.equals("on");
				String mode = params.get(20);
				isMoonLight = mode.equals("1");
				bright = Integer.valueOf(params.get(1));
				nightBright = Integer.valueOf(params.get(19));
			}

	}

	
	
}
