package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CapybaraTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    boolean[] tests;

    @Test
    public void testEquipAccessory() throws Exception {
        done = false;
        tests = new boolean[10];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                int[] type = {0, 1, 2, 2, 1, 0, 0, 1, 0, 2};
                for (int i = 0; i < 10; i++) {
                    Capybara.helmet = Capybara.head = Capybara.face = null;
                    Accessory acc = Accessory.accessories[0];
                    acc.type = type[i];
                    Capybara.equipAccessory(acc);
                    if (type[i] == 0) tests[i] = Capybara.helmet.equals(acc);
                    if (type[i] == 1) tests[i] = Capybara.head.equals(acc);
                    if (type[i] == 2) tests[i] = Capybara.face.equals(acc);
                }
                done = true;
            }


        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testUnequipAccessory() throws Exception {
        done = false;
        tests = new boolean[10];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                int[] type = {0, 1, 2, 2, 1, 0, 0, 1, 0, 2};
                for (int i = 0; i < 10; i++) {
                    Capybara.helmet = Capybara.head = Capybara.face = null;
                    Accessory acc = Accessory.accessories[0];
                    if (type[i] == 0) Capybara.helmet = acc;
                    if (type[i] == 1) Capybara.head = acc;
                    if (type[i] == 2) Capybara.face = acc;
                    Capybara.unequipAccessory(acc);
                    if (type[i] == 0) tests[i] = Capybara.helmet == null;
                    if (type[i] == 1) tests[i] = Capybara.head == null;
                    if (type[i] == 2) tests[i] = Capybara.face == null;
                }
                done = true;
            }


        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }
}
