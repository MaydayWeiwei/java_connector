package org.openflexo.technologyadapter.java;

import java.io.File;

public class SimpleTest {
	
	public static void main(String[] args) {
		File file = new File("/Users/wei/workspace/apache_tomcat/consommation/.git/logs/refs/remotes/origin");
		File f = new File("/Users/wei/workspace");
		System.out.println(isValidateJAVAFolder(f));
	}

    private static boolean isValidateJAVAFolder(File folder) {
    	System.out.println(folder.getAbsolutePath());
    	if(folder.isHidden()) {
    		return false;
    	}
    	else if(folder.getParentFile()==null){
    		return true;    		
    	}
    	else{
    		return isValidateJAVAFolder(folder.getParentFile());
    	}
    }
}
