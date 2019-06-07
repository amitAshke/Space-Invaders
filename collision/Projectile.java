package collision;

import game.GameLevel;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import collision.listeners.HitListener;
import geometry.Line;
import geometry.Point;
import visuals.sprites.Sprite;
import game.GameEnvironment;

/**
 * The class represents a projectile that is either shot by the player or by the aliens.
 */
public class Projectile implements Sprite, HitNotifier {
    private Point center;
    private int size;
    private Color color;
    private Velocity velocity;
    private Line trajectory;
    private GameEnvironment environment;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * An empty constructor function to create a temporary projectile.
     */
    public Projectile() { }

    /**
     * A constructor function that assigns the 3 basic variables in order.
     *
     * @param center1 a "Point" variable representing the center of the ball.
     * @param size1 an "int" variable representing the projectile's size.
     * @param color1 a "Color" variable representing the projectile's color.
     * @param velocity1 a "Velicity" variable representing the projectile velocity.
     * @param environment1 a "GameEnvironment" variable containing the object the projectile can collide with.
     */
    public Projectile(Point center1, int size1, Color color1, Velocity velocity1, GameEnvironment environment1) {
        this.center = center1;
        this.size = size1;
        this.color = color1;
        this.velocity = velocity1;
        this.environment = environment1;
    }

    /**
     * A constructor function that assign the ball's velocity. The function also sets the ball's trajectory
     * according to the new velocity.
     *
     * @param velocity1 a "Velocity" variable to be assigned for the ball.
     * @param dt a unit of time.
     */
    public void setVelocity(Velocity velocity1, double dt) {
        this.velocity = velocity1;
        this.setTrajectory(dt);
    }

    /**
     * A constructor function that assigns the ball's velocity using 2 "double" variables. The function also sets the
     * ball's trajectory according to the new velocity.
     *
     * @param dx a "double" variable representing the horizontal velocity.
     * @param dy a "double" variable representing the vertical velocity.
     * @param dt a unit of time.
     */
    public void setVelocity(double dx, double dy, double dt) {
        this.velocity = new Velocity(dx, dy);
        this.setTrajectory(dt);
    }

    /**
     * The function sets a new trajectory according to the ball's position and velocity.
     *
     * @param dt a unit of time.
     */
    public void setTrajectory(double dt) {
        this.trajectory = new Line(this.center.getX(), this.center.getY(), this.center.getX()
                + this.velocity.getDx() * dt, this.center.getY() + this.velocity.getDy() * dt);
    }

    /**
     * A construct function that assigns the game environment of the ball.
     *
     * @param enviro a "game.GameEnvironment" variableto be assigned for the ball.
     */
    public void setGameEnvironment(GameEnvironment enviro) {
        this.environment = enviro;
    }

    /**
     * The function adds a "HitListener" variable to the ball's list.
     *
     * @param hl a "HitListener" variable to be added to a list.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * The function removes a "HitListener" variable to the ball's list.
     *
     * @param hl a "HitListener" variable to be added to a list.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * The function returns the 'x' value of the center point.
     *
     * @return the 'x' value of the center point.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * The function returns the 'y' value of the center point.
     *
     * @return the 'y' value of the center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * The function returns the center point.
     *
     * @return the center point.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * The function returns ball' radius length.
     *
     * @return the ball' radius length.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * The function returns the ball's "Velocity" variable.
     *
     * @return the ball's "Velocity" variable.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * The function returns the ball's trajectory variable.
     *
     * @return the ball's trajectory variable.
     */
    public Line getTrajectory() {
        return this.trajectory;
    }

    /**
     * The function sets an new values to the "Point" variable representing the center of the ball according to it's
     * "Velocity" values and the the collidables in it's game environment.
     *
     * @param dt a unit of time.
     */
    public void moveOneStep(double dt) {
        this.setTrajectory(dt);
        // Get information regarding the next collision.
        CollisionInfo nextCollision = this.environment.getClosestCollision(this.trajectory);
            // Otherwise checks if there is no collision by the next step.
        if (nextCollision.collisionPoint() == null) {
            // Otherwise move the ball normally.
            this.center = this.velocity.applyToPoint(this.center, dt);
            // Set a new trajectory according to the ball's location and current velocity.
            this.setTrajectory(dt);
            //Otherwise checks if there is a collision in the next step.
        } else {
            // Move the ball's center just before the collision point.
            this.center = new Point(nextCollision.collisionPoint().getX() - 0.01 * this.velocity.getDx(),
                    nextCollision.collisionPoint().getY() - 0.01 * this.velocity.getDy());
            if (nextCollision.collisionObject() instanceof Block) {
                ((Block) nextCollision.collisionObject()).notifyHit(this);
                notifyHit(nextCollision.collisionObject());
            } else if (nextCollision.collisionObject() instanceof Ship) {
                ((Ship) nextCollision.collisionObject()).notifyHit(this);
                notifyHit(nextCollision.collisionObject());
            } else if (nextCollision.collisionObject() instanceof Alien) {
                if (this.velocity.getDy() < 0) {
                    ((Alien) nextCollision.collisionObject()).notifyHit(this);
                    notifyHit(nextCollision.collisionObject());
                } else {
                    this.center = this.velocity.applyToPoint(this.center, dt);
                    this.setTrajectory(dt);
                }
            }
        }
    }

    /**
     * The function draw the ball that activated this function.
     *
     * @param surface a "DrawSurface" variable that draw in a window according
     *                to the function it uses.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.size);
    }

    /**
     * The function removes the ball from the game by removing it's sprite from the "GameLevel" variable's
     * "SpriteCollection".
     *
     * @param gameLevel a "GameLevel" variable from which the ball is removed.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    /**
     * The function notify all of the ball's listeners that a collision has occurred.
     *
     * @param beingHit The object that the projectile collided with.
     */
    public void notifyHit(Object beingHit) {
        for (HitListener hl:this.hitListeners) {
            hl.hitEvent(beingHit, this);
        }
    }

    /**
     * The function activates as a single unit of time is passes. The unit of time is based on the game itself, not
     * the ball.
     *
     * @param dt a unit of time.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }
}
