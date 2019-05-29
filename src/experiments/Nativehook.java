package experiments;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import q.BulpControl;

public class Nativehook {
	BulpControl bulpControl;
	


	public Nativehook(BulpControl bulpControl) {
		this.bulpControl = bulpControl;
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
						
		}
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
		GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
			
			@Override
			public void nativeKeyTyped(NativeKeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void nativeKeyReleased(NativeKeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void nativeKeyPressed(NativeKeyEvent arg0) {
				String keyText = NativeKeyEvent.getKeyText(arg0.getKeyCode());
				System.out.println (keyText);
				if (keyText.equals("Play")) {
					bulpControl.setLightOnOff();
				}
				if (keyText.equals("Previous")) {
					bulpControl.changeLightMode();
				}
				
			}
		});
	}


  }
