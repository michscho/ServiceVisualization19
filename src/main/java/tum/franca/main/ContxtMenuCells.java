package tum.franca.main;

import java.util.List;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.properties.PropertiesUtil;
import tum.franca.reader.FidlReader;
import tum.franca.reader.PropertiesReader;
 
/**
 * 
 * @author michaelschott
 *
 */
public class ContxtMenuCells {
	
	public static boolean binding = false;
	private static ContextMenu contextMenu;

	public static ContextMenu getContextMenu() {
		return contextMenu;
	}
	
	public static void setContextMenu(Pane pane, FidlReader fidlReader) {
		
		PropertiesReader pR = fidlReader.getPropertiesReader();

		if (contextMenu != null && contextMenu.isShowing()) {
			contextMenu.hide();
		}
		
		contextMenu = new ContextMenu();
		
		List<List<String>> list = PropertiesUtil.getAllPropertiesAsEnums();
		
		for (int i = 0; i < list.size(); i++) {
			List<String> innerList = list.get(i);
			ToggleGroup group = new ToggleGroup();
			Menu menuBinding = new Menu("");
			for (int j = 0; j < innerList.size(); j++) {
				if (j == 0) {
					menuBinding = new Menu(innerList.get(j));
				} else {
					RadioMenuItem radioMenuItem = new RadioMenuItem(innerList.get(j));
					radioMenuItem.setToggleGroup(group);
					String groupString = menuBinding.getText().toLowerCase();
					String propertyString = innerList.get(j);
					radioMenuItem.setOnAction(action -> pR.setProperty(groupString, propertyString));
					if (innerList.get(j) == pR.getProperties().get(i)) {
						radioMenuItem.setSelected(true);
					}
					menuBinding.getItems().add(radioMenuItem);
				}
			}
			 contextMenu.getItems().add(menuBinding);
		} 
		
	}

 
}