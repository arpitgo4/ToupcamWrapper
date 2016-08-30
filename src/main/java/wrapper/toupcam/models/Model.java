package wrapper.toupcam.models;

import java.util.Arrays;

public class Model implements IModel {

	public String name;
	public long flag;
	public long maxspeed;
	public long preview;
	public long still;
	public Resolution[] res;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getFlag() {
		return flag;
	}
	public void setFlag(long flag) {
		this.flag = flag;
	}
	public long getMaxspeed() {
		return maxspeed;
	}
	public void setMaxspeed(long maxspeed) {
		this.maxspeed = maxspeed;
	}
	public long getPreview() {
		return preview;
	}
	public void setPreview(long preview) {
		this.preview = preview;
	}
	public long getStill() {
		return still;
	}
	public void setStill(long still) {
		this.still = still;
	}
	public Resolution[] getRes() {
		return res;
	}
	public void setRes(Resolution[] res) {
		this.res = res;
	}
	
	@Override
	public String toString() {
		return "Model [name=" + name + ", flag=" + flag + ", maxspeed=" + maxspeed + ", preview=" + preview + ", still="
				+ still + ", res=" + Arrays.toString(res) + "]";
	}
}
