package com.wordpress.brancodes.main;

import com.wordpress.brancodes.node.EightsPuzzleNode;
import com.wordpress.brancodes.node.Node;
import com.wordpress.brancodes.searcher.*;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {

		eightsPuzzleTester();
		// queensPuzzleTester();
		// other games?

	}

	private static void eightsPuzzleTester() {
		final int[][][] BOARD_CONFIGS = new int[][][] {
				{{ 1, 3, 4 },
				 { 8, 6, 2 },
				 { 7, 0, 5 }},
				{{ 2, 8, 1 },
				 { 0, 4, 3 },
				 { 7, 6, 5 }},
				{{ 5, 6, 7 },
				 { 4, 0, 8 },
				 { 3, 2, 1 }}
		};

		final String[] BOARD_DIFFICULTIES = new String[]
				{ "easy", "medium", "hard" };

		final List<Searcher<EightsPuzzleNode>> searchers = List.of(
				new UninformedSearcher<>("BFS", Deque::addFirst),
				new UninformedSearcher<>("DFS", Deque::addLast),
				new SortedUninformedSearcher<>("UCS", EightsPuzzleNode::getTileValue),
				new InformedSearcher<>("GBF" , EightsPuzzleNode::getTileValue, EightsPuzzleNode::misplacedTiles, Node::heuristicFunction), // f(n) = h(n)
				new InformedSearcher<>("A*h1", EightsPuzzleNode::getTileValue, EightsPuzzleNode::misplacedTiles), // default f(n) is g(n) + h(n)
				new InformedSearcher<>("A*h2", EightsPuzzleNode::getTileValue, EightsPuzzleNode::manhattanSum),
				new InformedSearcher<>("A*h3", EightsPuzzleNode::getTileValue, EightsPuzzleNode::manhattanSumByValue)
		);

		for (int i = 0; i < BOARD_CONFIGS.length; i++) {
			System.out.println("           " + BOARD_DIFFICULTIES[i]);
			System.out.println("ALG  length cost time  space");
			for (final Searcher<EightsPuzzleNode> searcher : searchers) {
				printSearch(searcher, new EightsPuzzleNode(BOARD_CONFIGS[i]));
			}
		}
	}

	private static <T extends Node> void printSearch(final Searcher<T> searcher, final T root) {
		printSearch(searcher, root, false);
	}

	private static <T extends Node> void printSearch(final Searcher<T> searcher, final T root, final boolean printStack) {
		StringBuilder sB = new StringBuilder();
		String searcherAlias = searcher.getAlias();
		final SearchStats searchStats = searcher.search(root);
		sB.append(searcherAlias)
		  .append(":")
		  .append(" ".repeat(4 - searcherAlias.length()))
		  .append(searchStats);
		if (printStack)
			sB.append('\n').append(searchStats.solution().stackTrace().stream().map(Node::toString).collect(Collectors.joining("\n")));
		System.out.println(sB);
	}

}
