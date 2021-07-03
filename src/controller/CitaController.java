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

public class CitaController extends CRUD<Cita>{

    private Connection connection;

    public CitaController(){
        connect();
    }

    private int numberOfTries = 0;
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

    @Override
    public BigInteger createRecord(Object ... objects) {
        String sql = "insert into cita(NUMEROFICHA, FECHA, ANAMNESIS, RECETA, DIAGNOSTICO, EXAMENES) values (?, ?, ?," +
                " ?, ?, ?)";
        try{
            ScalarHandler<BigInteger> scalarHandler = new ScalarHandler<>();
            return new QueryRunner().insert(connection, sql, scalarHandler, objects);
        }catch (Exception e){
            System.out.println("ERROR: Error while creating record in CITA");
            return BigInteger.valueOf(-1);
        }
    }

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

    @Override
    public List<Cita> getAll() {
        try{
            BeanListHandler<Cita> beanListHandler = new BeanListHandler<>(Cita.class);
            return new QueryRunner().query(connection, "select * from cita", beanListHandler);
        }catch (Exception e){
            System.out.println("ERROR: Error while getting all records from Cita");
            return new ArrayList<>();
        }
    }

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
