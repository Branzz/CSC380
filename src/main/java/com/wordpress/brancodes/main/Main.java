package com.wordpress.brancodes.main;

import com.wordpress.brancodes.node.Node;
import com.wordpress.brancodes.searcher.InformedSearcher;
import com.wordpress.brancodes.searcher.Searcher;
import com.wordpress.brancodes.searcher.UninformedSearcher;

import java.util.Deque;
import java.util.List;

import static com.wordpress.brancodes.main.Data.BOARD_CONFIGS;
import static com.wordpress.brancodes.main.Data.BOARD_DIFFICULTIES;

public class Main {

	public static void main(String[] args) {

		final List<Searcher> searchers = List.of(
				new UninformedSearcher("BFS", Deque::addFirst),
				new UninformedSearcher("DFS", Deque::addLast),
				new UninformedSearcher("UCS", Deque::add),
				//					         h(n)						f(n)
				new InformedSearcher("GBF",  Node::misplacedTiles,		Node::heuristicFunction), // f(n) = h(n)
				new InformedSearcher("A*h1", Node::misplacedTiles,		node -> node.costFunction() + node.heuristicFunction()), // f(n) = g(n) + h(n)
				new InformedSearcher("A*h2", Node::manhattanSum,		node -> node.costFunction() + node.heuristicFunction()),
				new InformedSearcher("A*h3", Node::manhattanSumByValue,	node -> node.costFunction() + node.heuristicFunction())
		);

		System.out.println("     length  cost  time space");
		for (int i = 0; i < BOARD_CONFIGS.length; i++) {
			System.out.print(BOARD_DIFFICULTIES[i] + ":\n");
			for (final Searcher searcher : searchers) {
				StringBuilder sB = new StringBuilder();
				String searcherAlias = searcher.getAlias();
				sB.append(searcherAlias)
				  .append(":")
				  .append(" ".repeat(4 - searcherAlias.length()))
				  .append(searcher.search(BOARD_CONFIGS[i]));
				System.out.println(sB);
			}
		}
	}

}
