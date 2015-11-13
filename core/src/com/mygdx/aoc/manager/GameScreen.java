package com.mygdx.aoc.manager;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * Interface used by ScreenManager. Like a Gdx Screen but with two additional methods.
 *
 * @see ScreenManager
 * @see Screen
 */
public interface GameScreen extends Screen {
    InputProcessor processor();

    boolean blocksInput();
}

