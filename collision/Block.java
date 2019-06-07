package collision;

import biuoop.DrawSurface;
import collision.listeners.HitListener;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import visuals.sprites.Sprite;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * The class represents a block in the game.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private int[] position;
    private int width = 5;
    private int height = 5;
    private Rectangle hitbox;
    private List<HitListener> hitListeners = new ArrayList<>();
    private Point upperleft;

    /**
     * An empty constructor.
     */
    public Block() {
    }

    /**
     * A constructor function that receives some values and creates a "Block" variable accordingly.
     *
     * @param upperleft1 the upperleft point of the block.
     * @param shieldNum the shield the block is a part of.
     * @param row the row in the shield.
     * @param col the column in the shield.
     */
    public Block(Point upperleft1, int shieldNum, int row, int col) {
        this.upperleft = upperleft1;
        this.hitbox = new Rectangle(upperleft, width, height);
        position = new int[]{shieldNum, row, col};
    }

    /**
     * The function returns the block position in term of shields.
     *
     * @return the block position in term of shields
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * The function returns the block's width.
     *
     * @return the block's width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * The function returns the block's height.
     *
     * @return the block's height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * The function returns the "Rectangle" value of the block.
     *
     * @return the "Rectangle" value of the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.hitbox;
    }

    /**
     * The function draw the "BlockDefinitionReader" variable that called for the function based on it's color and
     * rectangle values. Additionally it draw either it's hit points value ot 'X' inside of the rectangle, based on
     * the value of it's hit points.
     *
     * @param surface a "DrawSurface" variable used to draw the block on the screen.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.CYAN);
        surface.fillRectangle((int) upperleft.getX(), (int) upperleft.getY(), width, height);
    }

    /**
     * This function is used to inform the block that a unit of time has passed. Currently none of the block values
     * are based on the time passed, therefore the function is empty.
     *
     * @param dt A unit of time that has passed.
     */
    public void timePassed(double dt) {
    }

    /**
     * The function adds a "HitListener" variable to the block's list.
     *
     * @param hl a "HitListener" variable to be added to a list.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * The function removes a "HitListener" variable to the block's list.
     *
     * @param hl a "HitListener" variable to be added to a list.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * The function notify all of the block's "HitListener" variables that a collision with the block occurred.
     *
     * @param hitter the projectile that collided with the block.
     */
    public void notifyHit(Projectile hitter) {
        for (HitListener hl : this.hitListeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * The function adds the block to the game as a collidable object and a sprite.
     *
     * @param gameLevel a "GameLevel" variable to which the block is added as a collidable object and a sprite.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.getSpriteCollection().addSprite(this);
        gameLevel.getEnvironment().addCollidable(this);
    }

    /**
     * The function removes the block from the game.
     *
     * @param gameLevel the "GameLevel" variable from which the block is removed.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
    }
}
