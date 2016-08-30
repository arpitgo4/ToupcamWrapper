package wrapper.toupcam.libraries;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

import wrapper.toupcam.callbacks.PTOUPCAM_EVENT_CALLBACK;
import wrapper.toupcam.models.ToupcamInst;

public interface LibToupcam extends Library {
	
	int Toupcam_Enum(ToupcamInst[] toupcamInst);
	int Toupcam_Open();
	int Toupcam_Enum(Pointer pointer);
	
	Pointer Toupcam_Open(String id);
	
	int Toupcam_StartPullModeWithCallback(Pointer handler, PTOUPCAM_EVENT_CALLBACK callback, int other);
	
	int Toupcam_PullImage(Pointer handler, Pointer pImageData, int bits, long pnWidth, long pnHeight);
	
	int Toupcam_Snap(Pointer handler, int resolutionIndex);
}
