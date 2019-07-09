package tum.franca.view.treeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import tum.franca.main.MainApp;
import tum.franca.reader.FidlReader;
import tum.franca.reader.PropertiesReader;

/**
 * 
 * @author michaelschott
 *
 */
public class TreeViewCreator {

	private List<FidlReader> fidlList;
	private TreeItem<String> rootItem;

	/**
	 * 
	 * @param fidlList
	 */
	public TreeViewCreator(List<FidlReader> fidlList) {
		this.fidlList = fidlList;
		rootItem = new TreeItem<String>("Services");
		rootItem.setExpanded(true);
	}

	/**
	 *  Creates a tree with services and their properties.
	 */
	public void createTree() {
		for (FidlReader fidlReader : fidlList) {
			TreeItem<String> item = new TreeItem<String>(fidlReader.getFirstInterfaceName());
			PropertiesReader pR = fidlReader.getPropertiesReader();
			HashMap<String, String> hashMap = pR.getAllStringProperties();
			for (Map.Entry<String, String> entry : hashMap.entrySet()) {
				TreeItem<String> itemKey = new TreeItem<String>(entry.getKey());
				TreeItem<String> itemValue = new TreeItem<String>(entry.getValue());
				itemKey.getChildren().add(itemValue);
				item.getChildren().add(itemKey);
			}
			rootItem.getChildren().add(item);
		}
		TreeView<String> treeView =  new TreeView<String>(rootItem);
		MainApp.root.getItems().set(2, treeView);
	}

}
