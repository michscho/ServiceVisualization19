package tum.franca.graph.cells.service;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.edges.Edge;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class RectangleCellNodes {

	// PROPERTIES
	private static final double handleRadius = 8d;
	private static final Color nodeColor = new Color(0.2, 0.2, 0.2, 0.5);

	// DRAG AND DROP LINE
	private static Line line;
	public static RectangleCell soureRectangleCell;
	private DragStartHandler startHandler;
	private RectangleCell rectangleCell;

	// NODES
	private Circle resizeHandleN;
	private Circle resizeHandleNE;
	private Circle resizeHandleE;
	private Circle resizeHandleSE;
	private Circle resizeHandleS;
	private Circle resizeHandleSW;
	private Circle resizeHandleW;
	private Circle resizeHandleNW;

	/**
	 * Makes the nodes for drag and drop for rectangles invisible.
	 */
	void setInvisible() {
		resizeHandleN.setVisible(false);
		resizeHandleNE.setVisible(false);
		resizeHandleE.setVisible(false);
		resizeHandleSE.setVisible(false);
		resizeHandleS.setVisible(false);
		resizeHandleSW.setVisible(false);
		resizeHandleW.setVisible(false);
		resizeHandleNW.setVisible(false);
	}

	/**
	 * Makes the nodes for drag and drop for rectangles visible.
	 */
	void setVisible() {
		resizeHandleN.setVisible(true);
		resizeHandleN.toFront();
		resizeHandleNE.setVisible(true);
		resizeHandleNE.toFront();
		resizeHandleE.setVisible(true);
		resizeHandleE.toFront();
		resizeHandleSE.setVisible(true);
		resizeHandleSE.toFront();
		resizeHandleS.setVisible(true);
		resizeHandleS.toFront();
		resizeHandleSW.setVisible(true);
		resizeHandleSW.toFront();
		resizeHandleW.setVisible(true);
		resizeHandleW.toFront();
		resizeHandleNW.setVisible(true);
		resizeHandleNW.toFront();
	}

	/**
	 * 
	 * @author michaelschott
	 * 
	 * Initalizes the dragging of the line.
	 *
	 */
	class DragStartHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			for (ICell icell : MainApp.graph.getModel().getAddedCells()) {
				if (icell instanceof RectangleCell) {
					((RectangleCell) icell).cn.setVisible();
				}
			}

			RectangleCellNodes.soureRectangleCell = rectangleCell;
			Node sourceNode = (Node) event.getSource();
			line = new Line();

			if (sourceNode instanceof Circle) {
				line.startXProperty().bind(((Circle) sourceNode).centerXProperty());
				line.startYProperty().bind(((Circle) sourceNode).centerYProperty());
				line.setEndX(line.getStartX());
				line.setEndY(line.getStartY());
				MainApp.graph.getCanvas().getChildren().add(line);
				line.startFullDrag();
			}
			
		}
	}

	// *********************** NORTH ***********************
	DragNodeSupplier NORTH = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final DoubleBinding halfWidthProperty = widthProperty.divide(2);

			resizeHandleN = new Circle(handleRadius, nodeColor);
			resizeHandleN.centerXProperty().bind(xProperty.add(halfWidthProperty));
			resizeHandleN.centerYProperty().bind(yProperty);

			setUpDragging(resizeHandleN);

			return resizeHandleN;
		}
	};
	// *********************** NORTH ***********************

	// *********************** NORTH-EAST ***********************
	DragNodeSupplier NORTH_EAST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();

			resizeHandleNE = new Circle(handleRadius, nodeColor);
			resizeHandleNE.centerXProperty().bind(xProperty.add(widthProperty));
			resizeHandleNE.centerYProperty().bind(yProperty);

			setUpDragging(resizeHandleNE);

			return resizeHandleNE;
		}
	};
	// *********************** NORTH-EAST ***********************


	// *********************** EAST ***********************
	DragNodeSupplier EAST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();
			final DoubleBinding halfHeightProperty = heightProperty.divide(2);

			resizeHandleE = new Circle(handleRadius, nodeColor);
			resizeHandleE.centerXProperty().bind(xProperty.add(widthProperty));
			resizeHandleE.centerYProperty().bind(yProperty.add(halfHeightProperty));

			setUpDragging(resizeHandleE);

			return resizeHandleE;
		}
	};
	// *********************** EAST ***********************

	// *********************** SOUTH-EAST ***********************
	DragNodeSupplier SOUTH_EAST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

			resizeHandleSE = new Circle(handleRadius, nodeColor);
			resizeHandleSE.centerXProperty().bind(xProperty.add(widthProperty));
			resizeHandleSE.centerYProperty().bind(yProperty.add(heightProperty));

			setUpDragging(resizeHandleSE);

			return resizeHandleSE;
		}
	};
	// *********************** SOUTH-EAST ***********************

	// *********************** SOUTH ***********************
	DragNodeSupplier SOUTH = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final DoubleBinding halfWidthProperty = widthProperty.divide(2);
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

			resizeHandleS = new Circle(handleRadius, nodeColor);
			resizeHandleS.centerXProperty().bind(xProperty.add(halfWidthProperty));
			resizeHandleS.centerYProperty().bind(yProperty.add(heightProperty));

			setUpDragging(resizeHandleS);

			return resizeHandleS;
		}
	};
	// *********************** SOUTH ***********************

	// *********************** SOUTH-WEST ***********************
	DragNodeSupplier SOUTH_WEST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

			resizeHandleSW = new Circle(handleRadius, nodeColor);
			resizeHandleSW.centerXProperty().bind(xProperty);
			resizeHandleSW.centerYProperty().bind(yProperty.add(heightProperty));

			setUpDragging(resizeHandleSW);

			return resizeHandleSW;
		}
	};
	// *********************** SOUTH-WEST ***********************

	// *********************** WEST ***********************
	DragNodeSupplier WEST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();
			final DoubleBinding halfHeightProperty = heightProperty.divide(2);

			resizeHandleW = new Circle(handleRadius, nodeColor);
			resizeHandleW.centerXProperty().bind(xProperty);
			resizeHandleW.centerYProperty().bind(yProperty.add(halfHeightProperty));

			setUpDragging(resizeHandleW);

			return resizeHandleW;
		}
	};
	// *********************** WEST ***********************

	// *********************** NORTH-WEST ***********************
	DragNodeSupplier NORTH_WEST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();

			resizeHandleNW = new Circle(handleRadius, nodeColor);
			resizeHandleNW.centerXProperty().bind(xProperty);
			resizeHandleNW.centerYProperty().bind(yProperty);

			setUpDragging(resizeHandleNW);

			return resizeHandleNW;
		}
	};
	// *********************** NORTH-WEST ***********************

	/**
	 * 
	 * @param region
	 * @param rectangleCell
	 * 
	 * Initalization of all nodes.
	 */
	public void makeResizable(Region region, RectangleCell rectangleCell) {
		this.rectangleCell = rectangleCell;
		makeResizable(region, this.NORTH, this.NORTH_EAST, this.EAST, this.SOUTH_EAST, this.SOUTH, this.SOUTH_WEST,
				this.WEST, this.NORTH_WEST);
	}

	public double angleOf(Point p1, Point p2) {
		final double deltaY = (p1.y - p2.y);
		final double deltaX = (p2.x - p1.x);
		final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
		return (result < 0) ? (360d + result) : result;
	}

//	public void setRequiresProvides(Line line) {	
//			Circle circle = new Circle(2);
//			circle.layoutXProperty().bind((line.startXProperty().add(line.endXProperty())).divide(2));
//			circle.layoutYProperty().bind((line.startYProperty().add(line.endYProperty())).divide(2));
//			Arc arc = new Arc(0, 0, 5, 5, 90, 180);
//			
//			arc.setType(ArcType.OPEN);
//			arc.setStrokeWidth(2);
//			arc.setStroke(Color.BLACK);
//			arc.setStrokeType(StrokeType.INSIDE);
//			arc.setFill(null);
//			arc.layoutXProperty().bind((line.startXProperty().add(line.endXProperty())).divide(2));
//			arc.layoutYProperty().bind((line.startYProperty().add(line.endYProperty())).divide(2));
//			MainApp.graph.getCanvas().getChildren().add(circle);
//			MainApp.graph.getCanvas().getChildren().add(arc);
//			
//			Point point1 = new Point((int) line.getStartX(), (int) line.getStartY());
//			Point point2 = new Point((int) line.getEndX(), (int) line.getEndY());
//			arc.setStartAngle(angleOf(point1, point2) + 90);
//			line.startXProperty().addListener(new ChangeListener<Object>() {
//
//				@Override
//				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
//					Point point1 = new Point((int) line.getStartX(), (int) line.getStartY());
//					Point point2 = new Point((int) line.getEndX(), (int) line.getEndY());
//					arc.setStartAngle(angleOf(point1, point2) + 90);
//				}
//			});
//			line.endXProperty().addListener(new ChangeListener<Object>() {
//
//				@Override
//				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
//					Point point1 = new Point((int) line.getStartX(), (int) line.getStartY());
//					Point point2 = new Point((int) line.getEndX(), (int) line.getEndY());
//					arc.setStartAngle(angleOf(point1, point2) + 90);
//				}
//			});
//			}


	public void makeResizable(Region region, DragNodeSupplier... nodeSuppliers) {
		final Wrapper<Point2D> mouseLocation = new Wrapper<>();
		final List<Node> dragNodes = Arrays.stream(nodeSuppliers).map(supplier -> supplier.apply(region, mouseLocation))
				.collect(Collectors.toList());
		region.parentProperty().addListener((obs, oldParent, newParent) -> {
			for (final Node c : dragNodes) {
				final Pane currentParent = (Pane) c.getParent();
				if (currentParent != null) {
					currentParent.getChildren().remove(c);
				}
				((Pane) newParent).getChildren().add(c);
				startHandler = new DragStartHandler();
				c.setOnDragDetected(startHandler);
				c.setUserData(Boolean.TRUE);

				EventHandler<MouseDragEvent> dragReleaseHandler = evt -> {
					for (ICell icell : MainApp.graph.getModel().getAddedCells()) {
						if (icell instanceof RectangleCell) {
							((RectangleCell) icell).cn.setInvisible();
						}
					}
					if (evt.getGestureSource() == evt.getSource()
							|| rectangleCell.name.equals(RectangleCellNodes.soureRectangleCell.name)) {
						MainApp.graph.getCanvas().getChildren().remove(line);
					} else {
						Node sourceNode = (Node) evt.getSource();
						if (sourceNode instanceof Circle) {

							Edge edge = new Edge(soureRectangleCell, rectangleCell);
							MainApp.graph.getModel().addEdge(edge);
							MainApp.graph.addEgde(edge);
							
//							for (IEdge iEdge : MainApp.graph.getModel().getAddedEdges()) {
//								if (iEdge instanceof Edge) {
//									//((Edge) iEdge).getGraphic(MainApp.graph).toBack();
//								}
//							}
							
							
							// setRequiresProvides(line);

							for (ICell ICell : MainApp.graph.getModel().getAddedCells()) {
								if (ICell instanceof RectangleCell) {
									((RectangleCell) ICell).pane.toFront();
								}
							}
						}
					}
				};
				
				EventHandler<MouseEvent> dragEnteredHandler = evt -> {
					if (line != null) {

						Node node = (Node) evt.getSource();
						Bounds bounds = node.getBoundsInParent();
						line.setEndX((bounds.getMinX() + bounds.getMaxX()) / 2);
						line.setEndY((bounds.getMinY() + bounds.getMaxY()) / 2);

					}
				};

				c.setOnMouseDragReleased(dragReleaseHandler);
				c.setOnMouseDragEntered(dragEnteredHandler);

				EventHandler<MouseEvent> mouseDragged = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						if (line != null) {
							Node pickResult = t.getPickResult().getIntersectedNode();
							if (pickResult == null || pickResult.getUserData() != Boolean.TRUE) {
								line.setEndX(t.getX());
								line.setEndY(t.getY());
							}
						}
					}
				};

				EventHandler<MouseEvent> mouseReleased = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						for (ICell icell : MainApp.graph.getModel().getAddedCells()) {
							if (icell instanceof RectangleCell) {
								((RectangleCell) icell).cn.setInvisible();
							}
						}
						MainApp.graph.getCanvas().getChildren().remove(line);
						line = null;
					}
				};
				MainApp.graph.getCanvas().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragged);
				MainApp.graph.getCanvas().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleased);

				c.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						rectangleCell.cn.setVisible();
					}
				});

				c.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						rectangleCell.cn.setInvisible();
					}
				});
			}
		});
	}

	private static void setUpDragging(Node node) {
		node.setCursor(Cursor.CLOSED_HAND);
	}

	static class Wrapper<T> {
		T value;
	}

	static interface DragNodeSupplier {

		public Node apply(Region region, Wrapper<Point2D> mouseLocation);

	}

}
