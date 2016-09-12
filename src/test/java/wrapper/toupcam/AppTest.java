package wrapper.toupcam;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jna.Pointer;

public class AppTest {
	
	App toupcam;
	
	@Before public void setup(){
		toupcam = new App();
	}
	
	@Ignore
	@Test public void testConnectedCams(){
		assertEquals(1, toupcam.countConnectedCams());
	}
	
	@Ignore
	@Test public void testGetToupcams(){
		assert(toupcam.getToupcams().isEmpty());
	}
	
	@Ignore
	@Test public void testOpencam(){
		toupcam.getToupcams().forEach(cam -> {
			String id = cam.getId();
			Pointer handler = (Pointer) ((App) toupcam).openCam(id);
		});
	}
	
	/*@Test public void testStartPull(){
		boolean result = toupcam.startPullWithCallBack();
		System.out.println(result);
	}*/
	
}
