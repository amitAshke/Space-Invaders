package collision.listeners;

import collision.Alien;
import collision.Block;
import collision.Projectile;

/**
 * The class represents a "HitListener" that prints information regarding collisions.
 */
public class PrintHitListener implements HitListener {
    /**
     * The function prints information regarding a collision once it occurred.
     *
     * @param beingHit the object that is being hit.
     * @param hitter the projectile that hit.
     */
    public void hitEvent(Object beingHit, Projectile hitter) {
        if (beingHit instanceof Alien) {
            System.out.println("An alien was hit");
        } else if (beingHit instanceof Block) {
            System.out.println("A shield was hit");
        }
    }
}