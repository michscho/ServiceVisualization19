package tum.franca.tabs;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.RectangleUtil;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.edges.IEdge;
import tum.franca.graph.graph.Grid;
import tum.franca.main.MainApp;
import tum.franca.save.SaveGraphState;

/**
 * MenuBar at the top of the Grouping Canvas.
 * 
 * @author michaelschott
 *
 */
public class MenuBarTop {

	public static boolean alignOnGrid = false;

	private static RadioMenuItem menuItemAlign;

	/**
	 * Service Groups: Rearrange Relations: Show all, show cohesion, show coupling,
	 * disable all
	 * 
	 * @return MenuBar
	 */
	static MenuBar getMenuBar() {
		
		MenuBar menuBar = new MenuBar();

		// Grid
		Menu gridMenu = new Menu("Grid");
		Image gridIcon = new Image(MenuBarTop.class.getResourceAsStream("grid.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		gridMenu.setGraphic(imageView);
		ToggleGroup toggleGroupGrid = new ToggleGroup();
		ToggleGroup toggleGroupGrid2 = new ToggleGroup();
		menuItemAlign = new RadioMenuItem("Align on Grid");
		SeparatorMenuItem seperatorItem = new SeparatorMenuItem();
		menuItemAlign.setOnAction(onClickOnMenuItemAlign);
		RadioMenuItem menuItemGrid1 = new RadioMenuItem("Show Grids");
		menuItemGrid1.setOnAction(onClickOnMenuItemGrid1);
		RadioMenuItem menuItemGrid2 = new RadioMenuItem("Disable Grids");
		menuItemGrid2.setOnAction(onClickOnMenuItemGrid2);
		menuItemAlign.setToggleGroup(toggleGroupGrid2);
		menuItemGrid1.setToggleGroup(toggleGroupGrid);
		menuItemGrid2.setToggleGroup(toggleGroupGrid);
		menuItemAlign.setSelected(false);
		menuItemGrid2.setSelected(true);
		gridMenu.getItems().addAll(menuItemAlign, seperatorItem, menuItemGrid1, menuItemGrid2);

		// Service Groups
		Menu groupsMenu = new Menu("Service Groups");
		Image groupIcon = new Image(MenuBarTop.class.getResourceAsStream("group.png"));
		ImageView imageView2 = new ImageView(groupIcon);
		imageView2.setFitHeight(15);
		imageView2.setFitWidth(15);
		groupsMenu.setGraphic(imageView2);
		MenuItem menuItemGrous = new MenuItem("Rearrange Service Groups");
		groupsMenu.getItems().add(menuItemGrous);
		groupsMenu.setOnAction(onClickOnMenuGroups);

		// Relations
		Menu edgesMenu = new Menu("Relations");
		Image edgeIcon = new Image(MenuBarTop.class.getResourceAsStream("relation.png"));
		ImageView imageView3 = new ImageView(edgeIcon);
		imageView3.setFitHeight(15);
		imageView3.setFitWidth(15);
		edgesMenu.setGraphic(imageView3);
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
		edgesMenu.getItems().addAll(menuItem0, menuItem1, menuItem2, menuItem3);

		// Adding
		menuBar.getMenus().addAll(undoRedoMenu, gridMenu, groupsMenu, edgesMenu);
		return menuBar;
	}

	/**
	 * Toggle Align boolean.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItemAlign = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if (alignOnGrid) {
				alignOnGrid = false;
				menuItemAlign.setSelected(false);
			} else {
				alignOnGrid = true;
				menuItemAlign.setSelected(true);
			}
		}

	};

	/**
	 * Show grids.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItemGrid1 = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			Grid.add();
		}

	};

	/**
	 * Disable grids.
	 */
	static EventHandler<ActionEvent> onClickOnMenuItemGrid2 = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			Grid.remove();
		}

	};

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
