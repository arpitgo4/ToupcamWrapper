package wrapper.toupcam.libraries;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

import wrapper.toupcam.callbacks.ImageStreamCallback;
import wrapper.toupcam.callbacks.PTOUPCAM_DATA_CALLBACK;
import wrapper.toupcam.callbacks.EventCallback;
import wrapper.toupcam.callbacks.PTOUPCAM_HOTPLUG_CALLBACK;

public interface LibToupcam extends Library {
	
	int Toupcam_Enum(Pointer pointer);
	
	Pointer Toupcam_Open(String id);
	
	int Toupcam_StartPullModeWithCallback(Pointer handler, EventCallback callback, int other);
	
	int Toupcam_PullImage(Pointer handler, Pointer pImageData, int bits, Pointer pnWidth, Pointer pnHeight);
	
	int Toupcam_PullStillImage(Pointer handler, Pointer pImageData, int bits, Pointer pnWidth, Pointer pnHeight);
	
	int Toupcam_Snap(Pointer handler, int resolutionIndex);
	
	int Toupcam_StartPushMode(Pointer handler, PTOUPCAM_DATA_CALLBACK callback, Pointer other);
	
	void Toupcam_HotPlug(PTOUPCAM_HOTPLUG_CALLBACK callback);
	
	int Toupcam_put_eSize(Pointer handler, int resolutionIndex);
	
	int Toupcam_get_RawFormat(Pointer handler, Pointer nFourCC, Pointer bitdepth);
	
	/**
	 * To set various options for the toupcam to work.
	 * Like set Raw format images, quality of received images.
	 * @param handler
	 * @param iOption
	 * @param iValue
	 * @return
	 */
	int Toupcam_put_Option(Pointer handler, int iOption, int iValue);
}
