package tum.franca.main;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author michaelschott
 *
 */
public class VisualisationsAlerts {
	
	public static void wrongGrouping() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wrong grouping");
		alert.setHeaderText(null);
		alert.setContentText("Please provide a property for grouping 1. then for 2. and ...");
		alert.showAndWait();
	}

	
	public static void noFilesSelected() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("No files selected");
		alert.setHeaderText(null);
		alert.setContentText("Please select files");
		
		alert.showAndWait();
	}
	
	public static void noValidInteger(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No valid integer: " + string);
		alert.setContentText("Please provide a valid integer (ex.: 109).");

		alert.showAndWait();
	}

}
