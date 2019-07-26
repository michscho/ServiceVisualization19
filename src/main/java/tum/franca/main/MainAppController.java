package tum.franca.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import tum.franca.factory.GroupSetter;
import tum.franca.factory.TabPaneSetter;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;
import tum.franca.save.DataModel;
import tum.franca.view.listView.ListViewWrapper;
import tum.franca.view.treeView.TreeViewCreator;

/**
 * 
 * @author michaelschott
 *
 */
public class MainAppController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private SplitPane splitPane;

	@FXML
	private SplitPane splitPane2;

	@FXML
	private ListView<String> listView;
	@FXML
	private ListView<String> listView2;
	@FXML
	private ListView<String> listView3;
	@FXML
	private ListView<String> listView4;

	@FXML
	private Button groupingButton;
	@FXML
	private Button functionButton;

	@FXML
	private RadioMenuItem fileChanges;
	public static RadioMenuItem staticFileChanges;

	private ListViewWrapper listViewWrapper;
	
	private TabPaneSetter tabPaneSetter;
	
	@FXML
	private TreeView<String> treeView;
	public static TreeView<String> staticTreeView;
	
	@FXML
	private Text zoomText;
	public static Text staticZoomText;
	@FXML
	private Text servicesText;
	public static Text staticServicesText;
	@FXML
	private Text groupsText;
	public static Text staticGroupsText;
	@FXML
	private Text subGroupsText;
	public static Text staticSubGroupsText;
	@FXML
	private Text subSubGroupsText;
	public static Text staticSubSubGroupsText;
	@FXML
	private Text relationsText;
	public static Text staticRelationsText;
	@FXML
	private Text layoutXText;
	public static Text staticLayoutXText;
	@FXML
	private Text layoutYText;
	public static Text staticLayoutYText;
	

	@FXML
	public void initialize() throws Exception {
		listViewWrapper = new ListViewWrapper(listView, listView2, listView3, listView4);
		listViewWrapper.createListViews();
		splitPane.setDividerPosition(1, 0.78);
		fileChanges.setSelected(true);
		staticTreeView = treeView;
		staticZoomText = zoomText;
		staticServicesText = servicesText;
		staticGroupsText = groupsText;
		staticSubGroupsText = subGroupsText;
		staticSubSubGroupsText = subSubGroupsText;
		staticRelationsText = relationsText;
		staticLayoutXText = layoutXText;
		staticLayoutYText = layoutYText;
		staticFileChanges = fileChanges;
	}

	@FXML
	public void applyGrouping() {
		if (StaticFidlReader.getFidlList() != null) {
			GroupSetter gS = new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
			gS.createCanvas();
			tabPaneSetter.setCanvas();
			Metrics.setServicesAndGroups();
			Metrics.setRelations();
			TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
			treeView.createTree();
			splitPane.setDividerPosition(1, 0.82);
		} else {
			VisualisationsAlerts.noFilesSelected();
		}
	}

	@FXML
	public void makeNewGroup() {
		TextInputDialog dialog = new TextInputDialog("Group");
		dialog.setTitle("Rectangle Group Name");
		dialog.setHeaderText("How should the group be called?");
		dialog.setContentText("Please enter the name:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Your name: " + result.get());
			final ICell cellGroup = new ResizableRectangleCell(60, 120, result.get(),
					ResizableRectangleCell.FontStyle.BIG, "");
			MainApp.graph.addCell(cellGroup);
		}

	}
	
	@FXML
	public void save() {
		DataModel dataModel = new DataModel();
		dataModel.save();
	}

	@FXML
	public void importSavedFile() {
		DataModel dataModel = new DataModel();
		dataModel.deserialize();
		dataModel.importSavedFile(tabPaneSetter);
		splitPane.setDividerPosition(1, 0.82);
	}
	
	@FXML
	public void importFile() throws IOException {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Fidl Files","*.fidl"));
		List<File> list = fileChooser.showOpenMultipleDialog(MainApp.primaryStage);
		if (list == null) {
			VisualisationsAlerts.noFilesSelected();
		} else {
			StaticFidlReader.newFidlList();
			for (File file : list) {
				URI uri = URI.createFileURI(file.getAbsolutePath());
				StaticFidlReader.getFidlList().add(new FidlReader(uri));
			}
			GroupSetter gS = new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
			try {
				gS.createCanvas();
				if (tabPaneSetter == null) {					
					this.tabPaneSetter = new TabPaneSetter();
				}
				tabPaneSetter.setCanvas();
				Metrics.setZoom(1.0);
				Metrics.setServicesAndGroups();
				Metrics.setRelations();
				TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
				treeView.createTree();
				groupingButton.setDisable(false);
				functionButton.setDisable(false);
				splitPane.setDividerPosition(1, 0.82);
			} catch (NullPointerException e) {
				VisualisationsAlerts.wrongGrouping();
			}
		}
	}

	@FXML
	public void timeSpecification() throws InterruptedException {
		TextInputDialog dialog = new TextInputDialog(GroupSetter.interval + "");
		dialog.setTitle("Time Specification Settings");
		dialog.setHeaderText("Set the interval for grouping the time specification.");
		dialog.setContentText("Set the interval in ns:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				GroupSetter.interval = Integer.valueOf(result.get());
			} catch (Exception e) {
				VisualisationsAlerts.noValidInteger(result.get());
				timeSpecification();
			}
		}
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}

	@FXML
	public void about() {
		AboutWindow window = new AboutWindow();
		window.showAboutWindow();
	}

}
