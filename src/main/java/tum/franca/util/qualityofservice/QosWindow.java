package tum.franca.util.qualityofservice;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author michaelschott
 *
 */
public class QosWindow extends Application {
	
	public QosWindowController qosWindowController;
	
	public void showQosWindow() {
		Parent root;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getResource("/fxml/QualityOfService.fxml").openStream());
			qosWindowController = (QosWindowController) fxmlLoader.getController();
			Stage stage = new Stage();
			stage.setTitle("Quality of Service Settings");
			stage.setScene(new Scene(root, 650, 400));
			stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {	
		showQosWindow();
	}
	
	public QosWindowController getController() {
		return this.qosWindowController;
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