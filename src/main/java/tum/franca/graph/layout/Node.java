package tum.franca.graph.layout;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {


	private List<Node<T>> children = new ArrayList<>();

	private Node<T> parent = null;

	public Node() {
	}

	public Node<T> addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	private void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Node<T> getParent() {
		return parent;
	}

}