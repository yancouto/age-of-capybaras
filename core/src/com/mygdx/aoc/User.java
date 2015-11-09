package com.mygdx.aoc;

import com.badlogic.gdx.Preferences;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
    /**
     * Variables for capybaras counting
     */
    static public BigDecimal capybaras, cps, cpc;
    /**
     * Variables for kapivarium counting
     */
    static public BigDecimal kapivarium, kps;
    static {
        capybaras = cps = cpc = BigDecimal.TEN;
        kapivarium = kps = BigDecimal.ZERO;
    }

    static private float cur1 = 0.f, cur20 = 0.f;

    /**
     * Called each update so capybaras, kapivarium and other User stats can be updated
     *
     * @param dt the delta time since last call
     */
    static public void update(float dt) {
        BigDecimal delta = new BigDecimal(dt);
        capybaras = capybaras.add(cps.multiply(delta));
        kapivarium = kapivarium.add(kps.multiply(delta));
        cur1 += dt;
        if (cur1 >= 1) {
            cur1 -= 1;
            cps = cps.multiply(new BigDecimal(1.4)); // temporary
        }
        cur20 += dt;
        if (cur20 >= 20) {
            cur20 -= 20;
            ResourceManager.saveGame();
        }
    }

    /**
     * Helper class to store user data using Android's Preferences
     * @see User#loadGame()
     * @see User#saveGame()
     */
    private static class Data {
        String c, cps, cpc;
        String k, kps;

        Data reset() {
            c = User.capybaras.toBigInteger().toString();
            cps = User.cps.toBigInteger().toString();
            cpc = User.cpc.toBigInteger().toString();
            k = User.kapivarium.toBigInteger().toString();
            kps = User.kps.toBigInteger().toString();
            return this;
        }
    }

    /**
     * Saves user data
     * @see ResourceManager#saveGame()
     */
    static public void saveGame() {
        ResourceManager.prefs.putLong("time", System.currentTimeMillis());
        ResourceManager.prefs.putString("userData", ResourceManager.json.toJson(new Data().reset()));
    }

    /**
     * Loads user data
     * @see ResourceManager#loadGame()
     */
    static public void loadGame() {
        Preferences prefs = ResourceManager.prefs;
        if (prefs.contains("userData")) {
            Data d = ResourceManager.json.fromJson(Data.class, prefs.getString("userData"));
            capybaras = new BigDecimal(new BigInteger(d.c));
            cps = new BigDecimal(new BigInteger(d.cps));
            cpc = new BigDecimal(new BigInteger(d.cpc));
            kapivarium = new BigDecimal(new BigInteger(d.k));
            kps = new BigDecimal(new BigInteger(d.kps));
        }
        addPast(prefs.getLong("time", System.currentTimeMillis()));
    }

    /**
     * Simulates the click o the Matriarch Capybara
     */
    static public void capybaraClick() {
        capybaras = capybaras.add(cpc);
        cpc = cpc.add(cpc.multiply(BigDecimal.TEN).add(BigDecimal.ONE)); // temporary
    }

    /**
     * Adds Capybara that should be added assuming you left the device at time {@code prev}
     * and the game kept running
     * @param prev time the user left the game
     */
    static public void addPast(long prev) {
        double d = (System.currentTimeMillis() - prev) / 1000.;
        BigDecimal dt = new BigDecimal(d);
        capybaras = capybaras.add(cps.multiply(dt));
        kapivarium = kapivarium.add(kps.multiply(dt));
        System.out.println(d + " seconds since last visit");
    }

    static private final String unit = " kMGTPEZY";
    static public CharSequence toSmallString(BigInteger x) {
        String s = x.toString();
        int sz = s.length();
        if(sz <= 4) return s;
        int u = (sz / 3);
        if(sz % 3 == 0) u--;
        StringBuilder st = new StringBuilder(8);
        st.append(s, 0, sz - u * 3);
        st.append('.');
        st.append(s, sz - u * 3, sz - u * 3 + 2);
        while(st.length() < 7) st.append(' ');
        if (sz / 3 < unit.length()) st.append(unit.charAt(sz / 3));
        else st.append("muito");
        return st;
    }
}
