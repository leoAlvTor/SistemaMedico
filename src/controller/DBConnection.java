package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistemamedico";

    private static final String USER = "usuario_seguro";
    private static final String PASSWORD = "pass2021word";

    public static Connection getConnection(){
        try{

            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);

        }catch (Exception e){
            System.err.println("ERROR: Error while trying to connect.");
            System.err.println(e.getMessage());
            return null;
        }
    }

}
