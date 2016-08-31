package wrapper.toupcam;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import wrapper.toupcam.callbacks.PTOUPCAM_EVENT_CALLBACK;
import wrapper.toupcam.enumerations.Event;
import wrapper.toupcam.enumerations.HResult;
import wrapper.toupcam.enumerations.Options;
import wrapper.toupcam.libraries.Hello;
import wrapper.toupcam.libraries.LibToupcam;
import wrapper.toupcam.models.Image;
import wrapper.toupcam.models.Model;
import wrapper.toupcam.models.MyStructure;
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
		Pointer handler = app.openCam(null);
		//System.out.println("Set RAW Options Result: " + app.setOptions(handler, Options.OPTION_RAW, 1));
		System.out.println("Start Pull Result: " + app.startPullWithCallBack(handler));
		System.out.println("Get SnapShot Result: " + app.getSnapShot(handler, 0));
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
					convertPointerToImage(image.getImagePointer(), image.getWidth(), image.getHeight());
					
				}else if(Event.key(event) == Event.EVENT_IMAGE){
					//System.out.println("Image Data Available");
					Image image = getImage(handler);
					//convertPointerToImage(image.getImagePointer(), image.getWidth(), image.getHeight());
				}
			}
		}, 10);
		return HResult.key(result);
	}
	
	public void convertPointerToImage(Pointer imagePointer, int width, int height){
		byte[] imageBytes = imagePointer.getByteArray(0, width * height);
		InputStream in = new ByteArrayInputStream(imageBytes);
		BufferedImage bImageFromConvert;
		try {
			bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "jpg", new File(
					Constants.PATH + "/image.jpg"));
		} catch (Exception e) {
			System.out.println("Exception thrown during convertion : " + e);
		}
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
