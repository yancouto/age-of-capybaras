package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoreTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done = false;
    int loreAge = -1;
    @Test
    public void testGetLoreAge() throws Exception {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Lore l = new Lore("lore01");
                loreAge = l.getLoreAge();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertEquals(loreAge, 1);
    }
}