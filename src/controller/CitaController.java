package controller;

import model.Cita;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.swing.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for implement CRUD interface to Cita model.
 *
 * @version 1.0
 * @author Leonardo Alvarado Torres
 */
public class CitaController implements CRUD<Cita>{

    // Global variable for database connection.
    private Connection connection;

    /**
     * Default constructor for CitaController.
     * Execute connect method.
     */
    public CitaController(){
        connect();
    }

    // Global variable to determine the number of connection tries.
    private int numberOfTries = 0;

    /**
     * Implements a method to connect to database, if some error occurs the method call itself (by recursion) to
     * try to connect again until the number of tries reach to 5. The method wait 0.5 seconds.
     * @since 1.0
     */
    private void connect() {
        connection = DBConnection.getConnection();
        if (Objects.isNull(connection) && numberOfTries < 5){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            numberOfTries++;
            try {
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("ERROR: Error while tring to sleep. " + e.getMessage());
            }
            connect();
        }else if(numberOfTries == 5){
            JOptionPane.showMessageDialog(null, "Error no se pudo conectar a la base de datos.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Insert a new record into database using DBUtils.
     *
     * @param objects objects to being inserted into table.
     * @return the number of records created if everything went ok else return -1.
     */
    @Override
    public BigInteger createRecord(Object ... objects) {
        String sql = "insert into cita(NUMEROFICHA, FECHA, ANAMNESIS, RECETA, DIAGNOSTICO, EXAMENES) values (?, ?, ?," +
                " ?, ?, ?)";
        try{
            ScalarHandler<BigInteger> scalarHandler = new ScalarHandler<>();
            return new QueryRunner().insert(connection, sql, scalarHandler, objects);
        }catch (Exception e){
            System.out.println("ERROR: Error while creating record in CITA, " + e.getMessage());
            return BigInteger.valueOf(-1);
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Get a Cita record by its ID.
     *
     * @param id the ID of the record.
     * @return a Cita instance if everything went ok else return null.
     */
    @Override
    public Cita getRecordById(Object id) {
        ResultSetHandler<Cita> resultSetHandler = new BeanHandler<>(Cita.class);
        try{
            return new QueryRunner().query(connection, "select * from cita where NUMEROREGISTRO = ?",
                    resultSetHandler, id);
        }catch (Exception e){
            System.out.println("ERROR: Error while getting Cita record with ID: " + id);
            return null;
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Get all records from Cita table.
     *
     * @return a List of Cita class if everything went ok else return an empty array.
     */
    @Override
    public List<Cita> getAll() {
        try{
            BeanListHandler<Cita> beanListHandler = new BeanListHandler<>(Cita.class);
            return new QueryRunner().query(connection, "select * from cita", beanListHandler);
        }catch (Exception e){
            System.out.println("ERROR: Error while getting all records from Cita, " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Defines a custom method for getting all records from Cita by its foreign key.
     *
     * @param numeroFicha the ID the of the foreign key.
     * @return a list of Cita if everything ok else return an empty array.
     */
    public List<Cita> getAllByPaciente(int numeroFicha){
        try{
            BeanListHandler<Cita> beanListHandler = new BeanListHandler<>(Cita.class);
            return new QueryRunner().query(connection, "select * from cita where NUMEROFICHA = ?", beanListHandler,
                    numeroFicha);
        }catch (Exception e){
            System.out.println("ERROR: Error while getting all records by NUMEROFICHA = " + numeroFicha);
            return new ArrayList<>();
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Update an existing record by its id.
     *
     * @param id represents the record id.
     * @param objects an array of objects to update the record.
     * @return the number of records updated else return -1.
     */
    @Override
    public int updateRecord(Object id, Object ... objects) {
        Object[] objects1 = new Object[objects.length+1];
        System.arraycopy(objects, 0, objects1, 0, objects.length);
        objects1[objects.length] = id;
        try{
            String updateSQL = "update cita set NUMEROFICHA = ?, FECHA = ?, ANAMNESIS = ?, RECETA = ?, DIAGNOSTICO = " +
                    "?, EXAMENES = ? where NUMEROREGISTRO = ?";
            return new QueryRunner().update(connection, updateSQL, objects1);
        }catch (Exception e){
            System.out.println("ERROR: Error while updating record with id: " + id);
            return -1;
        }
    }

    /**
     * Implements the override method from CRUD interface.
     * Delete a record from Cita table using its ID.
     *
     * @param id represents the id of object which is going to be deleted.
     * @return the number of records deleted else return -1.
     */
    @Override
    public int deleteRecord(Object id) {
        try{
            var sql = "delete from cita where NUMEROREGISTRO = ?";
            return new QueryRunner().update(connection, sql, id);
        }catch (Exception e){
            System.out.println("ERROR: Error while deleting Cita record with id: " + id);
            return -1;
        }
    }

    /**
     * Defines a custom method to close database connection.
     *
     * @return true if connection was closed else false.
     */
    public boolean closeConnection(){
        try{
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
}
