package tum.franca.graph.cells.servicegroup;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tum.franca.factory.creator.ServiceCreation;
import tum.franca.factory.creator.ServiceGroupCreation;
import tum.franca.graph.cells.AbstractCell;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.graph.Graph;
import tum.franca.main.MainApp;
import tum.franca.main.window.ColorPickerWindow;
import tum.franca.util.Binding;
import tum.franca.util.RectangleUtil;
import tum.franca.util.propertyfunction.PropertyEntity;
import tum.franca.view.metric.GroupMetrics;
import tum.franca.view.metric.VadimCebotariGroupMetrics;
import tum.franca.view.tab.MenuBarTop;
import tum.franca.view.treeView.GroupTreeViewCreator;

/**
 * 
 * @author michaelschott ResziableRectangleCell represents a service group.
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

		// ---------
		Line hLine = new Line();
		hLine.fillProperty().bind(view.fillProperty());
		hLine.strokeProperty().bind(view.strokeProperty());
		hLine.startXProperty().bind(view.layoutXProperty());
		hLine.startYProperty().bind(view.layoutYProperty().add(25));
		hLine.endXProperty().bind(view.widthProperty().divide(3));
		hLine.endYProperty().bind(view.layoutYProperty().add(25));

		// /
		// /
		// /
		Line dLine = new Line();
		dLine.fillProperty().bind(view.fillProperty());
		dLine.strokeProperty().bind(view.strokeProperty());
		dLine.startXProperty().bind(view.widthProperty().divide(3));
		dLine.startYProperty().bind(view.layoutYProperty().add(25));
		dLine.endXProperty().bind(view.widthProperty().divide(3).add(15));
		dLine.endYProperty().bind(view.layoutYProperty().add(15));

		// |
		// |
		// |
		Line vLine = new Line();
		vLine.fillProperty().bind(view.fillProperty());
		vLine.strokeProperty().bind(view.strokeProperty());
		vLine.startXProperty().bind(view.widthProperty().divide(3).add(15));
		vLine.startYProperty().bind(view.layoutYProperty().add(15));
		vLine.endXProperty().bind(view.widthProperty().divide(3).add(15));
		vLine.endYProperty().bind(view.layoutYProperty());

		Label label = new Label(getName());
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

		label.layoutXProperty().bind(view.layoutXProperty().add(10));
		label.layoutYProperty().bind(view.layoutYProperty().add(2));
		Tooltip toolTip = new Tooltip(label.getText());
		toolTip.setStyle("-fx-font-size: 18");
		label.setTooltip(toolTip);
		label.setPrefWidth(hLine.endXProperty().subtract(hLine.startXProperty()).subtract(3).doubleValue());

		hLine.endXProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				label.setPrefWidth(hLine.endXProperty().subtract(hLine.startXProperty()).subtract(3).doubleValue());
			}
		});

		view.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (view.getWidth() <= 100 || view.heightProperty().doubleValue() <= 40) {
					label.setVisible(false);
					hLine.setVisible(false);
					dLine.setVisible(false);
					vLine.setVisible(false);
				}
				if (view.getWidth() > 100 && view.heightProperty().doubleValue() > 40) {
					label.setVisible(true);
					hLine.setVisible(true);
					dLine.setVisible(true);
					vLine.setVisible(true);
				}
			}
		});

		view.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (view.widthProperty().doubleValue() <= 40 || view.heightProperty().doubleValue() <= 40) {
					label.setVisible(false);
					hLine.setVisible(false);
					dLine.setVisible(false);
					vLine.setVisible(false);
				}
				if (view.getWidth() > 100 && view.heightProperty().doubleValue() > 40) {
					label.setVisible(true);
					hLine.setVisible(true);
					dLine.setVisible(true);
					vLine.setVisible(true);
				}
			}
		});

		// Set invisible
		imageView.setVisible(false);
		cellGestures.setInvisible();

		// Add to pane
		pane.getChildren().add(imageView);
		pane.getChildren().add(label);
		pane.getChildren().add(hLine);
		pane.getChildren().add(dLine);
		pane.getChildren().add(vLine);

		// Add Eventfilter
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedPropertyFunction);
		pane.addEventFilter(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);
		pane.addEventFilter(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
		pane.addEventFilter(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
		pane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> imageView.setVisible(true));
		pane.addEventFilter(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);

		pane.setOnMouseClicked(e -> e.consume());

		return pane;
	}

	EventHandler<MouseEvent> onMouseClickedPropertyFunction = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			ResContxtMenu.getContextMenu(cell, pane, event);
		}
	};

	/**
	 * Returns all Rectangles which are in this group.
	 * 
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
	 * 
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

	// ***** GETTER AND SETTER START ******

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

	// ***** GETTER AND SETTER END ******

	// ***** EVENTHANDLER START ******

	EventHandler<MouseEvent> onMouseEnteredEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			cellGestures.setVisible();
			imageView.setVisible(true);
		}
	};

	EventHandler<MouseEvent> onMouseExitedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			cellGestures.setInvisible();
			imageView.setVisible(false);
		}
	};

	EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			GroupMetrics.setAll(cell);
			VadimCebotariGroupMetrics.setAll(cell);
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

				ContextMenu menu = new ContextMenu();
				MenuItem remove = new MenuItem("Remove Group");
				SeparatorMenuItem sep1 = new SeparatorMenuItem();
				MenuItem changeColor = new MenuItem("Change Color");
				SeparatorMenuItem sep2 = new SeparatorMenuItem();
				MenuItem newService = new MenuItem("New Service");
				MenuItem newServiceGroup = new MenuItem("New Group");
				menu.getItems().addAll(newService, newServiceGroup, sep2, changeColor, sep1, remove);

				menu.show(pane, event.getSceneX(), event.getSceneY());

				remove.setOnAction(e -> {
					MainApp.graph.removeCell(cell);
					MainApp.graph.getModel().removeCell(cell);
				});

				newService.setOnAction(e -> {
					int x = (int) ((MainApp.graph.getCanvas().getTranslateX() / MainApp.graph.getScale() * -1.0
							+ event.getX()));

					int y = (int) ((MainApp.graph.getCanvas().getTranslateY() / MainApp.graph.getScale() * -1.0
							+ event.getY()));
					ServiceCreation.initServiceCreationWithLocation(event.getSceneX(), event.getSceneY(), x, y);
				});

				newServiceGroup.setOnAction(e -> {
					int x = (int) ((MainApp.graph.getCanvas().getTranslateX() / MainApp.graph.getScale() * -1.0
							+ event.getX()));

					int y = (int) ((MainApp.graph.getCanvas().getTranslateY() / MainApp.graph.getScale() * -1.0
							+ event.getY()));

					ServiceGroupCreation.initServiceGroupCreationWithLocation(event.getSceneX(), event.getSceneY(), x,
							y);
				});

				changeColor.setOnAction(e -> {
					ColorPickerWindow colorPickerWindow = new ColorPickerWindow();
					colorPickerWindow.start(new Stage());
					color = (Color) view.getFill();
					colorStroke = (Color) view.getStroke();
					ColorPickerWindow.initColorPicker(view, color, colorStroke, event.getSceneX(), event.getSceneY(),
							cell);
				});

				event.consume();
			}
		}
	};

	EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			Binding.unbind(pane, style.ordinal());
			if (MenuBarTop.alignOnGrid) {
				System.out.println("X: " + pane.getLayoutX());
				System.out.println("Y: " + pane.getLayoutY());
				if (pane.getLayoutX() >= 0) {
					if (pane.getLayoutX() % 50 <= 35 && pane.getLayoutX() % 50 != 0) {
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
					if (pane.getLayoutX() % 50 >= -35 && pane.getLayoutX() % 50 != -0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutX(pane.getLayoutX() - pane.getLayoutX() % 50);
						System.out.println("X1, newly calculated X: " + (pane.getLayoutX() - pane.getLayoutX() % 50));
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutX() % 50 < -35) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutX(pane.getLayoutX() - (50 + (pane.getLayoutX() % 50)));
						System.out.println(
								"X2, newly calculated X: " + (pane.getLayoutX() - (50 + (pane.getLayoutX() % 50))));
						Binding.unbind(pane, style.ordinal());
					}
				}
				if (pane.getLayoutY() >= 0) {
					if (pane.getLayoutY() % 50 <= 35 && pane.getLayoutY() % 50 != 0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutY() - pane.getLayoutY() % 50);
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutY() % 50 > 35) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutY() + 50 - (pane.getLayoutY() % 50));
						Binding.unbind(pane, style.ordinal());
					}
				} else { // pane.getLayoutY()
							// <
							// 0
					if (pane.getLayoutY() % 50 >= -35 && pane.getLayoutY() % 50 != -0) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutY() - pane.getLayoutY() % 50);
						System.out.println("Y1, newly calculated Y: " + (pane.getLayoutY() - pane.getLayoutY() % 50));
						Binding.unbind(pane, style.ordinal());
					} else if (pane.getLayoutY() % 50 < -35) {
						Binding.bind(pane, style.ordinal());
						pane.setLayoutY(pane.getLayoutY() - (50 + (pane.getLayoutY() % 50)));
						System.out.println(
								"Y2, newly calculated Y: " + (pane.getLayoutY() - (50 + (pane.getLayoutY() % 50))));
						Binding.unbind(pane, style.ordinal());

					}

				}
			}
		}
	};

	// ***** EVENTHANDLER END ******

}
