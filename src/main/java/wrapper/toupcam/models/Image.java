package wrapper.toupcam.models;

import com.sun.jna.Pointer;

import wrapper.toupcam.enumerations.HResult;

public class Image {

	private int width;
	private int height;
	private Pointer imagePointer;
	private HResult hresult;
	
	public Image(Pointer imagePointer, int width, int height, HResult hresult) {
		this.imagePointer = imagePointer;
		this.width = width;
		this.height = height;
		this.hresult = hresult;
	}
	
	public HResult getHresult() {
		return hresult;
	}
	public void setHresult(HResult hresult) {
		this.hresult = hresult;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Pointer getImagePointer() {
		return imagePointer;
	}
	public void setImagePointer(Pointer imagePointer) {
		this.imagePointer = imagePointer;
	}
	@Override
	public String toString() {
		return "Image [width=" + width + ", height=" + height + ", imagePointer=" + imagePointer + ", hresult=" + hresult
				+ "]";
	}

}
