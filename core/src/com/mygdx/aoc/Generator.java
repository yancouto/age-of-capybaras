package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
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
 * firstCPS : BigDecimal    = first CPS
 * seed : long              = seed used by Random generator to create color and other things
 * costGrowth : BigDecimal  = increase in cost after update
 * cpsGrowth : BigDecimal   = increase in CPS after update
 * <p/>
 * Suppose current update is priced curPrice and this generator currently supplies curCPS
 * then after buying the upgrade the new CPS will be
 * curCPS * cpsGrowth
 * and the next price will be
 * curPrice * costGrowth
 * <p/>
 * After every 100th update, this Generator CPS will be multiplied by 2
 */
public class Generator extends Widget {
    /**
     * All generator instances
     */
    public static Generator[] generators;
    private static Drawable pixel;
    private static Sound fail;
    public final String name;
    private BitmapFont fontSmall, fontBig, fontTiny;
    private BigDecimal currentCost, currentCPS, cpsGrowth, costGrowth;
    private Random random;
    private int currentLevel;
    private Color backColor, fillColor;
    private Rectangle buyButton = new Rectangle();
    private boolean buttonHeld = false;

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
                if (!buttonHeld && buyButton.contains(x, y))
                    buttonHeld = true;
                return buttonHeld;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonHeld = false;
                if (!buyButton.contains(x, y)) return;
                if (User.capybaras.compareTo(currentCost) >= 0)
                    buyLevel(true);
                else fail.play(ResourceManager.prefs.getInteger("soundVolume") / 100.f);
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
        fail = ResourceManager.skin.get("negative", Sound.class);
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

    public String getName() {
        return name;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Multiplies current CPS by {@code num}. Assumes {@code currentLevel > 0}.
     * @param num amount to multiply CPS
     */
    public void multiplyCPS(BigDecimal num) {
        BigDecimal newCPS = currentCPS.multiply(num);
        User.addCPS(newCPS.subtract(currentCPS));
        currentCPS = newCPS;
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
            if (User.capybaras.compareTo(currentCost) < 0) return false;
            User.removeCapybara(currentCost);
        }
        currentLevel++;
        BigDecimal newCPS = currentCPS;
        if (currentLevel != 1) newCPS = newCPS.multiply(cpsGrowth);
        else currentCPS = BigDecimal.ZERO;
        if (currentLevel % 100 == 0) newCPS = newCPS.add(newCPS);
        User.addCPS(newCPS.subtract(currentCPS));
        currentCPS = newCPS;
        currentCost = currentCost.multiply(costGrowth);
        return true;
    }

    public void setCurrentLevel(int i) {
        currentLevel = i;
    }

    public BigDecimal getCurrentCPS() { return currentCPS; }

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
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        if (currentLevel == 0) {
            fontSmall.draw(batch, name, getX() + 50, getY() + s2 + h4 * 2.f);
        } else {
            pixel.draw(batch, getX(), getY(), getWidth() * (currentLevel % 100) / 100.f, getHeight());
            fontSmall.draw(batch, "Lvl: " + currentLevel, getX() + 50, getY() + h4 * 2.f + s2);
            BigInteger cps = currentCPS.toBigInteger();
            fontSmall.draw(batch, User.toSmallString(cps, 1), getX() + 50, getY() + h4 + s2 - getHeight() / 20.f);
            fontTiny.draw(batch, User.toBla(cps) + " CPS", getX() + 200, getY() + h4 + t2 - getHeight() / 20.f);
            fontSmall.draw(batch, name, getX() + 50, getY() + h4 * 3.f + s2 + getHeight() / 20.f);
        }
        buyButton.set(getWidth() * .55f, h4 * .75f, 400, h4 * 2.5f);
        Color buttonColor = Color.GREEN;
        if (User.capybaras.compareTo(currentCost) < 0) buttonColor = Color.GRAY;
        else if (buttonHeld) buttonColor = Color.LIME;
        batch.setColor(buttonColor);
        pixel.draw(batch, getX() + buyButton.x, getY() + buyButton.y, buyButton.width, buyButton.height);
        batch.setColor(Color.WHITE);
        BigInteger intCost = currentCost.toBigInteger();
        fontSmall.draw(batch, User.toSmallString(intCost, 3), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .7f + s2);
        fontTiny.draw(batch, User.toBla(intCost), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .25f + t2);
    }

    private static class GeneratorData {
        String initialCost, firstWin, cpsGrowth, costGrowth;
        long seed;

        public void copyTo(Generator g) {
            g.random = new Random(seed);
            g.currentLevel = 0;
            g.currentCost = new BigDecimal(initialCost);
            g.currentCPS = BigDecimal.ZERO;
            g.currentCPS = new BigDecimal(firstWin);
            g.cpsGrowth = new BigDecimal(cpsGrowth);
            g.costGrowth = new BigDecimal(costGrowth);
        }
    }
}
