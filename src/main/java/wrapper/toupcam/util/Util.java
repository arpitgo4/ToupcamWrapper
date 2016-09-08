package wrapper.toupcam.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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

	public static BufferedImage convertImagePointerToImage(Pointer imagePointer, int width, int height){
		BufferedImage newbImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		int[] ints = convertImagePointerToIntArray(imagePointer, width, height);
		newbImage.setRGB(0, 0, width, height, ints, 0, width);
		return newbImage;
	}
	
	public static BufferedImage convertIntArrayToImage(int[] imageData, int width, int height){
		BufferedImage newbImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		newbImage.setRGB(0, 0, width, height, imageData, 0, width);
		return newbImage;
	}
	
	public static byte[] convertImagePointerToByteArray(Pointer imagePointer, int width, int height){
		int[] imageData = convertImagePointerToIntArray(imagePointer, width, height);
		ByteBuffer byteBuffer = ByteBuffer.allocate(imageData.length * 4);
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		intBuffer.put(imageData);
		return byteBuffer.array();
		/*int srcLength = imageData.length;
	    byte[]dst = new byte[srcLength << 2];
	    
	    for (int i=0; i<srcLength; i++) {
	        int x = imageData[i];
	        int j = i << 2;
	        dst[j++] = (byte) ((x >>> 0) & 0xff);           
	        dst[j++] = (byte) ((x >>> 8) & 0xff);
	        dst[j++] = (byte) ((x >>> 16) & 0xff);
	        dst[j++] = (byte) ((x >>> 24) & 0xff);
	    }
	    return dst;*/
	}
	
	private static int[] convertImagePointerToIntArray(Pointer imagePointer, int width, int height){
		int counter = 0 ;
		int[] ints = new int[height * width *  3];
		for (int indexPix = 0; indexPix < height * width *  3 ; )
		{
			int redVal = imagePointer.getByte(indexPix++);
			int greenVal = imagePointer.getByte(indexPix++);
			int blueVal = imagePointer.getByte(indexPix++);

			final int rgb = (0xff << 24) + (blueVal<< 16) + (greenVal << 8) + redVal;

			if (indexPix < height *  width *  3){ 
				ints[counter++] = rgb; }
		}
		return ints;
	}

	private static int imageCounter = 0;
	public static void writeImageToDisk(BufferedImage image){
		createImageDir();		// prefer displaying images on JFrame, in that case remove this line. 
		try {
			ImageIO.write(image, "png", new File(
					Constants.IMAGES_PATH + "/image" + imageCounter++ + ".png"));
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
