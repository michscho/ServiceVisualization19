package tum.franca.view.metric;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell.GroupType;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class MetricsViewController {
	@FXML
	private TreeTableView<MetricsEntry> tableView;

	@FXML
	private BarChart<Integer, Integer> barchart;

	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() throws Exception {
		TreeTableColumn<MetricsEntry, String> metricsName = new TreeTableColumn<MetricsEntry, String>("Metrics Name");
		metricsName.impl_setWidth(200);
		TreeTableColumn<MetricsEntry, String> metricsValue = new TreeTableColumn<MetricsEntry, String>("Value");
		TreeTableColumn<MetricsEntry, String> metricsDescription = new TreeTableColumn<MetricsEntry, String>("Description");
		metricsDescription.impl_setWidth(450);
		
		tableView.getColumns().addAll(metricsName,metricsValue, metricsDescription);

		MetricsEntry eRoot = new MetricsEntry("Metrics", "00", "Not visible");
		
		setServicesAndGroups();
		MetricsEntry e1 = new MetricsEntry("General", "", "Simple overall counts");
		MetricsEntry e11 = new MetricsEntry("# Services", String.valueOf(serviceCounter), "");
		MetricsEntry e12 = new MetricsEntry("# Groups", String.valueOf(groupCounter), "");
		MetricsEntry e13 = new MetricsEntry("# SubGroups", String.valueOf(subGroupCounter), "");
		MetricsEntry e14 = new MetricsEntry("# SubSubGroups", String.valueOf(subSubGroupCounter), "");
		
		MetricsEntry e2 = new MetricsEntry("Struturcal", "", "Metrics like Coupling and Cohesion");
		MetricsEntry e21 = new MetricsEntry("Relation", String.valueOf(GeneralMetrics.getRelations()), "");
		MetricsEntry e22 = new MetricsEntry("Cohesion", String.valueOf(GeneralMetrics.getCohesion()), "Measures the relations within a group");
		MetricsEntry e23 = new MetricsEntry("Coupling", String.valueOf(GeneralMetrics.getCoupling()), "Measures the relations between groups");
		MetricsEntry e24 = new MetricsEntry("Avg. Coupling", String.format("%.02f", GeneralMetrics.getAverageCouplingOfService()), "");
		MetricsEntry e25 = new MetricsEntry("Avg. Cohesion", String.format("%.02f", GeneralMetrics.getAverageCohesionOfService()), "");
		
		MetricsEntry e3 = new MetricsEntry("Properties", "", "");
		MetricsEntry e31 = new MetricsEntry("Most common", GeneralMetrics.getMostCommonProperty(), "");
		MetricsEntry e32 = new MetricsEntry("Least common", GeneralMetrics.getLeastCommonProperty(), "");
		
		MetricsEntry e4 = new MetricsEntry("Special", "", "Metrics in relation to Groups");
		MetricsEntry e41 = new MetricsEntry("Avg. Services / Group", String.format("%.02f", GeneralMetrics.getAvgCouplingPerGroup()), "");
		MetricsEntry e42 = new MetricsEntry("Avg. Coupling / Group", String.format("%.02f", GeneralMetrics.getAvgServicePerGroup()), "");

		
		// Items
		TreeItem<MetricsEntry> itemRoot = new TreeItem<MetricsEntry>(eRoot);
		
		TreeItem<MetricsEntry> item1 = new TreeItem<MetricsEntry>(e1);
		TreeItem<MetricsEntry> item11 = new TreeItem<MetricsEntry>(e11);
		TreeItem<MetricsEntry> item12 = new TreeItem<MetricsEntry>(e12);
		TreeItem<MetricsEntry> item13 = new TreeItem<MetricsEntry>(e13);
		TreeItem<MetricsEntry> item14 = new TreeItem<MetricsEntry>(e14);
		item1.getChildren().addAll(item11, item12, item13, item14);
		
		TreeItem<MetricsEntry> item2 = new TreeItem<MetricsEntry>(e2);
		TreeItem<MetricsEntry> item21 = new TreeItem<MetricsEntry>(e21);
		TreeItem<MetricsEntry> item22 = new TreeItem<MetricsEntry>(e22);
		TreeItem<MetricsEntry> item23 = new TreeItem<MetricsEntry>(e23);
		TreeItem<MetricsEntry> item24 = new TreeItem<MetricsEntry>(e24);
		TreeItem<MetricsEntry> item25 = new TreeItem<MetricsEntry>(e25);
		item2.getChildren().addAll(item21, item22, item23, item24, item25);

		TreeItem<MetricsEntry> item3 = new TreeItem<MetricsEntry>(e3);
		TreeItem<MetricsEntry> item31 = new TreeItem<MetricsEntry>(e31);
		TreeItem<MetricsEntry> item32 = new TreeItem<MetricsEntry>(e32);
		item3.getChildren().addAll(item31, item32);
		
		TreeItem<MetricsEntry> item4 = new TreeItem<MetricsEntry>(e4);
		TreeItem<MetricsEntry> item41 = new TreeItem<MetricsEntry>(e41);
		TreeItem<MetricsEntry> item42 = new TreeItem<MetricsEntry>(e42);
		item4.getChildren().addAll(item41, item42);
		
		itemRoot.getChildren().addAll(item1, item2, item3, item4);
		
		metricsName.setCellValueFactory(new TreeItemPropertyValueFactory<MetricsEntry, String>("name"));
		metricsValue.setCellValueFactory(new TreeItemPropertyValueFactory<MetricsEntry, String>("value"));
		metricsDescription.setCellValueFactory(new TreeItemPropertyValueFactory<MetricsEntry, String>("description"));

		 
		// Set root Item for Tree
		tableView.setRoot(itemRoot);
		tableView.setShowRoot(false);

		
		
		TreeItem<String> rootNode = new TreeItem<String>("Metrics");

		rootNode.getChildren().add(new TreeItem<String>("General"));
		rootNode.getChildren().add(new TreeItem<String>("Structural"));
		rootNode.getChildren().add(new TreeItem<String>("Properties"));
		rootNode.getChildren().add(new TreeItem<String>("Special"));

	}
	
	int serviceCounter = 0, groupCounter = 0, subGroupCounter = 0, subSubGroupCounter = 0;

	/**
	 * Set the number of Groups and subgroups.
	 */
	public void setServicesAndGroups() {
		serviceCounter = 0;
		groupCounter = 0;
		subGroupCounter = 0;
		subSubGroupCounter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				serviceCounter++;
			}
			if (iCell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) iCell).style == GroupType.TOPLEVEL) {
					groupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == GroupType.SUBLEVEL) {
					subGroupCounter++;
				}
				if (((ResizableRectangleCell) iCell).style == GroupType.SUBSUBLEVEL) {
					subSubGroupCounter++;
				}
			}
		}
	}

}
