package wrapper.toupcam.callbacks;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface PTOUPCAM_DATA_CALLBACK extends Callback {

	void invoke(Pointer imagePointer, Pointer imageMetaData, boolean isSnapshot);
	
}
