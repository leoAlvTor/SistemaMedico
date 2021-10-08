package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to define table model for Java Swing.
 * @author Leonardo Alvarado
 */
public class PacienteTableModel extends AbstractTableModel {

    private final String[] columnas = {"N. FICHA", "CEDULA", "APELLIDO", "NOMBRE"};

    public Class<?>[] tiposColumnas = {String.class, String.class, String.class, String.class};

    private List<Paciente> pacienteList;

    public PacienteTableModel(){
        super();
        pacienteList = new ArrayList<>();
    }

    public PacienteTableModel(List<Paciente> pacientes){
        super();
        if(pacienteList != null)
            this.pacienteList.stream()
                .sorted(Comparator.comparing(Paciente::getApellidos)).collect(Collectors.toList());
        this.pacienteList = pacientes;
    }


    @Override
    public int getRowCount() {
        return pacienteList.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return tiposColumnas[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Paciente paciente = pacienteList.get(rowIndex);

        switch (columnIndex){
            case 0:
                paciente.setNumeroFicha((int) aValue);
                break;
            case 1:
                paciente.setCedula((String) aValue);
                break;
            case 2:
                paciente.setApellidos((String) aValue);
                break;
            case 3:
                paciente.setNombres((String) aValue);
                break;

            default:
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Paciente paciente = pacienteList.get(rowIndex);
        
        switch (columnIndex){
            case 0:
                return paciente.getNumeroFicha();
            case 1:
                return paciente.getCedula();
            case 2:
                return paciente.getApellidos();
            case 3:
                return paciente.getNombres();

            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column){
        return columnas[column];
    }

}
