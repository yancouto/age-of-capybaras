package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.mygdx.aoc.screen.MainScreen;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpgradeTest {

    @BeforeClass
    public static void setUp() throws Exception {
        if(Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }

//  Main idea of the tests:
//      upgrade01.json -> NextLevelUpgrade
//      upgrade02.json -> GeneratorUpgrade
//      upgrade03.json -> ClickUpgrade
//  In each case, you first should assert that the corresponding upgrade is inside
//  the upgrade Table of MainScreen; Current money and modifier should be stored.
//  Then, when you buy the upgrade (call the tested method), you should assert that
//  money value is subtracted the expected amount and multiplier is increased the
//  expected amount as well. With the exception of NextLevel (when finalAge is not true),
//  its respectives upgrades should not be in upgrade Table of MainScreen anymore.

    Cell c;
    boolean done;
    @Ignore
    public void testBuyGenerator() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                FileHandle upg = Gdx.files.internal("upgrades/upgrade02.json");
                Upgrade u = new Upgrade(upg);
                c = MainScreen.instance().upgrades.getCell(u);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertNotNull(c);
    }

    @Test
    public void testBuyClick() throws Exception {

    }

    @Test
    public void testBuyNextAge() throws Exception {

    }
}