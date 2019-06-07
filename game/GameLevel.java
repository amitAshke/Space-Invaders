package game;

import collision.Projectile;
import collision.Ship;
import collision.Velocity;
import collision.listeners.ProjectileRemover;
import game.levels.LevelInformation;
import visuals.Animation;
import visuals.AnimationRunner;
import visuals.ingameInformation.LevelIndicator;
import visuals.ingameInformation.LivesIndicator;
import visuals.ingameInformation.ScoreIndicator;
import visuals.sprites.SpriteCollection;
import visuals.PauseScreen;
import visuals.CountdownAnimation;
import visuals.sprites.Sprite;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import geometry.Point;
import geometry.Rectangle;
import collision.Collidable;

import java.awt.Color;

/**
 * The class represents the two top actions that are performed to run the game. Namely to initialize the game and to
 * run it.
 */
public class GameLevel implements Animation {
    private int windowWidth;
    private int windowHeight;
    private static final int BORDER_WIDTH = 15;
    private static final int INFO_WIDTH = 20;
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private GUI gui;
    private KeyboardSensor keyboard;
    private Counter remainingAliens = new Counter(0);
    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private boolean running;

    /**
     * A constructor function that set values for the "GameLevel" variable.
     *
     * @param keyboardSensor  the variable to be set as the "KeyboardSensor".
     * @param animationRunner the variable to be set as the "AnimationRunner".
     * @param width           the numeric value to be set as the width of the game's window.
     * @param height          the numeric value to be set as the height of the game's window.
     * @param gameGui         gameGui the variable to best as the "GUI".
     * @param gameScore       the variable to be set as the game's score "Counter".
     * @param livesLeft       the variable to be set as the game's lives "Counter".
     */
    public GameLevel(KeyboardSensor keyboardSensor, AnimationRunner animationRunner, int width, int height, GUI
            gameGui, Counter gameScore, Counter livesLeft) {
        this.keyboard = keyboardSensor;
        this.runner = animationRunner;
        this.windowWidth = width;
        this.windowHeight = height;
        this.gui = gameGui;
        this.score = gameScore;
        this.numberOfLives = livesLeft;
    }

    /**
     * The function returns the game's window height.
     *
     * @return the game's window height.
     */
    public int getWindowHeight() {
        return this.windowHeight;
    }

    /**
     * The function returns the game's window width.
     *
     * @return the game's window width.
     */
    public int getWindowWidth() {
        return this.windowWidth;
    }

    /**
     * The function returns the game's borders width.
     *
     * @return the game's borders width.
     */
    public int getBorderWidth() {
        return BORDER_WIDTH;
    }

    /**
     * The function returns the game's information tab's width.
     *
     * @return the game's information tab's width.
     */
    public int getInfoWidth() {
        return this.INFO_WIDTH;
    }

    /**
     * The function returns the "GameEnvironment" variable.
     *
     * @return the "GameEnvironment" variable.
     */
    public GameEnvironment getGameEnvironment() {
        return this.environment;
    }

    /**
     * The function return the "Counter" variable for the remaining block.
     *
     * @return the "Counter" variable for the remaining block variable.
     */
    public Counter getRemainingAliens() {
        return remainingAliens;
    }

    /**
     * The function returns the "Counter" variable for the game's score.
     *
     * @return the "Counter" variable for the game's score.
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * The function return the "Counter" variable for the remaining lives.
     *
     * @return the "Counter" variable for the remaining lives.
     */
    public Counter getNumberOfLives() {
        return numberOfLives;
    }

    /**
     * The function returns the "visuals.sprites.SpriteCollection" member of the "game.GameLevel" class.
     *
     * @return the "visuals.sprites.SpriteCollection" member of the "game.GameLevel" class.
     */
    public SpriteCollection getSpriteCollection() {
        return this.sprites;
    }

    /**
     * The function returns the "game.GameEnvironment" member of the "game.GameLevel" class.
     *
     * @return the "game.GameEnvironment" member of the "game.GameLevel" class.
     */
    public GameEnvironment getEnvironment() {
        return environment;
    }

    /**
     * The function return the opposite value of the boolean value of the "GameLevel" variable.
     *
     * @return the opposite value of the boolean value of the "GameLevel" variable.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * The function perform everything is needed in one frame, namely it pauses the game if the player presses 'p',
     * notify all of the sprites that a unit of time has passed, draw all of the sprites and show the image.
     *
     * @param surface a "DrawSurface" variable used to draw and show all of the sprites.
     * @param dt a unit of time.
     */
    public void doOneFrame(DrawSurface surface, double dt) {
        final int framesPerSecond = 60;
        if (this.keyboard.isPressed("p")) {
            this.runner.run(framesPerSecond, new PauseScreen(keyboard), this.keyboard, this.keyboard.SPACE_KEY);
        }
         this.sprites.notifyAllTimePassed(dt);
        this.sprites.drawAllOn(surface);
        gui.show(surface);
    }

    /**
     * The function initializes the game's objects and their properties.
     */
    public void initialize() {
        final Point livesPosition = new Point(windowWidth / 4, BORDER_WIDTH * 2 / 3);
        final LivesIndicator livesInfo = new LivesIndicator(livesPosition, this.numberOfLives);
        final Point scorePosition = new Point(this.windowWidth * 2 / 4, BORDER_WIDTH * 2 / 3);
        final ScoreIndicator scoreInfo = new ScoreIndicator(scorePosition, this.score);
        this.sprites.addSprite(new Rectangle(new Point(0, 0), windowWidth, INFO_WIDTH, Color.WHITE));
        this.sprites.addSprite(scoreInfo);
        this.sprites.addSprite(livesInfo);
    }

    /**
     * The function initializes the paddle and it's properties. Also, it runs the game itself on an infinite "while"
     * loop.
     *
     * @param level the level which the player play in the turn.
     * @return true or false depending on whether or not the player is alive.
     */
    public boolean playOneTurn(LevelInformation level) {
        final int framesPerSecond = 60;
        final double dt = 1.0 / framesPerSecond;
        final Point shipStartingPoint = new Point(windowWidth / 2 - level.shipWidth() / 2, windowHeight
                - BORDER_WIDTH);
        final int millisecondsPerFrame = 1000 / framesPerSecond;
        this.running = true;
        Sleeper sleeper = new Sleeper();
        final Point levelNamePosition = new Point(this.windowWidth * 3 / 4, BORDER_WIDTH * 2 / 3);
        final LevelIndicator levelInfo = new LevelIndicator(levelNamePosition, level.levelName());
        this.getSpriteCollection().addSprite(levelInfo);
        Ship player = new Ship(shipStartingPoint, level.shipWidth(), BORDER_WIDTH, level.shipSpeed());
        player.setMovementLimits(0, windowWidth);
        player.addToGame(this);
        player.setGui(this.gui);
        CountdownAnimation countdown = new CountdownAnimation(3, 3, this.sprites);
        this.runner.run(1, countdown, this.keyboard, null);
        while (!shouldStop()) {
            long startTime = System.currentTimeMillis();
            DrawSurface surface = gui.getDrawSurface();
            if (player.isCanShoot() && this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                Projectile projectile = new Projectile(player.shootFrom(), 2, Color.WHITE, new Velocity(0, -600),
                        environment);
                projectile.addHitListener(new ProjectileRemover(this));
                sprites.addSprite(projectile);
                player.reload();
            }
            if (level.alienFormation().isCanShoot()) {
                Projectile projectile = new Projectile(level.alienFormation().shootFrom(), 3, Color.RED,
                        new Velocity(0, 200), environment);
                projectile.addHitListener(new ProjectileRemover(this));
                sprites.addSprite(projectile);
                level.alienFormation().reload();
            }
            doOneFrame(surface, dt);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (remainingAliens.getValue() != 0 && level.alienFormation().lowestPoint() >= level.shields()
                    .invasionPoint()) {
                player.notifyHit(new Projectile());
            }
            if (remainingAliens.getValue() == 0 || !player.isAlive()) {
                this.running = false;
            }
        }
        removeRemainingProjectiles();
        boolean isWonRound = player.isAlive();
        player.removeFromGame(this);
        return isWonRound;
    }

    /**
     * The function remove a collidable from the list of collidables in the "GameLevel" variable's "GameEnvironment"
     * value.
     *
     * @param c a collidable to be removed from the list of collidables in the "GameLevel" variable's "GameEnvironment"
     *          value.
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollidables().remove(c);
    }

    /**
     * The function remove a sprite from the list of sprites in the "GameLevel" variable's "SpriteCollevtion" value.
     *
     * @param s a sprite to be removed from the list of sprites in the "GameLevel" variable's "SpriteCollevtion" value.
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSprites().remove(s);
    }

    /**
     * The function removes the remaining projectile from the game after the player has lost a life.
     */
    public void removeRemainingProjectiles() {
        int spriteIndex = sprites.getSprites().size() - 1;
        while (sprites.getSprites().get(spriteIndex) instanceof Projectile) {
            sprites.getSprites().remove(spriteIndex);
            spriteIndex--;
        }
    }
}
