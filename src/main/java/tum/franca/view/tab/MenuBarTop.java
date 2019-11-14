package tum.franca.view.tab;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.edges.IEdge;
import tum.franca.graph.graph.Grid;
import tum.franca.main.MainApp;
import tum.franca.util.ColorUtil;
import tum.franca.util.RectangleUtil;

/**
 * MenuBar at the top of the Grouping Canvas.
 * 
 * @author michaelschott
 *
 */
public class MenuBarTop {

	public static boolean alignOnGrid = false;
	public static boolean isGridEnabled = false;

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
		Image gridIcon = new Image(MenuBarTop.class.getResourceAsStream("/grid.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		gridMenu.setGraphic(imageView);
		ToggleGroup toggleGroupGrid = new ToggleGroup();
		ToggleGroup toggleGroupGrid2 = new ToggleGroup();
		menuItemAlign = new RadioMenuItem("Align on Grid");
		SeparatorMenuItem seperatorItem = new SeparatorMenuItem();
		menuItemAlign.setOnAction(onClickOnMenuItemAlign);
		RadioMenuItem menuItemGrid1 = new RadioMenuItem("Enable Grid");
		menuItemGrid1.setOnAction(onClickEnableGrid);
		RadioMenuItem menuItemGrid2 = new RadioMenuItem("Disable Grid");
		menuItemGrid2.setOnAction(onClickDisableGrid);
		menuItemAlign.setToggleGroup(toggleGroupGrid2);
		menuItemGrid1.setToggleGroup(toggleGroupGrid);
		menuItemGrid2.setToggleGroup(toggleGroupGrid);
		if (!alignOnGrid) {
			menuItemAlign.setSelected(false);
		} else {
			menuItemAlign.setSelected(true);
		}
		menuItemGrid2.setSelected(true);
		gridMenu.getItems().addAll(menuItemAlign, seperatorItem, menuItemGrid1, menuItemGrid2);

		// Service Groups
		Menu groupsMenu = new Menu("Service Groups");
		Image groupIcon = new Image(MenuBarTop.class.getResourceAsStream("/group.png"));
		ImageView imageView2 = new ImageView(groupIcon);
		imageView2.setFitHeight(15);
		imageView2.setFitWidth(15);
		groupsMenu.setGraphic(imageView2);
		MenuItem menuItemGroups = new MenuItem("Rearrange Service Groups");
		groupsMenu.getItems().add(menuItemGroups);
		MenuItem menuItemGroups2 = new MenuItem("Darker");
		groupsMenu.getItems().add(menuItemGroups2);
		MenuItem menuItemGroups3 = new MenuItem("Brighter");
		groupsMenu.getItems().add(menuItemGroups3);
		menuItemGroups.setOnAction(onClickOnMenuGroups);
		menuItemGroups2.setOnAction(onClickOnMenuGroups2);
		menuItemGroups3.setOnAction(onClickOnMenuGroups3);

		// Relations
		Menu edgesMenu = new Menu("Relations");
		Image edgeIcon = new Image(MenuBarTop.class.getResourceAsStream("/relation.png"));
		ImageView imageView3 = new ImageView(edgeIcon);
		imageView3.setFitHeight(15);
		imageView3.setFitWidth(15);
		edgesMenu.setGraphic(imageView3);
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioMenuItem menuItem0 = new RadioMenuItem("Enable All");
		menuItem0.setOnAction(onClickEnableAll);
		RadioMenuItem menuItem1 = new RadioMenuItem("Enable Coehison");
		menuItem1.setOnAction(onClickEnableCohesion);
		RadioMenuItem menuItem2 = new RadioMenuItem("Enable Coupling");
		menuItem2.setOnAction(onClickEnableCoupling);
		RadioMenuItem menuItem3 = new RadioMenuItem("Disable All");
		menuItem3.setOnAction(onClickDisableAll);
		menuItem0.setToggleGroup(toggleGroup);
		menuItem1.setToggleGroup(toggleGroup);
		menuItem2.setToggleGroup(toggleGroup);
		menuItem3.setToggleGroup(toggleGroup);
		menuItem0.setSelected(true);
		edgesMenu.getItems().addAll(menuItem0, menuItem1, menuItem2, menuItem3);

		// Adding
		menuBar.getMenus().addAll(gridMenu, groupsMenu, edgesMenu);
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
	static EventHandler<ActionEvent> onClickEnableGrid = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			isGridEnabled = true;
			Grid.add();
		}

	};

	/**
	 * Disable grids.
	 */
	static EventHandler<ActionEvent> onClickDisableGrid = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			isGridEnabled = false;
			Grid.remove();
		}

	};

	/**
	 * Show all.
	 */
	static EventHandler<ActionEvent> onClickEnableAll = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			ObservableList<IEdge> edges = MainApp.graph.getModel().getAddedEdges();
			for (IEdge iEdge : edges) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).getGroup().setVisible(true);
				}
			}
		}
	};

	/**
	 * Show cohesion.
	 */
	static EventHandler<ActionEvent> onClickEnableCohesion = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			for (IEdge iEdge : MainApp.graph.getModel().getAddedEdges()) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).getGroup().setVisible(true);
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
									((Edge) edge).getGroup().setVisible(false);
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
	static EventHandler<ActionEvent> onClickEnableCoupling = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			for (IEdge iEdge : MainApp.graph.getModel().getAddedEdges()) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).getGroup().setVisible(false);
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
									((Edge) edge).getGroup().setVisible(true);
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
	static EventHandler<ActionEvent> onClickDisableAll = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			ObservableList<IEdge> edges = MainApp.graph.getModel().getAddedEdges();
			for (IEdge iEdge : edges) {
				if (iEdge instanceof Edge) {
					((Edge) iEdge).getGroup().setVisible(false);
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
						tum.franca.util.Binding.bind(((ResizableRectangleCell) cell).getPane(),
								((ResizableRectangleCell) cell).style.ordinal());
						MainApp.graph.getGraphic(cell).relocate(x, y);
						tum.franca.util.Binding.unbind(((ResizableRectangleCell) cell).getPane(),
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

	static EventHandler<ActionEvent> onClickOnMenuGroups2 = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			ColorUtil.colorIndex++;
			ColorUtil.recolorCanvas();
		}
	};

	static EventHandler<ActionEvent> onClickOnMenuGroups3 = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			ColorUtil.colorIndex--;
			ColorUtil.recolorCanvas();
		}
	};

}
