package com.github.industrialcraft.pyrobite.terminal.ui.window;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.AssetLoader;

import com.github.industrialcraft.pyrobite.terminal.ui.window.components.WinLabel;

import java.util.ArrayList;

public class Window extends WinUIComponent {

    public static boolean RENDER_WINDOW_NAMES = Boolean.FALSE;

    private final ArrayList<WinUIComponent> components;
    private boolean isEmbedded;
    private final String name;

    private static final AssetLoader.Asset<Texture> leftSide =
            AssetLoader.getInstance().getTexture("window/window_side_left.png");
    private static final AssetLoader.Asset<Texture> rightSide =
            AssetLoader.getInstance().getTexture("window/window_side_right.png");
    private static final AssetLoader.Asset<Texture> center =
            AssetLoader.getInstance().getTexture("window/window_internal.png");
    private static final AssetLoader.Asset<Texture> top =
            AssetLoader.getInstance().getTexture("window/window_side_top.png");
    private static final AssetLoader.Asset<Texture> bottom =
            AssetLoader.getInstance().getTexture("window/window_side_bottom.png");

    public Window(String name, int x, int y, float width, float height) {
        super(x, y, width, height);

        this.components = new ArrayList<>();
        this.name = name;

        if (RENDER_WINDOW_NAMES)
            this.components.add(new WinLabel(name, 0, getHeight() - 80));
    }

    public Window asEmbedded() {
        this.isEmbedded = true;
        return this;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        float x = isEmbedded ? getWindowUpdatedPosX() : getPositionX();
        float y = isEmbedded ? getWindowUpdatedPosY() : getPositionY();

        spriteBatch.draw(center.get(), x, y, getWidth(), getHeight());
        spriteBatch.draw(leftSide.get(), x, y, 30, getHeight());
        spriteBatch.draw(rightSide.get(), x + getWidth(), y, 30, getHeight());
        spriteBatch.draw(bottom.get(), x + 15, y - 25, getWidth(), 50);
        spriteBatch.draw(top.get(), x + 15, y - 25 + getHeight(), getWidth(), 50);

        for (WinUIComponent component : components) {
            component.setWindowUpdatedPosX(x + component.getPositionX() + 50);
            component.setWindowUpdatedPosY(y + component.getPositionY() + 50);
            component.render(spriteBatch, shapeRenderer, camera);
        }
    }

    @Override
    public void clickedComponent(int y, int x) {

    }

    public Window add(WinUIComponent component) {
        components.add(component);
        return this;
    }

    public void remove(WinUIComponent component) {
        components.remove(component);
    }

    public void clear() {
        components.clear();
    }
}
