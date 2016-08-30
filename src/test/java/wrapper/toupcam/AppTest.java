package wrapper.toupcam;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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
	
}
