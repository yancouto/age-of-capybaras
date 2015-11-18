package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.aoc.manager.ResourceManager;
import com.mygdx.aoc.screen.CapybaraScreen;

import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Static class used to keep User variables, like the number o capybaras and
 * kapivarium available.
 */
public class User {
    /**
     * Variables for capybaras counting
     */
    static public BigDecimal capybaras, cps, cpc;
    /**
     * Variables for kapivarium counting
     */
    static public BigDecimal kapivarium, kps;
    static public String[] powerName;
    static private float cur20 = 0.f, curCap = 0.f, nextCap = 15.f;

    static {
        capybaras = cps = BigDecimal.ZERO;
        cpc = BigDecimal.TEN;
        kapivarium = kps = BigDecimal.ZERO;
    }

    /**
     * Called each update so capybaras, kapivarium and other User stats can be updated
     *
     * @param dt the delta time since last call
     */
    static public void update(float dt) {
        BigDecimal delta = new BigDecimal(dt);
        addCapybara(cps.multiply(delta));
        kapivarium = kapivarium.add(kps.multiply(delta));
        cur20 += dt;
        if (cur20 >= 20) {
            cur20 -= 20;
            ResourceManager.saveGame();
        }
        curCap += dt;
        if (curCap >= nextCap) {
            curCap -= nextCap;
            CapybaraScreen.instance().addCapybara();
            nextCap = (float) Math.random() * 4.f + 2.f;
        }
    }

    /**
     * Saves user data
     *
     * @see ResourceManager#saveGame()
     */
    static public void saveGame() {
        ResourceManager.prefs.putLong("time", System.currentTimeMillis());
        ResourceManager.prefs.putString("userData", ResourceManager.json.toJson(new Data().reset()));
    }

    /**
     * Loads user data
     *
     * @see ResourceManager#loadGame()
     */
    static public void readPowers() {
        try {
            FileHandle powers = Gdx.files.internal("text/powersOfTen.txt");
            Reader reader = powers.reader();
            Scanner scanner = new Scanner(reader);
            powerName = new String[3332];
            int i = 0;
            while (scanner.hasNext())
                powerName[i++] = scanner.nextLine();
            reader.close();
            scanner.close();
        } catch (Exception e) {
            System.err.println("Cannot read powers of ten.");
            powerName = new String[1];
            powerName[0] = "";
        }

    }

    static public void loadGame() {
        readPowers();
        Preferences prefs = ResourceManager.prefs;
        if (prefs.contains("userData")) {
            Data d = ResourceManager.json.fromJson(Data.class, prefs.getString("userData"));
            capybaras = new BigDecimal(new BigInteger(d.c));
            kapivarium = new BigDecimal(new BigInteger(d.k));
            kps = new BigDecimal(new BigInteger(d.kps));
        }
        addPast(prefs.getLong("time", System.currentTimeMillis()));
    }


    /**
     * Multiplies current CPC by {@code num}
     *
     * @param num amount to multiply CPC
     */
    static public void multiplyCPC(BigDecimal num) {
        cpc = cpc.multiply(num);
    }

    /**
     * Simulates the click o the Matriarch Capybara
     */
    static public void capybaraClick() {
        addCapybara(cpc);
        CapybaraScreen.instance().addCapybara();
        CapybaraScreen.instance().addWin("+" + toSmallString(cpc.toBigInteger(), 1) + " " + toBla(cpc.toBigInteger()));
    }

    /**
     * Adds Capybara that should be added assuming you left the device at time {@code prev}
     * and the game kept running
     * @param prev time the user left the game
     */
    static public void addPast(long prev) {
        double d = (System.currentTimeMillis() - prev) / 1000.;
        BigDecimal dt = new BigDecimal(d);
        addCapybara(cps.multiply(dt));
        kapivarium = kapivarium.add(kps.multiply(dt));
        System.out.println(d + " seconds since last visit");
    }

    /**
     * Adds {@code num} CPS to the current CPS
     * @param num CPS to add
     */
    static public void addCPS(BigDecimal num) {
        cps = cps.add(num);
    }

    /**
     * Adds {@code num} capybaras to the current number
     *
     * @param num Capybaras to be added
     */
    static public void addCapybara(BigDecimal num) {
        capybaras = capybaras.add(num);
    }

    /**
     * Removes {@code num} capybaras from the current number
     * Assumes {@code num} is smaller than the current number of Capybaras
     *
     * @param num Capybaras to be removed
     */
    static public void removeCapybara(BigDecimal num) {
        capybaras = capybaras.subtract(num);
    }
    /**
     * No description
     *
     * @param n number of digits of cps
     * @return the string witch contains the value of cps spelled out
     */
    static public CharSequence toBla(int n) {
        if (n < 4) return "";
        int b = n / 3;
        if (n % 3 == 0) b--;
        return powerName[Math.min(b - 1, powerName.length - 1)];
    }

    static public CharSequence toBla(BigInteger x) {
        return toBla(x.toString().length());
    }

    /**
     * Example: toSmallString(123456789, 2) will return the string "123.45"
     *
     * @param x   cps
     * @param dec number of decimal places you want
     * @return a string witch contains the number x with dec decimal places.
     */
    static public CharSequence toSmallString(BigInteger x, int dec) {
        String s = x.toString();
        int size = s.length();
        if (size < 4) return s;
        int uni = size / 3;
        if (size % 3 == 0) uni--;
        StringBuilder st = new StringBuilder(100);
        st.append(s, 0, size - uni * 3);
        st.append('.');
        st.append(s, size - uni * 3, Math.min(size, size - uni * 3 + dec));
        return st;
    }

    /**
     * @see User#toSmallString(BigInteger, int)
     */
    static public CharSequence toSmallString(BigInteger x) {
        return toSmallString(x, 1);
    }

    /**
     * Helper class to store user data using Android's Preferences
     *
     * @see User#loadGame()
     * @see User#saveGame()
     */
    private static class Data {
        String c;
        String k, kps;

        Data reset() {
            c = User.capybaras.toBigInteger().toString();
            k = User.kapivarium.toBigInteger().toString();
            kps = User.kps.toBigInteger().toString();
            return this;
        }
    }
}
