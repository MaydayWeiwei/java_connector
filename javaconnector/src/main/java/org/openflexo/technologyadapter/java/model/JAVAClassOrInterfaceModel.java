package org.openflexo.technologyadapter.java.model;

import java.util.List;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

@ModelEntity
@ImplementationClass(value = JAVAClassOrInterfaceModelImpl.class)
public interface JAVAClassOrInterfaceModel extends
		TechnologyObject<JAVATechnologyAdapter> {

	public static final String MODEL_ITEM_KEY = "javaClassOrInterface";
	public static final String PARENT_FILE_KEY = "javaParentFile";
	public static final String PARENT_CLASS_KEY = "javaParentClass";
	public static final String METHOD_ITEM_KEY = "javaMethod";
	public static final String CLASS_ITEM_KEY = "javaInnerClass";
	public static final String FIELD_ITEM_KEY = "javaField";

	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public ClassOrInterfaceDeclaration getClassOrInterfaceModel();

	@Setter(value = MODEL_ITEM_KEY)
	public void setClassOrInterfaceModel(
			ClassOrInterfaceDeclaration classOrInterfaceModel);

	@Getter(value = PARENT_FILE_KEY, ignoreType = true)
	public JAVAFileModel getJavaFile();

	@Setter(value = PARENT_FILE_KEY)
	public void setJavaFile(JAVAFileModel javaFile);
	
	@Getter(value = PARENT_CLASS_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getFatherClass();

	@Setter(value = PARENT_CLASS_KEY)
	public void setFatherClass(JAVAClassOrInterfaceModel fatherClass);
	
	@Getter(value = METHOD_ITEM_KEY, ignoreType = true)
	public List<JAVAMethodModel> getMethods();

	@Setter(value = METHOD_ITEM_KEY)
	public void setMethods(List<JAVAMethodModel> methods);
	
	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public List<JAVAClassOrInterfaceModel> getInnerClasses();

	@Setter(value = CLASS_ITEM_KEY)
	public void setInnerClasses(List<JAVAClassOrInterfaceModel> innerClasses);
	
	@Getter(value = FIELD_ITEM_KEY, ignoreType = true)
	public List<JAVAFieldModel> getFields();

	@Setter(value = FIELD_ITEM_KEY)
	public void setFields(List<JAVAFieldModel> fields);

	public String getName();

}
