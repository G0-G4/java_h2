package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Loader {
    private final String url, name, password;
    Connection connection;
    String query = "select * from %s";
    Loader(String url, String name, String password){
        this.url = url;
        this.name = name;
        this.password = password;
    }
    public boolean connect(){
        try {
            connection = DriverManager.getConnection(url, name, password);
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }
    public ResultSet readTable(String table_name) throws SQLException{
        Statement statement = connection.createStatement();
        return statement.executeQuery(String.format(query, table_name));
    }
}
