package tum.franca.reader;

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

	/**
	 * 
	 * @return FFunctionalScope: powertrain, driverAssistance, interior, telematics,
	 *         crossfunctional
	 */
	public FFunctionalScope getFunctionalScope() {
		return getSpecialTypeFS().getFunctionalScope();
	}

	/**
	 * 
	 * @return FSpecialType
	 */
	private FSpecialType getSpecialTypeFS() {
		return getFirstInterface().getSpecialType().stream().filter(type -> type.getFunctionalScope() != null)
				.findFirst().orElseThrow(() -> new NullPointerException());
	}

	/**
	 * 
	 * @return FBinding: static, dynamic
	 */
	public FBinding getBinding() {
		return null;
	}

	/**
	 * 
	 * @return FSaftyCritical: ASIL_A, ASIL_B, ASIL_C, ASIL_D
	 */
	public FSaftyCritical getSaftyCritical() {
		return null;
	}

	/**
	 * 
	 * @return FSecurityCritical: wireLevelSecurity, userAutentication,
	 *         serviceLevelSecurity
	 */
	public FSecurityCritical getSecurityCritical() {
		return null;
	}

	/**
	 * 
	 * @return FTimeSpecification: ns, nss, ms, s
	 */
	public FTimeSpecification getTimeSpecification() {
		return null;
	}

	/**
	 * 
	 * @return Time as Integer
	 */
	public Integer getTime() {
		return null;
	}

	/**
	 * 
	 * @return FRuntime: onboard, offboard
	 */
	public FRuntime getRuntime() {
		return null;
	}

}
