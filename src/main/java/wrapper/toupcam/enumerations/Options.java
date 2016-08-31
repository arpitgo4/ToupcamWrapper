package wrapper.toupcam.enumerations;

public enum Options
{
    OPTION_NOFRAME_TIMEOUT(0x01),    /* iValue: 1 = enable; 0 = disable. default: enable */
    OPTION_THREAD_PRIORITY(0x02),    /* set the priority of the internal thread which grab data from the usb device. iValue: 0 = THREAD_PRIORITY_NORMAL; 1 = THREAD_PRIORITY_ABOVE_NORMAL; 2 = THREAD_PRIORITY_HIGHEST; default: 0; see: msdn SetThreadPriority */
    OPTION_PROCESSMODE    (0x03),    /* 0 = better image quality, more cpu usage. this is the default value
                                         1 = lower image quality, less cpu usage */
    OPTION_RAW            (0x04),    /* raw mode, read the sensor data. This can be set only BEFORE Toupcam_StartXXX() */
    OPTION_HISTOGRAM      (0x05),    /* 0 = only one, 1 = continue mode */
    OPTION_BITDEPTH       (0x06),    /* 0 = 8bits mode, 1 = 16bits mode */
    OPTION_FAN            (0x07),    /* 0 = turn off the cooling fan, 1 = turn on the cooling fan */
    OPTION_COOLER         (0x08),    /* 0 = turn off cooler, 1 = turn on cooler */
    OPTION_LINEAR         (0x09),    /* 0 = turn off tone linear, 1 = turn on tone linear */
    OPTION_CURVE          (0x0a),    /* 0 = turn off tone curve, 1 = turn on tone curve */
    OPTION_TRIGGER        (0x0b),    /* 0 = continuous mode, 1 = trigger mode, default value = 0 */
    OPTION_RGB48          (0x0c);     /* enable RGB48 format when bitdepth > 8 */
    
    private int value;
    
    Options(int value){
    	this.value = value;
    }
    
    public int getValue(){
    	return value;
    }
    
    public static Options key(long option){
		for(Options v : values()){
			if(v.value == option)
				return v;
		}
		return null;
	}
};