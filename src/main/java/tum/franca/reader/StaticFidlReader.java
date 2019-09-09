package tum.franca.reader;

import java.util.ArrayList;
import java.util.List;

import tum.franca.graph.cells.RectangleCell;

public class StaticFidlReader {
	
	public static List<FidlReader> fidlList;
	
	public static void newFidlList(){
		fidlList = new ArrayList<>();
	}
	
	public static List<FidlReader> getFidlList(){
		return fidlList;
	}
	
	public static FidlReader getFidl(RectangleCell cell) {
		if (fidlList == null) { 
			return null;
		} 
		for (FidlReader fidlReader : fidlList) {
			if(cell.getName().equals(fidlReader.getFirstInterfaceName())) {
				return fidlReader;
			}
		}
		return null;
		
	}

}
