
package tum.franca.main;

import javafx.collections.ObservableList;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.RectangleUtil;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell.GroupType;
import tum.franca.graph.edges.IEdge;

/**
 * 
 * @author michaelschott
 *
 */
public class Metrics {
	
	/**
	 * Sets all Metrics.
	 */
	public static void setAll() {
		setServicesAndGroups();
		setRelations();
		setCoupling();
		setCohesion();
		setMostCommon();
		setLeastCommon();
	}

	/**
	 * Shows the zoom of the canvas.
	 * 
	 * @param zoom
	 */
	public static void setZoom(double zoom) {
		zoom *= 100;
		MainAppController.staticZoomText.setText(String.valueOf(zoom).substring(0, 4) + "%");
	}

	/**
	 * Set the number of Groups and subgroups.
	 */
	public static void setServicesAndGroups() {
		int serviceCounter = 0, groupCounter = 0, subGroupCounter = 0, subSubGroupCounter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				serviceCounter++;
			}
			if (iCell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) iCell).style == GroupType.TOPLEVEL) {
					groupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == GroupType.SUBLEVEL) {
					subGroupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == GroupType.SUBSUBLEVEL) {
					subSubGroupCounter++;
				}
			}
		}
		MainAppController.staticServicesText.setText(String.valueOf(serviceCounter));
		MainAppController.staticGroupsText.setText(String.valueOf(groupCounter));
		MainAppController.staticSubGroupsText.setText(String.valueOf(subGroupCounter));
		MainAppController.staticSubSubGroupsText.setText(String.valueOf(subSubGroupCounter));
	}

	private static int getRelations() {
		ObservableList<IEdge> cells = MainApp.graph.getModel().getAddedEdges();
		int relation = 0;
		for (@SuppressWarnings("unused")
		IEdge edge : cells) {
			relation++;
		}
		return relation;
	}

	/**
	 * Sets the number of relations.
	 */
	public static void setRelations() {
		MainAppController.staticRelationsText.setText(String.valueOf(getRelations()));
	}

	private static int getCoupling() {
		int couplingCounter = 0;
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			ICell sourceCell = edge.getSource();
			ICell targetCell = edge.getTarget();
			for (ResizableRectangleCell groupCell1 : ((RectangleCell) sourceCell).getGroupRectangle()) {
				if (groupCell1.style.ordinal() == RectangleUtil.getHighestGroup()) {
					for (ResizableRectangleCell groupCell2 : ((RectangleCell) targetCell).getGroupRectangle()) {
						if (groupCell2.style.ordinal() == RectangleUtil.getHighestGroup()) {
							if (!groupCell1.equals(groupCell2)) {
								couplingCounter++;
							}
						}
					}
				}
			}
		}
		return couplingCounter;
	}

	/**
	 * Sets the coupling.
	 */
	public static void setCoupling() {
		MainAppController.staticCouplingText.setText(String.valueOf(getCoupling()));

	}

	private static int getCohesion() {
		return getRelations() - getCoupling();
	}

	/**
	 * Set the cohesion.
	 */
	public static void setCohesion() {
		MainAppController.staticCohesionText.setText(String.valueOf(getCohesion()));
	}
	
	private static String getMostCommonProperty() {
		int i = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i < ((ResizableRectangleCell) iCell).getRectangleCells().size()) {
				i = ((ResizableRectangleCell) iCell).getRectangleCells().size();
				}
			}
		}
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i == ((ResizableRectangleCell) iCell).getRectangleCells().size()) {
				return ((ResizableRectangleCell) iCell).getName();
				}
			}
		}
		return "Unknown";
	}
	
	/**
	 * Sets the most common property.
	 */
	public static void setMostCommon() {
		MainAppController.staticMostCommonText.setText(getMostCommonProperty());
	}
	
	private static String getLeastCommonProperty() {
		int i = 1000;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i > ((ResizableRectangleCell) iCell).getRectangleCells().size()) {
				i = ((ResizableRectangleCell) iCell).getRectangleCells().size();
				}
			}
		}
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (i == ((ResizableRectangleCell) iCell).getRectangleCells().size()) {
				return ((ResizableRectangleCell) iCell).getName();
				}
			}
		}
		return "Unknown";
	}
	
	/**
	 * Sets the least common property.
	 */
	public static void setLeastCommon() {
		MainAppController.staticLeastCommonText.setText(getLeastCommonProperty());
	}

}
