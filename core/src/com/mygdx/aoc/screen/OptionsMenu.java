package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.aoc.ResourceManager;

/**
 * Menu where volume can be changed, lore can be seen, and other options can be tweaked.
 */
public class OptionsMenu implements Screen {
    private Drawable pixel;
    public Stage stage;
    private LoreScreen loreScreen;
    private MainScreen mainScreen;

    public OptionsMenu(Stage s, MainScreen ms) {
        mainScreen = ms;
        pixel = ResourceManager.skin.getDrawable("pixel");

        stage = new Stage(s.getViewport(), s.getBatch());
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Slider.SliderStyle ss = new Slider.SliderStyle(ResourceManager.skin.newDrawable("pixel", Color.BROWN),
                ResourceManager.skin.newDrawable("pixel", Color.GOLD));
        ss.background = ResourceManager.skin.newDrawable("pixel", Color.OLIVE);
        ss.background.setMinWidth(1080 * .6f);
        ss.background.setMinHeight(1920 * .07f);
        ss.knob = ResourceManager.skin.newDrawable("pixel", Color.BLACK);
        ss.knob.setMinWidth(1080 * .02f);
        ss.knob.setMinHeight(1920 * .1f);

        Slider sl = new Slider(0, 100, 1, false, ss);
        sl.setValue(ResourceManager.prefs.getInteger("soundVolume", 50));
        sl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ResourceManager.prefs.putInteger("soundVolume", (int) ((Slider) actor).getValue());
            }
        });
        table.add(sl).minSize(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        table.row();

        sl = new Slider(0, 100, 1, false, ss);
        sl.setValue(ResourceManager.prefs.getInteger("musicVolume", 50));
        sl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ResourceManager.prefs.putInteger("musicVolume", (int) ((Slider) actor).getValue());
            }
        });
        table.add(sl).minSize(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        table.row();

        Button b = new Button(ResourceManager.skin.newDrawable("pixel", Color.GOLD));
        table.add(b).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        b.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget().hit(x, y, true) == null) return;
                loreScreen = new LoreScreen();
                mainScreen.game.setScreen(loreScreen);
            }
        });
        table.row();

        b = new Button(ResourceManager.skin.newDrawable("pixel", Color.GOLD));
        table.add(b).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        table.row();

        b = new Button(ResourceManager.skin.newDrawable("pixel", Color.GOLD));
        table.add(b).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        b.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget().hit(x, y, true) == null) return;
                mainScreen.game.setScreen(mainScreen);
            }
        });
        stage.addListener(new InputListener(){
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    mainScreen.game.setScreen(mainScreen);
                    return true;
                }
                return false;
            }
        });
    }

    private final Color backgroundColor = Color.DARK_GRAY;

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().setColor(backgroundColor);
        pixel.draw(stage.getBatch(), 0, 0, 1080, 1920);
        stage.getBatch().end();

        stage.getViewport().apply();
        stage.act(delta);
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
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
