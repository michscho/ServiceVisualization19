package tum.franca.data;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import tum.franca.main.MainAppController;
import tum.franca.view.tab.TabPaneSetter;

/**
 * 
 * @author michaelschott
 * https://stackoverflow.com/questions/11530276/how-do-i-implement-a-simple-undo-redo-for-actions-in-java
 *
 */
public class RedoManager {

	private static Stack<File> stackList = new Stack<File>();
	private static int index = -1;

	public static void saveState() throws IOException {
		renewIndex();
		RedoManagerState redoManager = new RedoManagerState();
		File file = File.createTempFile("tmpVisFile", ".vis");
		file.deleteOnExit();
		stackList.add(file);
		redoManager.save(file);
		index++;
		TabPaneSetter.redo.setDisable(isRedoPossible());
		TabPaneSetter.undo.setDisable(isUndoPossible());
	}

	public static void undo() {
		setState();
		index--;
		TabPaneSetter.redo.setDisable(isRedoPossible());
		TabPaneSetter.undo.setDisable(isUndoPossible());
	}

	public static void redo() {
		if (index == stackList.size() - 1)
			return;
		index++;
		setState();
		TabPaneSetter.redo.setDisable(isRedoPossible());
		TabPaneSetter.undo.setDisable(isUndoPossible());
	}

	private static void setState() {
		RedoManagerState redoManager = new RedoManagerState();
		redoManager.importState(stackList.get(index));
		redoManager.setState(MainAppController.tabPaneSetter);
	}

	private static boolean isRedoPossible() {
		if (index == stackList.size() - 1)
			return true;
		return false;
	}

	private static boolean isUndoPossible() {
		if (index == -1)
			return true;
		return false;
	}

	private static void renewIndex() {
		if (stackList.size() < 1)
			return;
		for (int i = stackList.size() - 1; i > index; --i) {
			stackList.remove(i);
		}
	}

}
