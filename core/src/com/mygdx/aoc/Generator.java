package com.mygdx.aoc;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Generator extends Widget {
    private Drawable background;
    private BitmapFont font;
    public final String name;

    // TODO: Decide how generators will "scale"
    @Override
    public float getPrefWidth() {
        return 1080;
    }

    @Override
    public float getPrefHeight() {
        return 1920 * .1f;
    }

    public Generator(String name) {
        this.name = name;
        background = ResourceManager.skin.newDrawable("white", Util.randomColor());
        ResourceManager.fontPar.size = 100;
        if (!ResourceManager.skin.has("bigDog100", BitmapFont.class)) {
            font = ResourceManager.fontGenDog.generateFont(ResourceManager.fontPar);
            ResourceManager.skin.add("bigDog100", font);
        } else font = ResourceManager.skin.getFont("bigDog100");
        Preferences prefs = ResourceManager.prefs;
        if (prefs.contains(name + "generator")) {
            // TODO: read generator info
        }
    }

    private void save() {
        // TODO: write generator info
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, getX(), getY(), getWidth(), getHeight());
        font.draw(batch, name, getX() + 50, getY() + (font.getLineHeight() + getHeight()) / 2.f);
    }

    public static Generator[] generators;

    public static void loadGame() {
        String[] gs = {"Hydrochoerus Hive", "Capybara Colony",
                "Matriarch Mitosis", "Human Field", "Time Machine"};
        generators = new Generator[gs.length];
        for (int i = 0; i < gs.length; i++)
            generators[i] = new Generator(gs[i]);
    }

    public static void saveGame() {
        for (Generator g : generators)
            g.save();
    }
}
