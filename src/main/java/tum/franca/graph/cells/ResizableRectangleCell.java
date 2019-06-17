package tum.franca.graph.cells;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;


public class ResizableRectangleCell extends AbstractCell {
	
	private final Rectangle view = new Rectangle(120, 60);
	String name;

	public ResizableRectangleCell(String name) {
		this.name = name;
	}

	@Override
	public Region getGraphic(Graph graph) {
		
		view.setStroke(Color.BLUE);
		view.setFill(new Color(0, 0, 0.2, 0.1));

		final Pane pane = new Pane(view);
		pane.setPrefSize(120, 60);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());
		CellGestures.makeResizable(pane);
		
		Text text = new Text(getName());
		text.setStyle("-fx-font: 20 arial;");
		pane.getChildren().add(text);

		return pane;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Rectangle getView() {
		return view;
	}

}
