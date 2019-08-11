package tum.franca.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.edges.IEdge;
import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.Model;
import tum.franca.graph.layout.GroupingLayout;
import tum.franca.main.MainApp;
import tum.franca.tabs.RenameableTab;
import tum.franca.tabs.TabPaneSetter;

/**
 * 
 * @author michaelschott
 *
 */
public class DataModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// RectangleCell Data
	public List<Integer> rectangleCellX = new ArrayList<Integer>();
	public List<Integer> rectangleCellY = new ArrayList<Integer>();
	public List<String> rectangleCellName = new ArrayList<String>();

	// ResizableCell Data
	public List<Integer> groupRectangleWidth = new ArrayList<Integer>();
	public List<Integer> groupRectangleHeight = new ArrayList<Integer>();
	public List<Integer> groupRectangleX = new ArrayList<Integer>();
	public List<Integer> groupRectangleY = new ArrayList<Integer>();
	public List<String> groupRectangleName = new ArrayList<String>();
	public List<String> groupRectangleColor = new ArrayList<String>();
	public List<String> groupRectangleColorStroke = new ArrayList<String>();
	public List<ResizableRectangleCell.GroupType> groupRectangleStyle = new ArrayList<ResizableRectangleCell.GroupType>();

	// Edges Data
	public List<String> edgeSource = new ArrayList<String>();
	public List<String> edgeTarget = new ArrayList<String>();

	public void save() {
		initCell();
		initEdges();
		serialize();
	}

	private void serialize() {

		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Visualisation Files", "*.vis"));
			fileChooser.setTitle("Save file");
			Tab tab = TabPaneSetter.tabPane.getSelectionModel().getSelectedItem();
			String string = "default";
			if (tab instanceof RenameableTab) {
				RenameableTab renTab = (RenameableTab) tab;
				string = ((RenameableTab) tab).name.get();
			}
			fileChooser.setInitialFileName("visualisation-"+ string  + ".vis");
			File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);
			FileOutputStream fileOut = new FileOutputStream(savedFile.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + savedFile.getAbsolutePath());
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public void deserialize() {
		DataModel e = null;
		try {
			// FileChooser
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Visualisation Files", "*.vis"));
			File savedFile = fileChooser.showOpenDialog(MainApp.primaryStage);
			FileInputStream fileIn = new FileInputStream(savedFile.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (DataModel) in.readObject();

			// Rectangle Data
			rectangleCellX = e.rectangleCellX;
			rectangleCellY = e.rectangleCellY;
			rectangleCellName = e.rectangleCellName;

			// ResizableRec Data
			groupRectangleWidth = e.groupRectangleWidth;
			groupRectangleHeight = e.groupRectangleHeight;
			groupRectangleX = e.groupRectangleX;
			groupRectangleY = e.groupRectangleY;
			groupRectangleName = e.groupRectangleName;
			groupRectangleColor = e.groupRectangleColor;
			groupRectangleColorStroke = e.groupRectangleColorStroke;
			groupRectangleStyle = e.groupRectangleStyle;

			// Edge
			edgeSource = e.edgeSource;
			edgeTarget = e.edgeTarget;

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("DataModel class not found");
			c.printStackTrace();
			return;
		}
	}

	private void initCell() {
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				rectangleCellX.add(((RectangleCell) iCell).pane.layoutXProperty().intValue());
				rectangleCellY.add(((RectangleCell) iCell).pane.layoutYProperty().intValue());
				rectangleCellName.add(((RectangleCell) iCell).getName());
			}
			if (iCell instanceof ResizableRectangleCell) {
				groupRectangleX.add(((ResizableRectangleCell) iCell).pane.layoutXProperty().intValue());
				groupRectangleY.add(((ResizableRectangleCell) iCell).pane.layoutYProperty().intValue());
				groupRectangleWidth.add(((ResizableRectangleCell) iCell).width);
				groupRectangleHeight.add(((ResizableRectangleCell) iCell).heigth);
				groupRectangleName.add(((ResizableRectangleCell) iCell).getName());
				groupRectangleColor.add(((ResizableRectangleCell) iCell).color.toString());
				groupRectangleColorStroke.add(((ResizableRectangleCell) iCell).colorStroke.toString());
				groupRectangleStyle.add(((ResizableRectangleCell) iCell).style);
			}
		}
	}

	private void initEdges() {
		for (IEdge iEdge : MainApp.graph.getModel().getAddedEdges()) {
			edgeSource.add(((RectangleCell) iEdge.getSource()).name);
			edgeTarget.add(((RectangleCell) iEdge.getTarget()).name);
		}
	}

	public void importSavedFile(TabPaneSetter tabpane) {
		ObservableList<ICell> cellList = FXCollections.observableArrayList();
		for (int i = 0; i < groupRectangleName.size(); i++) {
			ResizableRectangleCell rec = new ResizableRectangleCell(groupRectangleWidth.get(i),
					groupRectangleHeight.get(i), groupRectangleName.get(i), groupRectangleStyle.get(i), "");
			rec.color = Color.web(groupRectangleColor.get(i));
			rec.colorStroke = Color.web(groupRectangleColorStroke.get(i));
			rec.setX(groupRectangleX.get(i));
			rec.setY(groupRectangleY.get(i));
			cellList.add(rec);
		}

		for (int i = 0; i < rectangleCellName.size(); i++) {
			RectangleCell rec = new RectangleCell(rectangleCellName.get(i), null, null);
			rec.setX(rectangleCellX.get(i));
			rec.setY(rectangleCellY.get(i));
			cellList.add(rec);
		}

		MainApp.graph = new Graph();
		final Model model = MainApp.graph.getModel();
		for (ICell iCell : cellList) {			MainApp.graph.getModel().addCell(iCell);
			MainApp.graph.getGraphic(iCell).relocate(iCell.getX(), iCell.getY());
		}

		ObservableList<Edge> edgeList = FXCollections.observableArrayList();
		for (int i = 0; i < edgeSource.size(); i++) {
			for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
				for (ICell iCell2 : MainApp.graph.getModel().getAddedCells()) {
					if (iCell instanceof RectangleCell && iCell2 instanceof RectangleCell) {
						if (edgeSource.get(i).equals(((RectangleCell) iCell).getName()) && edgeTarget.get(i).equals(((RectangleCell) iCell2).getName())) {
							 edgeList.add(new Edge(iCell, iCell2));
						}
					}
				}
			}

		}
		
		for (Edge edge : edgeList) {
			model.addEdge(edge);
			MainApp.graph.addEgde(edge);
		}
		
		for (ICell iCell : cellList) {
			MainApp.graph.addCell(iCell);
			MainApp.graph.getGraphic(iCell).relocate(iCell.getX(), iCell.getY());
		}

		if (tabpane == null) {
			tabpane = new TabPaneSetter();
		}
		tabpane.setCanvas();
	}

}
