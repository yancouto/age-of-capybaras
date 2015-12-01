package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class GeneratorTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    boolean[] tests;

    @Test
    public void getFromFile() throws Exception {
        done = false;
        tests = new boolean[4];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Generator g = new Generator(Gdx.files.internal("test/00-TestGen.json"));
                tests[0] = "TestGen".equals(g.getName());
                tests[1] = BigDecimal.valueOf(2).equals(g.getCurrentCPS());
                g.buyLevel(false);
                tests[2] = BigDecimal.valueOf(2).equals(g.getCurrentCPS());
                g.buyLevel(false);
                tests[3] = BigDecimal.valueOf(4).equals(g.getCurrentCPS());
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void buyLevelPay() throws Exception {
        done = false;
        tests = new boolean[60];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Generator g = new Generator(Gdx.files.internal("test/00-TestGen.json"));
                BigDecimal prevCPS = User.cps;
                BigDecimal prevCap = User.capybaras = new BigDecimal("1e100");
                int t = 0;
                for (int i = 0; i < 15; i++) {
                    tests[t++] = g.buyLevel(true);
                    tests[t++] = User.capybaras.compareTo(prevCap) < 0;
                    tests[t++] = User.cps.compareTo(prevCPS) > 0;
                    tests[t++] = i + 1 == g.getCurrentLevel();
                    prevCap = User.capybaras;
                    prevCPS = User.cps;
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testMultiplyCPS() throws Exception {
        done = false;
        tests = new boolean[5];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Generator g = new Generator(Gdx.files.internal("test/00-TestGen.json"));
                g.buyLevel(false);
                String[] mult = {"12", "1", ".1", "123e10", "12.12121212e150"};
                BigDecimal a = BigDecimal.valueOf(2);
                for (int i = 0; i < 5; i++) {
                    BigDecimal m = new BigDecimal(mult[i]);
                    g.multiplyCPS(m);
                    a = a.multiply(m);
                    tests[i] = a.compareTo(g.getCurrentCPS()) == 0;
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void buyLevelNoPay() throws Exception {
        done = false;
        tests = new boolean[60];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Generator g = new Generator(Gdx.files.internal("test/00-TestGen.json"));
                BigDecimal prevCPS = User.cps;
                BigDecimal cap = User.capybaras = BigDecimal.valueOf(12);
                int t = 0;
                for (int i = 0; i < 15; i++) {
                    tests[t++] = g.buyLevel(false);
                    tests[t++] = User.capybaras.compareTo(cap) == 0;
                    tests[t++] = User.cps.compareTo(prevCPS) > 0;
                    tests[t++] = i + 1 == g.getCurrentLevel();
                    prevCPS = User.cps;
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

}