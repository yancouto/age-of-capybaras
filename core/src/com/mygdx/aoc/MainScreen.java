package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen {
    final AgeOfCapybaras g;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private String currentScene;

    private Button option, accessory, ads, backOptions, backAccessory;

    public MainScreen(AgeOfCapybaras game) {
        g = game;
        ResourceManager.loadMain();
        font = ResourceManager.skin.getFont("bigDog");

        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
        stage = new Stage(new ScreenViewport(cam), ResourceManager.batch);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        table.top();
        Drawable bad = ResourceManager.skin.getDrawable("badlogic");
        Color[] colors = {Color.FIREBRICK, Color.BLUE, Color.CYAN, Color.FOREST, Color.GOLD};
        ColorDrawable[] ico = new ColorDrawable[5];
        for(int i = 0; i < colors.length; i++)
            ico[i] = new ColorDrawable(bad, colors[i]);
        accessory = new Button(ico[0]);
        accessory.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Going to Acessory");
                goToAcessory();
                return true;
            }
        });
        option = new Button(ico[1]);
        option.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Going to Options");
                goToOptions();
                return true;
            }
        });
        InputListener backListener = new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Going back");
                goToMain();
                return true;
            }
        };
        backAccessory = new Button(ico[2]);
        backAccessory.addListener(backListener);
        backOptions = new Button(ico[3]);
        backOptions.addListener(backListener);
        ads = new Button(ico[4]);
        ads.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Going to Ads");
                goToAds();
                return true;
            }
        });

        final Button capybara = new Button(ResourceManager.skin.getDrawable("capybara"));
        capybara.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        table.add(accessory).maxSize(300).left().top().padLeft(30).padTop(40);
        table.add(option).maxSize(300).expandX().right().top().padRight(30).padTop(40);
        table.row();
        float mxw = 1080 * .9f, mxh = 1920 * .8f;
        float mn = Math.min(mxw / capybara.getWidth(), mxh / capybara.getHeight());
        table.add(capybara).top().left().padTop(1920 * .025f).padLeft(1080 * .05f).maxSize(mn * capybara.getWidth(), mn * capybara.getHeight());
        currentScene = "Main Screen";
    }

    private void goToOptions() {
        table.getCells().get(0).setActor(backOptions);
        table.getCells().get(1).clearActor();
        currentScene = "Options Menu";
    }

    private void goToAds() {
        System.out.println("Not Yet Implemented");
    }

    private void goToMain() {
        table.getCells().get(0).setActor(accessory);
        table.getCells().get(1).setActor(option);
        currentScene = "Main Screen";
    }

    private void goToAcessory() {
        table.getCells().get(0).setActor(ads);
        table.getCells().get(1).setActor(backAccessory);
        currentScene = "Accessory Screen";
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        stage.getBatch().begin();
        font.draw(stage.getBatch(), currentScene, 50, 200);
        stage.getBatch().end();

        User.update(delta);
    }

    @Override
    public void resize(int width, int height)  {
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
}
