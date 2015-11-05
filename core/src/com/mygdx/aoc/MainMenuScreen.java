package com.mygdx.aoc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
    final AgeOfCapybaras g;
    OrthographicCamera cam;

    public MainMenuScreen(AgeOfCapybaras game) {
        this.g = game;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 480, 800);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        g.batch.setProjectionMatrix(cam.combined);

        g.batch.begin();
        g.font.draw(g.batch, "hey dude", 85, 325);
        g.batch.end();
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
