package wrapper.toupcam.enumerations;

public enum HResult {

	S_OK(0x00000000),			// Operation successful
	S_FALSE(0x00000001),		// Operation successful
	E_FAIL(0x80004005),			// Unspecified failure
	E_INVALIDARG(0x80070057),	// One or more arguments are not valid
	E_NOTIMPL(0x80004001),		// Not supported or not implemented
	E_POINTER(0x80004003),		// Pointer that is not valid
	E_UNEXPECTED(0x8000FFFF);	// Unexpected failure
	
	private int value;
	
	HResult(int value){
		this.value = value;
	}
	
	public static HResult key(long hresult){
		for(HResult v : values()){
			if(v.value == hresult)
				return v;
		}
		return null;
	}
}
