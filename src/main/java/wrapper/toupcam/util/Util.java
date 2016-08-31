package wrapper.toupcam.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

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
	
	public static void convertImagePointerToImage(Pointer imagePointer, int width, int height){
		byte[] imageBytes = imagePointer.getByteArray(0, width * height);
		InputStream in = new ByteArrayInputStream(imageBytes);
		BufferedImage bImageFromConvert;
		try {
			bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "jpg", new File(
					Constants.PATH + "/image.jpg"));
		} catch (Exception e) {
			System.out.println("Exception thrown during convertion : " + e);
		}
	}
	
}
