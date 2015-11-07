package com.mygdx.aoc;

import com.badlogic.gdx.Game;

public class AgeOfCapybaras extends Game {


    @Override
    public void create() {
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        // important
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.dispose();
    }
}
