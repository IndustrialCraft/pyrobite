package com.github.industrialcraft.pyrobite.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scene {
    private ArrayList<Entity> entities;
    private Scheduler scheduler;
    private Camera camera;
    private SpriteBatch spriteBatch;
    private Entity cameraEntity;
    public Scene() {
        this.entities = new ArrayList<>();
        this.scheduler = new Scheduler();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.spriteBatch = new SpriteBatch();
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

        if(cameraEntity!=null&&cameraEntity.isDead())
            cameraEntity = null;
        if(cameraEntity!=null) {
            this.camera.position.x = cameraEntity.getPosition().getX()/2/* - (camera.viewportWidth/2)*/;
            this.camera.position.y = cameraEntity.getPosition().getY()/2/* - (camera.viewportHeight/2)*/;
        } else {
            this.camera.position.x = 0;
            this.camera.position.y = 0;
        }
        this.camera.update();

        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.spriteBatch.setTransformMatrix(camera.view);

        this.spriteBatch.begin();
        for(Entity entity : this.entities){
            entity.onRender(this.spriteBatch, this.camera);
        }
        this.spriteBatch.end();
    }

    public Entity getCameraEntity() {
        return cameraEntity;
    }
    public void setCameraEntity(Entity cameraEntity) {
        this.cameraEntity = cameraEntity;
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
