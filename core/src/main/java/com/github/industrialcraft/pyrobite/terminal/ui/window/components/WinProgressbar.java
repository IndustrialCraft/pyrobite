package com.github.industrialcraft.pyrobite.terminal.ui.window.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.AssetLoader;
import com.github.industrialcraft.pyrobite.terminal.ui.window.WinUIComponent;

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
        super(positionX, positionY, width, 100);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {

        percentage ++;
        if (percentage > 100) {
            percentage = 0;
        }

        spriteBatch.draw(middle.get(), getWindowUpdatedPosX() + 30, getWindowUpdatedPosY(), getWidth() - 20, getHeight());
        spriteBatch.draw(start.get(), getWindowUpdatedPosX(), getWindowUpdatedPosY(), 30, getHeight());
        spriteBatch.draw(end.get(), getWindowUpdatedPosX() + getWidth(), getWindowUpdatedPosY(), 30, getHeight());

        if (!(percentage < 5)) {
            spriteBatch.draw(progress.get(), getWindowUpdatedPosX() + 30, getWindowUpdatedPosY() +20, percentage/100f * getWidth() - 35, getHeight() - 40);
        }
    }

    @Override
    public void clickedComponent(int y, int x) {

    }

}
