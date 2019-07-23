package tum.franca.graph.cells;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tum.franca.graph.graph.Graph;
import tum.franca.main.ContxtMenuCells;
import tum.franca.main.MainAppController;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;
import tum.franca.view.treeView.SimpleTreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class RectangleCell extends AbstractCell {

	public String name;
	public HashMap<Integer, Integer> groupNumbers;
	public boolean used;
	public StackPane pane;
	public Rectangle view;
	public transient FidlReader fidlReader;
	public Text text;

	public RectangleCell(String name, HashMap<Integer, Integer> groupNumbers, FidlReader fidlReader) {
		this.name = name;
		this.groupNumbers = groupNumbers;
		this.fidlReader = fidlReader;
	}

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

		pane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				if (MainAppController.staticFileChanges.isSelected()) {
				ContxtMenuCells.setContextMenu(pane, fidlReader);
				ContxtMenuCells.getContextMenu().show(pane, event.getSceneX(), event.getSceneY());
				}

			}
		});

		TextField textField = new TextField(name);

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
			}
		});

		pane.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (textField != null) {
					pane.getChildren().remove(textField);
				}
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

	public FidlReader getFidlReader() {
		return StaticFidlReader.getFidl(this);
	}

}
