package com.mygdx.aoc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ResourceManager {
    public static Skin skin;
    public static SpriteBatch batch;
    public static FreeTypeFontGenerator fontGenDog;
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontPar;
    public static Preferences prefs;

    public static void init() {
        prefs = Gdx.app.getPreferences("default");
        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));


        batch = new SpriteBatch();
        fontGenDog = new FreeTypeFontGenerator(Gdx.files.internal("fonts/good_dog.ttf"));
        fontPar = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontPar.size = 200;
        fontPar.borderWidth = 3;
        BitmapFont font = fontGenDog.generateFont(fontPar);
        skin.add("bigDog", font);
        skin.add("badlogic", new Texture(Gdx.files.internal("badlogic.jpg")));
    }

    static class Data {
        byte[] c, cps, cpc;
        byte[] k, kps;

        Data reset() {
            c = User.capybaras.toBigInteger().toByteArray();
            cps = User.cps.toBigInteger().toByteArray();
            cpc = User.cpc.toBigInteger().toByteArray();
            k = User.kapivarium.toBigInteger().toByteArray();
            kps = User.kps.toBigInteger().toByteArray();
            return this;
        }
    }

    private static Json json = new Json();

    public static void saveGame() {
        if (prefs == null) return;
        prefs.putLong("time", System.currentTimeMillis());
        prefs.putString("data", json.toJson(new Data().reset()));
        prefs.flush();
    }

    public static void loadGame() {
        if (prefs.contains("data")) {
            Data d = json.fromJson(Data.class, prefs.getString("data"));
            User.capybaras = new BigDecimal(new BigInteger(d.c));
            User.cps = new BigDecimal(new BigInteger(d.cps));
            User.cpc = new BigDecimal(new BigInteger(d.cpc));
            User.kapivarium = new BigDecimal(new BigInteger(d.k));
            User.kps = new BigDecimal(new BigInteger(d.kps));
        }
        User.addPast(prefs.getLong("time", System.currentTimeMillis()));
    }

    public static void loadMain() {
        Texture t = new Texture(Gdx.files.internal("images/capybara.jpg"));
        skin.add("capybara", t);
    }

    public static void dispose() {
        batch.dispose();
        fontGenDog.dispose();
        skin.dispose();
    }
}
