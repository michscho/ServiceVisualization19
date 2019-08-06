package tum.franca.view.treeView;

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
		Tab innterTab2 = new Tab("Metrics");
		Tab innterTab3 = new Tab("Fidl-File");
		innerTabPane.getTabs().addAll(innerTabMain, innterTab2, innterTab3);
		return innerTabPane;
	}
	
	public static void setContentMain(Node node) {
		innerTabMain.setContent(node);
	}
	

}
