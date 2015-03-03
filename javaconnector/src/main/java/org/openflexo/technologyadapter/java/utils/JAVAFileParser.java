package org.openflexo.technologyadapter.java.utils;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide methods to parser a .java file
 * 
 * @author wei
 *
 */
public class JAVAFileParser {

	public static ClassOrInterfaceDeclaration getRoot(File javaFile) throws Exception {
		FileInputStream javaFileInputStream = new FileInputStream(javaFile);
		CompilationUnit cu;
		try {
			// parse the file
			cu = JavaParser.parse(javaFileInputStream);
		} finally {
			javaFileInputStream.close();
		}
		ClassOrInterfaceDeclaration root = null;
		for (Node node : cu.getChildrenNodes()) {
			if (node instanceof ClassOrInterfaceDeclaration) {
				root = (ClassOrInterfaceDeclaration) node;
			}
		}
		return root;
	}

	public static List<ClassOrInterfaceDeclaration> getInnerClassList(ClassOrInterfaceDeclaration parent) {
		List<ClassOrInterfaceDeclaration> innerClassList = new ArrayList<ClassOrInterfaceDeclaration>();
		for (Node node : parent.getChildrenNodes()) {
			if (node instanceof ClassOrInterfaceDeclaration) {
				innerClassList.add((ClassOrInterfaceDeclaration) node);
			}
		}
		return innerClassList;
	}

	public static List<MethodDeclaration> getMethodList(ClassOrInterfaceDeclaration parent) {
		List<MethodDeclaration> methodList = new ArrayList<MethodDeclaration>();
		for (Node node : parent.getChildrenNodes()) {
			if (node instanceof MethodDeclaration) {
				methodList.add((MethodDeclaration) node);
			}
		}
		return methodList;
	}

	public static List<FieldDeclaration> getFieldList(ClassOrInterfaceDeclaration parent) {
		List<FieldDeclaration> fieldList = new ArrayList<FieldDeclaration>();
		for (Node node : parent.getChildrenNodes()) {
			if (node instanceof FieldDeclaration) {
				fieldList.add((FieldDeclaration) node);
			}
		}
		return fieldList;
	}

}
