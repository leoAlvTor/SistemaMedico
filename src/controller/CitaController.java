package controller;

import model.Cita;

import java.util.List;

public class CitaController extends CRUD<Cita>{

    public CitaController(){

    }


    @Override
    public boolean createRecord(Cita instance) {
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
