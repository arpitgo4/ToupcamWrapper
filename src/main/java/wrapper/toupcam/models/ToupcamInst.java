package wrapper.toupcam.models;

public class ToupcamInst implements IModel {

	public String displayName;
	public String id;
	public String model;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "ToupcamInst [displayName=" + displayName + ", id=" + id + ", model=" + model + "]";
	}
}
