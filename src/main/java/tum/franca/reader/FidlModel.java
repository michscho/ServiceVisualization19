package tum.franca.reader;

import org.eclipse.emf.common.util.URI;
import org.franca.core.dsl.FrancaIDLHelpers;
import org.franca.core.franca.FModel;

@SuppressWarnings("deprecation")
public abstract class FidlModel {
	
	protected URI uri;
	
	public FidlModel(URI uri) {
		this.uri = uri;
		this.fmodel = FrancaIDLHelpers.instance().loadModel(uri, uri);
	}

	protected FModel fmodel;

}
