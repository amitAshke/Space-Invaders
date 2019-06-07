package game.levels;

import collision.AlienFormation;
import collision.Shields;
import visuals.sprites.Sprite;

/**
 * The interface is used for all of the levels.
 */
public interface LevelInformation {
    /**
     * The function returns the ship's speed.
     *
     * @return the ship's speed.
     */
    int shipSpeed();

    /**
     * The function returns the ship's width.
     *
     * @return the ship's width.
     */
    int shipWidth();

    /**
     * The function returns the level's name.
     *
     * @return the level's name.
     */
    String levelName();

    /**
     * The function returns the level's background.
     *
     * @return the level's background.
     */
    Sprite getBackground();

    /**
     * The function returns the level's alien formation.
     *
     * @return the level's alien formation.
     */
    AlienFormation alienFormation();

    /**
     * The function returns the level's shields.
     *
     * @return the level's shields.
     */
    Shields shields();

    /**
     * The function return the number of aliens needed to be removes in the level.
     *
     * @return the number of aliens needed to be removes in the level.
     */
    int numberOfAliensToRemove();
}