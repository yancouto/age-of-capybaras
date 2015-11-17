package com.mygdx.aoc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.aoc.manager.ResourceManager;

public class Capybara extends Actor {
    public final long creation;
    public static Accessory helmet = null, head = null, face = null;
    private Vector2 v;
    private float rot;
    private TextureRegion capybara;

    public Capybara(float x, float y) {
        creation = System.currentTimeMillis();
        setPosition(x, y);
        v = new Vector2(1, 0);
        v.rotateRad((float) (Math.random() * 2 * Math.PI));
        v.scl((float) (Math.random() * 5 + 3) * 30);
        capybara = new TextureRegion(ResourceManager.skin.get("capybara", Texture.class));
        setSize(1080 * .3f, 1920 * .3f);
        setOrigin(getWidth() / 2.f, getHeight() / 2.f);
        setRotation((float) (Math.random() * 360));
        rot = (float) Math.random() * 80 - 40;
    }

    static void equipAccessory (Accessory acc) {
        acc.buttonState = "Unequip";
        acc.equiped = true;
        if (acc.type == 0) {
            unequipAccessory(head);
            unequipAccessory(face);
            unequipAccessory(helmet);
            helmet = acc;
        }
        else if (acc.type == 1) {
            unequipAccessory(helmet);
            unequipAccessory(head);
            head = acc;
        }
        else if (acc.type == 2) {
            unequipAccessory(helmet);
            unequipAccessory(face);
            face = acc;
        }
    }

    static void unequipAccessory(Accessory acc) {
        if (acc == null) return;
        acc.buttonState = "Equip";
        acc.equiped = false;
        if (acc.equals(helmet))
            helmet = null;
        else if (acc.equals(head))
            head = null;
        else if (acc.equals(face))
            face = null;
    }

    static public void loadGame() {
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        moveBy(v.x * delta, v.y * delta);
        rotateBy(rot * delta);
        float d = 200;
        if (getX() + getWidth() >= 1080 + d && v.x > 0) v.x = -v.x;
        if (getX() <= 0 - d && v.x < 0) v.x = -v.x;
        if (getY() + getHeight() >= 1920 + d && v.y > 0) v.y = -v.y;
        if (getY() <= 0 - d && v.y < 0) v.y = -v.y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(capybara, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
