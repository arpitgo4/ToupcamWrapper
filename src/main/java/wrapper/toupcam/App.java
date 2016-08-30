package wrapper.toupcam;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import wrapper.toupcam.libraries.Hello;
import wrapper.toupcam.libraries.LibToupcam;
import wrapper.toupcam.structures.MyStructure;
import wrapper.toupcam.structures.ToupcamInst;

public class App {

	public static final String HELLO_SO = "Hello.so";
    public static final String TOUPCAM_SO = "libtoupcam.so";
    public static final String PATH = "/home/arpit/WorkSpaces/Toupcam_java_wrapper_WORKSPACE/"
    		+ "ToupcamJavaWrapper/src/main/resources/";
	
	public static void main(String[] args){
		App app = new App();
		Native.setProtected(true);
		//app.callHelloSOMethods();
		app.callCameraSOMethods();
	}
	
	public void callCameraSOMethods(){
        LibToupcam libToupcam = (LibToupcam) Native.loadLibrary(PATH + TOUPCAM_SO, LibToupcam.class);
       // ToupcamInst[] buffer = new ToupcamInst[1];
        Pointer structure = new Memory(512 * 16);
        System.out.println("Number of Toupcams cameras detected: " + libToupcam.Toupcam_Enum(structure));
       /* for(ToupcamInst buf : buffer){
            System.out.println(buf);
        	System.out.println("Native DisplayName: " + buf.displayName);
        	System.out.println("Display Name: " + buf.displayName);
        	
            System.out.println("id: " + Pointer.nativeValue(buf.id));
        }*/
        System.out.println("DisplayName: " + structure.getString(0));
        
        System.out.println("Id: " + structure.getString(1 * 64));
        Pointer modelPointer = structure.getPointer(128);
        System.out.println(modelPointer);
        System.out.println("Model Name: " + modelPointer.getString(128));
    }

    public void callHelloSOMethods(){
        Hello helloLib = (Hello) Native.loadLibrary(PATH + HELLO_SO, Hello.class);
        helloLib.printHello();
        MyStructure st = new MyStructure();
        helloLib.printStruct(st);
        helloLib.printStructPointer(st);
    }
	
}
