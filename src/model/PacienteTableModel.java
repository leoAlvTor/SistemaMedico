package model;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PacienteTableModel extends AbstractTableModel {

    private final String[] columnas = {"N. FICHA", "CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO", "CELULAR",
            "ESTADO CIVIL", "PROCEDENCIA", "RECIDENCIA", "FECHA NACIMIENTO", "GENERO", "ANTECEDENTES", "PESO", "TALLA",
            "GRUPO SANGUINEO"};

    public Class<?>[] tiposColumnas = {String.class, String.class, String.class, String.class, String.class,
            String.class,
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
                paciente.setNombres((String) aValue);
                break;
            case 3:
                paciente.setApellidos((String) aValue);
                break;
            case 4:
                paciente.setDireccion((String) aValue);
                break;
            case 5:
                paciente.setTelefono((String) aValue);
                break;
            case 6:
                paciente.setCelular((String) aValue);
                break;
            case 7:
                paciente.setEstadoCivil((String) aValue);
                break;
            case 8:
                paciente.setProcedencia((String) aValue);
                break;
            case 9:
                paciente.setResidencia((String) aValue);
                break;
            case 10:
                paciente.setFechaNacimiento((String) aValue);
                break;
            case 11:
                paciente.setGenero((String) aValue);
                break;
            case 12:
                paciente.setAntecedentes((String) aValue);
                break;
            case 13:
                paciente.setPeso((double) aValue);
                break;
            case 14:
                paciente.setTalla((double) aValue);
                break;
            case 15:
                paciente.setGrupoSanguineo((String) aValue);
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
                return paciente.getNombres();
            case 3:
                return paciente.getApellidos();
            case 4:
                return paciente.getDireccion();
            case 5:
                return paciente.getTelefono();
            case 6:
                return paciente.getCelular();
            case 7:
                return paciente.getEstadoCivil();
            case 8:
                return paciente.getProcedencia();
            case 9:
                return paciente.getResidencia();
            case 10:
                return paciente.getFechaNacimiento();
            case 11:
                return paciente.getGenero();
            case 12:
                return paciente.getAntecedentes();
            case 13:
                return paciente.getPeso();
            case 14:
                return paciente.getTalla();
            case 15:
                return paciente.getGrupoSanguineo();
            default:
                return "";
        }
    }
}
