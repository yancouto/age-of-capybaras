package com.mygdx.aoc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class OptionsMenu {
    public boolean enabled = false;
    private Drawable back, bg;
    public Stage stage;
    private Table table;
    private MainScreen m;

    public OptionsMenu(Stage s, MainScreen ms) {
        m = ms;
        bg = ResourceManager.skin.newDrawable("white", 0, 0, 0, .6f);
        back = ResourceManager.skin.newDrawable("white", Color.BROWN);

        stage = new Stage(s.getViewport(), s.getBatch());
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Slider.SliderStyle ss = new Slider.SliderStyle(back, ResourceManager.skin.newDrawable("white", Color.GOLD));
        ss.background = ResourceManager.skin.newDrawable("white", Color.OLIVE);
        ss.background.setMinWidth(1080 * .6f);
        ss.background.setMinHeight(1920 * .07f);
        ss.knob = ResourceManager.skin.newDrawable("white", Color.BLACK);
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

        Button b = new Button(ResourceManager.skin.newDrawable("white", Color.GOLD));
        table.add(b).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        table.row();

        b = new Button(ResourceManager.skin.newDrawable("white", Color.GOLD));
        table.add(b).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        table.row();

        b = new Button(ResourceManager.skin.newDrawable("white", Color.GOLD));
        table.add(b).size(1080 * .6f, 1920 * .1f).pad(1920 * .025f, 0, 1920 * .025f, 0);
        b.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                m.goToMain();
                return true;
            }
        });
    }

    public void render(float delta, Batch batch) {
        if (!enabled) return;
        stage.getBatch().begin();
        bg.draw(stage.getBatch(), 0, 0, 1080, 1920);
        back.draw(stage.getBatch(), 1080 * .1f, 1920 * .1f, 1080 * .8f, 1920 * .8f);
        stage.getBatch().end();

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }
}
