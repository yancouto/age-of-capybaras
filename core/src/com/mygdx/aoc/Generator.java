package com.mygdx.aoc;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Implements Generator logic.
 * Each generator can be upgrade by paying some amount of Capybaras, and it
 * generates some CPS. After some number of upgrades (most usually 100) you
 * get a boost for that particular Generator.
 * +----------------------+
 * |.Name......|   _____  |
 * |.Quantity..|  | $$$ | |
 * |.CPS.......|  |_____| |
 * +----------------------+
 */
public class Generator extends Widget {
    private Drawable pixel;
    private BitmapFont font;
    public final String name;
    private int currentLevel = 33;

    // TODO: Decide how generators will "scale"
    @Override
    public float getPrefWidth() {
        return 1080;
    }

    @Override
    public float getPrefHeight() {
        return 1920 * .125f;
    }

    private Color backColor, fillColor;

    public Generator(String name) {
        this.name = name;
        pixel = ResourceManager.skin.getDrawable("pixel");

        float a = (float) Math.random(), b = (float) Math.random(), c = (float) Math.random();
        float mult1 = 1.25f / (a + b + c), mult2 = 2.f / (a + b + c);
        backColor = new Color(a * mult1, b * mult1, c * mult1, 1);
        fillColor = new Color(a * mult2, b * mult2, c * mult2, 1);


        font = ResourceManager.getFont("goodDog", 80);

        Preferences prefs = ResourceManager.prefs;
        if (prefs.contains(name + "generator")) {
            // TODO: read generator info
        }
    }

    /**
     * Saves this generator's data
     *
     * @see #saveGame()
     */
    private void save() {
        // TODO: write generator info
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(backColor);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(fillColor);
        pixel.draw(batch, getX(), getY(), getWidth() * (currentLevel % 100) / 100.f, getHeight());
        float c2 = font.getLineHeight() / 2.f;
        float h4 = getHeight() / 4.f;
        font.draw(batch, "X CPS", getX() + 50, getY() + h4 + c2 - getHeight() / 20.f);
        font.draw(batch, String.valueOf(currentLevel), getX() + 50, getY() + h4 * 2.f + c2);
        font.draw(batch, name, getX() + 50, getY() + h4 * 3.f + c2 + getHeight() / 20.f);
    }

    /**
     * All generator instances
     */
    public static Generator[] generators;

    /**
     * Loads generators instances and initializes them
     *
     * @see ResourceManager#loadGame()
     */
    public static void loadGame() {
        String[] gs = {"Hydrochoerus Hive", "Capybara Colony",
                "Matriarch Mitosis", "Human Field", "Time Machine"};
        generators = new Generator[gs.length];
        for (int i = 0; i < gs.length; i++)
            generators[i] = new Generator(gs[i]);
    }

    /**
     * Saves generators data
     * @see ResourceManager#saveGame()
     */
    public static void saveGame() {
        for (Generator g : generators)
            g.save();
    }
}
