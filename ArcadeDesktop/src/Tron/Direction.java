package Tron;

public enum Direction {
	LEFT, RIGHT, UP, DOWN;

	public static int[] getValueFromDirection(Direction dir) {
		switch (dir) {
		case LEFT:
			return new int[] { -1, 0 };

		case RIGHT:
			return new int[] { 1, 0 };

		case UP:
			return new int[] { 0, -1 };

		case DOWN:
			return new int[] { 0, 1 };

		default:
			return new int[] { 0, 0 };
		}
	}

	public static Direction rotate90(Direction dir) {
		switch (dir) {
		case LEFT:
			return Direction.UP;

		case RIGHT:
			return Direction.DOWN;

		case UP:
			return Direction.RIGHT;

		case DOWN:
			return Direction.LEFT;

		default:
			return Direction.UP;
		}
	}

	public static Direction rotate270(Direction dir) {
		switch (dir) {
		case LEFT:
			return Direction.DOWN;

		case RIGHT:
			return Direction.UP;

		case UP:
			return Direction.LEFT;

		case DOWN:
			return Direction.RIGHT;

		default:
			return Direction.UP;
		}
	}

	public static Direction getOpposite(Direction dir) {
		switch (dir) {
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		default:
			return UP;
		}
	}
}
