package tum.franca.tabs;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.RectangleUtil;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;

/**
 * MenuBar at the top of the Grouping Canvas.
 * 
 * @author michaelschott
 *
 */
class MenuBarTop {

	/**
	 * Service Groups: Rearrange Relations: Show all, show cohesion, show coupling,
	 * disable all
	 * 
	 * @return MenuBar
	 */
	static MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();

		// Service Groups
		Menu menuGroups = new Menu("Service Groups");
		MenuItem menuItemGrous = new MenuItem("Rearrange Service Groups");
		menuGroups.getItems().add(menuItemGrous);
		menuGroups.setOnAction(onClickOnMenuGroups);

		// Relations
		Menu menuEdges = new Menu("Relations");
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioMenuItem menuItem0 = new RadioMenuItem("Show All");
		menuItem0.setOnAction(onClickOnMenuItem0);
		RadioMenuItem menuItem1 = new RadioMenuItem("Show Coehison");
		menuItem1.setOnAction(onClickOnMenuItem1);
		RadioMenuItem menuItem2 = new RadioMenuItem("Show Coupling");
		menuItem2.setOnAction(onClickOnMenuItem2);
		RadioMenuItem menuItem3 = new RadioMenuItem("Disable All");
		menuItem3.setOnAction(onClickOnMenuItem3);
		menuItem0.setToggleGroup(toggleGroup);
		menuItem1.setToggleGroup(toggleGroup);
		menuItem2.setToggleGroup(toggleGroup);
		menuItem3.setToggleGroup(toggleGroup);
		menuItem0.setSelected(true);
		menuEdges.getItems().addAll(menuItem0, menuItem1, menuItem2, menuItem3);

		// Adding
		menuBar.getMenus().addAll(menuGroups, menuEdges);
		return menuBar;
	}

	/**
	 * Show all.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItem0 = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			ObservableList<IEdge> edges = MainApp.graph.getModel().getAddedEdges();
			for (IEdge iEdge : edges) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).edgeGraphic.getGroup().setVisible(true);
				}
			}
		}
	};

	/**
	 * Show cohesion.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItem1 = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			for (IEdge iEdge : MainApp.graph.getModel().getAddedEdges()) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).edgeGraphic.getGroup().setVisible(true);
				}
			}
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				ICell sourceCell = edge.getSource();
				ICell targetCell = edge.getTarget();
				for (ResizableRectangleCell groupCell1 : ((RectangleCell) sourceCell).getGroupRectangle()) {
					if (groupCell1.style.ordinal() == RectangleUtil.getHighestGroup()) {
						for (ResizableRectangleCell groupCell2 : ((RectangleCell) targetCell).getGroupRectangle()) {
							if (groupCell2.style.ordinal() == RectangleUtil.getHighestGroup()) {
								if (!groupCell1.equals(groupCell2)) {
									((Edge) edge).edgeGraphic.getGroup().setVisible(false);
								}
							}
						}
					}
				}
			}
		}
	};

	/**
	 * Show coupling.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItem2 = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			for (IEdge iEdge : MainApp.graph.getModel().getAddedEdges()) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).edgeGraphic.getGroup().setVisible(false);
				}
			}
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				ICell sourceCell = edge.getSource();
				ICell targetCell = edge.getTarget();
				for (ResizableRectangleCell groupCell1 : ((RectangleCell) sourceCell).getGroupRectangle()) {
					if (groupCell1.style.ordinal() == RectangleUtil.getHighestGroup()) {
						for (ResizableRectangleCell groupCell2 : ((RectangleCell) targetCell).getGroupRectangle()) {
							if (groupCell2.style.ordinal() == RectangleUtil.getHighestGroup()) {
								if (!groupCell1.equals(groupCell2)) {
									((Edge) edge).edgeGraphic.getGroup().setVisible(true);
								}
							}
						}
					}
				}
			}

		}
	};

	/**
	 * Disable all.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItem3 = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			ObservableList<IEdge> edges = MainApp.graph.getModel().getAddedEdges();
			for (IEdge iEdge : edges) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).edgeGraphic.getGroup().setVisible(false);
				}
			}
		}
	};

	/**
	 * Rearranges the top group.
	 */
	static EventHandler<ActionEvent> onClickOnMenuGroups = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			int counter = 0;
			int x = 0;
			int y = 0;
			for (ICell cell : MainApp.graph.getModel().getAddedCells()) {
				if (cell instanceof ResizableRectangleCell) {
					if (((ResizableRectangleCell) cell).style.ordinal() == RectangleUtil.getHighestGroup()) {
						tum.franca.main.Binding.bind(((ResizableRectangleCell) cell).getPane(),
								((ResizableRectangleCell) cell).style.ordinal());
						MainApp.graph.getGraphic(cell).relocate(x, y);
						tum.franca.main.Binding.unbind(((ResizableRectangleCell) cell).getPane(),
								((ResizableRectangleCell) cell).style.ordinal());
						counter++;
						if (counter % 2 == 0) {
							y += ((ResizableRectangleCell) cell).getPane().getHeight() + 50;
						} else {
							x += ((ResizableRectangleCell) cell).getPane().getWidth() + 50;
						}

					}
				}
			}

		}
	};

}
