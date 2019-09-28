package tum.franca.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.Model;
import tum.franca.graph.layout.GroupingLayout;
import tum.franca.main.MainApp;
import tum.franca.util.reader.*;
import tum.franca.view.list.ListViewWrapper;

/**
 * 
 * @author michaelschott
 *
 */
public class GroupSetter{

	public static List<FidlReader> fidlList;
	private static ObservableList<String> listViewItems1;
	private static ObservableList<String> listViewItems2;
	private static ObservableList<String> listViewItems3;

	/**
	 * 
	 * @param fidlList
	 * @param listViewWrapper
	 */
	public GroupSetter(List<FidlReader> fidlList, ListViewWrapper listViewWrapper) {
		GroupSetter.fidlList = fidlList;
		listViewItems1 = listViewWrapper.getListView1().getItems();
		listViewItems2 = listViewWrapper.getListView2().getItems();
		listViewItems3 = listViewWrapper.getListView3().getItems();
	}

	/**
	 * Main steps to create the canvas.
	 */
	public static void createCanvas() {
		MainApp.graph = new Graph();
		final Model model = MainApp.graph.getModel();

		grouping(model);
		
		ModelSetter.addCells(model);
		ModelSetter.addRequiredEdge(model);
		ModelSetter.addProvidedEdge(model);

		MainApp.graph.endUpdate();
		MainApp.graph.layout(new GroupingLayout());

		makeTopLevelGroup();
		if (!subGroup.isEmpty()) {
			makeSubLevelGroup();
		}
		
		if (!subsubGroup.isEmpty()) {
			makeSubSubLevelGroup();
		}
		
		relocateToForeground();

	}
	
	
	/**
	 * RectangleCell should be in the Foreground.
	 */
	public static void relocateToForeground() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (RectangleCell rectangleCell : cellList) {
			rectangleCell.pane.toFront();
		}
	}

	/**
	 * Make Top Level Group.
	 */
	public static void makeTopLevelGroup() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 0; i < getHighestGroup(cellList, 0) + 1; ++i) {
			double maxX = 0;
			double maxY = 0;
			double minX = Double.POSITIVE_INFINITY;
			double minY = Double.POSITIVE_INFINITY;
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
			ObservableList<String> list = listViewItems1;
			StringBuilder builder = new StringBuilder();
			for (String string : list) {
				if (!string.equals("")) {
					StringBuilder innerBuilder = new StringBuilder();
					String[] inner = string.split(" ");
					for (int l = 0; l < inner.length; l++) {
						innerBuilder.append(inner[l]);
					}
					builder.append(innerBuilder.toString() + " ");
				}
			}
			final ICell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 205, (int) (maxY - minY) + 150,
					replaceNotDefined(reverseGroup.get(i)), ResizableRectangleCell.GroupType.TOPLEVEL, builder.toString());
			MainApp.graph.addCell(cellGroup);
			MainApp.graph.getModel().addCell(cellGroup);
			MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 50, (int) minY - 50);
		}
	}

	private static String replaceNotDefined(String input) {
		return input;
		//return input.replaceAll("notDefined", "");
	}

	public static void makeSubLevelGroup() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 0; i < getHighestGroup(cellList, 0) + 1; i++) {
			for (int j = 0; j < getHighestGroup(cellList, 1) + 1; j++) {
				double maxX = 0;
				double maxY = 0;
				double minX = Double.POSITIVE_INFINITY;
				double minY = Double.POSITIVE_INFINITY;
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
				
				ObservableList<String> list = listViewItems2;
				StringBuilder builder = new StringBuilder();
				for (String string : list) {
					if (!string.equals("")) {
						StringBuilder innerBuilder = new StringBuilder();
						String[] inner = string.split(" ");
						for (int l = 0; l < inner.length; l++) {
							innerBuilder.append(inner[l]);
						}
						builder.append(innerBuilder.toString() + " ");
					} 
				}
				final ResizableRectangleCell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 155, (int) (maxY - minY) + 90,
						replaceNotDefined(reverseSubGroup.get(j)), ResizableRectangleCell.GroupType.SUBLEVEL, builder.toString());
				MainApp.graph.addCell(cellGroup);
				MainApp.graph.getModel().addCell(cellGroup);
				MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 20, (int) minY - 20);
			}

		}
	}

	public static void makeSubSubLevelGroup() {
		List<RectangleCell> cellList = getRectangleList(MainApp.graph.getModel().getAddedCells());
		for (int i = 0; i < getHighestGroup(cellList, 0) + 1; i++) {
			for (int j = 0; j < getHighestGroup(cellList, 1) + 1; j++) {
				for (int k = 0; k < getHighestGroup(cellList, 2) + 1; k++) {
					double maxX = 0;
					double maxY = 0;
					double minX = Double.POSITIVE_INFINITY;
					double minY = Double.POSITIVE_INFINITY;
					for (RectangleCell rectangleCell : cellList) {
						if (rectangleCell.getGrouping().get(0) == i && rectangleCell.getGrouping().get(1) == j
								&& rectangleCell.getGrouping().get(2) == k) {
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
					ObservableList<String> list = listViewItems3;
					StringBuilder builder = new StringBuilder();
					for (String string : list) {
						if (!string.equals("")) {
							StringBuilder innerBuilder = new StringBuilder();
							String[] inner = string.split(" ");
							for (int l = 0; l < inner.length; l++) {
								innerBuilder.append(inner[l]);
							}
							builder.append(innerBuilder.toString() + " ");
						}
					}
					final ResizableRectangleCell cellGroup = new ResizableRectangleCell((int) (maxX - minX) + 118,
							(int) (maxY - minY) + 55, replaceNotDefined(reverseSubSubGroup.get(k)),
							ResizableRectangleCell.GroupType.SUBSUBLEVEL, builder.toString());
					MainApp.graph.addCell(cellGroup);
					MainApp.graph.getModel().addCell(cellGroup);
					MainApp.graph.getGraphic(cellGroup).relocate((int) minX - 5, (int) minY - 5);
				}
			}
		}
	}

	/**
	 * 
	 * @param cellList
	 * @param group
	 * @return int as highest number of the group
	 */
	private static int getHighestGroup(List<RectangleCell> cellList, int group) {
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
	private static List<RectangleCell> getRectangleList(ObservableList<ICell> iCellList) {
		List<RectangleCell> cellRecList = new ArrayList<RectangleCell>();
		iCellList.stream().forEach(cell -> {
			if (cell instanceof RectangleCell) {
				cellRecList.add((RectangleCell) cell);
			}
		});
		return cellRecList;
	}

	// ASIL_A, 1; ASIL_B 2 
	// can also have multiple Properties ASIL_A, crossfunctional;
	private static HashMap<String, Integer> group;
	private static HashMap<Integer, String> reverseGroup = new HashMap<Integer, String>();
	private static HashMap<String, Integer> subGroup;
	private static HashMap<Integer, String> reverseSubGroup = new HashMap<Integer, String>();
	private static HashMap<String, Integer> subsubGroup;
	private static HashMap<Integer, String> reverseSubSubGroup = new HashMap<Integer, String>();

	public static void grouping(Model model) {
		group = setGroups(listViewItems1);
		subGroup = setGroups(listViewItems2);
		subsubGroup = setGroups(listViewItems3);
		reverseMaps();
	}

	/**
	 * ASIL_A, 1 -> 1, ASIL_A
	 */
	public static void reverseMaps() {
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

	/**
	 * A name is created depending on the selected properties. 
	 * Then these names are stored in a HashMap to remove duplicate properties.
	 * Example: {ASIL_A crossfunctional,0};{ASIL_B,1}...
	 * Is made for all groups and then the parent groups are added. 
	 * @param list - List of properties selected in List groups/subgroups/subsubgroups
	 * @return HashMap<String, Integer>
	 */
	private static HashMap<String, Integer> setGroups(ObservableList<String> list) {
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
				if (string.equals("Hardware Dependend")) {
					sB.append(propertiesReader.getHardwareDependend() + " ");
				}
				if (string.equals("Runtime")) {
					sB.append(propertiesReader.getRuntime() + " ");
				}
				if (string.equals("Security Critical")) {
					sB.append(propertiesReader.getSecurityCritical() + " ");
				}
				if (string.equals("Safety Critical")) {
					sB.append(propertiesReader.getSafetyCritical() + " ");
				}
				if (string.equals("Time Specification")) {
					sB.append(getTimeInterval(propertiesReader) + " ");
				}
			}
			if (!(uniqueProperties.containsKey(sB.toString()) | sB.toString().equals(""))) {
				uniqueProperties.put(sB.toString(), counter++);
			}
		}
		System.out.println(uniqueProperties);
		return uniqueProperties;
	}
	
	public static int interval = 1000;

	/**
	 * Adjusts the time interval.
	 * 
	 * @param propertiesReader
	 * @return String
	 */
	public static String getTimeInterval(PropertiesReader propertiesReader) {
		double time = propertiesReader.getTime();
		double timeInNS = 0;
		if (propertiesReader.getTimeSpecification().getName().equals("s")) {
			timeInNS = time * 1000 * 1000 * 1000;
		}
		if (propertiesReader.getTimeSpecification().getName().equals("ms")) {
			timeInNS = time * 1000 * 1000;
		}
		if (propertiesReader.getTimeSpecification().getName().equals("nss")) {
			timeInNS = time * 1000;
		}
		if (propertiesReader.getTimeSpecification().getName().equals("ns")) {
			timeInNS = time;
		}
		if (propertiesReader.getTimeSpecification().getName().equals("notDefined")) {
			return "notDefined";
		}
		String unity = "ns";
		int length = (int) (Math.log10(timeInNS) + 1);
		double timeFrom = Math.abs(timeInNS - (timeInNS % interval));
		double timeTo = timeFrom + interval;
		if (length > 3 && length <= 6) {
			timeFrom /= 1000;
			timeTo /= 1000;
			unity = "µs";
		}
		if (length > 6 && length <= 9) {
			timeFrom /= 1000*1000;
			timeTo /= 1000*1000;
			unity = "ms";

		}
		if (length > 9) {
			timeFrom /= 1000*1000*1000;
			timeTo /= 1000*1000*1000;
			unity = "s";
		}
		if ((int) timeFrom != (int) timeTo) {
		return "Time between >" + (int) timeFrom + unity + " and <" + (int) timeTo + unity;
		} else {
		return "Time of ~" + timeFrom + unity;
		}
	}


	/**
	 * The property and its characteristics are assigned to corresponding groups or subgroups. 
	 * Safty Critical -> ASIL A (1.3) and FunctionalScope -> Crossfunctional (2.1)
	 * --> {(1.3),(2.1)}
	 * @param fidlReader
	 * @return HashMap<Integer,Integer>
	 */
	static HashMap<Integer, Integer> getGroup(FidlReader fidlReader) {
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
			if (string.equals("Hardware Dependend")) {
				sB.append(propertiesReader.getHardwareDependend() + " ");
			}
			if (string.equals("Runtime")) {
				sB.append(propertiesReader.getRuntime() + " ");
			}
			if (string.equals("Security Critical")) {
				sB.append(propertiesReader.getSecurityCritical() + " ");
			}
			if (string.equals("Safety Critical")) {
				sB.append(propertiesReader.getSafetyCritical() + " ");
			}
			if (string.equals("Time Specification")) {
				sB.append(getTimeInterval(propertiesReader) + " ");
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
			if (string.equals("Hardware Dependend")) {
				sB1.append(propertiesReader.getHardwareDependend() + " ");
			}
			if (string.equals("Runtime")) {
				sB1.append(propertiesReader.getRuntime() + " ");
			}
			if (string.equals("Security Critical")) {
				sB1.append(propertiesReader.getSecurityCritical() + " ");
			}
			if (string.equals("Safety Critical")) {
				sB1.append(propertiesReader.getSafetyCritical() + " ");
			}
			if (string.equals("Time Specification")) {
				sB.append(getTimeInterval(propertiesReader) + " ");
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
			if (string.equals("Hardware Dependend")) {
				sB2.append(propertiesReader.getHardwareDependend() + " ");
			}
			if (string.equals("Runtime")) {
				sB2.append(propertiesReader.getRuntime() + " ");
			}
			if (string.equals("Security Critical")) {
				sB2.append(propertiesReader.getSecurityCritical() + " ");
			}
			if (string.equals("Safety Critical")) {
				sB2.append(propertiesReader.getSafetyCritical() + " ");
			}
			if (string.equals("Time Specification")) {
				sB.append(getTimeInterval(propertiesReader) + " ");
			}
		}
		if (!subsubGroup.isEmpty()) {
			grouping.put(2, subsubGroup.get(sB2.toString()));

		}
		return grouping;
	}

}
