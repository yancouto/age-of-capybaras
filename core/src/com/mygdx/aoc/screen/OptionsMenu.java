package com.mygdx.aoc.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

/**
 * Menu where volume can be changed, lore can be seen, and other options can be tweaked.
 */
public class OptionsMenu implements GameScreen {
    private static OptionsMenu optionsMenu;
    public Stage stage;
    private Drawable background;
    private Drawable pixel;

    private OptionsMenu() {
        background = ResourceManager.skin.getDrawable("configBackground");
        pixel = ResourceManager.skin.getDrawable("pixel");

        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.center();

        Slider.SliderStyle ss = new Slider.SliderStyle(ResourceManager.skin.getDrawable("sliderSlider"),
                ResourceManager.skin.getDrawable("sliderKnob"));
        ss.knobDown = ResourceManager.skin.newDrawable("sliderKnob", Color.DARK_GRAY);

        Slider sl = new Slider(0, 100, 1, false, ss);
        sl.setValue(ResourceManager.prefs.getInteger("soundVolume", 50));
        sl.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ResourceManager.prefs.putInteger("soundVolume", (int) ((Slider) actor).getValue());
                ResourceManager.prefs.flush();
            }
        });
        table.add(new Image(ResourceManager.skin.getDrawable("soundEmpty"))).padRight(50);
        table.add(sl).size(1080 * .5f, 1920 * .1f).left();
        table.add(new Image(ResourceManager.skin.getDrawable("soundFull"))).padLeft(50);
        table.row();

        sl = new Slider(0, 100, 1, false, ss);
        sl.setValue(ResourceManager.prefs.getInteger("musicVolume", 50));
        sl.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int val = (int) ((Slider) actor).getValue();
                ResourceManager.prefs.putInteger("musicVolume", val);
                CapybaraScreen.backMusic.setVolume(val / 100.f);
                ResourceManager.prefs.flush();
            }
        });
        table.add(new Image(ResourceManager.skin.getDrawable("musicEmpty"))).padRight(50);
        table.add(sl).size(1080 * .5f, 1920 * .1f).left();
        table.add(new Image(ResourceManager.skin.getDrawable("musicFull"))).padLeft(50);
        table.row();

        TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
        bs.up = ResourceManager.skin.newDrawable("pixel", .5f, .5f, .5f, 1.f);
        bs.over = ResourceManager.skin.newDrawable("pixel", .3f, .3f, .3f, 1.f);
        bs.font = ResourceManager.getFont("goodDog", 100);
        Button b = new TextButton("Lore", bs);
        table.add(b).colspan(3).size(1080 * .6f, 1920 * .1f).pad(1920 * .05f, 0, 1920 * .025f, 0);
        table.row();
        b.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget().hit(x, y, true) == null) return;
                System.out.println("Going to Lore");
                while (ScreenManager.popScreen() != OptionsMenu.this) ;
                ScreenManager.pushScreen(LoreScreen.instance());
            }
        });

        b = new TextButton("Credits", bs);
        table.add(b).colspan(3).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        b.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget().hit(x, y, true) == null) return;
                System.out.println("Going to Credits");
                while (ScreenManager.popScreen() != OptionsMenu.this) ;
                ScreenManager.pushScreen(CreditsScreen.instance());
            }
        });
        table.row();

        b = new TextButton("Back", bs);
        table.add(b).colspan(3).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        b.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget().hit(x, y, true) == null) return;
                while (ScreenManager.popScreen() != OptionsMenu.this) ;
                ScreenManager.pushScreen(CapybaraScreen.instance());
                ScreenManager.pushScreen(MainScreen.instance());
            }
        });
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    while (ScreenManager.popScreen() != OptionsMenu.this) ;
                    ScreenManager.pushScreen(CapybaraScreen.instance());
                    ScreenManager.pushScreen(MainScreen.instance());
                    return true;
                }
                return false;
            }
        });
    }

    public static OptionsMenu instance() {
        if (optionsMenu == null) optionsMenu = new OptionsMenu();
        return optionsMenu;
    }

    @Override
    public InputProcessor processor() {
        return stage;
    }

    @Override
    public boolean blocksInput() {
        return true;
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        Batch b = stage.getBatch();
        b.begin();
        b.setColor(Color.WHITE);
        background.draw(b, 0, 0, 1080, 1920);
        b.end();

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
        ScreenManager.setBackground(new Color(0xedededff));
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
