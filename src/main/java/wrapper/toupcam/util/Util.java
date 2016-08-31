package wrapper.toupcam.util;

public class Util {

	/**
	 * to keep the JVM running, for receiving callbacks
	 * from toupcam.
	 */
	public static void keepVMRunning(){
		new Thread(new Runnable(){
			@Override public void run(){
				while(true){}
			}
		}).start();
	}
	
	
}
