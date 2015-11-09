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


    public static Json json = new Json();

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
