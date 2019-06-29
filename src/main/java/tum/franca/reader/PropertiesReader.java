package tum.franca.reader;

import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.franca.core.franca.FBinding;
import org.franca.core.franca.FFunctionalScope;
import org.franca.core.franca.FRuntime;
import org.franca.core.franca.FSaftyCritical;
import org.franca.core.franca.FSecurityCritical;
import org.franca.core.franca.FSpecialType;
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
	
	public HashMap<String, String> getAllStringProperties() {
		try {
		propertiesMap.put("Functional Scope",getFunctionalScope().getName());
		} catch (NullPointerException e) {
		}
		try {
		propertiesMap.put("Binding", getBinding().getLiteral());
		} catch (NullPointerException e) {	
		}
		
		try {
		propertiesMap.put("Safty Critical", getSaftyCritical().getName());
		} catch (NullPointerException e) {	
		}
			
		try	{	
		propertiesMap.put("Security Critial", getSecurityCritical().getName());
		} catch (NullPointerException e) {	
		}
		
		try	{	
		propertiesMap.put("Time", getTime().toString());
		} catch (NullPointerException e) {	
		}
		
		try {
		propertiesMap.put("Time Specificaiton", getTimeSpecification().getName());
		} catch (NullPointerException e) {	
		}
		
		try {
		propertiesMap.put("Runtime", getRuntime().getName());
		} catch (NullPointerException e) {
		}
		
		return propertiesMap;
	}

	/**
	 * 
	 * @return FFunctionalScope: powertrain, driverAssistance, interior, telematics,
	 *         crossfunctional
	 */
	public FFunctionalScope getFunctionalScope() {
		return getSpecialTypeFunctionalScope().getFunctionalScope();
	}

	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypeFunctionalScope() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getFunctionalScope() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return FBinding: static, dynamic
	 */
	public FBinding getBinding() {
		return getSpecialTypeBindings().getBinding();
	}
	
	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypeBindings() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getBinding() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return FSaftyCritical: ASIL_A, ASIL_B, ASIL_C, ASIL_D
	 */
	public FSaftyCritical getSaftyCritical() {
		return getSpecialTypeSaftyCritical().getSaftyCritical();
	}
	
	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypeSaftyCritical() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getSaftyCritical() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return FSecurityCritical: wireLevelSecurity, userAutentication,
	 *         serviceLevelSecurity
	 */
	public FSecurityCritical getSecurityCritical() {
		return getSpecialTypeSecurityCritical().getSecurityCritical();
	}
	
	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypeSecurityCritical() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getSecurityCritical() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return FTimeSpecification: ns, nss, ms, s
	 */
	public FTimeSpecification getTimeSpecification() {
		return getSpecialTypTimeSpecification().getTimeSpecification();
	}
	
	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypTimeSpecification() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getTimeSpecification() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return Time as Integer
	 */
	public Integer getTime() {
		return getSpecialTypTime().getTime();
	}
	
	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypTime() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getTime() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return FRuntime: onboard, offboard
	 */
	public FRuntime getRuntime() {
		return getSpecialTypRunTime().getRuntime();
	}
	
	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypRunTime() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getRuntime() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

}
