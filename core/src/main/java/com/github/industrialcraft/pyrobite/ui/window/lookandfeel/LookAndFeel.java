package com.github.industrialcraft.pyrobite.ui.window.lookandfeel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class LookAndFeel {

    public Texture BUTTON_BEGIN;
    public Texture BUTTON_CENTER;
    public Texture BUTTON_END;

    public Texture WINDOW_SIDE_LEFT;
    public Texture WINDOW_SIDE_RIGHT;
    public Texture WINDOW_SIDE_TOP;
    public Texture WINDOW_SIDE_BOTTOM;
    public Texture WINDOW_SIDE_CENTER;

    public Texture PROGRESSBAR_BEGIN;
    public Texture PROGRESSBAR_CENTER;
    public Texture PROGRESSBAR_END;
    public Texture PROGRESSBAR_CONTENT;

    public LookAndFeel() {

    }

    public Texture getTexture(String path) {
        Texture texture = new Texture(Gdx.files.internal(path));

        if (Gdx.files.internal(path) == null) {
            throw new IllegalStateException("texture null, " + path);
        }

        return texture;
    }

}
