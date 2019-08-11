package tum.franca.graph.layout;

import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.graph.Graph;

/**
 * 
 * @author michaelschott
 *
 */
public class GroupingLayout implements Layout {

	@Override
	public void execute(Graph graph) {
		final List<RectangleCell> cells = getRecCell(graph.getModel().getAddedCells());
		buildTreeStructure(cells, graph);
	}

	public void buildTreeStructure(List<RectangleCell> cells, Graph graph) {
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode("Tree");
		for (RectangleCell rectangleCell : cells) {
			build(rectangleCell, tree, 0);
		}
		createGroups(tree, graph, tree.getDepth());
	}

	public void build(RectangleCell cell, DefaultMutableTreeNode tree, int counter) {
		if (counter < cell.getGrouping().size()) {
			DefaultMutableTreeNode subTree = new DefaultMutableTreeNode(cell.getGrouping().get(counter));
			if (hasSameNodeChild(tree, subTree) == -1) {
				tree.add(subTree);
				build(cell, subTree, counter + 1);
			} else {
				build(cell, (DefaultMutableTreeNode) tree.getChildAt(hasSameNodeChild(tree, subTree)), counter + 1);
			}
		} else { // counter == cell.getGrouping().size()
			tree.add(new DefaultMutableTreeNode(cell));
		}
	}

	public int hasSameNodeChild(DefaultMutableTreeNode tree, DefaultMutableTreeNode subTree) {
		for (int i = 0; i < tree.getChildCount(); i++) {
			DefaultMutableTreeNode childTree = (DefaultMutableTreeNode) tree.getChildAt(i);
			if (childTree.getUserObject().toString().equals(subTree.getUserObject().toString())) {
				return i;
			}
		}
		return -1;
	}

	int x = 0;
	int y = 0;

	int xGroup = 0;
	int yGroup = 0;

	int counter = 0;

	/**
	 * 
	 * @param tree
	 * @param graph
	 * @param depth
	 */
	public void createGroups(DefaultMutableTreeNode tree, Graph graph, int depth) {
		boolean skip = false;
		int counter = 0;
		int x = 0 + xGroup;
		int y = 0 + yGroup;
		for (int i = 0; i < tree.getChildCount(); i++) {
			DefaultMutableTreeNode leafCells = (DefaultMutableTreeNode) tree.getChildAt(i);
			if (leafCells.getUserObject() instanceof RectangleCell) {
				RectangleCell cell = (RectangleCell) leafCells.getUserObject();
				
				// START: indented effect
				// * * * *
				//  * * * *
				// * * * *
				if (counter % 4 == 0 && counter % 8 != 0) {
					x = 50 + xGroup;
					y += 50;
					yGroup += 50;
				}

				if (counter % 8 == 0) {
					x = 0 + xGroup;
					y += 50;
					yGroup += 50;
				}
				
				System.out.println(cell.name + " Layout X:" +  x + "Layout Y:" + y);
				graph.getGraphic(cell).relocate(x, y);
				cell.setX(x);
				cell.setY(y);
				skip = true;
				counter += 1;
				x += 150;
				// END: indented effect
				// * * * *
				//  * * * *
				// * * * *
				
			}
		}
		if (tree.getDepth() == depth && !skip) {
			for (int i = 0; i < tree.getChildCount(); i++) {
				DefaultMutableTreeNode leafCells2 = (DefaultMutableTreeNode) tree.getChildAt(i);
				if (i == 0 || i % 4 == 0) {
					xGroup = 0;
				} else {
					xGroup += 700;
				}
				if (i % 4 == 0) {
					yGroup += 200;
				}

				createGroups(leafCells2, graph, depth);
			}
		} else if (!skip) {
			for (int i = 0; i < tree.getChildCount(); i++) {
				DefaultMutableTreeNode leafCells2 = (DefaultMutableTreeNode) tree.getChildAt(i);
				yGroup += 60;
				xGroup += 25;
				createGroups(leafCells2, graph, depth);
			}
		}
	}

	public void printTree(DefaultMutableTreeNode tree, String appender) {
		if (tree.getUserObject() instanceof RectangleCell) {
			RectangleCell cell = (RectangleCell) tree.getUserObject();
			System.out.println("Node" + appender + cell.getName());
		} else {
			System.out.println("Node" + appender + tree.toString());
		}
		for (int i = 0; i < tree.getChildCount(); i++) {
			printTree((DefaultMutableTreeNode) tree.getChildAt(i), appender + appender);
		}
	}

	/**
	 * 
	 * @param listCell
	 * @return tranformation of ICell to Rectangle Cell
	 */
	private List<RectangleCell> getRecCell(List<ICell> listCell) {
		List<RectangleCell> cells = new LinkedList<RectangleCell>();
		for (ICell iCell : listCell) {
			if (iCell instanceof RectangleCell) {
				cells.add((RectangleCell) iCell);
			}
		}
		return cells;
	}

}
