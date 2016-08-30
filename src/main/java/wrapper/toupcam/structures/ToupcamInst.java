package wrapper.toupcam.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class ToupcamInst extends Structure {

	public Pointer displayName;
	public Pointer id;
	public Pointer model;
	
	@Override protected List getFieldOrder() {
		return Arrays.asList(new String[] {"displayName", "id", "model"});
	}
	
}
