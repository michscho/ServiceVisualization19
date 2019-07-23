package tum.franca.main;

import javafx.collections.ObservableList;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell.FontStyle;
import tum.franca.graph.edges.IEdge;

/**
 * 
 * @author michaelschott
 *
 */
public class Metrics {

	public static void setZoom(double zoom) {
		zoom *= 100;
		MainAppController.staticZoomText.setText(String.valueOf(zoom).substring(0, 4) + "%");
	}

	public static void setServicesAndGroups() {
		ObservableList<ICell> cells = MainApp.graph.getModel().getAddedCells();
		int serviceCounter = 0;
		int groupCounter = 0;
		int subGroupCounter = 0;
		int subSubGroupCounter = 0;
		for (ICell iCell : cells) {
			if (iCell instanceof RectangleCell) {
				serviceCounter++;
			}
			if (iCell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) iCell).style == FontStyle.BIG) {
					groupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == FontStyle.MEDIUM) {
					subGroupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == FontStyle.SMALL) {
					subSubGroupCounter++;
				}
			}
		}
		MainAppController.staticServicesText.setText(String.valueOf(serviceCounter));
		MainAppController.staticGroupsText.setText(String.valueOf(groupCounter));
		MainAppController.staticSubGroupsText.setText(String.valueOf(subGroupCounter)); 
		MainAppController.staticSubSubGroupsText.setText(String.valueOf(subSubGroupCounter)); 
	}
	
	public static void setRelations() {
		ObservableList<IEdge> cells = MainApp.graph.getModel().getAddedEdges();
		int relation = 0;
		for (IEdge iCell : cells) {
			relation++;
		}
		MainAppController.staticRelationsText.setText(String.valueOf(relation));
	}

}
