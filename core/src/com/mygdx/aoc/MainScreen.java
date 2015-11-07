package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainScreen implements Screen {
    final AgeOfCapybaras g;
    private Stage stage;
    private Table table;

    public MainScreen(AgeOfCapybaras game) {
        g = game;
        ResourceManager.loadMain();

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
        stage = new Stage(new FitViewport(1080, 1920, cam), ResourceManager.batch);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = ResourceManager.skin.getDrawable("capybara");
        final Button imageButton = new Button(bs);
        imageButton.addListener(new InputListener() {
            int i = 0;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Nova Capivara Hue" + (i++));
                return true;

            }
        });
        float mxw = 1080 * .95f, mxh = 1920 * .8f;
        float mn = Math.min(mxw / imageButton.getWidth(), mxh / imageButton.getHeight());
        table.add(imageButton).maxSize(mn * imageButton.getWidth(), mn * imageButton.getHeight()).padBottom(1920 * .15f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
