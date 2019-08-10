package tum.franca.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.eclipse.emf.common.util.URI;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tum.franca.factory.GroupSetter;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;
import tum.franca.save.DataModel;
import tum.franca.tabs.TabPaneSetter;
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

	private TabPaneSetter tabPaneSetter;

	// ListView
	@FXML
	private ListView<String> listView;
	@FXML
	private ListView<String> listView2;
	@FXML
	private ListView<String> listView3;
	@FXML
	private ListView<String> listView4;

	private ListViewWrapper listViewWrapper;

	// TreeView
	@FXML
	private TreeView<String> treeView;
	public static TreeView<String> staticTreeView;

	// Buttons
	@FXML
	private Button groupingButton;
	@FXML
	private Button functionButton;

	// TODO Delete
	@FXML
	private RadioMenuItem fileChanges;
	public static RadioMenuItem staticFileChanges;

	// Metrics
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
	private Text couplingText;
	public static Text staticCouplingText;
	@FXML
	private Text cohesionText;
	public static Text staticCohesionText;
	@FXML
	private Text mostCommon;
	public static Text staticMostCommonText;
	@FXML
	private Text leastCommon;
	public static Text staticLeastCommonText;

	@FXML
	private Menu menuFile;
	@FXML
	private Menu menuSettings;
	@FXML
	private Menu menuHelp;

	/**
	 * Initalizes the controller and will be called at the beginning.
	 * 
	 * @throws Exception
	 */
	@FXML
	public void initialize() throws Exception {
		listViewWrapper = new ListViewWrapper(listView, listView2, listView3, listView4);
		listViewWrapper.createListViews();
		StaticSplitter.setStaticSplitPane(splitPane);
		fileChanges.setSelected(true);
		staticTreeView = treeView;
		staticZoomText = zoomText;
		staticServicesText = servicesText;
		staticGroupsText = groupsText;
		staticSubGroupsText = subGroupsText;
		staticSubSubGroupsText = subSubGroupsText;
		staticRelationsText = relationsText;
		staticFileChanges = fileChanges;
		staticCouplingText = couplingText;
		staticCohesionText = cohesionText;
		staticMostCommonText = mostCommon;
		staticLeastCommonText = leastCommon;
	}

	@FXML
	public void applyGrouping() {
		if (StaticFidlReader.getFidlList() != null) {
			GroupSetter gS = new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
			GroupSetter.createCanvas();
			tabPaneSetter.setCanvas();
			Metrics.setServicesAndGroups();
			Metrics.setRelations();
			TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
			treeView.createTree();
			StaticSplitter.setStaticSplitPane(splitPane);
		} else {
			VisualisationsAlerts.noFilesSelected();
		}
	}

	@FXML
	public void persistFidlClicked() {
		for (ICell iCell2 : MainApp.graph.getModel().getAddedCells()) {
			if (iCell2 instanceof ResizableRectangleCell) {
				String string1 = ((ResizableRectangleCell) iCell2).group;
				String string2 = ((ResizableRectangleCell) iCell2).getName();
				String[] stringArray1 = string1.split(" ");
				String[] stringArray2 = string2.split(" ");
				for (int i = 0; i < stringArray1.length; i++) {
					for (int j = 0; j < stringArray2.length; j++) {
						if (i == j) {
							for (RectangleCell cell : ((ResizableRectangleCell) iCell2).getRectangleCells()) {
								System.out.println(cell.name);
								cell.fidlReader.getPropertiesReader().setProperty(stringArray1[i], stringArray2[i]);
							}
						}
					}
				}
			}
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
		StaticSplitter.setStaticSplitPane(splitPane);
	}

	@FXML
	public void importFile() throws IOException {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Fidl Files", "*.fidl"));
		List<File> list = fileChooser.showOpenMultipleDialog(MainApp.primaryStage);
		if (list == null) {
			VisualisationsAlerts.noFilesSelected();
		} else {
			StaticFidlReader.newFidlList();
			for (File file : list) {
				URI uri = URI.createFileURI(file.getAbsolutePath());
				StaticFidlReader.getFidlList().add(new FidlReader(uri));
			}
			new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
			try {
				GroupSetter.createCanvas();
				if (tabPaneSetter == null) {
					this.tabPaneSetter = new TabPaneSetter();
				}
				tabPaneSetter.setCanvas();
				Metrics.setZoom(1.0);
				Metrics.setServicesAndGroups();
				Metrics.setRelations();
				Metrics.setCoupling();
				Metrics.setCohesion();
				Metrics.setMostCommon();
				TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
				treeView.createTree();
				groupingButton.setDisable(false);
				functionButton.setDisable(false);
				StaticSplitter.setStaticSplitPane(splitPane);
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

	@FXML
	public void saveAsPng() {
		SnapshotParameters param = new SnapshotParameters();
        param.setDepthBuffer(true);
        param.setTransform(Transform.scale(4, 4));
        WritableImage image = MainApp.graph.getCanvas().snapshot(param, null);

		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save png");
		fileChooser.setInitialFileName("snapshot.png");
		File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", savedFile);
		} catch (IOException e) {
		}
	}

}
