package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Processor {
    private final ResultSet table_list, table_cols;
    private Map<String, TableInfo> tables;
    Processor(ResultSet table_list, ResultSet table_cols){
        this.table_list = table_list;
        this.table_cols = table_cols;
    }
    public void process() throws SQLException {
        tables = new HashMap<>();
        while(table_list.next()){
            String table_name;
            table_name = table_list.getString(1);
            String primary_keys = table_list.getString(2);
            tables.put(table_name, new TableInfo(table_name, primary_keys));
        }
        while(table_cols.next()){
            String table_name = table_cols.getString(1);
            String column = table_cols.getString(2);
            String type = table_cols.getString(3);
            if (tables.containsKey(table_name) && tables.get(table_name).hasPK(column)){
                tables.get(table_name).addKeyType(column, type);
            }
        }
    }

    public void print(String name) throws IOException, RuntimeException{
        if(tables == null){
            throw new RuntimeException("process before printing");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            for (var t:tables.values()) {
                writer.write(t.toString()+"\n");
            }
        }
    }
}
