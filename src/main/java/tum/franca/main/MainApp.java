package tum.franca.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import tum.franca.graph.graph.Graph;

/**
 * 
 * @author michaelschott
 *
 */
public class MainApp extends Application {

	public static Stage primaryStage;
	public static Graph graph;
	public static SplitPane root;

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		MainApp.primaryStage = primaryStage;
		
		MainApp.root = FXMLLoader.load(getClass().getResource("MainApp.fxml")); 
						
		final Scene scene = new Scene(root, 1920, 1080);

		primaryStage.setTitle("Service Visualisation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}