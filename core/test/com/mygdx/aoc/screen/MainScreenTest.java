package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.manager.ScreenManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MainScreenTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    boolean[] tests;

    @Test
    public void testBackKey() throws Exception {
        done = false;
        tests = new boolean[1];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(CapybaraScreen.instance());
                ScreenManager.pushScreen(MainScreen.instance());

                Gdx.input.getInputProcessor().keyDown(Input.Keys.BACK);

                tests[0] = ScreenManager.topScreen() == OptionsMenu.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testOptionsButton() throws Exception {
        done = false;
        tests = new boolean[1];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(CapybaraScreen.instance());
                ScreenManager.pushScreen(MainScreen.instance());

                InputListener is = (InputListener) MainScreen.instance().option.getListeners().get(1);
                is.touchUp(null, 0, 0, 1, 1);

                tests[0] = ScreenManager.topScreen() == OptionsMenu.instance();

                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testAccessoryButton() throws Exception {
        done = false;
        tests = new boolean[1];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(CapybaraScreen.instance());
                ScreenManager.pushScreen(MainScreen.instance());

                InputListener is = (InputListener) MainScreen.instance().accessory.getListeners().get(1);
                is.touchUp(null, 0, 0, 1, 1);

                tests[0] = ScreenManager.topScreen() == AccessoryScreen.instance(null);

                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }
}