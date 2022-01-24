package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Stream;

public class SortedUninformedSearcher extends Searcher {

	public SortedUninformedSearcher(String alias, Function<Node, Integer> sortingFunction) {
		super(alias);
		nodeQueue = new PriorityQueue<>(Comparator.comparing(sortingFunction));
	}

	@Override
	protected void queueNodes(final Stream<Node> children) {
		children.forEach(nodeQueue::add);
	}

}
