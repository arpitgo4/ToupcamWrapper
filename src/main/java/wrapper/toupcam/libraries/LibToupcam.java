package wrapper.toupcam.libraries;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

import wrapper.toupcam.callbacks.PTOUPCAM_EVENT_CALLBACK;
import wrapper.toupcam.enumerations.Options;

public interface LibToupcam extends Library {
	
	int Toupcam_Enum(Pointer pointer);
	
	Pointer Toupcam_Open(String id);
	
	int Toupcam_StartPullModeWithCallback(Pointer handler, PTOUPCAM_EVENT_CALLBACK callback, int other);
	
	int Toupcam_PullImage(Pointer handler, Pointer pImageData, int bits, long pnWidth, long pnHeight);
	
	int Toupcam_PullStillImage(Pointer handler, Pointer pImageData, int bits, long pnWidth, long pnHeight);
	
	int Toupcam_Snap(Pointer handler, int resolutionIndex);
	
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
