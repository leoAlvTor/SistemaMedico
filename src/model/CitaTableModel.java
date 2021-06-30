package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CitaTableModel extends AbstractTableModel {

    private final String[] columnas = {"N. REGISTRO", "N. FICHA", "FECHA", "ANAMNESIS", "RECETA", "DIAGNOSITCO",
            "EXAMENES"};

    private final Class<?>[] tiposColumnas = {String.class, String.class, String.class, String.class, String.class,
            String.class, String.class};

    private List<Cita> citaList;

    public CitaTableModel(){
        super();
        citaList = new ArrayList<>();
    }

    public CitaTableModel(List<Cita> citas){
        this.citaList = citas;
    }

    @Override
    public int getRowCount() {
        return citaList.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return tiposColumnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cita cita = citaList.get(rowIndex);

        switch (columnIndex){
            case 0:
                return cita.getNumeroRegistro();
            case 1:
                return cita.getNumeroFicha();
            case 2:
                return cita.getFechaAsString();
            case 3:
                return cita.getAnamnesis();
            case 4:
                return cita.getReceta();
            case 5:
                return cita.getDiagnostico();
            case 6:
                return cita.getExamenes();
            default:
                return "";
        }
    }
}
