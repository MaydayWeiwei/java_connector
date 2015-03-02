package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

@ModelEntity
@ImplementationClass(value = JAVAClassOrInterfaceModelImpl.class)
public interface JAVAClassOrInterfaceModel extends TechnologyObject<JAVATechnologyAdapter> {

	public static final String MODEL_ITEM_KEY = "classModel";
	public static final String FILE_ITEM_KEY = "javaFile";
	public static final String CLASS_ITEM_KEY = "javaClass";
	public static final String METHOD_ITEM_KEY = "javaMethods";
	public static final String INNERCLASS_ITEM_KEY = "javaInnerClasses";
	public static final String FIELD_ITEM_KEY = "javaFields";

	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public ClassOrInterfaceDeclaration getClassModel();

	@Setter(value = MODEL_ITEM_KEY)
	public void setClassModel(ClassOrInterfaceDeclaration classModel);

	@Getter(value = FILE_ITEM_KEY, ignoreType = true)
	public JAVAFileModel getJavaFile();

	@Setter(value = FILE_ITEM_KEY)
	public void setJavaFile(JAVAFileModel javaFile);

	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getJavaClass();

	@Setter(value = CLASS_ITEM_KEY)
	public void setJavaClass(JAVAClassOrInterfaceModel javaClass);

	@Getter(value = METHOD_ITEM_KEY, ignoreType = true)
	public List<JAVAMethodModel> getJavaMethods();

	@Setter(value = METHOD_ITEM_KEY)
	public void setJavaMethods(List<JAVAMethodModel> javaMethods);

	@Getter(value = INNERCLASS_ITEM_KEY, ignoreType = true)
	public List<JAVAClassOrInterfaceModel> getInnerClasses();

	@Setter(value = INNERCLASS_ITEM_KEY)
	public void setInnerClasses(List<JAVAClassOrInterfaceModel> innerClasses);

	@Getter(value = FIELD_ITEM_KEY, ignoreType = true)
	public List<JAVAFieldModel> getJavaFields();

	@Setter(value = FIELD_ITEM_KEY)
	public void setJavaFields(List<JAVAFieldModel> javaFields);

	public String getName();

}
