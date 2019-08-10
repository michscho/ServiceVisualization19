package tum.franca.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import tum.franca.main.MainApp;
import tum.franca.main.Metrics;
import tum.franca.view.treeView.InnerTabPane;

/**
 * 
 * @author michaelschott
 *
 */
public class TabPaneSetter {

	public static TabPane tabPane;

	/**
	 * Automatic creation of new Tabs. Automatic change of graph object.
	 */
	public void setCanvas() {

		// Setting menu at the top
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(MenuBarTop.getMenuBar());

		if (tabPane == null || tabPane.getTabs().size() == 0) {
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

			borderPane.setCenter(tabPane);
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
