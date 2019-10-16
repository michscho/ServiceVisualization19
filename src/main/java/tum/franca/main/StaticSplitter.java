package tum.franca.main;

import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;

/**
 * 
 * @author michaelschott
 *
 */
public class StaticSplitter {

	/**
	 * @param splitPane
	 */
	public static void setStaticSplitPane(SplitPane splitPane) {
		Divider divider = splitPane.getDividers().get(0);
		divider.setPosition(0.165);
		Divider divider2 = splitPane.getDividers().get(1);
		divider2.setPosition(0.81);
	}

}
