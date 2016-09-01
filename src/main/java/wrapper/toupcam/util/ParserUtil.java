package wrapper.toupcam.util;

import com.sun.jna.Pointer;

import wrapper.toupcam.models.ImageHeader;

public class ParserUtil {

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
