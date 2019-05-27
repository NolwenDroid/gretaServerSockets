package q;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class Greta {
	static BulpControl bulpControl;
	public Greta() {
	new Thread (new Runnable() {
	
			@Override
			public void run() {
				startListen();		
			}
		}).start();
	
	new Thread (new Runnable() {
		
		@Override
		public void run() {
			bulpControl	= new BulpControl();
			bulpControl.start();
			
			
			
		}
	}).start();
}
	
	private static void startListen() {
		ServerSocket socket = null;
		
		   try {
	        	socket = new ServerSocket(773);
				Socket clientSocket = socket.accept();
				System.out.println("accept");
				InputStream inputStream = clientSocket.getInputStream();
				byte[] bytes = IOUtils.toByteArray(inputStream);
				String converted = new String(bytes, StandardCharsets.UTF_8);
				System.out.println(converted);
				converted =converted.toLowerCase();
				if (converted.contains("свет")) {
					if (converted.contains("включ") && (converted.contains("выключ"))){
						bulpControl.setLightOnOff();
					} else {
						if (converted.contains("включ")){
							bulpControl.setLightOn();
						} else if (converted.contains("выключ")) {
							bulpControl.setLightOff();
						}
					}
				}
				
			} catch (IOException e) {
				System.out.println("error");
				e.printStackTrace();
			}
	     	try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	     	startListen();
		
	}

}
