package com.github.industrialcraft.pyrobite.ui.window;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.ui.UIComponent;

public abstract class WinUIComponent extends UIComponent {

    private float windowUpdatedPosX;
    private float windowUpdatedPosY;

    private float positionX;
    private float positionY;
    private float width;
    private float height;

    public WinUIComponent(float positionX, float positionY, float width, float height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    @Override
    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera);

    public abstract void clickedComponent(int x, int y);

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setWindowUpdatedPosX(float windowUpdatedPosX) {
        this.windowUpdatedPosX = windowUpdatedPosX;
    }

    public void setWindowUpdatedPosY(float windowUpdatedPosY) {
        this.windowUpdatedPosY = windowUpdatedPosY;
    }

    public float getWindowUpdatedPosX() {
        return windowUpdatedPosX;
    }

    public float getWindowUpdatedPosY() {
        return windowUpdatedPosY;
    }
}
