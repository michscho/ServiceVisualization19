package tum.franca.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.franca.core.franca.FProvides;
import org.franca.core.franca.FRequires;

import tum.franca.graph.cells.ICell;
import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.edges.Edge;
import tum.franca.graph.graph.Model;
import tum.franca.reader.FidlReader;

/**
 * 
 * @author michaelschott
 *
 */
public class ModelSetter {
	
	/**
	 * Add cells to the Model with his interface name
	 * 
	 * @param model
	 */
	static void addCells(Model model) {
		List<ICell> cellList = new ArrayList<ICell>();
		for (FidlReader fidlReader : GroupSetter.fidlList) {
			try {
				final RectangleCell cell = new RectangleCell(fidlReader.getFirstInterfaceName(), GroupSetter.getGroup(fidlReader), fidlReader);
				cellList.add(cell);
				model.addCell(cell);
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}
	
	/**
	 * Add required Edges to the model.
	 * 
	 * @param model
	 */
	static void addRequiredEdge(Model model) {
		List<ICell> cellList = model.getAddedCells();
		for (FidlReader fidlReader : GroupSetter.fidlList) {
			try {
				EList<FProvides> providesList = fidlReader.getFirstProvides();
				for (FProvides fProvides : providesList) {
					System.out.println(fProvides.getProvides());
					String providesString = fProvides.getProvides();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {
							if (((RectangleCell) iCell).getName().equals(providesString)) {
								final Edge edge = new Edge(iCell,model.getRectangleCell(fidlReader.getFirstInterfaceName())
										);
								model.addEdge(edge);
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	/**
	 * Add provided Egdes to the model.
	 * 
	 * @param model
	 */
	static void addProvidedEdge(Model model) {
		List<ICell> cellList = model.getAddedCells();
		for (FidlReader fidlReader : GroupSetter.fidlList) {
			try {
				EList<FRequires> requiresList = fidlReader.getFirstRequires();
				for (FRequires fRequires : requiresList) {
					String requiredString = fRequires.getRequires();
					for (ICell iCell : cellList) {
						if (iCell instanceof RectangleCell) {
							if (((RectangleCell) iCell).getName().equals(requiredString)) {
								final Edge edge = new Edge((model.getRectangleCell(fidlReader.getFirstInterfaceName())),
										iCell);
								model.addEdge(edge);
							}
						}
					}
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}


}
