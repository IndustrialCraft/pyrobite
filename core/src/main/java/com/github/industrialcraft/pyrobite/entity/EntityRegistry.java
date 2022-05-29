package com.github.industrialcraft.pyrobite.entity;

import com.github.industrialcraft.pyrobite.test.TestEntity;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class EntityRegistry {
    private static EntityRegistry INSTANCE = new EntityRegistry();
    public static EntityRegistry getInstance() {
        return INSTANCE;
    }

    private BiMap<String,Class> entities;
    private BiMap<Class,String> entitiesReversed;
    private EntityRegistry() {
        this.entities = HashBiMap.create();
        this.entitiesReversed = HashBiMap.create();
    }
    public <T extends Entity> void register(String name, Class<T> entity){
        if(this.entities.containsKey(name)||this.entities.containsValue(entity))
            throw new IllegalStateException(name + " already registered");
        this.entities.put(name, entity);
        this.entitiesReversed.put(entity, name);
    }
    public String getName(Entity entity){
        return this.entitiesReversed.get(entity.getClass());
    }
    public Entity create(String name){
        Class clazz = entities.get(name);
        if(clazz==null) return null;
        try {
            return (Entity) clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
