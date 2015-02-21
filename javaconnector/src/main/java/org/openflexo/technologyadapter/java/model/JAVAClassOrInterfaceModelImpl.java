package org.openflexo.technologyadapter.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.utils.JAVAFileParser;

public abstract class JAVAClassOrInterfaceModelImpl extends FlexoObjectImpl implements JAVAClassOrInterfaceModel {

	private static final Logger LOGGER = Logger.getLogger(JAVAClassOrInterfaceModelImpl.class.getName());
	private JAVATechnologyAdapter technologyAdapter;
	private JAVAFileModel javaFile;
	private JAVAClassOrInterfaceModel fatherClass;
	private ClassOrInterfaceDeclaration classOrInterfaceModel;
	private List<JAVAClassOrInterfaceModel> innerClasses;
	private List<JAVAFieldModel> fields;
	private List<JAVAMethodModel> methods;

	public JAVAClassOrInterfaceModelImpl() {
	}

	public void setTechnologyAdapter(JAVATechnologyAdapter technologyAdapter) {
		this.technologyAdapter = technologyAdapter;
	}

	@Override
	public JAVATechnologyAdapter getTechnologyAdapter() {
		return this.technologyAdapter;
	}

	@Override
	@Getter(value = PARENT_FILE_KEY, ignoreType = true)
	public JAVAFileModel getJavaFile() {
		return javaFile;
	}

	@Override
	@Setter(value = PARENT_FILE_KEY)
	public void setJavaFile(JAVAFileModel javaFile) {
		this.javaFile = javaFile;
	}

	@Override
	@Getter(value = PARENT_CLASS_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getFatherClass() {
		return fatherClass;
	}

	@Override
	@Setter(value = PARENT_CLASS_KEY)
	public void setFatherClass(JAVAClassOrInterfaceModel fatherClass) {
		this.fatherClass = fatherClass;
	}

	@Override
	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public ClassOrInterfaceDeclaration getClassOrInterfaceModel() {
		return classOrInterfaceModel;
	}

	@Override
	@Setter(value = MODEL_ITEM_KEY)
	public void setClassOrInterfaceModel(ClassOrInterfaceDeclaration classOrInterfaceModel) {
		this.classOrInterfaceModel = classOrInterfaceModel;
		try {
			final List<ClassOrInterfaceDeclaration> innerClasses = JAVAFileParser.getInnerClassList(classOrInterfaceModel);
			final List<FieldDeclaration> javaFields = JAVAFileParser.getFieldList(classOrInterfaceModel);
			final List<MethodDeclaration> javaMethods = JAVAFileParser.getMethodList(classOrInterfaceModel);

			final List<JAVAClassOrInterfaceModel> sonClasses = new ArrayList<JAVAClassOrInterfaceModel>();
			final List<JAVAFieldModel> sonFields = new ArrayList<JAVAFieldModel>();
			final List<JAVAMethodModel> sonMethods = new ArrayList<JAVAMethodModel>();

			for (FieldDeclaration javaField : javaFields) {
				final ModelFactory factory = new ModelFactory(JAVAFieldModel.class);
				final JAVAFieldModelImpl child = (JAVAFieldModelImpl) factory.newInstance(JAVAFieldModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setJavaFatherItem(this);
				child.setFieldModel(javaField);
				sonFields.add(child);
			}
			for (MethodDeclaration javaMethod : javaMethods) {
				final ModelFactory factory = new ModelFactory(JAVAMethodModel.class);
				final JAVAMethodModelImpl child = (JAVAMethodModelImpl) factory.newInstance(JAVAMethodModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setJavaFatherItem(this);
				child.setMethodModel(javaMethod);
				sonMethods.add(child);
			}
			for (ClassOrInterfaceDeclaration innerClass : innerClasses) {
				final ModelFactory factory = new ModelFactory(JAVAClassOrInterfaceModel.class);
				final JAVAClassOrInterfaceModelImpl child = (JAVAClassOrInterfaceModelImpl) factory
						.newInstance(JAVAClassOrInterfaceModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setFatherClass(this);
				child.setClassOrInterfaceModel(innerClass);
				sonClasses.add(child);
			}
			this.setFields(sonFields);
			this.setMethods(sonMethods);
			this.setInnerClasses(sonClasses);
		} catch (final ModelDefinitionException e) {
			final String msg = "Error while setting MapModel of map " + this;
			LOGGER.log(Level.SEVERE, msg, e);
		}
	}

	@Override
	@Getter(value = METHOD_ITEM_KEY, ignoreType = true)
	public List<JAVAMethodModel> getMethods() {
		return methods;
	}

	@Override
	@Setter(value = METHOD_ITEM_KEY)
	public void setMethods(List<JAVAMethodModel> methods) {
		this.methods = methods;
	}

	@Override
	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public List<JAVAClassOrInterfaceModel> getInnerClasses() {
		return innerClasses;
	}

	@Override
	@Setter(value = CLASS_ITEM_KEY)
	public void setInnerClasses(List<JAVAClassOrInterfaceModel> innerClasses) {
		this.innerClasses = innerClasses;
	}

	@Override
	@Getter(value = FIELD_ITEM_KEY, ignoreType = true)
	public List<JAVAFieldModel> getFields() {
		return fields;
	}

	@Override
	@Setter(value = FIELD_ITEM_KEY)
	public void setFields(List<JAVAFieldModel> fields) {
		this.fields = fields;
	}

	@Override
	public String getName() {
		return this.classOrInterfaceModel.getName();
	}

}
