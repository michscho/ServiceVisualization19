package tum.franca.main;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tum.franca.graph.graph.Graph;
import tum.franca.properties.PropertiesUtil;

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

	/**
	 * Start the application.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {

		// Icon Bar: MacOS
		try {
			URL iconURL = MainApp.class.getResource("visualisation.png");
			Image image = new ImageIcon(iconURL).getImage();
			com.apple.eawt.Application.getApplication().setDockIconImage(image);
		} catch (Exception e) {
			// Won't work on Windows or Linux.
		}

		MainApp.primaryStage = primaryStage;
		MainApp.root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
		
		PropertiesUtil.getAllPropertiesAsEnums();

		final Scene scene = new Scene(root, 1920, 1080);

		primaryStage.getIcons()
				.add(new javafx.scene.image.Image("https://img.icons8.com/clouds/100/000000/administrative-tools.png"));
		primaryStage.setTitle("Service Visualisation");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		    	if (VisualisationsAlerts.saveDialog()) {
		        Platform.exit();
		        System.exit(0);
		    	} else {
		    		t.consume();
		    	}
		    }
		});
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}