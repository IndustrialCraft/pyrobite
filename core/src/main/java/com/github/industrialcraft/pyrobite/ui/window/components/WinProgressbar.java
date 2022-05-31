package com.github.industrialcraft.pyrobite.ui.window.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.AssetLoader;
import com.github.industrialcraft.pyrobite.ui.window.WinUIComponent;

public class WinProgressbar extends WinUIComponent {

    private int percentage = 100;

    private static final AssetLoader.Asset<Texture> start =
            AssetLoader.getInstance().getTexture("window/progress_bar_start.png");
    private static final AssetLoader.Asset<Texture> middle =
            AssetLoader.getInstance().getTexture("window/progress_bar_middle.png");
    private static final AssetLoader.Asset<Texture> end =
            AssetLoader.getInstance().getTexture("window/progress_bar_end.png");

    private static final AssetLoader.Asset<Texture> progress =
            AssetLoader.getInstance().getTexture("window/progress.png");

    public WinProgressbar(float positionX, float positionY, float width) {
        super(positionX, positionY, width, 30);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {

        percentage ++;
        if (percentage > 100) {
            percentage = 0;
        }

        spriteBatch.draw(middle.get(), getWindowUpdatedPosX() + 10, getWindowUpdatedPosY(), getWidth() - 10, getHeight());
        spriteBatch.draw(start.get(), getWindowUpdatedPosX(), getWindowUpdatedPosY(), 10, getHeight());
        spriteBatch.draw(end.get(), getWindowUpdatedPosX() + getWidth(), getWindowUpdatedPosY(), 10, getHeight());

        spriteBatch.draw(progress.get(), getWindowUpdatedPosX() + 10, getWindowUpdatedPosY() + 5, (getWidth()-10)/100*percentage, 20);
    }

    @Override
    public void clickedComponent(int x, int y) {

    }

}
