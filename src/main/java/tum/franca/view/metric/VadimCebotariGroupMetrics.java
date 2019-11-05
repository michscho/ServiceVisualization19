package tum.franca.view.metric;

import java.util.ArrayList;
import java.util.List;

import org.franca.core.franca.FBinding;
import org.franca.core.franca.FMethod;

import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;

/**
 * 
 * @author michaelschott
 *
 */
public class VadimCebotariGroupMetrics {

	/**
	 * Sets groups metrics in the application.
	 */
	public static void setAll(ResizableRectangleCell cell) {
		MainAppController.staticsgic.setText(String.valueOf(getSGIC(cell)));
		MainAppController.staticsgiic.setText(String.valueOf(getSGIIC(cell)));
		MainAppController.staticsgied.setText(String.format("%.02f", getSGIED(cell)));
		MainAppController.staticsgcd.setText(String.format("%.02f", getSGCD(cell)));
		MainAppController.staticsgsdd.setText(String.format("%.02f", getSGSDD(cell)));
		MainAppController.staticsgwdd.setText(String.format("%.02f", getSGSSD(cell)));
	}

	/**
	 * Service Group Interface Count
	 */
	public static int getSGIC(ResizableRectangleCell cell) {
		int counter = 0;
		for (RectangleCell recCell : cell.containsRectangleCell()) {
			counter += recCell.fidlReader.getInterfaces().get(0).getMethods().size();
		}
		return counter;
	}

	/**
	 * Service Group Internal Interface Count
	 */
	public static int getSGIIC(ResizableRectangleCell cell) {
		int counter = 0;
		for (RectangleCell recCell : cell.containsRectangleCell()) {
			for (FMethod method : recCell.fidlReader.getInterfaces().get(0).getMethods()) {
				if (isInternalInterface(cell, method)) {
					counter++;
				}
			}
		}
		return counter;
	}

	private static boolean isInternalInterface(ResizableRectangleCell cell, FMethod fMethod) {
		List<ICell> iCellList = MainApp.graph.getModel().getAddedCells();
		List<RectangleCell> allRecCellsList = new ArrayList<RectangleCell>();
		for (ICell iCell : iCellList) {
			if (iCell instanceof RectangleCell) {
				allRecCellsList.add((RectangleCell) iCell);
			}
		}
		for (RectangleCell recCell2 : allRecCellsList) {
			for (FMethod method : recCell2.fidlReader.getInterfaces().get(0).getMethods()) {
				if (fMethod.getName().equals(method.getName()) && !cell.containsRectangleCell().contains(recCell2)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Service Group Interface Exposure Degree
	 */
	public static double getSGIED(ResizableRectangleCell cell) {
		return (double) getSGIC(cell) / (double) (getSGIC(cell) + getSGIIC(cell));
	}

	/**
	 * Service Group Composability Degree
	 */
	public static double getSGCD(ResizableRectangleCell cell) {
		int atomicServices = getAtomicServices(cell);
		int compositeServices = cell.containsRectangleCell().size() - atomicServices;
		return (double) compositeServices / (double) (atomicServices + compositeServices);
	}

	private static int getAtomicServices(ResizableRectangleCell cell) {
		int counter = 0;
		for (RectangleCell recCell : cell.containsRectangleCell()) {
			if (isAtomticService(recCell)) {
				counter++;
			}
		}
		return counter;
	}

	private static boolean isAtomticService(RectangleCell cell) {
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			if (cell.equals((RectangleCell) edge.getTarget())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Service Group Strict Dynamicity Degree
	 */
	public static double getSGSDD(ResizableRectangleCell cell) {
		return (double) numberDynamicServices(cell) / (double) totalNumberServices(cell);
	}
	
	private static int totalNumberServices(ResizableRectangleCell cell) {
		return cell.containsRectangleCell().size();
	}
	
	private static int numberStaticServices(ResizableRectangleCell cell) {
		int counter = 0;
		for (RectangleCell recCell : cell.containsRectangleCell()) {
			if (isStaticService(recCell)) {
				counter++;
			}
		}
		return counter;
	}
	
	private static int numberDynamicServices(ResizableRectangleCell cell) {
		int counter = 0;
		for (RectangleCell recCell : cell.containsRectangleCell()) {
			if (!isStaticService(recCell)) {
				counter++;
			}
		}
		return counter;
	}
	
	private static boolean isStaticService(RectangleCell cell) {
		return cell.fidlReader.getPropertiesReader().getBinding() == FBinding.STATIC ? true : false;
	}

	/**
	 * Service Group Strict Static Degree
	 */
	public static double getSGSSD(ResizableRectangleCell cell) {
		return (double) numberStaticServices(cell) / (double) totalNumberServices(cell);
	}

	public static void getXY7() {
		// TODO
	}

	public static void getXY8() {
		// TODO
	}

	public static void getXY9() {
		// TODO
	}

}
