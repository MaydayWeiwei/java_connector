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

package org.openflexo.technologyadapter.java.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.library.JAVAIconLibrary;
import org.openflexo.technologyadapter.java.view.JAVAModuleView;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class JAVAAdapterController extends TechnologyAdapterController<JAVATechnologyAdapter> {
    
	static final Logger LOGGER = Logger.getLogger(JAVAAdapterController.class.getPackage().getName());

	@Override
	public Class<JAVATechnologyAdapter> getTechnologyAdapterClass() {
		return JAVATechnologyAdapter.class;
	}

	@Override
	public void initializeActions(ControllerActionInitializer actionInitializer) {
		actionInitializer.getController().getModuleInspectorController()
				.loadDirectory(ResourceLocator.locateResource("Inspectors/JAVA"));
	}

	@Override
	public ImageIcon getTechnologyBigIcon() {
		return JAVAIconLibrary.JAVA_TECHNOLOGY_BIG_ICON;
	}

	@Override
	public ImageIcon getTechnologyIcon() {
		return JAVAIconLibrary.JAVA_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getModelIcon() {
		return JAVAIconLibrary.JAVA_FILE_ICON;
	}

	@Override
	public ImageIcon getMetaModelIcon() {
		return JAVAIconLibrary.JAVA_FILE_ICON;
	}

	@Override
	public ImageIcon getIconForTechnologyObject(final Class<? extends TechnologyObject<JAVATechnologyAdapter>> objectClass) {
		// TODO Auto-generated method stub
		return JAVAIconLibrary.JAVA_TECHNOLOGY_ICON;
	}

	@Override
	public ModuleView<?> createModuleViewForObject(final TechnologyObject<JAVATechnologyAdapter> object, final FlexoController controller, final FlexoPerspective perspective) {
		// TODO Auto-generated method stub : update your moduleView code to have somethig represented
		if (object instanceof JAVAFolderModel){
			return new JAVAModuleView((JAVAFolderModel) object, controller, perspective);
		}
		return new EmptyPanel<TechnologyObject<JAVATechnologyAdapter>>(controller, perspective, object);
	}

	@Override
	public ImageIcon getIconForPatternRole(Class<? extends FlexoRole<?>> role) {
		// TODO Auto-generated method stub
		return JAVAIconLibrary.JAVA_TECHNOLOGY_ICON;
	}

	@Override
	public String getWindowTitleforObject(
			TechnologyObject<JAVATechnologyAdapter> obj, FlexoController controller) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean hasModuleViewForObject(
			TechnologyObject<JAVATechnologyAdapter> obj, FlexoController controller) {
		// TODO Auto-generated method stub
		return obj instanceof JAVAFolderModel;
	}
}

