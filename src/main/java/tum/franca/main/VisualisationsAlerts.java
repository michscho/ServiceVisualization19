package tum.franca.main;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * 
 * @author michaelschott
 *
 */
public class VisualisationsAlerts {

	public static boolean saveDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("You have unsaved files, are you sure to exit?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

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
	
	public static void noValidServiceName(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No valid Service Name: " + string);
		alert.setContentText("Please set a valid Name");

		alert.showAndWait();
	}

}
