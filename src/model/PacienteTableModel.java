package model;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PacienteTableModel extends AbstractTableModel {

    private String[] columnas = {"N. FICHA", "CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "CELULAR",
            "ESTADO CIVIL", "PROCEDENCIA", "RECIDENCIA", "FECHA NACIMIENTO", "GENERO", "ANTECEDENTES", "PESO", "TALLA",
            "GRUPO SANGUINEO"};

    public Class[] tiposColumnas = {String.class, String.class, String.class, String.class, String.class, String.class,
            String.class, String.class, String.class, String.class, String.class, String.class, String.class,
            String.class, String.class, String.class};

    private List<Paciente> pacienteList;

    public PacienteTableModel(){
        super();
        pacienteList = new ArrayList<>();
    }

    public PacienteTableModel(List<Paciente> pacientes){
        super();
        this.pacienteList = pacientes;
    }


    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
