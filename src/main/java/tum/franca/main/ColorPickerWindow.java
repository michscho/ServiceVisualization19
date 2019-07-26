package tum.franca.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tum.franca.graph.cells.ResizableRectangleCell;

public class ColorPickerWindow extends Application {

	public static Stage stage;

	@Override
	public void start(Stage stage) {
		if (ColorPickerWindow.stage == null) {
			ColorPickerWindow.stage = stage;
		} else {
			ColorPickerWindow.stage.close();
			ColorPickerWindow.stage = stage;
		}
	}

	public static void initColorPicker(Rectangle view, Color color1, Color colorStroke, double x, double y) {
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setValue(color1);

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.setFill(colorPicker.getValue());
			}
		});

		final Text text1 = new Text("Change Fill:");
		final Text text2 = new Text("Change Stroke:  ");

		final ColorPicker colorPicker2 = new ColorPicker();
		colorPicker2.setValue(colorStroke);

		colorPicker2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Color color = colorPicker2.getValue();
				view.setStroke(colorPicker2.getValue());
			}
		});
		

		FlowPane root = new FlowPane();
		root.setPadding(new Insets(10));
		root.setHgap(10);
		root.getChildren().addAll(text1, colorPicker, text2, colorPicker2);

		Scene scene = new Scene(root, 170, 100);

		stage.setTitle("Change Color");
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