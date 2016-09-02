package wrapper.toupcam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Native;

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

		// extracting subdir's name from resources path.  eg. (x64/libToupcam.so) => x64
		String subDir = libraryName.substring(0, libraryName.indexOf("/"));
		createNativeDir(subDir);
		String pathToLibrary = Constants.NATIVE_LIB_EXTRACTION_DIR + File.separator + libraryName;

		try(InputStream in = classLoader.getResourceAsStream(libraryName);
				FileOutputStream outStream = new FileOutputStream(pathToLibrary)){

			byte[] buf = new byte[8*1024];
			int len;
			while((len = in.read(buf)) != -1)
				outStream.write(buf,0,len);

			return new File(pathToLibrary).getAbsolutePath();
		}catch(Exception e){return null;}
	}

	public static Object loadLibrary(String libraryName, Class interfaceClass){
		String absPathToLibrary = extractNativeLibs(libraryName);
		if(absPathToLibrary != null)
			return Native.loadLibrary(absPathToLibrary, interfaceClass);
		else return null;
	}

	private static void createNativeDir(String subDir) {
		File native_dir = new File(Constants.NATIVE_LIB_EXTRACTION_DIR + File.separator + subDir);
		if(!native_dir.exists()) native_dir.mkdirs();
	}

}
