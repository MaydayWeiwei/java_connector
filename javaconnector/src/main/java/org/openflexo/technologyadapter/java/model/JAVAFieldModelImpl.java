package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.FieldDeclaration;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

/**
 * Represents an JAVA field
 * 
 * @author wei
 *
 */
public abstract class JAVAFieldModelImpl extends FlexoObjectImpl implements JAVAFieldModel {

	private JAVATechnologyAdapter technologyAdapter;
	private JAVAClassOrInterfaceModel javaClass;
	private FieldDeclaration fieldModel;

	public JAVAFieldModelImpl() {
	}

	public void setTechnologyAdapter(JAVATechnologyAdapter technologyAdapter) {
		this.technologyAdapter = technologyAdapter;
	}

	@Override
	public JAVATechnologyAdapter getTechnologyAdapter() {
		return this.technologyAdapter;
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
	public FieldDeclaration getFieldModel() {
		return fieldModel;
	}

	@Override
	@Setter(value = MODEL_ITEM_KEY)
	public void setFieldModel(FieldDeclaration fieldModel) {
		this.fieldModel = fieldModel;
	}

	@Override
	public String getName() {
		return this.fieldModel.getVariables().get(0).getChildrenNodes().get(0).toString();
	}

}
