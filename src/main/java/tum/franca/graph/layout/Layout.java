package tum.franca.graph.layout;

import tum.franca.graph.graph.Graph;

public interface Layout {

	public void execute(Graph graph);

	void execute(Graph graph, int position);

}