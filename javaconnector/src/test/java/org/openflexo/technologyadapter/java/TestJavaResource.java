package org.openflexo.technologyadapter.java;

import java.util.Collection;

import org.junit.Assume;
import org.junit.Test;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.technologyadapter.java.rm.JAVAResourceRepository;

public class TestJavaResource extends OpenflexoTestCase {

	private static JAVATechnologyAdapter adapter;
	private static FlexoServiceManager applicationContext;

	@Test
	public void testInitializeServiceManager() throws Exception {
		applicationContext = instanciateTestServiceManager();
		adapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(JAVATechnologyAdapter.class);
		resourceCenter = (DirectoryResourceCenter) applicationContext.getResourceCenterService().getResourceCenters().get(0);
		Assume.assumeNotNull(applicationContext, adapter, resourceCenter);

		JAVAResourceRepository javaResourceRepository = resourceCenter.getRepository(JAVAResourceRepository.class, adapter);
		Collection<JAVAResource> javaResources = javaResourceRepository.getAllResources();

	}

}
