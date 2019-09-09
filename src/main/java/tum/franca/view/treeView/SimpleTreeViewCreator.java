package tum.franca.view.treeView;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
public class SimpleTreeViewCreator extends AbstractTreeView {
	
	private RectangleCell rectCell;
	private FidlReader fidlReader;
	private PropertiesReader propReader;
	
	/**
	 * 
	 * @param rootName
	 */
	public SimpleTreeViewCreator(String rootName) {
		this.rootItem = new TreeItem<String>(rootName);
		rootItem.setExpanded(true);
		System.out.println(rootName);
		this.rectCell = MainApp.graph.getModel().getRectangleCell(rootName); 
		this.fidlReader = StaticFidlReader.getFidl(rectCell);
		this.propReader = fidlReader.getPropertiesReader();
	}
	
	/**
	 * Creates a simple tree with only the current cell and his characteristics.
	 */
	public void createTree() {
		HashMap<String, String> hashMap = propReader.getAllStringPropertiesWithoutNotDefinedOnes();
		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
			TreeItem<String> itemKey = new TreeItem<String>(entry.getKey());
			itemKey.setExpanded(true);
			TreeItem<String> itemValue = new TreeItem<String>(entry.getValue());
			itemValue.setExpanded(true);
			itemKey.getChildren().add(itemValue);
			rootItem.getChildren().add(itemKey);
		}
		setRoot();
	}

}
