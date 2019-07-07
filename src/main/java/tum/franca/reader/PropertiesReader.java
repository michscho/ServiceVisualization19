package tum.franca.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.franca.core.franca.FBinding;
import org.franca.core.franca.FFunctionalScope;
import org.franca.core.franca.FRuntime;
import org.franca.core.franca.FSaftyCritical;
import org.franca.core.franca.FSecurityCritical;
import org.franca.core.franca.FTimeSpecification;

/**
 * 
 * @author michaelschott
 *
 */
public class PropertiesReader extends InterfaceReader {

	public PropertiesReader(URI uri) {
		super(uri);
	}
	
	public HashMap<String, String> propertiesMap = new HashMap<String, String>();
	
	public List<String> getProperties(){
		List<String> list = new ArrayList<String>();
		list.add(getBinding().getName());
		list.add(getFunctionalScope().getName());
		list.add(getRuntime().getName());
		list.add(getSaftyCritical().getName());
		list.add(getSecurityCritical().getName());
		return list;
	}
	
	public HashMap<String, String> getAllStringProperties() {
		if(getFunctionalScope().getName() != "notDefined") {
		propertiesMap.put("Functional Scope",getFunctionalScope().getName());
		}
		if(getBinding().getName() != "notDefined") {
		propertiesMap.put("Binding", getBinding().getLiteral());
		}
		if(getSaftyCritical().getName() != "notDefined") {
		propertiesMap.put("Safty Critical", getSaftyCritical().getName());
		}
		if(getSecurityCritical().getName() != "notDefined") {
		propertiesMap.put("Security Critial", getSecurityCritical().getName());
		}
		if(getTime() != 0) {
		propertiesMap.put("Time", getTime().toString());
		}
		if(getTimeSpecification().getName() != "notDefined") {
		propertiesMap.put("Time Specification", getTimeSpecification().getName());
		}
		if(getRuntime().getName() != "notDefined") {
		propertiesMap.put("Runtime", getRuntime().getName());
		}
		
		return propertiesMap;
	}

	/**
	 * 
	 * @return FFunctionalScope: powertrain, driverAssistance, interior, telematics,
	 *         crossfunctional
	 */
	public FFunctionalScope getFunctionalScope() {
		return getFirstInterface().getFunctionalScope();
	}

	/**
	 * 
	 * @return FBinding: static, dynamic
	 */
	public FBinding getBinding() {
		return getFirstInterface().getBinding();
	}
	
	/**
	 * 
	 * @return FSaftyCritical: ASIL_A, ASIL_B, ASIL_C, ASIL_D
	 */
	public FSaftyCritical getSaftyCritical() {
		return getFirstInterface().getSaftyCritical();
	}
	
	/**
	 * 
	 * @return FSecurityCritical: wireLevelSecurity, userAutentication,
	 *         serviceLevelSecurity
	 */
	public FSecurityCritical getSecurityCritical() {
		return getFirstInterface().getSecurityCritical();
	}

	/**
	 * 
	 * @return FTimeSpecification: ns, nss, ms, s
	 */
	public FTimeSpecification getTimeSpecification() {
		return getFirstInterface().getTimeSpecification();
	}
	
	/**
	 * 
	 * @return Time as Integer
	 */
	public Integer getTime() {
		return getFirstInterface().getTime();
	}

	/**
	 * 
	 * @return FRuntime: onboard, offboard
	 */
	public FRuntime getRuntime() {
		return getFirstInterface().getRuntime();
	}
	
	/**
	 * 
	 * @return 
	 */
	public Boolean getHardwareDependend() {
		return getFirstInterface().isHardwareDependend();
	}
	

}
