package tum.franca.graph.graph;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * 
 * https://github.com/sirolf2009/fxgraph/blob/master/src/main/java/com/fxgraph/graph/PannableCanvas.java
 */
public class PannableCanvas extends Pane {

	private final DoubleProperty scaleProperty;

	public PannableCanvas() {
		this(new SimpleDoubleProperty(1.0));
	}

	public PannableCanvas(DoubleProperty scaleProperty) {
		super(createGrid());
		this.scaleProperty = scaleProperty;
		scaleXProperty().bind(scaleProperty);
		scaleYProperty().bind(scaleProperty);
	}
	
	private static GridPane createGrid() {

		GridPane gridPane = new GridPane();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; i < 10; j++) {
				Button button1 = new Button("Button 1");
				gridPane.add(button1, i, j);
			}
		}
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

	public double getScale() {
		return scaleProperty.get();
	}

	public void setScale(double scale) {
		scaleProperty.set(scale);
	}

	public DoubleProperty scaleProperty() {
		return scaleProperty;
	}

	public void setPivot(double x, double y) {
		setTranslateX(getTranslateX() - x);
		setTranslateY(getTranslateY() - y);
	}

	/**
	 * Mouse drag context used for scene and nodes.
	 */
	public static class DragContext {

		double mouseAnchorX;
		double mouseAnchorY;

		double translateAnchorX;
		double translateAnchorY;

	}
}

