package tum.franca.graph.cells;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;


public class RectangleCell extends AbstractCell {
	
	String name;
	int group;

	public RectangleCell(String name, int groupID) {
		this.name = name;
		this.group = groupID;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public Region getGraphic(Graph graph) {
		final Rectangle view = new Rectangle(80, 40);

		view.setStroke(Color.BLACK);
		view.setFill(Color.WHITE);

		final StackPane pane = new StackPane(view);
		pane.setPrefSize(80, 40);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());
		
		Text text = new Text(getName());
		pane.getChildren().add(text);
			    
		view.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                // TODO
            }
        });
		
		return pane;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
