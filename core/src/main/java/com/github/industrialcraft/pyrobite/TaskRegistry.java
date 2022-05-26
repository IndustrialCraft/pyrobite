package com.github.industrialcraft.pyrobite;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TaskRegistry {
    private static TaskRegistry INSTANCE = new TaskRegistry();
    public static TaskRegistry getInstance() {
        return INSTANCE;
    }

    private BiMap<String,Class> tasks;
    private BiMap<Class,String> tasksReversed;
    private TaskRegistry() {
        this.tasks = HashBiMap.create();
        this.tasksReversed = HashBiMap.create();
    }
    public <T extends TaskData> void register(String name, Class<T> task){
        if(this.tasks.containsKey(name)||this.tasks.containsValue(task))
            throw new IllegalStateException(name + " already registered");
        this.tasks.put(name, task);
        this.tasksReversed.put(task, name);
    }
    public String getName(TaskData task){
        return this.tasksReversed.get(task.getClass());
    }
    public TaskData create(String name){
        Class clazz = tasks.get(name);
        if(clazz==null) return null;
        try {
            return (TaskData) clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
