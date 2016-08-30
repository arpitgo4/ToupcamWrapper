package wrapper.toupcam;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import wrapper.toupcam.libraries.Hello;
import wrapper.toupcam.libraries.LibToupcam;
import wrapper.toupcam.models.Model;
import wrapper.toupcam.models.MyStructure;
import wrapper.toupcam.models.Resolution;
import wrapper.toupcam.models.ToupcamInst;
import wrapper.toupcam.util.Constants;

public class App implements ToupCam {
    
    LibToupcam libToupcam = null;
    
	public static void main(String[] args){
		App app = new App();
		Native.setProtected(true);
		//app.callHelloSOMethods();
		//app.callCameraSOMethods();
		app.getToupcams();
		//System.out.println(app.openCam(null));
	}
	
	public void callCameraSOMethods(){
        libToupcam = (LibToupcam) Native.loadLibrary(Constants.PATH + Constants.x64_TOUPCAM_SO, LibToupcam.class);
 
        Pointer structure = new Memory(512 * 16);
        System.out.println("Number of Toupcams cameras detected: " + libToupcam.Toupcam_Enum(structure));
              
        System.out.println("DisplayName: " + structure.getString(0));
        System.out.println("Id: " + structure.getString(1 * 64));
        
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
        Hello helloLib = (Hello) Native.loadLibrary(Constants.PATH + Constants.HELLO_SO, Hello.class);
        helloLib.printHello();
        MyStructure st = new MyStructure();
        helloLib.printStruct(st);
        helloLib.printStructPointer(st);
    }
    
    public App(){
    	libToupcam = (LibToupcam) getNativeLib();
    }
    
    /**
     * Checks for the machine's architecture and OS, load 
     * and returns machine specific native library.
     * 
     * Machine Architecture: 32-bit or 64-bit
     * OS: Linux or Windows
     *  
     * @return
     */
    private Object getNativeLib(){
    	Object nativeLib;
    	if(Platform.is64Bit()){
    		if(Platform.isLinux())
    			nativeLib = (LibToupcam) Native.loadLibrary(Constants.PATH + Constants.x64_TOUPCAM_SO, LibToupcam.class);
    		else
    			nativeLib = (LibToupcam) Native.loadLibrary(Constants.PATH + Constants.x64_TOUPCAM_DLL, LibToupcam.class);
    	}else {
    		if(Platform.isLinux())
    			nativeLib = (LibToupcam) Native.loadLibrary(Constants.PATH + Constants.x86_TOUPCAM_SO, LibToupcam.class);
    		else
    			nativeLib = (LibToupcam) Native.loadLibrary(Constants.PATH + Constants.x86_TOUPCAM_DLL, LibToupcam.class);
    	}
    	return nativeLib;
    }

	@Override
	public int countConnectedCams() {
		Memory memory = new Memory(Constants.MEM_SIZE_FOR_TOUPCAMINST);
		return libToupcam.Toupcam_Enum(memory);
	}

	@Override
	public List<ToupcamInst> getToupcams() {
		List<ToupcamInst> toupcamInstList = new ArrayList<ToupcamInst>();
		Memory structurePointer = new Memory(Constants.MEM_SIZE_FOR_TOUPCAMINST);
		int count_cams = libToupcam.Toupcam_Enum(structurePointer);
		for(int i = 0 ; i < count_cams; i++) toupcamInstList.add(new ToupcamInst());
		
		
		toupcamInstList.forEach(toupcamInst -> {
			
			int structurePointerOffset = 0;
			toupcamInst.setDisplayName(structurePointer.getString(structurePointerOffset));
			structurePointerOffset += 64;
			toupcamInst.setId(structurePointer.getString(structurePointerOffset));
			structurePointerOffset += 64;
			
			Pointer modelPointer = structurePointer.getPointer(structurePointerOffset);
			int modelPointerOffset = 0;
			toupcamInst.setModel(new Model());
			
			toupcamInst.getModel().setName(modelPointer.getPointer(modelPointerOffset).getString(0));
			modelPointerOffset += Pointer.SIZE;
			toupcamInst.getModel().setFlag(modelPointer.getInt(modelPointerOffset));
			modelPointerOffset += Constants.INT_SIZE;
			toupcamInst.getModel().setMaxspeed(modelPointer.getInt(modelPointerOffset));
			modelPointerOffset += Constants.INT_SIZE;
			toupcamInst.getModel().setStill(modelPointer.getInt(modelPointerOffset));
			modelPointerOffset += Constants.INT_SIZE;
			toupcamInst.getModel().setPreview(modelPointer.getInt(modelPointerOffset));
			modelPointerOffset += Constants.INT_SIZE;
			
			int resolutions = (int) Math.max(toupcamInst.getModel().getPreview(),
					toupcamInst.getModel().getStill());
			
			Resolution[] resolutionArray = new Resolution[resolutions];
			for(int i = 0; i < resolutions; i++) resolutionArray[i] = new Resolution();
			
			toupcamInst.getModel().setRes(resolutionArray);
			Resolution[] toupcamInstRes = toupcamInst.getModel().getRes();
			
			for(int i = 0; i < resolutions; i++){
				toupcamInstRes[i].width = modelPointer.getInt(modelPointerOffset);
				modelPointerOffset += Constants.INT_SIZE;
				toupcamInstRes[i].height = modelPointer.getInt(modelPointerOffset);
				modelPointerOffset += Constants.INT_SIZE;
			}
			
		});
		
		return toupcamInstList;
	}
	
	public Object openCam(String id){
		return libToupcam.Toupcam_Open(id);
	}
}
