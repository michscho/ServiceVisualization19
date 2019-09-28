package tum.franca.graph.edges;

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
import javafx.scene.control.RadioMenuItem;
import tum.franca.graph.cells.service.RectangleCell;
import tum.franca.main.MainApp;
import tum.franca.util.qualityofservice.QosCreation;
import tum.franca.util.reader.FidlReader;
import tum.franca.util.reader.StaticFidlReader;

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

	public static Menu getQosAttributesMenu(Edge edge) {
		
		Menu qosAttributes = new Menu("See QOS Attributes");

		RectangleCell sourceCell = (RectangleCell) edge.getSource();
		RectangleCell targetCell = (RectangleCell) edge.getTarget();

		EList<FContent> contentList = new BasicEList<>();
		EList<QOS> qosList = new BasicEList<>();

		for (FProvides provides : ((RectangleCell) sourceCell).getFidlReader().getFirstProvides()) {
			if (provides.getProvides().equals(targetCell.getName())) {
				for (FContent content : provides.getQos().getContent()) {
					if (provides.getQos() != null) {
						contentList.add(content);
						qosList.add(provides.getQos());
					}
				}
			}
		}
		for (FRequires requires : ((RectangleCell) sourceCell).getFidlReader().getFirstRequires()) {
			if (requires.getRequires().equals(targetCell.getName())) {
				if (requires.getQos() != null) {
					for (FContent content : requires.getQos().getContent()) {
						contentList.add(content);
						qosList.add(requires.getQos());
					}
				}
			}
		}
		for (FProvides provides : ((RectangleCell) targetCell).getFidlReader().getFirstProvides()) {
			if (provides.getProvides().equals(sourceCell.getName())) {
				for (FContent content : provides.getQos().getContent()) {
					if (provides.getQos() != null) {
						contentList.add(content);
						qosList.add(provides.getQos());
					}
				}
			}
		}
		for (FRequires requires : ((RectangleCell) targetCell).getFidlReader().getFirstRequires()) {
			if (requires.getRequires().equals(sourceCell.getName())) {
				for (FContent content : requires.getQos().getContent()) {
					if (requires.getQos() != null) {
						contentList.add(content);
						qosList.add(requires.getQos());
					}
				}
			}
		}

		for (FContent fContent : contentList) {
			MenuItem menu = new MenuItem(fContent.getKey().getKey() + " " + fContent.getValue().getValue() + " "
					+ fContent.getUnit().getUnit());
			qosAttributes.getItems().add(menu);
		}
		
		if (contentList.isEmpty() && edge.qos != null) {
			for (FidlReader fr : StaticFidlReader.getFidlList()) {
				for (QOS qos : fr.getFModel().getQos()) {
					if (qos.getName().equals(edge.qos)) {
						for (FContent content : qos.getContent()) {
							MenuItem menu = new MenuItem(content.getKey().getKey() + " " + content.getValue().getValue() + " "
									+ content.getUnit().getUnit());
							qosAttributes.getItems().add(menu);
						}
					}
				}
			}
		}
		
		for (QOS qos : qosList) {
			edge.qos = qos.getName();
		}
		
		return qosAttributes;
	}

	public static void setContextMenu(Edge edge, Group group) {

		if (contextMenu != null && contextMenu.isShowing()) {
			contextMenu.hide();
		}

		contextMenu = new ContextMenu();
		MenuItem remove = new MenuItem("Remove Edge");
		Menu qosAttributes = getQosAttributesMenu(edge);
		Menu qosAttributeChangeQuick = new Menu("QuickChange QOS");
		MenuItem qosAttributeChange = new MenuItem("Change QOS");

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
				RadioMenuItem item = new RadioMenuItem(qos.getName());
				if (edge.qos != null) {
					if (item.getText().equals(edge.qos)){
						item.setSelected(true);
					}
				}
				qosAttributeChangeQuick.getItems().add(item);
				EventHandler<ActionEvent> onItemClicked = new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						edge.qos = item.getText();	
					}
				};
				item.setOnAction(onItemClicked);
			}
		}
		
		


		contextMenu.getItems().addAll(remove, qosAttributes, qosAttributeChangeQuick, qosAttributeChange);
		remove.setOnAction(e -> {
			MainApp.graph.getCanvas().getChildren().remove(group);
			MainApp.graph.getModel().getAddedEdges().remove(edge);
			contextMenu.hide();
		});

	}

}