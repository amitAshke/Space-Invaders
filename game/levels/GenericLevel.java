package game.levels;

import collision.AlienFormation;
import collision.Shields;
import collision.Velocity;
import collision.listeners.HitListener;
import game.GameLevel;
import visuals.levelsBackgrounds.SolidColorBackground;
import visuals.sprites.Sprite;

import java.awt.Color;

/**
 * The class represents a generic level class.
 */
public class GenericLevel implements LevelInformation {
    private String levelName;
    private int shipSpeed;
    private int shipWidth;
    private int numberOfAliensToRemove;
    private Sprite background;
    private Shields shields;
    private AlienFormation alienFormation;

    /**
     * A constructor function that build a generic level in the game.
     *
     * @param roundNum the number of the round.
     * @param alienVelocity the aliens' velocity.
     */
    public GenericLevel(String roundNum, Velocity alienVelocity) {
        levelName = "Battle no. " + roundNum;
        shipSpeed = 500;
        shipWidth = 80;
        numberOfAliensToRemove = 50;
        background = new SolidColorBackground(Color.BLACK);
        shields = new Shields();
        alienFormation = new AlienFormation(alienVelocity);
    }

    /**
     * The function sets the level's name by using a "String" variable.
     *
     * @param name a "String" variable containing the name.
     */
    public void setName(String name) {
        this.levelName = name;
    }

    /**
     * The function sets the level's paddle speed by using a "int" variable.
     *
     * @param speedOfShip an "int" variable containing the ship's speed.
     */
    public void setShipSpeed(int speedOfShip) {
        this.shipSpeed = speedOfShip;
    }

    /**
     * The function sets the level's ship width by using a "int" variable.
     *
     * @param widthOfShip an "int" variable containing the ship's width.
     */
    public void setShipWidth(int widthOfShip) {
        this.shipWidth = widthOfShip;
    }

    /**
     * the function sets the number of aliens in the level by using an "int" variable.
     *
     * @param numOfAliens an "int" variable containing the number of aliens in the level.
     */
    public void setNumberOfBlocksToRemove(int numOfAliens) {
        this.numberOfAliensToRemove = numOfAliens;
    }

    /**
     * The function sets the background in the level by using a "Sprite" variable.
     *
     * @param levelBackground a "sprite" variable containing the background of the level.
     */
    public void setBackground(Sprite levelBackground) {
        this.background = levelBackground;
    }

    /**
     * The function returns the ship's speed.
     *
     * @return the ship's speed.
     */
    public int shipSpeed() {
        return this.shipSpeed;
    }

    /**
     * The function returns the ship's width.
     *
     * @return the ship's width.
     */
    public int shipWidth() {
        return this.shipWidth;
    }

    /**
     * The function returns the level's name.
     *
     * @return the level's name.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * The function returns the level's background.
     *
     * @return the level's background.
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * The function returns the level's alien formation.
     *
     * @return the level's alien formation.
     */
    public AlienFormation alienFormation() {
        return alienFormation;
    }

    /**
     * The function returns the level's shields.
     *
     * @return the level's shields.
     */
    public Shields shields() {
        return shields;
    }

    /**
     * The function return the number of aliens needed to be removes in the level.
     *
     * @return the number of aliens needed to be removes in the level.
     */
    public int numberOfAliensToRemove() {
        return this.numberOfAliensToRemove;
    }

    /**
     * The function adds a "HitListener" variable to the shields.
     *
     * @param hitListener the "HitListener" variable that is added to shields.
     */
    public void addHitlistenerToShields(HitListener hitListener) {
        shields.addHitlistener(hitListener);
    }

    /**
     * The function adds a "HitListener" variable to the aliens.
     *
     * @param hitListener the "HitListener" variable that is added to aliens.
     */
    public void addHitlistenerToAliens(HitListener hitListener) {
        alienFormation.addHitListener(hitListener);
    }

    /**
     * The function adds the blocks and the aliens to the game's collidables list.
     *
     * @param gameLevel a "GameLevel" variable to which the blocks and the aliens are added as collidables.
     */
    public void addCollidables(GameLevel gameLevel) {
        shields.addCollidables(gameLevel);
        alienFormation.addCollidable(gameLevel);
    }

    /**
     * The function adds the shields and the alien formation to the game as sprites.
     *
     * @param gameLevel a "GameLevel" variable to which the shields and the alien formation are added as sprites.
     */
    public void addSprites(GameLevel gameLevel) {
        gameLevel.getSpriteCollection().addSprite(shields);
        gameLevel.getSpriteCollection().addSprite(alienFormation);
    }
}
