package com.github.industrialcraft.pyrobite.ui.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.AssetLoader;

import com.github.industrialcraft.pyrobite.input.InputManager;
import com.github.industrialcraft.pyrobite.ui.window.components.WinLabel;
import com.github.industrialcraft.pyrobite.ui.window.lookandfeel.LookAndFeelConstants;

import java.util.ArrayList;

public class Window extends WinUIComponent {

    public static boolean RENDER_WINDOW_NAMES = Boolean.FALSE;

    private Runnable updateRunnable;

    private boolean canBeRemoved;

    private final ArrayList<WinUIComponent> components;
    private final WindowInput input;

    private boolean undecorated;
    private boolean isAnimated;
    private boolean isEmbedded;

    private float previousWidth;
    private boolean beingRemoved;
    private int animationIndex;

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
        this.input = new WindowInput(this);

        this.animationIndex = 0;
        this.isAnimated = true;
        this.beingRemoved = false;
        this.canBeRemoved = false;
        this.undecorated = false;

        this.setLookAndFeel(LookAndFeelConstants.WOODEN_LOOK_AND_FEEL);

        InputManager.addInput(this.input);

        if (RENDER_WINDOW_NAMES)
            this.components.add(new WinLabel(name + " {Window}", 0, getHeight() - 8));
    }

    public Window asEmbedded() {
        this.isEmbedded = true;
        return this;
    }

    public Window setUpdater(Runnable runnable) {
        this.updateRunnable = runnable;
        return this;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {

        if (updateRunnable != null)
            updateRunnable.run();

        float x = isEmbedded ? getWindowUpdatedPosX() : getPositionX();
        float y = isEmbedded ? getWindowUpdatedPosY() : getPositionY();

        if (beingRemoved && isAnimated) {
            if (previousWidth - animationIndex <= 2) {
                beingRemoved = false;
                canBeRemoved = true;
                return;
            }

            x = x + animationIndex /2f;
            setWidth(previousWidth - animationIndex);

            animationIndex += getWidth() / 3f;
        }

        if (!undecorated) {
            spriteBatch.draw(getLookAndFeel().WINDOW_SIDE_CENTER, x, y, getWidth(), getHeight());
            spriteBatch.draw(getLookAndFeel().WINDOW_SIDE_LEFT, x - 5, y, 10, getHeight());
            spriteBatch.draw(getLookAndFeel().WINDOW_SIDE_RIGHT, x + getWidth(), y, 10, getHeight());
            spriteBatch.draw(getLookAndFeel().WINDOW_SIDE_BOTTOM, x, y - 5, getWidth() + 5, 10);
            spriteBatch.draw(getLookAndFeel().WINDOW_SIDE_TOP, x, y - 5 + getHeight(), getWidth() + 5, 10);
        }

        if (!beingRemoved) {
            for (WinUIComponent component : components) {
                component.setWindowUpdatedPosX(x + component.getPositionX() + 4);
                component.setWindowUpdatedPosY(y + component.getPositionY() + 4);
                component.render(spriteBatch, shapeRenderer, camera);
            }
        }
    }

    public Window asUndecorated() {
        this.undecorated = true;
        return this;
    }

    @Override
    public void clickedComponent(int x, int y) {
        float my = Gdx.graphics.getHeight() - y;

        for (WinUIComponent component : components) {
            if (isInBoundingBox(
                    component.getWindowUpdatedPosX(),
                    component.getWindowUpdatedPosY(),
                    component.getWidth(),
                    component.getHeight(),
                    x,
                    my)) {

                component.clickedComponent(x, y);
            }
        }
    }

    private boolean isInBoundingBox(float x, float y, float width, float height, float mouseX, float mouseY) {
        return (x < mouseX && mouseX < x + width) && (y < mouseY && mouseY < y + height);
    }

    public Window add(WinUIComponent component) {
        component.setLookAndFeel(getLookAndFeel());
        component.setParent(this);

        components.add(component);
        return this;
    }

    public void remove(WinUIComponent component) {
        components.remove(component);
    }

    public void clear() {
        components.clear();
    }

    public void dispose() {
        if (!isAnimated) {
            this.canBeRemoved = true;
            this.beingRemoved = false;
            prepareToRemove();
            return;
        }

        if (beingRemoved) {
            return;
        }

        this.previousWidth = getWidth();
        this.beingRemoved = true;
        this.animationIndex = 0;

        prepareToRemove();
    }

    public void prepareToRemove() {
        InputManager.removeInput(this.input);
    }

    public boolean canBeRemoved() {
        return canBeRemoved;
    }

    public ArrayList<WinUIComponent> getComponents() {
        return components;
    }

    public void setEmbedded(boolean embedded) {
        isEmbedded = embedded;
    }

    public void setUndecorated(boolean undecorated) {
        this.undecorated = undecorated;
    }

    public void setAnimated(boolean animated) {
        isAnimated = animated;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public boolean isBeingRemoved() {
        return beingRemoved;
    }

    @Override
    public String toString() {
        return "Window{" +
                "canBeRemoved=" + canBeRemoved +
                ", undecorated=" + undecorated +
                ", isEmbedded=" + isEmbedded +
                ", previousWidth=" + previousWidth +
                ", beingRemoved=" + beingRemoved +
                ", animationIndex=" + animationIndex +
                ", isAnimated=" + isAnimated +
                '}';
    }
}
