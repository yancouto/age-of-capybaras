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

public class Upgrade extends Widget {
    public static Upgrade[] upgrades;
    private static Drawable pixel;
    private String name;
    private Color backColor, fillColor;
    private BitmapFont fontSmall, fontTiny;
    private Rectangle buyButton = new Rectangle();
    private Random random;
    private int currentLevel;
    //private BigInteger initialCost, currentCost;
    //private BigDecimal multiplier = new BigDecimal("1.1");

    public Upgrade (FileHandle file) {
        ResourceManager.json.fromJson(UpgradeData.class, file).copyTo(this);
        float a = random.nextFloat(), b = random.nextFloat(), c = random.nextFloat();
        float mult1 = 1.25f / (a + b + c), mult2 = 2.f / (a + b + c);
        backColor = new Color(a * mult1, b * mult1, c * mult1, 1);
        fillColor = new Color(a * mult2, b * mult2, c * mult2, 1);
        fontTiny = ResourceManager.getFont("goodDog", 50);
        fontSmall = ResourceManager.getFont("goodDog", 80);

        Preferences prefs = ResourceManager.prefs;
        int cur = prefs.getInteger(name + "Upgrade", 0);
        while (currentLevel < cur)
            buyLevel(false);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return buyButton.contains(x, y);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (buyButton.contains(x, y))
                    buyLevel(true);
            }
        });
    }

    public static void loadGame() {
        pixel = ResourceManager.skin.getDrawable("pixel");
        FileHandle[] upgs = Gdx.files.internal("upgrades").list();
        upgrades = new Upgrade[upgs.length];
        for (int i = 0; i < upgs.length; i++)
            upgrades[i] = new Upgrade(upgs[i]);
    }

    public static void saveGame() {
        for (Upgrade u : upgrades)
            u.save();
    }

    private void save() {
        ResourceManager.prefs.putInteger(name + "Upgrade", currentLevel);
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
//        if (pay) {
//            BigDecimal price;
//            if (currentLevel == 0) price = initialCost;
//            else price = currentCost;
//            if (User.capybaras.compareTo(price) < 0) return false;
//            User.capybaras = User.capybaras.subtract(price);
//        }
        currentLevel++;
        // no need to update
//        if (currentLevel == 1) {
//            User.addCPS(currentCPS);
//            return true;
//        }
//        BigDecimal newCPS = currentCPS.multiply(growth);
//        if (currentLevel % 100 == 0) newCPS = newCPS.add(newCPS);
//        User.addCPS(newCPS.subtract(currentCPS));
//        currentCPS = newCPS;
//        currentCost = currentCost.multiply(growth).multiply(multiplier);
        return true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(backColor);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(fillColor);
        float h4 = getHeight() / 4.f;
        //BigInteger cost;
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        if (currentLevel == 0) {
            fontSmall.draw(batch, name, getX() + 50, getY() + s2 + h4 * 2.f);
            //cost = initialCost.toBigInteger();
        } else {
            pixel.draw(batch, getX(), getY(), getWidth() * (currentLevel % 100) / 100.f, getHeight());
            fontSmall.draw(batch, "Lvl: " + currentLevel, getX() + 50, getY() + h4 * 2.f + s2);
            //BigInteger cps = currentCPS.toBigInteger();
            //fontSmall.draw(batch, User.toSmallString(cps, 1), getX() + 50, getY() + h4 + s2 - getHeight() / 20.f);
            //fontTiny.draw(batch, User.toBla(cps) + " CPS", getX() + 200, getY() + h4 + t2 - getHeight() / 20.f);
            fontSmall.draw(batch, name, getX() + 50, getY() + h4 * 3.f + s2 + getHeight() / 20.f);
            //cost = currentCost.toBigInteger();
        }
        batch.setColor(Color.LIME);
        buyButton.set(getWidth() * .55f, h4 * .75f, 400, h4 * 2.5f);
        pixel.draw(batch, getX() + buyButton.x, getY() + buyButton.y, buyButton.width, buyButton.height);
        batch.setColor(Color.WHITE);
        //fontSmall.draw(batch, User.toSmallString(cost, 3), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .7f + s2);
        //fontTiny.draw(batch, User.toBla(cost), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .25f + t2);
    }

    private static class UpgradeData {
        String name;
        long seed;

        public UpgradeData() {
        }

        public void copyTo(Upgrade u) {
            u.random = new Random(seed);
            u.name = new String(name);
        }
    }
}
