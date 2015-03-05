package org.openflexo.technologyadapter.java.controller;

import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoConceptNature;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.ViewPointNature;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.VirtualModelNature;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.FlexoConceptInstanceNature;
import org.openflexo.foundation.fml.rt.View;
import org.openflexo.foundation.fml.rt.ViewNature;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstanceNature;
import org.openflexo.foundation.nature.ProjectNature;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.technologyadapter.java.JAVATechnologyAdapter;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterControllerService;
import org.openflexo.view.controller.TechnologyPerspective;

public class JAVATechnologyPerspective extends TechnologyPerspective<JAVATechnologyAdapter> {

	static final Logger logger = Logger.getLogger(JAVATechnologyPerspective.class.getPackage().getName());

	/**
	 * @param controller
	 * @param name
	 */
	public JAVATechnologyPerspective(JAVATechnologyAdapter technologyAdapter, FlexoController controller) {
		super(technologyAdapter, controller);

	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean hasModuleViewForObject(FlexoObject object) {
		if (object instanceof TechnologyObject) {
			TechnologyAdapterControllerService tacService = getController().getApplicationContext().getTechnologyAdapterControllerService();
			JAVAAdapterController tac = tacService.getTechnologyAdapterController(getTechnologyAdapter());
			return tac.hasModuleViewForObject((TechnologyObject<JAVATechnologyAdapter>) object, getController());
		}
		else if (object instanceof RepositoryFolder<?>) {
			TechnologyAdapterControllerService tacService = getController().getApplicationContext().getTechnologyAdapterControllerService();
			JAVAAdapterController tac = tacService.getTechnologyAdapterController(getTechnologyAdapter());
			return tac.hasModuleViewForObject((RepositoryFolder<JAVAResource>) object, getController());
		}
		return false;
	}

	public final ModuleView<?> getModuleViewForRepositoryFolder(RepositoryFolder<JAVAResource> object) {
		TechnologyAdapterControllerService tacService = getController().getApplicationContext().getTechnologyAdapterControllerService();
		JAVAAdapterController tac = tacService.getTechnologyAdapterController(getTechnologyAdapter());
		if (tac != null) {
			return tac.createModuleViewForRepository(object, getController(), this);
		}
		return null;
	}

	@Override
	public String getWindowTitleforObject(FlexoObject object, FlexoController controller) {
		if (object instanceof JAVAFileModel) {
			JAVAFileModel fileModel = (JAVAFileModel) object;
			return fileModel.getName();
		}
		if (object instanceof RepositoryFolder<?>) {
			RepositoryFolder<JAVAResource> repository = (RepositoryFolder<JAVAResource>) object;
			return repository.getName();
		}
		if (object != null) {
			return object.toString();
		}
		logger.warning("Unexpected null object here");
		return null;
	}

	@SuppressWarnings("unchecked")
	public ModuleView<?> createModuleViewForObject(FlexoObject object) {

		if (object instanceof FlexoProject) {
			FlexoProject project = (FlexoProject) object;
			List<ProjectNature> availableNatures = getSpecificNaturesForProject(project);
			if (availableNatures.size() > 0) {
				ProjectNature nature = availableNatures.get(0);
				return getModuleViewForProject(project, nature);
			}
			return new EmptyPanel<FlexoObject>(getController(), this, object);
		}
		if (object instanceof View) {
			View view = (View) object;
			List<ViewNature> availableNatures = getSpecificNaturesForView(view);
			if (availableNatures.size() > 0) {
				ViewNature nature = availableNatures.get(0);
				return getModuleViewForView(view, nature);
			}
		}
		if (object instanceof VirtualModelInstance) {
			VirtualModelInstance vmi = (VirtualModelInstance) object;
			List<VirtualModelInstanceNature> availableNatures = getSpecificNaturesForVirtualModelInstance(vmi);
			if (availableNatures.size() > 0) {
				VirtualModelInstanceNature nature = availableNatures.get(0);
				return getModuleViewForVirtualModelInstance(vmi, nature);
			}
		}
		if (object instanceof FlexoConceptInstance) {
			FlexoConceptInstance vmi = (FlexoConceptInstance) object;
			List<FlexoConceptInstanceNature> availableNatures = getSpecificNaturesForFlexoConceptInstance(vmi);
			if (availableNatures.size() > 0) {
				FlexoConceptInstanceNature nature = availableNatures.get(0);
				return getModuleViewForFlexoConceptInstance(vmi, nature);
			}
		}
		if (object instanceof ViewPoint) {
			ViewPoint viewPoint = (ViewPoint) object;
			List<ViewPointNature> availableNatures = getSpecificNaturesForViewPoint(viewPoint);
			if (availableNatures.size() > 0) {
				ViewPointNature nature = availableNatures.get(0);
				return getModuleViewPointForViewPoint(viewPoint, nature);
			}
		}
		if (object instanceof VirtualModel) {
			VirtualModel virtualModel = (VirtualModel) object;
			List<VirtualModelNature> availableNatures = getSpecificNaturesForVirtualModel(virtualModel);
			if (availableNatures.size() > 0) {
				VirtualModelNature nature = availableNatures.get(0);
				return getModuleViewForVirtualModel(virtualModel, nature);
			}
		}
		if (object instanceof FlexoConcept) {
			FlexoConcept concept = (FlexoConcept) object;
			List<FlexoConceptNature> availableNatures = getSpecificNaturesForFlexoConcept(concept);
			if (availableNatures.size() > 0) {
				FlexoConceptNature nature = availableNatures.get(0);
				return getModuleViewForFlexoConcept(concept, nature);
			}
		}
		if (object instanceof TechnologyObject) {
			return getModuleViewForTechnologyObject((TechnologyObject<?>) object);
		}
		if (object instanceof RepositoryFolder<?>) {
			return getModuleViewForRepositoryFolder((RepositoryFolder<JAVAResource>) object);
		}
		return new EmptyPanel<FlexoObject>(getController(), this, object);
	}

}
