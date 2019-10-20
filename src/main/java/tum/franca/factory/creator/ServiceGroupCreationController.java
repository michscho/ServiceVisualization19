package tum.franca.factory.creator;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.main.MainApp;
import tum.franca.main.MainAppController;
import tum.franca.util.characteristics.CharacteristicsUtil;

/**
 * 
 * @author michaelschott
 *
 */
public class ServiceGroupCreationController {

	@FXML
	private TextField time;
	@FXML
	private ChoiceBox<String> groupLevel;
	@FXML
	private ChoiceBox<String> binding;
	@FXML
	private ChoiceBox<String> functional;
	@FXML
	private ChoiceBox<String> hardwareDependend;
	@FXML
	private ChoiceBox<String> runtime;
	@FXML
	private ChoiceBox<String> safety;
	@FXML
	private ChoiceBox<String> security;
	@FXML
	private ChoiceBox<String> timeUnit;

	private FilteredList<String> filteredList;
	private int group = 0;

	@FXML
	public void initialize() throws Exception {
		groupLevel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("Top Level Group")) {
					group = 0;
					filteredList = MainAppController.staticListWrapper.getListView1().getItems()
							.filtered(f -> !f.equals(""));
				}
				if (newValue.equals("Sub Level Group")) {
					group = 1;
					filteredList = MainAppController.staticListWrapper.getListView2().getItems()
							.filtered(f -> !f.equals(""));
				}
				if (newValue.equals("Low Level Group")) {
					group = 2;
					filteredList = MainAppController.staticListWrapper.getListView3().getItems()
							.filtered(f -> !f.equals(""));
				}
				time.setDisable(true);
				binding.setDisable(true);
				functional.setDisable(true);
				hardwareDependend.setDisable(true);
				runtime.setDisable(true);
				safety.setDisable(true);
				security.setDisable(true);
				timeUnit.setDisable(true);
				for (String string : filteredList) {
					if (string.equals("Binding")) {
						binding.setDisable(false);
					}
					if (string.equals("Functional Scope")) {
						functional.setDisable(false);
					}
					if (string.equals("Hardware Dependend")) {
						hardwareDependend.setDisable(false);
					}
					if (string.equals("Runtime")) {
						runtime.setDisable(false);
					}
					if (string.equals("Time Specification")) {
						safety.setDisable(false);
					}
					if (string.equals("Safety Critical")) {
						security.setDisable(false);
					}
					if (string.equals("Security Critical")) {
						timeUnit.setDisable(false);
						time.setDisable(false);
					}
				}

			}
		});
		initChoiceBoxes();

	}

	@FXML
	public void createServiceClicked() {
		StringBuilder sb = new StringBuilder("");
		StringBuilder groupS = new StringBuilder("");
		for (String s : filteredList) {
			if (s.equals("Binding")) {
				sb.append(binding.getValue());
				groupS.append(s);
			}
			if (s.equals("Functional Scope")) {
				sb.append(functional.getValue());
				groupS.append(s);
			}
			if (s.equals("Hardware Dependend")) {
				sb.append(hardwareDependend.getValue());
				groupS.append(s);
			}
			if (s.equals("Runtime")) {
				sb.append(runtime.getValue());
				groupS.append(s);
			}
			if (s.equals("Time Specification")) {
				sb.append(timeUnit.getValue());
				groupS.append(s);
			}
			if (s.equals("Safety Critical")) {
				sb.append(safety.getValue());
				groupS.append(s);
			}
			if (s.equals("Security Critical")) {
				sb.append(security.getValue());
				groupS.append(s);
			}
			sb.append(" ");
			groupS.append(" ");
		}
		ResizableRectangleCell cellGroup = null;
		if (group == 0) {
			  cellGroup = new ResizableRectangleCell(150, 150, sb.toString(),
					ResizableRectangleCell.GroupType.TOPLEVEL, groupS.toString());
		}
		if (group == 1) {
			 cellGroup = new ResizableRectangleCell(150, 150, sb.toString(),
					ResizableRectangleCell.GroupType.SUBLEVEL, groupS.toString());
		}
		if (group == 2) {
			cellGroup = new ResizableRectangleCell(150, 150, sb.toString(),
					ResizableRectangleCell.GroupType.SUBSUBLEVEL, groupS.toString());
		}
		
		MainApp.graph.addCell(cellGroup);
		MainApp.graph.getModel().addCell(cellGroup);
		overloadCounter = 0;
		relocate(cellGroup, 0);
		
	}
	
	private static int overloadCounter = 0;
	
	private void relocate(ResizableRectangleCell cellGroup, int y) {
		MainApp.graph.getGraphic(cellGroup).relocate(200, y);
		cellGroup.pane.toBack();
		if (cellGroup.containsResizableRectanlgeCells().size() > 0 || cellGroup.containsRectangleCell().size() > 0) {
			if (overloadCounter++ < 1000) {
			relocate(cellGroup, y + 100);
			}
		}
	}

	private void initChoiceBoxes() {
		groupLevel.setValue("Top Level Group");
		groupLevel.getItems().add("Top Level Group");

		if (MainAppController.staticListWrapper.getListView2().getItems().filtered(f -> !f.equals("")).size() > 0) {
			groupLevel.getItems().add("Sub Level Group");
		}
		if (MainAppController.staticListWrapper.getListView3().getItems().filtered(f -> !f.equals("")).size() > 0) {
			groupLevel.getItems().add("Low Level Group");
		}

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
