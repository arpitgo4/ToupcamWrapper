package wrapper.toupcam.callbacks;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface EventCallback extends Callback{

	void invoke(long event);

}
