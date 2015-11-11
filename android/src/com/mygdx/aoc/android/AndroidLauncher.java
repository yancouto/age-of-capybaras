package com.mygdx.aoc.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mygdx.aoc.AgeOfCapybaras;

public class AndroidLauncher extends AndroidApplication {
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onPause() {
//        Request an ad
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
//        Um id gerado para uma conta no AdMob deve ser colado no lugar de
//        AdRequest.DEVICE_ID_EMULATOR entre aspas quando o aplicativo estiver
//        pronto para ir para a Play Store.
        mInterstitialAd.loadAd(adRequest);
        super.onPause();
        AgeOfCapybaras.onPause();
    }

    @Override
    protected void onResume() {
//        Check that an ad is loaded and then display it
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        super.onResume();
        AgeOfCapybaras.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
//        Construct an InterstitialAd object and set its ad unit ID.
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //Outro Id que precisa ser criado pelo AdMob.
        initialize(new AgeOfCapybaras(), config);
    }
}
