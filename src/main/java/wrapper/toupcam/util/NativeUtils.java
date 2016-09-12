package wrapper.toupcam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Platform;

import wrapper.toupcam.libraries.LibToupcam;

/**
 * To extract native libraries packed in this JAR 
 * file at the {@code Constants.NATIVE_LIB_EXTRACTION_DIR}
 * for the JNA to load.
 * 
 * @author arpit
 *
 */
public class NativeUtils {

	private static String extractNativeLibs(String libraryName){
		ClassLoader classLoader = NativeUtils.class.getClassLoader();
		
		String subDir = null;
		if(libraryName.contains("/"))
			// extracting subdir's name from resources path.  eg. (x64/libToupcam.so) => x64
			subDir = libraryName.substring(0, libraryName.indexOf("/"));
		
		createNativeDir(subDir);
		String pathToLibrary = Constants.NATIVE_LIB_EXTRACTION_DIR + File.separator + libraryName;
		
		try(InputStream in = classLoader.getResourceAsStream(libraryName);
				FileOutputStream outStream = new FileOutputStream(pathToLibrary)){

			byte[] buf = new byte[8*1024];
			int len;
			while((len = in.read(buf)) != -1)
				outStream.write(buf,0,len);

			return new File(pathToLibrary).getAbsolutePath();
		}catch(Exception e){
			System.out.println(e);return null;}
	}

	public static Object loadLibrary(String libraryName, Class interfaceClass){
		String absPathToLibrary = extractNativeLibs(libraryName);
		if(absPathToLibrary != null)
			return Native.loadLibrary(absPathToLibrary, interfaceClass);
		else return null;
	}

	private static void createNativeDir(String subDir) {
		if(subDir != null){
			File native_dir = new File(Constants.NATIVE_LIB_EXTRACTION_DIR + File.separator + subDir);
			if(!native_dir.exists()) native_dir.mkdirs();
		}
	}
	
	/**
	 * Checks for the machine's architecture and OS, load 
	 * and returns machine specific native library.
	 * 
	 * Machine Architecture: 32-bit or 64-bit
	 * OS: Linux or Windows
	 *  
	 * Note: To load native library JNA requires absolute path.
	 * @return
	 */
	public static Object getNativeLib(){
		Object nativeLib;
		if(Platform.is64Bit()){
			if(Platform.isLinux())
				nativeLib = (LibToupcam) NativeUtils.loadLibrary(Constants.x64_TOUPCAM_SO, LibToupcam.class);
			else
				nativeLib = (LibToupcam) NativeUtils.loadLibrary(Constants.x64_TOUPCAM_DLL, LibToupcam.class);
		}else {
			if(Platform.isLinux())
				nativeLib = (LibToupcam) NativeUtils.loadLibrary(Constants.x86_TOUPCAM_SO, LibToupcam.class);
			else
				nativeLib = (LibToupcam) NativeUtils.loadLibrary(Constants.x86_TOUPCAM_DLL, LibToupcam.class);
		}
		return nativeLib;
	}

}
