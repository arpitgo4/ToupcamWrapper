package wrapper.toupcam;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import wrapper.toupcam.libraries.LibHello;
import wrapper.toupcam.util.Constants;

public class Hello {

	private LibHello libHello;
	
	public Hello(){
		this.libHello = (LibHello) Native.loadLibrary(Constants.NATIVE_LIB_EXTRACTION_DIR + "/Hello.so", LibHello.class);
	}
	
	public static void main(String[] args){
		Native.setProtected(true);
		Hello h = new Hello();
		
		Pointer p = h.libHello.getIntBuffer();
		int[] arr = p.getIntArray(0, 3);
		for(int a : arr)
			System.out.println(a);
	}
	
	
}
