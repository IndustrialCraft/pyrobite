package com.github.industrialcraft.pyrobite.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.terminal.TerminalExecutor;
import com.github.industrialcraft.pyrobite.ui.window.Window;

import java.util.ArrayList;

public class UI {
    private ArrayList<UIComponent> components;

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    private OrthographicCamera uiCamera;

    public UI() {
        this.components = new ArrayList<>();
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.uiCamera = new OrthographicCamera(2000, 2000);
    }

    public void addComponent(UIComponent component){
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

        if (TerminalExecutor.commandCamera != null) {
            this.uiCamera = TerminalExecutor.commandCamera;
            TerminalExecutor.commandCamera = null;
        }

        //spriteBatch.setTransformMatrix(uiCamera.view);
        spriteBatch.setProjectionMatrix(uiCamera.combined);
        //shapeRenderer.setTransformMatrix(uiCamera.view);
        shapeRenderer.setProjectionMatrix(uiCamera.combined);

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
