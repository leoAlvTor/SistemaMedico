package controller;

import model.Cita;

import javax.swing.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

public class CitaController extends CRUD<Cita>{

    private Connection connection;
    private Cita cita;

    public CitaController(){
        connection = DBConnection.getConnection();
        cita = new Cita();
        if(Objects.isNull(connection))
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
    }

    @Override
    public BigInteger createRecord(Object ... objects) {
        String sql = "INSERT INTO CITA VALUES(?,?,?,?,?,?,?,?)";


        return BigInteger.ONE;
    }

    @Override
    public Cita getRecordById(Object id) {
        return null;
    }

    @Override
    public List<Cita> getAll() {
        return null;
    }

    @Override
    public int updateRecord(Object id, Object ... objects) {
        return -1;
    }

    @Override
    public int deleteRecord(Object id) {
        return -1;
    }
}
