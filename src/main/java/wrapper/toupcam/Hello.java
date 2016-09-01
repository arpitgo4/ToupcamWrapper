package wrapper.toupcam;

import java.nio.ByteBuffer;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import wrapper.toupcam.libraries.LibHello;
import wrapper.toupcam.util.Constants;

public class Hello {

	private LibHello libHello;
	
	public Hello(){
		this.libHello = (LibHello) Native.loadLibrary(Constants.PATH + "/Hello.so", LibHello.class);
	}
	
	public static void main(String[] args){
		Native.setProtected(true);
		Hello h = new Hello();
		
		Pointer p = h.libHello.getIntBuffer();
		byte[] buf = p.getByteArray(0, 4);
		int i = buf[0];
		//System.out.println(i);
		
		int result = ByteBuffer.wrap(buf).getInt();
		System.out.println(result);
	}
	
	
}
