package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to define table model for Java Swing.
 *
 * @author Leonardo Alvarado
 */
public class CitaTableModel extends AbstractTableModel {

    private final String[] columnas = {"N. REGISTRO", "N. FICHA", "FECHA", "ANAMNESIS", "RECETA"};

    private final Class<?>[] tiposColumnas = {String.class, String.class, String.class, String.class, String.class};

    private List<Cita> citaList;

    public CitaTableModel(){
        super();
        citaList = new ArrayList<>();
    }

    public CitaTableModel(List<Cita> citas){
        super();
        this.citaList = citas;

        Collections.sort(citaList, Comparator.comparing(Cita::getNumeroRegistro).reversed());

        if(citas != null)
            this.citaList.stream()
                    .sorted(Comparator.comparing(Cita::getFecha)).collect(Collectors.toList());
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
            default -> "";
        };
    }

    @Override
    public String getColumnName(int column){
        return columnas[column];
    }

}
