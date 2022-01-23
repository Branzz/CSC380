package com.wordpress.brancodes.main;

import com.wordpress.brancodes.node.Node;
import com.wordpress.brancodes.searcher.Searcher;
import com.wordpress.brancodes.searcher.UninformedSearcher;

import java.util.List;

import static com.wordpress.brancodes.main.Data.*;

public class Main {

	private static final List<Searcher> searchers = List.of(
			new UninformedSearcher("BFS", (queue, nodes) -> nodes.forEach(queue::addFirst)),
			new UninformedSearcher("DFS", (queue, nodes) -> nodes.forEach(queue::addLast))
	);

	public static void main(String[] args) {
		System.out.println("     length  cost  time space");
		for (int i = 0; i < BOARD_CONFIGS.length; i++) {
			System.out.print(BOARD_DIFFICULTIES[i] + ":");
			for (final Searcher searcher : searchers) {
				StringBuilder sB = new StringBuilder();
				String searcherAlias = searcher.getAlias();
				sB.append(searcherAlias)
				  .append(":")
				  .append(" ".repeat(4 - searcherAlias.length()))
				  .append(searcher.search(new Node(BOARD_CONFIGS[i], null, null, 0, 0)));
				System.out.println(sB);
			}
		}
	}

	/**
	 * @return number of misplaced tiles
	 */
	public static int h1(int[][] board) {
		int count = 0;
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (GOAL[i][j] != board[i][j])
					count++;
		return count;
	}

	/**
	 * @return sum of Manhattan distances
	 */
	public static int h2(int[][] board) {
		return 0;
	}

	/**
	 * @return tile value * sum of Manhattan distances
	 */
	public static int h3(int[][] board) {
		return 0;
	}

}
