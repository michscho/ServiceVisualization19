package tum.franca.graph.cells;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.franca.core.franca.FContent;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;
import org.franca.core.franca.QOS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import tum.franca.graph.edges.Edge;
import tum.franca.main.MainApp;
import tum.franca.main.QosCreation;
import tum.franca.reader.FidlReader;
import tum.franca.reader.StaticFidlReader;

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

	public static void setContextMenu(Edge edge, Group group) {

		if (contextMenu != null && contextMenu.isShowing()) {
			contextMenu.hide();
		}

		contextMenu = new ContextMenu();
		MenuItem remove = new MenuItem("Remove Edge");
		Menu qosAttributes = new Menu("See QOS Attributes");
		Menu qosAttributeChangeQuick = new Menu("QuickChange QOS");
		MenuItem qosAttributeChange = new MenuItem("Change QOS");

		RectangleCell cell1 = (RectangleCell) edge.getSource();
		RectangleCell cell2 = (RectangleCell) edge.getTarget();
		
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				QosCreation qosC = new QosCreation();
				qosC.createQosCreation();
			}
		};
		
		qosAttributeChange.setOnAction(handler);
		
		
		for (FidlReader fr : StaticFidlReader.getFidlList()) {
			for (QOS qos : fr.getFModel().getQos()) {
				MenuItem item = new MenuItem(qos.getName());
				qosAttributeChangeQuick.getItems().add(item);
				EventHandler<ActionEvent> onItemClicked = new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						((RectangleCell) edge.getSource()).getFidlReader().getFirstProvides();
						((RectangleCell) edge.getSource()).getFidlReader().getFirstProvides();
						((RectangleCell) edge.getTarget()).getFidlReader().getFirstProvides();
						((RectangleCell) edge.getTarget()).getFidlReader().getFirstProvides();
						
					}
				};
				item.setOnAction(onItemClicked);
			}
		}
		
		

		EList<FContent> contentList = new BasicEList<>();

		for (FProvides provides : ((RectangleCell) cell1).getFidlReader().getFirstProvides()) {
			if (provides.getProvides().equals(cell2.getName())) {
				for (FContent content : provides.getQos().getContent()) {
					if (provides.getQos() != null) {
						contentList.add(content);
					}
				}
			}
		}
		for (FRequires requires : ((RectangleCell) cell1).getFidlReader().getFirstRequires()) {
			if (requires.getRequires().equals(cell2.getName())) {
				if (requires.getQos() != null) {
					for (FContent content : requires.getQos().getContent()) {
						contentList.add(content);
					}
				}
			}
		}
		for (FProvides provides : ((RectangleCell) cell2).getFidlReader().getFirstProvides()) {
			if (provides.getProvides().equals(cell1.getName())) {
				for (FContent content : provides.getQos().getContent()) {
					if (provides.getQos() != null) {
						contentList.add(content);
					}
				}
			}
		}
		for (FRequires requires : ((RectangleCell) cell2).getFidlReader().getFirstRequires()) {
			if (requires.getRequires().equals(cell1.getName())) {
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

		contextMenu.getItems().addAll(remove, qosAttributes, qosAttributeChangeQuick, qosAttributeChange);
		remove.setOnAction(e -> {
			MainApp.graph.getCanvas().getChildren().remove(group);
			MainApp.graph.getModel().getAddedEdges().remove(edge);
			contextMenu.hide();
		});

	}

}