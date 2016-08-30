package wrapper.toupcam;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {
	
	@Test public void testConnectedCams(){
		ToupCam toupcam = new App();
		assertEquals(1, toupcam.countConnectedCams());
	}
	
}
