package tum.franca.graph.layout;

import java.util.List;

import tum.franca.graph.cells.RectangleCell;
import tum.franca.graph.graph.Graph;
import tum.franca.graph.graph.ICell;

/**
 * 
 * @author michaelschott
 *
 */
public class GroupLayout implements Layout {
	
	/**
	 *  Layout:
	 *  * * * *     * * * *
	 *   * * * *     * * * *
	 *  * * * *     * * * *
	 *  
	 *  * * * *     * *
	 *   * * * * 
	 */
	@Override
	public void execute(Graph graph) {

		final List<ICell> cells = graph.getModel().getAddedCells();

		int highestGroup = getHighestGroupId(cells);
		
		int xGroup = 0;
		int yGroup = 0;

		for (int i = 1; i <= highestGroup; i++) {	
			
			if (i == 1 || i % 4 == 0) {
				xGroup = 0;
			} else {
				xGroup += 500;
			}
			
			if (i % 4 == 0) {
				yGroup += 200;
			}
			
			int counter = 0;
			int x  = 0 + xGroup;
			int y = 0 + yGroup;

			for (final ICell cell : cells) {

				if (cell instanceof RectangleCell && ((RectangleCell) cell).getGroup() == i) {

					// indented effect
					if (counter % 4 == 0 && counter % 8 != 0) {
						x = 50 + xGroup;
						y += 50;
						yGroup += 25;
					}
					
					if (counter % 8 == 0) {
						x = 0 + xGroup;
						y += 50;
						yGroup += 25;
					}

					graph.getGraphic(cell).relocate(x, y);
					cell.setX(x);
					cell.setY(y);

					counter += 1;
					x += 100;
				}
			}			
		}
	}

	/**
	 * 
	 * @param cells
	 * @return highest number
	 */
	private int getHighestGroupId(List<ICell> cells) {
		int max = 0;
		for (final ICell cell : cells) {
			if (cell instanceof RectangleCell) {
				if (((RectangleCell) cell).getGroup() > max) {
					max = ((RectangleCell) cell).getGroup();
				}
			}
		}
		return max;
	}
	
}
