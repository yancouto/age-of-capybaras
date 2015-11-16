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
import com.mygdx.aoc.screen.CapybaraScreen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class Upgrade extends Widget {
    public static Upgrade[] upgrades;
    private static Drawable pixel;
    private String name, description, generatorName, type;
    private Color colorActive, colorInactive;
    private BitmapFont fontSmall, fontTiny;
    private Rectangle buyButton = new Rectangle();
    private Random random;
    private BigDecimal cost, multiplier;
    private boolean bought = false;

    public Upgrade (FileHandle file) {
        ResourceManager.json.fromJson(UpgradeData.class, file).copyTo(this);
        float a = random.nextFloat(), b = random.nextFloat(), c = random.nextFloat();
        float mult1 = 1.25f / (a + b + c), mult2 = 2.f / (a + b + c);
        colorInactive = new Color(a * mult1, b * mult1, c * mult1, 1);
        colorActive = new Color(a * mult2, b * mult2, c * mult2, 1);
        fontTiny = ResourceManager.getFont("goodDog", 50);
        fontSmall = ResourceManager.getFont("goodDog", 80);

        //Preferences prefs = ResourceManager.prefs;
        //if (prefs.getBoolean(name + "Upgrade", false)) buy();

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return buyButton.contains(x, y);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (buyButton.contains(x, y) && (!bought || type.equals("age")))
                    buy();
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
        ResourceManager.prefs.putBoolean(name + "Upgrade", bought);
    }

    @Override
    public float getPrefWidth() {
        return 1080;
    }

    @Override
    public float getPrefHeight() {
        return 1920 * .125f;
    }

    public boolean buy() {
        if (User.capybaras.compareTo(cost) < 0) return false;

        if (type.equals("click")) {
            User.clickMultiplier = multiplier;
            User.capybaras = User.capybaras.subtract(cost);
            bought = true;
        }
        else if (type.equals("generator")) {
            for (Generator g : Generator.generators) {
                if (g.getName().equals(generatorName) && g.getCurrentLevel() > 0) {
                    g.setGrowth(multiplier);
                    User.capybaras = User.capybaras.subtract(cost);
                    bought = true;
                }
                return false;
            }
        }
        else if (type.equals("age")) {
            CapybaraScreen.advanceAge();
            BigDecimal curAge = new BigDecimal(CapybaraScreen.currentAge());
            User.capybaras = User.capybaras.subtract(cost);
            cost = cost.multiply(curAge);
        }
        return true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(bought ? colorInactive : colorActive);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        float h4 = getHeight() / 4.f;
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        fontSmall.draw(batch, name, getX() + 25, getY() + h4 * 2.f + s2 + getHeight() / 20.f);

        if (multiplier.compareTo(BigDecimal.ZERO) == 0)
            fontTiny.draw(batch, description, getX() + 25, getY() + 2f * s2);
        else
            fontTiny.draw(batch, description + " x" + multiplier, getX() + 25, getY() + 2f * s2);

        if (!bought || type.equals("age")) {
            BigInteger ncost = cost.toBigInteger();
            batch.setColor(Color.LIME);
            buyButton.set(getWidth() * .55f, h4 * .75f, 400, h4 * 2.5f);
            pixel.draw(batch, getX() + buyButton.x, getY() + buyButton.y, buyButton.width, buyButton.height);
            batch.setColor(Color.WHITE);
            fontSmall.draw(batch, User.toSmallString(ncost, 3), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .7f + s2);
            fontTiny.draw(batch, User.toBla(ncost), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .25f + t2);
        }
    }

    private static class UpgradeData {
        String name, cost, description, type, generatorName, multiplier;
        long seed;

        public UpgradeData() {
        }

        public void copyTo(Upgrade u) {
            u.name = name;
            u.description = description;
            u.cost = new BigDecimal(cost);
            u.type = type;
            u.generatorName = generatorName;
            u.multiplier = new BigDecimal(multiplier);
            u.random = new Random(seed);
        }
    }
}
