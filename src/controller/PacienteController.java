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

public class PacienteController implements CRUD<Paciente> {

    private Connection connection;

    public PacienteController(){
        connect();
    }

    private int numberOfTries = 1;
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

    @Override
    public Paciente getRecordById(Object id) {
        ResultSetHandler<Paciente> resultSetHandler = new BeanHandler<>(Paciente.class);
        var runner = new QueryRunner();
        try {
            return runner.query(connection, "select * from paciente where NUMEROFICHA = ?", resultSetHandler, id);
        }catch (Exception e){
            System.out.println("ERROR: Error while getting Paciente record with ID: " + id);
            return null;
        }
    }

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

    @Override
    public int deleteRecord(Object id) {
        try {
            var sql = "delete from paciente where NUMEROFICHA = ?";
            return new QueryRunner().update(connection, sql, id);
        }catch (Exception e){
            return -1;
        }
    }

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
}
