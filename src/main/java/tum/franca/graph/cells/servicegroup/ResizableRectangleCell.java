package tum.franca.graph.cells.servicegroup;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tum.franca.graph.cells.AbstractCell;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.edges.IEdge;
import tum.franca.graph.graph.Graph;
import tum.franca.main.MainApp;
import tum.franca.main.window.ColorPickerWindow;
import tum.franca.util.Binding;
import tum.franca.util.RectangleUtil;
import tum.franca.util.propertyfunction.PropertyEntity;
import tum.franca.view.tab.MenuBarTop;
import tum.franca.view.treeView.GroupTreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class ResizableRectangleCell extends AbstractCell {

	public ResizableRectangleCell cell;
	public Rectangle view;
	private String name;
	public GroupType style;
	public Pane pane;
	public String group;
	public CellGestures cellGestures;
	
	// Property Function
	private ImageView imageView;
	public List<PropertyEntity> properties = new ArrayList<PropertyEntity>();
	
	// Style
	public Color color;
	public Color colorStroke;
	public int x;
	public int y;
	public int width;
	public int heigth;
	
	// Group information text
	private Text numberServicesText;
	private Text couplingText;
	private Text cohesionText;
	private Text cocoText;
	

	public enum GroupType {
		SUBSUBLEVEL, SUBLEVEL, TOPLEVEL
	}

	/**
	 * 
	 * @param width  - width in int
	 * @param heigth - height in int
	 * @param name   - Name of the Group: static, crossfunctional
	 * @param style  - Top Level Group, Sub-Level Group...
	 * @param group  - Binding, Functional Scope...
	 */
	public ResizableRectangleCell(int width, int heigth, String name, ResizableRectangleCell.GroupType style,
			String group) {
		this.cell = this;
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

	public static Pane sourcePane;

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

		// Property Function flash
		Image flashImage = new Image(MenuBarTop.class.getResourceAsStream("/flash.png"));
		imageView = new ImageView(flashImage);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		imageView.layoutXProperty().bind(view.widthProperty().subtract(18));
		imageView.layoutYProperty().bind(view.layoutYProperty().add(3));

		Text text = new Text(getName());
		if (style == GroupType.SUBSUBLEVEL) {
			text.setStyle("-fx-font: 16 arial;");
		} else if (style == GroupType.SUBLEVEL) {
			text.setStyle("-fx-font: 18 arial;");
		} else {
			text.setStyle("-fx-font: 20 arial;");
		}
		
		// Configure information text
		numberServicesText = new Text("# Services");
		numberServicesText.layoutXProperty().bind(view.widthProperty().add(10));
		numberServicesText.layoutYProperty().bind(view.layoutYProperty().add(10).add(8));
		couplingText = new Text("# Coupling");
		couplingText.layoutXProperty().bind(view.widthProperty().add(10));
		couplingText.layoutYProperty().bind(view.layoutYProperty().add(10).add(28));
		cohesionText = new Text("# Cohesion");
		cohesionText.layoutXProperty().bind(view.widthProperty().add(10));
		cohesionText.layoutYProperty().bind(view.layoutYProperty().add(10).add(48));
		cocoText = new Text("Coupling Choesion Faktor");
		cocoText.layoutXProperty().bind(view.widthProperty().add(10));
		cocoText.layoutYProperty().bind(view.layoutYProperty().add(10).add(68));
		
		// Set information text of mouse transparent
		numberServicesText.setMouseTransparent(true);
		couplingText.setMouseTransparent(true);
		cohesionText.setMouseTransparent(true);
		cocoText.setMouseTransparent(true);
		
		// Set invisible
		numberServicesText.setVisible(false);
		couplingText.setVisible(false);
		cohesionText.setVisible(false);
		cocoText.setVisible(false);
		imageView.setVisible(false);
		cellGestures.setInvisible();

		
		// Add to pane
		pane.getChildren().add(numberServicesText);
		pane.getChildren().add(couplingText);
		pane.getChildren().add(cohesionText);
		pane.getChildren().add(cocoText);
		pane.getChildren().add(imageView);
		pane.getChildren().add(text);

		// Add Eventfilter
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedPropertyFunction);
		pane.addEventFilter(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);
		pane.addEventFilter(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);
		pane.addEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
		pane.addEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
		pane.setOnMouseClicked(e -> e.consume());

		return pane;

	}

	EventHandler<MouseEvent> onMouseClickedPropertyFunction = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			ResContxtMenu.getContextMenu(cell,pane, event);
		}
	};

	/**
	 * Returns all Rectangles which are in this group.
	 * @return List<RectangleCell>
	 */
	public List<RectangleCell> containsRectangleCell() {
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

	/**
	 * Returns all Subgroups of this group.
	 * @return List<ResizableRectangleCell>
	 */
	public List<ResizableRectangleCell> containsResizableRectanlgeCells() {
		List<ResizableRectangleCell> outputList = new ArrayList<ResizableRectangleCell>();
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell && !iCell.equals(this)) {

				ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
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
	
	//***** GETTER AND SETTER START ******

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
	
	//***** GETTER AND SETTER END ******
	
	private int getCohesion() {
		int counter = 0;
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			RectangleCell source = (RectangleCell) edge.getSource();
			RectangleCell target = (RectangleCell) edge.getTarget();
			if (containsRectangleCell().contains(source) && containsRectangleCell().contains(target)) {
				counter++;
			}
		}
		return counter;
	}
	
	private int getCoupling() {
		int counter = 0;
		for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
			RectangleCell source = (RectangleCell) edge.getSource();
			RectangleCell target = (RectangleCell) edge.getTarget();
			if (containsRectangleCell().contains(source) && !containsRectangleCell().contains(target) || !containsRectangleCell().contains(source) && containsRectangleCell().contains(target)) {
				counter++;
			}
		}
		return counter;
	}
	
	
	//***** EVENTHANDLER START ******

	EventHandler<MouseEvent> onMouseEnteredEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			numberServicesText.setText("# Services " + containsRectangleCell().size());
			numberServicesText.setVisible(true);
			cohesionText.setText("# Cohesion " + getCohesion());
			cohesionText.setVisible(true);
			couplingText.setText("# Coupling " + getCoupling());
			couplingText.setVisible(true);
			cellGestures.setVisible();
			imageView.setVisible(true);
			cocoText.setVisible(true);
			if ((float) getCoupling() / getCohesion() >= 1){
				cocoText.setFill(Color.RED);
				cocoText.setText("Coupling Chohesion Factor: " + ((float) getCoupling()) / getCohesion() + "\nUnlikly high, consider change!");
			} else {
				cocoText.setFill(Color.GREEN);
				cocoText.setText("Coupling Chohesion Factor: " + ((float) getCoupling()) / getCohesion() + "\nSeems ok!");
			}
		}
	};
	


	EventHandler<MouseEvent> onMouseExitedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			numberServicesText.setVisible(false);
			cohesionText.setVisible(false);
			couplingText.setVisible(false);
			cellGestures.setInvisible();
			imageView.setVisible(false);
			cocoText.setVisible(false);
		}
	};

	EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (RectangleCell.popOver == null) {
			} else {
				RectangleCell.popOver.hide();
			}
			//event.consume();
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
				ColorPickerWindow.initColorPicker(view, color, colorStroke, event.getSceneX(), event.getSceneY(), cell);
				event.consume();
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
				} else { // pane.getLayoutX()
							// <
							// 0
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
				} else { // pane.getLayoutY()
							// <
							// 0
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
	
	//***** EVENTHANDLER END ******


}
