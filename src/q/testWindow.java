package q;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

import experiments.Nativehook;
import experiments.usb4java;
import experiments.usbhid;

public class testWindow {

	private JFrame frame;
	protected Greta greta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testWindow window = new testWindow();
					window.frame.setVisible(true);
					window.greta = new Greta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//usb4java usbforj = new usb4java();
		//usbhid hid= new usbhid();
	
		
	}

	public testWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
