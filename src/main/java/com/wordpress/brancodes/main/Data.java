package com.wordpress.brancodes.main;

import java.awt.*;

public class Data {

	public final static boolean PRINT_NODES = false;

	public final static int SIZE = 3;

	public final static int[][] GOAL = new int[][] {
			{ 1, 2, 3 },
			{ 8, 0, 4 },
			{ 7, 6, 5 }
	};

	public final static Point[] GOAL_POINTS = new Point[SIZE * SIZE];

	static {
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				GOAL_POINTS[GOAL[i][j]] = new Point(j, i); // p[i] is where i is on GOAL
	}

	public static final int[][][] BOARD_CONFIGS = new int[][][] {
			{{ 1, 3, 4 },
			 { 8, 6, 2 },
			 { 7, 0, 5 }},
			{{ 2, 8, 1 },
			 { 0, 4, 3 },
			 { 7, 6, 5 }},
			{{ 5, 6, 7 },
			 { 4, 0, 8 },
			 { 3, 2, 1 }}
	};

	public static final String[] BOARD_DIFFICULTIES = new String[]
			{ "easy", "medium", "hard" };

}
