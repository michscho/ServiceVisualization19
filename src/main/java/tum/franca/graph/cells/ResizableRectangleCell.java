package tum.franca.graph.cells;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tum.franca.graph.graph.Graph;
import tum.franca.main.Binding;
import tum.franca.main.ColorPickerWindow;
import tum.franca.main.MainApp;
import tum.franca.view.treeView.GroupTreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class ResizableRectangleCell extends AbstractCell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Rectangle view;
	private String name;
	public int width;
	public int heigth;
	public FontStyle style;
	public Pane pane;
	public Color color;
	public Color colorStroke;
	public int x;
	public int y;

	public enum FontStyle {
		SMALL, MEDIUM, BIG
	}

	/**
	 * 
	 * @param width
	 * @param heigth
	 * @param name
	 * @param style
	 */
	public ResizableRectangleCell(int width, int heigth, String name, ResizableRectangleCell.FontStyle style) {
		this.name = name;
		this.width = width;
		this.heigth = heigth;
		this.style = style;
		Random random = new Random();
		final float R = random.nextFloat();
		final float G = random.nextFloat();
		final float B = random.nextFloat();
		this.view = new Rectangle(width, heigth);
		color = new Color(R, G, B, 0.09);
		colorStroke = new Color(R, G, B, 0.8);
	}

	@Override
	public Region getGraphic(Graph graph) {

		view.setStroke(colorStroke);
		view.setFill(color);
		view.setStyle("-fx-stroke-dash-array: 15 15 15 15;");

		pane = new Pane(view);
		pane.setPrefSize(width, heigth);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());
		CellGestures cG = new CellGestures();
		cG.makeResizable(pane);
		cG.setInvisible();
		
		pane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
            	ColorPickerWindow colorPickerWindow = new ColorPickerWindow();
        		colorPickerWindow.start(new Stage());
        		color = (Color) view.getFill();
        		colorStroke = (Color) view.getStroke();
        		ColorPickerWindow.initColorPicker(view, color, colorStroke, event.getSceneX(), event.getSceneY());
            	
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
		
				
		pane.addEventFilter(
			    MouseEvent.MOUSE_ENTERED,
			    new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent event) {
			        	cG.setVisible();
			        }
			    }
			);
		
	pane.setOnMouseExited(new EventHandler<MouseEvent>()
    {
        @Override
        public void handle(MouseEvent t) {
        	cG.setInvisible();
        }
    });
		
		this.pane.addEventFilter(
			    MouseEvent.MOUSE_PRESSED,
			    new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent event) {
			        	Binding.bind(pane, style.ordinal());
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
			    }
			);
		
		this.pane.addEventFilter(
			    MouseEvent.MOUSE_RELEASED,
			    new EventHandler<MouseEvent>() {
			        @Override
			        public void handle(MouseEvent event) {
			        	Binding.unBind(pane, style.ordinal());
			        }
			    }
			);


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

	public Pane getPane() {
		return pane;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
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
