package tum.franca.main;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.UnsupportedLookAndFeelException;

import org.franca.core.franca.FArgument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tum.franca.graph.graph.Graph;
import tum.franca.util.alerts.VisualisationsAlerts;


/**
 * 
 * @author michaelschott
 *
 */
public class MainApp extends Application {
	
	public static Stage primaryStage;
	public static Graph[] graphList = new Graph[10];
	public static Graph graph;
	public static SplitPane root;
	public static Scene scene;
	
	public MainApp() {
	}

	/**
	 * Start the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		System.out.println("************** Visualisation ***************");
		System.out.println(FArgument.class); // To test if library works		

		MainApp.primaryStage = primaryStage;
		MainApp.root = FXMLLoader.load(getClass().getResource("/MainApp.fxml"));
		
		scene = new Scene(root, 1920, 1080);

		primaryStage.getIcons()
				.add(new javafx.scene.image.Image("https://img.icons8.com/clouds/100/000000/administrative-tools.png"));
		primaryStage.setTitle("Service Visualisation");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	if (VisualisationsAlerts.saveDialog()) {
		        Platform.exit();
		        System.exit(0);
		    	} else {
		    		event.consume();
		    	}
		    }
		});
	}
	
	/**
	 * 
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}