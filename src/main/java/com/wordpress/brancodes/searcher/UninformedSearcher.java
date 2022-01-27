package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class UninformedSearcher<T extends Node> extends Searcher<T> {

	private final BiConsumer<Deque<T>, Stream<T>> nodeQueuer; // how to place node in queue

	public UninformedSearcher(String alias, BiConsumer<Deque<T>, T> nodeQueuer) {
		super(alias);
		this.nodeQueuer = (deque, nodes) -> nodes.forEach(node -> nodeQueuer.accept(deque, node));
		nodeQueue = new ArrayDeque<>();
	}

	@Override
	protected void queueNodes(final Stream<T> children) {
		nodeQueuer.accept((Deque<T>) nodeQueue, children);
	}

}
