package tum.franca.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;

import javafx.collections.ObservableList;
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
import tum.franca.reader.PropertiesReader;

/**
 * 
 * @author michaelschott
 *
 */
public class MainAppController {

	@FXML
	public void makeNewGroup() {

		TextInputDialog dialog = new TextInputDialog("Group");
		dialog.setTitle("Rectangle Group Name");
		dialog.setHeaderText("How should the group be called?");
		dialog.setContentText("Please enter your name:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Your name: " + result.get());
			final ICell cellGroup = new ResizableRectangleCell(60, 120, result.get());
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
		
		setGroups(fidlList, fidlCellMap, cellList);
		
		addCells(fidlList, fidlCellMap, cellList, model);
		addEdges(fidlList, fidlCellMap, cellList, model);

		MainApp.graph.endUpdate();
		MainApp.graph.layout(new GroupLayout());

		MainApp.root.getItems().set(1, MainApp.graph.getCanvas());

		makeGroups();
	}
	
	private void setGroups(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap, List<ICell> cellList) {
		for (FidlReader fidlReader : fidlList) {
			try {
			PropertiesReader pR = fidlReader.getPropertiesReader();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
	}

	private void addCells(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap, List<ICell> cellList,
			Model model) {

		Random random = new Random();

		for (FidlReader fidlReader : fidlList) {
			int r = random.nextInt(6) + 1;
			try {
				final ICell cell = new RectangleCell(fidlReader.getFirstInterfaceName(), r);
				cellList.add(cell);
				fidlCellMap.put(fidlReader, cell);
				model.addCell(cell);
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	private void addEdges(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap, List<ICell> cellList,
			Model model) {
		addRequiredEdge(fidlList, fidlCellMap, cellList, model);
		addProvidedEdge(fidlList, fidlCellMap, cellList, model);
	}

	private void addRequiredEdge(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap,
			List<ICell> cellList, Model model) {
		for (FidlReader fidlReader : fidlList) {
			try {
				EList<FProvides> providesList = fidlReader.getFirstProvides();
				for (FProvides fProvides : providesList) {
					String providesString = fProvides.getProvidedImport();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {
							if (((RectangleCell) iCell).getName().equals(providesString)) {
								final Edge edge = new Edge(fidlCellMap.get(fidlReader), iCell);
								model.addEdge(edge);
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	private void addProvidedEdge(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap,
			List<ICell> cellList, Model model) {
		for (FidlReader fidlReader : fidlList) {
			try {
				EList<FRequires> requiresList = fidlReader.getFirstRequires();
				for (FRequires fRequires : requiresList) {
					String requiredString = fRequires.getRequiresImport();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {
							if (((RectangleCell) iCell).getName().equals(requiredString)) {
								final Edge edge = new Edge(fidlCellMap.get(fidlReader), iCell);
								model.addEdge(edge);
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	private void makeGroups() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 1; i <= getHighestGroup(cellList); i++) {
			double maxX = 0;
			double maxY = 0;
			double minX = 10000;
			double minY = 10000;
			for (RectangleCell rectangleCell : cellList) {
				if (rectangleCell.getGroup() == i) {
					double x = rectangleCell.getX();
					System.out.println(x);
					double y = rectangleCell.getY();
					System.out.println(y);
					if (x > maxX) {
						maxX = x;
					}
					if (y > maxY) {
						maxY = y;
					}
					if (x < minX) {
						minX = x;
					}
					if (y < minY) {
						minY = y;
					}
				}
			}
			final ICell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 105, (int) (maxY - minY) + 50,
					"Test");
			MainApp.graph.addCell(cellGroup);
			MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 5, (int) minY - 5);
		}
	}

	private int getHighestGroup(List<RectangleCell> cellList) {
		int max = 0;
		for (RectangleCell rectangleCell : cellList) {
			if (rectangleCell.getGroup() > max) {
				max = rectangleCell.getGroup();
			}
		}
		return max;
	}

	/**
	 * 
	 * @param iCellList
	 * @return RectangleCellList
	 */
	private List<RectangleCell> getRectangleList(ObservableList<ICell> iCellList) {
		List<RectangleCell> cellRecList = new ArrayList<RectangleCell>();
		iCellList.stream().forEach(cell -> {
			if (cell instanceof RectangleCell) {
				cellRecList.add((RectangleCell) cell);
			}
		});
		return cellRecList;
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
