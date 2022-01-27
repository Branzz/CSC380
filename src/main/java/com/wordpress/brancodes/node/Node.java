package com.wordpress.brancodes.node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public abstract class Node implements Evaluable {


	public Node() {
	}

	/**
	 * successor function. Fills all possible next states into children
	 * MUST return type of current Node subclass
	 *
	 * @return the expansion
	 */
	public abstract List<? extends Node> expand();

	public abstract boolean isExpanded();

	public abstract boolean isGoal();

	public abstract int getDepth();

	public abstract Node getParent();

	public Queue<Node> stackTrace() {
		Deque<Node> nodeStack = new ArrayDeque<>(getDepth());
		Node current = this;
		while (current != null) {
			nodeStack.addFirst(current);
			current = current.getParent();
		}
		return nodeStack;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		if (heuristicFunction() != -1) { // if informed
			sB.append(" evaluationCost: ")
			  .append(evaluationFunction())
			  .append(" pathCost: ")
			  .append(costFunction())
			  .append(" goalCost: ")
			  .append(heuristicFunction());
		}
		return sB.toString();
	}

	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract int hashCode();

}
