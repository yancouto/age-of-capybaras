package com.mygdx.aoc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class AgeOfCapybaras extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FreeTypeFontGenerator fontGen;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontPar;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/good_dog.ttf"));
        fontPar = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontPar.flip = true;
        fontPar.size = 100;
        fontPar.borderWidth = 3;
        font = fontGen.generateFont(fontPar);
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        // important
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
        fontGen.dispose();
    }
}
