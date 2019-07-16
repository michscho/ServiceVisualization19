package tum.franca.view.treeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.main.MainApp;
import tum.franca.reader.FidlReader;
import tum.franca.reader.PropertiesReader;
import tum.franca.reader.StaticFidlReader;

/**
 * 
 * @author michaelschott
 *
 */
public class GroupTreeViewCreator extends AbstractTreeView {

	private List<ICell> intersectionCellList;

	/**
	 * 
	 * @param rootName
	 * @param intersectionCellList
	 */
	public GroupTreeViewCreator(String rootName, List<ICell> intersectionCellList) {
		this.rootItem = new TreeItem<String>(rootName);
		rootItem.setExpanded(true);
		this.intersectionCellList = intersectionCellList;
	}

	/**
	 *  Creates a tree.
	 */
	public void createTree() {
		if (intersectionCellList.isEmpty()) {
			TreeView<String> treeView = new TreeView<String>(rootItem);
			MainApp.root.getItems().set(2, treeView);
		} else {
			for (ICell iCell : intersectionCellList) {
				RectangleCell rectCell = (RectangleCell) iCell;
				FidlReader fidlReader = StaticFidlReader.getFidl(rectCell);
				TreeItem<String> item = new TreeItem<String>(fidlReader.getFirstInterfaceName());
				PropertiesReader pR = fidlReader.getPropertiesReader();
				HashMap<String, String> hashMap = pR.getAllStringPropertiesWithoutNotDefinedOnes();
				for (Map.Entry<String, String> entry : hashMap.entrySet()) {
					TreeItem<String> itemKey = new TreeItem<String>(entry.getKey());
					TreeItem<String> itemValue = new TreeItem<String>(entry.getValue());
					itemKey.getChildren().add(itemValue);
					item.getChildren().add(itemKey);
				}
				rootItem.getChildren().add(item);
				TreeView<String> treeView = new TreeView<String>(rootItem);
				MainApp.root.getItems().set(2, treeView);
			}
		}
	}

	/**
	 * 
	 */
	public void printList() {
		for (ICell iCell : intersectionCellList) {
			if (iCell instanceof RectangleCell) {
				System.out.println(((RectangleCell) iCell).getName());
			}
		}
	}

}
