package tum.franca.views.listview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import tum.franca.properties.PropertiesUtil;
import tum.franca.views.ListViewWrapper;

/**
 * 
 * @author michaelschott
 *
 */
public class ListViewCreator {

	private ListView<String> listView;
	private ObservableList<String> items;

	/**
	 * 
	 * @param listView
	 */
	public ListViewCreator(ListView<String> listView) {
		this.listView = listView;
		this.items = FXCollections.observableArrayList();
		this.items.addAll(PropertiesUtil.getAllPropertiesAsStrings());
		this.items.add("");
	}

	/**
	 * 
	 * @param listView
	 * @param empty
	 */
	public ListViewCreator(ListView<String> listView, boolean empty) {
		this.listView = listView;
		this.items = FXCollections.observableArrayList("", "", "");
	}

	/**
	 * Creates the listView.
	 * 
	 * @throws Exception
	 */
	public void createView() throws Exception {
		listView.setCellFactory(param -> new CustomCell());
		listView.setItems(items);
	}

	/**
	 * 
	 * @author michaelschott
	 *
	 */
	private class CustomCell extends ListCell<String> {

		public CustomCell() {

			setOnDragDetected(event -> {

				if (getItem() == null | getItem() == "") {
					return;
				}

				if (event.getButton() == MouseButton.PRIMARY) {
					WritableImage image = snapshot(new SnapshotParameters(), null);
					Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
					ClipboardContent content = new ClipboardContent();
					content.putString("property");
					dragboard.setDragView(image);
					dragboard.setContent(content);
					setCursor(Cursor.HAND);
					event.consume();
					ListViewWrapper.dragItem.set(this);
				}
			});

			setOnDragOver(event -> {
				if (event.getGestureSource() != this && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			});

			setOnDragEntered(event -> {
				if (event.getGestureSource() != this && event.getDragboard().hasString()) {
					setOpacity(0.3);
				}
			});

			setOnDragExited(event -> {
				if (event.getGestureSource() != this && event.getDragboard().hasString()) {
					setOpacity(1);
				}
			});

			setOnDragDropped(event -> {
				if (getItem() == null) {
					return;
				}

				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasString()) {

					ListCell<String> dragSourceCell = ListViewWrapper.dragItem.get();

					boolean set = false;
					for (int i = 0; i < items.size(); i++) {
						if (items.get(i) == "") {
							items.set(i, dragSourceCell.getItem());
							set = true;
							break;
						}
					}
					if (!set) {
						items.add(dragSourceCell.getItem());
					}
					event.setDropCompleted(true);
					ListViewWrapper.dragItem.set(null);

					success = true;
					ListViewWrapper.dropped = true;
				}

				event.setDropCompleted(success);
				event.consume();
			});

			setOnDragDone(event -> {
				if (ListViewWrapper.dropped) {

					listView.getItems().remove(getItem());
					listView.getItems().add("");
					event.consume();
					ListViewWrapper.dropped = false;
				}
			});
		}

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
				setText(null);
			} else {
				setText(item);
			}
		}
	}

}
