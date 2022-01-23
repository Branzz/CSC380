package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class Searcher {

	protected String alias;
	private HashSet<Node> nodeHistory;

	private Searcher() {
		// nodeHistory = new PriorityQueue<>((n1, n2) -> {
		// 	int[][] b1 = n1.getBoard();
		// 	int[][] b2 = n2.getBoard();
		// 	for (int i = 0; i < SIZE; i++)
		// 		for (int j = 0; j < SIZE; j++) {
		// 			int compare = b1[i][j] - b2[i][j];
		// 			if (compare < 0)
		// 				return -1;
		// 			else if (compare > 0)
		// 				return 1;
		// 		}
		// 	return 0;
		// });
	}

	protected Searcher(String alias) {
		this();
		this.alias = alias;
		nodeHistory = new HashSet<>();
		nodeHistory = new HashSet<>();
	}

	public final SearchStats search(Node root) {
		int time = 0;
		int space = 0;
		Node top = root;
		do {
			queueNodes(top.expand().stream().filter(nodeHistory::add));
			// nodeQueuer.accept(nodeQueue, );
			top = nodeQueue.poll();
			if (nodeQueue.size() > space)
				space = nodeQueue.size();
			time++;
		} while (!top.isGoal());
		nodeHistory.clear();
		nodeQueue.clear();
		return new SearchStats(top.getDepth(), top.getCost(), time, space);
	}

	protected final boolean isRepeat(final Node node) {
		return nodeHistory.contains(node);
	}

	/**
	 * @param children the children from expansion of popped node
	 */
	protected abstract void queueNodes(final Stream<Node> children);

	// /**
	//  * TODO
	//  * @param node
	//  * @return
	//  */
	// protected abstract Node nodeProcessor(Node node);

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		return getAlias();
	}

}
