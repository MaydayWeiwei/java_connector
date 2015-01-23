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
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;

/**
 * Abstract Simple implementation. Using Pamela.
 * 
 * @author SomeOne
 * 
 */
public abstract class JAVAFileModelImpl extends FlexoObjectImpl implements JAVAFileModel {

    private JAVATechnologyAdapter technologyAdapter;
    private File fileModel;
    private JAVAFolderModel fatherFolder;
    	
    public JAVAFileModelImpl() {
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
	public File getFileModel() {
		return fileModel;
	}

    @Override
    @Setter(value =  MODEL_ITEM_KEY)
	public void setFileModel(File fileModel) {
		this.fileModel = fileModel;
	}

    @Override
    @Getter(value = PARENT_ITEM_KEY, ignoreType = true)
	public JAVAFolderModel getFatherFolder() {
		return fatherFolder;
	}

    @Override
    @Setter(value =  PARENT_ITEM_KEY)
	public void setFatherFolder(JAVAFolderModel fatherFolder) {
		this.fatherFolder = fatherFolder;
	}

}
