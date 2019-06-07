package collision;

import geometry.Rectangle;

/**
 * The interface is used to make sure that every collidable object in the game has the same functions.
 */
public interface Collidable {
    /**
     * The function returns the rectangle that visually represents the collidable.
     *
     * @return the rectangle that visually represents the collidable.
     */
    Rectangle getCollisionRectangle();
}