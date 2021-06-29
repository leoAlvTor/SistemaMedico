package controller;

import model.Paciente;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
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
    public BigInteger createRecord(Object ... objects){
        try {
            ScalarHandler<BigInteger> scalarHandler = new ScalarHandler<>();
            var runner = new QueryRunner();
            String insertSQL = "insert into paciente(CEDULA, NOMBRES, APELLIDOS, DIRECCION, TELEFONO, CELULAR," +
                    " ESTADOCIVIL, PROCEDENCIA, RESIDENCIA, FECHANACIMIENTO, GENERO, ANTECEDENTES, PESO, TALLA," +
                    " GRUPOSANGUINEO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            return runner.insert(connection, insertSQL, scalarHandler, objects);
        }catch (Exception e){
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

        Paciente paciente = new Paciente();
        paciente.setCedula("NA");
        paciente.setNombres("Caldo de Patito");
        paciente.setApellidos("Local Bagre");
        paciente.setDireccion("UPS");
        paciente.setTelefono("0998072563");
        paciente.setCelular("09980725664");
        paciente.setEstadoCivil("SALTERO");
        paciente.setProcedencia("CUENCA");
        paciente.setResidencia("CUENCA");
        paciente.setFechaNacimiento("22/04/1999");
        paciente.setGenero("MASCULINO");
        paciente.setAntecedentes("Alergia al polvo.");
        paciente.setPeso(64);
        paciente.setTalla(164);
        paciente.setGrupoSanguineo("O+");

        pacienteController.createRecord(paciente.toList());


    }

}
