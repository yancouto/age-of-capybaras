package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.Capybara;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Screen where the Matriarch Capybara is
 */
public class CapybaraScreen implements GameScreen {
    private static CapybaraScreen capybaraScreen;
    private static int curAge;
    private static Texture back;
    private Stage stage;
    private Drawable capybara, pixel;
    private Pixmap capybaraMap;
    private Deque<Capybara> caps;

    private CapybaraScreen() {
        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);

        capybara = ResourceManager.skin.getDrawable("capybara");
        capybaraMap = ResourceManager.skin.get("capybaraMap", Pixmap.class);
        pixel = ResourceManager.skin.getDrawable("pixel");

        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    while (ScreenManager.popScreen() != CapybaraScreen.this) ;
                    ScreenManager.pushScreen(OptionsMenu.instance());
                    return true;
                }
                return false;
            }
        });
        caps = new ArrayDeque<Capybara>();
    }

    public static void loadGame() {
        curAge = ResourceManager.prefs.getInteger("gameAge", 1);
        updateAge();
    }

    public static void saveGame() {
        ResourceManager.prefs.putInteger("gameAge", curAge);
    }

    public static CapybaraScreen instance() {
        if (capybaraScreen == null) capybaraScreen = new CapybaraScreen();
        return capybaraScreen;
    }

    public static int currentAge() {
        return curAge;
    }

    public static void advanceAge() {
        curAge++;
        updateAge();
    }

    private static void updateAge() {
        if (back != null) back.dispose();
        back = new Texture(Gdx.files.internal(String.format("eras/era%02d.png", curAge)));
    }

    public void addCapybara() {
        if (!caps.isEmpty() && System.currentTimeMillis() - caps.peekLast().creation < 1000) return;
        Capybara c = new Capybara(1080 * .5f, 1920 * .3f);
        caps.addLast(c);
        stage.addActor(c);
        while (caps.size() > 15) caps.pollFirst().remove();
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

    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        Batch b = stage.getBatch();
        b.begin();
        b.setColor(Color.WHITE);
        b.draw(back, 0, 0, 1080, 1920);
        b.end();

        stage.act(delta);
        stage.draw();

        b.begin();
        b.setColor(Color.WHITE);
        capybara.draw(b, 0, 0, 1080, 1920);
        b.end();
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
        back.dispose();
    }

    public boolean touchesCapybara() {
        int x = Gdx.input.getX(), y = Gdx.input.getY();
        x = (x * capybaraMap.getWidth()) / Gdx.graphics.getWidth();
        y = (y * capybaraMap.getHeight()) / Gdx.graphics.getHeight();
        int color = capybaraMap.getPixel(x, y);
        // alpha != 0
        return ((color & 0xff) != 0);
    }
}
