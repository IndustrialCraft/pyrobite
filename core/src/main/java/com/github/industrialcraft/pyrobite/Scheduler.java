package com.github.industrialcraft.pyrobite;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Scheduler {
    private ArrayList<SchedulerTask> tasks;
    public Scheduler() {
        this.tasks = new ArrayList<>();
    }
    public void addTask(Entity entity, TaskData data, int time){
        this.tasks.add(new SchedulerTask(time, entity, data));
    }
    public void tick(){
        for(SchedulerTask task : tasks){
            if(task.decreseTime()){
                task.run();
            }
        }
        tasks.removeIf(schedulerTask -> schedulerTask.remainingTicks()<=0||schedulerTask.entity.isDead());
    }
    public List<SchedulerTask> getForEntity(Entity entity){
        return this.tasks.stream().filter(schedulerTask -> schedulerTask.entity==entity).collect(Collectors.toList());
    }
    public void reset(){
        tasks.clear();
    }

    class SchedulerTask{
        private int ticks;
        private Entity entity;
        private TaskData data;
        public SchedulerTask(int ticks, Entity entity, TaskData data) {
            this.ticks = ticks;
            this.entity = entity;
            this.data = data;
        }
        protected boolean decreseTime(){
            this.ticks--;
            return this.ticks<=0;
        }
        public int remainingTicks(){
            return this.ticks;
        }
        public void run(){
            this.entity.onTask(data);
        }
        public TaskData getData() {
            return data;
        }
    }
}
