package wrapper.toupcam.enumerations;

public enum Event {
	
	EVENT_EXPOSURE(0x0001),		 	/* exposure time changed */
	EVENT_TEMPTINT(0x0002), 		/* white balance changed); Temp/Tint mode */
	EVENT_CHROME(0x0003), 			/* reversed); do not use it */
	EVENT_IMAGE(0x0004), 			/* live image arrived); use Toupcam_PullImage to get this image */
	EVENT_STILLIMAGE(0x0005), 		/* snap (still) frame arrived); use Toupcam_PullStillImage to get this frame */
	EVENT_WBGAIN(0x0006), 			/* white balance changed); RGB Gain mode */
	EVENT_ERROR(0x0080), 			/* something error happens */
	EVENT_DISCONNECTED(0x0081); 	 /* camera disconnected */

	private int value;
	
	Event(int value){
		this.value = value;
	}
	
	public static Event key(long event){
		for(Event v : values()){
			if(v.value == event)
				return v;
		}
		return null;
	}
}
