package com.wordpress.brancodes.main;

import com.wordpress.brancodes.node.Node;
import com.wordpress.brancodes.searcher.*;

import java.util.Deque;
import java.util.List;

import static com.wordpress.brancodes.main.Data.BOARD_CONFIGS;
import static com.wordpress.brancodes.main.Data.BOARD_DIFFICULTIES;

public class Main {

	public static void main(String[] args) {

//		printSearch(BOARD_CONFIGS[1], new UninformedSearcher("BFS", Deque::addFirst));

		final List<Searcher> searchers = List.of(
				new UninformedSearcher("BFS", Deque::addFirst),
				new UninformedSearcher("DFS", Deque::addLast),
				new SortedUninformedSearcher("UCS", Node::costFunction),
				new InformedSearcher("GBF",  Node::misplacedTiles, Node::heuristicFunction), // f(n) = h(n)
				new InformedSearcher("A*h1", Node::misplacedTiles), // default f(n) is g(n) + h(n)
				new InformedSearcher("A*h2", Node::manhattanSum),
				new InformedSearcher("A*h3", Node::manhattanSumByValue)
		);

		for (int i = 0; i < BOARD_CONFIGS.length; i++) {
			System.out.println("           " + BOARD_DIFFICULTIES[i]);
			System.out.println("ALG  length cost time  space");
			for (final Searcher searcher : searchers) {
				printSearch(BOARD_CONFIGS[i], searcher);
			}
		}
	}

	private static void printSearch(final int[][] board, final Searcher searcher) {
		StringBuilder sB = new StringBuilder();
		String searcherAlias = searcher.getAlias();
		sB.append(searcherAlias)
		  .append(":")
		  .append(" ".repeat(4 - searcherAlias.length()))
		  .append(searcher.search(board));
		System.out.println(sB);
	}

}
