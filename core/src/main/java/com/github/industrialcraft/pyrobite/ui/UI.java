package com.github.industrialcraft.pyrobite.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.terminal.TerminalExecutor;
import com.github.industrialcraft.pyrobite.ui.window.Window;

import java.util.ArrayList;

public class UI {

    private final ArrayList<UIComponent> components;
    private final ArrayList<Window> windows;

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    private OrthographicCamera uiCamera;

    public UI() {
        this.components = new ArrayList<>();
        this.windows = new ArrayList<>();
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.uiCamera = new OrthographicCamera(2000, 2000);
    }

    public void addComponent(UIComponent component) {
        component.setUserInterface(this);
        this.components.add(component);
    }

    public void removeComponent(UIComponent component) {
        if (component instanceof Window) {
            Window window = (Window) component;
            window.prepareToRemove();
        }

        this.components.remove(component);
    }

    public void render() {

        windows.clear();

        for (UIComponent component : components)
            if (component instanceof Window)
                if (((Window) component).canBeRemoved())
                    windows.add((Window) component);

        for (Window window : windows) {
            removeComponent(window);
        }

        if (TerminalExecutor.commandCamera != null) {
            this.uiCamera = TerminalExecutor.commandCamera;
            TerminalExecutor.commandCamera = null;
        }

        //spriteBatch.setTransformMatrix(uiCamera.view);
        spriteBatch.setProjectionMatrix(uiCamera.combined);
        //shapeRenderer.setTransformMatrix(uiCamera.view);
        shapeRenderer.setProjectionMatrix(uiCamera.combined);

        try {
            componentRender();
        }
        catch (Exception e) {
            componentRender();
        }
    }

    public void componentRender() {
        for(UIComponent component : this.components){
            spriteBatch.begin();
            component.render(spriteBatch, shapeRenderer, uiCamera);
            spriteBatch.end();
        }
    }

    public Camera getUICamera() {
        return uiCamera;
    }

    public void resize(float w, float h) {
        //uiCamera = new OrthographicCamera(2000, 2000);
        uiCamera.setToOrtho(false, w, h);
        uiCamera.update();
    }
}
