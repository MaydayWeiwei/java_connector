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

import org.openflexo.foundation.technologyadapter.DeclareEditionActions;
import org.openflexo.foundation.technologyadapter.DeclareFetchRequests;
import org.openflexo.foundation.technologyadapter.DeclarePatternRole;
import org.openflexo.foundation.technologyadapter.DeclarePatternRoles;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.foundation.view.action.CreateVirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.fml.JAVAFileRole;
import org.openflexo.technologyadapter.java.fml.JAVAFolderRole;

/**
 * Implementation of the ModelSlot class for the JAVA technology adapter<br>
 * We expect here to connect an JAVA model conform to an JAVAMetaModel
 * 
 * @author SomeOne
 * 
 */
@DeclarePatternRoles({ // All pattern roles available through this model slot
        @DeclarePatternRole(flexoRoleClass = JAVAFolderRole.class, FML = "JAVAFolderRole"),
        @DeclarePatternRole(flexoRoleClass = JAVAFileRole.class, FML = "JAVAFileRole"),
    })
@DeclareEditionActions({ // All edition actions available through this model slot
    })
@DeclareFetchRequests({ // All requests available through this model slot
    })
@ModelEntity
@ImplementationClass(JAVAModelSlot.JAVAModelSlotImpl.class)
@XMLElement
public interface JAVAModelSlot extends FreeModelSlot<JAVAFolderModel> {

    @Override
    public JAVATechnologyAdapter getTechnologyAdapter();

    public static abstract class JAVAModelSlotImpl extends FreeModelSlotImpl<JAVAFolderModel> implements JAVAModelSlot {

        @Override
        public Class<JAVATechnologyAdapter> getTechnologyAdapterClass() {
            return JAVATechnologyAdapter.class;
        }

        /**
         * Instanciate a new model slot instance configuration for this model slot
         */
        @Override
        public JAVAModelSlotInstanceConfiguration createConfiguration(CreateVirtualModelInstance action) {
            return new JAVAModelSlotInstanceConfiguration(this, action);
        }

        @Override
        public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
            if (JAVAFolderRole.class.isAssignableFrom(patternRoleClass)) {
                return "JavaFolder";
        	}
            if (JAVAFileRole.class.isAssignableFrom(patternRoleClass)) {
                return "JavaFile";
        	}
            return null;
        }

        @Override
        public Type getType() {
            return JAVAFolderModel.class;
        }

        @Override
        public JAVATechnologyAdapter getTechnologyAdapter() {
            return (JAVATechnologyAdapter) super.getTechnologyAdapter();
        }

    }
}
