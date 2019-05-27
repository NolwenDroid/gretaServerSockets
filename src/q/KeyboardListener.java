package q;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardListener {

	public KeyboardListener(BulpControl bulpControl) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";

		   while (line.equalsIgnoreCase("quit") == false) {
		       try {
				line = in.readLine();
				in.close();
				System.out.println("l" +line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		       //do something
		   }

		   
	}
	
	
	
}
