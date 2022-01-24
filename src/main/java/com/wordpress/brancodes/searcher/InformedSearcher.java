package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Stream;

public class InformedSearcher extends Searcher {

	private final Function<Node, Integer> evaluationFunction;
	private final Function<Node, Integer> heuristicFunction;

	public InformedSearcher(String alias,
							final Function<Node, Integer> heuristicFunction,
							final Function<Node, Integer> evaluationFunction) {
		super(alias);
		this.heuristicFunction = heuristicFunction;
		this.evaluationFunction = evaluationFunction;
		nodeQueue = new PriorityQueue<>(Comparator.comparing(Node::evaluationFunction));
	}

	@Override
	protected void queueNodes(final Stream<Node> children) {
		children.peek(n -> n.setGoalCost(heuristicFunction.apply(n)))
				.peek(n -> n.setEvaluationCost(evaluationFunction.apply(n)))
				.forEach(nodeQueue::add);
	}

}
