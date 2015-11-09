package com.mygdx.aoc.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.ResourceManager;

public class Splash implements Screen {
    final private BitmapFont font;
    final private SpriteBatch batch;
    final private AgeOfCapybaras game;
    OrthographicCamera cam;
    private float total;
    private final float waitTime = 2.5f;

    public Splash(AgeOfCapybaras game) {
        this.game = game;
        font = ResourceManager.skin.getFont("bigDog");
        batch = ResourceManager.batch;
        total = 0.f;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        font.draw(batch, "hey dude, the startup logo"
                + "would be here. Please click", 1080 * .125f, 1920 - 120, 1080 * 0.75f, Align.center, true);
        batch.end();

        total += delta;
        if(total > waitTime || Gdx.input.isTouched()) {
            game.setScreen(new MainScreen(game));
            dispose();
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
