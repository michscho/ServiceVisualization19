package tum.franca.graph.graph;

import java.awt.Point;
import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleUtil;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.main.MainApp;

public class NodeGestures {

	final DragContext dragContext = new DragContext();
	final Graph graph;
	static boolean pressedBefore = false;

	public NodeGestures(Graph graph) {
		this.graph = graph;
	}

	public HashMap<Node, ICell> nodeCellMap = new HashMap<>();

	public void makeDraggable(final Node node, ICell cell) {
		nodeCellMap.put(node, cell);
		node.setOnMousePressed(onMousePressedEventHandler);
		node.setOnMouseDragged(onMouseDraggedEventHandler);
		node.setOnMouseReleased(onMouseReleaseEventHandler);
	}

	public void makeUndraggable(final Node node) {
		node.setOnMousePressed(null);
		node.setOnMouseDragged(null);
		node.setOnMouseReleased(null);
	}

	final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			final Node node = (Node) event.getSource();

			final double scale = graph.getScale();

			dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
			dragContext.y = node.getBoundsInParent().getMinY() * scale - event.getScreenY();

			if (!(event.isPrimaryButtonDown() && event.isSecondaryButtonDown())) {
				pressedBefore = true;
			}
		}
	};

	final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.PRIMARY && !event.isSecondaryButtonDown() && pressedBefore) {

				final Node node = (Node) event.getSource();
				double layoutX = node.getLayoutX();
				double layoutY = node.getLayoutY();

				double offsetX = event.getScreenX() + dragContext.x;
				double offsetY = event.getScreenY() + dragContext.y;

				// adjust the offset in case we are zoomed
				final double scale = graph.getScale();

				offsetX /= scale;
				offsetY /= scale;

				if (nodeCellMap.get(node) instanceof ResizableRectangleCell) {
					ResizableRectangleCell cell = (ResizableRectangleCell) nodeCellMap.get(node);
					for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
						if (!iCell.equals(cell)) {
							if (iCell instanceof ResizableRectangleCell
									&& ((ResizableRectangleCell) iCell).style.ordinal() == cell.style.ordinal()) {
								ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell;
								Point point = new Point((int) offsetX, (int) offsetY);
								Point point2 = RectangleUtil.getPointOfRechtangle(offsetX, offsetY,
										cell.pane.getWidth(), cell.pane.getHeight());
								Point point3 = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
								Point point4 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(),
										cell2.pane.getLayoutY(), cell2.pane.getWidth(), cell2.pane.getHeight());
								if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
									return;
								}
							}
						}
					}
				}

				node.relocate(offsetX, offsetY);
				if (RectangleUtil.inconsistantBoardState()) {
					node.relocate(layoutX, layoutY);
				}
			}
		}
	};

	final EventHandler<MouseEvent> onMouseReleaseEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			pressedBefore = false;
			RectangleUtil.inconsistantBoardState2();
		}
	};

	public static class DragContext {
		double x;
		double y;
	}
}