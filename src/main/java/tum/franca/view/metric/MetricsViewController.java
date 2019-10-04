package tum.franca.view.metric;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.text.Text;
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
	private Text avgcoupling;
	@FXML
	private Text avgcohesion;
	@FXML 
	private Text avgServices;
	@FXML
	private Text avgCoupling;
	
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
		avgcoupling.setText(String.format("%.02f", Metrics.getAverageCouplingOfService()));
		avgcohesion.setText(String.format("%.02f", Metrics.getAverageCohesionOfService()));
		avgCoupling.setText(String.format("%.02f", Metrics.getAvgCouplingPerGroup()));
		avgServices.setText(String.format("%.02f", Metrics.getAvgServicePerGroup()));
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
