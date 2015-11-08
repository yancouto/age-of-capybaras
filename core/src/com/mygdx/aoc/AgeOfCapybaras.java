package com.mygdx.aoc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AgeOfCapybaras extends Game {
    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        ResourceManager.init();
        ResourceManager.loadGame();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        // important
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.dispose();
    }

    public static void onPause() {
        ResourceManager.saveGame();
    }

    public static void onResume() {
        if (ResourceManager.prefs != null)
            User.addPast(ResourceManager.prefs.getLong("time", System.currentTimeMillis()));
    }
}
