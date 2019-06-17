package tum.franca.main;

import java.io.IOException;

import org.abego.treelayout.Configuration.Location;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.ICell;
import tum.franca.graph.graph.Model;
import tum.franca.graph.layout.AbegoTreeLayout;

public class MainApp extends Application {

	public static Stage primaryStage;
	public static Graph graph;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		MainApp.primaryStage = primaryStage;
		
		SplitPane root = FXMLLoader.load(getClass().getResource("MainApp.fxml")); 
		
						
		MainApp.graph = new Graph();
		addTreeComponents(graph);
		root.getItems().set(1, graph.getCanvas());
		final Scene scene = new Scene(root, 1920, 1080);

		primaryStage.setTitle("Service Visualisation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private void addTreeComponents(Graph graph) {
		final Model model = graph.getModel();
		graph.beginUpdate();

		final ICell cellA = new RectangleCell("Service 1");
		final ICell cellB = new RectangleCell("Service 2");
		final ICell cellC = new RectangleCell("Service 3");
		final ICell cellF = new RectangleCell("Service 4");
		final ICell cellG = new RectangleCell("Service 5");
		final ICell cellH = new RectangleCell("Service 6");
		final ICell cellI = new RectangleCell("Service 7");
		final ICell cellJ = new RectangleCell("Service 8");
		final ICell cellK = new RectangleCell("Service 9");
		final ICell cellL = new RectangleCell("Service 10");

		model.addCell(cellA);
		model.addCell(cellB);
		model.addCell(cellC);
		model.addCell(cellF);
		model.addCell(cellG);
		model.addCell(cellH);
		model.addCell(cellI);
		model.addCell(cellJ);
		model.addCell(cellK);
		model.addCell(cellL);

		final Edge edgeAB = new Edge(cellA, cellB);
		model.addEdge(edgeAB);
		final Edge edgeAC = new Edge(cellA, cellC);
		model.addEdge(edgeAC);
		final Edge edgeAH = new Edge(cellA, cellH);
		model.addEdge(edgeAH);
		final Edge edgeAI = new Edge(cellA, cellI);
		model.addEdge(edgeAI);
		final Edge edgeHK = new Edge(cellH, cellK);
		model.addEdge(edgeHK);
		final Edge edgeJK = new Edge(cellJ, cellK);
		model.addEdge(edgeJK);
		
		model.addEdge(cellC, cellF);
		model.addEdge(cellC, cellG);
		model.addEdge(cellA,cellG);
		model.addEdge(cellB, cellL);

		graph.endUpdate();
		graph.layout(new AbegoTreeLayout(200, 200, Location.Top));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}