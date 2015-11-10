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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.Generator;
import com.mygdx.aoc.ResourceManager;
import com.mygdx.aoc.User;

/**
 * Main game screen, where the Matriarch Capybara is and most mechanics will be used.
 */
public class MainScreen implements Screen {
    final AgeOfCapybaras game;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private State state;

    private Button option, accessory, ads, backAccessory;
    private Drawable capybara, pixel;
    private Pixmap capybaraMap;

    private OptionsMenu optionsMenu;

    public MainScreen(AgeOfCapybaras game) {
        this.game = game;
        ResourceManager.loadMain();
        font = ResourceManager.getFont("goodDog", 100);

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
        stage = new Stage(new FitViewport(1080, 1920, cam), ResourceManager.batch);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        table.top();
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
                goToAccessory();
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
                MainScreen.this.game.setScreen(optionsMenu);
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
        optionsMenu = new OptionsMenu(stage, this);

        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK && state != State.Main) {
                    goToMain();
                    return true;
                } else if (keycode == Input.Keys.BACK && state == State.Main) {
                    MainScreen.this.game.setScreen(optionsMenu);
                    return true;
                }
                return false;
            }
        });

        scrollUI();
        pixel = ResourceManager.skin.getDrawable("pixel");
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
        Accessory("Accessory Screen");

        String name;

        State(String s) {
            name = s;
        }
    }

    private void goToAds() {
        System.out.println("Not Yet Implemented");
    }

    /**
     * Changes the screen to the Main screen
     */
    public void goToMain() {
        table.getCells().get(0).setActor(accessory);
        table.getCells().get(1).setActor(option);
        state = State.Main;
    }

    /**
     * Changes the screen to the Accessory screen
     */
    private void goToAccessory() {
        table.getCells().get(0).setActor(ads);
        table.getCells().get(1).setActor(backAccessory);
        state = State.Accessory;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private Color backgroundColor = new Color(.9f, 1, .9f, 1);

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().apply();

        stage.getBatch().begin();
        stage.getBatch().setColor(backgroundColor);
        pixel.draw(stage.getBatch(), 0, 0, 1080, 1920);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE);
        capybara.draw(stage.getBatch(), 0, 0, 1080, 1920);
        font.draw(stage.getBatch(), state.name, 50, 350);
        font.draw(stage.getBatch(), User.toSmallString(User.capybaras.toBigInteger()), 50, 200);
        stage.getBatch().end();

        if(state == State.Main && Gdx.input.justTouched()) {
            int x = Gdx.input.getX(), y = Gdx.input.getY();
            x = (x * capybaraMap.getWidth()) / Gdx.graphics.getWidth();
            y = (y * capybaraMap.getHeight()) / Gdx.graphics.getHeight();
            int color = capybaraMap.getPixel(x, y);
            // alpha != 0
            if ((color & 0xff) != 0) User.capybaraClick();
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
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
