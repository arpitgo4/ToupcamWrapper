package wrapper.toupcam.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class Resolution extends Structure{

	public long width;
	public long height;
	
	@Override
	protected List getFieldOrder() {
		return Arrays.asList(new String[] {"width", "height"});
	}
	
}
