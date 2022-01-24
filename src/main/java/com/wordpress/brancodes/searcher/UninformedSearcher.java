package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class UninformedSearcher extends Searcher {

	private final BiConsumer<Deque<Node>, Stream<Node>> nodeQueuer; // how to place node in queue

	// public UninformedSearcher(String alias, BiConsumer<Deque<Node>, Stream<Node>> nodeQueuer) {
	// 	super(alias);
	// 	this.nodeQueuer = nodeQueuer;
	// 	nodeQueue = new ArrayDeque<>();
	// }

	public UninformedSearcher(String alias, BiConsumer<Deque<Node>, Node> nodeQueuer) {
		super(alias);
		this.nodeQueuer = (deque, nodes) -> nodes.forEach(node -> nodeQueuer.accept(deque, node));
		nodeQueue = new ArrayDeque<>();
	}

	@Override
	protected void queueNodes(final Stream<Node> children) {
		nodeQueuer.accept((Deque<Node>) nodeQueue, children);
	}

}
