package tum.franca.reader;

public class Properties {

	enum Binding {
		staticBinding("static"), dynamicBinding("dynamic");
		private final String name;
		private Binding(String s) {
			name = s;
		}
	}

	enum FunctionalScope {
		powertrain,
		driverAssistance,
		interior,
		telematics,
		crossfunctional
	}
	
	enum HardwareDependend {
		isTrue,
		isFalse,
	}
	
	enum Runtime {
		
	}
	
	enum TimeSpecification {
		
	}
	
	enum SecurityCritical {
		
	}
	
	enum SaftyCritical {
		
	}


}
