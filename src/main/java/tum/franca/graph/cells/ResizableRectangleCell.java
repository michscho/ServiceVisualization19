package tum.franca.graph.cells;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;


public class ResizableRectangleCell extends AbstractCell {
	
	private final Rectangle view; 
	private String name;
	private int x;
	private int y;

	public ResizableRectangleCell(int x, int y, String name) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.view = new Rectangle(x,y);
	}

	@Override
	public Region getGraphic(Graph graph) {
		
		view.setStroke(Color.BLUE);
		view.setFill(new Color(0, 0, 0.2, 0.1));

		final Pane pane = new Pane(view);
		pane.setPrefSize(x, y);
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
