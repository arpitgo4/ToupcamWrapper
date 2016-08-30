package wrapper.toupcam.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class Model extends Structure {

	public Pointer name;
	public long flag;
	public long maxspeed;
	public long preview;
	public long still;
	public Resolution[] res = new Resolution[10];
	
	@Override
	protected List getFieldOrder() {
		return Arrays.asList(new String[]{"name", "flag", "maxspeed", "preview", "still", "res"});
	}

}
