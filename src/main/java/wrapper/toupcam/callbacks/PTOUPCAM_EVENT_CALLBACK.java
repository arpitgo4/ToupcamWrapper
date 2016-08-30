package wrapper.toupcam.callbacks;

import com.sun.jna.Callback;

public interface PTOUPCAM_EVENT_CALLBACK extends Callback{

	void invoke(long event);

}
