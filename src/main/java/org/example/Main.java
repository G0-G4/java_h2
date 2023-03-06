package org.example;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:h2:", name, pwd, file;
        if (args.length < 4){
            System.out.println("ожидался полный путь до бд, логин, пароль, имя выходного файла");
            exit(1);
        }
        for(var a: args){
            System.out.println(a);
        }
        jdbcURL += args[0];
        name = args[1];
        pwd = args[2];
        file = args[3];
        System.out.println();
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
                proc.print(file);
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