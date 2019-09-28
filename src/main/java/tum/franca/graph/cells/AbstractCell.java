package tum.franca.graph.cells;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author michaelschott
 *
 */
public abstract class AbstractCell implements ICell {

	private final List<ICell> children = new ArrayList<>();
	private final List<ICell> parents = new ArrayList<>();
	
	private int x;
	private int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void addCellChild(ICell cell) {
		children.add(cell);
	}

	@Override
	public List<ICell> getCellChildren() {
		return children;
	}

	@Override
	public void addCellParent(ICell cell) {
		parents.add(cell);
	}

	@Override
	public List<ICell> getCellParents() {
		return parents;
	}

	

}
