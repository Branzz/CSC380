package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class UninformedSearcher extends Searcher {

	private Deque<Node> nodeDeque;
	private BiConsumer<Deque<Node>, Stream<Node>> nodeQueuer; // how to place node in queue

	public UninformedSearcher(String alias, BiConsumer<Deque<Node>, Stream<Node>> nodeQueuer) {
		super(alias);
		this.nodeQueuer = nodeQueuer;
		nodeDeque = new ArrayDeque<>();
	}

	@Override
	protected void queueNodes(final Stream<Node> children) {
		nodeQueuer.accept(nodeDeque, children);
	}

}
