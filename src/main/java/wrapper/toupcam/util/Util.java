package wrapper.toupcam.util;

import com.sun.jna.Pointer;

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
	
	public static void displayBytes(Pointer pointer){
		for(int i = 0; i < 100; i++)
			System.out.print(pointer.getByte(i));
	}
	
}
