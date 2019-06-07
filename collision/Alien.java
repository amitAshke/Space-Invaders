package collision;

import biuoop.DrawSurface;
import collision.listeners.HitListener;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import visuals.sprites.Sprite;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class represents an alien in the game.
 */
public class Alien implements Collidable, Sprite, HitNotifier {
    private Point inFormation;
    private Image image;
    private Rectangle hitbox;
    private Point upperleft;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * A constructor function that creates and alien based on the point received and it's position in the formation.
     *
     * @param upperleft1 a "Point" variable used to locate the alien.
     * @param inFormation1 a "Point" variable used to know where the alien is in the formation.
     */
    public Alien(Point upperleft1, Point inFormation1) {
        this.upperleft = upperleft1;
        this.hitbox = new Rectangle(upperleft, 40, 30);
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResource("enemy.png"));
        } catch (IOException e) {
            System.out.println("Error with alien image.");
        }
        this.inFormation = inFormation1;
    }

    /**
     * The function is used to reposition an alien.
     *
     * @param upperleft1 a "Point" variable which will determine the alien's new position.
     */
    public void reposition(Point upperleft1) {
        this.upperleft = upperleft1;
        this.hitbox = new Rectangle(upperleft, 40, 30);
    }

    /**
     * The function returns the "Rectangle" value of the alien.
     *
     * @return the "Rectangle" value of the alie.
     */
    public Rectangle getCollisionRectangle() {
        return this.hitbox;
    }

    /**
     * The function draws the alien on screen.
     *
     * @param surface a "DrawSurface" variable used to draw the alien.
     */
    public void drawOn(DrawSurface surface) {
        surface.drawImage((int) upperleft.getX(), (int) upperleft.getY(), this.image);
    }

    /**
     * A function that's informs the alien the a time unit represented by "dt" has passed.
     *
     * @param dt a unit of time.
     */
    public void timePassed(double dt) {
    }

    /**
     * The function moves the alien downwards.
     */
    public void moveDown() {
        this.upperleft = new Point(upperleft.getX(), upperleft.getY() + hitbox.getHeight() + 10);
        this.hitbox = new Rectangle(upperleft, 40, 30);
    }

    /**
     * The function moves the alien horizontaly based on the velocity it receives and the amount of time passed.
     *
     * @param velocity a "Velocity" variable indicating the velocity.
     * @param dt a unit of time.
     */
    public void moveHorizontal(Velocity velocity, double dt) {
        this.upperleft = new Point(upperleft.getX() + velocity.getDx() * dt, upperleft.getY());
        this.hitbox = new Rectangle(upperleft, 40, 30);
    }

    /**
     * The function adds a "HitListener" variable to the alien's list.
     *
     * @param hl a "HitListener" variable to be added to a list.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * The function removes a "HitListener" variable to the alien's list.
     *
     * @param hl a "HitListener" variable to be added to a list.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * The function notify all of the alien's "HitListener" variables the it was hit.
     *
     * @param hitter the projectile that collided with the alien.
     */
    public void notifyHit(Projectile hitter) {
        for (HitListener hl : this.hitListeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * The function removes the alien from the game.
     *
     * @param gameLevel the "GameLevel" variable from which the alien is removed.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
    }

    /**
     * The function returns the alien's position in the formation.
     *
     * @return the alien's position in the formation.
     */
    public Point getInFormation() {
        return inFormation;
    }
}
