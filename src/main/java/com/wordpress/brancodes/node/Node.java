package com.wordpress.brancodes.node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wordpress.brancodes.main.Data.GOAL;
import static com.wordpress.brancodes.main.Data.SIZE;

public class Node {

	private Move moveDirection; // move it took to get to this node
	private int[][] board; // the state
	private Node parent;
	private int depth;
	private int movedTileCost;
	private int cost;
	private int totalCost;
	private List<Node> children;
	private Point zero; // where the space is

	public Node(int[][] board, Node parent, Move moveDirection, int depth, int movedTileCost) {
		this.board = board;
		this.parent = parent;
		this.moveDirection = moveDirection;
		this.depth = depth;
		this.movedTileCost = movedTileCost;
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
			if (move != moveDirection.opposite // so that it doesn't search backwards up the tree
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

	public int[][] getBoard() {
		return board;
	}

	public int getDepth() {
		return depth;
	}

	public int getCost() {
		return movedTileCost;
	}

	public List<Node> getChildren() {
		return children;
	}

	public boolean isExpanded() {
		return children != null;
	}

	/**
	 * f(n)
	 */
	public int evaluationFunction() {
		return 0;
	}

	/**
	 * g(n)
	 */
	public int costFunction() {
		return 0;
	}

	/**
	 * h(n)
	 */
	public int heuristicFunction() {
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				sB.append(board[i][j] == 0 ? ' ' : board[i][j])
				  .append(j == SIZE - 1 ? '\n' : ' ');
			}
		}
		sB.append(moveDirection)
		  .append(" cost: ")
		  .append(cost)
		  .append("total cost: ")
		  .append(totalCost);
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
