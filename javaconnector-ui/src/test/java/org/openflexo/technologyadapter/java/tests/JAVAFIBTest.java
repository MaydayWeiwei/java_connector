package org.openflexo.technologyadapter.java.tests;

import org.junit.Test;
import org.openflexo.fib.utils.GenericFIBTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class JAVAFIBTest extends GenericFIBTestCase {

	public static void main(final String[] args) {
		System.out.println(generateFIBTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Fib")).getFile(), "Fib/"));
	}

	@Test
	public void testJAVAFolderPanel() {
		this.validateFIB("Fib/JAVAFolderPanel.fib");
	}

}
