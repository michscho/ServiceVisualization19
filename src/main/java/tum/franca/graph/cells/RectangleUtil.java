package tum.franca.graph.cells;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class RectangleUtil {

	/**
	 * Get low right Point of Rectangle.
	 * 
	 * ********* * * ********X <----
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static Point getPointOfRechtangle(double x, double y, double width, double height) {
		double newX = x + width;
		double newY = y + height;
		return new Point((int) newX, (int) newY);
	}

	/**
	 * Method to check if two Rectangles do overlap.
	 * 
	 * @param l1
	 * @param r1
	 * @param l2
	 * @param r2
	 * @return
	 */
	public static boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
		if (l1.x > r2.x || l2.x > r1.x) {
			return false;
		}

		if (l1.y > r2.y || l2.y > r1.y) {
			return false;
		}

		return true;
	}

	/**
	 * Returns the highest group of Graph. A graph can have 1-3 groups (Group,
	 * subgroups, subsubgroup). Depending on the available ResizableRectangleCell
	 * you can determine the numbers of different groups.
	 * 
	 * @return int
	 */
	public static int getHighestGroup() {
		int highestGroup = -1;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) iCell).style.ordinal() > highestGroup) {
					highestGroup = ((ResizableRectangleCell) iCell).style.ordinal();
				}
			}
		}
		return highestGroup;
	}

	/**
	 * Returns the number of Top-Level Groups.
	 * 
	 * @return int
	 */
	public static int getNumberOfTopLevelGroup() {
		int counter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) iCell).style.ordinal() == getHighestGroup()) {
					counter++;
				}
			}
		}
		return counter;
	}

	public static boolean inconsistantBoardState() {
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			for (ICell iCell2 : MainApp.graph.getModel().getAddedCells()) {
				if (!iCell.equals(iCell2)) {
					if (iCell instanceof ResizableRectangleCell) {
						if (iCell2 instanceof ResizableRectangleCell) {
							if (((ResizableRectangleCell) iCell).style == ((ResizableRectangleCell) iCell2).style) {
								ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
								ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell2;
								Point point = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
								Point point2 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(),
										cell2.pane.getLayoutY(),
										cell2.pane.getWidth() == 0 ? cell2.pane.getPrefWidth() : cell2.pane.getWidth(),
										cell2.pane.getHeight() == 0 ? cell2.pane.getPrefHeight()
												: cell2.pane.getHeight());
								Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
								Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
										cell.pane.getLayoutY(),
										cell.pane.getWidth() == 0 ? cell.pane.getPrefWidth() : cell.pane.getWidth(),
										cell.pane.getHeight() == 0 ? cell.pane.getPrefHeight() : cell.pane.getHeight());

								if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
									System.out.println(RectangleUtil.class);
									System.out.println(cell2.getName());
									System.out.println(cell.getName());
									System.out.println("inconsistant");
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static List<ResizableRectangleCell> inconsistantList;

	/**
	 * Sub-Level Group bigger than High-Level Group
	 * 
	 * @return
	 */
	public static void inconsistantBoardState2() {
		inconsistantList = new ArrayList<ResizableRectangleCell>();
		boolean inconsitant = false;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			for (ICell iCell2 : MainApp.graph.getModel().getAddedCells()) {
				if (!iCell.equals(iCell2)) {
					if (iCell instanceof ResizableRectangleCell) {
						if (iCell2 instanceof ResizableRectangleCell) {
							if (((ResizableRectangleCell) iCell).style
									.ordinal() > ((ResizableRectangleCell) iCell2).style.ordinal()) {
								ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
								ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell2;
								Point point = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
								Point point2 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(),
										cell2.pane.getLayoutY(),
										cell2.pane.getWidth() == 0 ? cell2.pane.getPrefWidth() : cell2.pane.getWidth(),
										cell2.pane.getHeight() == 0 ? cell2.pane.getPrefHeight()
												: cell2.pane.getHeight());
								Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
								Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
										cell.pane.getLayoutY(),
										cell.pane.getWidth() == 0 ? cell.pane.getPrefWidth() : cell.pane.getWidth(),
										cell.pane.getHeight() == 0 ? cell.pane.getPrefHeight() : cell.pane.getHeight());

								int counter = 0;
								if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
									counter++;
								}
								final double x0 = cell.pane.getLayoutX();
								final double y0 = cell.pane.getLayoutY();
								final double x = cell2.pane.getLayoutX();
								final double y = cell2.pane.getLayoutY();
								final double w = cell2.pane.getWidth();
								final double h = cell2.pane.getHeight();

								// consists of
								if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + cell.pane.getWidth()))
										&& ((y + h) <= (y0 + cell.pane.getHeight()))) {
									counter++;
								}

								if (counter == 1) {
									inconsistantList.add(cell2);
								}

							}
						}
					}
				}
			}
		}
		if (inconsistantList.size() == 0) {
			for (ICell rezCell : MainApp.graph.getModel().getAddedCells()) {
				if (rezCell instanceof ResizableRectangleCell) {
					((ResizableRectangleCell) rezCell).view.setStrokeWidth(1);
					MainApp.graph.getNodeGestures().makeDraggable(MainApp.graph.getGraphic(rezCell), rezCell);
				}
			}
		} else {
			for (ICell rezCell : MainApp.graph.getModel().getAddedCells()) {
				if (rezCell instanceof ResizableRectangleCell) {

					((ResizableRectangleCell) rezCell).view.setStrokeWidth(1);
					MainApp.graph.getNodeGestures().makeUndraggable(MainApp.graph.getGraphic(rezCell));
				}
			}
		}
		for (ResizableRectangleCell rezCell2 : inconsistantList) {
			if (getHighestGroup(inconsistantList) == ((ResizableRectangleCell) rezCell2).style.ordinal()) {
				rezCell2.view.setStrokeWidth(8);
				MainApp.graph.getNodeGestures().makeDraggable(MainApp.graph.getGraphic(rezCell2), rezCell2);
			} else {
				MainApp.graph.getNodeGestures().makeUndraggable(MainApp.graph.getGraphic(rezCell2));
			}
		}
	}

	public static int getHighestGroup(List<ResizableRectangleCell> inconsistantList) {
		int i = 0;
		for (ResizableRectangleCell cell : inconsistantList) {
			if (cell.style.ordinal() > i) {
				i = cell.style.ordinal();
			}
		}
		return i;
	}

	private static List<String> colorListWebString = Arrays.asList(new String[] { "#003300", "#666600", "#999900",
			"#994d00", "#993300", "#990000", "#4d001a", "#330033", "1a0068", "#001a80", "#003333" });

	public static int colorIndex = -1;

	public static void recolorCanvas() {
		int index = colorIndex;
		int counter = 0;
		for (ICell cell1 : MainApp.graph.getModel().getAddedCells()) {
			if (cell1 instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) cell1).style.ordinal() == getHighestGroup()) {
					index++;
					counter = Math.abs(index) % 11;
					((ResizableRectangleCell) cell1).view.setFill(Color.web(colorListWebString.get(counter), 0.05));
					((ResizableRectangleCell) cell1).view.setStroke(Color.web(colorListWebString.get(counter), 1));
					((ResizableRectangleCell) cell1).color = Color.web(colorListWebString.get(counter), 0.05);
					((ResizableRectangleCell) cell1).colorStroke = Color.web(colorListWebString.get(counter), 1);
					for (ICell cell2 : MainApp.graph.getModel().getAddedCells()) {
						if (cell2 instanceof ResizableRectangleCell && !cell2.equals(cell1)) {
							ResizableRectangleCell cellRez = (ResizableRectangleCell) cell1;
							ResizableRectangleCell cell2Rez = (ResizableRectangleCell) cell2;
							Point point = new Point((int) cell2Rez.pane.getLayoutX(), (int) cell2Rez.pane.getLayoutY());
							Point point2 = RectangleUtil.getPointOfRechtangle(cell2Rez.pane.getLayoutX(),
									cell2Rez.pane.getLayoutY(),
									cell2Rez.pane.getWidth() == 0 ? cell2Rez.pane.getPrefWidth()
											: cell2Rez.pane.getWidth(),
									cell2Rez.pane.getHeight() == 0 ? cell2Rez.pane.getPrefHeight()
											: cell2Rez.pane.getHeight());
							Point point3 = new Point((int) cellRez.pane.getLayoutX(), (int) cellRez.pane.getLayoutY());
							Point point4 = RectangleUtil.getPointOfRechtangle(cellRez.pane.getLayoutX(),
									cellRez.pane.getLayoutY(),
									cellRez.pane.getWidth() == 0 ? cellRez.pane.getPrefWidth()
											: cellRez.pane.getWidth(),
									cellRez.pane.getHeight() == 0 ? cellRez.pane.getPrefHeight()
											: cellRez.pane.getHeight());

							if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
								if (((ResizableRectangleCell) cell2).style.ordinal() == getHighestGroup() - 1) {
									((ResizableRectangleCell) cell2).view
											.setFill(Color.web(colorListWebString.get(counter), 0.15));
									((ResizableRectangleCell) cell2).color = Color.web(colorListWebString.get(counter),
											0.15);
									((ResizableRectangleCell) cell2).view
											.setStroke(Color.web(colorListWebString.get(counter), 1));
									((ResizableRectangleCell) cell2).colorStroke = Color
											.web(colorListWebString.get(counter), 1);
								} else if (((ResizableRectangleCell) cell2).style.ordinal() == getHighestGroup() - 2) {
									((ResizableRectangleCell) cell2).view
											.setFill(Color.web(colorListWebString.get(counter), 0.25));
									((ResizableRectangleCell) cell2).color = Color.web(colorListWebString.get(counter),
											0.25);
									((ResizableRectangleCell) cell2).view
											.setStroke(Color.web(colorListWebString.get(counter), 1));
									((ResizableRectangleCell) cell2).colorStroke = Color
											.web(colorListWebString.get(counter), 1);

								}
							}
						}
					}
				}
			}
		}

	}
}
