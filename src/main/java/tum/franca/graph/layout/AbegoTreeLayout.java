package tum.franca.graph.layout;

import org.abego.treelayout.Configuration;
import org.abego.treelayout.Configuration.Location;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.ICell;

public class AbegoTreeLayout implements Layout {

	private final Configuration<ICell> configuration;

	public AbegoTreeLayout() {
		this(100, 45, Location.Top);
	}

	public AbegoTreeLayout(double gapBetweenLevels, double gapBetweenNodes, Location location) {
		this(new DefaultConfiguration<ICell>(gapBetweenLevels, gapBetweenNodes, location));
	}

	public AbegoTreeLayout(Configuration<ICell> configuration) {
		this.configuration = configuration;
	}

	@Override
	public void execute(Graph graph) {
		execute(graph, 0);
	}

	@Override
	public void execute(Graph graph, int position) {
		final DefaultTreeForTreeLayout<ICell> layout = new DefaultTreeForTreeLayout<>(graph.getModel().getRoot());
		addRecursively(layout, graph.getModel().getRoot());
		final NodeExtentProvider<ICell> nodeExtentProvider = new NodeExtentProvider<ICell>() {

			@Override
			public double getWidth(ICell tn) {
				if(tn == graph.getModel().getRoot()) {
					return position;
				}
				return graph.getGraphic(tn).getWidth();
			}

			@Override
			public double getHeight(ICell tn) {
				if(tn == graph.getModel().getRoot()) {
					return position;
				}
				return graph.getGraphic(tn).getHeight();
			}
		};
		final TreeLayout<ICell> treeLayout = new TreeLayout<>(layout, nodeExtentProvider, configuration);
		treeLayout.getNodeBounds().entrySet().stream().filter(entry -> entry.getKey() != graph.getModel().getRoot()).forEach(entry -> {
			graph.getGraphic(entry.getKey()).setLayoutX(entry.getValue().getX());
			graph.getGraphic(entry.getKey()).setLayoutY(entry.getValue().getY());
		});
	}

	public void addRecursively(DefaultTreeForTreeLayout<ICell> layout, ICell node) {
		node.getCellChildren().forEach(cell -> {
			if(!layout.hasNode(cell)) {
				layout.addChild(node, cell);
				addRecursively(layout, cell);
			}
		});
	}

}
