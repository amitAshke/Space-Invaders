package collision.listeners;

import collision.Block;
import collision.Projectile;
import collision.Shields;
import game.Counter;
import game.GameLevel;

/**
 * A class the represents a "HitListener" that removes a block from the game.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Shields shields;
    private Counter remainingBlocks;

    /**
     * A constrictor function.
     *
     * @param gameLevel1 a "GameLevel" variable from which the block is removed.
     * @param shields1             a "Shields" variable from which the block will be removed.
     * @param removedBlocks a "Counter" variable representing the number of blocks remaining in the level.
     */
    public BlockRemover(GameLevel gameLevel1, Shields shields1, Counter removedBlocks) {
        this.gameLevel = gameLevel1;
        this.shields = shields1;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * A function that activates when notified the a collision with a block that has the "BlockRemover" as a
     * listener, It removes the block from the game if the block ran out of hit points.
     *
     * @param beingHit the block that being hit.
     * @param hitter the projectile that hit.
     */
    public void hitEvent(Object beingHit, Projectile hitter) {
        remainingBlocks.decrease(1);
        shields.removeBlock((Block) beingHit);
        ((Block) beingHit).removeFromGame(gameLevel);
    }
}
