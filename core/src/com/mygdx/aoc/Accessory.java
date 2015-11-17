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
    private BigDecimal price;
    private Color backColor, fillColor;
    private Rectangle actionButton = new Rectangle();

    public Accessory(FileHandle file) {
        final Accessory self = this;
        name = file.nameWithoutExtension().substring(3);

        ResourceManager.json.fromJson(AccessoryData.class, file).copyTo(this);

        Preferences prefs = ResourceManager.prefs;
        purchased = prefs.getBoolean(name + "PurchasedAccessory", false);
        equiped = prefs.getBoolean(name + "EquipedAccessory", false);

        if (!purchased) buttonState = price.toString();
        else if (!equiped) buttonState = "Equip";
        else {
            Capybara.equipAccessory(this);
            if (equiped)
                buttonState = "Unequip";
            else
                buttonState = "Equip";
        }
        FileHandle f = Gdx.files.internal("accessory-images/" + imageName);
        Texture t = new Texture(f);
        ResourceManager.skin.add(imageName, t);
        Pixmap pm = new Pixmap(f);
        ResourceManager.skin.add(imageName +"Map", pm);

//        TODO: Find good colors
        backColor = new Color(Color.ORANGE);
        fillColor = new Color(Color.RED);

        fontTiny = ResourceManager.getFont("goodDog", 50);
        fontSmall = ResourceManager.getFont("goodDog", 80);
        fontBig = ResourceManager.getFont("goodDog", 100);

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Hey you clicked at " + name + " (" + x + ", " + y + ")");
                return actionButton.contains(x, y);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actionButton.contains(x, y))
                    if (!purchased && buyAccessory()) {
                        buttonState = "Equip";
                        purchased = true;
                    } else if (purchased && equiped) {
                        buttonState = "Equip";
                        Capybara.unequipAccessory(self);
                    } else if (purchased && !equiped) {
                        buttonState = "Unequip";
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
//            System.out.println(accs[i]);
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
        if (User.capybaras.compareTo(price) < 0) return false;
        User.capybaras = User.capybaras.subtract(price);
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
        image = ResourceManager.skin.getDrawable(imageName);
        batch.setColor(backColor);
        pixel.draw(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(fillColor);
        float h4 = getHeight() / 4.f;
        float s2 = fontSmall.getLineHeight() / 2.f, t2 = fontTiny.getLineHeight() / 2.f;
        batch.setColor(Color.WHITE);
        image.draw(batch, getX() - 150, getY() - 1920 * .26f, 1080 * .5f, 1920 * .5f);
        batch.setColor(fillColor);
        fontTiny.draw(batch, name, 50 + getX() + 200, getY() + h4 * 2.f + s2);
        batch.setColor(Color.LIME);
        actionButton.set(50 + getWidth() * .55f, h4 * .75f, 300, h4 * 2.5f);
        pixel.draw(batch, getX() + actionButton.x, getY() + actionButton.y, actionButton.width, actionButton.height);
        batch.setColor(Color.WHITE);
        if (purchased) {
            if (equiped)
                fontSmall.draw(batch, buttonState, getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .7f + s2);
            else
                fontSmall.draw(batch, buttonState, 30 + getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .7f + s2);
        }
        else {
            BigInteger p = price.toBigInteger();
            fontSmall.draw(batch, User.toSmallString(p, 3), getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .7f + s2);
            fontTiny.draw(batch, User.toBla(p), getX() + actionButton.x + getWidth() * 0.03f, getY() + actionButton.y + actionButton.height * .25f + t2);
        }
    }

    private static class AccessoryData {
        String type, price, imageName;

        public AccessoryData() {
        }

        public void copyTo(Accessory a) {
            a.type = new Integer(type);
            a.imageName = new String(imageName);
            a.price = new BigDecimal(price);
        }
    }
}
