package com.mygdx.aoc.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

/**
 * Shows credits
 */
public class CreditsScreen implements GameScreen {
    private static CreditsScreen creditsScreen;
    private final String text = "Credits\n\nMusic\n" +
            "Lurid Delusion - matthew.pablo\n" +
            "Heroic Minority - Alexandr Zhelanov\n" +
            "Melancholy RPG - Alexandr Zhelanov\n";
    private Label label;
    private Stage stage;

    private CreditsScreen() {
        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);
        label = new Label(text, new Label.LabelStyle(ResourceManager.getFont("goodDog", 80), Color.WHITE));
        label.setAlignment(Align.center);
        label.setPosition(0, 0);
        label.setVisible(true);
        Table t = new Table();
        t.setFillParent(true);
        ScrollPane sp = new ScrollPane(label);
        t.add(sp).expand().center();
        stage.addActor(t);
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    while (ScreenManager.popScreen() != CreditsScreen.this) ;
                    ScreenManager.pushScreen(OptionsMenu.instance());
                    return true;
                }
                return false;
            }
        });
    }

    public static CreditsScreen instance() {
        if (creditsScreen == null) creditsScreen = new CreditsScreen();
        return creditsScreen;
    }

    @Override
    public InputProcessor processor() {
        return stage;
    }

    @Override
    public boolean blocksInput() {
        return false;
    }

    @Override
    public void show() {
        ScreenManager.setBackground(Color.GRAY);
    }

    @Override
    public void render(float delta) {
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
