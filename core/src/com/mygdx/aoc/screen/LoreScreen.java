
package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.aoc.Generator;
import com.mygdx.aoc.ResourceManager;

public class LoreScreen implements Screen {
    private Stage stage;
    final private BitmapFont font;
    final private SpriteBatch batch;
    private Table table;
    private MainScreen mainScreen;
    private OptionsMenu optionsMenu;

    public LoreScreen(OptionsMenu opt, MainScreen ms) {
        stage = new Stage(opt.stage.getViewport(), opt.stage.getBatch());
        table = new Table();
        font = ResourceManager.getFont("goodDog", 200);
        batch = ResourceManager.batch;
        mainScreen = ms;
        optionsMenu = opt;

        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        table.top();

        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    mainScreen.game.setScreen(optionsMenu);
                    return true;
                }
                return false;
            }
        });

        scrollUI();
    }

    private ScrollPane scrollPane;
    private Table lores;

    // TODO: Replace Generator class with Lore class when it's done
    private void scrollUI() {
        lores = new Table();
        for (Generator l : Generator.generators) {
            lores.add(l);
            lores.row();
        }
        scrollPane = new ScrollPane(lores);
        scrollPane.setPosition(0, 0);
        scrollPane.setSize(1080, 1920 * .3f);
        stage.addActor(scrollPane);
    }

    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//         Just to have some graphical feedback
        batch.begin();
        font.draw(batch, "Lore description goes here",
                1080 * .125f, 1920 - 120, 1080 * 0.75f, Align.center, true);
        batch.end();
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void dispose () {
        stage.dispose();
        font.dispose();
    }

    @Override
    public void hide () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show () { Gdx.input.setInputProcessor(stage); }
}
