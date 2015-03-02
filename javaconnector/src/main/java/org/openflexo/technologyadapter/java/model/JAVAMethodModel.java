package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.MethodDeclaration;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

@ModelEntity
@ImplementationClass(value = JAVAMethodModelImpl.class)
public interface JAVAMethodModel extends TechnologyObject<JAVATechnologyAdapter> {

	public static final String MODEL_ITEM_KEY = "javaMethodModel";
	public static final String CLASS_ITEM_KEY = "javaClass";

	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public MethodDeclaration getJavaMethodModel();

	@Setter(value = MODEL_ITEM_KEY)
	public void setJavaMethodModel(MethodDeclaration javaMethodModel);

	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getJavaClass();

	@Setter(value = CLASS_ITEM_KEY)
	public void setJavaClass(JAVAClassOrInterfaceModel javaClass);

	public String getName();

}
