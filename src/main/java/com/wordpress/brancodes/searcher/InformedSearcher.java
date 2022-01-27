package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Stream;

public class InformedSearcher<T extends Node> extends Searcher<T> {

	private final Function<T, Integer> evaluationFunction;
	private final Function<T, Integer> costFunction;
	private final Function<T, Integer> heuristicFunction;

	public InformedSearcher(String alias,
							final Function<T, Integer> heuristicFunction,
							final Function<T, Integer> costFunction,
							final Function<T, Integer> evaluationFunction) {
		super(alias);
		this.heuristicFunction = heuristicFunction;
		this.costFunction = costFunction;
		this.evaluationFunction = evaluationFunction;
		nodeQueue = new PriorityQueue<>(Comparator.comparing(T::evaluationFunction));
	}

	/**
	 * A* shortcut constructor
	 */
	public InformedSearcher(String alias, final Function<T, Integer> costFunction, final Function<T, Integer> heuristicFunction) {
		this(alias, heuristicFunction, costFunction, node -> node.costFunction() + node.heuristicFunction());
	}

	@Override
	protected void queueNodes(final Stream<T> children) {
		children.peek(n -> n.setGoalCost(heuristicFunction.apply(n)))
				.peek(n -> n.setEvaluationCost(costFunction.apply(n)))
				.peek(n -> n.setEvaluationCost(evaluationFunction.apply(n)))
				.forEach(nodeQueue::add);
	}

}
