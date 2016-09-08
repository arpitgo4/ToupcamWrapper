package wrapper.toupcam.callbacks;

import java.awt.image.BufferedImage;

import com.sun.jna.Callback;

import wrapper.toupcam.models.ImageHeader;

public interface ImageStreamCallback extends Callback {

	void onReceivePreviewImage(BufferedImage image, ImageHeader imageHeader);
	
	void onReceiveStillImage(BufferedImage image, ImageHeader imageHeader);
	
}
