package tum.franca.save;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import tum.franca.main.MainAppController;
import tum.franca.tabs.TabPaneSetter;

/**
 * 
 * @author michaelschott
 *
 */
public class RedoManagerWrapper {

	private static Stack<File> stackList = new Stack<File>();

	private static int counter = -1;

	public static void saveState() throws IOException {
		setCounter();
		RedoManager redoManager = new RedoManager();
		redoManager = new RedoManager();
		File file = File.createTempFile("tmpVisFile", ".vis");
		file.deleteOnExit();
		System.out.println(file.getAbsolutePath());
		stackList.add(file);
		redoManager.save(file);
		counter++;
		TabPaneSetter.redo.setDisable(!isRedo());
		TabPaneSetter.undo.setDisable(!isUndo());
		System.out.println("Saved state");
	}

	public static void redo() {
		if (counter == stackList.size() - 1)
			return;
		RedoManager redoManager = new RedoManager();
		redoManager.getState(stackList.get(counter));
		System.out.println(stackList.get(counter).getAbsolutePath());
		redoManager.importSavedFile(MainAppController.tabPaneSetter);
		System.out.println("redo");
		TabPaneSetter.redo.setDisable(!isRedo());
		TabPaneSetter.undo.setDisable(!isUndo());
		counter++;
	}
	
	public static boolean isRedo() {
		if (counter == stackList.size() - 1)
			return false;
		return true;
	}
	
	public static boolean isUndo() {
		if (counter == -1)
			return false;
		return true;
	}

	public static void undo() {
		if (counter == -1)
			return;
		RedoManager redoManager = new RedoManager();
		redoManager.getState(stackList.get(counter));
		System.out.println(stackList.get(counter).getAbsolutePath());
		redoManager.importSavedFile(MainAppController.tabPaneSetter);
		counter--;
		System.out.println("undo");
		TabPaneSetter.redo.setDisable(!isRedo());
		TabPaneSetter.undo.setDisable(!isUndo());
	}

	private static void setCounter() {
		if (stackList.size() < 1)
			return;
		for (int i = stackList.size() - 1; i > counter; i--) {
			stackList.remove(i);
		}
	}

}
