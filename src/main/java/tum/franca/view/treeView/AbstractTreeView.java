package tum.franca.view.treeView;

import javafx.scene.control.TreeItem;
import tum.franca.main.MainAppController;

public abstract class AbstractTreeView {
	
	protected TreeItem<String> rootItem;
	
	protected abstract void createTree();
	
	protected void setRoot() {
		MainAppController.staticTreeView.setRoot(rootItem);
	}

}
