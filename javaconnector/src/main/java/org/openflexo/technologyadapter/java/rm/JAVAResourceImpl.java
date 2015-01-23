/*
 * (c) Copyright 2013 Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.technologyadapter.java.rm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FileFlexoIODelegate;
import org.openflexo.foundation.resource.FileFlexoIODelegate.FileFlexoIODelegateImpl;
import org.openflexo.foundation.resource.FileWritingLock;
import org.openflexo.foundation.resource.FlexoResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.resource.SaveResourcePermissionDeniedException;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.JAVATechnologyContextManager;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModelImpl;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.toolbox.IProgress;

public abstract class JAVAResourceImpl extends
		FlexoResourceImpl<JAVAFolderModel> implements JAVAResource {

	private static final Logger LOGGER = Logger
			.getLogger(JAVAResourceImpl.class.getPackage().getName());

	private static ModelFactory MODEL_FACTORY;

	static {
		try {
			MODEL_FACTORY = new ModelFactory(JAVAFolderModel.class);
		} catch (final ModelDefinitionException e) {
			final String msg = "Error while initializing JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
		}
	}

	public static JAVAResource makeJAVAResource(String modelURI,
			File modelFile,
			JAVATechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(
							JAVAResource.class, FileFlexoIODelegate.class));
			JAVAResourceImpl returned = (JAVAResourceImpl) factory
					.newInstance(JAVAResource.class);
			returned.setName(modelFile.getName());
			returned.setFlexoIODelegate(FileFlexoIODelegateImpl
					.makeFileFlexoIODelegate(modelFile, factory));

			returned.setURI(modelURI);
			returned.setServiceManager(technologyContextManager
					.getTechnologyAdapter().getTechnologyAdapterService()
					.getServiceManager());
			returned.setTechnologyAdapter((JAVATechnologyAdapter) technologyContextManager
					.getTechnologyAdapter());
			returned.setTechnologyContextManager(technologyContextManager);
			technologyContextManager.registerResource(returned);

			return returned;
		} catch (ModelDefinitionException e) {
			final String msg = "Error while initializing JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
		}
		return null;
	}

	public static JAVAResource retrieveJAVAResource(File modelFile,
			JAVATechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(
							JAVAResource.class, FileFlexoIODelegate.class));
			JAVAResourceImpl returned = (JAVAResourceImpl) factory
					.newInstance(JAVAResource.class);
			returned.setName(modelFile.getName());
			returned.setFlexoIODelegate(FileFlexoIODelegateImpl
					.makeFileFlexoIODelegate(modelFile, factory));
			returned.setURI(modelFile.toURI().toString());
			returned.setServiceManager(technologyContextManager
					.getTechnologyAdapter().getTechnologyAdapterService()
					.getServiceManager());
			returned.setTechnologyAdapter((JAVATechnologyAdapter) technologyContextManager
					.getTechnologyAdapter());
			returned.setTechnologyContextManager(technologyContextManager);
			technologyContextManager.registerResource(returned);
			return returned;
		} catch (ModelDefinitionException e) {
			final String msg = "Error while initializing JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
		}
		return null;
	}

	@Override
	public JAVATechnologyAdapter getTechnologyAdapter() {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService()
					.getTechnologyAdapter(JAVATechnologyAdapter.class);
		}
		return null;
	}

	@Override
	public JAVAFolderModel loadResourceData(IProgress progress)
			throws ResourceLoadingCancelledException, FileNotFoundException,
			FlexoException {
		// TODO: Auto-generated Method
		final JAVAFolderModelImpl javaObject = (JAVAFolderModelImpl) MODEL_FACTORY
				.newInstance(JAVAFolderModel.class);
		javaObject.setFolderModel(((FileFlexoIODelegate) getFlexoIODelegate()).getFile());
		
		javaObject.setTechnologyAdapter(getTechnologyAdapter());
		javaObject.setResource(this);
		// Now you have to add here your parsing and call the correct set.
		return javaObject;
	}

	@Override
	public void save(IProgress progress) throws SaveResourceException {
		JAVAFolderModel resourceData = null;
		try {
			resourceData = getResourceData(progress);
		} catch (FileNotFoundException e) {
			final String msg = "Error while saving JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new SaveResourceException(getFlexoIODelegate());
		} catch (ResourceLoadingCancelledException e) {
			final String msg = "Error while saving JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new SaveResourceException(getFlexoIODelegate());
		} catch (FlexoException e) {
			final String msg = "Error while saving JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new SaveResourceException(getFlexoIODelegate());
		}

		if (!getFlexoIODelegate().hasWritePermission()) {
			if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.warning("Permission denied : "
						+ getFlexoIODelegate().toString());
			}
			throw new SaveResourcePermissionDeniedException(
					getFlexoIODelegate());
		}
		if (resourceData != null) {
			FileWritingLock lock = getFlexoIODelegate().willWriteOnDisk();
			writeToFile();
			getFlexoIODelegate().hasWrittenOnDisk(lock);
			notifyResourceStatusChanged();
			resourceData.clearIsModified(false);
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("Succeeding to save Resource " + getURI() + " : "
						+ getFlexoIODelegate().toString());
			}
		}
	}

	private void writeToFile() throws SaveResourceException {
		// TODO : Auto-generated method skeleton.
		FileOutputStream out = null;
		try {
			FileFlexoIODelegate delegate = (FileFlexoIODelegate) getFlexoIODelegate();
			out = new FileOutputStream(delegate.getFile());
		} catch (FileNotFoundException e) {
			final String msg = "Error while saving JAVA model resource";
			LOGGER.log(Level.SEVERE, msg, e);
			throw new SaveResourceException(getFlexoIODelegate());
		} finally {
			IOUtils.closeQuietly(out);
		}

		LOGGER.info("Wrote " + getFlexoIODelegate().toString());
	}

	@Override
	public Class<JAVAFolderModel> getResourceDataClass() {
		return JAVAFolderModel.class;
	}
}
