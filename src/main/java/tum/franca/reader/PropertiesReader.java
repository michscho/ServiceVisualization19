package tum.franca.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.franca.FBinding;
import org.franca.core.franca.FFunctionalScope;
import org.franca.core.franca.FRuntime;
import org.franca.core.franca.FSaftyCritical;
import org.franca.core.franca.FSecurityCritical;
import org.franca.core.franca.FTimeSpecification;
import org.franca.core.utils.ModelPersistenceHandler;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tum.franca.properties.PropertiesWrapper;
import tum.franca.properties.PropertiesWrapper.Properties;
import tum.franca.properties.PropertiesWrapper.Properties.BINDING;

/**
 * 
 * @author michaelschott
 *
 */
public class PropertiesReader extends InterfaceReader {

	public Properties.BINDING binding;
	public Properties.FUNCTIONALSCOPE functinoalScope;
	public Properties.RUNTIME runtime;
	public Properties.SAFTYCRITICAL saftyCritical;
	public Properties.SECURITYCRITICAL securityCritical;
	public Properties.TIMESPECIFICATION timeSpecification;

	public PropertiesReader(URI uri) {
		super(uri);
		binding = getBindingProperties();
		functinoalScope = getFunctionalScopeProperties();
		runtime = getRuntimeProperties();
		saftyCritical = getSaftyCriticalProperties();
		securityCritical = getSecurityCriticalProperties();
		timeSpecification = getTimeSpecifiactionProperties();
	}

	public void setProperty(String group, String property) {
		System.out.println("PROPERTY" + property + " " + group);
		FrancaPersistenceManager fPM = new FrancaPersistenceManager();
		switch (group) {

		// BINDING
		case "binding":

			switch (property) {

			case "static":
				getFirstInterface().setBinding(FBinding.STATIC);
				break;

			case "dynamic":
				getFirstInterface().setBinding(FBinding.DYNAMIC);
				break;

			default:
				getFirstInterface().setBinding(FBinding.NOT_DEFINED);
				break;
			}

			break;

		// FUNCTIONAL SCOPE
		case "functionalscope":

			switch (property) {
			case "powertrain":
				getFirstInterface().setFunctionalScope(FFunctionalScope.POWERTRAIN);
				break;

			case "driverAssistance":
				getFirstInterface().setFunctionalScope(FFunctionalScope.DRIVER_ASSISTANCE);
				break;

			case "interior":
				getFirstInterface().setFunctionalScope(FFunctionalScope.INTERIOR);
				break;

			case "telematics":
				getFirstInterface().setFunctionalScope(FFunctionalScope.TELEMATICS);
				break;

			case "crossfunctional":
				getFirstInterface().setFunctionalScope(FFunctionalScope.CROSSFUNCTIONAL);
				break;

			default:
				getFirstInterface().setFunctionalScope(FFunctionalScope.NOT_DEFINED);
				break;
			}

			break;

		// RUNTIME
		case "runtime":

			switch (property) {
			case "onboard":
				getFirstInterface().setRuntime(FRuntime.ONBOARD);
				break;

			case "offboard":
				getFirstInterface().setRuntime(FRuntime.OFFBOARD);
				break;

			default:
				getFirstInterface().setRuntime(FRuntime.NOT_DEFINED);
				break;
			}

			break;

		// SAFTYCRITICAL
		case "saftyCritical":

			switch (property) {
			case "ASIL_A":
				getFirstInterface().setSaftyCritical(FSaftyCritical.ASIL_A);
				break;

			case "ASIL_B":
				getFirstInterface().setSaftyCritical(FSaftyCritical.ASIL_B);
				break;

			case "ASIL_C":
				getFirstInterface().setSaftyCritical(FSaftyCritical.ASIL_C);
				break;

			case "ASIL_D":
				getFirstInterface().setSaftyCritical(FSaftyCritical.ASIL_D);
				break;

			default:
				getFirstInterface().setSaftyCritical(FSaftyCritical.NOT_DEFINED);
				break;
			}

			break;

		// SECURTIYCRITICAL
		case "securityCritical":

			switch (property) {
			case "servicelevelsecurity":
				getFirstInterface().setSecurityCritical(FSecurityCritical.SERVICE_LEVEL_SECURITY);
				break;

			case "userauthentication":
				getFirstInterface().setSecurityCritical(FSecurityCritical.USER_AUTHENTICATION);
				break;

			case "wirelevelsecurity":
				getFirstInterface().setSecurityCritical(FSecurityCritical.WIRE_LEVEL_SECURITY);
				break;

			default:
				getFirstInterface().setSecurityCritical(FSecurityCritical.NOT_DEFINED);
				break;
			}

			break;

		// TIME SPECIFICATION
		case "timeSpecification":

			switch (property) {
			case "ms":
				getFirstInterface().setTimeSpecification(FTimeSpecification.MS);
				break;

			case "ns":
				getFirstInterface().setTimeSpecification(FTimeSpecification.NS);
				break;

			case "nss":
				getFirstInterface().setTimeSpecification(FTimeSpecification.NSS);
				break;

			case "s":
				getFirstInterface().setTimeSpecification(FTimeSpecification.S);
				break;

			default:
				getFirstInterface().setTimeSpecification(FTimeSpecification.NOT_DEFINED);
				break;
			}

			break;
		default:
			throw new NotImplementedException();
		}
		fPM.saveModel(fmodel, uri.toString());
	}

	public HashMap<String, String> propertiesMap = new HashMap<String, String>();

	public List<String> getProperties() {
		List<String> list = new ArrayList<String>();
		list.add(getBinding().getName());
		list.add(getFunctionalScope().getName());
		list.add(getRuntime().getName());
		list.add(getSaftyCritical().getName());
		list.add(getSecurityCritical().getName());
		list.add(getTimeSpecification().getName());
		return list;
	}

	public HashMap<String, String> getAllStringPropertiesWithoutNotDefinedOnes() {
		if (getFunctionalScope().getName() != "notDefined") {
			propertiesMap.put("Functional Scope", getFunctionalScope().getName());
		}
		if (getBinding().getName() != "notDefined") {
			propertiesMap.put("Binding", getBinding().getLiteral());
		}
		if (getSaftyCritical().getName() != "notDefined") {
			propertiesMap.put("Safty Critical", getSaftyCritical().getName());
		}
		if (getSecurityCritical().getName() != "notDefined") {
			propertiesMap.put("Security Critial", getSecurityCritical().getName());
		}
		if (getTime() != 0) {
			propertiesMap.put("Time", getTime().toString());
		}
		if (getTimeSpecification().getName() != "notDefined") {
			propertiesMap.put("Time Specification", getTimeSpecification().getName());
		}
		if (getRuntime().getName() != "notDefined") {
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

	public Properties.FUNCTIONALSCOPE getFunctionalScopeProperties() {
		return PropertiesWrapper.functionaScopeHashMap.get(getFirstInterface().getFunctionalScope().getName());
	}

	/**
	 * 
	 * @return FBinding: static, dynamic
	 */
	public FBinding getBinding() {
		return getFirstInterface().getBinding();
	}

	public Properties.BINDING getBindingProperties() {
		return PropertiesWrapper.bindingHashMap.get(getFirstInterface().getBinding().getName());
	}

	/**
	 * 
	 * @return FSaftyCritical: ASIL_A, ASIL_B, ASIL_C, ASIL_D
	 */
	public FSaftyCritical getSaftyCritical() {
		return getFirstInterface().getSaftyCritical();
	}

	public Properties.SAFTYCRITICAL getSaftyCriticalProperties() {
		return PropertiesWrapper.saftyCriticalHashMap.get(getFirstInterface().getSaftyCritical().getName());
	}

	/**
	 * 
	 * @return FSecurityCritical: wireLevelSecurity, userAutentication,
	 *         serviceLevelSecurity
	 */
	public FSecurityCritical getSecurityCritical() {
		return getFirstInterface().getSecurityCritical();
	}

	public Properties.SECURITYCRITICAL getSecurityCriticalProperties() {
		return PropertiesWrapper.securityCriticalHashMap.get(getFirstInterface().getSecurityCritical().getName());
	}

	/**
	 * 
	 * @return FTimeSpecification: ns, nss, ms, s
	 */
	public FTimeSpecification getTimeSpecification() {
		return getFirstInterface().getTimeSpecification();
	}

	public Properties.TIMESPECIFICATION getTimeSpecifiactionProperties() {
		return PropertiesWrapper.timeSpecificationHashMap.get(getFirstInterface().getTimeSpecification().getName());
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

	public Properties.RUNTIME getRuntimeProperties() {
		return PropertiesWrapper.runtimeHashMap.get(getFirstInterface().getRuntime().getName());
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getHardwareDependend() {
		return getFirstInterface().isHardwareDependend();
	}

}
