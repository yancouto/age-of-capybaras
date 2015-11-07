package com.mygdx.aoc;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ColorDrawable implements Drawable {
    private final Drawable d;
    private final Color color;

    public ColorDrawable(Drawable d, Color c) {
        this.d = d;
        color = c;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.setColor(color);
        d.draw(batch, x, y, width, height);
    }

    @Override
    public float getLeftWidth() {
        return d.getLeftWidth();
    }

    @Override
    public void setLeftWidth(float leftWidth) {
        d.setLeftWidth(leftWidth);
    }

    @Override
    public float getRightWidth() {
        return d.getRightWidth();
    }

    @Override
    public void setRightWidth(float rightWidth) {
        d.setRightWidth(rightWidth);
    }

    @Override
    public float getTopHeight() {
        return d.getTopHeight();
    }

    @Override
    public void setTopHeight(float topHeight) {
        d.setTopHeight(topHeight);
    }

    @Override
    public float getBottomHeight() {
        return d.getBottomHeight();
    }

    @Override
    public void setBottomHeight(float bottomHeight) {
        d.setTopHeight(bottomHeight);
    }

    @Override
    public float getMinWidth() {
        return d.getMinWidth();
    }

    @Override
    public void setMinWidth(float minWidth) {
        d.setMinWidth(minWidth);
    }

    @Override
    public float getMinHeight() {
        return d.getMinWidth();
    }

    @Override
    public void setMinHeight(float minHeight) {
        d.setMinHeight(minHeight);
    }
}
