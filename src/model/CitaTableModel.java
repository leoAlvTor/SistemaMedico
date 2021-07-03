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
        super();
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

        return switch (columnIndex) {
            case 0 -> cita.getNumeroRegistro();
            case 1 -> cita.getNumeroFicha();
            case 2 -> cita.getFecha();
            case 3 -> cita.getAnamnesis();
            case 4 -> cita.getReceta();
            case 5 -> cita.getDiagnostico();
            case 6 -> cita.getExamenes();
            default -> "";
        };
    }

    @Override
    public String getColumnName(int column){
        return columnas[column];
    }

}
