package wrapper.toupcam.models;

public class Resolution implements IModel {

	public long width;
	public long height;
	
	public Resolution(){}
	
	public Resolution(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public long getWidth() {
		return width;
	}
	public void setWidth(long width) {
		this.width = width;
	}
	public long getHeight() {
		return height;
	}
	public void setHeight(long height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "Resolution [width=" + width + ", height=" + height + "]";
	}
}
