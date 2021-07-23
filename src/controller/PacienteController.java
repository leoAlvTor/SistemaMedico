package controller;

import model.Paciente;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.swing.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.*;

/**
 * Class for implement CRUD interface to Paciente model.
 *
 * @version 1.0
 * @author Leonardo Alvarado Torres
 */
public class PacienteController implements CRUD<Paciente> {

    // Global variable for database connection.
    private Connection connection;

    /**
     * Default constructor for PacienteController.
     * Execute coonect method.
     */
    public PacienteController(){
        connect();
    }

    // Global variable to determine the number of connection tries.
    private int numberOfTries = 1;

    /**
     * Implements a method to connect to database, if some error occurs the method call itself (by recursion) to try
     * to connect again until the number of tries reach to 5. The method wait 0.5 seconds.
     * @since 1.0
     */
    private void connect() {
        connection = DBConnection.getConnection();
        if (Objects.isNull(connection) && numberOfTries <= 5){
            JOptionPane.showMessageDialog(null,
                    "Error al conectar con la base de datos, INTENTO NUMERO: " + numberOfTries,
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            numberOfTries++;
            try {
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("ERROR: Error while tring to sleep. " + e.getMessage());
            }
            connect();
        }else if(numberOfTries > 5){
            JOptionPane.showMessageDialog(null, "Error no se pudo conectar a la base de datos.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    /**
     * Implements the override method form CRUD interface.
     * Insert a new record into database using DBUtils.
     *
     * @param objects objects to being inserted into table.
     * @return the number of records created else -1.
     */
    @Override
    public BigInteger createRecord(Object ... objects){
        try {
            ScalarHandler<BigInteger> scalarHandler = new ScalarHandler<>();
            String insertSQL = "insert into paciente(CEDULA, NOMBRES, APELLIDOS, DIRECCION, TELEFONO, CELULAR," +
                    " ESTADOCIVIL, PROCEDENCIA, RESIDENCIA, FECHANACIMIENTO, GENERO, ANTECEDENTES, PESO, TALLA," +
                    " GRUPOSANGUINEO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return new QueryRunner().insert(connection, insertSQL, scalarHandler, objects);
        }catch (Exception e){
            System.out.println("ERROR: Error while creating record in PACIENTE.");
            return BigInteger.valueOf(-1);
        }
    }

    /**
     * Get Paciente by its ID.
     *
     * @param id the ID of the record.
     * @return a Paciente instance else return null.
     */
    @Override
    public Paciente getRecordById(Object id) {
        ResultSetHandler<Paciente> resultSetHandler = new BeanHandler<>(Paciente.class);
        var runner = new QueryRunner();
        try {
            Paciente p = runner.query(connection, "select * from paciente where NUMEROFICHA = ?", resultSetHandler, id);
            return p;
        }catch (Exception e){
            System.out.println("ERROR: Error while getting Paciente record with ID: " + id);
            return null;
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Get all records from Paciente table.
     *
     * @return a List of Paciente class else return an empty array.
     */
    @Override
    public List<Paciente> getAll(){
        try {
            BeanListHandler<Paciente> beanListHandler = new BeanListHandler<>(Paciente.class);
            var runner = new QueryRunner();
            return runner.query(connection, "select * from paciente", beanListHandler);
        }catch (Exception e){
            System.out.println("ERROR: Error while getting all records from Paciente");
            return new ArrayList<>();
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Update an existing record by its id.
     *
     * @param id represents the records id.
     * @param objects an array of objects to update the record.
     * @return the number of records updated else return -1.
     */
    @Override
    public int updateRecord(Object id, Object ... objects) {
        Object[] objects1 = new Object[objects.length+1];
        System.arraycopy(objects, 0, objects1, 0, objects.length);
        objects1[objects.length] = id;
        try {
            QueryRunner runner = new QueryRunner();
            String updateSQL = "update paciente set CEDULA = ?, NOMBRES = ?, APELLIDOS = ?, DIRECCION = ?, TELEFONO = ?, " +
                    "CELULAR = ?, ESTADOCIVIL = ?, PROCEDENCIA = ?, RESIDENCIA = ?, FECHANACIMIENTO = ?, GENERO = ?, " +
                    "ANTECEDENTES = ?, PESO = ?, TALLA = ?, GRUPOSANGUINEO = ? where NUMEROFICHA = ?";
            return runner.update(connection, updateSQL, objects1);
        }catch (Exception e){
            System.out.println("ERROR: Error while updating record with id: " + id + ", " + e.getMessage());
            return -1;
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Delete a record from Paciente table using its ID.
     *
     * @param id represents the id of object which is going to be deleted.
     * @return the number of records deleted else return -1.
     */
    @Override
    public int deleteRecord(Object id) {
        try {
            var sql = "delete from paciente where NUMEROFICHA = ?";
            return new QueryRunner().update(connection, sql, id);
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * Defines a custom method to close database connection.
     *
     * @return true if connection was closed else false.
     */
    public boolean closeConnection(){
        try {
            if (!Objects.isNull(this.connection) && !this.connection.isClosed()) {
                this.connection.close();
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println("ERROR: Error while closing the connection.");
            return false;
        }
    }

    public boolean verifyIfIDExists(String id){
        try{
            ResultSetHandler<Paciente> resultSetHandler = new BeanHandler<>(Paciente.class);
            Paciente paciente = new QueryRunner().query(connection, "select * from paciente where CEDULA = ?",
                    resultSetHandler, id);
            return paciente == null;
        }catch (Exception e){
            System.out.println("ERROR: Error while getting record by its second id: " + id + ", " + e.getMessage());
            return false;
        }
    }

    public boolean verifyIfNameAndLastNameExists(String name, String lastName){
        try{
            ResultSetHandler<Paciente> resultSetHandler = new BeanHandler<>(Paciente.class);
            return new QueryRunner().query(connection, "select * from paciente where NOMBRES = ? and APELLIDOS = ?",
                    resultSetHandler, name, lastName) == null;
        }catch (Exception e){
            System.out.println("ERROR: Error while getting records by its name: " + name + ", lastname: " + lastName + ", " + e.getMessage());
            return false;
        }
    }
}
