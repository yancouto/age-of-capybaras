package com.mygdx.aoc.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.User;
import com.mygdx.aoc.screen.AccessoryScreen;
import com.mygdx.aoc.screen.CapybaraScreen;
import com.mygdx.aoc.screen.MainScreen;
import com.mygdx.aoc.screen.Splash;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                ScreenManager.popScreen();
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
                ScreenManager.popScreen();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        verify(gs, times(1)).show();
    }

    boolean capAccessory, capMain;

    @Test
    public void testCapybaraClick() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (ScreenManager.topScreen() == Splash.splash) {
                    Gdx.app.postRunnable(this);
                    return;
                }
                // changing to MainScreen to be sure
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(CapybaraScreen.instance());
                ScreenManager.pushScreen(AccessoryScreen.instance(null));

                CapybaraScreen bak = CapybaraScreen.instance();
                CapybaraScreen cs = CapybaraScreen.capybaraScreen = mock(CapybaraScreen.class);
                when(cs.touchesCapybara()).thenReturn(true);

                BigDecimal bef = User.capybaras;
                boolean ok = Gdx.input.getInputProcessor().touchDown(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 1, 1);
                if (ok) Gdx.input.getInputProcessor().touchUp(1080 / 2, 1920 / 2, 1, 1);
                capAccessory = ok && User.capybaras.compareTo(bef) != 0;

                // changing to MainScreen
                ScreenManager.popScreen();
                ScreenManager.pushScreen(MainScreen.instance());

                bef = User.capybaras;
                ok = Gdx.input.getInputProcessor().touchDown(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 1, 1);
                if (ok) Gdx.input.getInputProcessor().touchUp(1080 / 2, 1920 / 2, 1, 1);
                capMain = ok && User.capybaras.compareTo(bef) != 0;
                CapybaraScreen.capybaraScreen = bak;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(capAccessory);
        assertTrue(capMain);
    }
}