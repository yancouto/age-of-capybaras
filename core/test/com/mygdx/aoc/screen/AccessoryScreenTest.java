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

import static org.junit.Assert.*;

public class AccessoryScreenTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    boolean[] tests;
    AccessoryScreen as;

    @Test
    public void testHelmetTab() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                as = AccessoryScreen.instance(null);
                InputListener il = (InputListener) as.helmet.getListeners().get(1);
                il.touchUp(null, 50, 50, 0, 0);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(as.scrollPaneHelmet.isVisible());
        assertTrue(!as.scrollPaneHead.isVisible());
        assertTrue(!as.scrollPaneFace.isVisible());
    }

    @Test
    public void testHeadTab() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                as = AccessoryScreen.instance(null);
                InputListener il = (InputListener) as.head.getListeners().get(1);
                il.touchUp(null, 50, 50, 0, 0);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(as.scrollPaneHead.isVisible());
        assertTrue(!as.scrollPaneHelmet.isVisible());
        assertTrue(!as.scrollPaneFace.isVisible());
    }

    @Test
    public void testFaceTab() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                as = AccessoryScreen.instance(null);
                InputListener il = (InputListener) as.face.getListeners().get(1);
                il.touchUp(null, 50, 50, 0, 0);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(as.scrollPaneFace.isVisible());
        assertTrue(!as.scrollPaneHelmet.isVisible());
        assertTrue(!as.scrollPaneHead.isVisible());
    }

    @Test
    public void testBackButton() throws Exception {
        done = false;
        tests = new boolean[1];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(AccessoryScreen.instance(null));

                as = AccessoryScreen.instance(null);
                InputListener il = (InputListener) as.back.getListeners().get(1);
                il.touchUp(null, 50, 50, 0, 0);

                tests[0] = ScreenManager.topScreen() == MainScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testBackKey() throws Exception {
        done = false;
        tests = new boolean[1];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(AccessoryScreen.instance(null));

                Gdx.input.getInputProcessor().keyDown(Input.Keys.BACK);

                tests[0] = ScreenManager.topScreen() == MainScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }
}