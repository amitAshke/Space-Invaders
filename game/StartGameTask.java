package game;

import biuoop.KeyboardSensor;
import visuals.AnimationRunner;

/**
 * The class represents a task of starting to play the game.
 */
public class StartGameTask implements Task<Void> {
    private AnimationRunner runner;
    private KeyboardSensor sensor;
    private HighScoresTable table;

    /**
     * a constructor Function.
     *
     * @param animationRunner a "AnimationRunner" variable to create a "GameFlow" variable.
     * @param keyboardSensor a "KeyboardSensor" variable to create a "GameFlow" variable.
     * @param highScoresTable a "HighScoresTable" variable to create a "GameFlow" variable.
     */
    public StartGameTask(AnimationRunner animationRunner, KeyboardSensor keyboardSensor, HighScoresTable
            highScoresTable) {
        this.runner = animationRunner;
        this.sensor = keyboardSensor;
        this.table = highScoresTable;
    }

    /**
     * The function creates a "GameFlow" variable and a list of levels, Then the function runs the levels with the
     * "GameFlow" variable it created.
     *
     * @return null;
     */
    public Void run() {
        GameFlow gameFlow = new GameFlow(runner, sensor, table);
        gameFlow.runLevels();
        return null;
    }
}
