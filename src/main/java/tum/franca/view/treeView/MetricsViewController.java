package tum.franca.view.treeView;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.text.Text;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell.GroupType;
import tum.franca.main.MainApp;
import tum.franca.main.Metrics;

/**
 * 
 * @author michaelschott
 *
 */
public class MetricsViewController {
	
	@FXML
	private Text services;
	@FXML
	private Text groups;
	@FXML
	private Text subgroups;
	@FXML
	private Text subsubgroups;
	@FXML
	private Text mostcommon;
	@FXML
	private Text leastcommon;
	@FXML
	private Text relations;
	@FXML
	private Text coupling;
	@FXML
	private Text cohesion;
	
	@FXML
	private BarChart<Integer,Integer> barchart;
	
	@FXML
	public void initialize() throws Exception {
		setServicesAndGroups();
		mostcommon.setText(Metrics.getMostCommonProperty());
		leastcommon.setText(Metrics.getLeastCommonProperty());
		relations.setText(String.valueOf(Metrics.getRelations()));
		coupling.setText(String.valueOf(Metrics.getCoupling()));
		cohesion.setText(String.valueOf(Metrics.getCohesion()));
	}
	
	/**
	 * Set the number of Groups and subgroups.
	 */
	public void setServicesAndGroups() {
		int serviceCounter = 0, groupCounter = 0, subGroupCounter = 0, subSubGroupCounter = 0;
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
		services.setText(String.valueOf(serviceCounter));
		groups.setText(String.valueOf(groupCounter));
		subgroups.setText(String.valueOf(subGroupCounter));
		subsubgroups.setText(String.valueOf(subSubGroupCounter));
	}
	

}
