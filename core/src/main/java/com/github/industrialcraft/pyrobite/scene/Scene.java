package com.github.industrialcraft.pyrobite.scene;

import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scene {
    private ArrayList<Entity> entities;
    private Scheduler scheduler;
    public Scene() {
        this.entities = new ArrayList<>();
        this.scheduler = new Scheduler();
    }
    public void add(Entity entity){
        this.entities.add(entity);
        entity.onAdd();
    }
    public void onTick(){
        ArrayList<Entity> deleted = new ArrayList<>();
        entities.removeIf(entity -> {
            if(entity.isDead())
                deleted.add(entity);
            return entity.isDead();
        });
        deleted.forEach(entity -> entity.onDeath());

        for(Entity entity : this.entities){
            entity.onTick();
        }
    }
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    public void dispose(){
        for(Entity entity : entities){
            entity.onDeath();
        }
    }
    public Scheduler getScheduler() {
        return scheduler;
    }
}
