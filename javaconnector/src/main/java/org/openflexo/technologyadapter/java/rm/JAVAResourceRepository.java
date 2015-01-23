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

package org.openflexo.technologyadapter.java.rm;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterFileResourceRepository;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;

public class JAVAResourceRepository extends TechnologyAdapterFileResourceRepository<JAVAResource, JAVATechnologyAdapter, JAVAFolderModel> {

    public JAVAResourceRepository(JAVATechnologyAdapter adapter, FlexoResourceCenter<?> resourceCenter) {
        super(adapter, resourceCenter);
    }

    private static final String DEFAULT_BASE_URI = "http://www.openflexo.org/JAVATechnologyAdapter/Models";

    @Override
    public String getDefaultBaseURI() {
        return DEFAULT_BASE_URI;
    }

}
