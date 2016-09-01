package wrapper.toupcam.models;

import wrapper.toupcam.enumerations.HResult;

public class RawFormat {

	private int nFourCC;
	private int bitdepth;
	private HResult result;
	
	public RawFormat(int nFourCC, int bitdepth, HResult result) {
		this.nFourCC = nFourCC;
		this.bitdepth = bitdepth;
		this.result = result;
	}
	public int getnFourCC() {
		return nFourCC;
	}
	public void setnFourCC(int nFourCC) {
		this.nFourCC = nFourCC;
	}
	public int getBitdepth() {
		return bitdepth;
	}
	public void setBitdepth(int bitdepth) {
		this.bitdepth = bitdepth;
	}
	public HResult getResult() {
		return result;
	}
	public void setResult(HResult result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "RawFormat [nFourCC=" + nFourCC + ", bitdepth=" + bitdepth + ", result=" + result + "]";
	}
	
}
