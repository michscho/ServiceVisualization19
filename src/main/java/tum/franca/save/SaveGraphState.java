package tum.franca.save;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TabPane;
import tum.franca.graph.graph.Graph;
import tum.franca.main.MainApp;
import tum.franca.main.Metrics;
import tum.franca.tabs.TabPaneSetter;
import tum.franca.view.treeView.InnerTabPane;

/**
 * 
 * @author michaelschott
 *
 */
public class SaveGraphState {

	public static List<Graph> listGraph = new ArrayList<>();
	public static int index = 0;

	public static void saveState() {
		Graph graph = new Graph();
		graph = MainApp.graph;
		listGraph.add(graph);
		index++;
	}

	public static void setUndoState() {
		Graph graph = listGraph.get(listGraph.size()-2);
		MainApp.graph = graph;
		Metrics.setAll();
		TabPane innerTabPane = InnerTabPane.getInnerTabPane();
		InnerTabPane.setContentMain(graph.getCanvas());
		TabPaneSetter.tabPane.getSelectionModel().getSelectedItem().setContent(innerTabPane);
		index--;
	}

	public static boolean hasUndoState() {
		if (listGraph.size() > 0) {
			return true;
		}
		return false;
	}

	public static void resetState() {
		listGraph.clear();
		index = 0;
	}

}
