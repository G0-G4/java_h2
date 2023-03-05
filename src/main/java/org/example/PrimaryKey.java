package org.example;

public class PrimaryKey {
    private final String name;
    private String type;

    PrimaryKey(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}