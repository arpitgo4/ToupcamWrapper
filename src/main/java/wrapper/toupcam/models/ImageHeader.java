package wrapper.toupcam.models;

public class ImageHeader {

	private int size;
	private int width;
	private int height;
	private int planes;
	private int bitcount;
	private int compression;
	private int imageSize;
	private int xPelsPerMeter;
	private int yPelsPerMeter;
	private int clrUsed;
	private int clrImportant;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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
	public int getPlanes() {
		return planes;
	}
	public void setPlanes(int planes) {
		this.planes = planes;
	}
	public int getBitcount() {
		return bitcount;
	}
	public void setBitcount(int bitcount) {
		this.bitcount = bitcount;
	}
	public int getCompression() {
		return compression;
	}
	public void setCompression(int compression) {
		this.compression = compression;
	}
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}
	public int getxPelsPerMeter() {
		return xPelsPerMeter;
	}
	public void setxPelsPerMeter(int xPelsPerMeter) {
		this.xPelsPerMeter = xPelsPerMeter;
	}
	public int getyPelsPerMeter() {
		return yPelsPerMeter;
	}
	public void setyPelsPerMeter(int yPelsPerMeter) {
		this.yPelsPerMeter = yPelsPerMeter;
	}
	public int getClrUsed() {
		return clrUsed;
	}
	public void setClrUsed(int clrUsed) {
		this.clrUsed = clrUsed;
	}
	public int getClrImportant() {
		return clrImportant;
	}
	public void setClrImportant(int clrImportant) {
		this.clrImportant = clrImportant;
	}
	
	@Override
	public String toString() {
		return "ImageHeader [size=" + size + ", width=" + width + ", height=" + height + ", planes=" + planes
				+ ", bitcount=" + bitcount + ", compression=" + compression + ", imageSize=" + imageSize
				+ ", xPelsPerMeter=" + xPelsPerMeter + ", yPelsPerMeter=" + yPelsPerMeter + ", clrUsed=" + clrUsed
				+ ", clrImportant=" + clrImportant + "]";
	}
	
}
