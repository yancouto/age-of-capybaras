package com.mygdx.aoc.screen;


import com.admob.AdsController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

public class Splash implements GameScreen {
    final private BitmapFont font;
    final private SpriteBatch batch;
    private final float waitTime = 0.01f;
    OrthographicCamera cam;
    private float total;
    private AdsController adsController;
    public static Splash splash;

    public Splash(AdsController adsController) {
        splash = this;
        this.adsController = adsController;
        font = ResourceManager.getFont("goodDog", 180);
        batch = ResourceManager.batch;
        total = 0.f;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
    }

    @Override
    public InputProcessor processor() {
        return null;
    }

    @Override
    public boolean blocksInput() {
        return false;
    }

    @Override
    public void render(float delta) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        font.draw(batch, "hey dude, the startup logo"
                + "would be here. Please click", 1080 * .125f, 1920 - 120, 1080 * 0.75f, Align.center, true);
        batch.end();

        total += delta;
        if (total > waitTime || Gdx.input.isTouched()) {
            while (ScreenManager.popScreen() != this) ;
            ScreenManager.pushScreen(CapybaraScreen.instance());
            MainScreen mainScreen = MainScreen.instance();
            mainScreen.setAdsController(adsController);
            ScreenManager.pushScreen(mainScreen);
            dispose();
        }
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
