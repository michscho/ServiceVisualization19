package tum.franca.reader;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.franca.core.franca.FAttribute;
import org.franca.core.franca.FInterface;
import org.franca.core.franca.FMethod;

public class InterfaceReader extends FidlModel {

	public InterfaceReader(URI uri) {
		super(uri);
	}

	public FInterface getFirstInterface() {
		return fmodel.getInterfaces().get(0);
	}
	
	public List<FMethod> getFirstMethods() {
		return getFirstInterface().getMethods(); 
	}
	
	public List<FAttribute> getFirstAttributes() {		
		return getFirstInterface().getAttributes();
	}
		

}
