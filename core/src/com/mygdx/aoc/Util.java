package com.mygdx.aoc;

import com.badlogic.gdx.graphics.Color;

/**
 * Class with useful general use methods
 */
public class Util {
    /**
     * Generates a random Color with given alpha
     *
     * @param alpha
     * @return random Color
     */
    public static Color randomColor(float alpha) {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), alpha);
    }

    /**
     * Generates a random Color with alpha 1
     * @return random Color
     */
    public static Color randomColor() {
        return randomColor(1);
    }
}
