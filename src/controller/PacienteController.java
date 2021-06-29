package controller;

import model.Paciente;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class PacienteController extends CRUD<Paciente> {

    private Connection connection;

    public PacienteController(){
        this.connection = DBConnection.getConnection();
    }

    @Override
    public boolean createRecord(Paciente instance){
        var sql = "insert into paciente(CEDULA, NOMBRES, APELLIDOS, DIRECCION, TELEFONO, CELULAR, ESTADOCIVIL, " +
                "PROCEDENCIA, RESIDENCIA, FECHANACIMIENTO, GENERO, ANTECEDENTES, PESO, TALLA, GRUPOSANGUINEO) values " +
                "(?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            List<Object> objectList = instance.mapToList();
            for (int i = 0; i < objectList.size(); i++) {
                if(!Objects.isNull(objectList.get(i)))
                    preparedStatement.setObject(i + 1, instance.mapToList().get(i));
                else
                    preparedStatement.setObject(i, "NA");
            }
            boolean isCorrect = preparedStatement.execute();
            preparedStatement.close();
            return isCorrect;
        }catch (Exception e){
            System.err.println("ERROR: Error while creating Paciente.");
            return false;
        }
    }

    @Override
    public Paciente getRecordById(Object id) {
        ResultSetHandler<Paciente> resultSetHandler = new BeanHandler<>(Paciente.class);
        var runner = new QueryRunner();
        try {
            return runner.query(connection, "select * from paciente where NUMEROFICHA = ?", resultSetHandler
                    , id);
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
    public boolean updateRecord(Paciente instance) {
        return false;
    }

    @Override
    public boolean deleteRecord(Paciente instance) {
        return false;
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

    public static void main(String[] args) throws Exception{
        PacienteController pacienteController = new PacienteController();

    }

}
