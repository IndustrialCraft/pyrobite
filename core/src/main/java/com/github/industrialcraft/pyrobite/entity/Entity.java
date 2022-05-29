package com.github.industrialcraft.pyrobite.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.pyrobite.DamageType;
import com.github.industrialcraft.pyrobite.TaskData;
import com.github.industrialcraft.pyrobite.location.Position;
import com.google.gson.JsonObject;

public abstract class Entity {
    private Position position;
    private float health;

    public Entity() {
        this(new Position(0, 0));
    }
    public Entity(Position position) {
        this.health = getMaxHealth();
        this.position = position;
    }

    public void fromJson(JsonObject json){
        this.health = json.get("health").getAsFloat();
        this.position = new Position(json.get("positionX").getAsFloat(), json.get("positionY").getAsFloat());
    }

    public JsonObject toJson(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("health", this.health);
        jsonObject.addProperty("positionX", this.position.getX());
        jsonObject.addProperty("positionY", this.position.getY());
        return jsonObject;
    }

    public float getHealth() {
        return health;
    }

    public void kill(){
        this.health = 0;
    }
    public boolean isDead(){
        return health <= 0;
    }

    public void onAdd() {}
    public void onDeath() {}
    public void onTask(TaskData task) {}

    public void teleport(Position position){
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }

    public abstract void onRender(SpriteBatch spriteBatch, Camera camera);
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
