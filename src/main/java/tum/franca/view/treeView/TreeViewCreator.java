package tum.franca.view.treeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TreeItem;
import tum.franca.util.reader.*;

/**
 * 
 * @author michaelschott
 *
 */
public class TreeViewCreator extends AbstractTreeView {

	private List<FidlReader> fidlList;

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
			HashMap<String, String> hashMap = pR.getAllStringPropertiesWithoutNotDefinedOnes();
			for (Map.Entry<String, String> entry : hashMap.entrySet()) {
				TreeItem<String> itemKey = new TreeItem<String>(entry.getKey());
				TreeItem<String> itemValue = new TreeItem<String>(entry.getValue());
				itemKey.getChildren().add(itemValue);
				item.getChildren().add(itemKey);
			}
			rootItem.getChildren().add(item);
		}
		setRoot();
	}


}
