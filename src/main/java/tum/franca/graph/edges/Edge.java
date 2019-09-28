package tum.franca.graph.edges;

import java.awt.Point;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.graph.Graph;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class Edge extends AbstractEdge {

	public boolean custom;
	private Group group;
	private Line line;
	private Edge edge;
	public String qos;

	public Edge(ICell source, ICell target) {
		super(source, target);
	}

	@Override
	public Group getGraphic(Graph graph) {
		group = new Group();

		line = new Line();
		
		edge = this;

		final DoubleBinding sourceX = this.getSource().getXAnchor(graph, this);
		final DoubleBinding sourceY = this.getSource().getYAnchor(graph, this);
		final DoubleBinding targetX = this.getTarget().getXAnchor(graph, this);
		final DoubleBinding targetY = this.getTarget().getYAnchor(graph, this);

		line.startXProperty().bind(sourceX);
		line.startYProperty().bind(sourceY);

		line.endXProperty().bind(targetX);
		line.endYProperty().bind(targetY);

		EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				ContxtMenuEdge.setContextMenu(edge, group);
				ContxtMenuEdge.getContextMenu().show(MainApp.primaryStage, t.getScreenX(), t.getScreenY());
				t.consume();
			}
		};
		
		line.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);

		Circle circle = new Circle(2);
		circle.layoutXProperty().bind((line.startXProperty().add(line.endXProperty())).divide(2));
		circle.layoutYProperty().bind((line.startYProperty().add(line.endYProperty())).divide(2));
		Arc arc = new Arc(0, 0, 5, 5, 90, 180);
		Point point1 = new Point((int) sourceX.get(), (int) sourceY.get());
		Point point2 = new Point((int) targetX.get(), (int) targetY.get());
		arc.setStartAngle(angleOf(point1, point2) + 90);
		line.startXProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				Point point1 = new Point((int) sourceX.get(), (int) sourceY.get());
				Point point2 = new Point((int) targetX.get(), (int) targetY.get());
				arc.setStartAngle(angleOf(point1, point2) + 90);
			}
		});
		line.endXProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				Point point1 = new Point((int) sourceX.get(), (int) sourceY.get());
				Point point2 = new Point((int) targetX.get(), (int) targetY.get());
				arc.setStartAngle(angleOf(point1, point2) + 90);
			}
		});
		arc.setType(ArcType.OPEN);
		arc.setStrokeWidth(2);
		arc.setStroke(Color.BLACK);
		arc.setStrokeType(StrokeType.INSIDE);
		arc.setFill(null);
		arc.layoutXProperty().bind((line.startXProperty().add(line.endXProperty())).divide(2));
		arc.layoutYProperty().bind((line.startYProperty().add(line.endYProperty())).divide(2));
		
		arc.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
		circle.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
		arc.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> e.consume());
		circle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> e.consume());
		line.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> e.consume());

		group.getChildren().addAll(line,arc,circle);
		
		return group;
	}

	public Group getGroup() {
		return group;
	}

	public Line getLine() {
		return line;
	}

	public double angleOf(Point p1, Point p2) {
		final double deltaY = (p1.y - p2.y);
		final double deltaX = (p2.x - p1.x);
		final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
		return (result < 0) ? (360d + result) : result;
	}

}