package tum.franca.graph.cells;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ShadowBuilder;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;
import tum.franca.main.Binding;
import tum.franca.main.ChangeWindow;
import tum.franca.main.ContxtMenuCells;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;
import tum.franca.tabs.MenuBarTop;
import tum.franca.view.treeView.SimpleTreeViewCreator;

/**
 * 
 * @author michaelschott
 * @param <onRectangleLayoutXChange>
 *
 */
public class RectangleCell extends AbstractCell {

	public RectangleCell recCell;
	public String name;
	public HashMap<Integer, Integer> groupNumbers;
	public boolean used;
	public StackPane pane;
	public Rectangle view;
	public transient FidlReader fidlReader;
	public Text text;
	private TextField textField;

	public RectangleCell(String name, HashMap<Integer, Integer> groupNumbers, FidlReader fidlReader) {
		this.recCell = this;
		this.name = name;
		this.groupNumbers = groupNumbers;
		this.fidlReader = fidlReader;
	}

	// for Example: 0,1 / 1,2 / 2,3
	public HashMap<Integer, Integer> getGrouping() {
		return groupNumbers;
	}

	@Override
	public Region getGraphic(Graph graph) {
		view = new Rectangle(100, 40);

		view.setStroke(Color.BLACK);
		view.setFill(Color.WHITE);

		pane = new StackPane(view);
		pane.setPrefSize(100, 40);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());

		DropShadow e = new DropShadow();
		e.setWidth(8);
		e.setHeight(8);
		e.setOffsetX(2);
		e.setOffsetY(2);
		e.setRadius(2);
		view.setEffect(e);

		String input = "";
		if (getName().length() >= 13) {
			input = getName().substring(0, 12) + "...";
		} else {
			input = getName();
		}

		text = new Text(input);
		pane.getChildren().add(text);

		textField = new TextField(name);

		pane.addEventFilter(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);

		// pane.addEventFilter(MouseEvent.MOUSE_PRESSED, onPressedEventHandler);

		pane.addEventFilter(MouseEvent.MOUSE_RELEASED, onReleasedEventHandler);

		pane.addEventFilter(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);

		return pane;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FidlReader getFidlReader() {
		return StaticFidlReader.getFidl(this);
	}

	/**
	 * 
	 * @return List<ResizableRectangleCell>
	 */
	public List<ResizableRectangleCell> getGroupRectangle() {
		List<ResizableRectangleCell> outputList = new ArrayList<ResizableRectangleCell>();
		List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
		for (ICell iCell : cellList) {
			if (iCell instanceof ResizableRectangleCell) {

				ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
				Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
				Point point2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(), pane.getLayoutY(),
						pane.getWidth() == 0 ? pane.getPrefWidth() : pane.getWidth(),
						pane.getHeight() == 0 ? pane.getPrefHeight() : pane.getHeight());
				Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
				Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(), cell.pane.getLayoutY(),
						cell.pane.getWidth() == 0 ? cell.pane.getPrefWidth() : cell.pane.getWidth(),
						cell.pane.getHeight() == 0 ? cell.pane.getPrefHeight() : cell.pane.getHeight());

				if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
					outputList.add(cell);
				}
			}
		}
		return outputList;
	}

	EventHandler<MouseEvent> onMouseExitedEventHandler = t -> {
		if (textField != null) {
			pane.getChildren().remove(textField);
		}
	};

	EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			SimpleTreeViewCreator treeView = new SimpleTreeViewCreator(name);
			treeView.createTree();
			if (MainAppController.staticFileChanges.isSelected() && !(t.getButton() == MouseButton.SECONDARY)) {
				pane.getChildren().add(textField);
				textField.textProperty().addListener((observable, oldValue, newValue) -> {
					String input = "";
					if (textField.getText().length() >= 13) {
						input = textField.getText().substring(0, 12) + "...";
					} else {
						input = textField.getText();
					}
					text.setText(input);
					getFidlReader().getPropertiesReader().setInterfaceName(textField.getText());
				});
			}
			if (MainAppController.staticFileChanges.isSelected() && t.getButton() == MouseButton.SECONDARY) {
				ContxtMenuCells.setContextMenu(pane, fidlReader);
				ContxtMenuCells.getContextMenu().show(pane, t.getSceneX(), t.getSceneY());
			}
		}
	};

	List<ICell> intersectionCellListBefore = new ArrayList<ICell>();

//	EventHandler<MouseEvent> onPressedEventHandler = new EventHandler<MouseEvent>() {
//		@Override
//		public void handle(MouseEvent event) {
//			if (MainAppController.staticFileChanges.isSelected() && event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
//				List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
//
//				for (ICell iCell : cellList) {
//					if (iCell instanceof ResizableRectangleCell) {
//
//						ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
//						Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
//						Point point2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(), pane.getLayoutY(),
//								pane.getWidth(), pane.getHeight());
//						Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
//						Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
//								cell.pane.getLayoutY(), cell.pane.getWidth(), cell.pane.getHeight());
//
//						if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
//							intersectionCellListBefore.add(iCell);
//							System.out.println(intersectionCellListBefore);
//						}
//					}
//				}
//			}
//		}
//	};

	List<ICell> intersectionCellListAfter = new ArrayList<ICell>();

	EventHandler<MouseEvent> onReleasedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (MenuBarTop.alignOnGrid) {
				if (pane.getLayoutX() >= 0) {
					if (pane.getLayoutX() % 50 < 15 && pane.getLayoutX() % 50 != 0) {
						pane.setLayoutX(pane.getLayoutX() - pane.getLayoutX() % 50);
					} else if (pane.getLayoutX() % 50 > 35) {
						pane.setLayoutX(pane.getLayoutX() + 50 - (pane.getLayoutX() % 50));
					}
				} else { // pane.getLayoutX() < 0
					if (pane.getLayoutX() % 50 > -15 && pane.getLayoutX() % 50 != -0) {
						pane.setLayoutX(pane.getLayoutX() + pane.getLayoutX() % 50);
					} else if (pane.getLayoutX() % 50 < -35) {
						pane.setLayoutX(pane.getLayoutX() - (50 + (pane.getLayoutX() % 50)));
					}
				}
				if (pane.getLayoutY() >= 0) {
					if (pane.getLayoutY() % 50 < 10 && pane.getLayoutY() % 50 != 0) {
						pane.setLayoutY(pane.getLayoutY() - pane.getLayoutY() % 50);
					} else if (pane.getLayoutY() % 50 > 40) {
						pane.setLayoutY(pane.getLayoutY() + 50 - (pane.getLayoutY() % 50));
					}
				} else { // pane.getLayoutY() < 0
					if (pane.getLayoutY() % 50 > -15 && pane.getLayoutY() % 50 != -0) {
						pane.setLayoutY(pane.getLayoutX() + pane.getLayoutY() % 50);
					} else if (pane.getLayoutY() % 50 < -35) {
						pane.setLayoutY(pane.getLayoutX() - (50 + (pane.getLayoutY() % 50)));
					}

				}
			}
//			if (MainAppController.staticFileChanges.isSelected()  && event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
//				List<ICell> cellList = MainApp.graph.getModel().getAddedCells();
//				for (ICell iCell : cellList) {
//					if (iCell instanceof ResizableRectangleCell) {
//
//						ResizableRectangleCell cell = (ResizableRectangleCell) iCell;
//						Point point = new Point((int) pane.getLayoutX(), (int) pane.getLayoutY());
//						Point point2 = RectangleUtil.getPointOfRechtangle(pane.getLayoutX(), pane.getLayoutY(),
//								pane.getWidth(), pane.getHeight());
//						Point point3 = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
//						Point point4 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(),
//								cell.pane.getLayoutY(), cell.pane.getWidth(), cell.pane.getHeight());
//
//						if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
//							intersectionCellListAfter.add(iCell);
//						}
//					}
//				}
//				boolean changed = false;
//				for (ICell iCell : intersectionCellListAfter) {
//					boolean sameInBoth = false;
//					for (ICell iCell2 : intersectionCellListBefore) {
//						if (iCell2.equals(iCell)) {
//							sameInBoth = true;
//						}
//					}
//					if (!sameInBoth | intersectionCellListAfter.size() != intersectionCellListBefore.size()) {
//						changed = true;
//					}
//				}
//				if (intersectionCellListAfter.size() != intersectionCellListBefore.size()) {
//					changed = true;
//				}
//
//				if (changed) {
//					ChangeWindow changeWindow = new ChangeWindow();
//					changeWindow.showChangeWindow(intersectionCellListBefore, intersectionCellListAfter);
//				}
//
//				intersectionCellListAfter.clear();
//				intersectionCellListBefore.clear();
//			}
		}
	};

}
