package tum.franca.view.treeView;

import javafx.scene.control.TreeItem;

public abstract class AbstractTreeView {
	
	protected TreeItem<String> rootItem;
	
	protected abstract void createTree();

}
