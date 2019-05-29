package experiments;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

public class usb4java {
	
	 public usb4java() {
		Context context = new Context();
	     int result = LibUsb.init(context);
	     if (result < 0)
	     {
	         throw new LibUsbException("Unable to initialize libusb", result);
	     }
	     DeviceList list = new DeviceList();
	     result = LibUsb.getDeviceList(context, list);
	     
	     if (result < 0)
	     {
	         throw new LibUsbException("Unable to get device list", result);
	     }
	     	
	    
	     try
	     {
	         // Iterate over all devices and list them
	         for (Device device: list)
	         {
	             int address = LibUsb.getDeviceAddress(device);
	             int busNumber = LibUsb.getBusNumber(device);
	             DeviceDescriptor descriptor = new DeviceDescriptor();
	             result = LibUsb.getDeviceDescriptor(device, descriptor);
	             if (result < 0)
	             {
	                 throw new LibUsbException(
	                     "Unable to read device descriptor", result);
	             }
	             System.out.format(
	                 "Bus %03d, Device %03d: Vendor %04x, Product %04x%n",
	                 busNumber, address, descriptor.idVendor(),
	                 descriptor.idProduct());
	                         }
	        
	     }
	     finally
	     {
	         // Ensure the allocated device list is freed
	         LibUsb.freeDeviceList(list, true);
	     }

	     // Deinitialize the libusb context
	     LibUsb.exit(context);
	}
	
}

