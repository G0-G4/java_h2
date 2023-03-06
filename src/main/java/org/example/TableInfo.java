package org.example;

import java.util.HashMap;
import java.util.Map;

public class TableInfo {
    private final String name;
    private Map<String, PrimaryKey> primary_keys = new HashMap<>();
    TableInfo(String name, String pk){
        this.name = name;
        for (String key: pk.split(",")) {
            primary_keys.put(key.strip().toLowerCase(), new PrimaryKey(key, null));
        }
    }
    public boolean hasPK(String key){
        key = key.strip().toLowerCase();
        return primary_keys.containsKey(key);
    }
    public void addKeyType(String key, String type){
        key = key.strip().toLowerCase();
        if (primary_keys.containsKey(key)){
            primary_keys.get(key).setType(type);
        }
        else{
            System.out.println(key + "остутствует");
        }
    }
    @Override
    public String toString() {
        return name;
    }
    public void print(){
        for(var k: primary_keys.values()){
            System.out.println(name + " " + k.getName() + " " + k.getType());
        }
    }

}

