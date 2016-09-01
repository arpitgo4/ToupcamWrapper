package wrapper.toupcam.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.sun.jna.Pointer;

import wrapper.toupcam.models.ImageHeader;

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

	/**
	 * To parse the image header received from push image callback.
	 * @param imageHeaderPointer
	 * @return
	 */
	public static ImageHeader parseImageHeader(Pointer imageHeaderPointer){
		ImageHeader header = new ImageHeader();
		int offset = 0;
		header.setSize(imageHeaderPointer.getInt(offset));					// 0 (int)
		header.setWidth(imageHeaderPointer.getInt(offset += 4));			// 4 (int)
		header.setHeight(imageHeaderPointer.getInt(offset += 4));			// 8 (int)
		header.setPlanes(imageHeaderPointer.getShort(offset += 4));			// 12 (short)
		header.setBitcount(imageHeaderPointer.getShort(offset += 2));		// 14 (short)
		header.setCompression(imageHeaderPointer.getInt(offset += 2));		// 16 (int)
		header.setImageSize(imageHeaderPointer.getInt(offset += 4));		// 20 (int)
		header.setxPelsPerMeter(imageHeaderPointer.getInt(offset += 4));	// 24 (int)
		header.setyPelsPerMeter(imageHeaderPointer.getInt(offset += 4));	// 28 (int)
		header.setClrUsed(imageHeaderPointer.getInt(offset += 4));			// 32 (int)
		header.setClrImportant(imageHeaderPointer.getInt(offset += 4));		// 36 (int)	

		return header;
	}
	
}
