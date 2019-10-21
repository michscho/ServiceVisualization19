package tum.franca.main.window;

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
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
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

	public static void initColorPicker(Rectangle view, Color color1, Color colorStroke, double x, double y,
			ResizableRectangleCell cell) {
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setValue(color1);

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Color color = Color.color(colorPicker.getValue().getRed(), colorPicker.getValue().getGreen(),
						colorPicker.getValue().getBlue(), 0.9);
				view.setFill(color.brighter());

				for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
					((Edge) edge).toFront();
				}
				for (ICell cell : MainApp.graph.getModel().getAddedCells()) {
					if (cell instanceof RectangleCell) {
						((RectangleCell) cell).pane.toFront();
					}
				}
			}
		});

		final Text text1 = new Text("Change Fill:");
		final Text text2 = new Text("Change Stroke:  ");

		final ColorPicker colorPicker2 = new ColorPicker();
		colorPicker2.setValue(colorStroke);

		colorPicker2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (colorPicker2.getValue().getOpacity() < 0.4) {
					view.setStroke(Color.color(colorPicker2.getValue().getRed(), colorPicker2.getValue().getGreen(),
							colorPicker2.getValue().getBlue(), colorPicker2.getValue().getOpacity()));
				} else {
					view.setStroke(Color.color(colorPicker2.getValue().getRed(), colorPicker2.getValue().getGreen(),
							colorPicker2.getValue().getBlue(), colorPicker2.getValue().getOpacity()));
				}
			}
		});

		FlowPane root = new FlowPane();
		root.setPadding(new Insets(10));
		root.setHgap(10);
		root.getChildren().addAll(text1, colorPicker, text2, colorPicker2);

		Scene scene = new Scene(root, 170, 150);

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