package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.main.Data;
import com.wordpress.brancodes.node.Node;

import java.util.HashSet;
import java.util.Queue;
import java.util.stream.Stream;

public abstract class Searcher {

	protected Queue<Node> nodeQueue;
	protected String alias;
	private HashSet<Node> nodeHistory;

	private Searcher() { }

	protected Searcher(String alias) {
		this();
		this.alias = alias;
		nodeHistory = new HashSet<>();
	}

	public final SearchStats search(int[][] board) {
		return search(new Node(board, null, null, 0, 0));
	}

	public final SearchStats search(final Node root) {
		int time = 0;
		int space = 0;
		Node top = root;
		do {
			if (Data.PRINT_NODES)
				System.out.println(top);
 			queueNodes(top.expand().stream().filter(nodeHistory::add));
			// nodeQueuer.accept(nodeQueue, );
			top = nodeQueue.poll();
			if (nodeQueue.size() > space)
				space = nodeQueue.size();
			time++;
		} while (!top.isGoal());
		if (Data.PRINT_NODES)
			System.out.println(top);
		nodeHistory.clear();
		nodeQueue.clear();
		return new SearchStats(top.getDepth(), top.getTileValue(), time, space);
	}

	protected final boolean isRepeat(final Node node) {
		return nodeHistory.contains(node);
	}

	/**
	 * @param children the children from expansion of popped node
	 */
	protected abstract void queueNodes(final Stream<Node> children);

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		return getAlias();
	}

}
