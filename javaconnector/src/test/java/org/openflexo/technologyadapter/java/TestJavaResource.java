package org.openflexo.technologyadapter.java;

import java.io.FileNotFoundException;
import java.util.Collection;

import org.junit.Assume;
import org.junit.Test;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.technologyadapter.java.rm.JAVAResourceRepository;

public class TestJavaResource extends OpenflexoTestCase {

	private ModelFactory factory;


	private static JAVATechnologyAdapter adapter;
	private static FlexoServiceManager applicationContext;

	@Test
	public void testInitializeServiceManager() throws Exception {
		applicationContext = instanciateTestServiceManager();
		adapter = serviceManager.getTechnologyAdapterService()
				.getTechnologyAdapter(JAVATechnologyAdapter.class);
		resourceCenter = (DirectoryResourceCenter) applicationContext
				.getResourceCenterService().getResourceCenters().get(0);
		Assume.assumeNotNull(applicationContext, adapter, resourceCenter);

		try {
			this.factory = new ModelFactory(JAVAFolderModel.class);
		} catch (final ModelDefinitionException e) {
			System.out.println("error creating factory");
		}

		JAVAResourceRepository javaResourceRepository = resourceCenter
				.getRepository(JAVAResourceRepository.class, adapter);
		Collection<JAVAResource> javaResources = javaResourceRepository
				.getAllResources();

		for (JAVAResource javaResource : javaResources) {
			try {
				JAVAFolderModel javaFolder = javaResource
						.loadResourceData(null);
				System.out.println(javaFolder.getFolderModel().getName()
						+ "  folder : "
						+ javaFolder.getChildrenFolders().size()
						+ "  file : "
						+ javaFolder.getChildrenFiles().size()
						);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceLoadingCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FlexoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
