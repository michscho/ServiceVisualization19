package tum.franca.graph.cells;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.READER;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tum.franca.main.MainApp;

public class CellGestures {

	static final double handleRadius = 6d;

	Rectangle resizeHandleN;
	Rectangle resizeHandleNE;
	Rectangle resizeHandleE;
	Rectangle resizeHandleSE;
	Rectangle resizeHandleS;
	Rectangle resizeHandleSW;
	Rectangle resizeHandleW;
	Rectangle resizeHandleNW;

	void setInvisible() {
		resizeHandleN.setVisible(false);
		resizeHandleNE.setVisible(false);
		resizeHandleE.setVisible(false);
		resizeHandleSE.setVisible(false);
		resizeHandleS.setVisible(false);
		resizeHandleSW.setVisible(false);
		resizeHandleW.setVisible(false);
		resizeHandleNW.setVisible(false);
	}

	void setVisible() {
		resizeHandleN.setVisible(true);
		resizeHandleNE.setVisible(true);
		resizeHandleE.setVisible(true);
		resizeHandleSE.setVisible(true);
		resizeHandleS.setVisible(true);
		resizeHandleSW.setVisible(true);
		resizeHandleW.setVisible(true);
		resizeHandleNW.setVisible(true);
	}

	DragNodeSupplier NORTH = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final DoubleBinding halfWidthProperty = widthProperty.divide(2);

			resizeHandleN = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleN.xProperty().bind(xProperty.add(halfWidthProperty).subtract(handleRadius / 2));
			resizeHandleN.yProperty().bind(yProperty.subtract(handleRadius / 2));

			setUpDragging(resizeHandleN, mouseLocation, Cursor.N_RESIZE);

			resizeHandleN.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragNorth(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleN;
		}
	};

	DragNodeSupplier NORTH_EAST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();

			resizeHandleNE = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleNE.xProperty().bind(xProperty.add(widthProperty).subtract(handleRadius / 2));
			resizeHandleNE.yProperty().bind(yProperty.subtract(handleRadius / 2));

			setUpDragging(resizeHandleNE, mouseLocation, Cursor.NE_RESIZE);

			resizeHandleNE.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragNorth(event, mouseLocation, region, handleRadius);
					dragEast(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleNE;
		}
	};

	/**
	 * EAST
	 */
	DragNodeSupplier EAST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();
			final DoubleBinding halfHeightProperty = heightProperty.divide(2);

			resizeHandleE = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleE.xProperty().bind(xProperty.add(widthProperty).subtract(handleRadius / 2));
			resizeHandleE.yProperty().bind(yProperty.add(halfHeightProperty).subtract(handleRadius / 2));

			setUpDragging(resizeHandleE, mouseLocation, Cursor.E_RESIZE);

			resizeHandleE.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragEast(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleE;
		}
	};

	DragNodeSupplier SOUTH_EAST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

			resizeHandleSE = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleSE.xProperty().bind(xProperty.add(widthProperty).subtract(handleRadius / 2));
			resizeHandleSE.yProperty().bind(yProperty.add(heightProperty).subtract(handleRadius / 2));

			setUpDragging(resizeHandleSE, mouseLocation, Cursor.SE_RESIZE);

			resizeHandleSE.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragSouth(event, mouseLocation, region, handleRadius);
					dragEast(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleSE;
		}
	};

	DragNodeSupplier SOUTH = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty widthProperty = region.prefWidthProperty();
			final DoubleBinding halfWidthProperty = widthProperty.divide(2);
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

			resizeHandleS = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleS.xProperty().bind(xProperty.add(halfWidthProperty).subtract(handleRadius / 2));
			resizeHandleS.yProperty().bind(yProperty.add(heightProperty).subtract(handleRadius / 2));

			setUpDragging(resizeHandleS, mouseLocation, Cursor.S_RESIZE);

			resizeHandleS.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragSouth(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleS;
		}
	};

	DragNodeSupplier SOUTH_WEST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();

			resizeHandleSW = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleSW.xProperty().bind(xProperty.subtract(handleRadius / 2));
			resizeHandleSW.yProperty().bind(yProperty.add(heightProperty).subtract(handleRadius / 2));

			setUpDragging(resizeHandleSW, mouseLocation, Cursor.SW_RESIZE);

			resizeHandleSW.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragSouth(event, mouseLocation, region, handleRadius);
					dragWest(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleSW;
		}
	};

	DragNodeSupplier WEST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();
			final ReadOnlyDoubleProperty heightProperty = region.prefHeightProperty();
			final DoubleBinding halfHeightProperty = heightProperty.divide(2);

			resizeHandleW = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleW.xProperty().bind(xProperty.subtract(handleRadius / 2));
			resizeHandleW.yProperty().bind(yProperty.add(halfHeightProperty).subtract(handleRadius / 2));

			setUpDragging(resizeHandleW, mouseLocation, Cursor.W_RESIZE);

			resizeHandleW.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragWest(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleW;
		}
	};

	DragNodeSupplier NORTH_WEST = new DragNodeSupplier() {
		@Override
		public Node apply(Region region, Wrapper<Point2D> mouseLocation) {
			final DoubleProperty xProperty = region.layoutXProperty();
			final DoubleProperty yProperty = region.layoutYProperty();

			resizeHandleNW = new Rectangle(handleRadius, handleRadius, Color.BLACK);
			resizeHandleNW.xProperty().bind(xProperty.subtract(handleRadius / 2));
			resizeHandleNW.yProperty().bind(yProperty.subtract(handleRadius / 2));

			setUpDragging(resizeHandleNW, mouseLocation, Cursor.NW_RESIZE);

			resizeHandleNW.setOnMouseDragged(event -> {
				if (mouseLocation.value != null) {
					dragNorth(event, mouseLocation, region, handleRadius);
					dragWest(event, mouseLocation, region, handleRadius);
					mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
				}
			});
			return resizeHandleNW;
		}
	};

	public ResizableRectangleCell rezRectangle;

	public void makeResizable(Region region, ResizableRectangleCell rectangleCell) {
		this.rezRectangle = rectangleCell;
		makeResizable(region, this.NORTH, this.NORTH_EAST, this.EAST, this.SOUTH_EAST, this.SOUTH, this.SOUTH_WEST,
				this.WEST, this.NORTH_WEST);
	}

	public void makeResizable(Region region, DragNodeSupplier... nodeSuppliers) {
		final Wrapper<Point2D> mouseLocation = new Wrapper<>();
		final List<Node> dragNodes = Arrays.stream(nodeSuppliers).map(supplier -> supplier.apply(region, mouseLocation))
				.collect(Collectors.toList());
		region.parentProperty().addListener((obs, oldParent, newParent) -> {
			for (final Node c : dragNodes) {
				final Pane currentParent = (Pane) c.getParent();
				if (currentParent != null) {
					currentParent.getChildren().remove(c);
				}
				((Pane) newParent).getChildren().add(c);
				c.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						rezRectangle.cellGestures.setVisible();
					}
				});
				c.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						rezRectangle.cellGestures.setInvisible();
					}
				});
			}
		});
	}

	private void dragNorth(MouseEvent event, Wrapper<Point2D> mouseLocation, Region region, double handleRadius) {
		ResizableRectangleCell cell = rezRectangle;
		int counter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter++;
				}
			}
		}
		final double deltaY = event.getSceneY() - mouseLocation.value.getY();
		final double newY = region.getLayoutY() + deltaY;

		int counter2 = 0;

		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = newY;
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter2++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = newY;
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter2++;
				}
			}
		}

		if (counter > counter2) {
			return;
		}
		// When same group == same group
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (!iCell.equals(cell)) {
				if (iCell instanceof ResizableRectangleCell
						&& ((ResizableRectangleCell) iCell).style.ordinal() == cell.style.ordinal()) {
					ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell;
					Point point = new Point((int) cell.pane.getLayoutX(), (int) newY);
					Point point2 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(), newY,
							cell.pane.getWidth(), region.getPrefHeight() - deltaY);
					Point point3 = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
					Point point4 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(), cell2.pane.getLayoutY(),
							cell2.pane.getWidth(), cell2.pane.getHeight());
					if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
						return;
					}
				}
			}
		}

		if (newY != 0 && newY >= handleRadius && newY <= region.getLayoutY() + region.getHeight() - handleRadius) {
			region.setLayoutY(newY);
			region.setPrefHeight(region.getPrefHeight() - deltaY);
		}
		
		RectangleUtil.inconsistantBoardState2();


	}

	private void dragEast(MouseEvent event, Wrapper<Point2D> mouseLocation, Region region, double handleRadius) {
		ResizableRectangleCell cell = rezRectangle;
		int counter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter++;
				}
			}
		}
		final double deltaX = event.getSceneX() - mouseLocation.value.getX();
		final double newMaxX = region.getLayoutX() + region.getWidth() + deltaX;

		int counter2 = 0;

		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + region.getPrefWidth() + deltaX))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter2++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + region.getPrefWidth() + deltaX))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter2++;
				}
			}
		}

		if (counter > counter2) {
			return;
		}
		// When same group == same group
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (!iCell.equals(cell)) {
				if (iCell instanceof ResizableRectangleCell
						&& ((ResizableRectangleCell) iCell).style.ordinal() == cell.style.ordinal()) {
					ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell;
					Point point = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
					Point point2 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(), cell.pane.getLayoutY(),
							region.getPrefWidth() + deltaX, cell.pane.getHeight());
					Point point3 = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
					Point point4 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(), cell2.pane.getLayoutY(),
							cell2.pane.getWidth(), cell2.pane.getHeight());
					if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
						System.out.println(CellGestures.class);
						System.out.println(cell.getName());
						System.out.println(cell2.getName());
						return;
					}
				}
			}
		}


		if (newMaxX >= region.getLayoutX()
				&& newMaxX <= region.getParent().getBoundsInLocal().getWidth() - handleRadius) {
			region.setPrefWidth(region.getPrefWidth() + deltaX);
		}
		
		RectangleUtil.inconsistantBoardState2();

	}

	/**
	 * SOUTH
	 * 
	 * @param event
	 * @param mouseLocation
	 * @param region
	 * @param handleRadius
	 */
	private void dragSouth(MouseEvent event, Wrapper<Point2D> mouseLocation, Region region, double handleRadius) {

		ResizableRectangleCell cell = rezRectangle;
		int counter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter++;
				}
			}
		}
		final double deltaY = event.getSceneY() - mouseLocation.value.getY();
		final double newMaxY = region.getLayoutY() + region.getHeight() + deltaY;

		int counter2 = 0;

		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
							&& ((y + h) <= (y0 + region.getPrefHeight() + deltaY))) {
						counter2++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
						&& ((y + h) <= (y0 + region.getPrefHeight() + deltaY))) {
					counter2++;
				}
			}
		}

		if (counter > counter2) {
			return;
		}
		// When same group == same group
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (!iCell.equals(cell)) {
				if (iCell instanceof ResizableRectangleCell
						&& ((ResizableRectangleCell) iCell).style.ordinal() == cell.style.ordinal()) {
					ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell;
					Point point = new Point((int) cell.pane.getLayoutX(), (int) cell.pane.getLayoutY());
					Point point2 = RectangleUtil.getPointOfRechtangle(cell.pane.getLayoutX(), cell.pane.getLayoutY(),
							cell.pane.getWidth(), region.getPrefHeight() + deltaY);
					Point point3 = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
					Point point4 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(), cell2.pane.getLayoutY(),
							cell2.pane.getWidth(), cell2.pane.getHeight());
					if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
						return;
					}
				}
			}
		}

		double height = region.getPrefHeight();

		if (newMaxY >= region.getLayoutY()
				&& newMaxY <= region.getParent().getBoundsInLocal().getHeight() - handleRadius) {
			region.setPrefHeight(region.getPrefHeight() + deltaY);
		}
		
		RectangleUtil.inconsistantBoardState2();


	}
	
	// SOUTH

	/**
	 * WEST
	 * 
	 * @param event
	 * @param mouseLocation
	 * @param region
	 * @param handleRadius
	 */
	private void dragWest(MouseEvent event, Wrapper<Point2D> mouseLocation, Region region, double handleRadius) {

		ResizableRectangleCell cell = rezRectangle;

		int counter = 0;
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = rezRectangle.pane.getLayoutX();
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = rezRectangle.pane.getLayoutX();
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + rezRectangle.pane.getWidth()))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter++;
				}
			}
		}

		final double deltaX = event.getSceneX() - mouseLocation.value.getX();
		final double newX = region.getLayoutX() + deltaX;

		int counter2 = 0;

		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof ResizableRectangleCell) {
				if (!iCell.equals(rezRectangle)) {
					final double x0 = newX;
					final double y0 = rezRectangle.pane.getLayoutY();
					final double x = ((ResizableRectangleCell) iCell).pane.getLayoutX();
					final double y = ((ResizableRectangleCell) iCell).pane.getLayoutY();
					final double w = ((ResizableRectangleCell) iCell).pane.getPrefWidth();
					final double h = ((ResizableRectangleCell) iCell).pane.getPrefHeight();
					if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + (region.getPrefWidth() - deltaX)))
							&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
						counter2++;
					}
				}
			}
			if (iCell instanceof RectangleCell) {
				final double x0 = newX;
				final double y0 = rezRectangle.pane.getLayoutY();
				final double x = ((RectangleCell) iCell).pane.getLayoutX();
				final double y = ((RectangleCell) iCell).pane.getLayoutY();
				final double w = ((RectangleCell) iCell).pane.getPrefWidth();
				final double h = ((RectangleCell) iCell).pane.getPrefHeight();
				if ((x >= x0) && (y >= y0) && ((x + w) <= (x0 + (region.getPrefWidth() - deltaX)))
						&& ((y + h) <= (y0 + rezRectangle.pane.getHeight()))) {
					counter2++;
				}
			}
		}

		if (counter > counter2) {
			return;
		}

		// When same group == same group
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (!iCell.equals(cell)) {
				if (iCell instanceof ResizableRectangleCell
						&& ((ResizableRectangleCell) iCell).style.ordinal() == cell.style.ordinal()) {
					ResizableRectangleCell cell2 = (ResizableRectangleCell) iCell;
					Point point = new Point((int) newX, (int) cell.pane.getLayoutY());
					Point point2 = RectangleUtil.getPointOfRechtangle(newX, cell.pane.getLayoutY(),
							region.getPrefWidth() - deltaX, cell.pane.getHeight());
					Point point3 = new Point((int) cell2.pane.getLayoutX(), (int) cell2.pane.getLayoutY());
					Point point4 = RectangleUtil.getPointOfRechtangle(cell2.pane.getLayoutX(), cell2.pane.getLayoutY(),
							cell2.pane.getWidth(), cell2.pane.getHeight());
					if (RectangleUtil.doOverlap(point, point2, point3, point4)) {
						return;
					}
				}
			}
		}

		if (newX != 0 && newX <= region.getParent().getBoundsInLocal().getWidth() - handleRadius) {
			region.setLayoutX(newX);
			region.setPrefWidth(region.getPrefWidth() - deltaX);
		}
		
		RectangleUtil.inconsistantBoardState2();


	}

	private static void setUpDragging(Node node, Wrapper<Point2D> mouseLocation, Cursor hoverCursor) {
		node.setOnMouseEntered(event -> {
			node.getParent().setCursor(hoverCursor);
		});
		node.setOnMouseExited(event -> {
			node.getParent().setCursor(Cursor.DEFAULT);
		});
		node.setOnDragDetected(event -> {
			node.getParent().setCursor(Cursor.CLOSED_HAND);
			mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
		});
		node.setOnMouseReleased(event -> {
			node.getParent().setCursor(Cursor.DEFAULT);
			mouseLocation.value = null;
		});
	}

	static class Wrapper<T> {
		T value;
	}

	static interface DragNodeSupplier {

		public Node apply(Region region, Wrapper<Point2D> mouseLocation);

	}

}
