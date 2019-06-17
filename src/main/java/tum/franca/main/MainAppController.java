package tum.franca.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.graph.ICell;
import tum.franca.reader.FidlReader;

public class MainAppController {
	
	@FXML
	public void makeNewGroup() {
		
		TextInputDialog dialog = new TextInputDialog("Group");
		dialog.setTitle("Rectangle Group Name");
		dialog.setHeaderText("How should the group be called?");
		dialog.setContentText("Please enter your name:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your name: " + result.get());
		    final ICell cellGroup = new ResizableRectangleCell(result.get());
			MainApp.graph.addCell(cellGroup);
		}

	}
	
	@FXML
	public void importFile() throws IOException {
		final FileChooser fileChooser = new FileChooser();
		List<File> list = fileChooser.showOpenMultipleDialog(MainApp.primaryStage);
		for (File file : list) {
			URI uri = URI.createFileURI(file.getAbsolutePath());
			FidlReader reader = new FidlReader(uri);
			reader.printFModel();;
		}
		
		

	}
	
	@FXML
	public void about() {

		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("About.fxml"));
            Stage stage = new Stage();
            stage.setTitle("About VisualFX Franca");
            stage.setScene(new Scene(root, 450, 450));
            stage.setAlwaysOnTop(true);
            stage.show();
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

}
