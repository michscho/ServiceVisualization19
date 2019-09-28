package tum.franca.util.propertyfunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author michaelschott
 *
 */
public class PropertyEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private List<PropertyElement> elementList;
	
	public PropertyEntity(String name) {
		this.name = name;
		elementList = new ArrayList<PropertyElement>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PropertyElement> getElementList() {
		return elementList;
	}

	public void setElementList(List<PropertyElement> elementList) {
		this.elementList = elementList;
	}
	
	
	

}
