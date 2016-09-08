package wrapper.toupcam.callbacks;

import java.awt.image.BufferedImage;

import wrapper.toupcam.models.ImageHeader;

public interface ImageStreamCallback {

	void onReceivePreviewImage(BufferedImage image, ImageHeader imageHeader);
	
	void onReceiveStillImage(BufferedImage image, ImageHeader imageHeader);
	
	void onReceivePreviewImage(byte[] imageBytes, ImageHeader imageHeader);
	
	void onReceiveStillImage(byte[] imageBytes, ImageHeader imageHeader);
	
}
