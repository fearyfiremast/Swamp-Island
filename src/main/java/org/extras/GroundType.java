package org.extras;

/**
 * Mostly used to determine colour/sprite of base layer when drawing map but also affects player speed
 * @author          Xander Smith
 * @version         1.0
 */
public enum GroundType {
    path,
    bridge,
    marsh, // Slows down player
    water, // Synonymous with wall
    deepWater
}
