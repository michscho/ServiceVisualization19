package tum.franca.graph.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.IGraphNode;
import tum.franca.graph.edges.IEdge;
import tum.franca.graph.layout.Layout;

/**
 * 
 * https://github.com/sirolf2009/fxgraph/blob/master/src/main/java/com/fxgraph/graph/Graph.java
 *
 */
public class Graph {

	private final Model model;
	private final PannableCanvas pannableCanvas;
	private final Map<IGraphNode, Region> graphics;
	private final NodeGestures nodeGestures;
	private final ViewportGestures viewportGestures;
	private final BooleanProperty useNodeGestures;
	private final BooleanProperty useViewportGestures;

	public Graph() {
		this(new Model());
	}

	public Graph(Model model) {
		this.model = model;

		nodeGestures = new NodeGestures(this);
		useNodeGestures = new SimpleBooleanProperty(true);
		useNodeGestures.addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				model.getAddedCells().forEach(cell -> nodeGestures.makeDraggable(getGraphic(cell)));
			} else {
				model.getAddedCells().forEach(cell -> nodeGestures.makeUndraggable(getGraphic(cell)));
			}
		});

		pannableCanvas = new PannableCanvas();
		viewportGestures = new ViewportGestures(pannableCanvas);
		useViewportGestures = new SimpleBooleanProperty(true);
		useViewportGestures.addListener((obs, oldVal, newVal) -> {
			final Parent parent = pannableCanvas.parentProperty().get();
			if (parent == null) {
				return;
			}
			if (newVal) {
				parent.addEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
				parent.addEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
				parent.addEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
			} else {
				parent.removeEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
				parent.removeEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
				parent.removeEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
			}
		});
		pannableCanvas.parentProperty().addListener((obs, oldVal, newVal) -> {
			if (oldVal != null) {
				oldVal.removeEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
				oldVal.removeEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
				oldVal.removeEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
			}
			if (newVal != null) {
				newVal.addEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
				newVal.addEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
				newVal.addEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
			}
		});

		graphics = new HashMap<>();
		addEdges(getModel().getAddedEdges());
		addCells(getModel().getAddedCells());
	}

	public PannableCanvas getCanvas() {
		return pannableCanvas;
	}

	public Model getModel() {
		return model;
	}

	public void beginUpdate() {
		getCanvas().getChildren().clear();
	}

	public void endUpdate() {

		addEdges(model.getAddedEdges());
		addCells(model.getAddedCells());

		getModel().endUpdate();
	}

	/**
	 * 
	 * Adding a single cell to the graph.
	 * 
	 * @param cell
	 */
	public void addCell(ICell cell) {
		Region cellGraphic = getGraphic(cell);
		getCanvas().getChildren().add(cellGraphic);
		if (useNodeGestures.get()) {
			nodeGestures.makeDraggable(cellGraphic);
		}
	}

	public void addCell(int x, int y, ICell cell) {
		Region cellGraphic = getGraphic(cell);
		getCanvas().getChildren().add(cellGraphic);
		if (useNodeGestures.get()) {
			nodeGestures.makeDraggable(cellGraphic);
		}
	}

	public void addEgde(IEdge edge) {
		try {
			Region edgeGraphic = getGraphic(edge);
			getCanvas().getChildren().add(edgeGraphic);
		} catch (final Exception e) {
			throw new RuntimeException("failed to add " + edge, e);
		}
	}

	private void addEdges(List<IEdge> edges) {
		edges.forEach(edge -> {
			try {
				Region edgeGraphic = getGraphic(edge);
				getCanvas().getChildren().add(edgeGraphic);
			} catch (final Exception e) {
				throw new RuntimeException("failed to add " + edge, e);
			}
		});
	}

	private void addCells(List<ICell> cells) {
		cells.forEach(cell -> addCell(cell));
	}

	public Region getGraphic(IGraphNode node) {
		try {
			if (!graphics.containsKey(node)) {
				graphics.put(node, createGraphic(node));
			}
			return graphics.get(node);
		} catch (final Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Region createGraphic(IGraphNode node) {
		return node.getGraphic(this);
	}

	public double getScale() {
		return getCanvas().getScale();
	}

	public void layout(Layout layout) {
		layout.execute(this);
	}

	public NodeGestures getNodeGestures() {
		return nodeGestures;
	}

	public BooleanProperty getUseNodeGestures() {
		return useNodeGestures;
	}

	public ViewportGestures getViewportGestures() {
		return viewportGestures;
	}

	public BooleanProperty getUseViewportGestures() {
		return useViewportGestures;
	}
}