package tum.franca.util.propertyfunction;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import tum.franca.graph.cells.servicegroup.ResizableRectangleCell;

/**
 */
public class PropertyFunctionWindow extends Application {

	ResizableRectangleCell cell;

	public void showPropertyFunctionWindow(ResizableRectangleCell cell) {
		Parent root;
		this.cell = cell;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getResource("/PropertyFunction.fxml").openStream());
			Stage stage = new Stage();
			stage.setTitle("Property Function -" + cell.getName());
			stage.setScene(new Scene(root, 650, 400));
			stage.setAlwaysOnTop(true);
			stage.setResizable(false);
			stage.show();
			PropertyFunctionWindowController c = (PropertyFunctionWindowController) fxmlLoader.getController();
			c.properties = new ArrayList<PropertyEntity>();
			if (cell != null && cell.properties.size() > 0) {
				for (PropertyEntity oldEntity : cell.properties) {
					Pair<PropertyEntity, TitledPane> pair = c.addTitledPane(oldEntity.getName());
					PropertyEntity newEntity = pair.getKey();
					TitledPane titledPane = pair.getValue();
					FlowPane contentPane = c.addContentHeader(oldEntity);
					if (oldEntity.getElementList().size() == 0) {
						c.setEmptyContent(contentPane, newEntity);
					} else {
						for (PropertyElement element : oldEntity.getElementList()) {
							c.setContent(contentPane, element.getKey(), element.getValue(), element.getGroup(),
									newEntity);
						}
						titledPane.setContent(contentPane);
					}
				}
			}
			stage.setOnCloseRequest(event -> {
				PropertyFunctionWindowController con = (PropertyFunctionWindowController) fxmlLoader.getController();
				cell.properties = con.getProperties();
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		showPropertyFunctionWindow(null);
	}

	/**
	 * 
	 * @param args
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
