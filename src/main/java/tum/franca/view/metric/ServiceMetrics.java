package tum.franca.view.metric;

import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.main.MainAppController;

/**
 * 
 * @author michaelschott
 * 
 *         In this class the service metrics are calculated.
 *
 */
public class ServiceMetrics {

	/**
	 * Sets service metrics in the application. Use for updating.
	 */
	public static void setAll(RectangleCell cell) {
		setName(cell);
		setRelations(cell);
		setContainment(cell);
	}

	/**
	 * Sets the name of the service
	 * 
	 * @param cell
	 */
	public static void setName(RectangleCell cell) {
		MainAppController.staticServicesTextService.setText(cell.getName());
	}

	/**
	 * Sets the main relations attributes of the service.
	 * 
	 * @param cell
	 */
	public static void setRelations(RectangleCell cell) {
		MetricDependency mD = new MetricDependency();
		mD.buildDependencyTree3(cell);
		MainAppController.staticCouplingTextService.setText(MetricDependency.requires + "");
		MetricDependency mD2 = new MetricDependency();
		mD2.buildDependencyTree4(cell);
		MainAppController.staticCohesionTextService.setText(MetricDependency.provides + "");
		MainAppController.staticRelationsTextService
				.setText(MetricDependency.requires + MetricDependency.provides + "");
	}

	/**
	 * Sets the containment.
	 */
	public static void setContainment(RectangleCell cell) {
		MainAppController.staticServicesTextServiceGroup.setText("Unknown");
		MainAppController.staticServicesTextServiceSubGroup.setText("Unknown");
		MainAppController.staticServicesTextServiceSubSubGroup.setText("Unknown");
		for (ResizableRectangleCell rezCell : ((RectangleCell) cell).getGroupRectangle()) {
			System.out.println(rezCell.getName());
			System.out.println(rezCell.style);
			
			if (rezCell.style == ResizableRectangleCell.GroupType.TOPLEVEL) {
				MainAppController.staticServicesTextServiceGroup.setText(rezCell.getName());
			}
			if (rezCell.style == ResizableRectangleCell.GroupType.SUBLEVEL) {
				MainAppController.staticServicesTextServiceSubGroup.setText(rezCell.getName());
			}
			if (rezCell.style == ResizableRectangleCell.GroupType.SUBSUBLEVEL) {
				MainAppController.staticServicesTextServiceSubSubGroup.setText(rezCell.getName());
			}
		}
	}

}
