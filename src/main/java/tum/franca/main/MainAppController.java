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
import tum.franca.creator.ServiceCreation;
import tum.franca.creator.ServiceGroupCreation;
import tum.franca.factory.GroupSetter;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.cells.RectangleUtil;
import tum.franca.graph.cells.ResizableRectangleCell;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;
import tum.franca.save.DataModel;
import tum.franca.tabs.RenameableTab;
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
	@FXML
	private Button newService;
	@FXML
	private Button serviceGroup;

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
			Metrics.setAll();
			RectangleUtil.recolorCanvas();
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
	public void clickedOnNewService(){
		ServiceCreation.initServiceCreation();
	}
	
	@FXML
	public void clickedOnNewServiceGroup(){
		ServiceGroupCreation.initServiceGroupCreation();
		
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
			RectangleUtil.recolorCanvas();
			
			MainApp.graph.getCanvas().setScale(1.0);
			Metrics.setZoom(1.0);
			Metrics.setAll();
			TreeViewCreator treeView = new TreeViewCreator(StaticFidlReader.getFidlList());
			treeView.createTree();
			groupingButton.setDisable(false);
			newService.setDisable(false);
			serviceGroup.setDisable(false);
			StaticSplitter.setStaticSplitPane(splitPane);
			} catch (NullPointerException e) {
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
			RenameableTab renTab = (RenameableTab) tab;
			string = ((RenameableTab) tab).name.get();
		}
		fileChooser.setInitialFileName(string + "-snapshot.pdf");
		File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);

		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);

		Image graph = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());

		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		 
		float scaler = (float) (((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()) / image.getWidth()) * 100);

	
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
			RenameableTab renTab = (RenameableTab) tab;
			string = ((RenameableTab) tab).name.get();
		}
		fileChooser.setInitialFileName(string + "-snapshot.png");
		File savedFile = fileChooser.showSaveDialog(MainApp.primaryStage);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
		ImageIO.write(renderedImage, "png", savedFile);
		Desktop.getDesktop().open(savedFile);
	}
}
