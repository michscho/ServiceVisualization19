package tum.franca.reader;

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
	
	/**
	 * 
	 * @return FFunctionalScope: powertrain, driverAssistance, interior, telematics, crossfunctional
	 */
	public FFunctionalScope getFunctionalScope() {
		return getFirstAttributes().get(0).getType().getSpecialType().getFunctionalScope();
	}
	
	/**
	 * 
	 * @return FBinding: static, dynamic
	 */
	public FBinding getBinding() {
		return getFirstAttributes().get(0).getType().getSpecialType().getBinding();
	}
	
	/**
	 * 
	 * @return FSaftyCritical: ASIL_A, ASIL_B, ASIL_C, ASIL_D
	 */
	public FSaftyCritical getSaftyCritical() {
		return getFirstAttributes().get(0).getType().getSpecialType().getSaftyCritical();
	}
	
	/**
	 * 
	 * @return FSecurityCritical: wireLevelSecurity, userAutentication, serviceLevelSecurity
	 */
	public FSecurityCritical getSecurityCritical() {
		return getFirstAttributes().get(0).getType().getSpecialType().getSecurityCritical();
	}
	
	/**
	 * 
	 * @return FTimeSpecification: ns, nss, ms, s
	 */
	public FTimeSpecification getTimeSpecification() {
		return getFirstAttributes().get(0).getType().getSpecialType().getTimeSpecification();
	}
	
	/**
	 * 
	 * @return Time as Integer
	 */
	public Integer getTime() {
		return getFirstAttributes().get(0).getType().getSpecialType().getTime();
	}
	
	/**
	 * 
	 * @return FRuntime: onboard, offboard
	 */
	public FRuntime getRuntime() {
		return getFirstAttributes().get(0).getType().getSpecialType().getRuntime();
	}
	
}
