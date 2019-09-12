package tum.franca.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.franca.core.dsl.FrancaIDLHelpers;
import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.dsl.serializer.AbstractFrancaIDLSemanticSequencer;
import org.franca.core.franca.FBinding;
import org.franca.core.franca.FFunctionalScope;
import org.franca.core.franca.FHardwareDependend;
import org.franca.core.franca.FRuntime;
import org.franca.core.franca.FSafetyCritical;
import org.franca.core.franca.FSecurityCritical;
import org.franca.core.franca.FTimeSpecification;
import org.franca.core.franca.FrancaFactory;
import org.franca.core.franca.impl.ImportImpl;
import org.franca.core.utils.FrancaModelCreator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;
import tum.franca.properties.PropertiesWrapper;
import tum.franca.properties.PropertiesWrapper.Properties;

/**
 * 
 * @author michaelschott
 *
 */
public class PropertiesReader extends InterfaceReader {

	public Properties.BINDING binding;
	public Properties.FUNCTIONALSCOPE functinoalScope;
	public Properties.RUNTIME runtime;
	public Properties.SAFETYCRITICAL safetyCritical;
	public Properties.SECURITYCRITICAL securityCritical;
	public Properties.TIMESPECIFICATION timeSpecification;

	public PropertiesReader(URI uri) {
		super(uri);
		binding = getBindingProperties();
		functinoalScope = getFunctionalScopeProperties();
		runtime = getRuntimeProperties();
		safetyCritical = getSafetyCriticalProperties();
		securityCritical = getSecurityCriticalProperties();
		timeSpecification = getTimeSpecifiactionProperties();
	}

	public void setInterfaceName(String string) {
		getFirstInterface().setName(string);
		FrancaPersistenceManager fPM = new FrancaPersistenceManager();
		fPM.saveModel(fmodel, uri.toString());
	}
	
	public void setEdges() {
		FrancaFactory.eINSTANCE.createImport();
		
	}

	public void setProperty(String group, String property) {
		group = group.toLowerCase();
		property = property.toLowerCase();
		System.out.println("PROPERTY: " + group + " " + property);
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
			
		// Hardware Dependend
		case "hardwaredependend":
			
			switch (property) {
			case "ishardwaredependend":
				getFirstInterface().setHardwareDependend(FHardwareDependend.IS_HARDWARE_DEPENDEND);
				break;
				
			case "isnothardwaredepend":
				getFirstInterface().setHardwareDependend(FHardwareDependend.IS_NOT_HARDWARE_DEPENDEND);
				break;
				
			default:
				getFirstInterface().setHardwareDependend(FHardwareDependend.NOT_DEFINED);
				break;
				
			}

		// FUNCTIONAL SCOPE
		case "functionalscope":

			switch (property) {
			case "powertrain":
				getFirstInterface().setFunctionalScope(FFunctionalScope.POWERTRAIN);
				break;

			case "bodyandconfort":
				getFirstInterface().setFunctionalScope(FFunctionalScope.BODY_AND_CONFORT);
				break;

			case "chassisanddriverassistance":
				getFirstInterface().setFunctionalScope(FFunctionalScope.CHASSIS_AND_DRIVER_ASSISTANCE);
				break;

			case "humanmachineinterface":
				getFirstInterface().setFunctionalScope(FFunctionalScope.HUMAN_MACHINE_INTERFACE);
				break;

			case "crossfunctional":
				getFirstInterface().setFunctionalScope(FFunctionalScope.CROSSFUNCTIONAL);
				break;

			default:
				getFirstInterface().setFunctionalScope(FFunctionalScope.NOT_DEFINED);
				break;
			}

			break;
			
		case "functional":

			switch (property) {
			case "powertrain":
				getFirstInterface().setFunctionalScope(FFunctionalScope.POWERTRAIN);
				break;

			case "body_and_confort":
				getFirstInterface().setFunctionalScope(FFunctionalScope.BODY_AND_CONFORT);
				break;

			case "chassisanddriverassistance":
				getFirstInterface().setFunctionalScope(FFunctionalScope.CHASSIS_AND_DRIVER_ASSISTANCE);
				break;

			case "humanmachineinterface":
				getFirstInterface().setFunctionalScope(FFunctionalScope.HUMAN_MACHINE_INTERFACE);
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

		// SAFETYCRITICAL
		case "safetycritical":

			switch (property) {
			case "qm":
				getFirstInterface().setSafetyCritical(FSafetyCritical.QM);
				break;
			case "asil_a":
				getFirstInterface().setSafetyCritical(FSafetyCritical.ASIL_A);
				break;

			case "asil_b":
				getFirstInterface().setSafetyCritical(FSafetyCritical.ASIL_B);
				break;

			case "asil_c":
				getFirstInterface().setSafetyCritical(FSafetyCritical.ASIL_C);
				break;

			case "asil_d":
				getFirstInterface().setSafetyCritical(FSafetyCritical.ASIL_D);
				break;

			default:
				getFirstInterface().setSafetyCritical(FSafetyCritical.NOT_DEFINED);
				break;
			}

			break;

		// SECURTIYCRITICAL
		case "securitycritical":

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
		case "timespecification":

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
		list.add(getHardwareDependend().getName());
		list.add(getRuntime().getName());
		list.add(getSafetyCritical().getName());
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
		if (getSafetyCritical().getName() != "notDefined") {
			propertiesMap.put("Safety Critical", getSafetyCritical().getName());
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
		if (getHardwareDependend().getName()!= "notDefined") {
			propertiesMap.put("HardwareDependend", getRuntime().getName());
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
		System.out.println(getFirstInterface().getBinding().getName());
		return PropertiesWrapper.bindingHashMap.get(getFirstInterface().getBinding().getName());
	}

	/**
	 * 
	 * @return FSafetyCritical: ASIL_A, ASIL_B, ASIL_C, ASIL_D
	 */
	public FSafetyCritical getSafetyCritical() {
		return getFirstInterface().getSafetyCritical();
	}

	public Properties.SAFETYCRITICAL getSafetyCriticalProperties() {
		return PropertiesWrapper.safetyCriticalHashMap.get(getFirstInterface().getSafetyCritical().getName());
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
	 * @return FHardwareDependend: isHardwareDepenend, isNotHardwareDependend
	 */
	public FHardwareDependend getHardwareDependend() {
		return getFirstInterface().getHardwareDependend();
	}

}
