package com.github.industrialcraft.pyrobite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;

public class AssetManager {

    private static HashMap<String, Texture> textureHashMap = new HashMap<>();

    public static void loadTexture(String path, String id) {
        Texture texture = new Texture(Gdx.files.local("assets/" + path));
        textureHashMap.put(id, texture);
    }

    public static Texture getTexture(String id) {
        return textureHashMap.get(id);
    }

}
