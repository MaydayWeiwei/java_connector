package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.FieldDeclaration;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

public abstract class JAVAFieldModelImpl extends FlexoObjectImpl implements
		JAVAFieldModel {

    private JAVATechnologyAdapter technologyAdapter;
	private JAVAClassOrInterfaceModel javaFatherItem;
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
	@Getter(value = PARENT_ITEM_KEY, ignoreType = true)
	public JAVAClassOrInterfaceModel getJavaFatherItem() {
		return javaFatherItem;
	}

	@Override
	@Setter(value = PARENT_ITEM_KEY)
	public void setJavaFatherItem(JAVAClassOrInterfaceModel javaFatherItem) {
		this.javaFatherItem = javaFatherItem;
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
		return this.fieldModel.getVariables().get(0).getChildrenNodes().get(0)
				.toString();
	}

}
