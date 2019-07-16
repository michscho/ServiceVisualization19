package tum.franca.graph.edges;

import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.IGraphNode;

public interface IEdge extends IGraphNode {

	public ICell getSource();

	public ICell getTarget();

}
