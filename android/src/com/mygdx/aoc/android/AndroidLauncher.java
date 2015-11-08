package com.mygdx.aoc.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.aoc.AgeOfCapybaras;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onPause() {
        super.onPause();
        AgeOfCapybaras.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AgeOfCapybaras.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useAccelerometer = false;
        initialize(new AgeOfCapybaras(), config);
    }
}
