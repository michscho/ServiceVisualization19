package tum.franca.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.ICell;
import tum.franca.graph.graph.Model;
import tum.franca.graph.layout.GroupLayout;
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
		List<FidlReader> fidlList = new ArrayList<>();
		for (File file : list) {
			URI uri = URI.createFileURI(file.getAbsolutePath());
			fidlList.add(new FidlReader(uri));	
		}
		
		MainApp.graph = new Graph();
		final Model model = MainApp.graph.getModel();
		
		HashMap<FidlReader, ICell> fidlCellMap = new HashMap<>(); 
		List<ICell> cellList = new ArrayList<ICell>();
		for (FidlReader fidlReader : fidlList) {
			final ICell cell = new RectangleCell(fidlReader.getFirstInterfaceName(), 1);
			cellList.add(cell);
			fidlCellMap.put(fidlReader, cell);
			model.addCell(cell);
		}
		
		for (FidlReader fidlReader : fidlList) {
			EList<FProvides> providesList = fidlReader.getFirstProvides();
			for (FProvides fProvides : providesList) {
				String providesString = fProvides.getProvidedImport();
				for (ICell iCell : cellList) {
					if (iCell instanceof RectangleCell) {
						if (((RectangleCell) iCell).getName().equals(providesString)) {
							final Edge edge = new Edge(fidlCellMap.get(fidlReader),iCell);
							model.addEdge(edge);
						}
					}
				}
			}
		}
		
		for (FidlReader fidlReader : fidlList) {
			EList<FRequires> requiresList = fidlReader.getFirstRequires();
			for (FRequires fRequires : requiresList) {
				String requiredString = fRequires.getRequiredImport();
				for (ICell iCell : cellList) {
					if (iCell instanceof RectangleCell) {
						if (((RectangleCell) iCell).getName().equals(requiredString)) {
							final Edge edge = new Edge(fidlCellMap.get(fidlReader),iCell);
							model.addEdge(edge);
						}
					}
				}
			}
		}
		
		MainApp.graph.endUpdate();
		MainApp.graph.layout(new GroupLayout());
		
		MainApp.root.getItems().set(1, MainApp.graph.getCanvas());
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
