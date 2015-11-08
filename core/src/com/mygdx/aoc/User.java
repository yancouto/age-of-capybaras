package com.mygdx.aoc;

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
