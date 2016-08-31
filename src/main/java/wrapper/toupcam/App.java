package wrapper.toupcam;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import wrapper.toupcam.callbacks.PTOUPCAM_DATA_CALLBACK;
import wrapper.toupcam.callbacks.PTOUPCAM_EVENT_CALLBACK;
import wrapper.toupcam.callbacks.PTOUPCAM_HOTPLUG_CALLBACK;
import wrapper.toupcam.enumerations.Event;
import wrapper.toupcam.enumerations.HResult;
import wrapper.toupcam.enumerations.Options;
import wrapper.toupcam.libraries.LibToupcam;
import wrapper.toupcam.models.Image;
import wrapper.toupcam.models.Model;
import wrapper.toupcam.models.Resolution;
import wrapper.toupcam.models.ToupcamInst;
import wrapper.toupcam.util.Constants;
import wrapper.toupcam.util.Util;

public class App implements ToupCam  {

	private LibToupcam libToupcam = null;
	private Pointer camHandler;

	public static void main(String[] args){
		App app = new App();
		Native.setProtected(true);
		List<ToupcamInst> cams = app.getToupcams();
		app.registerPlugInOrOut();
		Pointer handler = app.openCam(null);
		System.out.println("Set RAW Options Result: " + app.setOptions(handler, Options.OPTION_RAW, 1));
		//System.out.println("Start Pull Result: " + app.startPullWithCallBack(handler));
		//System.out.println("Get SnapShot Result: " + app.getSnapShot(handler, 0));
		
		/*System.out.println("Start Push Result: " + app.startPushMode(handler));
		System.out.println("Get SnapShot Result: " + app.getSnapShot(handler, 0));*/
	}
	
	public App(){
		libToupcam = (LibToupcam) getNativeLib();
		Util.keepVMRunning();				// keep JVM from terminating
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
	
	public void registerPlugInOrOut(){
		libToupcam.Toupcam_HotPlug(new PTOUPCAM_HOTPLUG_CALLBACK() {
			@Override public void invoke() {
				System.out.println("Camera is pluged in or out.");
			}
		});
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

	public Pointer openCam(String id){
		camHandler = libToupcam.Toupcam_Open(id);
		return camHandler;
	}

	public HResult startPullWithCallBack(Pointer handler){
		int result = libToupcam.Toupcam_StartPullModeWithCallback(handler, new PTOUPCAM_EVENT_CALLBACK() {
			@Override public void invoke(long event) {
				System.out.println(Event.key(event) + " event received");
				if(Event.key(event) == Event.EVENT_STILLIMAGE){
					//System.out.println("Still Image Available!");
					Image image = getStillImage(handler);
					System.out.println(image);
					Util.convertImagePointerToImage(image.getImagePointer(), image.getWidth(), image.getHeight());
					
				}else if(Event.key(event) == Event.EVENT_IMAGE){
					//System.out.println("Image Data Available");
					Image image = getImage(handler);
					//convertPointerToImage(image.getImagePointer(), image.getWidth(), image.getHeight());
				}
			}
		}, 10);
		return HResult.key(result);
	}
	
	public HResult startPushMode(Pointer handler){
		int result = libToupcam.Toupcam_StartPushMode(handler, new PTOUPCAM_DATA_CALLBACK() {
			@Override public void invoke(Pointer imagePointer, Pointer imageMetaData, boolean isSnap) {
				System.out.println("isSnap: " + isSnap + ",Image Recevied: " + imagePointer);
				Util.convertImagePointerToImage(imagePointer, 1280, 960);  // 1280 * 960
			}
		}, 0);
		return HResult.key(result);
	}
	
	public HResult setOptions(Pointer handler, Options option, int value){
		return HResult.key(libToupcam.Toupcam_put_Option(handler, option.getValue(), value));
	}

	public HResult getSnapShot(Pointer handler, int resolutionIndex){
		return HResult.key(libToupcam.Toupcam_Snap(handler, resolutionIndex));
	}

	public Image getImage(Pointer handler){
		//width=1280, height=960
		Pointer imageBuffer = new Memory(1280 * 960);
		Pointer width = new Memory(4), height = new Memory(4);
		int result = libToupcam.Toupcam_PullImage(handler, imageBuffer, 8, width, height);
		return new Image(imageBuffer, width.getInt(0), height.getInt(0),HResult.key(result));
	}
	
	public Image getStillImage(Pointer handler){
		//width=2592, height=1944	
		Pointer imageBuffer = new Memory(2592 * 1944);
		Pointer width = new Memory(4), height = new Memory(4);
		int result = libToupcam.Toupcam_PullStillImage(handler, imageBuffer, 8, width, height);
		return new Image(imageBuffer, width.getInt(0), height.getInt(0), HResult.key(result));
	}

	public Pointer getCamHandler() {return camHandler;}
	public void setCamHandler(Pointer camHandler) {this.camHandler = camHandler;}


}
