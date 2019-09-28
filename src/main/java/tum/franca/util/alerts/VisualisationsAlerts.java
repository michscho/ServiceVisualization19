package tum.franca.util.alerts;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

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
	
	public static void stackTraceAlert(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText("An unexpected Exception occured");
		alert.setContentText("Error");

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

}
