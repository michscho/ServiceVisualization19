package tum.franca.view.metric;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import tum.franca.graph.cells.ICell;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class MetricDependency {

	private List<ICell> list;
	private DefaultMutableTreeNode tree; 


	public void buildDependencyTree(ICell root) {
		list = new ArrayList<ICell>();
		tree = new DefaultMutableTreeNode();
		buildTree(root, tree);
	}
	
	public void buildDependencyTree2(ICell root) {
		list = new ArrayList<ICell>();
		tree = new DefaultMutableTreeNode();
		buildTree2(root, tree);
	}
	
	public void buildDependencyTree3(ICell root) {
		list = new ArrayList<ICell>();
		tree = new DefaultMutableTreeNode();
		requires = 0;
		buildTree3(root, tree);
	}
	
	public void buildDependencyTree4(ICell root) {
		list = new ArrayList<ICell>();
		tree = new DefaultMutableTreeNode();
		provides = 0;
		buildTree4(root, tree);
	}
	
	public void buildDependencyTree5(ICell root) {
		list = new ArrayList<ICell>();
		tree = new DefaultMutableTreeNode();
		buildTree5(root, tree);
	}
	
	public int getNumberOfRequires() {
		return tree.getDepth()-1;
	}

	private void buildTree(ICell root, DefaultMutableTreeNode tree) {
		if (!list.contains(root)) {
			list.add(root);
			DefaultMutableTreeNode newDMT = new DefaultMutableTreeNode(root);
			tree.add(newDMT);
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				if (edge.getSource().equals(root)) {
					buildTree(edge.getTarget(), newDMT);
				}
			}
		}
	}
	
	private void buildTree2(ICell root, DefaultMutableTreeNode tree) {
		if (!list.contains(root)) {
			list.add(root);
			DefaultMutableTreeNode newDMT = new DefaultMutableTreeNode(root);
			tree.add(newDMT);
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				if (edge.getTarget().equals(root)) {
					buildTree(edge.getSource(), newDMT);
				}
			}
		}
	}
	
	private void buildTree5(ICell root, DefaultMutableTreeNode tree) {
		if (!list.contains(root)) {
			list.add(root);
			DefaultMutableTreeNode newDMT = new DefaultMutableTreeNode(root);
			tree.add(newDMT);
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				if (edge.getSource().equals(root)) {
					buildTree(edge.getTarget(), newDMT);
				}
				if (edge.getTarget().equals(root)) {
					buildTree(edge.getSource(), newDMT);
				}
			}
		}
	}
	
	public static int requires;
	
	private void buildTree3(ICell root, DefaultMutableTreeNode tree) {
		if (!list.contains(root)) {
			list.add(root);
			DefaultMutableTreeNode newDMT = new DefaultMutableTreeNode(root);
			tree.add(newDMT);
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				if (edge.getSource().equals(root)) {
					buildTree(edge.getTarget(), newDMT);
					requires++;
				}
			}
		}
	}
	
	public static int provides;
	
	private void buildTree4(ICell root, DefaultMutableTreeNode tree) {
		if (!list.contains(root)) {
			list.add(root);
			DefaultMutableTreeNode newDMT = new DefaultMutableTreeNode(root);
			tree.add(newDMT);
			for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
				if (edge.getTarget().equals(root)) {
					buildTree(edge.getSource(), newDMT);
					provides++;
				}
			}
		}
	}

}
