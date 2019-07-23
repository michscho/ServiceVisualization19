package tum.franca.factory;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import tum.franca.graph.graph.Graph;
import tum.franca.main.MainApp;

public class TabPaneSetter {
	
	TabPane tabPane;
	
	public TabPaneSetter() {
	}
	
	public void setCanvas() {   
		if (tabPane == null || tabPane.getTabs().size() == 0) {
			this.tabPane = new TabPane();
			StringProperty sP = new SimpleStringProperty("Current Group");
			Tab tab1 = new RenameableTab(sP);
			StringProperty sP2 = new SimpleStringProperty("New Group");
			Tab tab2 = new RenameableTab(sP2);
			
			tab1.setContent(MainApp.graph.getCanvas());
			MainApp.graphList[0] = MainApp.graph;
			
			tabPane.getSelectionModel().select(0);
			tabPane.getTabs().addAll(tab1, tab2);
			
			tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
		        if (MainApp.graphList[newTab.getTabPane().getSelectionModel().getSelectedIndex()] != null) {
		        	MainApp.graph = MainApp.graphList[newTab.getTabPane().getSelectionModel().getSelectedIndex()];
		        }
		    });
			
			MainApp.root.getItems().set(1, tabPane);
		} else {
			tabPane.getSelectionModel().getSelectedItem().setContent(MainApp.graph.getCanvas());
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
