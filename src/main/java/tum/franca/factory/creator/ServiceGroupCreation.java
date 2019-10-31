package tum.franca.factory.creator;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author michaelschott
 *
 */
public class ServiceGroupCreation extends Application {
	
	public static Stage stage;

	@Override
	public void start(Stage stage) {
		if (ServiceCreation.stage == null) {
			ServiceCreation.stage = stage;
		} else {
			ServiceCreation.stage.close();
			ServiceCreation.stage = stage;
		}
	}
	
	public static int x;
	public static int y;
	
	public static void initServiceGroupCreationWithLocation(double windowLocationX, double windowLocationY, int x, int y) {
		ServiceGroupCreation.x = x;
		ServiceGroupCreation.y = y;
		if (ServiceGroupCreation.stage == null) {
			ServiceGroupCreation.stage = new Stage();
		} else {
			ServiceGroupCreation.stage.close();
			ServiceGroupCreation.stage = new Stage();
		}
		Pane root;
	
		try {
			root = FXMLLoader.load(ServiceCreation.class.getResource("/fxml/ServiceGroup.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Create Group");
			stage.setResizable(false);
			stage.setX(windowLocationX);
			stage.setY(windowLocationY);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
