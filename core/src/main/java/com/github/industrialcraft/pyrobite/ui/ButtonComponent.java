package com.github.industrialcraft.pyrobite.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.github.industrialcraft.pyrobite.AssetLoader;

public class ButtonComponent extends UIComponent {

    private static final AssetLoader.Asset<Texture> startingTexture =
            AssetLoader.getInstance().getTexture("button_start.png");
    private static final AssetLoader.Asset<Texture> centerTexture =
            AssetLoader.getInstance().getTexture("button_middle.png");
    private static final AssetLoader.Asset<Texture> endingTexture =
            AssetLoader.getInstance().getTexture("button_end.png");

    private static final BitmapFont font = new BitmapFont();
    private static final GlyphLayout layout = new GlyphLayout(font, "");

    private final Runnable onClick;
    private String text;

    private final float margin;
    private final float x;
    private final float y;

    public ButtonComponent(float x, float y, float margin, String text, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.onClick = onClick;
        this.margin = margin;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private float forText(String t) {
        layout.setText(font, t);
        return layout.width;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        float width = forText(text)+margin;
        float height = layout.height+margin;
        Vector3 mouse = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if(x < mouse.x && x+width>mouse.x && y < mouse.y+height && y+height>mouse.y+height) {
            spriteBatch.setColor(Color.ORANGE);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
                onClick.run();
        }
        else
            spriteBatch.setColor(Color.WHITE);

        spriteBatch.draw(centerTexture.get(), x, y-height, width, height);
        font.draw(spriteBatch, text, x+(margin/2), y-(margin/2));
        spriteBatch.setColor(Color.WHITE);
    }
}
