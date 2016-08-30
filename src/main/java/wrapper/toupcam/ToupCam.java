package wrapper.toupcam;

import java.util.List;

import wrapper.toupcam.models.ToupcamInst;

public interface ToupCam {
	
	public int countConnectedCams();
	
	public List<ToupcamInst> getToupcams();
	
}
