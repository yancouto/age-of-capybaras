package com.mygdx.aoc.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import java.util.Stack;

/**
 * ScreenManager maintains a stack of GameScreens.
 * GameScreens are normal gdx Screens with 2 additional methods:
 * {@code processor()} and {@code blocksInput()}
 * <p/>
 * GameScreens are drawn in the order they were added, but the input is handled the other way.
 * (it makes sense because the topmost GameScreen should receive the input first)
 * <p/>
 * {@code processor()} should return the InputProcessor that Screen uses to handle input. (or null
 * if it does not handle input)
 * <p/>
 * {@code blocksInput()} should return whether this GameScreen blocks the input of GameScreens
 * beneath it on the stack. (useful for warnings)
 *
 * @see GameScreen
 */
public class ScreenManager {
    static private Stack<GameScreen> screenStack = new Stack<GameScreen>();
    private static Color backColor = Color.WHITE;

    /**
     * Removes and returns the topmost screen on the stack, its {@code hide()} method is called.
     *
     * @return the removed screen
     */
    static public GameScreen popScreen() {
        GameScreen gs = screenStack.pop();
        gs.hide();
        return gs;
    }

    /**
     * Returns the topmost screen on the stack.
     *
     * @return the topmost screen on the stack
     */
    static public GameScreen topScreen() {
        return screenStack.peek();
    }

    /**
     * Pushes screen on the stack, its {@code show()} method is called.
     *
     * @param screen screen to be added
     */
    static public void pushScreen(GameScreen screen) {
        screenStack.push(screen);
        screen.show();
    }

    /**
     * Changes the game's background.
     *
     * @param color color the background will be changed to. Its alpha is ignored
     */
    static public void setBackground(Color color) {
        backColor = color;
    }

    /**
     * Calls render on all the GameScreens.
     *
     * @param delta time since last render
     */
    static public void render(float delta) {
        Gdx.gl.glClearColor(backColor.r, backColor.g, backColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < screenStack.size(); i++)
            screenStack.get(i).render(delta);
    }

    /**
     * Initializes ScreenManager data.
     */
    static public void init() {
        Gdx.input.setInputProcessor(new InputHandler());
    }

    /**
     * Helper class, get input from Gdx and passes it along to the GameScreens.
     */
    private static class InputHandler implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().keyDown(keycode) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().keyUp(keycode) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().keyTyped(character) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().touchDown(screenX, screenY, pointer, button) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().touchUp(screenX, screenY, pointer, button) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().touchDragged(screenX, screenY, pointer) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().mouseMoved(screenX, screenY) || gs.blocksInput())
                    return true;
            }
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            for (int i = screenStack.size() - 1; i >= 0; i--) {
                GameScreen gs = screenStack.get(i);
                if (gs.processor() == null) continue;
                if (gs.processor().scrolled(amount) || gs.blocksInput())
                    return true;
            }
            return false;
        }
    }
}
