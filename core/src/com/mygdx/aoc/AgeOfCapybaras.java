package com.mygdx.aoc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.manager.ScreenManager;
import com.mygdx.aoc.screen.Splash;

/**
 * Main class, initializes all basic stuff and then creates the SplashScreen
 */
public class AgeOfCapybaras extends Game {
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

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        ResourceManager.game = this;
        ResourceManager.init();
        ScreenManager.init();
        ResourceManager.loadGame();
        ScreenManager.pushScreen(new Splash());
    }

    @Override
    public void render() {
        // important
        super.render();
        ScreenManager.render(Gdx.graphics.getDeltaTime());
        User.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.dispose();
    }
}
