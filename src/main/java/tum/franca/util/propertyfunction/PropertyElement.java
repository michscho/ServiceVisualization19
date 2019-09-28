package tum.franca.util.propertyfunction;

import java.io.Serializable;

public class PropertyElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	private String group;
	
	public PropertyElement(String key, String value, String group) {
		this.key = key;
		this.value = value;
		this.group = group;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	

}
