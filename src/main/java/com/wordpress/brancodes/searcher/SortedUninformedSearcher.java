package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Stream;

public class SortedUninformedSearcher<T extends Node> extends Searcher<T> {

	public SortedUninformedSearcher(String alias, Function<T, Integer> sortingFunction) {
		super(alias);
		nodeQueue = new PriorityQueue<>(Comparator.comparing(sortingFunction));
	}

	@Override
	protected void queueNodes(final Stream<T> children) {
		children.forEach(nodeQueue::add);
	}

}
