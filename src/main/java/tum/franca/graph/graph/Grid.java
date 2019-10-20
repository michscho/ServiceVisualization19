package tum.franca.graph.graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class Grid {
	
	/**
	 * Adding grid to canvas.
	 */
	public static void add() {
		int x = -10000;
		for (int i = 0; i < 2000; i++) {
			Line line = new Line();
			line.setStartX(x);
			line.setStartY(-5000);
			line.setEndX(x);
			line.setEndY(10000);
			line.setStrokeWidth(0.5);
			line.setStroke(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()), ((int) Color.GREY.getBlue()), 0.2));
			line.setFill(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()), ((int) Color.GREY.getBlue()), 0.2));
			x += 50;
			MainApp.graph.getCanvas().getChildren().add(line);
			line.toBack();
		}
		int y = -20000;
		for (int i = 0; i < 2000; i++) {
			Line line = new Line();
			line.setStartX(-5000);
			line.setStartY(y);
			line.setEndX(10000);
			line.setEndY(y);
			line.setStrokeWidth(0.5);
			line.setFill(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()), ((int) Color.GREY.getBlue()), 0.2));
			line.setStroke(Color.rgb(((int) Color.GREY.getRed()), ((int) Color.GREY.getGreen()), ((int) Color.GREY.getBlue()), 0.2));
			y += 50;
			MainApp.graph.getCanvas().getChildren().add(line);
			line.toBack();
		}
	}
	
	/**
	 * Removing grid from canvas.
	 */
	public static void remove() {
		List<Node> removeList = new ArrayList<>();
		for (Node node : MainApp.graph.getCanvas().getChildren()) {
			if (node instanceof Line) {
				removeList.add(node);
			}
		}
		MainApp.graph.getCanvas().getChildren().removeAll(removeList);
	}

}
