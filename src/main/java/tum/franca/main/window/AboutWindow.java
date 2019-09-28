package tum.franca.main.window;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author michaelschott
 *
 */
public class AboutWindow {
	
	public void showAboutWindow() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"));
			Stage stage = new Stage();
			stage.setTitle("About VisualFX Franca");
			stage.setScene(new Scene(root, 620, 450));
			stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
