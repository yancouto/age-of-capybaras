package com.mygdx.aoc.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import org.mockito.InOrder;

import java.math.BigDecimal;
import java.util.Stack;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
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

    boolean[] tests;
    boolean done;

    GameScreen gs, gs2, gs3;

    @Test
    public void testRender() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                gs2 = mock(GameScreen.class);
                gs3 = mock(GameScreen.class);
                ScreenManager.pushScreen(gs);
                ScreenManager.pushScreen(gs2);
                ScreenManager.pushScreen(gs3);
                ScreenManager.render(0);
                for (int i = 0; i < 3; i++) ScreenManager.popScreen();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        InOrder ord = inOrder(gs, gs2, gs3);
        ord.verify(gs).render(0);
        ord.verify(gs2).render(0);
        ord.verify(gs3).render(0);
    }

    InputProcessor ip1, ip2, ip3;

    @Test
    public void testInput() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                gs2 = mock(GameScreen.class);
                gs3 = mock(GameScreen.class);
                ip1 = mock(InputProcessor.class);
                ip2 = mock(InputProcessor.class);
                ip3 = mock(InputProcessor.class);
                when(gs.processor()).thenReturn(ip1);
                when(gs2.processor()).thenReturn(ip2);
                when(gs3.processor()).thenReturn(ip3);
                ScreenManager.pushScreen(gs);
                ScreenManager.pushScreen(gs2);
                ScreenManager.pushScreen(gs3);
                Gdx.input.getInputProcessor().keyDown(Input.Keys.BUTTON_SELECT);
                for (int i = 0; i < 3; i++) ScreenManager.popScreen();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        InOrder ord = inOrder(ip3, ip2, ip1);
        ord.verify(ip3).keyDown(Input.Keys.BUTTON_SELECT);
        ord.verify(ip2).keyDown(Input.Keys.BUTTON_SELECT);
        ord.verify(ip1).keyDown(Input.Keys.BUTTON_SELECT);
    }

    @Test
    public void testIsEmpty() throws Exception {
        done = false;
        tests = new boolean[2];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                boolean bak = ScreenManager.isEmpty();
                ScreenManager.pushScreen(gs);
                tests[0] = !ScreenManager.isEmpty();
                ScreenManager.popScreen();
                tests[1] = ScreenManager.isEmpty() == bak;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

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

    // testing pushScreen, popScreen, topScreen and isEmpty
    @Test
    public void testAllCommands() throws Exception {
        tests = new boolean[5];
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gs = mock(GameScreen.class);
                gs2 = mock(GameScreen.class);
                gs3 = mock(GameScreen.class);
                Stack<GameScreen> bak = new Stack<GameScreen>();
                while (!ScreenManager.isEmpty()) bak.push(ScreenManager.popScreen());

                ScreenManager.pushScreen(gs);
                ScreenManager.popScreen();
                tests[0] = ScreenManager.isEmpty();
                ScreenManager.pushScreen(gs2);
                ScreenManager.pushScreen(gs);
                tests[1] = ScreenManager.topScreen() == gs;
                tests[2] = ScreenManager.popScreen() == gs;
                ScreenManager.pushScreen(gs3);
                tests[3] = !ScreenManager.isEmpty();
                ScreenManager.popScreen();
                tests[4] = ScreenManager.topScreen() == gs2;
                ScreenManager.popScreen();

                while (!bak.isEmpty()) ScreenManager.pushScreen(bak.pop());

                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        boolean[] allTrue = {true, true, true, true, true};
        assertArrayEquals(tests, allTrue);
        verify(gs, times(2)).show();
        verify(gs, times(2)).hide();
        verify(gs2, times(1)).show();
        verify(gs2, times(1)).hide();
        verify(gs3, times(1)).show();
        verify(gs3, times(1)).hide();
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