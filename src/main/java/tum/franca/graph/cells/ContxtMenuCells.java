package tum.franca.graph.cells;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;
import tum.franca.reader.StaticFidlReader;
 
/**
 * 
 * @author michaelschott
 *
 */
public class ContxtMenuCells {
	
	public static boolean binding = false;
	private static ContextMenu contextMenu;

	public static ContextMenu getContextMenu() {
		return contextMenu;
	}
	
	public static void setContextMenu(RectangleCell cell) {
		
		if (contextMenu != null && contextMenu.isShowing()) {
			contextMenu.hide();
		}
		
		contextMenu = new ContextMenu();
		
		MenuItem menu = new MenuItem("Remove Service");
		
		menu.setOnAction(e -> {
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				if (edge.getSource().equals(cell) ||edge.getTarget().equals(cell)) {
					MainApp.graph.removeEdge(edge);
					MainApp.graph.getModel().getAddedEdges().remove(edge);
				}
			}
			MainApp.graph.removeCell(cell);
			MainApp.graph.getModel().getAddedCells().remove(cell);
			StaticFidlReader.getFidlList().remove(StaticFidlReader.getFidl(cell));
			contextMenu.hide();
			
		});
		
		contextMenu.getItems().add(menu);
		
	}

 
}