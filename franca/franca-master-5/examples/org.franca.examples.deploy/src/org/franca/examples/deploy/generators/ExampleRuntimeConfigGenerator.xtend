/*******************************************************************************
 * Copyright (c) 2013 itemis AG (http://www.itemis.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.franca.examples.deploy.generators

import org.example.spec.IPBasedIPC.Enums.AccessControl
import org.example.spec.SampleDeploySpec.ProviderPropertyAccessor
import org.franca.deploymodel.dsl.fDeploy.FDExtensionRoot
import org.franca.deploymodel.ext.providers.FDeployedProvider

import static extension org.franca.deploymodel.ext.providers.ProviderUtils.*

/**
 * This is an example code generator for the non-interface related part of 
 * Franca deployment models. It will generate a mimic configuration file
 * from the definitions of providers and interface instances in an *.fdepl file.
 * This class shows how to access the deployment information from the input model. 
 */
class ExampleRuntimeConfigGenerator {

	/**
	 * An instance of the ProviderPropertyAccessor for SampleDeploySpec-models
	 * (it has been generated automatically from SampleDeploySpec.fdepl).
	 */
	ProviderPropertyAccessor deploy

	/**
	 * This function is called from outside and generates code from a 
	 * Franca provider definition, which contains deployment information.
	 * The FDeployedProvider instance is a wrapper which is used to 
	 * instantiate a SampleDeploySpecProviderPropertyAccessor.
	 * This ProviderPropertyAccessor will be used to read the deployment
	 * information stored for this provider.
	 */
	def generateRuntimeConfig(FDeployedProvider deployed) {
		deploy = new ProviderPropertyAccessor(deployed)
		generateProvider(deployed.provider)	
	}


	/**
	 * This function generates the configuration file from a FDProvider
	 * definition. The PropertyAccessor ("deploy") is used for extracting
	 * the deployment information in a type-safe way easily. 
	 */
	def generateProvider(FDExtensionRoot provider) '''
		RUNTIME CONFIGURATION FOR PROVIDER: �provider.name�
		
		Process name: �deploy.getProcessName(provider)�
		
		Provided interfaces:
		�FOR inst : provider.instances�
		- instance of �inst.targetInterface.name�
		  - address: �deploy.getIPAddress(inst)�:�deploy.getPort(inst)�
		  - access:  �deploy.getAccessControl(inst).generate�
		�ENDFOR�
		
		--- generated by ExampleRuntimeConfigGenerator.
	'''
		
	
	// ***********************************************************************************
	// helper functions

	def generate (AccessControl ac) {
		switch (ac) {
			case AccessControl::local:  "local clients only"
			case AccessControl::subnet: "clients in same subnet only"
			case AccessControl::global: "all clients"
			default: "unknown"
		}
	}

}