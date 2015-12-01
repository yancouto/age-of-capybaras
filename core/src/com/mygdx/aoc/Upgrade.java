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
import com.mygdx.aoc.screen.MainScreen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class Upgrade extends Widget {
    public static Upgrade[] upgrades;
    private static Drawable pixel;
    private String name, description, generatorName, type;
    private Color fillColor;
    private BitmapFont fontSmall, fontTiny;
    private Rectangle buyButton = new Rectangle();
    private Random random;
    private BigDecimal cost, multiplier;
    private boolean bought;
    private Generator gen = null;
    private BigDecimal[] agesCost = null;

    public Upgrade(FileHandle file) {
        ResourceManager.json.fromJson(UpgradeData.class, file).copyTo(this);
        float a = random.nextFloat(), b = random.nextFloat(), c = random.nextFloat();

        float mult1 = 1.25f / (a + b + c);
        fillColor = new Color(a * mult1, b * mult1, c * mult1, 1);
        fontTiny = ResourceManager.getFont("goodDog", 50);
        fontSmall = ResourceManager.getFont("goodDog", 80);

        Preferences prefs = ResourceManager.prefs;
        bought = prefs.getBoolean(name + "Upgrade", false);

        if (type.equals("generator"))
            for (Generator g : Generator.generators)
                if (g.getName().equals(generatorName))
                    gen = g;


        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return buyButton.contains(x, y);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (buyButton.contains(x, y)) {
                    if (type.equals("click"))
                        buyClick(true);
                    else if (type.equals("generator"))
                        buyGenerator(true);
                    else if (type.equals("age"))
                        buyNextAge(true);
                }
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

    public boolean buyGenerator(boolean pay) {
        if (gen != null && gen.getCurrentLevel() > 0) {
            if (pay) {
                if (User.capybaras.compareTo(cost) < 0) return false;
                User.removeCapybara(cost);
                bought = true;
                MainScreen.instance().upgrades.removeActor(this);
            }
            gen.multiplyCPS(multiplier);
            return true;
        }
        return false;
    }

    public boolean buyClick(boolean pay) {
        if (pay) {
            if (User.capybaras.compareTo(cost) < 0) return false;
            User.removeCapybara(cost);
            bought = true;
            MainScreen.instance().upgrades.removeActor(this);
        }
        User.multiplyCPC(multiplier);
        return true;
    }

    public boolean buyNextAge(boolean pay) {
        if (pay) {
            if (User.capybaras.compareTo(cost) < 0) return false;
            User.removeCapybara(cost);
            CapybaraScreen.advanceAge();
            if (CapybaraScreen.currentAge() == agesCost.length)
                MainScreen.instance().upgrades.removeActor(this);
        }
        cost = agesCost[CapybaraScreen.currentAge() - 1];
        return true;
    }

    public String getType() {
        return type;
    }

    public boolean getBought() {
        return bought;
    }

    public boolean isFinalAge() {
        if (agesCost != null)
            if (agesCost.length == CapybaraScreen.currentAge())
                return true;
        return false;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public Generator getGen() {
        return gen;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(fillColor);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        float h4 = getHeight() / 4.f;
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        fontSmall.draw(batch, name, getX() + 25, getY() + h4 * 2.f + s2 + getHeight() / 20.f);

        if (multiplier.compareTo(BigDecimal.ZERO) == 0)
            fontTiny.draw(batch, description, getX() + 25, getY() + 2f * s2);
        else
            fontTiny.draw(batch, description + " x" + multiplier, getX() + 25, getY() + 2f * s2);

        BigInteger nCost = cost.toBigInteger();
        if (User.capybaras.compareTo(cost) < 0 || (gen != null && gen.getCurrentLevel() == 0))
            batch.setColor(Color.GRAY);
        else
            batch.setColor(Color.LIME);
        buyButton.set(getWidth() * .55f, h4 * .75f, 400, h4 * 2.5f);
        pixel.draw(batch, getX() + buyButton.x, getY() + buyButton.y, buyButton.width, buyButton.height);
        batch.setColor(Color.WHITE);
        fontSmall.draw(batch, User.toSmallString(nCost, 3), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .7f + s2);
        fontTiny.draw(batch, User.toBla(nCost), getX() + buyButton.x + getWidth() * 0.015f, getY() + buyButton.y + buyButton.height * .25f + t2);
    }

    private static class UpgradeData {
        String name, description, cost, type, generatorName, multiplier;
        long seed;

        public void copyTo(Upgrade u) {
            u.name = name;
            u.description = description;
            u.type = type;
            u.generatorName = generatorName;
            u.multiplier = new BigDecimal(multiplier);
            u.random = new Random(seed);
            if (type.equals("age")) {
                String[] agesCost = cost.split("\\s*,\\s*");
                u.agesCost = new BigDecimal[agesCost.length];
                for (int i = 0; i < agesCost.length; i++)
                    u.agesCost[i] = new BigDecimal(agesCost[i]);
            } else
                u.cost = new BigDecimal(cost);
        }
    }
}
