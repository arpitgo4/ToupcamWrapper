package wrapper.toupcam;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import wrapper.toupcam.libraries.Hello;
import wrapper.toupcam.libraries.LibToupcam;
import wrapper.toupcam.models.MyStructure;
import wrapper.toupcam.models.ToupcamInst;
import wrapper.toupcam.util.Constants;

public class App implements ToupCam {

	public static final String HELLO_SO = "Hello.so";
    public static final String TOUPCAM_SO = "x64/libtoupcam.so";
    public static final String TOUPCAM_DLL = "x64/toupcam.dll";
    public static final String PATH = "/home/arpit/WorkSpaces/Toupcam_java_wrapper_WORKSPACE/"
    		+ "ToupcamJavaWrapper/src/main/resources/";
    
    LibToupcam libToupcam = null;
	
	public static void main(String[] args){
		App app = new App();
		Native.setProtected(true);
		//app.callHelloSOMethods();
		app.callCameraSOMethods();
	}
	
	public void callCameraSOMethods(){
        libToupcam = (LibToupcam) Native.loadLibrary(PATH + TOUPCAM_SO, LibToupcam.class);
 
        Pointer structure = new Memory(512 * 16);
        System.out.println("Number of Toupcams cameras detected: " + libToupcam.Toupcam_Enum(structure));
              
        System.out.println("DisplayName: " + structure.getString(0));
        System.out.println("Id: " + structure.getString(1 * 63));
        
        Pointer modelPointer = structure.getPointer(128);
        int modelPointerOffset = 0;
        Pointer modelNamePointer = modelPointer.getPointer(0);
        
        System.out.println("Model Name: " + modelNamePointer.getString(0));
        modelPointerOffset += Pointer.SIZE;
        System.out.println("Flag: " + modelPointer.getInt(modelPointerOffset));
        modelPointerOffset += Constants.INT_SIZE;
        System.out.println("MaxSpeed: " + modelPointer.getInt(modelPointerOffset));
        modelPointerOffset += Constants.INT_SIZE;
        System.out.println("Still: " + modelPointer.getInt(modelPointerOffset));
        modelPointerOffset += Constants.INT_SIZE;
        System.out.println("Preview: " + modelPointer.getInt(modelPointerOffset));
        modelPointerOffset += Constants.INT_SIZE;
        
        
    }

    public void callHelloSOMethods(){
        Hello helloLib = (Hello) Native.loadLibrary(PATH + HELLO_SO, Hello.class);
        helloLib.printHello();
        MyStructure st = new MyStructure();
        helloLib.printStruct(st);
        helloLib.printStructPointer(st);
    }
    
    /*
     * initialization block to load native lib.
     */
    {
    	if(Platform.isLinux())
    		libToupcam = (LibToupcam) Native.loadLibrary(PATH + TOUPCAM_SO, LibToupcam.class);
    	else if(Platform.isWindows())
    		libToupcam = (LibToupcam) Native.loadLibrary(PATH + TOUPCAM_DLL, LibToupcam.class);
    }

	@Override
	public int countConnectedCams() {
		Memory memory = new Memory(Constants.MEM_SIZE_FOR_TOUPINST);
		return libToupcam.Toupcam_Enum(memory);
	}

	@Override
	public ToupcamInst getToupcams() {
		return null;
	}
	
}
