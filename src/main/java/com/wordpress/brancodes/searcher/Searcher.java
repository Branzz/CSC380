package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.main.Data;
import com.wordpress.brancodes.node.Node;

import java.util.HashSet;
import java.util.Queue;
import java.util.stream.Stream;

public abstract class Searcher<T extends Node> {

	protected Queue<T> nodeQueue; // to be instantiated by child
	protected String alias;
	private HashSet<T> nodeHistory;

	private Searcher() { }

	protected Searcher(String alias) {
		this();
		this.alias = alias;
		nodeHistory = new HashSet<>();
	}

	public final SearchStats search(final T root) {
		int time = 0;
		int space = 0;
		T top = root;
		do {
			if (Data.PRINT_NODES)
				System.out.println(top);
 			queueNodes(top.expand().stream().map(node -> (T) node).filter(nodeHistory::add));
			top = nodeQueue.poll();
			if (nodeQueue.size() > space)
				space = nodeQueue.size();
			time++;
			if (top == null) {
				System.out.println("The solution couldn't be found");
				return new SearchStats(null, time, space);
			}
		} while (!top.isGoal());
		if (Data.PRINT_NODES)
			System.out.println(top);
		nodeHistory.clear();
		nodeQueue.clear();
		return new SearchStats(top, time, space);
	}

	protected final boolean isRepeat(final T node) {
		return nodeHistory.contains(node);
	}

	/**
	 * @param children the children from expansion of popped node
	 */
	protected abstract void queueNodes(final Stream<T> children);

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		return getAlias();
	}

}
