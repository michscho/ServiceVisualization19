package tum.franca.view.tab;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;
import org.franca.core.franca.FrancaFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tum.franca.data.RedoManager;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;
import tum.franca.graph.edges.IEdge;
import tum.franca.main.MainApp;
import tum.franca.util.reader.FidlReader;
import tum.franca.util.reader.StaticFidlReader;
import tum.franca.view.metric.Metrics;

/**
 * 
 * @author michaelschott
 *
 */
public class TabPaneSetter {

	private Button createSaveButton() {
		Button button = new Button();
		Image gridIcon = new Image(TabPaneSetter.class.getResourceAsStream("/save.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button.setGraphic(imageView);
		button.setOnAction(onSaveClicked);
		return button;
	}

	private static Button createUndoButton() {
		Button button = new Button();
		Image gridIcon = new Image(TabPaneSetter.class.getResourceAsStream("/undo.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button.setGraphic(imageView);
		button.setOnAction(onUndoClicked);
		return button;
	}

	private static Button createRedoButton() {
		Button button = new Button();
		Image gridIcon = new Image(TabPaneSetter.class.getResourceAsStream("/redo.png"));
		ImageView imageView = new ImageView(gridIcon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button.setGraphic(imageView);
		button.setOnAction(onRedoClicked);
		return button;
	}

	static EventHandler<ActionEvent> onUndoClicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			RedoManager.undo();
		}

	};

	static EventHandler<ActionEvent> onRedoClicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			RedoManager.redo();
		}

	};
	
	private void saveCharacteristcs() {
		System.out.println("*********Saving Characteristics*********");
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
								cell.fidlReader.getPropertiesReader().setProperty(stringArray1[i], stringArray2[i]);
							}
						}
					}
				}
			}
		}
		
	}
	
	private void saveAddedEdges() {
		// Adding Edges
					System.out.println("**************************");
					for (IEdge edges : MainApp.graph.getModel().getAddedEdges()) {
						
						
						ICell sourceCell = edges.getSource();
						ICell targetCell = edges.getTarget();
						
						System.out.println("SourceCell Edge considered: " +  ((RectangleCell) sourceCell).getName());
						System.out.println("TargetCell Edge considered: " +  ((RectangleCell) targetCell).getName());

						
						if (sourceCell instanceof RectangleCell) {

							System.out.println("Rectanglename same as source Cell: " + ((RectangleCell) sourceCell).getName());

							boolean match = false;
							for (FidlReader fr : StaticFidlReader.getFidlList()) {
								EList<FProvides> providesList = fr.getFirstProvides();
								EList<FRequires> requiresList = fr.getFirstRequires();
								for (FProvides provides : providesList) {
									System.out.println("Provides " + provides.getProvides());
									if (((RectangleCell) targetCell).getName().equals(provides.getProvides()) && ((RectangleCell) sourceCell).getName().equals(fr.getFirstInterfaceName())) {
										System.out.println("Match true");
										match = true;
									}
								}
								for (FRequires requires : requiresList) {
									System.out.println("Requires " + requires.getRequires());
									if (((RectangleCell) targetCell).getName().equals(requires.getRequires()) && ((RectangleCell) sourceCell).getName().equals(fr.getFirstInterfaceName())) {
										System.out.println("Match true");
										match = true;
									}
								}

							}
							if (!match) {
								System.out.println("**********");
								System.out.println(((RectangleCell) sourceCell).getName());
								System.out.println(((RectangleCell) targetCell).getName());
								// Create requires
								FRequires requires = FrancaFactory.eINSTANCE.createFRequires();
								requires.setRequires(((RectangleCell) targetCell).getFidlReader().getFirstInterfaceName());
								
		//
//								// Create import
//								String[] uri = ((RectangleCell) targetCell).getFidlReader().getURI().toString().split("/");
//								String lastUriElement = ((RectangleCell) targetCell).getFidlReader().getURI().toString()
//										.split("/")[uri.length - 1];
//								boolean importUriMatch = false;
//								for (Import i : ((RectangleCell) sourceCell).getFidlReader().getImports()) {
//									if (i.getImportURI().equals(lastUriElement)) {
//										importUriMatch = true;
//									}
//								}
//								if (!importUriMatch) {
//									Import fImport = FrancaFactory.eINSTANCE.createImport();
//									fImport.setImportedNamespace(((RectangleCell) targetCell).getFidlReader().getName() + ".*");
//									fImport.setImportURI(lastUriElement);
		//
//									// Adding
//									((RectangleCell) sourceCell).getFidlReader().getImports().add(fImport);
//								
//								}
								
								((RectangleCell) sourceCell).getFidlReader().getFirstRequires().add(requires);
								
								// Saving
								FrancaPersistenceManager fPM = new FrancaPersistenceManager();
								fPM.saveModel(((RectangleCell) sourceCell).getFidlReader().getFModel(),
										((RectangleCell) sourceCell).getFidlReader().getURI().toString());
							}
						}

					}
	}
	
	private void saveRemovedEdges() {
		// Removing Edges
		for (FidlReader fr : StaticFidlReader.getFidlList()) {
			EList<FProvides> providesList = fr.getFirstProvides();
			EList<FRequires> requiresList = fr.getFirstRequires();

			EList<FProvides> providesToRemove = new BasicEList<>();
			EList<FRequires> requiresToRemove = new BasicEList<>();

			// PROVIDES
			for (FProvides provides : providesList) {
				boolean exists = false;
				for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
					RectangleCell sourceCell = (RectangleCell) edge.getSource();
					RectangleCell targetCell = (RectangleCell) edge.getTarget();
					if (provides.getProvides().equals(targetCell.getName()) && fr.getFirstInterfaceName().equals(sourceCell.getName())) {
						exists = true;
					}

				}
				if (!exists) {
					providesToRemove.add(provides);
					System.out.println(provides.getProvides());
				}

			}
			fr.getInterfaces().get(0).getProvided().removeAll(providesToRemove);

			// REQUIRES
			for (FRequires requires : requiresList) {
				boolean exists = false;
				for (IEdge edge : MainApp.graph.getModel().getAddedEdges()) {
					RectangleCell sourceCell = (RectangleCell) edge.getSource();
					RectangleCell targetCell = (RectangleCell) edge.getTarget();
					if (requires.getRequires().equals(targetCell.getName()) && fr.getFirstInterfaceName().equals(sourceCell.getName())) {
						exists = true;
					}

				}
				if (!exists) {
					requiresToRemove.add(requires);
					System.out.println(requires.getRequires());
				}

			}
			
			fr.getFirstRequires().removeAll(requiresToRemove);
			fr.getFirstProvides().removeAll(providesToRemove);
			
			FrancaPersistenceManager frManager = new FrancaPersistenceManager();

			
			System.out.println(fr.getURI().toString());
			frManager.saveModel(fr.getFModel(), fr.getURI().toString());				
			
		}
	}

	EventHandler<ActionEvent> onSaveClicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			saveCharacteristcs();
			saveAddedEdges();
			saveRemovedEdges();

		}

	};

	public static TabPane tabPane;
	public static Button undo;
	public static Button redo;

	/**
	 * Automatic creation of new Tabs. Automatic change of graph object.
	 */
	public void setCanvas() {

		// Setting menu at the top
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(MenuBarTop.getMenuBar());

		if (tabPane == null || tabPane.getTabs().size() == 0) {

			// HBox of control buttons
			HBox hbox = new HBox();
			undo = createUndoButton();
			redo = createRedoButton();
			undo.setDisable(true);
			redo.setDisable(true);
			hbox.getChildren().add(undo);
			hbox.getChildren().add(redo);
			hbox.getChildren().add(createSaveButton());
			
			// Default Tabs
			TabPaneSetter.tabPane = new TabPane();
			StringProperty sP = new SimpleStringProperty("Current Group");
			Tab tab1 = new RenameableTab(sP);
			StringProperty sP2 = new SimpleStringProperty("New Group");
			Tab tab2 = new RenameableTab(sP2);

			// Three Tabs at the bottom
			TabPane innerTabPane = InnerTabPane.getInnerTabPane();
			InnerTabPane.setContentMain(MainApp.graph.getCanvas());
			tab1.setContent(innerTabPane);

			MainApp.graphList[0] = MainApp.graph;
			tabPane.getSelectionModel().select(0);
			tabPane.getTabs().addAll(tab1, tab2);

			// If clicked on another tabPane
			tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
				if (MainApp.graphList[newTab.getTabPane().getSelectionModel().getSelectedIndex()] != null) {
					MainApp.graph = MainApp.graphList[newTab.getTabPane().getSelectionModel().getSelectedIndex()];
					Metrics.setAll();
				}
			});

			// Anchor the controls
			AnchorPane anchor = new AnchorPane();
			anchor.getChildren().addAll(tabPane, hbox);
			AnchorPane.setTopAnchor(hbox, 3.0);
			AnchorPane.setRightAnchor(hbox, 5.0);
			AnchorPane.setTopAnchor(tabPane, 1.0);
			AnchorPane.setRightAnchor(tabPane, 1.0);
			AnchorPane.setLeftAnchor(tabPane, 1.0);
			AnchorPane.setBottomAnchor(tabPane, 1.0);
			borderPane.setCenter(anchor);
			MainApp.root.getItems().set(1, borderPane);

		} else {

			// Three Tabs at the bottom
			TabPane innerTabPane = InnerTabPane.getInnerTabPane();
			InnerTabPane.setContentMain(MainApp.graph.getCanvas());
			tabPane.getSelectionModel().getSelectedItem().setContent(innerTabPane);
			Metrics.setAll();

			MainApp.graphList[tabPane.getSelectionModel().getSelectedIndex()] = MainApp.graph;
			ObservableList<Tab> tabList = tabPane.getTabs();
			boolean freeSpace = false;
			for (Tab tab : tabList) {
				if (tab.getContent() == null) {
					freeSpace = true;
				}
			}
			if (!freeSpace) {
				StringProperty sP2 = new SimpleStringProperty("New Group");
				Tab tab2 = new RenameableTab(sP2);
				tabPane.getTabs().add(tab2);
			}
		}
	}

	public int getNumberOfTabs() {
		return tabPane.getTabs().size();
	}

}
