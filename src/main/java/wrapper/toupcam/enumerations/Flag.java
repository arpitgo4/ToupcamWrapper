package wrapper.toupcam.enumerations;

public enum Flag {

	FLAG_CMOS(0x00000001),       		//cmos sensor
	FLAG_CCD_PROGRESSIVE(0x00000002),   // progressive ccd sensor
	FLAG_CCD_INTERLACED(0x00000004),    //interlaced ccd sensor
	FLAG_ROI_HARDWARE(0x00000008),    	// support hardware ROI
	FLAG_MONO(0x00000010),     			// monochromatic
	FLAG_BINSKIP_SUPPORTED(0x00000020), // support bin/skip mode
	FLAG_USB30(0x00000040),    			// USB 3.0
	FLAG_COOLED(0x00000080),    		// Cooled
	FLAG_USB30_OVER_USB20(0x00000100),  // usb3.0 camera connected to usb2.0 port
	FLAG_ST4(0x00000200),    			// ST4
	FLAG_GETTEMPERATURE(0x00000400),    // support to get the temperature of sensor
	FLAG_PUTTEMPERATURE(0x00000800),    // support to put the temperature of sensor
	FLAG_BITDEPTH10(0x00001000),    	// Maximum Bit Depth(10
	FLAG_BITDEPTH12(0x00002000),    	// Maximum Bit Depth(12
	FLAG_BITDEPTH14(0x00004000),    	// Maximum Bit Depth(14
	FLAG_BITDEPTH16(0x00008000),    	// Maximum Bit Depth(16
	FLAG_FAN(0x00010000),    			// cooling fan
	FLAG_COOLERONOFF(0x00020000),    	// cooler can be turn on or off
	FLAG_ISP(0x00040000),    			// image signal processing supported
	FLAG_TRIGGER(0x00080000);   		//  support the trigger mode

	private int value;
	
	Flag(int value){
		this.value = value;
	}
	
	public static Flag key(long flag){
		for(Flag v : values()){
			if(v.value == flag)
				return v;
		}
		return null;
	}
	
}