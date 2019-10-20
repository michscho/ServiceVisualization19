package tum.franca.view.treeView;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class FidlViewController {

	@FXML
	private Text syntax;
	@FXML
	private TextArea textArea;
	@FXML
	private ListView<String> listView;
	
	
	@FXML
	public void open() throws IOException {
		URI uri = MainApp.graph.getModel()
				.getRectangleCell(listView.getSelectionModel().getSelectedItem()).fidlReader.getURI();
		Desktop.getDesktop().edit(new File(uri.toFileString()));

	}
	
	@FXML
	public void openExplorer() throws IOException {
		URI uri = MainApp.graph.getModel()
				.getRectangleCell(listView.getSelectionModel().getSelectedItem()).fidlReader.getURI();
		String uriFileString = uri.toFileString();
		String[] stringArray = uriFileString.split("/");
		Desktop.getDesktop().open(new File(uriFileString.replace(stringArray[stringArray.length-1], "")));

	}
	
	
	@FXML
	public void onReloadClicked() {
		listView.getItems().clear();
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
				listView.getItems().add(((RectangleCell) iCell).name);
			}
		}

		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				textArea.setText("");
				textArea.setEditable(false);
				URI uri = MainApp.graph.getModel()
						.getRectangleCell(listView.getSelectionModel().getSelectedItem()).fidlReader.getURI();
				try (BufferedReader reader = new BufferedReader(new FileReader(new File(uri.toFileString())))) {

					String line;
					while ((line = reader.readLine()) != null) {
						textArea.appendText(line + "\n");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@FXML
	public void initialize() throws Exception {
		listView.getItems().clear();
		for (ICell iCell : MainApp.graph.getModel().getAddedCells()) {
			if (iCell instanceof RectangleCell) {
			
				listView.getItems().add(((RectangleCell) iCell).name);
			}
		}

		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				textArea.setText("");
				textArea.setEditable(false);
				URI uri = MainApp.graph.getModel()
						.getRectangleCell(listView.getSelectionModel().getSelectedItem()).fidlReader.getURI();
				try (BufferedReader reader = new BufferedReader(new FileReader(new File(uri.toFileString())))) {

					String line;
					while ((line = reader.readLine()) != null) {
						textArea.appendText(line + "\n");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
