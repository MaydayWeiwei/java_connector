package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.FieldDeclaration;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

@ModelEntity
@ImplementationClass(value = JAVAFieldModelImpl.class)
public interface JAVAFieldModel extends TechnologyObject<JAVATechnologyAdapter> {

	public static final String MODEL_ITEM_KEY = "fieldModel";
	public static final String CLASS_ITEM_KEY = "javaClass";

	@Getter(value = MODEL_ITEM_KEY, ignoreType = true)
	public FieldDeclaration getFieldModel();

	@Setter(value = MODEL_ITEM_KEY)
	public void setFieldModel(FieldDeclaration fieldModel);

	@Getter(value = CLASS_ITEM_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getJavaClass();

	@Setter(value = CLASS_ITEM_KEY)
	public void setJavaClass(JAVAClassOrInterfaceModel javaFatherItem);

	public String getName();

}
