 package tum.franca.util;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class ColorUtil {

	
	static List<String> colorListWebString = Arrays.asList(new String[] { "#003300", "#666600", "#999900",
	"#994d00", "#993300", "#990000", "#4d001a", "#330033", "1a0068", "#001a80", "#003333" });
	
	public static int colorIndex = -1;
	
	/**
	 * 
	 */
	public static void recolorCanvas() {
		int index = colorIndex;
		int counter = 0;
		for (ICell cell1 : MainApp.graph.getModel().getAddedCells()) {
			if (cell1 instanceof ResizableRectangleCell) {
				if (((ResizableRectangleCell) cell1).style.ordinal() == RectangleUtil.getHighestGroup()) {
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
								if (((ResizableRectangleCell) cell2).style.ordinal() == RectangleUtil.getHighestGroup() - 1) {
									((ResizableRectangleCell) cell2).view
											.setFill(Color.web(colorListWebString.get(counter), 0.15));
									((ResizableRectangleCell) cell2).color = Color.web(colorListWebString.get(counter),
											0.15);
									((ResizableRectangleCell) cell2).view
											.setStroke(Color.web(colorListWebString.get(counter), 1));
									((ResizableRectangleCell) cell2).colorStroke = Color
											.web(colorListWebString.get(counter), 1);
								} else if (((ResizableRectangleCell) cell2).style.ordinal() == RectangleUtil.getHighestGroup() - 2) {
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
