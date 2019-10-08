package tum.franca.view.metric;

import javafx.scene.paint.Color;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;

/**
 * 
 * @author michaelschott
 * 
 *         In this class the group metrics are calculated.
 *
 */
public class GroupMetrics {

	/**
	 * Sets groups metrics in the application. Use for updating.
	 */
	public static void setAll(ResizableRectangleCell cell) {
		setNumberOfServices(cell);
		setGroupName(cell);
		setCoupling(cell);
		setCohesion(cell);
		setRelation(cell);
		setNumberSubGroups(cell);
		setNumberSubSubGroups(cell);
		setNumberPropertyFunctions(cell);
		setCouplingCohesionFactor(cell);
		setInfoText(cell);
	}

	public static void setNumberOfServices(ResizableRectangleCell cell) {
		MainAppController.staticServicesTextGroup.setText(String.valueOf(getNumberOfServices(cell)));
	}

	public static int getNumberOfServices(ResizableRectangleCell cell) {
		return cell.containsRectangleCell().size();
	}

	public static void setGroupName(ResizableRectangleCell cell) {
		MainAppController.staticGroupName.setText(cell.group + " " + cell.getName());
	}

	public static void setCohesion(ResizableRectangleCell cell) {
		MainAppController.staticCohesionTextGroup.setText(getCohesion(cell) + "");
	}

	public static void setCoupling(ResizableRectangleCell cell) {
		MainAppController.staticCouplingTextGroup.setText(getCoupling(cell) + "");
	}

	public static void setRelation(ResizableRectangleCell cell) {
		MainAppController.staticRelationsTextGroup.setText(getCoupling(cell) + getCohesion(cell) + "");
	}

	public static void setNumberSubGroups(ResizableRectangleCell cell) {
		MainAppController.staticSubGroupsTextGroup.setText(getNumberSubGroups(cell) + "");
	}
	
	public static int getNumberSubGroups(ResizableRectangleCell cell) {
		return (int) cell.containsResizableRectanlgeCells().stream().filter(f -> f.containsRectangleCell().size() > 0).count();
	}

	public static void setNumberSubSubGroups(ResizableRectangleCell cell) {
		MainAppController.staticSubSubGroupsTextGroup.setText(getNumberSubGroups(cell) + "");
	}

	public static int getCohesion(ResizableRectangleCell cell) {
		int counter = 0;
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			RectangleCell source = (RectangleCell) edge.getSource();
			RectangleCell target = (RectangleCell) edge.getTarget();
			if (cell.containsRectangleCell().contains(source) && cell.containsRectangleCell().contains(target)) {
				counter++;
			}
		}
		return counter;
	}

	public static int getCoupling(ResizableRectangleCell cell) {
		int counter = 0;
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			RectangleCell source = (RectangleCell) edge.getSource();
			RectangleCell target = (RectangleCell) edge.getTarget();
			if (cell.containsRectangleCell().contains(source) && !cell.containsRectangleCell().contains(target)
					|| !cell.containsRectangleCell().contains(source)
							&& cell.containsRectangleCell().contains(target)) {
				counter++;
			}
		}
		return counter;
	}
	
	public static int getNumberPropertyFunctions(ResizableRectangleCell cell) {
		return cell.properties.size();
	}
	
	public static void setNumberPropertyFunctions(ResizableRectangleCell cell) {
		MainAppController.staticPropertyFunctionGroup.setText(getNumberPropertyFunctions(cell) + "");
	}
	
	public static float getCouplingCohesionFactor(ResizableRectangleCell cell) {
		return (float) getCoupling(cell) / getCohesion(cell);
	}
	
	public static void setCouplingCohesionFactor(ResizableRectangleCell cell) {
		MainAppController.staticCoupCoheFactor.setText(String.format("%.02f",getCouplingCohesionFactor(cell)));
	}
	
	public static void setInfoText(ResizableRectangleCell cell) {
		if (getCouplingCohesionFactor(cell) >= 1) {
			MainAppController.staticInfoText.setFill(Color.RED);
			MainAppController.staticInfoText.setText("Unlikly high, consider change!");
		} else {
			MainAppController.staticInfoText.setFill(Color.GREEN);
			MainAppController.staticInfoText.setText("Seems ok!");
		}
	}

}
