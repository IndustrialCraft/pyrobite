package com.github.industrialcraft.pyrobite;

import com.google.gson.JsonObject;

public abstract class TaskData {
    public abstract void fromJson(JsonObject jsonObject);
    public abstract JsonObject toJson();
}
