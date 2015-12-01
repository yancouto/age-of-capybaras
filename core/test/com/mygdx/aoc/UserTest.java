package com.mygdx.aoc;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.aoc.screen.CapybaraScreen;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {

    @Before
    public void setUp() throws Exception {
        if (Gdx.app != null) return;
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(new AgeOfCapybaras(null), conf);
    }

    boolean done;
    boolean[] tests;

    @Test
    public void readPowersCorrectRead() throws Exception {
        done = false;
        tests = new boolean[3];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Files bak = Gdx.files;
                Files cur = Gdx.files = mock(Files.class);
                when(cur.internal(anyString())).thenReturn(bak.internal("test/powers.txt"));
                User.readPowers();
                tests[0] = "power1".equals(User.powerName[0]);
                tests[1] = "power2".equals(User.powerName[1]);
                tests[2] = "power3".equals(User.powerName[2]);
                Gdx.files = bak;
                User.readPowers();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void readPowersHandleException() throws Exception {
        done = false;
        tests = new boolean[3];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Files bak = Gdx.files;
                Files cur = Gdx.files = mock(Files.class);
                when(cur.internal(anyString())).thenReturn(null);
                User.readPowers();
                tests[0] = User.powerName != null;
                tests[1] = User.powerName.length == 1;
                tests[2] = tests[1] && "".equals(User.powerName[0]);
                Gdx.files = bak;
                User.readPowers();
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testMultiplyCPC() throws Exception {
        done = false;
        tests = new boolean[5];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                String[] bef = {"0", "100", "345123213729394592374592734", "234523452345", ".0002"};
                String[] mul = {"1.12e14", "1", "1232342354535634563456", "0", "7736763.1231231e7"};
                for (int i = 0; i < 5; i++) {
                    BigDecimal cpcBefore = User.cpc = new BigDecimal(bef[i]);
                    BigDecimal mult = new BigDecimal(mul[i]);
                    User.multiplyCPC(mult);
                    tests[i] = User.cpc.equals(cpcBefore.multiply(mult));
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    CapybaraScreen cs;

    @Test
    public void testCapybaraClick() throws Exception {
        done = false;
        tests = new boolean[1];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                User.cpc = new BigDecimal("1e10");
                BigDecimal bef = User.capybaras;
                CapybaraScreen bak = CapybaraScreen.instance();
                cs = CapybaraScreen.capybaraScreen = mock(CapybaraScreen.class);
                User.capybaraClick();
                tests[0] = bef.add(User.cpc).equals(User.capybaras);
                CapybaraScreen.capybaraScreen = bak;
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
        verify(cs, atLeastOnce()).addCapybara();
        verify(cs, atLeastOnce()).addWin(anyString());
    }

    @Test
    public void testAddPast() throws Exception {
        done = false;
        tests = new boolean[2];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                User.cps = new BigDecimal("1e10");
                BigDecimal bef = User.capybaras;
                User.addPast(System.currentTimeMillis() - 1);
                tests[0] = User.capybaras.compareTo(bef) > 0;
                bef = User.capybaras;
                User.cps = BigDecimal.ZERO;
                User.addPast(0);
                tests[1] = User.capybaras.equals(bef);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testAddCPS() throws Exception {
        done = false;
        tests = new boolean[5];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                BigDecimal cpcBefore = User.cps = BigDecimal.ONE;
                String[] bd = {".01", "1", "934609237421392437273294", "0", "555533.1100002992e12"};
                for (int i = 0; i < 5; i++) {
                    BigDecimal add = new BigDecimal(bd[i]);
                    User.addCPS(add);
                    cpcBefore = cpcBefore.add(add);
                    tests[i] = User.cps.equals(cpcBefore);
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testAddCapybara() throws Exception {
        done = false;
        tests = new boolean[5];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                BigDecimal cpcBefore = User.capybaras = BigDecimal.ZERO;
                String[] bd = {"111e30", ".0001", "934609237421392437273294", "0", "1.01010101011"};
                for (int i = 0; i < 5; i++) {
                    BigDecimal add = new BigDecimal(bd[i]);
                    User.addCapybara(add);
                    cpcBefore = cpcBefore.add(add);
                    tests[i] = User.capybaras.equals(cpcBefore);
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testRemoveCapybara() throws Exception {
        done = false;
        tests = new boolean[5];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                BigDecimal cpcBefore = User.capybaras = new BigDecimal("1e50");
                String[] bd = {"111", ".0001", "934609237421392437273294", "0", "1.01010101010101"};
                for (int i = 0; i < 5; i++) {
                    BigDecimal add = new BigDecimal(bd[i]);
                    User.removeCapybara(add);
                    cpcBefore = cpcBefore.subtract(add);
                    tests[i] = User.capybaras.equals(cpcBefore);
                }
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void toBlaNumberOfDigits() throws Exception {
        done = false;
        tests = new boolean[10];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                String[] bd = {"123", "30001", "932209237421392437273294", "0", "8787878787878787",
                        "999", "1001", "999999999", "1000000007", "10101010101010"};
                for (int i = 0; i < 10; i++)
                    tests[i] = User.toBla(new BigInteger(bd[i])).equals(User.toBla(bd[i].length()));
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void toBlaCorrect() throws Exception {
        done = false;
        tests = new boolean[10];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                String[] bd = {"123", "30001", "932209237421392437273294", "0", "8787878787878787",
                        "999", "1001", "999999999", "1000000007", "1e100"};
                String[] ans = {"", "thousand", "sextillion", "", "quadrillion", "", "thousand",
                        "million", "billion", "duotrigintillion"};
                for (int i = 0; i < 10; i++)
                    tests[i] = User.toBla(new BigDecimal(bd[i]).toBigIntegerExact()).equals(ans[i]);
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }

    @Test
    public void testToSmallString() throws Exception {
        done = false;
        tests = new boolean[10];
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                String[] bd = {"311", "40011", "837590274969372892748324", "0", "2433241287878787",
                        "999", "1001", "999999999", "1000000007", "2e100"};
                int[] pre = {1, 2, 5, 3, 4, 1, 3, 2, 6, 7};
                String[] ans = {"311", "40.01", "837.59027", "0", "2.4332", "999", "1.001",
                        "999.99", "1.000000", "20.0000000"};
                for (int i = 0; i < 10; i++)
                    tests[i] = ans[i].equals(User.toSmallString
                            (new BigDecimal(bd[i]).toBigIntegerExact(), pre[i]).toString());
                done = true;
            }
        });
        while (!done) Thread.sleep(10);
        for (boolean b : tests)
            assertTrue(b);
    }
}