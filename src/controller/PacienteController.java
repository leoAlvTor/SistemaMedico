package controller;

import model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteController extends CRUD<Paciente> {

    @Override
    public boolean createRecord(Paciente instance) {

        return false;
    }

    @Override
    public Paciente getRecordById(Object id) {
        return null;
    }

    @Override
    public List<Paciente> getAll() {
        return null;
    }

    @Override
    public boolean updateRecord(Paciente instance) {
        return false;
    }

    @Override
    public boolean deleteRecord(Paciente instance) {
        return false;
    }


}
