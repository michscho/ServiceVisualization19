package tum.franca.graph.cells;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.franca.core.franca.FContent;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import tum.franca.graph.edges.Edge;
import tum.franca.main.MainApp;

/**
 * 
 * @author michaelschott
 *
 */
public class ContxtMenuEdge {

	public static boolean binding = false;
	private static ContextMenu contextMenu;

	public static ContextMenu getContextMenu() {
		return contextMenu;
	}

	public static void setContextMenu(Edge edge) {

		if (contextMenu != null && contextMenu.isShowing()) {
			contextMenu.hide();
		}

		contextMenu = new ContextMenu();
		MenuItem remove = new MenuItem("Remove Edge");
		Menu qosAttributes = new Menu("See QOS Attributes");
		MenuItem qosAttributeChange = new MenuItem("Change Attributes");

		RectangleCell cell1 = (RectangleCell) edge.getSource();
		RectangleCell cell2 = (RectangleCell) edge.getTarget();

		EList<FContent> contentList = new BasicEList<>();

		for (FProvides provides : ((RectangleCell) cell1).getFidlReader().getFirstProvides()) {
			if (provides.getProvides().getName().equals(cell2.getName())) {
				for (FContent content : provides.getQos().getContent()) {
					if (provides.getQos() != null) {
						contentList.add(content);
					}
				}
			}
		}
		for (FRequires requires : ((RectangleCell) cell1).getFidlReader().getFirstRequires()) {
			if (requires.getRequires().getName().equals(cell2.getName())) {
				if (requires.getQos() != null) {
					for (FContent content : requires.getQos().getContent()) {
						contentList.add(content);
					}
				}
			}
		}
		for (FProvides provides : ((RectangleCell) cell2).getFidlReader().getFirstProvides()) {
			if (provides.getProvides().getName().equals(cell1.getName())) {
				for (FContent content : provides.getQos().getContent()) {
					if (provides.getQos() != null) {
						contentList.add(content);
					}
				}
			}
		}
		for (FRequires requires : ((RectangleCell) cell2).getFidlReader().getFirstRequires()) {
			if (requires.getRequires().getName().equals(cell1.getName())) {
				for (FContent content : requires.getQos().getContent()) {
					if (requires.getQos() != null) {
						contentList.add(content);
					}
				}
			}
		}

		for (FContent fContent : contentList) {
			MenuItem menu = new MenuItem(fContent.getKey().getKey() + " " + fContent.getValue().getValue() + " "
					+ fContent.getUnit().getUnit());
			qosAttributes.getItems().add(menu);
		}

		contextMenu.getItems().addAll(remove, qosAttributes, qosAttributeChange);
		remove.setOnAction(e -> {
			MainApp.graph.removeEdge(edge);
			MainApp.graph.getModel().getAddedEdges().remove(edge);
			contextMenu.hide();
		});

	}

}