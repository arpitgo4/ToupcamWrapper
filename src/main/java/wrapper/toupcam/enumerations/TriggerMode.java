package wrapper.toupcam.enumerations;

public enum TriggerMode {

	TOUPCAM_FLAG_TRIGGER_SOFTWARE(0x00080000),	  	/* support software trigger */
	TOUPCAM_FLAG_TRIGGER_EXTERNAL(0x00100000),  	/* support external trigger */
	TOUPCAM_FLAG_TRIGGER_SINGLE(0x00200000); 		/* only support trigger single: one trigger, one image */
	
	private int value;
	
	TriggerMode(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	public static TriggerMode key(long mode){
		for(TriggerMode v : values()){
			if(v.value == mode)
				return v;
		}
		return null;
	}
	
}
