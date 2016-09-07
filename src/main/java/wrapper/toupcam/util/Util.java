package wrapper.toupcam.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.sun.jna.Native;
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

	public static BufferedImage convertImagePointerToImage(Pointer imagePointer, int width, int height){
		int counter = 0 ;
		BufferedImage newbImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		int[] ints = new int[height * width *  3 ];
		for (int indexPix = 0; indexPix < height * width *  3 ; )
		{
			int redVal = imagePointer.getByte(indexPix++);
			int greenVal = imagePointer.getByte(indexPix++);
			int blueVal = imagePointer.getByte(indexPix++);

			final int rgb = (0xff << 24) + (blueVal<< 16) + (greenVal << 8) + redVal;

			if (indexPix < height *  width *  3){ 
				ints[counter++] = rgb; }
		}
		newbImage.setRGB(0, 0, width, height, ints, 0, width);
		//writeImageToDisk(newbImage);
		
		return newbImage;
	}

	private static int counter = 0;
	private static void writeImageToDisk(BufferedImage image){
		createImageDir();		// prefer displaying images on JFrame, in that case remove this line. 
		try {
			ImageIO.write(image, "png", new File(
					Constants.IMAGES_PATH + "/image" + counter++ + ".png"));
		} catch (Exception e) {
			System.out.println("Exception thrown during convertion : " + e);
		}
	}

	private static void createImageDir() {
		File file = new File(Constants.IMAGES_PATH);
		if(!file.exists())
			file.mkdirs();
	}

}
