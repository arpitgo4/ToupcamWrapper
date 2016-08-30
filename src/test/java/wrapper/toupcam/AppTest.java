package wrapper.toupcam;

import static org.junit.Assert.assertEquals;

import javax.swing.text.AbstractDocument.LeafElement;

import org.junit.Before;
import org.junit.Test;

import com.sun.jna.Pointer;

public class AppTest {
	
	ToupCam toupcam;
	
	@Before public void setup(){
		toupcam = new App();
	}
	
	@Test public void testConnectedCams(){
		assertEquals(1, toupcam.countConnectedCams());
	}
	
	@Test public void testGetToupcams(){
		toupcam.getToupcams().forEach(System.out::println);
	}
	
	@Test public void testOpencam(){
		toupcam.getToupcams().forEach(cam -> {
			String id = cam.getId();
			System.out.println(id);
			Pointer handler = (Pointer) ((App) toupcam).openCam(id);
			System.out.println(handler);
		});
	}
	
}
