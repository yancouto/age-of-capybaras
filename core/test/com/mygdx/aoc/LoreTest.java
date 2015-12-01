package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.aoc.manager.ResourceManager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoreTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    boolean[] tests;

    @Test
    public void testGetLore() throws Exception {
        done = false;
        tests = new boolean[12];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                int[] ar = {1, 3, 6, 7, 9, 12};
                String[] name = {"USP's Olympic Streak", "World Domination", "Ancient Egypt",
                        "Middle Ages", "Cold War", "Star Capybara Child"};
                int t = 0;
                for (int i = 0; i < ar.length; i++) {
                    Lore l = new Lore(String.format("lore%02d", ar[i]));
                    tests[t++] = l.getLoreAge() == ar[i];
                    tests[t++] = name[i].equals(l.getLoreName());
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void readLoreData() throws Exception {
        done = false;
        tests = new boolean[3];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Json bak = ResourceManager.json;
                Json cur = ResourceManager.json = mock(Json.class);
                Lore.LoreData ld = new Lore.LoreData();
                ld.age = 1000;
                ld.name = "Empty";
                ld.description = "...";
                when(cur.fromJson(any(Class.class), any(FileHandle.class))).thenReturn(ld);
                Lore l = new Lore("lore01");
                tests[0] = l.getLoreAge() == ld.age;
                tests[1] = ld.name.equals(l.getLoreName());
                tests[2] = ld.description.equals(l.getLoreDescription());
                ResourceManager.json = bak;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }
}