package tum.franca.util.propertyfunction;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Pair;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class PropertyFunctionWindowController {

	@FXML
	private Button addAttribute;
	@FXML
	private Accordion accordion;
	@FXML
	private ScrollPane scrollPane;

	public List<PropertyEntity> properties = new ArrayList<PropertyEntity>();

	public List<PropertyEntity> getProperties() {
		return properties;
	}

	@FXML
	public void onAddAttributeClicked() {
		scrollPane.setFitToHeight(true);
		accordion.getPanes().add(configureTitledPaneEmpty("property"));
	}

	public Pair<PropertyEntity, TitledPane> addTitledPane(String title) {
		PropertyEntity entity = new PropertyEntity(title);
		TitledPane pane = configureTitledPane(title, entity);
		accordion.getPanes().add(pane);
		return new Pair<PropertyEntity, TitledPane>(entity, pane);
	}

	public TitledPane configureTitledPane(String title, 
			PropertyEntity property) {

		properties.add(property);

		TitledPane tpane = new TitledPane();
		tpane.setAlignment(Pos.CENTER);

		HBox titledPaneHeader = new HBox();
		titledPaneHeader.setAlignment(Pos.CENTER);
		titledPaneHeader.setPadding(new Insets(0, 10, 0, 35));
		titledPaneHeader.minWidthProperty().bind(tpane.widthProperty());

		HBox region = new HBox();
		region.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(region, Priority.ALWAYS);

		Button delete = new Button("X");
		TextField textField = new TextField(title);
		textField.setPrefWidth(120);
		
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				property.setName(newValue);
			}
		});

		titledPaneHeader.getChildren().addAll(textField, region, delete);

		EventHandler<ActionEvent> onDeleteClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				accordion.getPanes().remove(tpane);
				properties.remove(property);
			}
		};

		delete.setOnAction(onDeleteClicked);

		tpane.setGraphic(titledPaneHeader);

		return tpane;
	}

	public TitledPane configureTitledPaneEmpty(String title) {

		PropertyEntity property = new PropertyEntity(title);
		properties.add(property);

		TitledPane tpane = new TitledPane();
		tpane.setAlignment(Pos.CENTER);

		HBox contentPane = new HBox();
		contentPane.setAlignment(Pos.CENTER);
		contentPane.setPadding(new Insets(0, 10, 0, 35));
		contentPane.minWidthProperty().bind(tpane.widthProperty());

		HBox region = new HBox();
		region.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(region, Priority.ALWAYS);

		Button delete = new Button("X");
		TextField textField = new TextField(title);
		textField.setPrefWidth(120);
		
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				property.setName(newValue);

			}
		});


		contentPane.getChildren().addAll(textField, region, delete);

		EventHandler<ActionEvent> onDeleteClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				accordion.getPanes().remove(tpane);
				properties.remove(property);
			}
		};

		delete.setOnAction(onDeleteClicked);

		tpane.setGraphic(contentPane);
		FlowPane pane = addContentHeader(property);
		tpane.setContent(pane);
		
		setEmptyContent(pane, property);

		return tpane;
	}

	public FlowPane addContentHeader(PropertyEntity property) {
		FlowPane contentHeader = new FlowPane();
		contentHeader.setVgap(4);
		contentHeader.setHgap(4);
		contentHeader.setAlignment(Pos.TOP_LEFT);

		String space = "                                     ";
		String space1 = "                                 ";
		String space2 = "     ";
		Text text1 = new Text("Key" + space);
		Text text2 = new Text("Value" + space);
		Text text3 = new Text("Group" + space1);
		Text text4 = new Text("Add" + space2);
		Text text5 = new Text("Remove");

		contentHeader.getChildren().addAll(text1, text2, text3, text4, text5);

		return contentHeader;
	}

	public void setEmptyContent(FlowPane contentPane, PropertyEntity entity) {
		setContent(contentPane, "", "", "", entity);
	}

	public void setContent(FlowPane contentHeader, String keyString, String valueString, String group,
			PropertyEntity property) {
		PropertyElement element = new PropertyElement(keyString, valueString, group);
		property.getElementList().add(element);
		String space1 = "         ";
		TextField key = new TextField(keyString);
		key.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				element.setKey(newValue);

			}
		});
		key.setPrefWidth(120);
		Text spaceText1 = new Text(space1);
		TextField value = new TextField(valueString + "");
		value.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				element.setValue(newValue);

			}
		});
		value.setPrefWidth(120);
		String space2 = "            ";
		Text spaceText2 = new Text(space2);
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setValue(group);
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				element.setGroup(newValue);

			}
		});
		if (MainApp.graph != null) {
			comboBox.getItems()
					.addAll(MainApp.graph.getModel().getAddedCells().stream()
							.filter(e -> e instanceof ResizableRectangleCell)
							.map(e -> ((ResizableRectangleCell) e).getName()).toArray(String[]::new));
		}
		comboBox.setPrefWidth(120);
		String space3 = "           ";
		Text spaceText3 = new Text(space3);
		Button add = new Button("+");
		EventHandler<ActionEvent> onAddButtonClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setContent(contentHeader, "", "", "", property);
			}
		};
		add.setOnAction(onAddButtonClicked);

		String space4 = "      ";
		Text spaceText4 = new Text(space4);
		Button remove = new Button("X");
		EventHandler<ActionEvent> onRemoveButtonClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				contentHeader.getChildren().removeAll(key, spaceText1, value, spaceText2, comboBox, spaceText3, add,
						spaceText4, remove);
				property.getElementList().remove(element);
			}
		};
		remove.setOnAction(onRemoveButtonClicked);
		contentHeader.getChildren().addAll(key, spaceText1, value, spaceText2, comboBox, spaceText3, add, spaceText4,
				remove);

	}

}
