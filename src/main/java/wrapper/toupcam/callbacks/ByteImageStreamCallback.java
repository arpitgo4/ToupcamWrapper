package wrapper.toupcam.callbacks;

import java.awt.image.BufferedImage;

import wrapper.toupcam.models.ImageHeader;

public interface ByteImageStreamCallback extends ImageStreamCallback {

	@Override
	default void onReceivePreviewImage(BufferedImage image, ImageHeader imageHeader) {}

	@Override
	default void onReceiveStillImage(BufferedImage image, ImageHeader imageHeader) {}

	
}
