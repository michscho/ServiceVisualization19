package tum.franca.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;

import javafx.collections.ObservableList;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.ICell;
import tum.franca.graph.graph.Model;
import tum.franca.graph.layout.GroupLayout;
import tum.franca.main.MainApp;
import tum.franca.reader.FidlReader;
import tum.franca.reader.PropertiesReader;

/**
 * 
 * @author michaelschott
 *
 */
public class Factory {

	public void createCanvas(List<FidlReader> fidlList) {
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
	
	
	private HashMap<Integer, String> reverseUniqueProperties = new HashMap<>();
	private HashMap<String, Integer> uniqueProperties = new HashMap<>();
	
	private void setGroups(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap, List<ICell> cellList) {
		int counter = 1;
		for (FidlReader fidlReader : fidlList) {
			try {
			PropertiesReader pR = fidlReader.getPropertiesReader();
			if(uniqueProperties.containsKey(pR.getFunctionalScope().getName())) {
			} else {
			uniqueProperties.put(pR.getFunctionalScope().getName(), counter);
			reverseUniqueProperties.put(counter, pR.getFunctionalScope().getName());
			counter++;
			}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
	}
	

	private void addCells(List<FidlReader> fidlList, HashMap<FidlReader, ICell> fidlCellMap, List<ICell> cellList,
			Model model) {

		for (FidlReader fidlReader : fidlList) {
			try {
				final ICell cell = new RectangleCell(fidlReader.getFirstInterfaceName(), uniqueProperties.get(fidlReader.getPropertiesReader().getFunctionalScope().getName()));
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
					reverseUniqueProperties.get(Integer.valueOf(i)));
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

}
