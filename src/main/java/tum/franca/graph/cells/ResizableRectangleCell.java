package tum.franca.graph.cells;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tum.franca.graph.graph.Graph;
import tum.franca.main.Binding;
import tum.franca.main.ColorPickerWindow;
import tum.franca.main.MainApp;
import tum.franca.tabs.MenuBarTop;
import tum.franca.view.treeView.GroupTreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class ResizableRectangleCell extends AbstractCell {

	public Rectangle view;
	private String name;
	public int width;
	public int heigth;
	public GroupType style;
	public Pane pane;
	public Color color;
	public Color colorStroke;
	public int x;
	public int y;
	public String group;
	public CellGestures cellGestures;

	public enum GroupType {
		SUBSUBLEVEL, SUBLEVEL, TOPLEVEL
	}

	/**
	 * 
	 * @param width
	 * @param heigth
	 * @param name
	 * @param style
	 * @param group
	 */
	public ResizableRectangleCell(int width, int heigth, String name, ResizableRectangleCell.GroupType style,
			String group) {
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
		this.group = group;
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
		cellGestures = new CellGestures();
		cellGestures.makeResizable(pane, this);
		cellGestures.setInvisible();

		Text text = new Text(getName());
		if (style == GroupType.SUBSUBLEVEL) {
			text.setStyle("-fx-font: 16 arial;");
		} else if (style == GroupType.SUBLEVEL) {
			text.setStyle("-fx-font: 18 arial;");
		} else {
			text.setStyle("-fx-font: 20 arial;");
		}
		pane.getChildren().add(text);

		pane.addEventFilter(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);

		pane.addEventFilter(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);

		pane.addEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);

		pane.addEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

		return pane;
	}

	/**
	 * 
	 * @return List<ResizableRectangleCell>
	 */
	public List<RectangleCell> getRectangleCells() {
		List<RectangleCell> outputList = new ArrayList<RectangleCell>();
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {

				RectangleCell cell = (RectangleCell) iCell;
				Point thisResRectanglePoint1 = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
				Point thisResRectanglePoint2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(), pane.getLayoutY(),
						pane.getWidth() == 0 ? pane.getPrefWidth() : pane.getWidth(),
						pane.getHeight() == 0 ? pane.getPrefHeight() : pane.getHeight());
				Point innerRectanglePoint3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
				Point innerRectanglePoint4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
						cell.pane.getLayoutY(),
						cell.pane.getWidth() == 0 ? cell.pane.getPrefWidth() : cell.pane.getWidth(),
						cell.pane.getHeight() == 0 ? cell.pane.getPrefHeight() : cell.pane.getHeight());

				if (RectangleUtil.doOverlap(innerRectanglePoint3, innerRectanglePoint4, thisResRectanglePoint1,
						thisResRectanglePoint2)) {
					outputList.add(cell);
				}
			}
		}
		return outputList;
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

	public void setPane(StackPane pane) {
		this.pane = pane;
	}

	EventHandler<MouseEvent> onMouseEnteredEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			cellGestures.setVisible();
		}
	};

	EventHandler<MouseEvent> onMouseExitedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			cellGestures.setInvisible();
		}
	};

	EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			Binding.bind(pane, style.ordinal());
			List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
			List<ICell> intersectionCellList = new ArrayList<ICell>();
			for (ICell iCell : cellList) {
				if (iCell instanceof RectangleCell) {
					RectangleCell cell = (RectangleCell) iCell;
					Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
					Point point2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(), pane.getLayoutY(),
							pane.getWidth(), pane.getHeight());
					Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
					Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(), cell.pane.getLayoutY(),
							cell.pane.getWidth(), cell.pane.getHeight());

					if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
						intersectionCellList.add(iCell);
					}
				}
			}
			GroupTreeViewCreator groupTreeView = new GroupTreeViewCreator(name, intersectionCellList);
			groupTreeView.createTree();
			if (event.isSecondaryButtonDown() && !event.isPrimaryButtonDown()) {
				ColorPickerWindow colorPickerWindow = new ColorPickerWindow();
				colorPickerWindow.start(new Stage());
				color = (Color) view.getFill();
				colorStroke = (Color) view.getStroke();
				ColorPickerWindow.initColorPicker(view, color, colorStroke, event.getSceneX(), event.getSceneY());
			}
		}
	};

	EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			Binding.unbind(pane, style.ordinal());
			if (MenuBarTop.alignOnGrid) {
				if (pane.getLayoutX() >= 0) {
					if (pane.getLayoutX() % 50 < 15 && pane.getLayoutX() % 50 != 0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutX(pane.getLayoutX() - pane.getLayoutX() % 50);
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutX() % 50 > 35) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutX(pane.getLayoutX() + 50 - (pane.getLayoutX() % 50));
						Binding.unbind(pane, style.ordinal());
					}
				} else { // pane.getLayoutX() < 0
					if (pane.getLayoutX() % 50 > -15 && pane.getLayoutX() % 50 != -0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutX(pane.getLayoutX() + pane.getLayoutX() % 50);
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutX() % 50 < -35) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutX(pane.getLayoutX() - (50 + (pane.getLayoutX() % 50)));
						Binding.unbind(pane, style.ordinal());
					}
				}
				if (pane.getLayoutY() >= 0) {
					if (pane.getLayoutY() % 50 < 10 && pane.getLayoutY() % 50 != 0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutY() - pane.getLayoutY() % 50);
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutY() % 50 > 40) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutY() + 50 - (pane.getLayoutY() % 50));
						Binding.unbind(pane, style.ordinal());
					}
				} else { // pane.getLayoutY() < 0
					if (pane.getLayoutY() % 50 > -15 && pane.getLayoutY() % 50 != -0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutX() + pane.getLayoutY() % 50);
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutY() % 50 < -35) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutX() - (50 + (pane.getLayoutY() % 50)));
						Binding.unbind(pane, style.ordinal());
					}

				}
			}
		}
	};

}
