package com.github.industrialcraft.pyrobite.ui.window.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.AssetLoader;
import com.github.industrialcraft.pyrobite.ui.window.WinUIComponent;

public class WinButton extends WinUIComponent {

    private String content;
    private Runnable runnable;
    private BitmapFont font;
    private GlyphLayout layout;

    private static final AssetLoader.Asset<Texture> start =
            AssetLoader.getInstance().getTexture("window/button_begin.png");
    private static final AssetLoader.Asset<Texture> middle =
            AssetLoader.getInstance().getTexture("window/button_center.png");
    private static final AssetLoader.Asset<Texture> end =
            AssetLoader.getInstance().getTexture("window/button_end.png");

    public WinButton(String content, float positionX, float positionY, float width, Runnable click) {
        super(positionX, positionY, width, 32);

        this.content = content;
        this.font = new BitmapFont(Gdx.files.internal("terminal/terminal_font.fnt"));
        this.layout = new GlyphLayout();
        this.runnable = click;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        spriteBatch.draw(middle.get(), getWindowUpdatedPosX() + 8, getWindowUpdatedPosY(), getWidth() - 8, getHeight());
        spriteBatch.draw(start.get(), getWindowUpdatedPosX(), getWindowUpdatedPosY(), 8, getHeight());
        spriteBatch.draw(end.get(), getWindowUpdatedPosX() + getWidth(), getWindowUpdatedPosY(), 8, getHeight());

        font.draw(spriteBatch, content, getWindowUpdatedPosX() + (getWidth()-wForStr())/2, getWindowUpdatedPosY() + getHeight() - 10);
    }

    private float wForStr() {
        this.layout.setText(font, content);
        return this.layout.width;
    }

    @Override
    public void clickedComponent(int x, int y) {
        runnable.run();
    }

    @Override
    public String toString() {
        return "WinButton{" +
                "content='" + content + '\'' +
                '}';
    }
}
