package tum.franca.tabs;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.graph.Grid;
import tum.franca.main.MainApp;
import tum.franca.main.Metrics;
import tum.franca.save.RedoManager;
import tum.franca.save.RedoManagerWrapper;
import tum.franca.view.treeView.InnerTabPane;

/**
 * 
 * @author michaelschott
 *
 */
public class TabPaneSetter {

	private Button createTabButton() {
		Button button = new Button();
		Image gridIcon = new Image(TabPaneSetter.class.getResourceAsStream("/save.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button.setGraphic(imageView);
		button.setOnAction(onSaveClicked);
		return button;
	}

	private static Button createTabButton2() {
		Button button = new Button();
		Image gridIcon = new Image(TabPaneSetter.class.getResourceAsStream("/undo.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button.setGraphic(imageView);
		button.setOnAction(onUndoClicked);
		return button;
	}

	private static Button createTabButton3() {
		Button button = new Button();
		Image gridIcon = new Image(TabPaneSetter.class.getResourceAsStream("/redo.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button.setGraphic(imageView);
		button.setOnAction(onRedoClicked);
		return button;
	}

	static EventHandler<ActionEvent> onUndoClicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			RedoManagerWrapper.undo();
		}

	};

	static EventHandler<ActionEvent> onRedoClicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			RedoManagerWrapper.redo();
		}

	};

	EventHandler<ActionEvent> onSaveClicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			for (ICell iCell2 : MainApp.graph.getModel().getAddedCells()) {
				if (iCell2 instanceof ResizableRectangleCell) {
					String string1 = ((ResizableRectangleCell) iCell2).group;
					String string2 = ((ResizableRectangleCell) iCell2).getName();
					String[] stringArray1 = string1.split(" ");
					String[] stringArray2 = string2.split(" ");
					for (int i = 0; i < stringArray1.length; i++) {
						for (int j = 0; j < stringArray2.length; j++) {
							if (i == j) {
								for (RectangleCell cell : ((ResizableRectangleCell) iCell2).getRectangleCells()) {
									System.out.println(cell.name);
									cell.fidlReader.getPropertiesReader().setProperty(stringArray1[i], stringArray2[i]);
								}
							}
						}
					}
				}
			}
		}

	};

	public static TabPane tabPane;
	public static Button undo;
	public static Button redo;

	/**
	 * Automatic creation of new Tabs. Automatic change of graph object.
	 */
	public void setCanvas() {

		// Setting menu at the top
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(MenuBarTop.getMenuBar());

		if (tabPane == null || tabPane.getTabs().size() == 0) {

			// HBox of control buttons
			HBox hbox = new HBox();
			undo = createTabButton2();
			redo = createTabButton3();
			undo.setDisable(true);
			redo.setDisable(true);
			hbox.getChildren().add(undo);
			hbox.getChildren().add(redo);
			hbox.getChildren().add(createTabButton());

			BorderPane subBorderPane = new BorderPane();

			// Default Tabs
			this.tabPane = new TabPane();
			StringProperty sP = new SimpleStringProperty("Current Group");
			Tab tab1 = new RenameableTab(sP);
			StringProperty sP2 = new SimpleStringProperty("New Group");
			Tab tab2 = new RenameableTab(sP2);

			// Three Tabs at the bottom
			TabPane innerTabPane = InnerTabPane.getInnerTabPane();
			InnerTabPane.setContentMain(MainApp.graph.getCanvas());
			tab1.setContent(innerTabPane);

			MainApp.graphList[0] = MainApp.graph;
			tabPane.getSelectionModel().select(0);
			tabPane.getTabs().addAll(tab1, tab2);

			// If clicked on another tabPane
			tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
				if (MainApp.graphList[newTab.getTabPane().getSelectionModel().getSelectedIndex()] != null) {
					MainApp.graph = MainApp.graphList[newTab.getTabPane().getSelectionModel().getSelectedIndex()];
					Metrics.setAll();
				}
			});

			// Anchor the controls
			AnchorPane anchor = new AnchorPane();
			anchor.getChildren().addAll(tabPane, hbox);
			AnchorPane.setTopAnchor(hbox, 3.0);
			AnchorPane.setRightAnchor(hbox, 5.0);
			AnchorPane.setTopAnchor(tabPane, 1.0);
			AnchorPane.setRightAnchor(tabPane, 1.0);
			AnchorPane.setLeftAnchor(tabPane, 1.0);
			AnchorPane.setBottomAnchor(tabPane, 1.0);
			borderPane.setCenter(anchor);
			MainApp.root.getItems().set(1, borderPane);

		} else {

			// Three Tabs at the bottom
			TabPane innerTabPane = InnerTabPane.getInnerTabPane();
			InnerTabPane.setContentMain(MainApp.graph.getCanvas());
			tabPane.getSelectionModel().getSelectedItem().setContent(innerTabPane);
			Metrics.setAll();

			MainApp.graphList[tabPane.getSelectionModel().getSelectedIndex()] = MainApp.graph;
			ObservableList<Tab> tabList = tabPane.getTabs();
			boolean freeSpace = false;
			for (Tab tab : tabList) {
				if (tab.getContent() == null) {
					freeSpace = true;
				}
			}
			if (!freeSpace) {
				StringProperty sP2 = new SimpleStringProperty("New Group");
				Tab tab2 = new RenameableTab(sP2);
				tabPane.getTabs().add(tab2);
			}
		}
	}

	public int getNumberOfTabs() {
		return tabPane.getTabs().size();
	}

}
