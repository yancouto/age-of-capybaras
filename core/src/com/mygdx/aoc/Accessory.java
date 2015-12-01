package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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


public class Accessory extends Widget {
    /**
     * All accessory instances
     */
    public static Accessory[] accessories;
    private static Drawable pixel;
    public final String name;
    public String buttonState;
    public Integer type; //0: helmet; 1:head; 2:face.
    public boolean purchased, equiped;
    public String imageName;
    public Drawable image;
    private BitmapFont fontSmall, fontBig, fontTiny;
    public BigDecimal price;
    private Color backColor, fillColor;
    private Rectangle actionButton = new Rectangle();
    private boolean buttonHeld = false;
    private Random random;

    public Accessory(FileHandle file) {
        final Accessory self = this;
        name = file.nameWithoutExtension().substring(3);

        ResourceManager.json.fromJson(AccessoryData.class, file).copyTo(this);

//        float a = this.random.nextFloat(), b = random.nextFloat(), c = random.nextFloat();
//        float mult1 = 1.25f / (a + b + c), mult2 = 2.f / (a + b + c);
//        backColor = new Color(a * mult1, b * mult1, c * mult1, 1);

        Preferences prefs = ResourceManager.prefs;
        purchased = prefs.getBoolean(name + "PurchasedAccessory", false);
        equiped = prefs.getBoolean(name + "EquipedAccessory", false);

        if (!purchased) buttonState = price.toString();
        else if (!equiped) buttonState = "Equip";
        else {
            Capybara.equipAccessory(this);
        }
        FileHandle f = Gdx.files.internal("accessory-images/" + imageName);
        Texture t = new Texture(f);
        ResourceManager.skin.add(imageName, t);
        Pixmap pm = new Pixmap(f);
        ResourceManager.skin.add(imageName + "Map", pm);
        image = ResourceManager.skin.getDrawable(imageName);

//        TODO: Find good colors
        backColor = new Color(Color.ORANGE);
        fillColor = new Color(Color.RED);

        fontTiny = ResourceManager.getFont("goodDog", 50);
        fontSmall = ResourceManager.getFont("goodDog", 80);
        fontBig = ResourceManager.getFont("goodDog", 100);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Hey you clicked at " + name + " (" + x + ", " + y + ")");
                if (!buttonHeld && actionButton.contains(x, y))
                    buttonHeld = true;
                return buttonHeld;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonHeld = false;
                if (actionButton.contains(x, y))
                    if (!purchased && buyAccessory()) {
                        buttonState = "Equip";
                        purchased = true;
                    } else if (purchased && equiped) {
                        Capybara.unequipAccessory(self);
                    } else if (purchased && !equiped) {
                        Capybara.equipAccessory(self);
                    }
            }
        });

    }

    /**
     * Loads accessories instances and initializes them
     *
     * @see ResourceManager#loadGame()
     */
    public static void loadGame() {
        pixel = ResourceManager.skin.getDrawable("pixel");
        FileHandle[] accs = Gdx.files.internal("accessory").list();
        accessories = new Accessory[accs.length];
        for (int i = 0; i < accs.length; i++) {
            accessories[i] = new Accessory(accs[i]);
        }
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

    public boolean buyAccessory() {
        if ((User.kapivarium.compareTo(price) < 0) || purchased) return false;
        User.kapivarium = User.kapivarium.subtract(price);
        purchased = true;
        return true;
    }

    /**
     * Saves this accessory's data
     *
     * @see #saveGame()
     */
    private void save() {
        ResourceManager.prefs.putBoolean(name + "PurchasedAccessory", purchased);
        ResourceManager.prefs.putBoolean(name + "EquipedAccessory", equiped);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(backColor);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(fillColor);
        float h4 = getHeight() / 4.f;
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        batch.setColor(Color.WHITE);
        image.draw(batch, getX() - 150, getY() - 1920 * .26f, 1080 * .5f, 1920 * .5f);
        batch.setColor(fillColor);
        fontTiny.draw(batch, name, 50 + getX() + 200, getY() + h4 * 2.f + s2);
        Color buttonColor = Color.GREEN;
        if (!purchased && User.kapivarium.compareTo(price) < 0) buttonColor = Color.GRAY;
        else if (buttonHeld || equiped) buttonColor = Color.LIME;
        batch.setColor(buttonColor);
        actionButton.set(50 + getWidth() * .55f, h4 * .75f, 300, h4 * 2.5f);
        pixel.draw(batch, getX() + actionButton.x, getY() + actionButton.y, actionButton.width, actionButton.height);
        batch.setColor(Color.WHITE);
        if (purchased) {
            if (equiped)
                fontSmall.draw(batch, buttonState, getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .7f + s2);
            else
                fontSmall.draw(batch, buttonState, 30 + getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .7f + s2);

        } else {
            BigInteger p = price.toBigInteger();
            fontSmall.draw(batch, User.toSmallString(p, 3), getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .7f + s2);
            fontTiny.draw(batch, User.toBla(p), getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .25f + t2);
        }
    }

    private static class AccessoryData {
        String type, price, imageName;
        Long seed;

        public AccessoryData() {
        }

        public void copyTo(Accessory a) {
            a.random = new Random(seed);
            a.type = Integer.valueOf(type);
            a.imageName = imageName;
            a.price = new BigDecimal(price);
        }
    }
}
