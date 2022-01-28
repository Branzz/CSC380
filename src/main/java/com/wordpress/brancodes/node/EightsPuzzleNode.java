package com.wordpress.brancodes.node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class EightsPuzzleNode extends Node {

	public final static int SIZE = 3;

	final static int[][] GOAL = new int[][] {
			{ 1, 2, 3 },
			{ 8, 0, 4 },
			{ 7, 6, 5 }
	};

	final static Point[] GOAL_POINTS = new Point[SIZE * SIZE];

	static {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				GOAL_POINTS[GOAL[i][j]] = new Point(j, i); // p[i] is where i is on GOAL
	}

	private final int depth;
	private final Move moveDirection; // move it took to get to this node
	private final int[][] board; // the state
	private final EightsPuzzleNode parent;
	private final int movedTileValue;
	private final Point zero; // where the space is
	private List<Node> children;

	private int evaluationCost;
	private int goalCost;

	/**
	 * default root node constructor
	 */
	public EightsPuzzleNode(int[][] board) {
		this(board, null, null, 0, 0);
	}

	public EightsPuzzleNode(int[][] board, EightsPuzzleNode parent, Move moveDirection, int depth, int movedTileValue) {
		super();
		this.depth = depth;
		this.board = board;
		this.parent = parent;
		this.moveDirection = moveDirection;
		this.movedTileValue = movedTileValue;
		goalCost = -1;
		zero = getZero();
	}

	public List<Node> expand() {
		if (isExpanded())
			return children;
		children = new ArrayList<>(4); // one index for each direction
		for (int i = 0; i < Move.values().length; i++) {
			Move move = Move.values()[i];
			if ((moveDirection == null || move != moveDirection.opposite) // so that it doesn't search backwards up the tree
				&& move.inBounds(zero)) { // can't move out of bounds pieces in
				// clone board
				int[][] modifiedBoard = new int[SIZE][SIZE];
				for (int ind = 0; ind < SIZE; ind++)
					System.arraycopy(board[ind], 0, modifiedBoard[ind], 0, SIZE);
				// make the move
				int movedTileValue = board[zero.x + move.rowDiff][zero.y + move.colDiff];
				modifiedBoard[zero.x][zero.y] = movedTileValue;
				modifiedBoard[zero.x + move.rowDiff][zero.y + move.colDiff] = 0;
				children.add(new EightsPuzzleNode(modifiedBoard, this, move, depth + 1, movedTileValue));
			}
		}
		return children;
	}

	/**
	 * @return the row and column of where our empty tile is
	 */
	private Point getZero() {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (board[i][j] == 0) {
					return new Point(i, j);
				}
		return new Point(-1, -1);
	}

	@Override
	public boolean isGoal() {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (GOAL[i][j] != board[i][j])
					return false;
		return true;
	}

	public int getBoardAsInt() {
		int boardAsInt = 0;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				boardAsInt += Math.pow(10, i * SIZE + j) * board[i][j];
		return boardAsInt;
	}

	public Integer misplacedTiles() {
		int count = 0;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (GOAL[i][j] != board[i][j])
					count++;
		return count;
	}

	public Integer manhattanSum() {
		int manhattanSum = 0;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (board[i][j] != 0) // don't include manhattan distance of the empty tile
					manhattanSum += abs(GOAL_POINTS[board[i][j]].y - i) + abs(GOAL_POINTS[board[i][j]].x - j);
		return manhattanSum;
	}

	public Integer manhattanSumByValue() {
		return manhattanSum() * movedTileValue;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public EightsPuzzleNode getParent() {
		return parent;
	}

	public int getTileValue() {
		return movedTileValue;
	}

	@Override
	public boolean isExpanded() {
		return children != null;
	}

	/** f(n) */
	public int evaluationFunction() {
		return evaluationCost;
	}

	/** g(n) */
	public int costFunction() {
		return getTileValue();
	}

	/** h(n) */
	public int heuristicFunction() {
		return goalCost;
	}

	// to be set by the (informed) searcher

	public void setEvaluationCost(final int evaluationCost) {
		this.evaluationCost = evaluationCost;
	}

	@Override
	public void setCostFunction(final int evaluationCost) {
		// not needed
	}

	public void setGoalCost(final int goalCost) {
		this.goalCost = goalCost;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				sB.append(board[i][j])
				  .append(' ');
			}
		}
		sB.append('\n').append(moveDirection);
		sB.append(super.toString());
		return sB.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return Arrays.deepEquals(board, ((EightsPuzzleNode) o).board);
	}

	@Override
	public int hashCode() {
		if (SIZE > 3) // 16+ digits won't fit into an int
			return Arrays.deepHashCode(board);
		else
			return getBoardAsInt();
	}

	enum Move {
		UP(1, 0), RIGHT(0, -1), DOWN(-1, 0, UP), LEFT(0, 1, RIGHT);

		// the location of the piece moving from the empty tile
		final int rowDiff;
		final int colDiff;
		Move opposite;

		Move(int rowDiff, int colDiff) {
			this.rowDiff = rowDiff;
			this.colDiff = colDiff;
		}

		Move(int rowDiff, int colDiff, Move opposite) {
			this.rowDiff = rowDiff;
			this.colDiff = colDiff;
			opposite.opposite = this;
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

		@Override
		public String toString() {
			return name();
		}

	}

}
