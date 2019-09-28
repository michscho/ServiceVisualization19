package tum.franca.view.list;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * 
 * @author michaelschott
 *
 */
public class ListViewWrapper {
	
	public static ObjectProperty<ListCell<String>> dragItem = new SimpleObjectProperty<>();
	public static boolean dropped = false;
	
	private ListView<String> listView1;
	private ListView<String> listView2;
	private ListView<String> listView3;
	private ListView<String> listView4;
	
	public ListViewWrapper(ListView<String> listView1, ListView<String> listView2, ListView<String> listView3, ListView<String> listView4) {
		this.listView1 = listView1;
		this.listView2 = listView2;
		this.listView3 = listView3;
		this.listView4 = listView4;
	}
	
	/**
	 *  Creates the four listViews.
	 * @throws Exception
	 */
	public void createListViews() throws Exception {
		ListViewCreator listView = new ListViewCreator(this.listView1, true);
		listView.createView();
		ListViewCreator listView2 = new ListViewCreator(this.listView2, true);
		listView2.createView();
		ListViewCreator listView3 = new ListViewCreator(this.listView3, true);
		listView3.createView();
		ListViewCreator listView4 = new ListViewCreator(this.listView4);
		listView4.createView();	
	}

	/**
	 * 
	 * @return filted ListView
	 */
	public ListView<String> getListView1() {
		return listView1;
	}
	
	/**
	 * 
	 * @return filted ListView
	 */
	public ListView<String> getListView2() {
		return listView2;
	}
	
	
	/**
	 * 
	 * @return filted ListView
	 */
	public ListView<String> getListView3() {
		return listView3;
	}
	
	/**
	 * 
	 * @return filted ListView
	 */
	public ListView<String> getListView4() {
		return listView4;
	}
	


}
