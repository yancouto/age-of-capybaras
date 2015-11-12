package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.math.BigDecimal;
import java.util.Random;

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
 * For now generators scale like this:
 * Each generator has 5 main parameters: initialCost, firstCost, firstWin, seed, growth
 * initialCost : BigInteger = initial cost to buy the generator
 * firstCost : BigInteger   = cost of the first upgrade, should be close to initialCost
 * firstWin : BigDecimal    = first CPS
 * seed : long              = seed used by Random generator to create color and other things
 * growth : BigDecimal      = increase after update
 * <p/>
 * Suppose current update is priced curPrice and this generator currently supplies curCPS
 * then after buying the upgrade the new CPS will be
 * curCPS * growth
 * and the next price will be
 * curPrice * growth
 * <p/>
 * After every 100th update, this Generator CPS will be multiplied by 2
 */
public class Generator extends Widget {
    private static Drawable pixel;
    private BitmapFont fontSmall, fontBig;
    public final String name;
    private BigDecimal initialCost, currentCost, currentCPS, growth;
    private Random random;
    private int currentLevel;

    @Override
    public float getPrefWidth() {
        return 1080;
    }

    @Override
    public float getPrefHeight() {
        return 1920 * .125f;
    }

    private static class GeneratorData {
        String initialCost, firstCost, firstWin, growth;
        long seed;

        public void copyTo(Generator g) {
            g.random = new Random(seed);
            g.currentLevel = 0;
            g.initialCost = new BigDecimal(initialCost);
            g.currentCPS = BigDecimal.ZERO;
            g.currentCost = new BigDecimal(firstCost);
            g.currentCPS = new BigDecimal(firstWin);
            g.growth = new BigDecimal(growth);
        }

        public GeneratorData() {
        }
    }

    private Color backColor, fillColor;

    public Generator(String name) {
        this.name = name;

        ResourceManager.json.fromJson(GeneratorData.class, Gdx.files.internal("generator/" + name + ".json")).copyTo(this);

        float a = random.nextFloat(), b = random.nextFloat(), c = random.nextFloat();
        float mult1 = 1.25f / (a + b + c), mult2 = 2.f / (a + b + c);
        backColor = new Color(a * mult1, b * mult1, c * mult1, 1);
        fillColor = new Color(a * mult2, b * mult2, c * mult2, 1);

        fontSmall = ResourceManager.getFont("goodDog", 80);
        fontBig = ResourceManager.getFont("goodDog", 100);

        Preferences prefs = ResourceManager.prefs;
        int cur = prefs.getInteger(name + "Generator", 0);
        while (currentLevel < cur)
            buyLevel();
    }

    /**
     * Advances to the next level
     */
    public void buyLevel() {
        currentLevel++;
        // no need to update
        if (currentLevel == 1) return;
        BigDecimal newCPS = currentCPS.multiply(growth);
        if (currentLevel % 100 == 0) newCPS = newCPS.add(newCPS);
        User.addCPS(newCPS.subtract(currentCPS));
        currentCPS = newCPS;
        currentCost = currentCost.multiply(growth);
    }

    /**
     * Saves this generator's data
     *
     * @see #saveGame()
     */
    private void save() {
        ResourceManager.prefs.putInteger(name + "Generator", currentLevel);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(backColor);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(fillColor);
        float h4 = getHeight() / 4.f;
        if (currentLevel == 0) {
            float c2 = fontBig.getLineHeight() / 2.f;
            fontBig.draw(batch, name, getX() + 50, getY() + c2 + h4 * 2.f);
        } else {
            float c2 = fontSmall.getLineHeight() / 2.f;
            pixel.draw(batch, getX(), getY(), getWidth() * (currentLevel % 100) / 100.f, getHeight());
            fontSmall.draw(batch, String.valueOf(currentLevel), getX() + 50, getY() + h4 * 2.f + c2);
            fontSmall.draw(batch, User.toSmallString(currentCPS.toBigInteger(), 2) + " CPS", getX() + 50, getY() + h4 + c2 - getHeight() / 20.f);
            fontSmall.draw(batch, name, getX() + 50, getY() + h4 * 3.f + c2 + getHeight() / 20.f);
        }
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
        pixel = ResourceManager.skin.getDrawable("pixel");
        String[] gs = {"Natural Birth"};
        generators = new Generator[gs.length];
        for (int i = 0; i < gs.length; i++)
            generators[i] = new Generator(gs[i]);
    }

    /**
     * Saves generators data
     *
     * @see ResourceManager#saveGame()
     */
    public static void saveGame() {
        for (Generator g : generators)
            g.save();
    }
}
