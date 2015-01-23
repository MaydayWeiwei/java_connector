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

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

@ModelEntity
@ImplementationClass(value = JAVAFileModelImpl.class)
public interface JAVAFileModel extends TechnologyObject<JAVATechnologyAdapter>, ResourceData<JAVAFileModel> {

    public static final String MODEL_ITEM_KEY = "javaFile";
    public static final String PARENT_ITEM_KEY = "javaParentFolder";

    @Getter(value = MODEL_ITEM_KEY, ignoreType = true)
    public File getFileModel();

    @Setter(value =  MODEL_ITEM_KEY)
    public void setFileModel(File file);
    
    @Getter(value = PARENT_ITEM_KEY, ignoreType = true)
    public JAVAFolderModel getFatherFolder();

    @Setter(value =  PARENT_ITEM_KEY)
    public void setFatherFolder(JAVAFolderModel fatherFolder);
    

}
