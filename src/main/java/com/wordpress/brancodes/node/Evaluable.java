package com.wordpress.brancodes.node;

public interface Evaluable {

	/**
	 * f(n)
	 */
	int evaluationFunction();

	/**
	 * g(n)
	 */
	int costFunction();

	/**
	 * h(n)
	 */
	int heuristicFunction();

	void setEvaluationCost(int evaluationCost);

	void setCostFunction(int evaluationCost);

	void setGoalCost(int goalCost);

}
