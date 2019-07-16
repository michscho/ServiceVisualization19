package tum.franca.graph.cells;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;
import tum.franca.main.ContxtMenu;
import tum.franca.main.MainApp;
import tum.franca.view.treeView.GroupTreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class ResizableRectangleCell extends AbstractCell {

	private final Rectangle view;
	private String name;
	private int x;
	private int y;
	private FontStyle style;
	private Pane pane;

	public enum FontStyle {
		SMALL, MEDIUM, BIG
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param name
	 * @param style
	 */
	public ResizableRectangleCell(int x, int y, String name, ResizableRectangleCell.FontStyle style) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.style = style;
		this.view = new Rectangle(x, y);
	}

	@Override
	public Region getGraphic(Graph graph) {

		Random random = new Random();
		final float R = random.nextFloat();
		final float G = random.nextFloat();
		final float B = random.nextFloat();
		Color color = new Color(R, G, B, 0.09);
		Color colorStroke = new Color(R, G, B, 0.8);

		view.setStroke(colorStroke);
		view.setFill(color);
		view.setStyle("-fx-stroke-dash-array: 15 15 15 15;");

		pane = new Pane(view);
		pane.setPrefSize(x, y);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());
		CellGestures.makeResizable(pane);
		
		pane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
 
            	ContxtMenu.setContextMenu(pane);
            	ContxtMenu.getContextMenu().show(pane, event.getSceneX(), event.getSceneY());
            }
        });

	
		Text text = new Text(getName());
		if (style == FontStyle.SMALL) {
			text.setStyle("-fx-font: 16 arial;");
		} else if (style == FontStyle.MEDIUM) {
			text.setStyle("-fx-font: 18 arial;");
		} else {
			text.setStyle("-fx-font: 20 arial;");
		}
		pane.getChildren().add(text);

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {					
					List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
					List<ICell> intersectionCellList = new ArrayList<ICell>();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {

							RectangleCell cell = (RectangleCell) iCell;
							Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
							Point point2 = getPointOfRechtangle(pane.getLayoutX(), pane.getLayoutY(), pane.getWidth(),
									pane.getHeight());
							Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
							Point point4 = getPointOfRechtangle(cell.pane.getLayoutX(), cell.pane.getLayoutY(),
									cell.pane.getWidth(), cell.pane.getHeight());

							if (doOverlap(point, point2, point3, point4)) {
								intersectionCellList.add(iCell);
							}
						}
					}
					GroupTreeViewCreator groupTreeView = new GroupTreeViewCreator(name, intersectionCellList);
					groupTreeView.createTree();
			}
		});

		return pane;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rectangle getView() {
		return view;
	}

	public static Point getPointOfRechtangle(double x, double y, double width, double height) {
		double newX = x + width;
		double newY = y + height;
		return new Point((int) newX, (int) newY);
	}

	public static boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
		if (l1.x > r2.x || l2.x > r1.x) {
			return false;
		}

		if (l1.y > r2.y || l2.y > r1.y) {
			return false;
		}

		return true;
	}

}
