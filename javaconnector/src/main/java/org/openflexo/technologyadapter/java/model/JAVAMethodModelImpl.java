package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.MethodDeclaration;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

public abstract class JAVAMethodModelImpl extends FlexoObjectImpl implements JAVAMethodModel {
	

    private JAVATechnologyAdapter technologyAdapter;
	private JAVAClassOrInterfaceModel javaFatherItem;
	private MethodDeclaration methodModel;

	public JAVAMethodModelImpl() {
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
	public MethodDeclaration getMethodModel() {
		return methodModel;
	}

	@Override
	@Setter(value = MODEL_ITEM_KEY)
	public void setMethodModel(MethodDeclaration methodModel) {
		this.methodModel = methodModel;
	}

	@Override
	public String getName() {
		return this.methodModel.getName();
	}

}
