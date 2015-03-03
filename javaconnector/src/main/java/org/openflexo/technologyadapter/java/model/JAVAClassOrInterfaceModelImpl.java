package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.utils.JAVAFileParser;

/**
 * Represents an JAVA class
 * 
 * @author wei
 *
 */
public abstract class JAVAClassOrInterfaceModelImpl extends FlexoObjectImpl implements JAVAClassOrInterfaceModel {

	private static final Logger LOGGER = Logger.getLogger(JAVAClassOrInterfaceModelImpl.class.getName());
	private JAVATechnologyAdapter technologyAdapter;
	private JAVAFileModel javaFile;
	private JAVAClassOrInterfaceModel javaClass;
	private ClassOrInterfaceDeclaration classModel;
	private List<JAVAClassOrInterfaceModel> innerClasses;
	private List<JAVAFieldModel> javaFields;
	private List<JAVAMethodModel> javaMethods;

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
	@Getter(value = FILE_ITEM_KEY, ignoreType = true)
	public JAVAFileModel getJavaFile() {
		return javaFile;
	}

	@Override
	@Setter(value = FILE_ITEM_KEY)
	public void setJavaFile(JAVAFileModel javaFile) {
		this.javaFile = javaFile;
	}

	@Override
	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getJavaClass() {
		return javaClass;
	}

	@Override
	@Setter(value = CLASS_ITEM_KEY)
	public void setJavaClass(JAVAClassOrInterfaceModel javaClass) {
		this.javaClass = javaClass;
	}

	@Override
	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public ClassOrInterfaceDeclaration getClassModel() {
		return classModel;
	}

	@Override
	@Setter(value = MODEL_ITEM_KEY)
	public void setClassModel(ClassOrInterfaceDeclaration classModel) {
		this.classModel = classModel;
		try {
			final List<ClassOrInterfaceDeclaration> innerClasses = JAVAFileParser.getInnerClassList(classModel);
			final List<FieldDeclaration> javaFields = JAVAFileParser.getFieldList(classModel);
			final List<MethodDeclaration> javaMethods = JAVAFileParser.getMethodList(classModel);

			final List<JAVAClassOrInterfaceModel> sonClasses = new ArrayList<JAVAClassOrInterfaceModel>();
			final List<JAVAFieldModel> sonFields = new ArrayList<JAVAFieldModel>();
			final List<JAVAMethodModel> sonMethods = new ArrayList<JAVAMethodModel>();

			for (FieldDeclaration javaField : javaFields) {
				final ModelFactory factory = new ModelFactory(JAVAFieldModel.class);
				final JAVAFieldModelImpl child = (JAVAFieldModelImpl) factory.newInstance(JAVAFieldModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setJavaClass(this);
				child.setFieldModel(javaField);
				sonFields.add(child);
			}
			for (MethodDeclaration javaMethod : javaMethods) {
				final ModelFactory factory = new ModelFactory(JAVAMethodModel.class);
				final JAVAMethodModelImpl child = (JAVAMethodModelImpl) factory.newInstance(JAVAMethodModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setJavaClass(this);
				child.setJavaMethodModel(javaMethod);
				sonMethods.add(child);
			}
			for (ClassOrInterfaceDeclaration innerClass : innerClasses) {
				final ModelFactory factory = new ModelFactory(JAVAClassOrInterfaceModel.class);
				final JAVAClassOrInterfaceModelImpl child = (JAVAClassOrInterfaceModelImpl) factory
						.newInstance(JAVAClassOrInterfaceModel.class);
				child.setTechnologyAdapter(this.getTechnologyAdapter());
				child.setJavaClass(this);
				child.setClassModel(innerClass);
				sonClasses.add(child);
			}
			this.setJavaFields(sonFields);
			this.setJavaMethods(sonMethods);
			this.setInnerClasses(sonClasses);
		} catch (final ModelDefinitionException e) {
			final String msg = "Error while setting MapModel of map " + this;
			LOGGER.log(Level.SEVERE, msg, e);
		}
	}

	@Override
	@Getter(value = METHOD_ITEM_KEY, ignoreType = true)
	public List<JAVAMethodModel> getJavaMethods() {
		return javaMethods;
	}

	@Override
	@Setter(value = METHOD_ITEM_KEY)
	public void setJavaMethods(List<JAVAMethodModel> methods) {
		this.javaMethods = methods;
	}

	@Override
	@Getter(value = INNERCLASS_ITEM_KEY, ignoreType = true)
	public List<JAVAClassOrInterfaceModel> getInnerClasses() {
		return innerClasses;
	}

	@Override
	@Setter(value = INNERCLASS_ITEM_KEY)
	public void setInnerClasses(List<JAVAClassOrInterfaceModel> innerClasses) {
		this.innerClasses = innerClasses;
	}

	@Override
	@Getter(value = FIELD_ITEM_KEY, ignoreType = true)
	public List<JAVAFieldModel> getJavaFields() {
		return javaFields;
	}

	@Override
	@Setter(value = FIELD_ITEM_KEY)
	public void setJavaFields(List<JAVAFieldModel> fields) {
		this.javaFields = fields;
	}

	@Override
	public String getName() {
		return this.classModel.getName();
	}

}
