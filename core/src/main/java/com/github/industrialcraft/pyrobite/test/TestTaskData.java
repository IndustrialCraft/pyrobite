package com.github.industrialcraft.pyrobite.test;

import com.github.industrialcraft.pyrobite.TaskData;
import com.github.industrialcraft.pyrobite.TaskRegistry;
import com.google.gson.JsonObject;

public class TestTaskData extends TaskData {
    static {
        TaskRegistry.getInstance().register("TestTaskData", TestTaskData.class);
    }
    @Override
    public void fromJson(JsonObject jsonObject) {

    }

    @Override
    public JsonObject toJson() {
        return new JsonObject();
    }
}
