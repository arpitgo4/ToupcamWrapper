package wrapper.toupcam.libraries;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface LibHello extends Library {
	
	Pointer getIntBuffer();

}
