package com.mygdx.aoc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.aoc.manager.ResourceManager;

import java.math.BigDecimal;
import java.util.Random;

public class Accessory extends Widget {
    /**
     * All accessory instances (almost a Generator copy)
     */
    public static Accessory[] accessories;
    private static Drawable pixel;
    public final String name;
    public final Integer type; //0: helmet; 1:head; 2:face.
    private BitmapFont fontSmall, fontBig;
    private BigDecimal initialCost, currentCost, currentCPS, growth;
    private Random random;
    private int currentLevel;
    private Color backColor, fillColor;

    public Accessory(String name, Integer type) {
        this.name = name;
        this.type = type;
//        TODO: Make json saving and loading control
//        ResourceManager.json2.fromJson(AccessoryData.class, Gdx.files.internal("accessory/" + name + ".json")).copyTo(this);
//        TODO: Find good colors
        backColor = new Color(Color.ORANGE);
        fillColor = new Color(Color.RED);

        fontSmall = ResourceManager.getFont("goodDog", 80);
        fontBig = ResourceManager.getFont("goodDog", 100);

    }

    /**
     * Loads accessories instances and initializes them
     *
     * @see ResourceManager#loadGame()
     */
    public static void loadGame() {
        pixel = ResourceManager.skin.getDrawable("pixel");
        String[] ac = {"Hatbara", "CAPbara", "Helmet1", "Helmet2", "Face1", "Face2"};
        Integer[] ty = {1, 1, 0, 0, 2, 2};
        accessories = new Accessory[ac.length];
        for (int i = 0; i < ac.length; i++)
            accessories[i] = new Accessory(ac[i], ty[i]);
    }

    /**
     * Saves accessories data
     *
     * @see ResourceManager#saveGame()
     */
    public static void saveGame() {
        for (Accessory a : accessories)
            a.save();
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
     * Saves this accessory's data
     *
     * @see #saveGame()
     */
    private void save() {
        ResourceManager.prefs.putInteger(name + "Accessory", currentLevel);
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

    private static class AccessoryData {
        String initialCost, firstCost, firstWin, growth;
        long seed;

        public AccessoryData() {
        }

        public void copyTo(Accessory a) {
            a.random = new Random(seed);
            a.currentLevel = 0;
            a.initialCost = new BigDecimal(initialCost);
            a.currentCPS = BigDecimal.ZERO;
            a.currentCost = new BigDecimal(firstCost);
            a.currentCPS = new BigDecimal(firstWin);
            a.growth = new BigDecimal(growth);
        }
    }
}
