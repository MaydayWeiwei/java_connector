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

package org.openflexo.technologyadapter.java.model;

import java.io.File;
import java.util.List;

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

@ModelEntity
@ImplementationClass(value = JAVAFolderModelImpl.class)
public interface JAVAFolderModel extends
		TechnologyObject<JAVATechnologyAdapter>, ResourceData<JAVAFolderModel> {

	public static final String MODEL_ITEM_KEY = "javaFolder";
	public static final String PARENT_ITEM_KEY = "javaParentFolder";
	public static final String CHILDREN_FOLDER_KEY = "javaChildrenFolders";
	public static final String CHILDREN_FILE_KEY = "javaChildrenFiles";
	public static final String MODEL_NAME_KEY = "javaFolderName";

	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public File getFolderModel();

	@Setter(value = MODEL_ITEM_KEY)
	public void setFolderModel(File folder);

	@Getter(value = PARENT_ITEM_KEY, ignoreType = true)
	public JAVAFolderModel getFatherFolder();

	@Setter(value = PARENT_ITEM_KEY)
	public void setFatherFolder(JAVAFolderModel fatherFolder);

	@Getter(value = CHILDREN_FOLDER_KEY, ignoreType = true)
	public List<JAVAFolderModel> getChildrenFolders();

	@Setter(value = CHILDREN_FOLDER_KEY)
	public void setChildrenFolders(List<JAVAFolderModel> childrenFolders);

	@Getter(value = CHILDREN_FILE_KEY, ignoreType = true)
	public List<JAVAFileModel> getChildrenFiles();

	@Setter(value = CHILDREN_FILE_KEY)
	public void setChildrenFiles(List<JAVAFileModel> childrenFiles);

	public String getName();

}
