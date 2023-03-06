package org.example;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            Processor proc = new Processor(table_list, table_cols);
            proc.process();
            try{
                proc.print("file.txt");
            }
            catch (IOException | RuntimeException e){
                e.printStackTrace();
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            exit(1);
        }
    }
}