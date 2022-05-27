package com.github.industrialcraft.pyrobite.entity;

import com.github.industrialcraft.pyrobite.DamageType;
import com.github.industrialcraft.pyrobite.TaskData;
import com.google.gson.JsonObject;

public abstract class Entity {

    private float health;

    public Entity() {
        this.health = getMaxHealth();
    }

    public void fromJson(JsonObject json){
        this.health = json.get("health").getAsFloat();
    }

    public JsonObject toJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("health", this.health);
        return jsonObject;
    }

    public float getHealth() {
        return health;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public void onAdd() {}
    public void onDeath() {}
    public void onTask(TaskData task) {}

    public abstract void onTick();
    public abstract float getMaxHealth();
    public abstract float getDamageTypeModifier(DamageType type);

    public void takeDamage(float amount, DamageType type){
        if(amount < 0)  return;
        this.health -= getDamageTypeModifier(type)*amount;
    }

    public void healDamage(float amount, DamageType type){
        if(amount < 0)  return;
        if(this.health<=0)  return;
        this.health += getDamageTypeModifier(type)*amount;
        if(this.health > getMaxHealth())
            this.health = getMaxHealth();
    }
}
