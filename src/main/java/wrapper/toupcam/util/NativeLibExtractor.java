package wrapper.toupcam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * To extract native libraries packed in this JAR 
 * file at the {@code Constants.NATIVE_LIB_EXTRACTION_DIR}
 * for the JNA to load.
 * 
 * @author arpit
 *
 */
public class NativeLibExtractor {

	public static void main(String[] args) throws Exception{
		NativeLibExtractor extractor = new NativeLibExtractor();
		boolean result = extractor.extractNativeLibs();
		System.out.println(result);
	}
	
	private List<String> nativeLibs = Arrays.asList(new String[]{
			Constants.x64_TOUPCAM_DLL, Constants.x64_TOUPCAM_SO,
			Constants.x86_TOUPCAM_DLL, Constants.x86_TOUPCAM_SO
	});
	
	public boolean extractNativeLibs(){
		ClassLoader classLoader = NativeLibExtractor.class.getClassLoader();
		
		for(String nativeLib : nativeLibs) {
			// extracting subdir's name from resources path.  eg. (x64/libToupcam.so) => x64
			String subDir = nativeLib.substring(0, nativeLib.indexOf("/"));
			createNativeDir(subDir);
			
			try(InputStream in = classLoader.getResourceAsStream(nativeLib);
				FileOutputStream outStream = 
					new FileOutputStream(Constants.NATIVE_LIB_EXTRACTION_DIR + File.separator + nativeLib)){
				
				byte[] buf = new byte[8*1024];
				int len;
				while((len = in.read(buf)) != -1)
		            outStream.write(buf,0,len);
		        
			}catch(Exception e){return false;}
		}
		return true;
	}
	
	private void createNativeDir(String subDir) {
		File native_dir = new File(Constants.NATIVE_LIB_EXTRACTION_DIR + File.separator + subDir);
		if(!native_dir.exists()) native_dir.mkdirs();
	}
	
}
