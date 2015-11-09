package com.mygdx.aoc;

import com.badlogic.gdx.Preferences;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
    static public BigDecimal capybaras, cps, cpc;
    static public BigDecimal kapivarium, kps;
    static private float cur = 0.f;

    static {
        capybaras = cps = cpc = BigDecimal.TEN;
        kapivarium = kps = BigDecimal.ZERO;
    }

    static private void updateSecond() {
    }

    static public void update(float dt) {
        BigDecimal delta = new BigDecimal(dt);
        capybaras = capybaras.add(cps.multiply(delta));
        kapivarium = kapivarium.add(kps.multiply(delta));
        cur += dt;
        if(cur >= 1) {
            cur -= 1;
            updateSecond();
            cps = cps.multiply(new BigDecimal(1.4)); // temporary
        }
    }

    private static class Data {
        byte[] c, cps, cpc;
        byte[] k, kps;

        Data reset() {
            c = User.capybaras.toBigInteger().toByteArray();
            cps = User.cps.toBigInteger().toByteArray();
            cpc = User.cpc.toBigInteger().toByteArray();
            k = User.kapivarium.toBigInteger().toByteArray();
            kps = User.kps.toBigInteger().toByteArray();
            return this;
        }
    }

    static public void saveGame() {
        ResourceManager.prefs.putLong("time", System.currentTimeMillis());
        ResourceManager.prefs.putString("userData", ResourceManager.json.toJson(new Data().reset()));
    }

    static public void loadGame() {
        Preferences prefs = ResourceManager.prefs;
        if (prefs.contains("userData")) {
            Data d = ResourceManager.json.fromJson(Data.class, prefs.getString("data"));
            capybaras = new BigDecimal(new BigInteger(d.c));
            cps = new BigDecimal(new BigInteger(d.cps));
            cpc = new BigDecimal(new BigInteger(d.cpc));
            kapivarium = new BigDecimal(new BigInteger(d.k));
            kps = new BigDecimal(new BigInteger(d.kps));
        }
        addPast(prefs.getLong("time", System.currentTimeMillis()));
    }

    static public void capybaraClick() {
        capybaras = capybaras.add(cpc);
        cpc = cpc.add(cpc.multiply(BigDecimal.TEN).add(BigDecimal.ONE)); // temporary
    }

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
