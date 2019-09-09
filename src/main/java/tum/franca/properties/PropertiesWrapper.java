package tum.franca.properties;

import java.util.HashMap;

/**
 * 
 * @author michaelschott
 *
 */
public final class PropertiesWrapper {

	public final static String BINDING = "Binding";
	public final static String FUNCTIONALSCOPE = "Functional Scope";
	public final static String HARDWAREDENDEND = "Hardware Dependend";
	public final static String RUNTIME = "Runtime";
	public final static String TIMESPECIFICATION = "Time Specification";
	public final static String SAFETYCRITICAL = "Safety Critical";
	public final static String SECURITYCRITICAL = "Security Critical";
;
	public final static HashMap<String, Properties.BINDING> bindingHashMap = new HashMap<String, Properties.BINDING>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.BINDING.NOTDEFINED);
			put("static", Properties.BINDING.STATIC);
			put("dynamic", Properties.BINDING.DYNAMIC);
		}
	};
	public final static HashMap<String, Properties.FUNCTIONALSCOPE> functionaScopeHashMap = new HashMap<String, Properties.FUNCTIONALSCOPE>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.FUNCTIONALSCOPE.NOTDEFINED);
			put("powertrain", Properties.FUNCTIONALSCOPE.CROSSFUNCTIONAL);
			put("driverAssistance", Properties.FUNCTIONALSCOPE.BODYANDCONFORT);
			put("interior", Properties.FUNCTIONALSCOPE.HUMANMACHINEINTERFACE);
			put("telematics", Properties.FUNCTIONALSCOPE.CHASSISANDDRIVERASSISTANCE);
			put("crossfunctional", Properties.FUNCTIONALSCOPE.CROSSFUNCTIONAL);
		}
	};
	public final static HashMap<String, Properties.HARDWAREDEPENDEND> hardwareDependendHashMap = new HashMap<String, Properties.HARDWAREDEPENDEND>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.HARDWAREDEPENDEND.NOTDEFINED);
			put("isHardwareDependend", Properties.HARDWAREDEPENDEND.ISHARDWAREDEPENDEND);
			put("isNotHardwareDependend", Properties.HARDWAREDEPENDEND.ISNOTHARDWAREDEPENDEND);
		}
	};
	public final static HashMap<String, Properties.RUNTIME> runtimeHashMap = new HashMap<String, Properties.RUNTIME>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.RUNTIME.NOTDEFINED);
			put("offboard", Properties.RUNTIME.OFFBOARD);
			put("onboard", Properties.RUNTIME.ONBOARD);
		}
	};
	public final static HashMap<String, Properties.TIMESPECIFICATION> timeSpecificationHashMap = new HashMap<String, Properties.TIMESPECIFICATION>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.TIMESPECIFICATION.NOTDEFINED);
			put("ns", Properties.TIMESPECIFICATION.NS);
			put("nss", Properties.TIMESPECIFICATION.NSS);
			put("ms", Properties.TIMESPECIFICATION.MS);
			put("s", Properties.TIMESPECIFICATION.S);
		}
	};
	public final static HashMap<String, Properties.SAFETYCRITICAL> safetyCriticalHashMap = new HashMap<String, Properties.SAFETYCRITICAL>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.SAFETYCRITICAL.NOTDEFINED);
			put("QM", Properties.SAFETYCRITICAL.QM);
			put("ASIL_A", Properties.SAFETYCRITICAL.ASIL_A);
			put("ASIL_B", Properties.SAFETYCRITICAL.ASIL_B);
			put("ASIL_C", Properties.SAFETYCRITICAL.ASIL_C);
			put("ASIL_D", Properties.SAFETYCRITICAL.ASIL_D);
		}
	};
	public final static HashMap<String, Properties.SECURITYCRITICAL> securityCriticalHashMap = new HashMap<String, Properties.SECURITYCRITICAL>() {
		private static final long serialVersionUID = 1L;
		{
			put("notDefined", Properties.SECURITYCRITICAL.NOTDEFINED);
			put("serviceLevelSecurity", Properties.SECURITYCRITICAL.SERVICELEVELSECURITY);
			put("userAutehntication", Properties.SECURITYCRITICAL.USERAUTHENTICATION);
			put("wirelevelsecurity", Properties.SECURITYCRITICAL.WIRELEVELSECURITY);
		}
	};

	public abstract static class Properties {

		public enum BINDING {
			NOTDEFINED("notDefined"), STATIC("static"), DYNAMIC("dynamic");

			private String string;

			private BINDING(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};

		public enum FUNCTIONALSCOPE {
			NOTDEFINED("notDefined"), POWERTRAIN("powertrain"), BODYANDCONFORT("body_And_Confort"),
			CHASSISANDDRIVERASSISTANCE("chassisAndDriverAssistance"), HUMANMACHINEINTERFACE("humanMachineInterface"), CROSSFUNCTIONAL("crossfunctional");
			private String string;

			private FUNCTIONALSCOPE(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};

		public enum HARDWAREDEPENDEND {
			NOTDEFINED("notDefined"), ISHARDWAREDEPENDEND("ishardwaredependend"), ISNOTHARDWAREDEPENDEND("isnothardwaredependend");
			private String string;

			private HARDWAREDEPENDEND(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};
		
		public enum RUNTIME {
			NOTDEFINED("notDefined"), ONBOARD("onboard"), OFFBOARD("offboard");
			private String string;

			private RUNTIME(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};

		public enum TIMESPECIFICATION {
			NOTDEFINED("notDefined"), NS("ns"), NSS("nss"), MS("ms"), S("s");
			private String string;

			private TIMESPECIFICATION(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};

		public enum SAFETYCRITICAL {
			NOTDEFINED("notDefined"), QM("QM"), ASIL_A("ASIL_A"), ASIL_B("ASIL_B"), ASIL_C("ASIL_C"), ASIL_D("ASIL_D");
			private String string;

			private SAFETYCRITICAL(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};

		public enum SECURITYCRITICAL {
			NOTDEFINED("notDefined"), WIRELEVELSECURITY("wirelevelSecurity"),
			USERAUTHENTICATION("userAuthentication"), SERVICELEVELSECURITY("serviceLevelSecurity");
			private String string;

			private SECURITYCRITICAL(String string) {
				this.string = string;
			}

			public String toString() {
				return string;
			}
		};
	}
}