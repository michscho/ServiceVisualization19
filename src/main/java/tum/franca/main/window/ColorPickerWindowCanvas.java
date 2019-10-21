package tum.franca.main.window;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class ColorPickerWindowCanvas extends Application {

	public static Stage stage;

	@Override
	public void start(Stage stage) {
		if (ColorPickerWindowCanvas.stage == null) {
			ColorPickerWindowCanvas.stage = stage;
		} else {
			ColorPickerWindowCanvas.stage.close();
			ColorPickerWindowCanvas.stage = stage;
		}
	}

	public static void initColorPicker(double x, double y) {
		final ColorPicker colorPicker = new ColorPicker();
		// TODO colorPicker.setValue();

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Color color = colorPicker.getValue();
				String webFormat = String.format("#%02x%02x%02x",
					    (int) (255 * color.getRed()),
					    (int) (255 * color.getGreen()),
					    (int) (255 * color.getBlue()));
				MainApp.root.getItems().get(1).setStyle("-fx-background-color: " + webFormat + ";");

			}
		});

		final Text text1 = new Text("Change Background:");

		FlowPane root = new FlowPane();
		root.setPadding(new Insets(10));
		root.setHgap(10);
		root.getChildren().addAll(text1, colorPicker);

		Scene scene = new Scene(root, 170, 60);
		
		stage = new Stage();

		stage.setTitle("Background");
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(x);
		stage.setY(y);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}