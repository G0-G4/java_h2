package org.example;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:h2:D:/gog/projects/java/database/database";
        String name = "gosha";
        String pwd = "";
        Loader l = new Loader(jdbcURL, name, pwd);
        if (!l.connect()){
            System.out.println("не удалось подкючиться к бд");
            exit(1);
        }
        try(ResultSet table_list = l.readTable("TABLE_LIST");
            ResultSet table_cols = l.readTable("TABLE_COLS")){
            Map<String, TableInfo> tables = new HashMap<>();
            while(table_list.next()){
                String table_name = table_list.getString(1);
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
            for (var t:tables.values()) {
                t.print();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            exit(1);
        }
    }
}