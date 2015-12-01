package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.manager.ScreenManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreditsScreenTest {
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
                ScreenManager.pushScreen(CreditsScreen.instance());

                Gdx.input.getInputProcessor().keyDown(Input.Keys.BACK);

                tests[0] = ScreenManager.topScreen() != CreditsScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

}