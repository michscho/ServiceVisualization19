package tum.franca.graph.cells;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;
import tum.franca.view.treeView.SimpleTreeViewCreator;


public class RectangleCell extends AbstractCell {
	
	String name;
	HashMap<Integer, Integer> groupNumbers;
	public boolean used;
	public StackPane pane; 
	public Rectangle view;

	public RectangleCell(String name, HashMap<Integer, Integer> groupNumbers) {
		this.name = name;
		this.groupNumbers = groupNumbers;
	}
	
	public HashMap<Integer, Integer> getGrouping() {
		return groupNumbers;
	}

	

	@Override
	public Region getGraphic(Graph graph) {
		view = new Rectangle(80, 40);

		view.setStroke(Color.BLACK);
		view.setFill(Color.WHITE);

		pane = new StackPane(view);
		pane.setPrefSize(80, 40);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());
		
		Text text = new Text(getName());
		pane.getChildren().add(text);
			    
		pane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
            	SimpleTreeViewCreator treeView = new SimpleTreeViewCreator(name);
                treeView.createSimpleTree();
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
