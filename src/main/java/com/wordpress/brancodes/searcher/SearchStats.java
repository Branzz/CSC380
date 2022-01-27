package com.wordpress.brancodes.searcher;

import com.wordpress.brancodes.node.Node;

/**
 * store final data from a search.
 */
public record SearchStats(Node solution, int time, int space) {

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		appendWithMargin(sB, 5, String.valueOf(solution.getDepth()));
		appendWithMargin(sB, 4, String.valueOf(solution.costFunction()));
		appendWithMargin(sB, 7, String.valueOf(time));
		appendWithMargin(sB, 6, String.valueOf(space));
		return sB.toString();
	}

	private static void appendWithMargin(final StringBuilder sB, final int spaceWidth, final String str) {
		sB.append(" ".repeat(spaceWidth - str.length())).append(str);
	}

}
