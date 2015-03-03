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

package org.openflexo.technologyadapter.java.view;

import javax.swing.JTabbedPane;

import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.technologyadapter.java.view.drawing.JAVARepositoryConstructor;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

@SuppressWarnings("serial")
public class JAVARepositoryView extends JTabbedPane implements ModuleView<RepositoryFolder<JAVAResource>> {

	private final FlexoController controller;

	private final RepositoryFolder<JAVAResource> repository;

	private final FlexoPerspective perspective;

	public JAVARepositoryView(RepositoryFolder<JAVAResource> repository, FlexoController controller, FlexoPerspective perspective) {
		this.controller = controller;
		this.repository = repository;
		this.perspective = perspective;
		JAVARepositoryConstructor constructor = new JAVARepositoryConstructor(repository, this);
		addTab(repository.getName(), constructor.createPanel());
	}

	@Override
	public RepositoryFolder<JAVAResource> getRepresentedObject() {
		return this.repository;
	}

	@Override
	public void deleteModuleView() {
		this.controller.removeModuleView(this);
	}

	@Override
	public FlexoPerspective getPerspective() {
		return this.perspective;
	}

	@Override
	public void willShow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void willHide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show(FlexoController controller, FlexoPerspective perspective) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAutoscrolled() {
		// TODO Auto-generated method stub
		return false;
	}

	public FlexoController getController() {
		return controller;
	}

}
