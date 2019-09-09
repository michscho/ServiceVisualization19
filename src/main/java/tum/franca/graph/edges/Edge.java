package tum.franca.graph.edges;

import java.awt.Point;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import tum.franca.graph.cells.ContxtMenuCells;
import tum.franca.graph.cells.ContxtMenuEdge;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.graph.Graph;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;
import tum.franca.main.VisualisationsAlerts;
import tum.franca.view.treeView.SimpleTreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class Edge extends AbstractEdge {

	public EdgeGraphic edgeGraphic;
	public boolean custom;

	public Edge(ICell source, ICell target) {
		super(source, target);
	}

	@Override
	public EdgeGraphic getGraphic(Graph graph) {
		if (edgeGraphic == null) {
			this.edgeGraphic = new EdgeGraphic(graph, this);
		}
		return edgeGraphic;
	}

	public class EdgeGraphic extends Pane {

		private final Group group;
		private final Line line;

		public EdgeGraphic(Graph graph, Edge edge) {
			addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println("Clicked"));
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
			
			//MainApp.primaryStage.addEventFilter(MouseEvent.ANY, e -> System.out.println( e));
			
			
			
			EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent t) {
					VisualisationsAlerts.noFilesSelected();
					ContxtMenuEdge.setContextMenu(edge);
					ContxtMenuEdge.getContextMenu().show(line,t.getScreenX(), t.getScreenY());
				}
			};

			group.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
			line.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
			

			getChildren().add(group);

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
			group.getChildren().add(circle);
			group.getChildren().add(arc);
			
			
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

}