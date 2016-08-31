package wrapper.toupcam.callbacks;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface PTOUPCAM_EVENT_CALLBACK extends Callback{

	void invoke(long event, Pointer context);

}
