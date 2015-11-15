package com.mygdx.aoc.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.Generator;
import com.mygdx.aoc.User;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

import java.math.BigInteger;

/**
 * Main game screen, where the Matriarch Capybara is and most mechanics will be used.
 */
public class MainScreen implements GameScreen {
    private static MainScreen mainScreen;
    private Stage stage;
    private Table table;
    private Button option, accessory;
    private ScrollPane scrollPane;
    private Table generators, upgrades;
    private BitmapFont numberFont, nameFont;

    private MainScreen() {
        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);

        numberFont = ResourceManager.getFont("goodDog", 185);
        nameFont = ResourceManager.getFont("goodDog", 70);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        table.top();
        Color[] colors = {Color.FIREBRICK, Color.BLUE};
        Drawable[] ico = new Drawable[colors.length];
        for (int i = 0; i < colors.length; i++)
            ico[i] = ResourceManager.skin.newDrawable("badlogic", colors[i]);
        accessory = new Button(ico[0]);
        accessory.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (accessory.hit(x, y, true) == null) return;
                System.out.println("Going to Accessory");
                while(ScreenManager.popScreen() != MainScreen.this);
                ScreenManager.pushScreen(AccessoryScreen.instance());
            }
        });
        option = new Button(ico[1]);
        option.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (option.hit(x, y, true) == null) return;
                while (ScreenManager.popScreen() != CapybaraScreen.instance()) ;
                ScreenManager.pushScreen(OptionsMenu.instance());
            }
        });

        table.add(accessory).maxSize(300).left().top().padLeft(30).padTop(40);
        table.add(option).maxSize(300).expandX().right().top().padRight(30).padTop(40);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return CapybaraScreen.instance().touchesCapybara();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (CapybaraScreen.instance().touchesCapybara())
                    User.capybaraClick();
            }

            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    while (ScreenManager.popScreen() != CapybaraScreen.instance()) ;
                    ScreenManager.pushScreen(OptionsMenu.instance());
                    return true;
                }
                return false;
            }
        });
        scrollUI();
    }

    public static MainScreen instance() {
        if (mainScreen == null) mainScreen = new MainScreen();
        return mainScreen;
    }

    /**
     * Creates the UI for the generators and upgrades
     * Maybe accessories will also be here
     */
    private void scrollUI() {
        generators = new Table();
        for (Generator g : Generator.generators) {
            generators.add(g);
            generators.row();
        }

        scrollPane = new ScrollPane(generators);
        scrollPane.setPosition(0, 0);
        scrollPane.setSize(1080, 1920 * .3f);
        stage.addActor(scrollPane);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        Batch batch = stage.getBatch();
        batch.begin();
        BigInteger cap = User.capybaras.toBigInteger();
        numberFont.draw(batch, User.toSmallString(cap, 3), 315, 1920 * .91f);
        nameFont.draw(batch, User.toBla(cap), 250, 1920 * .83f);
        batch.end();

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
        stage.dispose();
    }

    @Override
    public InputProcessor processor() {
        return stage;
    }

    @Override
    public boolean blocksInput() {
        return false;
    }
}
