package controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class for database connection.
 *
 * @version 1.0
 * @author Leonardo Alvarado Torres
 */
public class DBConnection {
    // Constructor.
    public DBConnection(){}

    // Class name connection.
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Database URL.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistemamedico";
    // Database user.
    private static final String USER = "usuario_seguro";
    // Database password.
    private static final String PASSWORD = "pass2021word";

    /**
     * Static method for creating a new connection to database.
     *
     * @return a Connection instance.
     * If any error occurs return null instead of Connection.
     */
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
