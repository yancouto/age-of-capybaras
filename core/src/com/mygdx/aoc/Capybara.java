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
        System.out.println(acc.name);
        if (acc.type == 0) {
            if (head != null) {
                head.equiped = false;
                head = null;
            }
            if (face != null) {
                face.equiped = false;
                face = null;
            }
            helmet = acc;
        }
        else if (acc.type == 1) {
            if (helmet != null) {
                helmet.equiped = false;
                helmet = null;
            }
            head = acc;
        }
        else if (acc.type == 2) {
            if (helmet != null) {
                helmet.equiped = false;
                helmet = null;
            }
            face = acc;
        }
    }

    static void unequipAccessory(Accessory acc) {
        System.out.println(acc.name);
        if (acc.equals(helmet)) {
            System.out.println(acc.name);
            helmet.equiped = false;
            helmet = null;
        }
        else if (acc.equals(head)) {
            head.equiped = false;
            head = null;
        }
        else if (acc.equals(face)) {
            face.equiped = false;
            face = null;
        }
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
