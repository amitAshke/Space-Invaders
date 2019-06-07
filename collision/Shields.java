package collision;

import biuoop.DrawSurface;
import collision.listeners.HitListener;
import game.GameLevel;
import geometry.Point;
import visuals.sprites.Sprite;

/**
 * The class represents the shields that are in the game.
 */
public class Shields implements Sprite {
    private int numberOfShields = 3;
    private int widthofShield = 4;
    private int lengthOfShield = 30;
    private int amount = numberOfShields * widthofShield * lengthOfShield;
    private Block[][][] allShields = new Block[numberOfShields][widthofShield][lengthOfShield];

    /**
     * A constructor function that initializes the shields.
     */
    public Shields() {
        Block block = new Block();
        for (int shieldNum = 0; shieldNum < numberOfShields; shieldNum++) {
            for (int row = 0; row < widthofShield; row++) {
                for (int col = 0; col < lengthOfShield; col++) {
                    allShields[shieldNum][row][col] = new Block(new Point(120 + 200 * shieldNum + block.getWidth()
                            * col, 500 + block.getHeight() * row), shieldNum, row, col);
                }
            }
        }
    }

    /**
     * The function returns the number of block that make the shields remain.
     *
     * @return the number of block that make the shields remain.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * The function returns a set number to end the round if the aliens reach it.
     *
     * @return a set number to end the round if the aliens reach it.
     */
    public int invasionPoint() {
        return 500;
    }

    /**
     * The function adds an "HitListener" variable the the list in each block.
     *
     * @param hitListener a "HitListener" variable to be added to each block in the shields.
     */
    public void addHitlistener(HitListener hitListener) {
        for (int shieldNum = 0; shieldNum < numberOfShields; shieldNum++) {
            for (int row = 0; row < widthofShield; row++) {
                for (int col = 0; col < lengthOfShield; col++) {
                    allShields[shieldNum][row][col].addHitListener(hitListener);
                }
            }
        }
    }

    /**
     * The function draw the shields by drawing each block in it.
     *
     * @param surface a "DrawSurface" variable used to draw the shields.
     */
    public void drawOn(DrawSurface surface) {
        for (int shieldNum = 0; shieldNum < numberOfShields; shieldNum++) {
            for (int row = 0; row < widthofShield; row++) {
                for (int col = 0; col < lengthOfShield; col++) {
                    if (allShields[shieldNum][row][col] != null) {
                        allShields[shieldNum][row][col].drawOn(surface);
                    }
                }
            }
        }
    }

    /**
     * The function informs the shields that a unit of time has passed.
     *
     * @param dt a unit of time.
     */
    public void timePassed(double dt) {
        for (int shieldNum = 0; shieldNum < numberOfShields; shieldNum++) {
            for (int row = 0; row < widthofShield; row++) {
                for (int col = 0; col < lengthOfShield; col++) {
                    if (allShields[shieldNum][row][col] != null) {
                        allShields[shieldNum][row][col].timePassed(dt);
                    }
                }
            }
        }
    }

    /**
     * The function adds each block to the game as a collidable.
     *
     * @param gameLevel a "GameLevel" variable to which the blocks are added as collidables.
     */
    public void addCollidables(GameLevel gameLevel) {
        for (int shieldNum = 0; shieldNum < numberOfShields; shieldNum++) {
            for (int row = 0; row < widthofShield; row++) {
                for (int col = 0; col < lengthOfShield; col++) {
                    gameLevel.getEnvironment().addCollidable(allShields[shieldNum][row][col]);
                }
            }
        }
    }

    /**
     * The function removes a specific block from ther shields.
     *
     * @param block the block that is removed from the shields.
     */
    public void removeBlock(Block block) {
        allShields[block.getPosition()[0]][block.getPosition()[1]][block.getPosition()[2]] = null;
    }
}
