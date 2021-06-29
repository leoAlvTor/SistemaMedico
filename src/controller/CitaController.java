package controller;

import model.Cita;

import javax.swing.*;
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
    public boolean createRecord(Cita instance) {
        String sql = "INSERT INTO CITA VALUES(?,?,?,?,?,?,?,?)";


        return false;
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
    public boolean updateRecord(Cita instance) {
        return false;
    }

    @Override
    public boolean deleteRecord(Cita instance) {
        return false;
    }
}
