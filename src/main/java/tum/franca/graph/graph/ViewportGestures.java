package tum.franca.graph.graph;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import tum.franca.factory.creator.ServiceCreation;
import tum.franca.factory.creator.ServiceGroupCreation;
import tum.franca.main.MainApp;
import tum.franca.main.window.ColorPickerWindowCanvas;

/**
 * https://github.com/generalic/GraphVisualisation/blob/master/src/hr/fer/zemris/graph/test/GraphPane.java
 * Listeners for making the scene's viewport draggable and zoomable
 */
public class ViewportGestures {

	private final DoubleProperty zoomSpeedProperty = new SimpleDoubleProperty(1.2d);
	private final DoubleProperty maxScaleProperty = new SimpleDoubleProperty(10.0d);
	private final DoubleProperty minScaleProperty = new SimpleDoubleProperty(0.1d);

	public final PannableCanvas.DragContext sceneDragContext = new PannableCanvas.DragContext();

	PannableCanvas canvas;

	public ViewportGestures(PannableCanvas canvas) {
		this.canvas = canvas;
	}

	public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
		return onMousePressedEventHandler;
	}

	public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
		return onMouseDraggedEventHandler;
	}

	public EventHandler<ScrollEvent> getOnScrollEventHandler() {
		return onScrollEventHandler;
	}

	public void setZoomBounds(double minScale, double maxScale) {
		minScaleProperty.set(minScale);
		maxScaleProperty.set(maxScale);
	}
	
	private final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			
			final Node node = (Node) event.getSource();

			final double scale = MainApp.graph.getScale();

//			double X = event.getScreenX() + node.getB - event.getScreenX();
//			double Y = event.getScreenY() + node.getLocalToParentTransform(). * scale - event.getScreenY();
//
//			X /= scale;
//			Y /= scale;
			
			System.out.println(event.getSceneX());
			System.out.println(event.getSceneY());
			
			System.out.println(canvas.getTranslateX());
			System.out.println(canvas.getTranslateY());
			
			System.out.println("Scale " + scale);
			
			System.out.println("CALC: " + ((canvas.getTranslateX()/-1)/scale));
			
			
			if (menu != null) {
				menu.hide();
			}

			if (!event.isPrimaryButtonDown()) {
				if (menu == null) {
					menu = new ContextMenu();
				} else {
					menu.hide();
					menu = new ContextMenu();
				}
				menu.setAutoHide(true);
				MenuItem item = new MenuItem("New Service");
				EventHandler<ActionEvent> onAddServiceClicked = new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						ServiceCreation.initServiceCreationWithLocation((int) ((canvas.getTranslateX()*-1)*scale + (event.getSceneX()-284)*scale),(int) ((canvas.getTranslateY()*-1)/scale + (event.getSceneY()-66)/scale));
						menu.hide();
					}
				};
				EventHandler<ActionEvent> onAddServiceGroupClicked = new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						ServiceGroupCreation.initServiceGroupCreationWithLocation(
								(int) event.getSceneX(),(int) event.getSceneY());
						menu.hide();
					}
				};
				item.setOnAction(onAddServiceClicked);
				MenuItem item2 = new MenuItem("New Service Group");
				item2.setOnAction(onAddServiceGroupClicked);
				SeparatorMenuItem sepMenuItem = new SeparatorMenuItem();
				MenuItem item3 = new MenuItem("Change Background");
				EventHandler<ActionEvent> onChangeBackgroundlicked = new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						ColorPickerWindowCanvas.initColorPicker((int) event.getSceneX(),(int) event.getSceneY());
						menu.hide();
					}
				};
				item3.setOnAction(onChangeBackgroundlicked);
				menu.getItems().addAll(item, item2, sepMenuItem, item3);
				menu.show(canvas, event.getSceneX(), event.getScreenY());
				menu.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
					menu.hide();
					e.consume();
				});
				return;
			}

			sceneDragContext.mouseAnchorX = event.getSceneX();
			sceneDragContext.mouseAnchorY = event.getSceneY();

			sceneDragContext.translateAnchorX = canvas.getTranslateX();
			sceneDragContext.translateAnchorY = canvas.getTranslateY();

			event.consume();

		}

	};

	private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {

			if (menu != null) {
				menu.hide();
			}
			
			canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
			canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);

			event.consume();
		}
	};

	ContextMenu menu = null;

	/**
	 * Mouse wheel handler: zoom to pivot point
	 */
	private final EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

		@Override
		public void handle(ScrollEvent event) {
			if (menu != null) {
				menu.hide();
			}
			
			double scale = canvas.getScale(); // currently we only use Y, same value is used for X
			final double oldScale = scale;

			if (event.getDeltaY() < 0) {
				scale /= 1.02;
			} else {
				scale *= 1.02;
			}

			scale = clamp(scale, minScaleProperty.get(), maxScaleProperty.get());
			final double f = (scale / oldScale) - 1;

			// maxX = right overhang, maxY = lower overhang
			final double maxX = canvas.getBoundsInParent().getMaxX()
					- canvas.localToParent(canvas.getPrefWidth(), canvas.getPrefHeight()).getX();
			final double maxY = canvas.getBoundsInParent().getMaxY()
					- canvas.localToParent(canvas.getPrefWidth(), canvas.getPrefHeight()).getY();

			// minX = left overhang, minY = upper overhang
			final double minX = canvas.localToParent(0, 0).getX() - canvas.getBoundsInParent().getMinX();
			final double minY = canvas.localToParent(0, 0).getY() - canvas.getBoundsInParent().getMinY();

			// adding the overhangs together, as we only consider the width of canvas itself
			final double subX = maxX + minX;
			final double subY = maxY + minY;

			// subtracting the overall overhang from the width and only the left and upper
			// overhang from the upper left point
			final double dx = (event.getSceneX() - ((canvas.getBoundsInParent().getWidth() - subX) / 2
					+ (canvas.getBoundsInParent().getMinX() + minX)));
			final double dy = (event.getSceneY() - ((canvas.getBoundsInParent().getHeight() - subY) / 2
					+ (canvas.getBoundsInParent().getMinY() + minY)));

			canvas.setScale(scale);

			// note: pivot value must be untransformed, i. e. without scaling
			canvas.setPivot(f * dx, f * dy);

			event.consume();

		}

	};

	public final EventHandler<ZoomEvent> onZoomEventHandler = new EventHandler<ZoomEvent>() {

		@Override
		public void handle(ZoomEvent event) {
			if (menu != null) {
				menu.hide();
			}
			double scale = canvas.getScale(); // currently we only use Y, same value is used for X
			final double oldScale = scale;

			if (event.getZoomFactor() > 1) {
				scale *= 1.010;
			} else {
				scale /= 1.010;
			}

			scale = clamp(scale, minScaleProperty.get(), maxScaleProperty.get());
			final double f = (scale / oldScale) - 1;

			// maxX = right overhang, maxY = lower overhang
			final double maxX = canvas.getBoundsInParent().getMaxX()
					- canvas.localToParent(canvas.getPrefWidth(), canvas.getPrefHeight()).getX();
			final double maxY = canvas.getBoundsInParent().getMaxY()
					- canvas.localToParent(canvas.getPrefWidth(), canvas.getPrefHeight()).getY();

			// minX = left overhang, minY = upper overhang
			final double minX = canvas.localToParent(0, 0).getX() - canvas.getBoundsInParent().getMinX();
			final double minY = canvas.localToParent(0, 0).getY() - canvas.getBoundsInParent().getMinY();

			// adding the overhangs together, as we only consider the width of canvas itself
			final double subX = maxX + minX;
			final double subY = maxY + minY;

			// subtracting the overall overhang from the width and only the left and upper
			// overhang from the upper left point
			final double dx = (event.getSceneX() - ((canvas.getBoundsInParent().getWidth() - subX) / 2
					+ (canvas.getBoundsInParent().getMinX() + minX)));
			final double dy = (event.getSceneY() - ((canvas.getBoundsInParent().getHeight() - subY) / 2
					+ (canvas.getBoundsInParent().getMinY() + minY)));

			canvas.setScale(scale);

			// note: pivot value must be untransformed, i. e. without scaling
			canvas.setPivot(f * dx, f * dy);

			event.consume();

		}

	};

	public static double clamp(double value, double min, double max) {
		if (Double.compare(value, min) < 0) {
			return min;
		}

		if (Double.compare(value, max) > 0) {
			return max;
		}

		return value;
	}

	public double getMinScale() {
		return minScaleProperty.get();
	}

	public void setMinScale(double minScale) {
		minScaleProperty.set(minScale);
	}

	public DoubleProperty minScaleProperty() {
		return minScaleProperty;
	}

	public double getMaxScale() {
		return maxScaleProperty.get();
	}

	public DoubleProperty maxScaleProperty() {
		return maxScaleProperty;
	}

	public void setMaxScale(double maxScale) {
		maxScaleProperty.set(maxScale);
	}

	public double getZoomSpeed() {
		return zoomSpeedProperty.get();
	}

	public DoubleProperty zoomSpeedProperty() {
		return zoomSpeedProperty;
	}

	public void setZoomSpeed(double zoomSpeed) {
		zoomSpeedProperty.set(zoomSpeed);
	}

}