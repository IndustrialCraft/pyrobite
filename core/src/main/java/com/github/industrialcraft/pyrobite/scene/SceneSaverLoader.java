package com.github.industrialcraft.pyrobite.scene;

import com.github.industrialcraft.pyrobite.*;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.entity.EntityRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SceneSaverLoader {
    public static Scene load(JsonObject json){
        Scene scene = new Scene();
        JsonArray entitiesJson = json.getAsJsonArray("entities");
        for(JsonElement entityJsonElement : entitiesJson){
            JsonObject entityJson = entityJsonElement.getAsJsonObject();
            Entity entity = EntityRegistry.getInstance().create(entityJson.get("type").getAsString());
            if(entity==null){
                System.out.println("error loading scene: entity type " + entityJson.get("type").getAsString() + " not found");
                continue;
            }
            entity.fromJson(entityJson);
            scene.add(entity);
            JsonArray entityTasksJsonElement = entityJson.getAsJsonArray("tasks");
            for(JsonElement element : entityTasksJsonElement){
                JsonObject taskJson = element.getAsJsonObject();
                int ticks = taskJson.get("ticks").getAsInt();
                TaskData taskData = TaskRegistry.getInstance().create(taskJson.get("type").getAsString());
                if(taskData==null){
                    System.out.println("error loading scene: task type " + taskJson.get("type").getAsString() + " not found");
                    continue;
                }
                taskData.fromJson(taskJson);
                scene.getScheduler().addTask(entity, taskData, ticks);
            }
        }
        return scene;
    }
    public static JsonObject save(Scene scene){
        JsonObject sceneJson = new JsonObject();
        JsonArray entities = new JsonArray();
        for(Entity entity : scene.getEntities()){
            JsonObject entityJson = entity.toJson();
            entityJson.addProperty("type", EntityRegistry.getInstance().getName(entity));
            JsonArray tasksJson = new JsonArray();
            for(Scheduler.SchedulerTask task : scene.getScheduler().getForEntity(entity)){
                JsonObject taskJson = task.getData().toJson();
                taskJson.addProperty("type", TaskRegistry.getInstance().getName(task.getData()));
                taskJson.addProperty("ticks", task.remainingTicks());
                tasksJson.add(taskJson);
            }
            entityJson.add("tasks", tasksJson);
            entities.add(entityJson);
        }

        sceneJson.add("entities", entities);
        return sceneJson;
    }
}
