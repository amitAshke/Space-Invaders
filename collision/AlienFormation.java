package collision;

import biuoop.DrawSurface;
import collision.listeners.HitListener;
import game.GameLevel;
import geometry.Point;
import visuals.sprites.Sprite;

import java.util.Random;

/**
 * The class represents the aliens' formation in the game.
 */
public class AlienFormation implements Sprite {
    private Alien[][] aliens = new Alien[5][10];
    private boolean moveRight = true;
    private Velocity velocity;
    private double timePassedSinceShot = 0;
    private boolean canShoot = false;

    /**
     * A constructor function the position the aliens and gives the formation it's initial speed.
     *
     * @param velocity1 the formation's initial speed.
     */
    public AlienFormation(Velocity velocity1) {
        this.velocity = velocity1;
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                aliens[row][col] = (new Alien(new Point(50 + 50 * col, 50 + 40 * row), new Point(row, col)));
            }
        }
    }

    /**
     * returns the formation lowest point in term of alien position.
     *
     * @return a numerical value signifying the lowest point in the formation.
     */
    public int lowestPoint() {
        for (int row = 4; row >= 0; row--) {
            for (int col = 0; col < 10; col++) {
                if (aliens[row][col] != null) {
                    return (int) (aliens[row][col].getCollisionRectangle().getUpperLeft().getY() + aliens[row][col]
                            .getCollisionRectangle().getHeight());
                }
            }
        }
        return 600;
    }

    /**
     * The function resets the alien formation after the player has lost a life.
     *
     * @param velocity1 a "Velocity" variable used to return the alien formation to it's original speed.
     */
    public void reset(Velocity velocity1) {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                if (aliens[row][col] != null) {
                    aliens[row][col].reposition(new Point(50 + 50 * col, 50 + 40 * row));
                }
            }
        }
        this.velocity = velocity1;
        moveRight = true;
        timePassedSinceShot = 0;
        canShoot = false;
    }

    /**
     * The function add a "HitListener" variable to all of the aliens in the formation.
     *
     * @param hitListener a "HitListener" variable that is added to all of the aliens in the formation.
     */
    public void addHitListener(HitListener hitListener) {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                aliens[row][col].addHitListener(hitListener);
            }
        }
    }

    /**
     * The function draws all of the aliens in the formation.
     *
     * @param surface a "DrawSurface" variable used to draw all of the aliens.
     */
    public void drawOn(DrawSurface surface) {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                if (aliens[row][col] != null) {
                    aliens[row][col].drawOn(surface);
                }
            }
        }
    }

    /**
     * The function informs the formation that a unit of time has passed.
     *
     * @param dt a unit of time.
     */
    public void timePassed(double dt) {
        boolean broke = false;
        if (moveRight) {
            Alien rightMostAlien = null;
            for (int col = 9; col >= 0; col--) {
                for (int row = 0; row < 5; row++) {
                    if (aliens[row][col] != null) {
                        rightMostAlien = aliens[row][col];
                        broke = true;
                        break;
                    }
                }
                if (broke) {
                    break;
                }
            }
            if (rightMostAlien == null) {
                return;
            }
            double rightMostX = rightMostAlien.getCollisionRectangle().getUpperLeft().getX() + rightMostAlien
                    .getCollisionRectangle().getWidth() + velocity.getDx() * dt;
            if (rightMostX > 800) {
                moveDown();
                velocity = new Velocity(velocity.getDx() * (-1.1), 0);
                moveRight = false;
            } else {
                moveHorizontal(dt);
            }
        } else {
            Alien leftMostAlien = null;
            for (int col = 0; col < 10; col++) {
                for (int row = 0; row < 5; row++) {
                    if (aliens[row][col] != null) {
                        leftMostAlien = aliens[row][col];
                        broke = true;
                        break;
                    }
                }
                if (broke) {
                    break;
                }
            }
            if (leftMostAlien == null) {
                return;
            }
            double leftMostX = leftMostAlien.getCollisionRectangle().getUpperLeft().getX() + velocity.getDx() * dt;
            if (leftMostX < 0) {
                moveDown();
                velocity = new Velocity(velocity.getDx() * (-1.1), 0);
                moveRight = true;
            } else {
                moveHorizontal(dt);
            }
        }
        timePassedSinceShot += dt;
        if (timePassedSinceShot >= 0.5) {
            canShoot = true;
        }
    }

    /**
     * The function moves the alien formation down by a set value.
     */
    public void moveDown() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                if (aliens[row][col] != null) {
                    aliens[row][col].moveDown();
                }
            }
        }
    }

    /**
     * The function moves the alien formation horizontally based on it's velocity.
     *
     * @param dt a unit of time.
     */
    public void moveHorizontal(double dt) {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                if (aliens[row][col] != null) {
                    aliens[row][col].moveHorizontal(velocity, dt);
                }
            }
        }
    }

    /**
     * The function adds each alien to the game as a collidable object.
     *
     * @param gameLevel the "GameLevel" variable to which the aliens are added.
     */
    public void addCollidable(GameLevel gameLevel) {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 5; row++) {
                gameLevel.getEnvironment().addCollidable(aliens[row][col]);
            }
        }
    }

    /**
     * The function removes a specific alien from the formation.
     *
     * @param alien the alien that's being removes from the formation.
     */
    public void removeAlien(Alien alien) {
        aliens[(int) alien.getInFormation().getX()][(int) alien.getInFormation().getY()] = null;
    }

    /**
     * The function returns true or false based on whether or not the aliens can shoot.
     *
     * @return true or false based on whether or not the aliens can shoot.
     */
    public boolean isCanShoot() {
        return canShoot;
    }

    /**
     * The function picks a random column for the formation and returns a "Point" value based on the lowest alien in
     * the column.
     *
     * @return a "Point" value based on the lowest alien in a random column.
     */
    public Point shootFrom() {
        Random random = new Random();
        int row = 4;
        int randCol = random.nextInt(10);
        Alien tempAlien = aliens[row][randCol];
        while (tempAlien == null) {
            for (row = 3; row >= 0; row--) {
                if (aliens[row][randCol] != null) {
                    tempAlien = aliens[row][randCol];
                    break;
                }
            }
             if (row < 0) {
                randCol = random.nextInt(10);
                row = 4;
                tempAlien = aliens[row][randCol];
            }
        }

         return new Point(tempAlien.getCollisionRectangle().getUpperLeft().getX() + tempAlien.getCollisionRectangle()
                 .getWidth() / 2, tempAlien.getCollisionRectangle().getUpperLeft().getY() + tempAlien
                 .getCollisionRectangle().getHeight() + 0.5);
    }

    /**
     * The function resets some variables after the aliens shot a projectile.
     */
    public void reload() {
        timePassedSinceShot = 0;
        canShoot = false;
    }
}
