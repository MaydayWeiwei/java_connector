package org.openflexo.technologyadapter.java;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import org.junit.Assume;
import org.junit.Test;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.model.JAVAFieldModel;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.model.JAVAMethodModel;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.technologyadapter.java.rm.JAVAResourceRepository;

public class TestJavaResource extends OpenflexoTestCase {


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

		JAVAResourceRepository javaResourceRepository = resourceCenter
				.getRepository(JAVAResourceRepository.class, adapter);
		Collection<JAVAResource> javaResources = javaResourceRepository
				.getAllResources();

		for (JAVAResource javaResource : javaResources) {
			try {
				JAVAFolderModel javaFolder = javaResource
						.loadResourceData(null);
				List<JAVAFileModel> javaFiles = javaFolder.getChildrenFiles();
				for(JAVAFileModel file : javaFiles) {
					System.out.println();
					System.out.println(file.getRootClass().getName()+".class");
					for(JAVAFieldModel field : file.getRootClass().getFields()) {
						System.out.println(field.getName()+".field");
					}
					for(JAVAMethodModel method : file.getRootClass().getMethods()) {
						System.out.println(method.getName()+".method");
					}
				}
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
