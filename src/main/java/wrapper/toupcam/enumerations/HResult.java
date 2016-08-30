package wrapper.toupcam.enumerations;

public enum HResult {

	S_OK(0x00000000),
	S_FALSE(0x00000001),
	E_FAIL(0x80004005),
	E_INVALIDARG(0x80070057),
	E_NOTIMPL(0x80004001),
	E_POINTER(0x80004003),
	E_UNEXPECTED(0x8000FFFF);
	
	private int value;
	
	HResult(int value){
		this.value = value;
	}
	
	public static HResult key(long event){
		for(HResult v : values()){
			if(v.value == event)
				return v;
		}
		return null;
	}
}
