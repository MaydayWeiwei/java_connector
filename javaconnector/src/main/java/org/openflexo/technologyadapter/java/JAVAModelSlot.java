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

package org.openflexo.technologyadapter.java;

import java.lang.reflect.Type;

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareEditionActions;
import org.openflexo.foundation.fml.annotations.DeclareFetchRequests;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRoles;
import org.openflexo.foundation.fml.rt.action.CreateVirtualModelInstance;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.java.fml.JAVAFileRole;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;

/**
 * Implementation of the ModelSlot class for the JAVA technology adapter<br>
 * We expect here to connect an JAVA model conform to an JAVAMetaModel
 * 
 * @author wei
 * 
 */
@DeclareFlexoRoles({ JAVAFileRole.class })
@DeclareEditionActions({ // All edition actions available through this model
// slot
})
@DeclareFetchRequests({ // All requests available through this model slot
})
@ModelEntity
@ImplementationClass(JAVAModelSlot.JAVAModelSlotImpl.class)
@XMLElement
public interface JAVAModelSlot extends FreeModelSlot<JAVAFileModel> {

	@Override
	public JAVATechnologyAdapter getModelSlotTechnologyAdapter();

	public static abstract class JAVAModelSlotImpl extends FreeModelSlotImpl<JAVAFileModel> implements JAVAModelSlot {

		@Override
		public Class<JAVATechnologyAdapter> getTechnologyAdapterClass() {
			return JAVATechnologyAdapter.class;
		}

		/**
		 * Instanciate a new model slot instance configuration for this model slot
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public JAVAModelSlotInstanceConfiguration createConfiguration(CreateVirtualModelInstance action) {
			return new JAVAModelSlotInstanceConfiguration(this, action);
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			if (JAVAFileRole.class.isAssignableFrom(patternRoleClass)) {
				return "JavaFile";
			}
			return null;
		}

		@Override
		public Type getType() {
			return JAVAFileModel.class;
		}

		@Override
		public JAVATechnologyAdapter getModelSlotTechnologyAdapter() {
			return (JAVATechnologyAdapter) super.getModelSlotTechnologyAdapter();
		}

	}
}
