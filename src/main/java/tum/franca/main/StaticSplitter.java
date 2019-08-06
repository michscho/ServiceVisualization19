package tum.franca.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;

public class StaticSplitter {

	/**
	 * @param splitPane
	 */
	public static void setStaticSplitPane(SplitPane splitPane) {
		Divider divider = splitPane.getDividers().get(0);
		divider.setPosition(0.165);
		Divider divider2 = splitPane.getDividers().get(1);
		divider2.setPosition(0.81);
		divider.positionProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
				divider.setPosition(0.165);
			}
		});
		divider2.positionProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
				divider2.setPosition(0.81);
			}
		});
	}

}
