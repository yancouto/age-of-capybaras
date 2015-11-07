package com.mygdx.aoc;

import java.math.BigDecimal;

public class User {
    static public BigDecimal capybaras, cps;
    static public BigDecimal kapivarium, kps;
    static private float cur = 0.f;

    static {
        capybaras = cps = BigDecimal.ZERO;
        kapivarium = kps = BigDecimal.ZERO;
    }

    static private void updateSecond() {
        capybaras = capybaras.add(cps);
        kapivarium = kapivarium.add(kps);
    }

    static public void update(float dt) {
        cur += dt;
        if(cur >= 1) {
            cur -= 1;
            updateSecond();
        }
    }
}
