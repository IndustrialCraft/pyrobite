package com.github.industrialcraft.pyrobite.test;

import com.github.industrialcraft.pyrobite.DamageType;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.entity.EntityRegistry;

public class TestEntity extends Entity {
    static {
        EntityRegistry.getInstance().register("TestEntity", TestEntity.class);
    }
    @Override
    public void onTick() {
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
