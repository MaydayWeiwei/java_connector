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

package org.openflexo.technologyadapter.java.fml;

import java.lang.reflect.Type;

import org.openflexo.foundation.view.ActorReference;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.ModelObjectActorReference;
import org.openflexo.foundation.view.VirtualModelInstanceModelFactory;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.fml.JAVAFolderRole.JAVAFolderRoleImpl;

@ModelEntity
@ImplementationClass(value = JAVAFolderRoleImpl.class)
@XMLElement
public interface JAVAFolderRole extends FlexoRole<JAVAFolderModel> {

    public JAVATechnologyAdapter getTechnologyAdapter();

    public abstract static class JAVAFolderRoleImpl extends FlexoRoleImpl<JAVAFolderModel> implements JAVAFolderRole {

        public JAVAFolderRoleImpl() {
            super();
        }

        /* (non-Javadoc)
         * @see org.openflexo.foundation.viewpoint.FlexoRole.FlexoRoleImpl#getType()
         */
        @Override
        public Type getType() {
            return JAVAFolderModel.class;
        }

        /* (non-Javadoc)
         * @see org.openflexo.foundation.viewpoint.FlexoRole.FlexoRoleImpl#getPreciseType()
         */
        @Override
        public String getPreciseType() {
            return JAVAFolderModel.class.getSimpleName();
        }

        /* (non-Javadoc)
         * @see org.openflexo.foundation.viewpoint.FlexoRole#defaultCloningStrategy()
         */
        @Override
        public RoleCloningStrategy defaultCloningStrategy() {
            return RoleCloningStrategy.Reference;
        }

        /* (non-Javadoc)
         * @see org.openflexo.foundation.viewpoint.FlexoRole.FlexoRoleImpl#defaultBehaviourIsToBeDeleted()
         */
        @Override
        public boolean defaultBehaviourIsToBeDeleted() {
            return false;
        }

        /* (non-Javadoc)
         * @see org.openflexo.foundation.viewpoint.FlexoRole.FlexoRoleImpl#makeActorReference(java.lang.Object, org.openflexo.foundation.view.FlexoConceptInstance)
         */
        @Override
        public ActorReference<JAVAFolderModel> makeActorReference(final JAVAFolderModel object, final FlexoConceptInstance epi) {
        	final VirtualModelInstanceModelFactory factory = epi.getFactory();
            final ModelObjectActorReference<JAVAFolderModel> returned = factory.newInstance(ModelObjectActorReference.class);
            returned.setFlexoRole(this);
            returned.setFlexoConceptInstance(epi);
            returned.setModellingElement(object);
            return returned;
        }

        /**
         * 
         * @return JAVA technology adapter in service manager.
         */
        @Override
        public JAVATechnologyAdapter getTechnologyAdapter() {
            return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(JAVATechnologyAdapter.class);
        }
    }
}
