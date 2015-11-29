package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoreTest {

    @Before
    public void setUp() throws Exception {
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        LwjglApplicationConfiguration.disableAudio = true;
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    @After
    public void tearDown() throws Exception {
        Gdx.app.exit();
    }

    boolean loreAgeDone = false;
    @Test
    public void testGetLoreAge() throws Exception {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Lore l = new Lore("lore01");
                loreAgeDone = true;
            }
        });
        while (!loreAgeDone) Thread.sleep(10);
        System.out.println("Finally");
    }
}