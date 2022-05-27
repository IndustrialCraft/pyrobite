package com.github.industrialcraft.pyrobite;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

    private static final AssetLoader INSTANCE = new AssetLoader();
    private final AssetManager assetManager;

    private AssetLoader() {
        this.assetManager = new AssetManager();
    }

    public Asset<Texture> getTexture(String filename) {
        this.assetManager.load(filename, Texture.class);
        return new Asset<>(filename);
    }

    public boolean update(){
        return assetManager.update();
    }

    public float progress(){
        return assetManager.getProgress();
    }

    public void finishLoading(){
        assetManager.finishLoading();
    }

    public static AssetLoader getInstance() {
        return INSTANCE;
    }

    public class Asset<T> {
        String filename;
        T asset;

        public Asset(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }

        public T get() {
            if(asset == null)
                asset = assetManager.get(getFilename());

            return asset;
        }
    }
}
