package tum.franca.main;

import java.awt.Desktop;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.eclipse.emf.common.util.URI;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tum.franca.data.DataModel;
import tum.franca.factory.GroupSetter;
import tum.franca.factory.creator.ServiceCreation;
import tum.franca.factory.creator.ServiceGroupCreation;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.main.window.AboutWindow;
import tum.franca.util.ColorUtil;
import tum.franca.util.alerts.VisualisationsAlerts;
import tum.franca.util.reader.FidlReader;
import tum.franca.util.reader.StaticFidlReader;
import tum.franca.view.list.ListViewWrapper;
import tum.franca.view.metric.GeneralMetrics;
import tum.franca.view.tab.RenameableTab;
import tum.franca.view.tab.TabPaneSetter;
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

	public static TabPaneSetter tabPaneSetter;

	// ListView
	@FXML
	private ListView<String> listView;
	@FXML
	private ListView<String> listView2;
	@FXML
	private ListView<String> listView3;
	@FXML
	private ListView<String> listView4;

	public static ListViewWrapper staticListWrapper;
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

	// Metrics, General
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
	private Text avgService;
	public static Text staticAvgService;
	@FXML
	private Text avgCoupling;
	public static Text staticAvgCoupling;

	// Metrics, Group
	// General
	@FXML
	private Text servicesTextGroup;
	public static Text staticServicesTextGroup;
	@FXML
	private Text groupName;
	public static Text staticGroupName;
	@FXML
	private Text subGroupsTextGroup;
	public static Text staticSubGroupsTextGroup;
	@FXML
	private Text subSubGroupsTextGroup;
	public static Text staticSubSubGroupsTextGroup;

	// Properties
	@FXML
	private Text mostCommonGroup;
	public static Text staticMostCommonGroup;
	@FXML
	private Text leastCommonGroup;
	public static Text staticLeastCommonGroup;

	// Relation
	@FXML
	private Text relationsTextGroup;
	public static Text staticRelationsTextGroup;
	@FXML
	private Text couplingTextGroup;
	public static Text staticCouplingTextGroup;
	@FXML
	private Text cohesionTextGroup;
	public static Text staticCohesionTextGroup;

	// Special
	@FXML
	private Text propertyFunctionGroup;
	public static Text staticPropertyFunctionGroup;
	@FXML
	private Text coupCoheFactor;
	public static Text staticCoupCoheFactor;
	@FXML
	private Text infoText;
	public static Text staticInfoText;
	
	// Metrics, Service
	// General
	@FXML
	private Text servicesTextService;
	public static Text staticServicesTextService;
	@FXML
	private Text servicesTextServiceGroup;
	public static Text staticServicesTextServiceGroup;
	@FXML
	private Text servicesTextServiceSubGroup;
	public static Text staticServicesTextServiceSubGroup;
	@FXML
	private Text servicesTextServiceSubSubGroup;
	public static Text staticServicesTextServiceSubSubGroup;
	
	// Relations
	@FXML
	private Text relationsTextService;
	public static Text staticRelationsTextService;
	@FXML
	private Text couplingTextService;
	public static Text staticCouplingTextService;
	@FXML
	private Text cohesionTextService;
	public static Text staticCohesionTextService;

	// Menu
	@FXML
	private Menu menuFile;
	@FXML
	private Menu menuSettings;
	@FXML
	private Menu menuHelp;

	public MainAppController() {
	}

	/**
	 * Initalizes the controller and will be called at the beginning.
	 * 
	 * @throws Exception
	 */
	@FXML
	public void initialize() throws Exception {
		listViewWrapper = new ListViewWrapper(listView, listView2, listView3, listView4);
		listViewWrapper.createListViews();
		staticListWrapper = listViewWrapper;
		StaticSplitter.setStaticSplitPane(splitPane);
		staticTreeView = treeView;
		
		//**************

		// GENERAL METRICS
		// General
		staticServicesText = servicesText;
		staticGroupsText = groupsText;
		staticSubGroupsText = subGroupsText;
		staticSubSubGroupsText = subSubGroupsText;
		
		// Relation
		staticRelationsText = relationsText;
		staticCouplingText = couplingText;
		staticCohesionText = cohesionText;
		
		// Properties
		staticMostCommonText = mostCommon;
		staticLeastCommonText = leastCommon;
		
		// Special
		staticAvgCoupling = avgCoupling;
		staticAvgService = avgService;
		
		//**************

		// GROUP METRICS
		// General
		staticServicesTextGroup = servicesTextGroup;
		staticGroupName = groupName;
		staticSubGroupsTextGroup = subGroupsTextGroup;
		staticSubSubGroupsTextGroup = subSubGroupsTextGroup;

		// Properties
		staticMostCommonGroup = mostCommonGroup;
		staticLeastCommonGroup = leastCommonGroup;

		// Relation
		staticRelationsTextGroup = relationsTextGroup;
		staticCouplingTextGroup = couplingTextGroup;
		staticCohesionTextGroup = cohesionTextGroup;

		// Special
		staticPropertyFunctionGroup = propertyFunctionGroup;
		staticCoupCoheFactor = coupCoheFactor;
		staticInfoText = infoText;
		
		//**************
		
		// SERVICE METRICS
		// General
		staticServicesTextService = servicesTextService;
		staticServicesTextServiceGroup = servicesTextGroup;
		staticServicesTextServiceSubGroup = servicesTextServiceSubGroup;
		staticServicesTextServiceSubSubGroup = servicesTextServiceSubSubGroup;
	
		// Relation
		staticRelationsTextService = relationsTextService;
		staticCouplingTextService = couplingTextService;
		staticCohesionTextService = cohesionTextService;

	}

	@FXML
	public void applyGrouping() {
		if (StaticFidlReader.getFidlList() != null) {
			new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
			GroupSetter.createCanvas();
			tabPaneSetter.setCanvas();
			GeneralMetrics.setAll();
			ColorUtil.recolorCanvas();
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
							for (RectangleCell cell : ((ResizableRectangleCell) iCell2).containsRectangleCell()) {
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
				try {
					FidlReader fr = new FidlReader(uri);
					StaticFidlReader.getFidlList().add(fr);
				} catch (Exception e) {
					System.err.println(e.getStackTrace());
				}
			}
			new GroupSetter(StaticFidlReader.getFidlList(), listViewWrapper);
			try {
				GroupSetter.createCanvas();
				if (tabPaneSetter == null) {
					MainAppController.tabPaneSetter = new TabPaneSetter();
				}
				tabPaneSetter.setCanvas();
				ColorUtil.recolorCanvas();
				MainApp.graph.getCanvas().setScale(1.0);
				GeneralMetrics.setAll();
				TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
				treeView.createTree();
				groupingButton.setDisable(false);
				StaticSplitter.setStaticSplitPane(splitPane);
			} catch (NullPointerException e) {
				e.printStackTrace();
				VisualisationsAlerts.wrongGrouping();
			}
		}
	}

	@FXML
	public void quitClicked() {
		if (VisualisationsAlerts.saveDialog()) {
			Platform.exit();
			System.exit(0);
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
	public void saveAsPdf() throws DocumentException, MalformedURLException, IOException {
		SnapshotParameters param = new SnapshotParameters();
		param.setDepthBuffer(true);
		param.setTransform(Transform.scale(2, 2));
		WritableImage image = MainApp.root.snapshot(param, null);
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save pdf");
		String string = "default";
		Tab tab = TabPaneSetter.tabPane.getSelectionModel().getSelectedItem();
		if (tab instanceof RenameableTab) {
			string = ((RenameableTab) tab).name.get();
		}
		fileChooser.setInitialFileName(string + "-snapshot.pdf");
		File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);

		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);

		Image graph = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());

		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());

		float scaler = (float) (((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin())
				/ image.getWidth()) * 100);

		PdfWriter.getInstance(document, new FileOutputStream(savedFile));
		document.open();

		document.newPage();
		Paragraph p = new Paragraph();
		p.add(string + "-snapshot");
		p.setAlignment(Element.ALIGN_CENTER);

		document.add(p);
		Image image2 = Image.getInstance(graph);
		image2.scalePercent(scaler);
		// image2.scaleAbsolute(PageSize.A4);
		document.add(image2);
		document.close();

		Desktop.getDesktop().open(savedFile);
	}

	@FXML
	public void minClicked() {
		MainApp.primaryStage.setIconified(true);
	}

	@FXML
	public void saveAsPng() throws IOException {
		SnapshotParameters param = new SnapshotParameters();
		param.setDepthBuffer(true);
		param.setTransform(Transform.scale(2, 2));
		WritableImage image = MainApp.root.snapshot(param, null);
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save png");
		String string = "default";
		Tab tab = TabPaneSetter.tabPane.getSelectionModel().getSelectedItem();
		if (tab instanceof RenameableTab) {
			string = ((RenameableTab) tab).name.get();
		}
		fileChooser.setInitialFileName(string + "-snapshot.png");
		File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
		ImageIO.write(renderedImage, "png", savedFile);
		Desktop.getDesktop().open(savedFile);
	}
}
