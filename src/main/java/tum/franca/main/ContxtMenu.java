package tum.franca.main;

import java.awt.Point;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;

/**
 * 
 * @author michaelschott
 *
 */
public class ContxtMenu {

	public static boolean binding = false;
	public static String bindingStringOn = "Enable Binding to Group";
	public static String bindingStringOff = "Disable Binding to Group";
	private static ContextMenu contextMenu;

	public static ContextMenu getContextMenu() {
		return contextMenu;
	}

	public static void setContextMenu(Pane pane) {

		if (contextMenu != null && contextMenu.isShowing()) {
			contextMenu.hide();
		}
		contextMenu = new ContextMenu();

		String menuItemString = "";
		if (!binding) {
			menuItemString = bindingStringOn;
		} else {
			menuItemString = bindingStringOff;
		}

		MenuItem item = new MenuItem(menuItemString);
		item.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (!binding) {
					binding = true;
					List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {

							RectangleCell cell = (RectangleCell) iCell;
							Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
							Point point2 = ResizableRectangleCell.getPointOfRechtangle(pane.getLayoutX(),
									pane.getLayoutY(), pane.getWidth(), pane.getHeight());
							Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
							Point point4 = ResizableRectangleCell.getPointOfRechtangle(cell.pane.getLayoutX(),
									cell.pane.getLayoutY(), cell.pane.getWidth(), cell.pane.getHeight());

							if (ResizableRectangleCell.doOverlap(point, point2, point3, point4)) {
								cell.pane.layoutXProperty().bind(
										pane.layoutXProperty().subtract(pane.getLayoutX()).add(cell.pane.getLayoutX()));
								cell.pane.layoutYProperty().bind(
										pane.layoutYProperty().subtract(pane.getLayoutY()).add(cell.pane.getLayoutY()));
							}
						}
					}
				} else {
					binding = false;
					List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
					pane.layoutXProperty().unbind();
					pane.layoutYProperty().unbind();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {

							RectangleCell cell = (RectangleCell) iCell;
							Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
							Point point2 = ResizableRectangleCell.getPointOfRechtangle(pane.getLayoutX(),
									pane.getLayoutY(), pane.getWidth(), pane.getHeight());
							Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
							Point point4 = ResizableRectangleCell.getPointOfRechtangle(cell.pane.getLayoutX(),
									cell.pane.getLayoutY(), cell.pane.getWidth(), cell.pane.getHeight());

							if (ResizableRectangleCell.doOverlap(point, point2, point3, point4)) {
								cell.pane.layoutXProperty().unbind();
								cell.pane.layoutYProperty().unbind();
							}
						}
					}
				}
			}
		});

		contextMenu.getItems().addAll(item);
	}

}
