package org.openflexo.technologyadapter.java.model;

import japa.parser.ast.body.MethodDeclaration;

import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;

/**
 * Represents an JAVA method
 * 
 * @author wei
 *
 */
public abstract class JAVAMethodModelImpl extends FlexoObjectImpl implements JAVAMethodModel {

	private JAVATechnologyAdapter technologyAdapter;
	private JAVAClassOrInterfaceModel javaClass;
	private MethodDeclaration javaMethodModel;

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
	public MethodDeclaration getJavaMethodModel() {
		return javaMethodModel;
	}

	@Override
	@Setter(value = MODEL_ITEM_KEY)
	public void setJavaMethodModel(MethodDeclaration javaMethodModel) {
		this.javaMethodModel = javaMethodModel;
	}

	@Override
	public String getName() {
		return this.javaMethodModel.getName();
	}

}
