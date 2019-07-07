package tum.franca.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import tum.franca.graph.layout.GroupingLayout;
import tum.franca.main.MainApp;
import tum.franca.reader.FidlReader;
import tum.franca.reader.PropertiesReader;
import tum.franca.view.listView.ListViewWrapper;

/**
 * 
 * @author michaelschott
 *
 */
public class GroupSetter {

	private List<FidlReader> fidlList;
	private ObservableList<String> listViewItems1;
	private ObservableList<String> listViewItems2;
	private ObservableList<String> listViewItems3;

	public GroupSetter(List<FidlReader> fidlList, ListViewWrapper listViewWrapper) {
		this.fidlList = fidlList;
		this.listViewItems1 = listViewWrapper.getListView1().getItems();
		this.listViewItems2 = listViewWrapper.getListView2().getItems();
		this.listViewItems3 = listViewWrapper.getListView3().getItems();
	}

	public void createCanvas() {
		MainApp.graph = new Graph();
		final Model model = MainApp.graph.getModel();

		grouping(model);
		addCells(model);
		addRequiredEdge(model);
		addProvidedEdge(model);

		MainApp.graph.endUpdate();
		MainApp.graph.layout(new GroupingLayout());

		MainApp.root.getItems().set(1, MainApp.graph.getCanvas());

		if (!subsubGroup.isEmpty()) {
			makeSubSubLevelGroup();
		}
		if (!subGroup.isEmpty()) {
			makeSubLevelGroup();
		}
		makeTopLevelGroup();

	}

	public void makeTopLevelGroup() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 0; i < getHighestGroup(cellList, 0) + 1; i++) {
			double maxX = 0;
			double maxY = 0;
			double minX = 10000;
			double minY = 10000;
			for (RectangleCell rectangleCell : cellList) {
				if (rectangleCell.getGrouping().get(0) == i) {
					double x = rectangleCell.getX();
					double y = rectangleCell.getY();
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
			final ICell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 205, (int) (maxY - minY) + 150,
					reverseGroup.get(i),2);
			MainApp.graph.addCell(cellGroup);
			MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 50, (int) minY - 50);
		}
	}

	public void makeSubLevelGroup() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 0; i < getHighestGroup(cellList, 0) + 1; i++) {
			for (int j = 0; j < getHighestGroup(cellList, 1) + 1; j++) {
				double maxX = 0;
				double maxY = 0;
				double minX = 10000;
				double minY = 10000;
				for (RectangleCell rectangleCell : cellList) {
					if (rectangleCell.getGrouping().get(0) == i && rectangleCell.getGrouping().get(1) == j) {
						double x = rectangleCell.getX();
						double y = rectangleCell.getY();
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
				final ICell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 155, (int) (maxY - minY) + 90,
						reverseSubGroup.get(j),1);
				MainApp.graph.addCell(cellGroup);
				MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 20, (int) minY - 20);
			}

		}
	}

	public void makeSubSubLevelGroup() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 0; i < getHighestGroup(cellList, 0) + 1; i++) {
			for (int j = 0; j < getHighestGroup(cellList, 1) + 1; j++) {
				for (int k = 0; k < getHighestGroup(cellList, 2) + 1; k++) {
					double maxX = 0;
					double maxY = 0;
					double minX = 10000;
					double minY = 10000;
					for (RectangleCell rectangleCell : cellList) {
						if (rectangleCell.getGrouping().get(0) == i && rectangleCell.getGrouping().get(1) == j  && rectangleCell.getGrouping().get(2) == k) {
							double x = rectangleCell.getX();
							double y = rectangleCell.getY();
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
					final ICell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 105,
							(int) (maxY - minY) + 50, reverseSubSubGroup.get(k), 0);
					MainApp.graph.addCell(cellGroup);
					MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 5, (int) minY - 5);
				}
			}
		}
	}

	private int getHighestGroup(List<RectangleCell> cellList, int group) {
		int max = 0;
		for (RectangleCell rectangleCell : cellList) {
			if (rectangleCell.getGrouping().get(group) > max) {
				max = rectangleCell.getGrouping().get(group);
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

	private HashMap<String, Integer> group;
	private HashMap<Integer, String> reverseGroup = new HashMap<Integer, String>();
	private HashMap<String, Integer> subGroup;
	private HashMap<Integer, String> reverseSubGroup = new HashMap<Integer, String>();
	private HashMap<String, Integer> subsubGroup;
	private HashMap<Integer, String> reverseSubSubGroup = new HashMap<Integer, String>();

	public void grouping(Model model) {
		this.group = setGroups(listViewItems1);
		this.subGroup = setGroups(listViewItems2);
		this.subsubGroup = setGroups(listViewItems3);
		reverseMaps();
	}

	public void reverseMaps() {
		for (Map.Entry<String, Integer> entry : group.entrySet()) {
			reverseGroup.put(entry.getValue(), entry.getKey());
		}
		for (Map.Entry<String, Integer> entry : subGroup.entrySet()) {
			reverseSubGroup.put(entry.getValue(), entry.getKey());
		}
		for (Map.Entry<String, Integer> entry : subsubGroup.entrySet()) {
			reverseSubSubGroup.put(entry.getValue(), entry.getKey());
		}
	}

	private HashMap<String, Integer> setGroups(ObservableList<String> list) {
		HashMap<String, Integer> uniqueProperties = new HashMap<>();
		int counter = 0;
		for (FidlReader fidlReader : fidlList) {
			PropertiesReader propertiesReader = fidlReader.getPropertiesReader();
			StringBuilder sB = new StringBuilder();
			for (String string : list) {
				if (string.equals("Binding")) {
					sB.append(propertiesReader.getBinding() + " ");
				}
				if (string.equals("Functional Scope")) {
					sB.append(propertiesReader.getFunctionalScope() + " ");
				}
				if (string.equals("Runtime")) {
					sB.append(propertiesReader.getRuntime() + " ");
				}
				if (string.equals("Security Critical")) {
					sB.append(propertiesReader.getSecurityCritical() + " ");
				}
				if (string.equals("Safty Critical")) {
					sB.append(propertiesReader.getSaftyCritical() + " ");
				}
			}
			if (!uniqueProperties.containsKey(sB.toString()) && !sB.toString().equals("")) {
				uniqueProperties.put(sB.toString(), counter++);
			}
		}
		System.out.println(uniqueProperties);
		return uniqueProperties;
	}

	/**
	 * Add cells to the Model with his interface name
	 * 
	 * @param model
	 */
	private void addCells(Model model) {
		List<ICell> cellList = new ArrayList<ICell>();
		for (FidlReader fidlReader : fidlList) {
			try {
				final RectangleCell cell = new RectangleCell(fidlReader.getFirstInterfaceName(), getGroup(fidlReader));
				cellList.add(cell);
				model.addCell(cell);
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	/**
	 * Setting the groups, subGroups
	 * 
	 * @param fidlReader
	 * @return HashMap<Integer,Integer>
	 */
	private HashMap<Integer, Integer> getGroup(FidlReader fidlReader) {
		HashMap<Integer, Integer> grouping = new HashMap<Integer, Integer>();
		PropertiesReader propertiesReader = fidlReader.getPropertiesReader();
		StringBuilder sB = new StringBuilder();
		for (String string : listViewItems1) {
			if (string.equals("Binding")) {
				sB.append(propertiesReader.getBinding() + " ");
			}
			if (string.equals("Functional Scope")) {
				sB.append(propertiesReader.getFunctionalScope() + " ");
			}
			if (string.equals("Runtime")) {
				sB.append(propertiesReader.getRuntime() + " ");
			}
			if (string.equals("Security Critical")) {
				sB.append(propertiesReader.getSecurityCritical() + " ");
			}
			if (string.equals("Safty Critical")) {
				sB.append(propertiesReader.getSaftyCritical() + " ");
			}
		}
		if (!group.isEmpty()) {
			grouping.put(0, group.get(sB.toString()));
		}
		StringBuilder sB1 = new StringBuilder();
		for (String string : listViewItems2) {
			if (string.equals("Binding")) {
				sB1.append(propertiesReader.getBinding() + " ");
			}
			if (string.equals("Functional Scope")) {
				sB1.append(propertiesReader.getFunctionalScope() + " ");
			}
			if (string.equals("Runtime")) {
				sB1.append(propertiesReader.getRuntime() + " ");
			}
			if (string.equals("Security Critical")) {
				sB1.append(propertiesReader.getSecurityCritical() + " ");
			}
			if (string.equals("Safty Critical")) {
				sB1.append(propertiesReader.getSaftyCritical() + " ");
			}
		}
		if (!subGroup.isEmpty()) {
			grouping.put(1, subGroup.get(sB1.toString()));
		}
		StringBuilder sB2 = new StringBuilder();
		for (String string : listViewItems3) {
			if (string.equals("Binding")) {
				sB2.append(propertiesReader.getBinding() + " ");
			}
			if (string.equals("Functional Scope")) {
				sB2.append(propertiesReader.getFunctionalScope() + " ");
			}
			if (string.equals("Runtime")) {
				sB2.append(propertiesReader.getRuntime() + " ");
			}
			if (string.equals("Security Critical")) {
				sB2.append(propertiesReader.getSecurityCritical() + " ");
			}
			if (string.equals("Safty Critical")) {
				sB2.append(propertiesReader.getSaftyCritical() + " ");
			}
		}
		if (!subsubGroup.isEmpty()) {
			grouping.put(2, subsubGroup.get(sB2.toString()));

		}
		return grouping;
	}

	/**
	 * Add required Edges to the model.
	 * 
	 * @param model
	 */
	private void addRequiredEdge(Model model) {
		List<ICell> cellList = model.getAddedCells();
		for (FidlReader fidlReader : fidlList) {
			try {
				EList<FProvides> providesList = fidlReader.getFirstProvides();
				for (FProvides fProvides : providesList) {
					String providesString = fProvides.getProvidedImport();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {
							if (((RectangleCell) iCell).getName().equals(providesString)) {
								final Edge edge = new Edge(model.getRectangleCell(fidlReader.getFirstInterfaceName()),
										iCell);
								model.addEdge(edge);
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	/**
	 * Add provided Egdes to the model.
	 * 
	 * @param model
	 */
	private void addProvidedEdge(Model model) {
		List<ICell> cellList = model.getAddedCells();
		for (FidlReader fidlReader : fidlList) {
			try {
				EList<FRequires> requiresList = fidlReader.getFirstRequires();
				for (FRequires fRequires : requiresList) {
					String requiredString = fRequires.getRequiresImport();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {
							if (((RectangleCell) iCell).getName().equals(requiredString)) {
								final Edge edge = new Edge((model.getRectangleCell(fidlReader.getFirstInterfaceName())),
										iCell);
								model.addEdge(edge);
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

}
