package tum.franca.factory.creator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.main.MainApp;
import tum.franca.util.alerts.VisualisationsAlerts;
import tum.franca.util.characteristics.CharacteristicsUtil;
import tum.franca.util.reader.FidlReader;
import tum.franca.util.reader.StaticFidlReader;

/**
 * 
 * @author michaelschott
 *
 */
public class ServiceCreationController {

	@FXML
	TextField serviceName;
	@FXML
	TextField time;
	@FXML
	ChoiceBox<String> binding;
	@FXML
	ChoiceBox<String> functional;
	@FXML
	ChoiceBox<String> hardwareDependend;
	@FXML
	ChoiceBox<String> runtime;
	@FXML
	ChoiceBox<String> safety;
	@FXML
	ChoiceBox<String> security;
	@FXML
	ChoiceBox<String> timeUnit;

	@FXML
	public void initialize() throws Exception {
		initChoiceBoxes();
	}

	@FXML
	public void createServiceClicked() throws IOException {
		if (!checkInput()) {
		} else {
			final FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Service");
			fileChooser.setInitialFileName(serviceName.getText() + ".fidl");
			File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);
			FileWriter fileWriter = null;
		    try {
		    	fileWriter = new FileWriter(savedFile);
	            fileWriter.write("package src interface " + serviceName.getText() + " {     }");
	            fileWriter.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			createFidlFile(savedFile);
		}
	}
	
	private static int counter = 0;

	@FXML
	public void createSimpleServiceClicked() {
		if (serviceName.getText().equals("")) {
			serviceName.setText("easyService" + counter++);
		}
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Service");
		fileChooser.setInitialFileName(serviceName.getText() + ".fidl");
		File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);
		FileWriter fileWriter = null;
	    try {
	    	fileWriter = new FileWriter(savedFile);
            fileWriter.write("package src interface " + serviceName.getText() + " {     }");
            fileWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		URI uri = URI.createFileURI(savedFile.getAbsolutePath());
		FidlReader fr = new FidlReader(uri);
		StaticFidlReader.getFidlList().add(fr);
		addService(fr);
	}
	
	private void createFidlFile(File file) {
		try {
		URI uri = URI.createFileURI(file.getAbsolutePath());
		FidlReader fr = new FidlReader(uri);
		StaticFidlReader.getFidlList().add(fr);
		fr.getPropertiesReader().setProperty("binding", binding.getValue());
		fr.getPropertiesReader().setProperty("hardwaredependend", hardwareDependend.getValue());
		fr.getPropertiesReader().setProperty("functionalscope", functional.getValue());
		fr.getPropertiesReader().setProperty("runtime", runtime.getValue());
		fr.getPropertiesReader().setProperty("safetyCritical", safety.getValue());
		fr.getPropertiesReader().setProperty("securityCritical", security.getValue());
		fr.getPropertiesReader().setProperty("timeSpecification", timeUnit.getValue());
		addService(fr);
		} catch (Exception e) {
			VisualisationsAlerts.stackTraceAlert(e);
		}
	}
	
	private void addService(FidlReader fr) {
		final RectangleCell cell = new RectangleCell(serviceName.getText(), null, fr);
		MainApp.graph.getModel().getAddedCells().add(cell);
		MainApp.graph.addCell(cell);
	}

	private boolean checkInput() {
		try {
			Integer.valueOf(time.getText());
		} catch (Exception e) {
			VisualisationsAlerts.noValidInteger(time.getText());
			time.setText("");
			return false;
		}
		if (serviceName.getText().equals("") | Character.isDigit(serviceName.getText().charAt(0))) {
			VisualisationsAlerts.noValidServiceName(serviceName.getText());
			return false;
		} else {
		return true;
		}
	}

	private void initChoiceBoxes() {
		List<List<String>> list = CharacteristicsUtil.getAllCharacteristicsAsEnums();

		for (int i = 0; i < list.size(); i++) {
			List<String> innerList = list.get(i);
			for (int j = 0; j < innerList.size(); j++) {
				if (j == 0) {
				} else {
					if (i == 0) {
						binding.setValue("notDefined");
						binding.getItems().add(innerList.get(j));
					}
					if (i == 1) {
						functional.setValue("notDefined");
						functional.getItems().add(innerList.get(j));
					}
					if (i == 2) {
						hardwareDependend.setValue("notDefined");
						hardwareDependend.getItems().add(innerList.get(j));
					}
					if (i == 3) {
						runtime.setValue("notDefined");
						runtime.getItems().add(innerList.get(j));
					}
					if (i == 4) {
						security.setValue("notDefined");
						security.getItems().add(innerList.get(j));
					}
					if (i == 5) {
						safety.setValue("notDefined");
						safety.getItems().add(innerList.get(j));
					}
					if (i == 6) {
						timeUnit.setValue("notDefined");
						timeUnit.getItems().add(innerList.get(j));
					}

				}
			}
		}
	}

}
