package tum.franca.view.metric;

public class MetricsEntry {
	
	public String name;
	public String value;
	public String description;
	
	public MetricsEntry(String name, String value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
