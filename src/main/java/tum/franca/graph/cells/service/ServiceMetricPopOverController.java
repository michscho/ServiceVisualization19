package tum.franca.graph.cells.service;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import tum.franca.view.metric.MetricDependency;

public class ServiceMetricPopOverController {
	
	@FXML
	private Text requires;
	@FXML
	private Text provides;
	@FXML
	private Text numberProvides;
	@FXML
	private Text numberRequires;
	
	@FXML
	public void initialize() {
		MetricDependency mD = new MetricDependency();
		mD.buildDependencyTree(RectangleCell.staticCell);
		provides.setText(mD.getNumberOfRequires() + "");
		MetricDependency mD2 = new MetricDependency();
		mD2.buildDependencyTree2(RectangleCell.staticCell);
		requires.setText(mD2.getNumberOfRequires() + "");
		MetricDependency mD3 = new MetricDependency();
		mD3.buildDependencyTree3(RectangleCell.staticCell);
		numberRequires.setText(MetricDependency.requires + "");
		MetricDependency mD4 = new MetricDependency();
		mD4.buildDependencyTree4(RectangleCell.staticCell);
		numberProvides.setText(MetricDependency.provides + "");
		MetricDependency mD5 = new MetricDependency();
		mD5.buildDependencyTree5(RectangleCell.staticCell);
		System.out.println(mD5.getNumberOfRequires() + "");
	}
	
	

}
