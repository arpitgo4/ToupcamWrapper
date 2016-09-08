package wrapper.toupcam;

import java.util.List;

import wrapper.toupcam.callbacks.ImageStreamCallback;
import wrapper.toupcam.enumerations.HResult;
import wrapper.toupcam.models.ToupcamInst;

public interface Toupcam {
	
	public int countConnectedCams();
	
	public List<ToupcamInst> getToupcams();
	
	public HResult startImageStreaming(ImageStreamCallback imageCallback);
	
	//public Toupcam getInstance();
	
	public HResult setResolution(int resolutionIndex);
	
	public HResult getStillImage(int resolutionIndex);
	
	public HResult pauseStreaming();
	
	public HResult resumeStreaming();
	
	public HResult stopStreaming();
	
}
