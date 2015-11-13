package com.mygdx.aoc.manager;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.Generator;
import com.mygdx.aoc.User;

public class ResourceManager {
    public static Skin skin;
    public static SpriteBatch batch;
    public static Preferences prefs;
    public static AgeOfCapybaras game;
    public static Json json = new Json();

    public static void init() {
        prefs = Gdx.app.getPreferences("default");
        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("pixel", new Texture(pixmap));


        batch = new SpriteBatch();
        processFont("goodDog", 3);
        skin.add("badlogic", new Texture(Gdx.files.internal("badlogic.jpg")));
    }

    private static void processFont(String name, int borderWidth) {
        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + name + ".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontPar = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontPar.borderWidth = borderWidth;
        skin.add(name + "FontGen", fontGen);
        skin.add(name + "FontPar", fontPar);
    }

    public static BitmapFont getFont(String name, int size) {
        String full = name + size;
        if (skin.has(full, BitmapFont.class))
            return skin.getFont(full);
        FreeTypeFontGenerator fontGen = skin.get(name + "FontGen", FreeTypeFontGenerator.class);
        FreeTypeFontGenerator.FreeTypeFontParameter fontPar = skin.get(name + "FontPar",
                FreeTypeFontGenerator.FreeTypeFontParameter.class);
        fontPar.size = size;
        BitmapFont font = fontGen.generateFont(fontPar);
        skin.add(full, font);
        return font;
    }

    public static void saveGame() {
        if (prefs == null) return;
        User.saveGame();
        Generator.saveGame();
        prefs.flush();
    }

    public static void loadGame() {
        User.loadGame();
        Generator.loadGame();
    }

    public static void loadCapybara() {
        FileHandle f = Gdx.files.internal("images/capybara.png");
        Texture t = new Texture(f);
        skin.add("capybara", t);
        Pixmap pm = new Pixmap(f);
        skin.add("capybaraMap", pm);
    }

    public static void dispose() {
        batch.dispose();
        skin.dispose();
    }
}
