package collision.listeners;

import collision.Projectile;

/**
 * A class the represents a "HitListener" that mark the ship as "dead".
 */
public class ShipHitListener implements HitListener {
    private boolean shipAlive = true;

    /**
     * The function returns the status of the ship. Whether it's alive and dead.
     *
     * @return the status of the ship.
     */
    public boolean isShipAlive() {
        return shipAlive;
    }

    /**
     * change the ship's statues in case it's been hit.
     *
     * @param beingHit the object that is being hit.
     * @param hitter the projectile the hit.
     */
    public void hitEvent(Object beingHit, Projectile hitter) {
        shipAlive = false;
    }
}
