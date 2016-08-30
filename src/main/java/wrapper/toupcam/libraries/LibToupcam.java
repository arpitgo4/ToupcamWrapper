package wrapper.toupcam.libraries;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

import wrapper.toupcam.models.ToupcamInst;

public interface LibToupcam extends Library {
	
	int Toupcam_Enum(ToupcamInst[] toupcamInst);
	int Toupcam_Open();
	int Toupcam_Enum(Pointer pointer);
	
}
