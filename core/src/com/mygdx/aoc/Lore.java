package com.mygdx.aoc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.aoc.manager.ResourceManager;

public class Lore {
    private String loreName;
    private String loreDescription;
    private int loreAge;

    public static Lore[] lores;

    public Lore (String name) {
        ResourceManager.json.fromJson(LoreData.class, Gdx.files.internal("lores/" + name + ".json")).copyTo(this);
    }

    public String getLoreName() {
        return loreName;
    }

    public String getLoreDescription() {
        return loreDescription;
    }

    public int getLoreAge () {
        return loreAge;
    }

    public static void loadGame() {
        FileHandle[] dir = Gdx.files.internal("lores/").list();
        lores = new Lore[dir.length];
        for (int i = 0; i < dir.length; i++)
            lores[i] = new Lore(dir[i].nameWithoutExtension());
    }


    private static class LoreData {
        String name, description;
        int age;

        public LoreData() {
        }

        public void copyTo(Lore l) {
            l.loreName = name;
            l.loreDescription = description;
            l.loreAge = age;
        }
    }
}
