package tum.franca.graph.cells.servicegroup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import tum.franca.util.propertyfunction.PropertyFunctionWindow;

/**
 * 
 * @author michaelschott
 *
 */
public class ResContxtMenu {

	private static ContextMenu contextMenu;

	/**
	 * 
	 * @param cell
	 * @param pane
	 * @param mouseEvent
	 * @return ContextMenu
	 * 
	 * ContextMenu for opening the property Function.
	 */
	public static ContextMenu getContextMenu(ResizableRectangleCell cell, Pane pane, MouseEvent mouseEvent) {
		if (contextMenu == null) {
			contextMenu = new ContextMenu();
		}

		contextMenu.getItems().clear();
		MenuItem item = new MenuItem("Property Function");
		contextMenu.getItems().add(item);

		EventHandler<ActionEvent> actionHandler = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new PropertyFunctionWindow().showPropertyFunctionWindow(cell);
				
				contextMenu.hide();
			}
		};

		item.setOnAction(actionHandler);

		contextMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
		return contextMenu;
	}

}
