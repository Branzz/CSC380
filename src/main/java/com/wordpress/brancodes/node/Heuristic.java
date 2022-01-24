package com.wordpress.brancodes.node;

public interface Heuristic {

	int evaluationFunction(); // f(n)

	int costFunction(); // g(n)

	int heuristicFunction(); // h(n)

}
