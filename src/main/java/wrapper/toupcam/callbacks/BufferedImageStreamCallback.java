package wrapper.toupcam.callbacks;

import wrapper.toupcam.models.ImageHeader;

public interface BufferedImageStreamCallback extends ImageStreamCallback {

	@Override
	default void onReceivePreviewImage(byte[] imageBytes, ImageHeader imageHeader) {}

	@Override
	default void onReceiveStillImage(byte[] imageBytes, ImageHeader imageHeader) {}
	
}
