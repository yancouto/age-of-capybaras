package com.mygdx.aoc.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.aoc.AgeOfCapybaras;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ScreenManagerTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    GameScreen gs;

    @Test
    public void testPopScreen() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                ScreenManager.pushScreen(gs);
                ScreenManager.popScreen();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        verify(gs, times(1)).hide();
    }

    boolean sameScreen;

    @Test
    public void testTopScreen() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                ScreenManager.pushScreen(gs);
                sameScreen = ScreenManager.topScreen() == gs;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(sameScreen);
    }

    @Test
    public void testPushScreen() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                ScreenManager.pushScreen(gs);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        verify(gs, times(1)).show();
    }
}