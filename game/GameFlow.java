package game;

import collision.Velocity;
import collision.listeners.AlienRemover;
import collision.listeners.BlockRemover;
import collision.listeners.PrintHitListener;
import collision.listeners.ScoreTrackingListener;
import game.levels.GenericLevel;
import visuals.AnimationRunner;
import visuals.EndgameScreen;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.DialogManager;
import visuals.HighScoresAnimation;

import java.io.File;
import java.io.IOException;

/**
 * The class is the one that's in control of the flow of the game from one level to the other.
 */
public class GameFlow {
    private static final int INFO_WIDTH = 15;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private GUI gui;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private HighScoresTable highscores;

    /**
     * A constructor function that sets the values for the "GameFlow" variable.
     *
     * @param ar the variable to be set as the "AnimationRunner".
     * @param ks the variable to be set as the "KeyboardSensor".
     * @param highscore the variable to be set as the "HighScoresTable".
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable highscore) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.highscores = highscore;
        this.gui = ar.getGui();
    }

    /**
     * The function runs the levels.
     */
    public void runLevels() {
        int roundNum = 1;
        Counter score = new Counter(0);
        Counter numberOfLives = new Counter(3);
        Velocity alienVelocity = new Velocity(60, 0);
        while (numberOfLives.getValue() > 0) {
            GenericLevel level = new GenericLevel(Integer.toString(roundNum), alienVelocity);
            GameLevel gameLevel = new GameLevel(this.keyboardSensor, this.animationRunner, WINDOW_WIDTH,
                    WINDOW_HEIGHT, this.gui, score, numberOfLives);
            final ScoreTrackingListener scoreTracker = new ScoreTrackingListener(score);
            final PrintHitListener printer = new PrintHitListener();
            final BlockRemover blockRemover = new BlockRemover(gameLevel, level.shields(), new Counter(level.shields()
                    .getAmount()));
            final AlienRemover alienRemover = new AlienRemover(gameLevel, level.alienFormation(), gameLevel
                    .getRemainingAliens());
            level.addHitlistenerToShields(printer);
            level.addHitlistenerToShields(blockRemover);
            level.addHitlistenerToAliens(printer);
            level.addHitlistenerToAliens(alienRemover);
            level.addHitlistenerToAliens(scoreTracker);
            gameLevel.getSpriteCollection().addSprite(level.getBackground());
            level.addCollidables(gameLevel);
            level.addSprites(gameLevel);
            gameLevel.initialize();
            gameLevel.getRemainingAliens().increase(level.numberOfAliensToRemove());
            while (gameLevel.getNumberOfLives().getValue() > 0 && gameLevel.getRemainingAliens().getValue() > 0) {
                boolean playerAlive = gameLevel.playOneTurn(level);
                if (!playerAlive) {
                    numberOfLives.decrease(1);
                }
                level.alienFormation().reset(alienVelocity);
            }
            if (gameLevel.getNumberOfLives().getValue() <= 0) {
                break;
            }
            alienVelocity = new Velocity(alienVelocity.getDx() * 1.1, 0);
            roundNum++;
        }
        this.animationRunner.run(60, new EndgameScreen(this.keyboardSensor, numberOfLives, score),
                keyboardSensor, keyboardSensor.SPACE_KEY);
        if (isScoreHigh(score)) {
            insertNewHighscore(score);
        }
        this.animationRunner.run(60, new HighScoresAnimation(highscores, keyboardSensor), keyboardSensor, keyboardSensor
                .SPACE_KEY);
    }

    /**
     * The function checks if the score at the end of the game is high enough to enter the highscore table.
     *
     * @param score the score at the end of the game.
     * @return true or false depending on whether or not the score is high enough to enter the highscore table.
     */
    public boolean isScoreHigh(Counter score) {
        return highscores.getRank(score.getValue()) != -1;
    }

    /**
     * The function give the player the option to write his name and insert his score allong with his name into the
     * highscore table.
     *
     * @param score the player's score.
     */
    public void insertNewHighscore(Counter score) {
        DialogManager dialog = gui.getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "");
        System.out.println(name);
        ScoreInfo newHighScore = new ScoreInfo(name, score.getValue());
        highscores.add(newHighScore);
        try {
            highscores.save(new File("highscore"));
        } catch (IOException saveError) {
            System.out.println("An error occurred while saving the file.");
        }
    }
}
