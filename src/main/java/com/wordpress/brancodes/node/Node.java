package com.wordpress.brancodes.node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wordpress.brancodes.main.Data.*;
import static java.lang.Math.abs;

public class Node implements Heuristic {

	private final Move moveDirection; // move it took to get to this node
	private final int[][] board; // the state
	private final Node parent;
	private final int depth;
	private final int movedTileValue;
	private final Point zero; // where the space is
	private List<Node> children;

	public Node(int[][] board, Node parent, Move moveDirection, int depth, int movedTileValue) {
		this.board = board;
		this.parent = parent;
		this.moveDirection = moveDirection;
		this.depth = depth;
		this.movedTileValue = movedTileValue;
		zero = getZero();
	}

	/**
	 * successor function. Fills all possible next states into children
	 * @return the expansion
	 */
	public List<Node> expand() {
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
				children.add(new Node(modifiedBoard, this, move, depth + 1, movedTileValue));
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

	public int[][] getBoard() {
		return board;
	}

	public Node getParent() {
		return parent;
	}

	public int getDepth() {
		return depth;
	}

	public int getTileValue() {
		return movedTileValue;
	}

	public List<Node> getChildren() {
		return children;
	}

	public boolean isExpanded() {
		return children != null;
	}

	private int evaluationCost;
	private int pathCost;
	private int goalCost;

	/** f(n) */
	@Override
	public int evaluationFunction() {
		return evaluationCost;
	}

	/** g(n) */
	@Override
	public int costFunction() {
		return getTileValue();
	}

	/** h(n) */
	@Override
	public int heuristicFunction() {
		return goalCost;
	}

	// to be set by the (informed) searcher

	public void setEvaluationCost(final int evaluationCost) {
		this.evaluationCost = evaluationCost;
	}

	public void setPathCost(final int pathCost) {
		this.pathCost = pathCost;
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
		if (evaluationCost != 0 || pathCost != 0 || goalCost != 0) { // if informed #TODO boolean informed?
			sB.append("evaluationCost: ")
			  .append(evaluationCost)
			  .append(" pathCost: ")
			  .append(pathCost)
			  .append(" goalCost: ")
			  .append(goalCost);
		}
		return sB.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return Arrays.deepEquals(board, ((Node) o).board);
	}

	@Override
	public int hashCode() {
		return getBoardAsInt();
	}

}
