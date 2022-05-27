package com.github.industrialcraft.pyrobite.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sun.security.provider.SHA;

public class ProgressBarComponent extends UIComponent{

    public Color color;
    public float completion;
    public float x;
    public float y;
    public float width;
    public float height;
    public float margin;

    public ProgressBarComponent(Color color, float completion, float x, float y, float width, float height) {
        this.color = color;
        this.completion = completion;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.margin = 2;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x+margin, y+margin, (width-(margin*2))*completion, height-(margin*2));
        shapeRenderer.end();
    }
}
