package tum.franca.main;

import javafx.scene.control.ListView;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.ResizableRectangleCell;

public class ChangeWindowController {

	@FXML
	private ListView<String> listView1;
	@FXML
	private ListView<String> listView2;

	public void initialize() {
		List<String> list = new ArrayList<>();
		for (ICell iCell2 : ChangeWindow.intersectionCellListBefore) {
			if (iCell2 instanceof ResizableRectangleCell) {
				String string1 = ((ResizableRectangleCell) iCell2).group;
				String string2 = ((ResizableRectangleCell) iCell2).getName();
				String[] stringArray1 = string1.split(" ");
				String[] stringArray2 = string2.split(" ");
				for (int i = 0; i < stringArray1.length; i++) {
					for (int j = 0; j < stringArray2.length; j++) {
						if (i == j) {
							list.add(stringArray1[i].toString() + ": " + stringArray2[j].toString());
						}
					}
				}
			}
		}
		List<String> list2 = new ArrayList<>();
		for (ICell iCell2 : ChangeWindow.intersectionCellListAfter) {
			if (iCell2 instanceof ResizableRectangleCell) {
				String string1 = ((ResizableRectangleCell) iCell2).group;
				String string2 = ((ResizableRectangleCell) iCell2).getName();
				String[] stringArray1 = string1.split(" ");
				String[] stringArray2 = string2.split(" ");
				for (int i = 0; i < stringArray1.length; i++) {
					for (int j = 0; j < stringArray2.length; j++) {
						if (i == j) {
							list2.add(stringArray1[i].toString() + ": " + stringArray2[j].toString());
						}
					}
				}
			}
		}
		List<String> toRemove = new ArrayList<String>();
		StringBuilder stringBuilder1 = new StringBuilder();
		StringBuilder stringBuilder2 = new StringBuilder();
		for (String string : list) {
			for (int i = 0; i < list2.size(); i++) {
				if (string.split(": ")[0].equals(list2.get(i).split(": ")[0])) {
					if (string.split(": ")[1].equals(list2.get(i).split(": ")[1])) {
						// stringBuilder1.append(string + "_");
						// stringBuilder2.append(list2.get(i) + "_");
					} else {
						stringBuilder1.append(string + "_");
						stringBuilder2.append(list2.get(i) + "_");
					}
					toRemove.add(string);
					toRemove.add(list2.get(i));
					break;
				}
			}
		}
		list.removeAll(toRemove);
		list2.removeAll(toRemove);

		for (String string : list) {
			if (!string.contains("notDefined")) {
			stringBuilder1.append(string + "_");
			stringBuilder2.append("notDefined" + "_");
			}
		}
		for (String string : list2) {
			if (!string.contains("notDefined")) {
			stringBuilder1.append("notDefined" + "_");
			stringBuilder2.append(string + "_");
			}
		}

		String[] strings1 = stringBuilder1.toString().split("_");
		String[] strings2 = stringBuilder2.toString().split("_");
		for (int i = 0; i < strings1.length; i++) {
			listView1.getItems().add(strings1[i]);
		}
		for (int i = 0; i < strings2.length; i++) {
			listView2.getItems().add(strings2[i]);
		}
		if (listView1.getItems().get(0).isEmpty() && listView2.getItems().get(0).isEmpty()) {
			ChangeWindow.show = false;
		} else {
			ChangeWindow.show = true;
		}

		
	}

}
