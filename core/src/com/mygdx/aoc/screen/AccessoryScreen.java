package com.mygdx.aoc.screen;

import com.admob.AdsController;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.Accessory;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.User;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

import java.math.BigInteger;

/**
 * Accessory game screen, where accessories can be purchased and equiped to the Matriarch Capybara.
 */
public class AccessoryScreen implements GameScreen {
    private static AccessoryScreen accessoryScreen;
    private Stage stage;
    private Table table, tabs;
    private Button back, ads;
    private Button helmet, head, face;
    private ButtonGroup tabsGroup;
    private ScrollPane scrollPaneHelmet, scrollPaneHead, scrollPaneFace;
    private Table accHelmet, accHead, accFace;
    private BitmapFont numberFont, nameFont;
    private AdsController adsController;

    private AccessoryScreen(final AdsController adsController) {
        this.adsController = adsController;

        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);

        numberFont = ResourceManager.getFont("goodDog", 185);
        nameFont = ResourceManager.getFont("goodDog", 70);

        table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        table.top();


        ads = new Button(ResourceManager.skin.getDrawable("adsButton"),
                ResourceManager.skin.newDrawable("adsButton", Color.DARK_GRAY));
        ads.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Requesting ad");
                adsController.requestAd();
                System.out.println("Ad requested");
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (ads.hit(x, y, true) == null) return;
                System.out.println("AQUIIIIII");
                adsController.displayAd();
                System.out.println("AQUIIIIII222");


            }
        });
        back = new Button(ResourceManager.skin.getDrawable("backButton1"),
                ResourceManager.skin.newDrawable("backButton1", Color.DARK_GRAY));
        back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (back.hit(x, y, true) == null) return;
                System.out.println("Going back");
                while (ScreenManager.popScreen() != AccessoryScreen.instance(adsController)) ;
                ScreenManager.pushScreen(MainScreen.instance());
            }
        });

        table.add(ads).maxSize(300).left().top().padLeft(30).padTop(40);
        table.add(back).maxSize(300).expandX().right().top().padRight(30).padTop(40);

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

        TextButton.TextButtonStyle textButtonStyle;
        textButtonStyle = new TextButton.TextButtonStyle(
                ResourceManager.skin.newDrawable("pixel", Color.GOLD), // up
                ResourceManager.skin.newDrawable("pixel", Color.GOLDENROD), // down
                ResourceManager.skin.newDrawable("pixel", Color.ORANGE), // check
                ResourceManager.getFont("goodDog", Math.round(1920 * .065f)));
        textButtonStyle.disabled = ResourceManager.skin.newDrawable("pixel", Color.LIGHT_GRAY);
        textButtonStyle.disabledFontColor = Color.LIGHT_GRAY;

        helmet = new TextButton("Helmet", textButtonStyle);
        helmet.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (helmet.hit(x, y, true) == null) return;
                scrollPaneHead.setVisible(false);
                scrollPaneFace.setVisible(false);
                scrollPaneHelmet.setVisible(true);
            }
        });

        head = new TextButton("Head", textButtonStyle);
        head.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (head.hit(x, y, true) == null) return;
                scrollPaneHelmet.setVisible(false);
                scrollPaneFace.setVisible(false);
                scrollPaneHead.setVisible(true);
            }
        });

        face = new TextButton("Face", textButtonStyle);
        face.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (face.hit(x, y, true) == null) return;
                scrollPaneHelmet.setVisible(false);
                scrollPaneHead.setVisible(false);
                scrollPaneFace.setVisible(true);
            }
        });

        helmet.setChecked(true);
        head.setChecked(false);
        face.setChecked(false);

        tabsGroup = new ButtonGroup(helmet, head, face);
        tabsGroup.setMaxCheckCount(1);
        tabsGroup.setMinCheckCount(1);

        tabs = new Table();
        tabs.setFillParent(true);
        tabs.setDebug(true);
        stage.addActor(tabs);

        tabs.bottom();
        tabs.add(helmet).maxSize(350).center().bottom().padRight(10).padLeft(40).padBottom(1920 * .2f).padTop(0).width(350);
        tabs.add(head).maxSize(350).center().bottom().padRight(10).padLeft(10).padBottom(1920 * .2f).padTop(0).width(350);
        tabs.add(face).maxSize(350).center().bottom().padRight(40).padLeft(10).padBottom(1920 * .2f).padTop(0).width(350);

        scrollUI();
    }

    public static AccessoryScreen instance(AdsController adsController) {
        if (accessoryScreen == null) accessoryScreen = new AccessoryScreen(adsController);
        return accessoryScreen;
    }

    public void setAdsController(AdsController adsController) {
        this.adsController = adsController;
    }

    /**
     * Creates the UI for the accessories
     */
    private void scrollUI() {
        accHelmet = new Table();
        accHead = new Table();
        accFace = new Table();
        for (Accessory a : Accessory.accessories) {
            if (a.type == 0) {
                accHelmet.add(a);
                accHelmet.row();
            } else if (a.type == 1) {
                accHead.add(a);
                accHead.row();
            } else {
                accFace.add(a);
                accFace.row();
            }
        }

        scrollPaneHelmet = new ScrollPane(accHelmet);
        scrollPaneHelmet.setPosition(0, 0);
        scrollPaneHelmet.setSize(1080, 1920 * .2f);
        scrollPaneHelmet.setVisible(true);
        stage.addActor(scrollPaneHelmet);

        scrollPaneHead = new ScrollPane(accHead);
        scrollPaneHead.setPosition(0, 0);
        scrollPaneHead.setSize(1080, 1920 * .2f);
        scrollPaneHead.setVisible(false);
        stage.addActor(scrollPaneHead);

        scrollPaneFace = new ScrollPane(accFace);
        scrollPaneFace.setPosition(0, 0);
        scrollPaneFace.setSize(1080, 1920 * .2f);
        scrollPaneFace.setVisible(false);
        stage.addActor(scrollPaneFace);
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
