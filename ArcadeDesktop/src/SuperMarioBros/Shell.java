package SuperMarioBros;

import Engine.Spritesheet;

public class Shell extends Entity {

	private final static int[] states = { 1, 1, 1, 1 };
	private final static int[] frames = { 1, 1, 1, 1 };

	public Shell(Spritesheet spritesheet, float x, float y) {
		super(spritesheet, x, y, 16, 16, 2f, states, frames);
	}

}
