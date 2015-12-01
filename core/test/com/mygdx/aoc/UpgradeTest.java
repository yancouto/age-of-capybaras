package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.screen.CapybaraScreen;
import com.mygdx.aoc.screen.MainScreen;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

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
        ResourceManager.prefs.clear();
    }

//  Main idea of the tests:
//  In each case, you first should assert that the corresponding upgrade is inside
//  the upgrade Table of MainScreen; Current money and modifier should be stored.
//  Then, when you buy the upgrade (call the tested method), you should assert that
//  money value is subtracted the expected amount and multiplier is increased the
//  expected amount as well. With the exception of NextLevel (when finalAge is not true),
//  its respectives upgrades should not be in upgrade Table of MainScreen anymore

    Cell cellBefore, cellAfter;
    Upgrade upg;
    BigDecimal capybarasBefore, capybarasAfter;
    BigDecimal cpcBefore, cpsBefore, curCpsBefore;
    int ageBefore;
    boolean done;

    @Test
    public void testBuyGenerator() throws Exception {
        done = false;
        cellBefore = cellAfter = null;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Upgrade u : Upgrade.upgrades) {
                    if (u.getType().equals("generator")) {
                        upg = u;
                        break;
                    }
                }
                if (User.capybaras.compareTo(upg.getCost()) < 0)
                    User.capybaras = upg.getCost();
                if (upg.getGen().getCurrentLevel() <= 0)
                    upg.getGen().setCurrentLevel(1);
                cellBefore = MainScreen.instance().upgrades.getCell(upg);
                cpsBefore = User.cps;
                curCpsBefore = upg.getGen().getCurrentCPS();
                capybarasBefore = User.capybaras;
                upg.buyGenerator(true);
                capybarasAfter = User.capybaras;
                cellAfter = MainScreen.instance().upgrades.getCell(upg);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertNotNull(cellBefore);
        assertNull(cellAfter);
        BigDecimal newCps = curCpsBefore.multiply(upg.getMultiplier());
        assertEquals(upg.getGen().getCurrentCPS(), curCpsBefore.multiply(upg.getMultiplier()));
        assertEquals(User.cps, cpsBefore.add(newCps.subtract(curCpsBefore)));
    }

    @Test
    public void testBuyClick() throws Exception {
        done = false;
        cellBefore = cellAfter = null;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Upgrade u : Upgrade.upgrades) {
                    if (u.getType().equals("click")) {
                        upg = u;
                        break;
                    }
                }
                if (User.capybaras.compareTo(upg.getCost()) < 0)
                    User.capybaras = upg.getCost();
                cellBefore = MainScreen.instance().upgrades.getCell(upg);
                cpcBefore = User.cpc;
                capybarasBefore = User.capybaras;
                upg.buyClick(true);
                capybarasAfter = User.capybaras;
                cellAfter = MainScreen.instance().upgrades.getCell(upg);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertNotNull(cellBefore);
        assertNull(cellAfter);
        assertEquals(upg.getCost(), capybarasBefore.subtract(capybarasAfter));
        assertEquals(User.cpc, cpcBefore.multiply(upg.getMultiplier()));
    }

//  TODO: Still remains the case when finalAge is true: The cell should not display after bought
//  TODO: Fix cost subtraction error
    @Test
    public void testBuyNextAge() throws Exception {
        done = false;
        cellBefore = cellAfter = null;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Upgrade u : Upgrade.upgrades) {
                    if (u.getType().equals("age") && !u.isFinalAge()) {
                        upg = u;
                        break;
                    }
                }
                if (User.capybaras.compareTo(upg.getCost()) < 0)
                    User.capybaras = upg.getCost();
                cellBefore = MainScreen.instance().upgrades.getCell(upg);
                ageBefore = CapybaraScreen.currentAge();
                capybarasBefore = User.capybaras;
                upg.buyNextAge(true);
                capybarasAfter = User.capybaras;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertNotNull(cellBefore);
//        assertEquals(upg.getCost(), capybarasBefore.subtract(capybarasAfter));
        assertEquals(CapybaraScreen.currentAge(), ageBefore + 1);
    }
}