package org.openflexo.technologyadapter.java;

import java.io.File;

public class SimpleTest {

	public static void main(String[] args) {
		File file = new File("/Users/wei/workspace/test/web_services/monserviceClient/src/DefaultNamespace/Calculatrice.java");
		File f = new File("/Users/wei/workspace/test");
		System.out.println(isValidateJAVAFile(file, f.getAbsolutePath(), file.getName().toLowerCase()));
	}

	private static boolean isValidateJAVAFile(File file, String resourceCenter, String fileName) {
		if (!isValidateJAVAFileName(fileName) || file.isHidden() || "target".equals(file.getName()) || "build".equals(file.getName())) {
			return false;
		}
		else if (resourceCenter.equals(file.getParent())) {
			return true;
		}
		else {
			return isValidateJAVAFile(file.getParentFile(), resourceCenter, fileName);
		}
	}

	private static boolean isValidateJAVAFileName(String fileName) {
		return fileName.endsWith(".java") || fileName.endsWith("xml");
	}
}
