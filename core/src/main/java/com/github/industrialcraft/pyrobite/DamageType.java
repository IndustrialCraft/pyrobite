package com.github.industrialcraft.pyrobite;

public class DamageType {
    String identifier;
    public DamageType(String identifier) {
        this.identifier = identifier;
    }
    public String getIdentifier() {
        return identifier;
    }

    public static final DamageType DELETE = new DamageType("delete");
}
