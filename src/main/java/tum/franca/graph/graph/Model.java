package tum.franca.graph.graph;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import tum.franca.graph.cells.AbstractCell;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.edges.IEdge;

/**
 * 
 * @author michaelschott
 *
 */
public class Model{

	private final ICell root;

	private transient ObservableList<ICell> cells;

	private transient ObservableList<IEdge> edges;

	public Model() {
		root = new AbstractCell() {
			@Override
			public Region getGraphic(Graph graph) {
				return null;
			}
		};
		clear();
	}

	public void clear() {
		cells = FXCollections.observableArrayList();

		edges = FXCollections.observableArrayList();
	}

	public void clearAddedLists() {
		cells.clear();
		edges.clear();
	}

	public void endUpdate() {
		// every cell must have a parent, if it doesn't, then the graphParent is
		// the parent
		attachOrphansToGraphParent(getAddedCells());
		
	}

	public ObservableList<ICell> getAddedCells() {
		return cells;
	}
	
	public RectangleCell getRectangleCell(String id) {
		for (ICell iCell : cells) {
			if (iCell instanceof RectangleCell) {
				if (((RectangleCell) iCell).getName().equals(id)){
					return (RectangleCell) iCell;
				}
			}
		}
		return null;
	}

	public ObservableList<IEdge> getAddedEdges() {
		return edges;
	}

	public void addCell(ICell cell) {
		if(cell == null) {
			throw new NullPointerException("Cannot add a null cell");
		}
		cells.add(cell);
	}
	
	public void removeCell(ICell cell) {
		if(cell == null) {
			throw new NullPointerException("Cannot remove a null cell");
		}
		cells.remove(cell);
	}

	public void addEdge(ICell sourceCell, ICell targetCell) {
		final IEdge edge = new Edge(sourceCell, targetCell);
		addEdge(edge);
	}

	public void addEdge(IEdge edge) {
		if(edge == null) {
			throw new NullPointerException("Cannot add a null edge");
		}
		edges.add(edge);
	}

	/**
	 * Attach all cells which don't have a parent to graphParent
	 *
	 * @param cellList
	 */
	public void attachOrphansToGraphParent(List<ICell> cellList) {
		for(final ICell cell : cellList) {
			if(cell.getCellParents().size() == 0) {
				root.addCellChild(cell);
			}
		}
	}


	public ICell getRoot() {
		return root;
	}
}