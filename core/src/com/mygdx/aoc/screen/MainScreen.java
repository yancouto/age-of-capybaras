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
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.Generator;
import com.mygdx.aoc.Upgrade;
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
    private ScrollPane scrollPaneGenerators, scrollPaneUpgrades;
    private Table generators, upgrades;
    private BitmapFont numberFont, nameFont;

    private MainScreen() {
        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);

        numberFont = ResourceManager.getFont("goodDog", 185);
        nameFont = ResourceManager.getFont("goodDog", 70);

        table = new Table();
        table.setFillParent(true);
        table.top();
        table.setDebug(true);

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

        stage.addActor(table);

        TextButton.TextButtonStyle textButtonStyle;
        textButtonStyle = new TextButton.TextButtonStyle(
                ResourceManager.skin.newDrawable("pixel", Color.GOLD), // up
                ResourceManager.skin.newDrawable("pixel", Color.GOLDENROD), // down
                ResourceManager.skin.newDrawable("pixel", Color.ORANGE), // check
                ResourceManager.getFont("goodDog", Math.round(1920 * .065f)));

        TextButton gens = new TextButton("Generators", textButtonStyle);
        TextButton upgs = new TextButton("Upgrades", textButtonStyle);
        gens.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scrollPaneGenerators.setVisible(true);
                scrollPaneUpgrades.setVisible(false);
            }
        });
        upgs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scrollPaneUpgrades.setVisible(true);
                scrollPaneGenerators.setVisible(false);
            }
        });

        ButtonGroup tabsGroup = new ButtonGroup(gens, upgs);
        tabsGroup.setMaxCheckCount(1);
        tabsGroup.setMinCheckCount(1);

        gens.setChecked(true);
        upgs.setChecked(false);

        Table tabs = new Table();
        tabs.setFillParent(true);
        tabs.bottom().left();
        tabs.setDebug(true);

        tabs.add(gens).size(gens.getWidth() + 100, gens.getHeight()).pad(0, 0, 1920 * .3f, 0);
        tabs.add(upgs).size(upgs.getWidth() + 100, upgs.getHeight()).pad(0, 1080 - gens.getWidth() - upgs.getWidth() - 200, 1920 * .3f, 0);

        stage.addActor(tabs);

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

        upgrades = new Table();
        for (Upgrade u : Upgrade.upgrades) {
            upgrades.add(u);
            upgrades.row();
        }

        scrollPaneGenerators = new ScrollPane(generators);
        scrollPaneGenerators.setPosition(0, 0);
        scrollPaneGenerators.setSize(1080, 1920 * .3f);
        stage.addActor(scrollPaneGenerators);

        scrollPaneUpgrades = new ScrollPane(upgrades);
        scrollPaneUpgrades.setPosition(0, 0);
        scrollPaneUpgrades.setSize(1080, 1920 * .3f);
        stage.addActor(scrollPaneUpgrades);
        scrollPaneUpgrades.setVisible(false);
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
