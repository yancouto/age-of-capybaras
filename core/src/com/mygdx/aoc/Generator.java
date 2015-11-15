package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.aoc.manager.ResourceManager;

import java.math.BigDecimal;
import java.math.BigInteger;
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
 * curPrice * growth * 1.1
 * <p/>
 * After every 100th update, this Generator CPS will be multiplied by 2
 */
public class Generator extends Widget {
    /**
     * All generator instances
     */
    public static Generator[] generators;
    private static Drawable pixel;
    public final String name;
    private BitmapFont fontSmall, fontBig, fontTiny;
    private BigDecimal initialCost, currentCost, currentCPS, growth;
    private Random random;
    private int currentLevel;
    private Color backColor, fillColor;
    private Rectangle buyButton = new Rectangle();
    private BigDecimal multiplier = new BigDecimal("1.1");

    public Generator(FileHandle file) {
        name = file.nameWithoutExtension().substring(3);

        ResourceManager.json.fromJson(GeneratorData.class, file).copyTo(this);

        float a = random.nextFloat(), b = random.nextFloat(), c = random.nextFloat();
        float mult1 = 1.25f / (a + b + c), mult2 = 2.f / (a + b + c);
        backColor = new Color(a * mult1, b * mult1, c * mult1, 1);
        fillColor = new Color(a * mult2, b * mult2, c * mult2, 1);

        fontTiny = ResourceManager.getFont("goodDog", 50);
        fontSmall = ResourceManager.getFont("goodDog", 80);
        fontBig = ResourceManager.getFont("goodDog", 100);

        Preferences prefs = ResourceManager.prefs;
        int cur = prefs.getInteger(name + "Generator", 0);
        while (currentLevel < cur)
            buyLevel(false);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Hey you clicked " + name + " at (" + x + ", " + y + ")");
                return buyButton.contains(x, y);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (buyButton.contains(x, y))
                    buyLevel(true);
            }
        });

    }

    /**
     * Loads generators instances and initializes them
     *
     * @see ResourceManager#loadGame()
     */
    public static void loadGame() {
        pixel = ResourceManager.skin.getDrawable("pixel");
        FileHandle[] gens = Gdx.files.internal("generator").list();
        generators = new Generator[gens.length];
        for (int i = 0; i < gens.length; i++)
            generators[i] = new Generator(gens[i]);
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

    @Override
    public float getPrefWidth() {
        return 1080;
    }

    @Override
    public float getPrefHeight() {
        return 1920 * .125f;
    }

    /**
     * Advances to the next level
     */
    public boolean buyLevel(boolean pay) {
        if (pay) {
            BigDecimal price;
            if (currentLevel == 0) price = initialCost;
            else price = currentCost;
            if (User.capybaras.compareTo(price) < 0) return false;
            User.capybaras = User.capybaras.subtract(price);
        }
        currentLevel++;
        // no need to update
        if (currentLevel == 1) {
            User.addCPS(currentCPS);
            return true;
        }
        BigDecimal newCPS = currentCPS.multiply(growth);
        if (currentLevel % 100 == 0) newCPS = newCPS.add(newCPS);
        User.addCPS(newCPS.subtract(currentCPS));
        currentCPS = newCPS;
        currentCost = currentCost.multiply(growth).multiply(multiplier);
        return true;
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
        BigInteger cost;
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        if (currentLevel == 0) {
            fontSmall.draw(batch, name, getX() + 50, getY() + s2 + h4 * 2.f);
            cost = initialCost.toBigInteger();
        } else {
            pixel.draw(batch, getX(), getY(), getWidth() * (currentLevel % 100) / 100.f, getHeight());
            fontSmall.draw(batch, "Lvl: " + currentLevel, getX() + 50, getY() + h4 * 2.f + s2);
            BigInteger cps = currentCPS.toBigInteger();
            fontSmall.draw(batch, User.toSmallString(cps, 1), getX() + 50, getY() + h4 + s2 - getHeight() / 20.f);
            fontTiny.draw(batch, User.toBla(cps) + " CPS", getX() + 200, getY() + h4 + t2 - getHeight() / 20.f);
            fontSmall.draw(batch, name, getX() + 50, getY() + h4 * 3.f + s2 + getHeight() / 20.f);
            cost = currentCost.toBigInteger();
        }
        batch.setColor(Color.LIME);
        buyButton.set(getWidth() * .55f, h4 * .75f, 400, h4 * 2.5f);
        pixel.draw(batch, getX() + buyButton.x, getY() + buyButton.y, buyButton.width, buyButton.height);
        batch.setColor(Color.WHITE);
        fontSmall.draw(batch, User.toSmallString(cost, 3), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .7f + s2);
        fontTiny.draw(batch, User.toBla(cost), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .25f + t2);
    }

    private static class GeneratorData {
        String initialCost, firstCost, firstWin, growth;
        long seed;

        public GeneratorData() {
        }

        public void copyTo(Generator g) {
            g.random = new Random(seed);
            g.currentLevel = 0;
            g.initialCost = new BigDecimal(initialCost);
            g.currentCPS = BigDecimal.ZERO;
            g.currentCost = new BigDecimal(firstCost);
            g.currentCPS = new BigDecimal(firstWin);
            g.growth = new BigDecimal(growth);
        }
    }
}
