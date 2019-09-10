package tum.franca.graph.cells;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import tum.franca.graph.cells.ResizableRectangleCell.Mode;
import tum.franca.main.MainApp;

public class ResContxtMenu {

	private static ContextMenu contextMenu;

	public static ContextMenu getContextMenu(Pane pane, MouseEvent mouseEvent) {
		if (contextMenu == null) {
			contextMenu = new ContextMenu();

			ToggleGroup toggle = new ToggleGroup();
			RadioMenuItem item0 = new RadioMenuItem("Normal");
			RadioMenuItem item1 = new RadioMenuItem("Visibility");
			Image openIcon = new Image(ResContxtMenu.class.getResourceAsStream("/visibilty.png"));
			ImageView openView = new ImageView(openIcon);
			RadioMenuItem item2 = new RadioMenuItem("Deployment");
			Image openIcon2 = new Image(ResContxtMenu.class.getResourceAsStream("/deploy.png"));
			ImageView openView2 = new ImageView(openIcon2);
			toggle.getToggles().addAll(item0, item1, item2);
			item0.setSelected(true);

			openView.setFitWidth(15);
			openView.setFitHeight(15);
			openView2.setFitWidth(15);
			openView2.setFitHeight(15);
			item1.setGraphic(openView);
			item2.setGraphic(openView2);
			
			item0.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					for (ICell rezCell : MainApp.graph.getModel().getAddedCells()) {
						if (rezCell instanceof ResizableRectangleCell) {
							MainApp.graph.getNodeGestures().makeDraggable(MainApp.graph.getGraphic(rezCell),rezCell);
						}
					}
					ResizableRectangleCell.mode = Mode.NORMAL;
					item0.setSelected(true);
				}
			});

			item1.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					for (ICell rezCell : MainApp.graph.getModel().getAddedCells()) {
						if (rezCell instanceof ResizableRectangleCell) {
							MainApp.graph.getNodeGestures().makeUndraggable(MainApp.graph.getGraphic(rezCell));
						}
					}
						item1.setSelected(true);
						ResizableRectangleCell.mode = Mode.VISIBILITY;
				}
			});
			item2.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					for (ICell rezCell : MainApp.graph.getModel().getAddedCells()) {
						if (rezCell instanceof ResizableRectangleCell) {
							MainApp.graph.getNodeGestures().makeUndraggable(MainApp.graph.getGraphic(rezCell));
						}
					}
						item2.setSelected(true);
						ResizableRectangleCell.mode = Mode.DEPLOYMENT;
				}
			});
			contextMenu.getItems().addAll(item0, item1, item2);
		}
		contextMenu.show(pane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
		return contextMenu;
	}

}
