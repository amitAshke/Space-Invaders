package collision.listeners;

import collision.Projectile;

/**
 * An interface for different classes that do specific things if a collision between an object and a projectile
 * occurred.
 */
public interface HitListener {
    /**
     * The function perform a specific task according to the class that implements the "HitListener" interface.
     *
     * @param beingHit the object that is being hit.
     * @param hitter the projectile the hit.
     */
    void hitEvent(Object beingHit, Projectile hitter);
}