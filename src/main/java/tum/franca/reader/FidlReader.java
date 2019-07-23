package tum.franca.reader;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.franca.FArgument;
import org.franca.core.franca.FAttribute;
import org.franca.core.franca.FBroadcast;
import org.franca.core.franca.FConstantDef;
import org.franca.core.franca.FInterface;
import org.franca.core.franca.FMethod;
import org.franca.core.franca.FModel;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;
import org.franca.core.franca.FTypeCollection;
import org.franca.core.franca.Import;

public class FidlReader extends FidlModel  {
	
	PropertiesReader propertiesReader;
	
	public FidlReader(URI uri) {
		super(uri);
		this.propertiesReader = new PropertiesReader(uri);
	}
	
	public FModel getFModel() {
		return fmodel;
	}

	public PropertiesReader getPropertiesReader() {
		return propertiesReader;
	}

	public void setPropertiesReader(PropertiesReader propertiesReader) {
		this.propertiesReader = propertiesReader;
	}

	public String getFirstInterfaceName() {
		return fmodel.getInterfaces().get(0).getName();
	}
	
	public EList<FProvides> getFirstProvides() {
		return fmodel.getInterfaces().get(0).getProvided();
	}
	
	public EList<FRequires> getFirstRequires() {
		return fmodel.getInterfaces().get(0).getRequired();
	}
	
	
	/**
	 * Get name of FModel.
	 * 
	 * @return String
	 */
	public String getName() {
		return fmodel.getName();
	}
	
	/***************************************************
	 * Get Imports
	 */
	public EList<Import> getImports() {
		return fmodel.getImports();
	}
	
	public EList<FInterface> getInterfaces() {
		return fmodel.getInterfaces();
	}
	
	public EList<FAttribute> getAttributes(FInterface fInterface) {
		return fInterface.getAttributes();
	}
	
	public EList<FMethod> getMethods(FInterface finterface) {
		return finterface.getMethods();
	}
	
	public EList<FProvides> getProvides(FInterface fprovides) {
		return fprovides.getProvided();
	}
	
	public EList<FArgument> getInMethods(FMethod fMethod){
		return fMethod.getInArgs();
	}
	
	public EList<FArgument> getOutMethods(FMethod fMethod){
		return fMethod.getOutArgs();
	}
	
	public EList<FBroadcast> getBroadcasts(FInterface fInterface) {
		return fInterface.getBroadcasts();
	}
		
	public EList<FTypeCollection> getTypeCollections() {
		return fmodel.getTypeCollections();
	}
	
	public EList<FConstantDef> getConstants(FTypeCollection fTypeCollection) {
		return fTypeCollection.getConstants();
	}
	
	/***************************************************
	 * Print Methods
	 */
	public void printImport(Import import1) {
		System.out.println("Import: " + import1.getImportedNamespace());
		System.out.println("Import: " + import1.getImportURI());
	}
	
	public void printInterface(FInterface fInterface) {
		System.out.println("*********************");
		if (fInterface.getVersion() != null) {
			System.out.println("Version major: " + fInterface.getVersion().getMajor());
			System.out.println("Version minor: " + fInterface.getVersion().getMinor());
		}
		System.out.println("Interface: " + fInterface.getName());
	}
	
	public void printProvides(FProvides fProvides) {
		System.out.println("FProvides: " + fProvides.getProvidedImport());
	}
	
	public void printAttributes(FAttribute fAttribute) {
		System.out.println("Attribute: " + fAttribute.getName());
	}
	
	public void printMethod(FMethod fMethod) {
		System.out.println("Methode: " + fMethod.getName());
	}
	
	public void printBroadcasts(FBroadcast fBroadcast) {
		System.out.println("Broadcast: " + fBroadcast.getName());
	}
	
	public void printTypeCollections(FTypeCollection typeCollection) {
		System.out.println("Type Collection: " + typeCollection.getName());
	}
	
	/***************************************************
	 * Print complete FModel
	 */
	public void printFModel() {
		EList<Import> listImport = getImports();
		for (Import import1 : listImport) {
			printImport(import1);
		}
		
		EList<FInterface> listInterface = getInterfaces();
		for (FInterface fInterface : listInterface) {
			printInterface(fInterface);
			
			EList<FAttribute> listAttribute = getAttributes(fInterface);
			for (FAttribute fAttribute : listAttribute) {
				printAttributes(fAttribute);
			}
			
			EList<FProvides> listProvides = getProvides(fInterface);
			for (FProvides fProvide : listProvides) {
				printProvides(fProvide);
			}
			
			EList<FMethod> listMethod = getMethods(fInterface);
			for (FMethod fMethod : listMethod) {
				printMethod(fMethod);
				
				EList<FArgument> listArgumentIn = getInMethods(fMethod);
				for (FArgument fArgument : listArgumentIn) {
					System.out.println("In: " + fArgument.getName());
				}
				
				EList<FArgument> listArgumentOut = getOutMethods(fMethod);
				for (FArgument fArgument : listArgumentOut) {
					System.out.println("Out: " + fArgument.getName());
				}
				
			}
			
			EList<FBroadcast> listBroadcast = getBroadcasts(fInterface);
			for (FBroadcast fBroadcast : listBroadcast) {
				printBroadcasts(fBroadcast);
			}
		}
		
		EList<FTypeCollection> listFTypeCollection = getTypeCollections(); 
			for (FTypeCollection fTypeCollection : listFTypeCollection) {
				printTypeCollections(fTypeCollection);
			}
		}
		
	}
	

