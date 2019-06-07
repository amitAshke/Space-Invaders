package collision.listeners;

import collision.Alien;
import collision.Projectile;
import game.Counter;

/**
 * The class represents a "HitListener" in order to keep track of the player's score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * A constructor function that sets the score.
     *
     * @param scoreCounter the score that's set for the "ScoreTrackingListener" variable.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * The function returns the score.
     *
     * @return the score.
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }

    /**
     * The function adds a number for the score according to whether or not the block is removed because of the
     * collision.
     *
     * @param beingHit the block that is being hit.
     * @param hitter the projectile that hit.
     */
    public void hitEvent(Object beingHit, Projectile hitter) {
        if (beingHit instanceof Alien && hitter.getVelocity().getDy() < 0) {
            currentScore.increase(100);
        }
    }
}
