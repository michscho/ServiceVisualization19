package tum.franca.view.treeView;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class InnerTabPane {
	
	public static Tab innerTabMain;
	
	public static TabPane getInnerTabPane() {
		TabPane innerTabPane = new TabPane();
		innerTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		innerTabPane.setSide(Side.BOTTOM);
		innerTabMain = new Tab("Visualisation");
		Tab innerTab2 = new Tab("Metrics");
		Tab innerTab3 = new Tab("Fidl-File");
		try {
			innerTab2.setContent(FXMLLoader.load(InnerTabPane.class.getResource("/MetricsView.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			innerTab3.setContent(FXMLLoader.load(InnerTabPane.class.getResource("/FidlView.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		innerTabPane.getTabs().addAll(innerTabMain, innerTab2, innerTab3);
		return innerTabPane;
	}
	
	public static void setContentMain(Node node) {
		innerTabMain.setContent(node);
	}
	

}
