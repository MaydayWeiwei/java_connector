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

import java.util.HashMap;
import java.util.Map;

import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.technologyadapter.java.rm.JAVAResource;

public class JAVATechnologyContextManager extends TechnologyContextManager<JAVATechnologyAdapter> {

	/** Stores all known java file where key is the URI of JAVAFile */
	protected Map<String, JAVAResource> javaFiles = new HashMap<String, JAVAResource>();

	public JAVATechnologyContextManager(JAVATechnologyAdapter adapter, FlexoResourceCenterService resourceCenterService) {
		super(adapter, resourceCenterService);
	}

	@Override
	public JAVATechnologyAdapter getTechnologyAdapter() {
		return (JAVATechnologyAdapter) super.getTechnologyAdapter();
	}

	public JAVAResource getJAVAResource(Object javaFile) {
		return javaFiles.get(javaFile);
	}

	public void registerJAVAFile(JAVAResource newjavaResource) {
		registerResource(newjavaResource);
		javaFiles.put(newjavaResource.getURI(), newjavaResource);
	}
}
