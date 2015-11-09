package com.mygdx.aoc;

import com.badlogic.gdx.graphics.Color;

public class Util {
    public static Color randomColor(float alpha) {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), alpha);
    }

    public static Color randomColor() {
        return randomColor(1);
    }
}
