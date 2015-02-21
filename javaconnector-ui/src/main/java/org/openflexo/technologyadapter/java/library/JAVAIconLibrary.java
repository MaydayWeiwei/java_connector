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

package org.openflexo.technologyadapter.java.library;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.toolbox.ImageIconResource;

public class JAVAIconLibrary {
	private static final Logger logger = Logger.getLogger(JAVAIconLibrary.class.getPackage().getName());

	public static final ImageIcon JAVA_TECHNOLOGY_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/java.jpg"));
	public static final ImageIcon JAVA_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/java-small.jpg"));
	public static final ImageIcon JAVA_FILE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/java.jpg"));

	public static ImageIcon iconForObject(Class<? extends TechnologyObject> objectClass) {
		return JAVA_TECHNOLOGY_ICON;
	}
}
