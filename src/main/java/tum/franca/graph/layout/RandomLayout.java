package tum.franca.graph.layout;

import java.util.List;
import java.util.Random;

import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.ICell;

public class RandomLayout implements Layout {

	private final Random rnd = new Random();

	@Override
	public void execute(Graph graph) {
		final List<ICell> cells = graph.getModel().getAddedCells();

		for (final ICell cell : cells) {
			final double x = rnd.nextDouble() * 500;
			final double y = rnd.nextDouble() * 500;

			graph.getGraphic(cell).relocate(x, y);
		}
	}

}