package com.github.industrialcraft.pyrobite.ui.window.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.AssetLoader;
import com.github.industrialcraft.pyrobite.ui.window.WinUIComponent;

public class WinProgressbar extends WinUIComponent {

    private int percentage = 100;

    public WinProgressbar(float positionX, float positionY, float width) {
        super(positionX, positionY, width, 30);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {

        percentage ++;
        if (percentage > 100) {
            percentage = 0;
        }

        spriteBatch.draw(getLookAndFeel().PROGRESSBAR_CENTER, getWindowUpdatedPosX() + 10, getWindowUpdatedPosY(), getWidth() - 10, getHeight());
        spriteBatch.draw(getLookAndFeel().PROGRESSBAR_BEGIN, getWindowUpdatedPosX(), getWindowUpdatedPosY(), 10, getHeight());
        spriteBatch.draw(getLookAndFeel().PROGRESSBAR_END, getWindowUpdatedPosX() + getWidth(), getWindowUpdatedPosY(), 10, getHeight());
        spriteBatch.draw(getLookAndFeel().PROGRESSBAR_CONTENT, getWindowUpdatedPosX() + 10, getWindowUpdatedPosY() + 5, (getWidth()-10)/100*percentage, 20);
    }

    @Override
    public void clickedComponent(int x, int y) {

    }

    @Override
    public String toString() {
        return "WinProgressbar{" +
                "percentage=" + percentage +
                '}';
    }
}
