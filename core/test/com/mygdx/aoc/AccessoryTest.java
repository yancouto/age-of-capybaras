package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class AccessoryTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    Accessory acc;
    BigDecimal kapivariumBefore, kapivariumAfter;
    boolean done;

    @Test
    public void testCannotBuyAccessory() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Accessory a : Accessory.accessories) {
                    if (!a.purchased) {
                        acc = a;
                        break;
                    }
                }
                User.kapivarium = BigDecimal.ZERO;
                acc.buyAccessory();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(!acc.purchased);
        assertTrue(User.kapivarium.compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    public void testCanBuyAccessory() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Accessory a : Accessory.accessories) {
                    if (!a.purchased) {
                        acc = a;
                        break;
                    }
                }
                User.kapivarium = acc.price;
                kapivariumBefore = User.kapivarium;
                acc.buyAccessory();
                kapivariumAfter = User.kapivarium;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        BigDecimal wastedKapivarium = kapivariumAfter.subtract(kapivariumBefore);
        assertTrue(acc.purchased);
        assertTrue(User.kapivarium.compareTo(wastedKapivarium) >= 0);
    }

    @Test
    public void testAlreadyHasAccessory() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Accessory a : Accessory.accessories) {
                    if (!a.purchased) {
                        acc = a;
                        break;
                    }
                }
                User.kapivarium = acc.price;
                acc.buyAccessory();
                //Accessory acc is now purchased
                User.kapivarium = acc.price;
                //And User has kapivarium enough to buy it
                kapivariumBefore = User.kapivarium;
                acc.buyAccessory();
                kapivariumAfter = User.kapivarium;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(acc.purchased);
        assertTrue(kapivariumAfter.compareTo(kapivariumBefore) >= 0);
    }


    String name;
    Accessory newAcc;

    @Test
    public void testLoadWithoutSaving() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Accessory a : Accessory.accessories) {
                    if (!a.purchased && !a.equiped) {
                        acc = a;
                        break;
                    }
                }
                name = acc.name;
                acc.purchased = true;
                acc.equiped = true;
                Accessory.loadGame();
                for (Accessory a : Accessory.accessories) {
                    if (a.name.equals(name)) {
                        newAcc = a;
                        break;
                    }
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(!newAcc.purchased);
        assertTrue(!newAcc.equiped);
    }

    boolean p, e;

    @Test
    public void testLoadWithSaving() throws Exception {
        done = false;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (Accessory a : Accessory.accessories) {
                    if (!a.purchased && !a.equiped) {
                        acc = a;
                        break;
                    }
                }
                name = acc.name;
                acc.purchased = true;
                acc.equiped = true;
                Accessory.saveGame();
                Accessory.loadGame();
                for (Accessory a : Accessory.accessories) {
                    if (a.name.equals(name)) {
                        newAcc = a;
                        break;
                    }
                }
                p = newAcc.purchased;
                e = newAcc.equiped;
                newAcc.purchased = false;
                newAcc.equiped = false;
                Accessory.saveGame();
                Accessory.loadGame();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        assertTrue(p);
        assertTrue(e);
    }
}