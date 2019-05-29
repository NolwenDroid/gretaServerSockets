package experiments;

import java.util.concurrent.TimeUnit;

import org.hid4java.HidDevice;
import org.hid4java.HidException;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.HidServicesSpecification;
import org.hid4java.ScanMode;
import org.hid4java.event.HidServicesEvent;

import q.BulpControl;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class usbhid implements HidServicesListener {
	HidDevice hidDevice;
	BulpControl bulpControl;
	
    public usbhid(BulpControl bulpControl) {
		this.bulpControl = bulpControl;
	}
	public void executeExample() throws HidException {
    	//Vendor 2412, Product 2800
        // Configure to use custom specification
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
      
        // Get HID services using custom specification
        HidServices hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);

        // Start the services
        System.out.println("Starting HID services.");
        hidServices.start();

        System.out.println("Enumerating attached devices...");

        // Provide a list of attached devices
        for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
            System.out.println(hidDevice);
            System.out.println(hidDevice.getProductId());
            if (hidDevice.isVidPidSerial(0x9da, 0xfffff624, null)) {
            	this.hidDevice = hidDevice;
            	System.out.println("hidDevice find ");
            }
        }

        // Open the device device by Vendor ID and Product ID with wildcard serial number
        //hidDevice = hidServices.getHidDevice(0x2412, 0x2800, null);
        hidDevice.open();
        
        if (hidDevice != null) {
            byte[] arr = new byte[256];
            int read = hidDevice.read(arr);
            System.out.println("from keyboard " + read);
        } else {
        	System.out.println("hidDevice null ");
        }
  

        // Shut down and rely on auto-shutdown hook to clear HidApi resources
        hidServices.shutdown();

    }
	@Override
	public void hidDeviceAttached(HidServicesEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hidDeviceDetached(HidServicesEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hidFailure(HidServicesEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
}
