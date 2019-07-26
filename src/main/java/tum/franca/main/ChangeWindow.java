package tum.franca.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.ResizableRectangleCell;

public class ChangeWindow {
	
	public static List<ICell> intersectionCellListBefore;
	public static List<ICell> intersectionCellListAfter;
	public static boolean show = false;
	
	public void showChangeWindow(List<ICell> intersectionCellListBefore, List<ICell> intersectionCellListAfter) {
		Parent root;
		ChangeWindow.intersectionCellListAfter = intersectionCellListAfter;
		ChangeWindow.intersectionCellListBefore = intersectionCellListBefore;
		try {
			root = FXMLLoader.load(getClass().getResource("Change.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Change");
			stage.setScene(new Scene(root, 350, 225));
			stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			if (show == true) {
				System.out.println("SHOW");
				stage.show();
			}
			show = false;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
