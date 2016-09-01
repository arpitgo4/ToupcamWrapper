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
		for(int i = 0; i < 10; i++)
			System.out.print(pointer.getByte(i) + ", ");
		System.out.println();
	}
	private static int counter = 0;
	public static void convertImagePointerToImage(Pointer imagePointer, int width, int height){
		//byte[] imageBytes = imagePointer.getByteArray(0, width * height);
		/*for(byte b : imageBytes)
			System.out.print(b + ", ");
		System.out.println();*/
		
		BufferedImage newbImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		int[] ints = new int[height * width];
		for (int indexPix = 0; indexPix < height * width; indexPix ++)
		{
			
			byte tempByte = imagePointer.getByte(indexPix);
			if (indexPix<10){
				System.out.println(tempByte);
			}
			//int tempInt = (int) tempByte;
	        int rgb = (0x88 << 24) | (tempByte << 16) | (tempByte << 8) | tempByte ;
	        ints[indexPix] = rgb; 
		}
		newbImage.getRGB(0, 0, width, height, ints, 0, width);
		
		//InputStream in = new ByteArrayInputStream(imageBytes);
		BufferedImage bImageFromConvert;
		try {
			//bImageFromConvert = ImageIO.read(in);
			ImageIO.write(newbImage, "png", new File(
					Constants.PATH + "/image" + counter++ + ".png"));
		} catch (Exception e) {
			System.out.println("Exception thrown during convertion : " + e);
		}
	}
	
}
