package com.mygdx.aoc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.aoc.screen.Splash;

public class AgeOfCapybaras extends Game {
    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        ResourceManager.init();
        ResourceManager.loadGame();
        this.setScreen(new Splash(this));
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

    /**
     * Called when the game is closed or when the user pauses the game
     * Pausing is done by minimizing the game by pressing the home button
     */
    public static void onPause() {
        ResourceManager.saveGame();
    }

    /**
     * Called when the game starts or when it becomes unpaused
     * Example: The user minimized the game but returned to it later
     */
    public static void onResume() {
        if (ResourceManager.prefs != null)
            User.addPast(ResourceManager.prefs.getLong("time", System.currentTimeMillis()));
    }
}
