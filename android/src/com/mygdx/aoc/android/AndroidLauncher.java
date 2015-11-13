package com.mygdx.aoc.android;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.InterstitialAd;

import com.mygdx.aoc.AgeOfCapybaras;

public class AndroidLauncher extends AndroidApplication {

    public AdManager adM; // AdManager usage example

    @Override
    protected void onPause() {

        adM.requestAd(); // AdManager usage example

        super.onPause();
        AgeOfCapybaras.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        double time;                            //
        adM.displayAd();                        //
        time = adM.elapsedTime();               // AdManager usage example
        if (time >= 20)                         //
            Log.v("ETime", "Time = " + time);   //

        AgeOfCapybaras.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Construct an InterstitialAd object.
        adM = new AdManager();              // AdManager usage example
        adM.init(new InterstitialAd(this)); // This context is necessary

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
        initialize(new AgeOfCapybaras(), config);
    }
}
