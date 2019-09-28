package tum.franca.util.qualityofservice;

import org.franca.core.franca.FContent;
import org.franca.core.franca.QOS;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import tum.franca.util.reader.FidlReader;
import tum.franca.util.reader.StaticFidlReader;

/**
 * 
 * @author michaelschott
 *
 */
public class QosCreation {

	public void createQosCreation() {
		QosWindow window = new QosWindow();
		window.showQosWindow();
		for (FidlReader fr : StaticFidlReader.getFidlList()) {
			for (QOS qos : fr.getFModel().getQos()) {
				TitledPane titledPane = null;
				if (qos.getExtendQOSRef() != null) {
					titledPane = window.getController().addTitledPane(qos.getName(), qos.getExtendQOSRef().getName());
				} else {
					titledPane = window.getController().addTitledPane(qos.getName(), "none");
				}
				for (FContent content : qos.getContent()) {
					window.getController().setContent((FlowPane) titledPane.getContent(), content.getKey().getKey(), content.getValue().getValue(), content.getUnit().getUnit());
					System.out.println(content.getKey().getKey());
				}
			}
		}
	}

}
