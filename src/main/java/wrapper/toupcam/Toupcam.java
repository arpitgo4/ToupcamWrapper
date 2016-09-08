package wrapper.toupcam;

import java.util.List;

import wrapper.toupcam.callbacks.ImageCallback;
import wrapper.toupcam.enumerations.HResult;
import wrapper.toupcam.models.ToupcamInst;

public interface Toupcam {
	
	public int countConnectedCams();
	
	public List<ToupcamInst> getToupcams();
	
	public HResult startImageStreaming(ImageCallback imageCallback);
	
	public Toupcam getInstance();
	
	public HResult setResolution(int resolutionIndex);
	
}
