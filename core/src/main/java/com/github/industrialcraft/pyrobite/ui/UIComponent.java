package com.github.industrialcraft.pyrobite.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class UIComponent {
    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera);
    public void onKeyTyped(char key){}
}
