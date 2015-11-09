package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.Generator;
import com.mygdx.aoc.ResourceManager;
import com.mygdx.aoc.User;

public class MainScreen implements Screen {
    final AgeOfCapybaras g;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private State state;
    private boolean hidden = false;

    private Button option, accessory, ads, backAccessory;
    private Drawable capybara, backgroundColor;
    private Pixmap capybaraMap;

    private com.mygdx.aoc.screen.OptionsMenu opt;

    public MainScreen(AgeOfCapybaras game) {
        g = game;
        ResourceManager.loadMain();
        ResourceManager.fontPar.size = 100;
        font = ResourceManager.fontGenDog.generateFont(ResourceManager.fontPar);

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
        stage = new Stage(new FitViewport(1080, 1920, cam), ResourceManager.batch);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        table.top();
        Drawable bad = ResourceManager.skin.getDrawable("badlogic");
        Color[] colors = {Color.FIREBRICK, Color.BLUE, Color.CYAN, Color.FOREST, Color.GOLD};
        Drawable[] ico = new Drawable[5];
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
                goToAcessory();
            }
        });
        option = new Button(ico[1]);
        option.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (option.hit(x, y, true) == null) return;
                System.out.println("Going to Options");
                goToOptions();
            }
        });
        backAccessory = new Button(ico[2]);
        backAccessory.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backAccessory.hit(x, y, true) == null) return;
                System.out.println("Going back");
                goToMain();
            }
        });
        ads = new Button(ico[4]);
        ads.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (ads.hit(x, y, true) == null) return;
                System.out.println("Going to Ads");
                goToAds();
            }
        });

        capybara = ResourceManager.skin.getDrawable("capybara");
        capybaraMap = ResourceManager.skin.get("capybaraMap", Pixmap.class);

        table.add(accessory).maxSize(300).left().top().padLeft(30).padTop(40);
        table.add(option).maxSize(300).expandX().right().top().padRight(30).padTop(40);

        state = State.Main;
        opt = new OptionsMenu(stage, this);

        stage.addListener(new InputListener(){
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK && state != State.Main) {
                    goToMain();
                    return true;
                } else if (keycode == Input.Keys.BACK && state == State.Main) {
                    goToOptions();
                    return true;
                }
                return false;
            }
        });

        scrollUI();
        backgroundColor = ResourceManager.skin.newDrawable("white", .9f, 1, .9f, 1);
    }


    private ScrollPane scrollPane;
    private Table generators, upgrades;

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

    /**
     * Used to identify the current screen state
     */
    private enum State {
        Main("Main Screen"),
        Accessory("Accessory Screen"),
        Options("Options Menu");

        String name;
        State(String s) {
            name = s;
        }
    }

    private void goToOptions() {
        accessory.setTouchable(Touchable.disabled);
        option.setTouchable(Touchable.disabled);
        opt.enabled = true;
        Gdx.input.setInputProcessor(opt.stage);
        state = State.Options;
    }

    private void goToAds() {
        System.out.println("Not Yet Implemented");
    }

    public void goToMain() {
        System.out.println("going to main");
        accessory.setTouchable(Touchable.enabled);
        option.setTouchable(Touchable.enabled);
        table.getCells().get(0).setActor(accessory);
        table.getCells().get(1).setActor(option);
        Gdx.input.setInputProcessor(stage);
        opt.enabled = false;
        state = State.Main;
    }

    private void goToAcessory() {
        table.getCells().get(0).setActor(ads);
        table.getCells().get(1).setActor(backAccessory);
        state = State.Accessory;
    }

    @Override
    public void show() {
        hidden = false;
    }

    @Override
    public void render(float delta) {
        if (hidden) return;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().apply();

        stage.getBatch().begin();
        backgroundColor.draw(stage.getBatch(), 0, 0, 1080, 1920);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE);
        capybara.draw(stage.getBatch(), 0, 0, 1080, 1920);
        font.draw(stage.getBatch(), state.name, 50, 350);
        font.draw(stage.getBatch(), User.toSmallString(User.capybaras.toBigInteger()), 50, 200);
        stage.getBatch().end();
        opt.render(delta, stage.getBatch());

        if(state == State.Main && Gdx.input.justTouched()) {
            float x = Gdx.input.getX(), y = Gdx.input.getY();
            int color = capybaraMap.getPixel((int)(x * capybaraMap.getWidth() / 1080), (int)(y * capybaraMap.getHeight() / 1920));
            // alpha != 0
            if((color & (255 << 24)) != 0) User.capybaraClick();
        }

        User.update(delta);
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
        hidden = true;
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
