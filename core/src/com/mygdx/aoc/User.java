package com.mygdx.aoc;

import com.badlogic.gdx.Preferences;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
    static public BigDecimal capybaras, cps, cpc;
    static public BigDecimal kapivarium, kps;
    static public String[] power_name = {"thousand", "million", "billion", "trillion", "quadrillion", "quintillion", "sextillion", "septillion", "octillion", "nonillion", "decillion", "undecillion", "duodecillion", "tredecillion", "quattuordecillion", "quindecillion", "sexdecillion", "septendecillion", "octadecillion", "novemdecillion", "vigintillion", "unvigintillion", "duovigintillion", "trevigintillion", "quattuorvigintillion", "quinvigintillion", "sexvigintillion", "septenvigintillion", "octavigintillion", "novemvigintillion", "trigintillion", "untrigintillion", "duotrigintillion", "tretrigintillion", "quattuortrigintillion", "quintrigintillion", "sextrigintillion", "septentrigintillion", "octatrigintillion", "novemtrigintillion", "quadragintillion", "unquadragintillion", "duoquadragintillion", "trequadragintillion", "quattuorquadragintillion", "quinquadragintillion", "sexquadragintillion", "septenquadragintillion", "octaquadragintillion", "novemquadragintillion", "quinquagintillion", "unquinquagintillion", "duoquinquagintillion", "trequinquagintillion", "quattuorquinquagintillion", "quinquinquagintillion", "sexquinquagintillion", "septenquinquagintillion", "octaquinquagintillion", "novemquinquagintillion", "sexagintillion", "unsexagintillion", "duosexagintillion", "tresexagintillion", "quattuorsexagintillion", "quinsexagintillion", "sexsexagintillion", "septensexagintillion", "octasexagintillion", "novemsexagintillion", "septuagintillion", "unseptuagintillion", "duoseptuagintillion", "treseptuagintillion", "quattuorseptuagintillion", "quinseptuagintillion", "sexseptuagintillion", "septenseptuagintillion", "octaseptuagintillion", "novemseptuagintillion", "octagintillion", "unoctogintillion", "duooctogintillion", "treoctogintillion", "quattuoroctogintillion", "quinoctogintillion", "sexoctogintillion", "septenoctogintillion", "octaoctogintillion", "novemoctogintillion", "nonagintillion", "unnonagintillion", "duononagintillion", "trenonagintillion", "quattuornonagintillion", "quinnonagintillion", "sexnonagintillion", "septennonagintillion", "octanonagintillion", "novemnonagintillion", "centillion", "cenuntillion", "cendotillion", "centretillion", "cenquattuortillion", "cenquintillion", "censextillion", "censeptentillion", "cenoctotillion", "cennovemtillion", "cendecillion", "cenundecillion", "cendodecillion", "centredecillion", "cenquattuordecillion", "cenquindecillion", "censexdecillion", "censeptendecillion", "cenoctodecillion", "cennovemdecillion", "cenvigintillion", "cenunvigintillion", "cendovigintillion", "centrevigintillion", "cenquattuorvigintillion", "cenquinvigintillion", "censexvigintillion", "censeptenvigintillion", "cenoctovigintillion", "cennovemvigintillion", "centrigintillion", "cenuntrigintillion", "cendotrigintillion", "centretrigintillion", "cenquattuortrigintillion", "cenquintrigintillion", "censextrigintillion", "censeptentrigintillion", "cenoctotrigintillion", "cennovemtrigintillion", "cenquadragintillion", "cenunquadragintillion", "cendoquadragintillion", "centrequadragintillion", "cenquattuorquadragintillion", "cenquinquadragintillion", "censexquadragintillion", "censeptenquadragintillion", "cenoctoquadragintillion", "cennovemquadragintillion", "cenquinquagintillion", "cenunquinquagintillion", "cendoquinquagintillion", "centrequinquagintillion", "cenquattuorquinquagintillion", "cenquinquinquagintillion", "censexquinquagintillion", "censeptenquinquagintillion", "cenoctoquinquagintillion", "cennovemquinquagintillion", "censexagintillion", "cenunsexagintillion", "cendosexagintillion", "centresexagintillion", "cenquattuorsexagintillion", "cenquinsexagintillion", "censexsexagintillion", "censeptensexagintillion", "cenoctosexagintillion", "cennovemsexagintillion", "censeptuagintillion", "cenunseptuagintillion", "cendoseptuagintillion", "centreseptuagintillion", "cenquattuorseptuagintillion", "cenquinseptuagintillion", "censexseptuagintillion", "censeptenseptuagintillion", "cenoctoseptuagintillion", "cennovemseptuagintillion", "cenoctogintillion", "cenunoctogintillion", "cendooctogintillion", "centreoctogintillion", "cenquattuoroctogintillion", "cenquinoctogintillion", "censexoctogintillion", "censeptenoctogintillion", "cenoctooctogintillion", "cennovemoctogintillion", "cennonagintillion", "cenunnonagintillion", "cendononagintillion", "centrenonagintillion", "cenquattuornonagintillion", "cenquinnonagintillion", "censexnonagintillion", "censeptennonagintillion", "cenoctononagintillion", "cennovemnonagintillion", "duocentillion", "duocenuntillion", "duocendotillion", "duocentretillion", "duocenquattuortillion", "duocenquintillion", "duocensextillion", "duocenseptentillion", "duocenoctotillion", "duocennovemtillion", "duocendecillion", "duocenundecillion", "duocendodecillion", "duocentredecillion", "duocenquattuordecillion", "duocenquindecillion", "duocensexdecillion", "duocenseptendecillion", "duocenoctodecillion", "duocennovemdecillion", "duocenvigintillion", "duocenunvigintillion", "duocendovigintillion", "duocentrevigintillion", "duocenquattuorvigintillion", "duocenquinvigintillion", "duocensexvigintillion", "duocenseptenvigintillion", "duocenoctovigintillion", "duocennovemvigintillion", "duocentrigintillion", "duocenuntrigintillion", "duocendotrigintillion", "duocentretrigintillion", "duocenquattuortrigintillion", "duocenquintrigintillion", "duocensextrigintillion", "duocenseptentrigintillion", "duocenoctotrigintillion", "duocennovemtrigintillion", "duocenquadragintillion", "duocenunquadragintillion", "duocendoquadragintillion", "duocentrequadragintillion", "duocenquattuorquadragintillion", "duocenquinquadragintillion", "duocensexquadragintillion", "duocenseptenquadragintillion", "duocenoctoquadragintillion", "duocennovemquadragintillion", "duocenquinquagintillion", "duocenunquinquagintillion", "duocendoquinquagintillion", "duocentrequinquagintillion", "duocenquattuorquinquagintillion", "duocenquinquinquagintillion", "duocensexquinquagintillion", "duocenseptenquinquagintillion", "duocenoctoquinquagintillion", "duocennovemquinquagintillion", "duocensexagintillion", "duocenunsexagintillion", "duocendosexagintillion", "duocentresexagintillion", "duocenquattuorsexagintillion", "duocenquinsexagintillion", "duocensexsexagintillion", "duocenseptensexagintillion", "duocenoctosexagintillion", "duocennovemsexagintillion", "duocenseptuagintillion", "duocenunseptuagintillion", "duocendoseptuagintillion", "duocentreseptuagintillion", "duocenquattuorseptuagintillion", "duocenquinseptuagintillion", "duocensexseptuagintillion", "duocenseptenseptuagintillion", "duocenoctoseptuagintillion", "duocennovemseptuagintillion", "duocenoctogintillion", "duocenunoctogintillion", "duocendooctogintillion", "duocentreoctogintillion", "duocenquattuoroctogintillion", "duocenquinoctogintillion", "duocensexoctogintillion", "duocenseptenoctogintillion", "duocenoctooctogintillion", "duocennovemoctogintillion", "duocennonagintillion", "duocenunnonagintillion", "duocendononagintillion", "duocentrenonagintillion", "duocenquattuornonagintillion", "duocenquinnonagintillion", "duocensexnonagintillion", "duocenseptennonagintillion", "duocenoctononagintillion", "duocennovemnonagintillion"};
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
        if (cur >= 1) {
            cur -= 1;
            updateSecond();
            cps = cps.multiply(new BigDecimal(1.4)); // temporary
        }
    }

    static public void saveGame() {
        ResourceManager.prefs.putLong("time", System.currentTimeMillis());
        ResourceManager.prefs.putString("userData", ResourceManager.json.toJson(new Data().reset()));
    }

    static public void loadGame() {
        if (true) return;
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

    /**
     * No description
     *
     * @param n number of digits of cps
     * @return the string witch contains the value of cps spelled out
     */
    static public CharSequence toBla(int n) {
        int b = n / 3;
        if (n % 3 == 0) b--;
        return power_name[b - 1];
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
        st.append(' ');
        st.append(toBla(size));
        return st;
    }

    static public CharSequence toSmallString(BigInteger x) {
        return toSmallString(x, 1);
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


}
