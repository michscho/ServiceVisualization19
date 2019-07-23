package tum.franca.graph.edges;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.graph.Graph;

public class Edge extends AbstractEdge {

	private transient final StringProperty textProperty;

	public Edge(ICell source, ICell target) {
		super(source, target);
		textProperty = new SimpleStringProperty();
	}

	@Override
	public EdgeGraphic getGraphic(Graph graph) {
		return new EdgeGraphic(graph, this, textProperty);
	}

	public StringProperty textProperty() {
		return textProperty;
	}

	public static class EdgeGraphic extends Pane {

		private final Group group;
		private final Line line;

		public EdgeGraphic(Graph graph, Edge edge, StringProperty textProperty) {
			group = new Group();
			line = new Line();

			final DoubleBinding sourceX = edge.getSource().getXAnchor(graph, edge);
			final DoubleBinding sourceY = edge.getSource().getYAnchor(graph, edge);
			final DoubleBinding targetX = edge.getTarget().getXAnchor(graph, edge);
			final DoubleBinding targetY = edge.getTarget().getYAnchor(graph, edge);

			line.startXProperty().bind(sourceX);
			line.startYProperty().bind(sourceY);

			line.endXProperty().bind(targetX);
			line.endYProperty().bind(targetY);
			group.getChildren().add(line);

			getChildren().add(group);
			
			Circle circle = new Circle(2);
			circle.layoutXProperty().bind((line.startXProperty().add(line.endXProperty())).divide(2));
			circle.layoutYProperty().bind((line.startYProperty().add(line.endYProperty())).divide(2));
			Arc arc = new Arc(0, 0, 5, 5, 90, 180);
			if (sourceX.get() > targetX.get()) {
			arc.setStartAngle(90);
			} else {
			arc.setStartAngle(270);
			}
			line.startXProperty().addListener(new ChangeListener<Object>() {

				@Override
				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
					if (sourceX.get() > targetX.get()) {
						arc.setStartAngle(90);
						} else {
						arc.setStartAngle(270);
						}
				}
			});
		    arc.setType(ArcType.OPEN);
		    arc.setStrokeWidth(2);
		    arc.setStroke(Color.BLACK);
		    arc.setStrokeType(StrokeType.INSIDE);
		    arc.setFill(null);
			arc.layoutXProperty().bind((line.startXProperty().add(line.endXProperty())).divide(2));
			arc.layoutYProperty().bind((line.startYProperty().add(line.endYProperty())).divide(2));
			group.getChildren().add(circle);
			group.getChildren().add(arc);
		}

		public Group getGroup() {
			return group;
		}

		public Line getLine() {
			return line;
		}


	}

}