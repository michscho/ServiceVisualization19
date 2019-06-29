package TreeView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;
import tum.franca.reader.FidlReader;
import tum.franca.reader.PropertiesReader;

/**
 * 
 * @author michaelschott
 *
 */
public class TreeViewFactory {

	/**
	 * 
	 * @param fidlList
	 * @return TreeView<String>
	 */
	public TreeView<String> getSimpleTreeView(List<FidlReader> fidlList) {
	  TreeItem<String> rootItem = new TreeItem<String> ("Services");
	  rootItem.setExpanded(true);
	  for (FidlReader fidlReader : fidlList) {
		TreeItem<String> item = new TreeItem<String>(fidlReader.getFirstInterfaceName());
		PropertiesReader pR = fidlReader.getPropertiesReader();
		HashMap<String, String> hashMap = pR.getAllStringProperties();
		for(Map.Entry<String,String> entry : hashMap.entrySet()) {
			TreeItem<String> itemKey = new TreeItem<String>(entry.getKey());
			TreeItem<String> itemValue = new TreeItem<String>(entry.getValue());
			itemKey.getChildren().add(itemValue);
			item.getChildren().add(itemKey);
		}
		rootItem.getChildren().add(item);
	  }
      TreeView<String> treeView = new TreeView<String>(rootItem);
      return treeView;
	}
	
	public TreeView<String> getCurrentTreeView(){
		// TODO
		return null;
	}

}
