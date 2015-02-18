/*
 * (c) Copyright 2013- Openflexo
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

package org.openflexo.technologyadapter.java.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

/**
 * Abstract Simple implementation. Using Pamela.
 * 
 * @author SomeOne
 * 
 */
public abstract class JAVAFolderModelImpl extends FlexoObjectImpl implements
		JAVAFolderModel {

	private static final Logger LOGGER = Logger
			.getLogger(JAVAFolderModelImpl.class.getName());

	private JAVATechnologyAdapter technologyAdapter;

	private File folderModel;
	private JAVAFolderModel fatherFolder;
	private List<JAVAFolderModel> childrenFolders;
	private List<JAVAFileModel> childrenFiles;

	public JAVAFolderModelImpl() {
	}

	public void setTechnologyAdapter(JAVATechnologyAdapter technologyAdapter) {
		this.technologyAdapter = technologyAdapter;
	}

	@Override
	public JAVATechnologyAdapter getTechnologyAdapter() {
		return this.technologyAdapter;
	}

	@Override
	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public File getFolderModel() {
		return folderModel;
	}

	@Override
	@Setter(value = MODEL_ITEM_KEY)
	public void setFolderModel(File folderModel) {
		this.folderModel = folderModel;
		try {
			final List<JAVAFolderModel> childrenFolders = new ArrayList<JAVAFolderModel>();
			final List<JAVAFileModel> childrenFiles = new ArrayList<JAVAFileModel>();
			for (final File item : folderModel.listFiles()) {
				if (!item.isHidden()) {
					if (item.isDirectory()) {
						final ModelFactory factory = new ModelFactory(
								JAVAFolderModel.class);
						final JAVAFolderModelImpl child = (JAVAFolderModelImpl) factory
								.newInstance(JAVAFolderModel.class);
						child.setTechnologyAdapter(this.getTechnologyAdapter());
						child.setFatherFolder(this);
						child.setFolderModel(item);
						childrenFolders.add(child);
					} else {
						if (isValidJAVAFile(item)) {
							final ModelFactory factory = new ModelFactory(
									JAVAFileModel.class);
							final JAVAFileModelImpl child = (JAVAFileModelImpl) factory
									.newInstance(JAVAFileModel.class);
							child.setTechnologyAdapter(this
									.getTechnologyAdapter());
							child.setFatherFolder(this);
							child.setFileModel(item);
							childrenFiles.add(child);
						}
					}
				}
			}
			this.setChildrenFolders(childrenFolders);
			this.setChildrenFiles(childrenFiles);
		} catch (final ModelDefinitionException e) {
			final String msg = "Error while setting MapModel of map " + this;
			LOGGER.log(Level.SEVERE, msg, e);
		}
	}

	@Override
	@Getter(value = PARENT_ITEM_KEY, ignoreType = true)
	public JAVAFolderModel getFatherFolder() {
		return fatherFolder;
	}

	@Override
	@Setter(value = PARENT_ITEM_KEY)
	public void setFatherFolder(JAVAFolderModel fatherFolder) {
		this.fatherFolder = fatherFolder;
	}

	@Override
	@Getter(value = CHILDREN_FOLDER_KEY, ignoreType = true)
	public List<JAVAFolderModel> getChildrenFolders() {
		return childrenFolders;
	}

	@Override
	@Setter(value = CHILDREN_FOLDER_KEY)
	public void setChildrenFolders(List<JAVAFolderModel> childrenFolders) {
		this.childrenFolders = childrenFolders;
	}

	@Override
	@Getter(value = CHILDREN_FILE_KEY, ignoreType = true)
	public List<JAVAFileModel> getChildrenFiles() {
		return childrenFiles;
	}

	@Override
	@Setter(value = CHILDREN_FILE_KEY)
	public void setChildrenFiles(List<JAVAFileModel> childrenFiles) {
		this.childrenFiles = childrenFiles;
	}

	@Override
	public String getName() {
		return this.folderModel.getName();
	}

	private boolean isValidJAVAFile(File candidateFile) {
		String fileName = candidateFile.getName();
		return fileName.endsWith(".xml") || fileName.endsWith(".java")
				|| fileName.endsWith(".jsp");
	}

}
