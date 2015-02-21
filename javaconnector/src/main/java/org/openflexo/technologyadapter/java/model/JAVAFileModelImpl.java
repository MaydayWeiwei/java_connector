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

import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.utils.JAVAFileParser;

/**
 * Abstract Simple implementation. Using Pamela.
 * 
 * @author SomeOne
 * 
 */
public abstract class JAVAFileModelImpl extends FlexoObjectImpl implements JAVAFileModel {

	private JAVATechnologyAdapter technologyAdapter;
	private File fileModel;
	private JAVAClassOrInterfaceModel rootClass;

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
	@Setter(value = MODEL_ITEM_KEY)
	public void setFileModel(File fileModel) {
		this.fileModel = fileModel;
		if (fileModel.getName().toLowerCase().endsWith(".java")) {
			try {
				ClassOrInterfaceDeclaration javaClass = JAVAFileParser.getRoot(fileModel);
				final ModelFactory factory = new ModelFactory(JAVAClassOrInterfaceModel.class);
				final JAVAClassOrInterfaceModelImpl child = (JAVAClassOrInterfaceModelImpl) factory
						.newInstance(JAVAClassOrInterfaceModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setJavaFile(this);
				child.setClassOrInterfaceModel(javaClass);
				this.setRootClass(child);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	@Setter(value = CLASS_ITEM_KEY)
	public void setRootClass(JAVAClassOrInterfaceModel rootClass) {
		this.rootClass = rootClass;
	}

	@Override
	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getRootClass() {
		return rootClass;
	}

	@Override
	public String getName() {
		return this.fileModel.getName();
	}

}
