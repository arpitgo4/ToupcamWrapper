package wrapper.toupcam.models;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class MyStructure extends Structure {
	public int number = 10;
	
	@Override
	protected List getFieldOrder() {
		return Arrays.asList(new String[] { "number" });
	}
}
