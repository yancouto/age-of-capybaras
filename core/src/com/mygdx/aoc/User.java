package com.mygdx.aoc;

import com.badlogic.gdx.Preferences;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Static class used to keep User variables, like the number o capybaras and
 * kapivarium avaiable.
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
    static public String[] power_name = {"thousand", "million", "billion", "trillion", "quadrillion", "quintillion", "sextillion", "septillion", "octillion", "nonillion", "decillion", "undecillion", "duodecillion", "tredecillion", "quattuordecillion", "quindecillion", "sexdecillion", "septendecillion", "octadecillion", "novemdecillion", "vigintillion", "unvigintillion", "duovigintillion", "trevigintillion", "quattuorvigintillion", "quinvigintillion", "sexvigintillion", "septenvigintillion", "octavigintillion", "novemvigintillion", "trigintillion", "untrigintillion", "duotrigintillion", "tretrigintillion", "quattuortrigintillion", "quintrigintillion", "sextrigintillion", "septentrigintillion", "octatrigintillion", "novemtrigintillion", "quadragintillion", "unquadragintillion", "duoquadragintillion", "trequadragintillion", "quattuorquadragintillion", "quinquadragintillion", "sexquadragintillion", "septenquadragintillion", "octaquadragintillion", "novemquadragintillion", "quinquagintillion", "unquinquagintillion", "duoquinquagintillion", "trequinquagintillion", "quattuorquinquagintillion", "quinquinquagintillion", "sexquinquagintillion", "septenquinquagintillion", "octaquinquagintillion", "novemquinquagintillion", "sexagintillion", "unsexagintillion", "duosexagintillion", "tresexagintillion", "quattuorsexagintillion", "quinsexagintillion", "sexsexagintillion", "septensexagintillion", "octasexagintillion", "novemsexagintillion", "septuagintillion", "unseptuagintillion", "duoseptuagintillion", "treseptuagintillion", "quattuorseptuagintillion", "quinseptuagintillion", "sexseptuagintillion", "septenseptuagintillion", "octaseptuagintillion", "novemseptuagintillion", "octagintillion", "unoctogintillion", "duooctogintillion", "treoctogintillion", "quattuoroctogintillion", "quinoctogintillion", "sexoctogintillion", "septenoctogintillion", "octaoctogintillion", "novemoctogintillion", "nonagintillion", "unnonagintillion", "duononagintillion", "trenonagintillion", "quattuornonagintillion", "quinnonagintillion", "sexnonagintillion", "septennonagintillion", "octanonagintillion", "novemnonagintillion", "centillion", "cenuntillion", "cendotillion", "centretillion", "cenquattuortillion", "cenquintillion", "censextillion", "censeptentillion", "cenoctotillion", "cennovemtillion", "cendecillion", "cenundecillion", "cendodecillion", "centredecillion", "cenquattuordecillion", "cenquindecillion", "censexdecillion", "censeptendecillion", "cenoctodecillion", "cennovemdecillion", "cenvigintillion", "cenunvigintillion", "cendovigintillion", "centrevigintillion", "cenquattuorvigintillion", "cenquinvigintillion", "censexvigintillion", "censeptenvigintillion", "cenoctovigintillion", "cennovemvigintillion", "centrigintillion", "cenuntrigintillion", "cendotrigintillion", "centretrigintillion", "cenquattuortrigintillion", "cenquintrigintillion", "censextrigintillion", "censeptentrigintillion", "cenoctotrigintillion", "cennovemtrigintillion", "cenquadragintillion", "cenunquadragintillion", "cendoquadragintillion", "centrequadragintillion", "cenquattuorquadragintillion", "cenquinquadragintillion", "censexquadragintillion", "censeptenquadragintillion", "cenoctoquadragintillion", "cennovemquadragintillion", "cenquinquagintillion", "cenunquinquagintillion", "cendoquinquagintillion", "centrequinquagintillion", "cenquattuorquinquagintillion", "cenquinquinquagintillion", "censexquinquagintillion", "censeptenquinquagintillion", "cenoctoquinquagintillion", "cennovemquinquagintillion", "censexagintillion", "cenunsexagintillion", "cendosexagintillion", "centresexagintillion", "cenquattuorsexagintillion", "cenquinsexagintillion", "censexsexagintillion", "censeptensexagintillion", "cenoctosexagintillion", "cennovemsexagintillion", "censeptuagintillion", "cenunseptuagintillion", "cendoseptuagintillion", "centreseptuagintillion", "cenquattuorseptuagintillion", "cenquinseptuagintillion", "censexseptuagintillion", "censeptenseptuagintillion", "cenoctoseptuagintillion", "cennovemseptuagintillion", "cenoctogintillion", "cenunoctogintillion", "cendooctogintillion", "centreoctogintillion", "cenquattuoroctogintillion", "cenquinoctogintillion", "censexoctogintillion", "censeptenoctogintillion", "cenoctooctogintillion", "cennovemoctogintillion", "cennonagintillion", "cenunnonagintillion", "cendononagintillion", "centrenonagintillion", "cenquattuornonagintillion", "cenquinnonagintillion", "censexnonagintillion", "censeptennonagintillion", "cenoctononagintillion", "cennovemnonagintillion", "duocentillion", "duocenuntillion", "duocendotillion", "duocentretillion", "duocenquattuortillion", "duocenquintillion", "duocensextillion", "duocenseptentillion", "duocenoctotillion", "duocennovemtillion", "duocendecillion", "duocenundecillion", "duocendodecillion", "duocentredecillion", "duocenquattuordecillion", "duocenquindecillion", "duocensexdecillion", "duocenseptendecillion", "duocenoctodecillion", "duocennovemdecillion", "duocenvigintillion", "duocenunvigintillion", "duocendovigintillion", "duocentrevigintillion", "duocenquattuorvigintillion", "duocenquinvigintillion", "duocensexvigintillion", "duocenseptenvigintillion", "duocenoctovigintillion", "duocennovemvigintillion", "duocentrigintillion", "duocenuntrigintillion", "duocendotrigintillion", "duocentretrigintillion", "duocenquattuortrigintillion", "duocenquintrigintillion", "duocensextrigintillion", "duocenseptentrigintillion", "duocenoctotrigintillion", "duocennovemtrigintillion", "duocenquadragintillion", "duocenunquadragintillion", "duocendoquadragintillion", "duocentrequadragintillion", "duocenquattuorquadragintillion", "duocenquinquadragintillion", "duocensexquadragintillion", "duocenseptenquadragintillion", "duocenoctoquadragintillion", "duocennovemquadragintillion", "duocenquinquagintillion", "duocenunquinquagintillion", "duocendoquinquagintillion", "duocentrequinquagintillion", "duocenquattuorquinquagintillion", "duocenquinquinquagintillion", "duocensexquinquagintillion", "duocenseptenquinquagintillion", "duocenoctoquinquagintillion", "duocennovemquinquagintillion", "duocensexagintillion", "duocenunsexagintillion", "duocendosexagintillion", "duocentresexagintillion", "duocenquattuorsexagintillion", "duocenquinsexagintillion", "duocensexsexagintillion", "duocenseptensexagintillion", "duocenoctosexagintillion", "duocennovemsexagintillion", "duocenseptuagintillion", "duocenunseptuagintillion", "duocendoseptuagintillion", "duocentreseptuagintillion", "duocenquattuorseptuagintillion", "duocenquinseptuagintillion", "duocensexseptuagintillion", "duocenseptenseptuagintillion", "duocenoctoseptuagintillion", "duocennovemseptuagintillion", "duocenoctogintillion", "duocenunoctogintillion", "duocendooctogintillion", "duocentreoctogintillion", "duocenquattuoroctogintillion", "duocenquinoctogintillion", "duocensexoctogintillion", "duocenseptenoctogintillion", "duocenoctooctogintillion", "duocennovemoctogintillion", "duocennonagintillion", "duocenunnonagintillion", "duocendononagintillion", "duocentrenonagintillion", "duocenquattuornonagintillion", "duocenquinnonagintillion", "duocensexnonagintillion", "duocenseptennonagintillion", "duocenoctononagintillion", "duocennovemnonagintillion"};

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
     *
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

    /**
     * Adds {@code num} CPS to the current CPS
     * @param num CPS to add
     */
    static public void addCPS(BigDecimal num) {
        cps = cps.add(num);
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

    /**
     * @see User#toSmallString(BigInteger, int)
     */
    static public CharSequence toSmallString(BigInteger x) {
        return toSmallString(x, 1);
    }

}
