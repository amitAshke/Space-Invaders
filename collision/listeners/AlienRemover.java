package collision.listeners;

import collision.Alien;
import collision.AlienFormation;
import collision.Projectile;
import game.Counter;
import game.GameLevel;

/**
 * A class the represents a "HitListener" that removes an alien from the game.
 */
public class AlienRemover implements HitListener {
    private GameLevel gameLevel;
    private AlienFormation alienFormation;
    private Counter remainingAliens;

    /**
     * A constructor function that sets the "GameLevel" and "Counter" values.
     *
     * @param gameLevel1 a "GameLevel" variable from which the alien is removed.
     * @param alienFormation1 a "AlienFormation" variable for the alien to be removed from.
     * @param aliens a Counter containing the number of balls remaining.
     */
    public AlienRemover(GameLevel gameLevel1, AlienFormation alienFormation1, Counter aliens) {
        this.gameLevel = gameLevel1;
        this.alienFormation = alienFormation1;
        this.remainingAliens = aliens;
    }

    /**
     * The function removes the alien that is being hit by a projectile.
     *
     * @param beingHit the alien that is being hit.
     * @param hitter the projectile that hit.
     */
    public void hitEvent(Object beingHit, Projectile hitter) {
        remainingAliens.decrease(1);
        alienFormation.removeAlien((Alien) beingHit);
        ((Alien) beingHit).removeFromGame(gameLevel);
    }
}
