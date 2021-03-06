package q;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class BulpControl {
	Socket mSocket;
	private static final String off = "{\"id\":1,\"method\":\"set_power\",\"params\":[\"off\", \"smooth\", 500]}\r\n";
	private static final String on =   "{\"id\":2,\"method\":\"set_power\",\"params\":[\"on\", \"smooth\", 500]}\r\n";
	private static final String toggle =   "{\"id\":3,\"method\":\"toggle\",\"params\":[]}\r\n";
	 

	//Should be 21 params exactly
	private static final String prop = "{\"id\":4,\"method\":\"get_prop\",\"params\":[\"power\",\"bright\",\"ct\",\"rgb\",\"hue\",\"sat\",\"color_mode\",\"delayoff\",\"flow_params\",\"music_on\",\"name\",\"bg_power\",\"bg_flowing\",\"bg_ct\",\"bg_lmode\",\"bg_bright\",\"bg_rgb\",\"bg_hue\",\"bg_sat\",\"nl_br\",\"active_mode\"]}\r\n";
	private static final String resultResponse = "\"result\":[\"ok\"]";
	private static final String setNightMode =  "{\"id\":5,\"method\":\"set_power\",\"params\":[\"on\", \"smooth\", 500,5]}\r\n";
	private static final String setDayMode =  "{\"id\":6,\"method\":\"set_power\",\"params\":[\"on\", \"smooth\", 500, 1]}\r\n";;
	private static final String dropBrightness = "{\"id\":7,\"method\":\"set_bright\",\"params\":[1, \"smooth\", 200]}\r\n";
	private static final String maxBrightness = "{\"id\":8,\"method\":\"set_bright\",\"params\":[99, \"smooth\", 200]}\r\n";
	private static final String setBrightness = "{\"id\":9,\"method\":\"set_bright\",\"params\":[%value, \"smooth\", 200]}\r\n";
	private BufferedOutputStream mBos;
	private BulpParamsModel bulpParamsModel = new BulpParamsModel();

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				boolean cmd_run = true;
				try {
					mSocket = new Socket("192.168.10.101", 55443);
					mSocket.setKeepAlive(true);
					mBos = new BufferedOutputStream(mSocket.getOutputStream());
					getProps();
					BufferedReader mReader = null;
					mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
					while (cmd_run) {
						try {
							if (mReader != null) {
								String value = mReader.readLine();
								System.out.println(value);
								Gson gson = new Gson();
								BulpPropsResponse bulpPropsResponse = gson.fromJson(value, BulpPropsResponse.class);
								if (bulpPropsResponse!=null && bulpPropsResponse.id==4) {
									bulpParamsModel.update (bulpPropsResponse);
								}
								if (value.contains(resultResponse)) {
									getProps();
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		}).start();

	}

	private void write(String command) {

		if (mBos != null && mSocket!=null && mSocket.isConnected()) {

			System.out.println(command);
			try {
				byte [] bytes = command.getBytes();
				System.out.println (String.valueOf(bytes.length));
				mBos.write(command.getBytes(StandardCharsets.UTF_8));
				mBos.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println("socket error, is connected - " + String.valueOf(mSocket.isConnected()));
		}

	}

	public void setLightOn() {
		write(on);

	}

	public void setLightOff() {
		write(off);
	}

	public void getProps() {
		write(prop);
	}

	public boolean isOn() {
		if (bulpParamsModel !=null) {
			return bulpParamsModel.getPowerState();
		} else {
			return false;
		}
	}

	public void setLightOnOff() {
		write(toggle);
//		if (isOn()) {
//			setLightOff();
//		} else {
//			setLightOn();
//		}

	}
	public void setDayMode() {
		
	}
	public void setNightMode() {
		
	}
	public void changeLightMode() {
		if (bulpParamsModel !=null) {
			if (bulpParamsModel.isMoonLight()) {
				write(setDayMode);
				write (dropBrightness);
			} else {
				write(setNightMode);
				write (maxBrightness);
			}
		}
		
	}
	public void incrementBrightness(boolean isIncrement) {
		if (bulpParamsModel !=null) {
			int oldBrightness = 0;
			int newBrightness = 0;
			if (bulpParamsModel.isMoonLight()) {
				oldBrightness = bulpParamsModel.nightBright;
			} else {
				oldBrightness = bulpParamsModel.bright;
			}
			if (isIncrement) {
				newBrightness=oldBrightness+20;
			} else {
				newBrightness=oldBrightness -20;
			}
			if (newBrightness<1) {
				newBrightness =1;
			}
			if (newBrightness >99) {
				newBrightness = 99;
			}
			if (oldBrightness != newBrightness) {
				write (setBrightness.replace("%value",String.valueOf(newBrightness)));
				System.out.println("old " + String.valueOf(oldBrightness) + " newBrightness " + String.valueOf(newBrightness));	
			}
			
		}
	}

}
