package com.mygdx.aoc.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.aoc.AgeOfCapybaras;
import com.mygdx.aoc.manager.ScreenManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class OptionsMenuTest {

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
                ScreenManager.pushScreen(OptionsMenu.instance());

                Gdx.input.getInputProcessor().keyDown(Input.Keys.BACK);

                tests[0] = ScreenManager.topScreen() == MainScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testBackButton() throws Exception {
        done = false;
        tests = new boolean[2];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(OptionsMenu.instance());

                TextButton tb = null;
                System.out.println("size " + OptionsMenu.instance().stage.getActors().size);
                for (Cell c : ((Table) OptionsMenu.instance().stage.getActors().first()).getCells()) {
                    if (!(c.getActor() instanceof TextButton)) continue;
                    if (((TextButton) c.getActor()).getText().toString().equals("Back")) {
                        tb = (TextButton) c.getActor();
                        break;
                    }
                }
                tests[0] = tb != null;
                if (tests[0]) {
                    InputEvent ie = new InputEvent();
                    ie.setTarget(tb);
                    InputListener il = (InputListener) tb.getListeners().get(tb.getListeners().size - 1);
                    il.touchUp(ie, 0, 0, 1, 1);
                }
                tests[1] = ScreenManager.topScreen() == MainScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testLoreButton() throws Exception {
        done = false;
        tests = new boolean[2];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(OptionsMenu.instance());

                TextButton tb = null;
                System.out.println("size " + OptionsMenu.instance().stage.getActors().size);
                for (Cell c : ((Table) OptionsMenu.instance().stage.getActors().first()).getCells()) {
                    if (!(c.getActor() instanceof TextButton)) continue;
                    if (((TextButton) c.getActor()).getText().toString().equals("Lore")) {
                        tb = (TextButton) c.getActor();
                        break;
                    }
                }
                tests[0] = tb != null;
                if (tests[0]) {
                    InputEvent ie = new InputEvent();
                    ie.setTarget(tb);
                    InputListener il = (InputListener) tb.getListeners().get(tb.getListeners().size - 1);
                    il.touchUp(ie, 0, 0, 1, 1);
                }
                tests[1] = ScreenManager.topScreen() == LoreScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testCreditsButton() throws Exception {
        done = false;
        tests = new boolean[2];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                while (!ScreenManager.isEmpty()) ScreenManager.popScreen();
                ScreenManager.pushScreen(OptionsMenu.instance());

                TextButton tb = null;
                System.out.println("size " + OptionsMenu.instance().stage.getActors().size);
                for (Cell c : ((Table) OptionsMenu.instance().stage.getActors().first()).getCells()) {
                    if (!(c.getActor() instanceof TextButton)) continue;
                    if (((TextButton) c.getActor()).getText().toString().equals("Credits")) {
                        tb = (TextButton) c.getActor();
                        break;
                    }
                }
                tests[0] = tb != null;
                if (tests[0]) {
                    InputEvent ie = new InputEvent();
                    ie.setTarget(tb);
                    InputListener il = (InputListener) tb.getListeners().get(tb.getListeners().size - 1);
                    il.touchUp(ie, 0, 0, 1, 1);
                }
                tests[1] = ScreenManager.topScreen() == CreditsScreen.instance();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }
}