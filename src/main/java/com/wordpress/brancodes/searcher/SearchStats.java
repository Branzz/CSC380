package com.wordpress.brancodes.searcher;

/**
 * Store final data from a search.
 */
public record SearchStats(int length, int cost, int time, int space) {

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		appendWithMargin(sB, 6, String.valueOf(length));
		appendWithMargin(sB, 6, String.valueOf(cost));
		appendWithMargin(sB, 6, String.valueOf(time));
		appendWithMargin(sB, 6, String.valueOf(space));
		return sB.toString();
	}

	private static void appendWithMargin(final StringBuilder sB, final int spaceWidth, final String str) {
		sB.append(" ".repeat(spaceWidth - str.length())).append(str);
	}

}
