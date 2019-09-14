package tum.franca.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;
import org.franca.core.franca.FrancaFactory;
import org.franca.core.franca.QOS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;

/**
 * 
 * @author michaelschott
 *
 */
public class QosWindowController {

	@FXML
	private Button addAttribute;
	@FXML
	private Accordion accordion;
	@FXML
	private ScrollPane scrollPane;

	@FXML
	public void onAddAttributeClicked() {
		scrollPane.setFitToHeight(true);
		accordion.getPanes().add(getTitledPane("attribute", "none"));
	}

	public TitledPane addTitledPane(String title, String extendsName) {
		TitledPane pane = getTitledPane(title, extendsName);
		accordion.getPanes().add(pane);
		return pane;
	}

	public List<ChoiceBox<String>> cbList = new ArrayList<>();
	public List<TextField> tfList = new ArrayList<>();
	public HashMap<ChoiceBox<String>, TextField> map = new HashMap<>();

	public TitledPane getTitledPane(String title, String extendsName) {

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
		ChoiceBox<String> choice = new ChoiceBox<String>();
		choice.setPrefWidth(160);
		choice.getItems().add(textField.getText());
		choice.getItems().add("none");
		choice.setValue(extendsName);

		tfList.add(textField);
		cbList.add(choice);
		map.put(choice, textField);

		updateReferences();

		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			for (ChoiceBox<String> choiceBox : cbList) {

				String tmpValue = choiceBox.getValue();

				choiceBox.getItems().clear();
				choiceBox.getItems().add("none");
				for (TextField t : tfList) {
					if (!map.get(choiceBox).equals(t)) {
						String s = t.getText();
						choiceBox.getItems().add(s);
					}
				}
				choiceBox.setValue(tmpValue);

				if (tmpValue.equals(oldValue)) {
					choiceBox.setValue(newValue);
				} else {
					choiceBox.setValue(tmpValue);
				}
			}
		});

		contentPane.getChildren().addAll(textField, new Text(" extends "), choice, region, delete);

		EventHandler<ActionEvent> onDeleteClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String s = textField.getText();
				System.out.println("textfield removed " + s);
				for (FidlReader fr : StaticFidlReader.getFidlList()) {
					
					for (int i = 0; i < fr.getFirstProvides().size(); i++) {
						if (fr.getFirstProvides().get(i).getQos() != null) {
							if (fr.getFirstProvides().get(i).getQos().getName().equals(s)) {
								FProvides provides = FrancaFactory.eINSTANCE.createFProvides();
								provides.setProvides(fr.getFirstProvides().get(i).getProvides());
								fr.getFirstProvides().remove(i);
								fr.getFirstProvides().add(provides);
								System.out.println("Removed " + fr.getFirstInterfaceName());
							}
						}
					}
					for (int i = 0; i < fr.getFirstRequires().size(); i++) {
						if (fr.getFirstRequires().get(i).getQos() != null) {
							System.out.println(fr.getFirstRequires().get(i).getQos().getName());
							System.out.println(fr.getFirstInterfaceName());
							if (fr.getFirstRequires().get(i).getQos().getName().equals(s)) {
								FRequires requires = FrancaFactory.eINSTANCE.createFRequires();
								requires.setRequires(fr.getFirstRequires().get(i).getRequires());
								fr.getFirstRequires().remove(i);
								fr.getFirstRequires().add(requires);
								System.out.println("Removed " + fr.getFirstInterfaceName());
								FrancaPersistenceManager fPM = new FrancaPersistenceManager();
								fPM.saveModel(fr.getFModel(), fr.getURI().toString());
							}
						}
					}
					
					
					QOS qosToRemove = null;
					for (QOS qos : fr.getFModel().getQos()) {
						if (qos.getName().equals(s)) {
							qosToRemove = qos;
						}
					}

					if (qosToRemove != null) {
						fr.getFModel().getQos().remove(qosToRemove);
						FrancaPersistenceManager fPM = new FrancaPersistenceManager();
						fPM.saveModel(fr.getFModel(), fr.getURI().toString());
					}
					
					FrancaPersistenceManager fPM = new FrancaPersistenceManager();
					fPM.saveModel(fr.getFModel(), fr.getURI().toString());
					
					
					

				}
				accordion.getPanes().remove(tpane);
				cbList.remove(choice);
				tfList.remove(textField);
				updateReferences();
			}
		};

		delete.setOnAction(onDeleteClicked);

		tpane.setGraphic(contentPane);
		tpane.setContent(addContentPane());

		return tpane;
	}

	public void updateReferences() {
		for (ChoiceBox<String> choiceBox : cbList) {

			String tmpValue = choiceBox.getValue();

			choiceBox.getItems().clear();
			choiceBox.getItems().add("none");
			for (TextField t : tfList) {
				if (!map.get(choiceBox).equals(t)) {
					String s = t.getText();
					choiceBox.getItems().add(s);
				}
			}
			choiceBox.setValue(tmpValue);
		}
	}

	public FlowPane addContentPane() {
		FlowPane contentPane2 = new FlowPane();
		contentPane2.setVgap(4);
		contentPane2.setHgap(4);
		contentPane2.setAlignment(Pos.TOP_LEFT);

		String space = "                                     ";
		String space2 = "     ";
		Text text3 = new Text("Key" + space);
		Text text4 = new Text("Value" + space);
		Text text5 = new Text("Unit" + space);
		Text text6 = new Text("Add" + space2);
		Text text7 = new Text("Remove");

		contentPane2.getChildren().addAll(text3, text4, text5, text6, text7);

		// setContent(contentPane2,"",0,"");

		return contentPane2;
	}

	public void setContent(FlowPane contentPane2, String keyString, int valueString, String unitString) {
		String space1 = "         ";
		TextField key = new TextField(keyString);
		key.setPrefWidth(120);
		Text spaceText1 = new Text(space1);
		TextField value = new TextField(valueString + "");
		value.setPrefWidth(120);
		String space2 = "            ";
		Text spaceText2 = new Text(space2);
		TextField unit = new TextField(unitString);
		unit.setPrefWidth(120);
		String space3 = "           ";
		Text spaceText3 = new Text(space3);
		Button add = new Button("+");
		EventHandler<ActionEvent> onAddButtonClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setContent(contentPane2, "", 0, "");
			}
		};
		add.setOnAction(onAddButtonClicked);

		String space4 = "      ";
		Text spaceText4 = new Text(space4);
		Button remove = new Button("X");
		EventHandler<ActionEvent> onRemoveButtonClicked = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				contentPane2.getChildren().removeAll(key, spaceText1, value, spaceText2, unit, spaceText3, add,
						spaceText4, remove);
			}
		};
		remove.setOnAction(onRemoveButtonClicked);
		contentPane2.getChildren().addAll(key, spaceText1, value, spaceText2, unit, spaceText3, add, spaceText4,
				remove);

	}

}
