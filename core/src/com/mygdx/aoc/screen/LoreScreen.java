package com.mygdx.aoc.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.aoc.Lore;
import com.mygdx.aoc.manager.GameScreen;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;

import java.util.ArrayList;

public class LoreScreen implements GameScreen {
    private static LoreScreen lore;
    private Stage stage;
    private ArrayList<TextButton> disabledButtons = new ArrayList<TextButton>();
    private TextButton currentAgeButton, checkedButton;

    private Label.LabelStyle labelStyle =
            new Label.LabelStyle(ResourceManager.getFont("goodDog", 70), Color.WHITE);
    final private Label description = new Label(findLoreDescription(CapybaraScreen.currentAge()), labelStyle);
    final ScrollPane scrollPaneDescription = new ScrollPane(description);
    private TextButton.TextButtonStyle textButtonStyle;
    private ScrollPane scrollPaneLores;
    private Drawable background;

    private LoreScreen() {
        stage = new Stage(new FitViewport(1080, 1920), ResourceManager.batch);
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    while (ScreenManager.popScreen() != LoreScreen.this) ;
                    ScreenManager.pushScreen(OptionsMenu.instance());
                    return true;
                }
                return false;
            }
        });
        scrollPaneLores();
        background = ResourceManager.skin.getDrawable("loreBackground");
    }

    public static LoreScreen instance() {
        if (lore == null) lore = new LoreScreen();
        return lore;
    }

    private void scrollPaneLores() {
        textButtonStyle = new TextButton.TextButtonStyle(
                ResourceManager.skin.newDrawable("pixel", Color.GOLD), // up
                ResourceManager.skin.newDrawable("pixel", Color.GOLDENROD), // down
                ResourceManager.skin.newDrawable("pixel", Color.ORANGE), // check
                ResourceManager.getFont("goodDog", 70));
        textButtonStyle.disabled = ResourceManager.skin.newDrawable("pixel", Color.LIGHT_GRAY);
        textButtonStyle.disabledFontColor = Color.LIGHT_GRAY;

        description.setAlignment(Align.center);
        description.setWrap(true);

        Table lores = new Table();

        scrollPaneLores = new ScrollPane(lores);

        for (Lore l : Lore.lores) {
            final TextButton b;
            if (l.getLoreAge() > CapybaraScreen.currentAge()) {
                b = new TextButton(l.getLoreAge() + ". ?????", textButtonStyle);
                b.setDisabled(true);
                disabledButtons.add(b);
            } else {
                b = new TextButton(l.getLoreAge() + ". " + l.getLoreName(),
                        textButtonStyle);
            }
            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!b.isDisabled()) {
                        int i = Integer.parseInt(b.getText().toString().split("\\.\\s")[0]);
                        description.setText(findLoreDescription(i));
                        scrollPaneDescription.setScrollY(0);
                        checkedButton.setChecked(false);
                        b.setChecked(true);
                        checkedButton = b;
                    }
                }
            });
            if (l.getLoreAge() == CapybaraScreen.currentAge()) {
                b.setChecked(true);
                currentAgeButton = b;
                checkedButton = b;
            }
            lores.add(b).size(1080, 1920 * .075f).pad(0, 0, 1920 * .0025f, 0);
            lores.row();
        }

        Table table = new Table();
        table.add(scrollPaneDescription).size(1080, 1920 * .65f).pad(0, 0, 1920 * .025f, 0);
        table.row();
        table.add(scrollPaneLores).size(1080, 1920 * .3f);
        table.row();
        table.setFillParent(true);
        stage.addActor(table);
    }

    private String findLoreDescription(int i) {
        for (Lore l : Lore.lores)
            if (l.getLoreAge() == i)
                return l.getLoreDescription();
        return "";
    }

    private String findLoreName(int i) {
        for (Lore l : Lore.lores)
            if (l.getLoreAge() == i)
                return l.getLoreName();
        return "";
    }

    private void updateEnable() {
        for (TextButton b : disabledButtons) {
            int i = Integer.parseInt(b.getText().toString().split("\\.\\s")[0]);
            if (i <= CapybaraScreen.currentAge()) {
                b.setDisabled(false);
                b.setStyle(textButtonStyle);
                b.setText(i + ". " + findLoreName(i));
                if (i == CapybaraScreen.currentAge())
                    currentAgeButton = b;
                //disabled.remove(b);  To avoid ERROR: java.util.ConcurrentModificationException
            }
        }
        checkedButton.setChecked(false);
        currentAgeButton.setChecked(true);
        checkedButton = currentAgeButton;
        description.setText(findLoreDescription(CapybaraScreen.currentAge()));
        scrollPaneDescription.setScrollY(0);
        System.out.print(scrollPaneLores.getMaxY());
        if (CapybaraScreen.currentAge() > 2)
            scrollPaneLores.setScrollY((CapybaraScreen.currentAge() - 2) * 1920 * .0775f);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        Batch b = stage.getBatch();
        b.begin();
        b.setColor(Color.WHITE);
        background.draw(b, 0, 0, 1080, 1920);
        b.end();

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        updateEnable();
        ScreenManager.setBackground(new Color(0xdcc57fff));
    }

    @Override
    public boolean blocksInput() {
        return false;
    }

    @Override
    public InputProcessor processor() {
        return stage;
    }
}
