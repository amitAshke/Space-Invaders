package collision;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collision.listeners.ShipHitListener;
import geometry.Rectangle;
import geometry.Point;

import java.awt.Color;

import visuals.sprites.Sprite;
import game.GameLevel;

/**
 * The class represents the ship in the game.
 */
public class Ship implements Sprite, Collidable {
    private GUI gui;
    private KeyboardSensor keyboard;
    private Rectangle hitbox;
    private Color color = Color.ORANGE;
    private int shipSpeed;
    private int leftLimit;
    private int rightLimit;
    private double timePassedSinceShot = 0;
    private ShipHitListener shipAlive = new ShipHitListener();
    private boolean canShoot = false;

    /**
     * A constructor function that applies values to the ship's rectangle, color and speed.
     *
     * @param upperLeft a "Point" variable to be set to the upper left point of the "Rectangle" variable.
     * @param width     a "double" variable to be set to the width of the "Rectangle" variable.
     * @param height    the a "double" variable to be set to  height of the "Rectangle" variable.
     * @param speed     an "int" variable to be set to the ship's speed.
     */
    public Ship(Point upperLeft, double width, double height, int speed) {
        this.hitbox = new Rectangle(upperLeft, width, height);
        this.shipSpeed = speed;
    }

    /**
     * A constructor function to set the ship's GUI, and set the ship's "KeyboardSensor" accordingly.
     *
     * @param gu to be set as the ship's GUI.
     */
    public void setGui(GUI gu) {
        this.gui = gu;
        this.keyboard = this.gui.getKeyboardSensor();
    }

    /**
     * The function sets the ship limits of movements.
     *
     * @param left  the left limit of the ship's movement.
     * @param right the right limit of the ship's movement.
     */
    public void setMovementLimits(int left, int right) {
        this.leftLimit = left;
        this.rightLimit = right;
    }

    /**
     * The function return true or false depending on whether or no the ship is alive.
     *
     * @return true or false depending on whether or no the ship is alive.
     */
    public boolean isAlive() {
        return shipAlive.isShipAlive();
    }

    /**
     * The function returns the "Rectangle" value of the ship.
     *
     * @return the "Rectangle" value of the ship.
     */
    public Rectangle getCollisionRectangle() {
        return this.hitbox;
    }

    /**
     * The function returns the speed of the ship.
     *
     * @return the speed of the ship.
     */
    public int getPaddleSpeed() {
        return this.shipSpeed;
    }

    /**
     * The function moves the ship to the left according to the it's speed and "Rectangle" values.
     *
     * @param dt a unit of time that has passed.
     */
    public void moveLeft(double dt) {
        // Checks if the ship won't reach the left border block in the next step.
        if (this.hitbox.getUpperLeft().getX() - this.shipSpeed * dt > this.leftLimit) {
            // Move the ship to the left according to it's own speed.
            this.hitbox.setUpperLeft(new Point(this.hitbox.getUpperLeft().getX() - this.shipSpeed * dt,
                    this.hitbox.getUpperLeft().getY()));
        } else {
            // Otherwise place the ship where it touches the left border block
            this.hitbox.setUpperLeft(new Point(this.leftLimit, this.hitbox.getUpperLeft().getY()));
        }

    }

    /**
     * The function moves the ship to the right according to the it's speed and "Rectangle" values.
     *
     * @param dt a unit of time that has passed.
     */
    public void moveRight(double dt) {
        // The upper right corner point of the ship.
        Point upperRight = new Point(this.hitbox.getUpperLeft().getX() + this.hitbox.getWidth(),
                this.hitbox.getUpperLeft().getY());
        // Checks if the ship won't reach the right border block in the next step.
        if (upperRight.getX() + this.shipSpeed * dt < this.rightLimit) {
            // Move the ship to the right according to it's own speed.
            this.hitbox.setUpperLeft(new Point(this.hitbox.getUpperLeft().getX() + this.shipSpeed * dt,
                    this.hitbox.getUpperLeft().getY()));
        } else {
            // Otherwise place the ship where it touches the right border block
            this.hitbox.setUpperLeft(new Point(this.rightLimit - this.hitbox.getWidth(), this.hitbox.getUpperLeft()
                    .getY()));
        }
    }

    /**
     * This function is used to inform the ship that a unit of time has passed. According to the button press on
     * the keyboard the function will wither tell the ship to move right or left.
     *
     * @param dt a unit of time that has passed.
     */
    public void timePassed(double dt) {
        // Checks if the "left arrow key" is pressed.
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            // If so than activate the function that moves the ship left.
            moveLeft(dt);
            // Otherwise checks if th "right arrow key" is pressed.
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            // If so than activate the function that moves the ship right.
            moveRight(dt);
        }
        timePassedSinceShot += dt;
        if (timePassedSinceShot >= 0.35) {
            canShoot = true;
        }
    }

    /**
     * The function draw the "Ship" variable that called for the function based on it's color and rectangle values.
     *
     * @param surface a "DrawSurface" variable used to draw the block on the screen.
     */
    public void drawOn(DrawSurface surface) {
        // Sets the "DrawSurface" variable's color to the ship's
        surface.setColor(this.color);
        // Draw the rectangle that visually represents the ship.
        surface.fillRectangle((int) this.hitbox.getUpperLeft().getX(), (int) this.hitbox.getUpperLeft().getY(),
                (int) this.hitbox.getWidth(), (int) this.hitbox.getHeight());
    }

    /**
     * The function checks if two number are different by 0.001 and returns a boolean variable accordingly.
     *
     * @param num1 the first number in the comparison.
     * @param num2 the second number in the comparison.
     * @return true or false based on the comparison mentioned earlier.
     */
    public boolean isAlmostEqual(double num1, double num2) {
        return Math.abs(num2 - num1) < 0.001;
    }

    /**
     * The function adds the ship to the "game.GameLevel" sprites and collidables.
     *
     * @param gameLevel a "game.GameLevel" variable.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.getSpriteCollection().addSprite(this);
        gameLevel.getEnvironment().getCollidables().add(this);
    }

    /**
     * The function removes the ship from the game.
     *
     * @param gameLevel a "GameLevel" variable from which the ship is removed.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * The function notify the ship the it was hit and takes action accordingly.
     *
     * @param hitter the projectile that collided with the ship.
     */
    public void notifyHit(Projectile hitter) {
        shipAlive.hitEvent(this, hitter);
    }

    /**
     * The function returns the Point from which the ship is shooting.
     *
     * @return the Point from which the ship is shooting.
     */
    public Point shootFrom() {
        return new Point(hitbox.getUpperLeft().getX() + hitbox.getWidth() / 2, hitbox.getUpperLeft().getY() - 0.5);
    }

    /**
     * The function returns true or false depending on whether or not the ship can shoot.
     *
     * @return true or false depending on whether or not the ship can shoot.
     */
    public boolean isCanShoot() {
        return canShoot;
    }

    /**
     * The function resets certain value after the ship shot a projectile.
     */
    public void reload() {
        timePassedSinceShot = 0;
        canShoot = false;
    }
}
