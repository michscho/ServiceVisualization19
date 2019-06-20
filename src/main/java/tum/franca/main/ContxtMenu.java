package tum.franca.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import tum.franca.graph.graph.ICell;

public class ContxtMenu {
	
	public static ContextMenu getContextMenu(ICell cell) {

		ContextMenu contxtMenu = new ContextMenu();
 
        MenuItem item = new MenuItem("Remove Group");
        item.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               //MainApp.graph.removeCell(cell);
            }
        });
       
        contxtMenu.getItems().addAll(item);
		
        return contxtMenu;
		
	}

}
