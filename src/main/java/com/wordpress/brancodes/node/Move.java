package com.wordpress.brancodes.node;

import java.awt.*;

import static com.wordpress.brancodes.main.Data.SIZE;

public enum Move {
	UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);

	// the location of the piece moving from the empty tile
	final int rowDiff;
	final int colDiff;
	Move opposite;

	Move(int rowDiff, int colDiff) {
		this.rowDiff = rowDiff;
		this.colDiff = colDiff;
	}

	static {
		UP.opposite = DOWN;
		RIGHT.opposite = LEFT;
		DOWN.opposite = UP;
		LEFT.opposite = RIGHT;
	}

	/**
	 * example:
	 *  0X
	 *  00
	 * X (0, 1) can't move UP (row = (0) - 1) < 0; nor RIGHT (col = (1) + 1) >= 2
	 *
	 * @return whether the point lies on the grid when this move is applied.
	 */
	public boolean inBounds(Point point) {
		return inBounds(point.x + rowDiff) && inBounds(point.y + colDiff);
	}

	/**
	 * @return if the pos along the row or column is within the valid range;
	 * ArrayIndexOutOfBoundsException would be thrown otherwise.
	 */
	private boolean inBounds(int index) {
		return index >= 0 && index < SIZE;
	}

}
