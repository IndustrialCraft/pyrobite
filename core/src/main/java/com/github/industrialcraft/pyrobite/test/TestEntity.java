package com.github.industrialcraft.pyrobite.test;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.pyrobite.AssetLoader;
import com.github.industrialcraft.pyrobite.DamageType;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.entity.EntityRegistry;
import com.github.industrialcraft.pyrobite.location.Position;

public class TestEntity extends Entity {
    private static final AssetLoader.Asset<Texture> teleporterTexture =
            AssetLoader.getInstance().getTexture("teleporter.jpeg");

    static {
        EntityRegistry.getInstance().register("TestEntity", TestEntity.class);
    }

    public TestEntity() {
    }
    public TestEntity(Position position) {
        super(position);
    }

    @Override
    public void onRender(SpriteBatch spriteBatch, Camera camera) {
        spriteBatch.draw(teleporterTexture.get(), getPosition().getX(), getPosition().getY(), 50, 50);
    }

    @Override
    public float getMaxHealth() {
        return 100;
    }

    @Override
    public float getDamageTypeModifier(DamageType type) {
        return 1;
    }
}
