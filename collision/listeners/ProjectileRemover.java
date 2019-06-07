package collision.listeners;

import game.GameLevel;
import collision.Projectile;

/**
 * The class represents a "HitListener" that removes a projectile from the game once it got hit.
 */
public class ProjectileRemover implements HitListener {
    private GameLevel gameLevel;

    /**
     * A constructor function that sets the "GameLevel" and "Counter" values.
     *
     * @param g a "GameLevel" variable to be set to remove the projectile from.
     */
    public ProjectileRemover(GameLevel g) {
        this.gameLevel = g;
    }

    /**
     * The function removes the projectile that hit the block from the game.
     *
     * @param beingHit the object that is being hit.
     * @param hitter the projectile that hit.
     */
    public void hitEvent(Object beingHit, Projectile hitter) {
        hitter.removeFromGame(this.gameLevel);
    }
}
