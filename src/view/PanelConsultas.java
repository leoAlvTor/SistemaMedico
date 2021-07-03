package view;

import controller.CitaController;
import controller.PacienteController;
import model.Cita;
import model.CitaTableModel;
import model.Paciente;
import org.jdesktop.swingx.VerticalLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PanelConsultas extends JPanel {

    private JLabel lblNumeroFicha, lblFecha, lblCedula, lblApellidos, lblNombre, lblEdad,
    lblGenero, lblResidencia, lblProcedencia, lblAnamnesis, lblExamenes, lblDiagnostico, lblReceta;

    private JTabbedPane tabbedPane;

    private JTextField txtNumeroFicha, txtCedula, txtApellidos, txtNombres, txtEdad,
    txtGenero, txtResidencia, txtProcedencia, txtExamenes, txtDiagnostico, txtAutoComplete;

    private JTextArea txtReceta, txtHistorial, txtAnamnesis;
    private JScrollPane jScrollPane, jScrollPaneReceta, jScrollPaneAnamnesis;

    private JButton btnNuevo, btnGuardar, btnCancelar, btnImprimir, btnSalir, btnBuscar;

    public CitaController citaController = new CitaController();
    public PacienteController pacienteController = new PacienteController();

    private JTable tablaHistorial;


    // DATA FROM DB
    private Paciente paciente;
    private Map<String, Paciente> nombreApellidoPacienteMap;
    private List<Cita> citaList;

    public PanelConsultas(){
        citaController = new CitaController();

        initLabels();
        initTextFields();
        initButtons();
        generateView();

        loadData();
    }

    private void loadData(){
        nombreApellidoPacienteMap =
                pacienteController.getAll().stream().collect(Collectors.toMap(Paciente::getNombresApellidos,
                        Function.identity(), (a, b) -> a));
        AutoCompleteDecorator.decorate(txtAutoComplete, nombreApellidoPacienteMap.keySet().stream().toList(), false);

    }

    private void repintar(Component[] components){
        for(Component component : components){
            if(component.getClass() == JPanel.class) {
                component.setBackground(Color.ORANGE);
                repintar(((JPanel) component).getComponents());
            }
        }
    }

    private void initLabels(){
        lblNumeroFicha = new JLabel("NUM. FICHA:");
        lblCedula = new JLabel("CEDULA:");
        lblApellidos = new JLabel("APELLIDOS:");
        lblNombre = new JLabel("NOMBRES:");
        lblEdad = new JLabel("EDAD:");
        lblGenero = new JLabel("GENERO:");
        lblResidencia = new JLabel("RESIDENCIA:");
        lblProcedencia = new JLabel("PROCEDENCIA:");
        lblAnamnesis = new JLabel("ANAMNESIS Y EXAMEN FISICO:");
        lblExamenes = new JLabel("EXAMENES:");
        lblDiagnostico = new JLabel("DIAGNOSTICO:");
        lblReceta = new JLabel("RECETA:");
    }

    private void initTextFields(){
        txtNumeroFicha = new JTextField();
        txtCedula = new JTextField();
        txtApellidos = new JTextField();
        txtNombres = new JTextField();
        txtEdad = new JTextField();
        txtGenero = new JTextField();
        txtResidencia = new JTextField();
        txtProcedencia = new JTextField();

        txtAnamnesis = new JTextArea();
        txtExamenes = new JTextField();
        txtDiagnostico = new JTextField();
        txtReceta = new JTextArea(10, 0);
        txtHistorial = new JTextArea();

        txtAutoComplete = new JTextField();
    }

    private void initButtons(){
        btnNuevo = new JButton("NUEVO");
        btnGuardar = new JButton("GUARDAR");
        btnCancelar = new JButton("CANCELAR");
        btnSalir = new JButton("SALIR");
        btnBuscar = new JButton("BUSCAR");
        btnImprimir = new JButton("IMPRIMIR");
        asignarFunciones();
    }

    private void asignarFunciones(){
        btnNuevo.addActionListener(e -> nuevoRegistro(this.getComponents()));
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnCancelar.addActionListener(e -> cancelarRegistro());
        btnImprimir.addActionListener(e -> imprimirRegistro());
        btnSalir.addActionListener(e -> salir());
        btnBuscar.addActionListener(e -> buscarRegistro());
    }

    private void nuevoRegistro(Component[] componentParam){
        for(Component component : componentParam)
            if(component.getClass().getName().contains("JPanel"))
                nuevoRegistro(((JPanel) component).getComponents());
            else
                switch (component.getClass().getName()){
                    case "javax.swing.JTextField":
                        ((JTextField)component).setText("");
                        break;
                    case "javax.swing.JComboBox":
                        ((JComboBox)component).setSelectedIndex(0);
                        break;
                    case "javax.swing.JTextArea":
                        ((JTextArea)component).setText("");
                        break;
                    default:
                        break;
                }
    }

    private void guardarRegistro(){
        var cita = mapFieldsToObject();

        if(citaController.createRecord(cita.toList()).intValue() > 0){
            JOptionPane.showMessageDialog(null, "Se ha guardado la consulta correctamente.",
                    "CONSULTA CREADA", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Error al crear la consulta.", "ERROR AL CREAR",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cancelarRegistro(){

    }

    private void imprimirRegistro(){

    }

    private void salir(){
        this.citaController.closeConnection();
        this.pacienteController.closeConnection();
        System.exit(0);
    }

    private void buscarRegistro(){
        var jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 2));
        jPanel.add(new JLabel("INGRESE EL NOMBRE: "));
        jPanel.add(txtAutoComplete);
        if(JOptionPane.showConfirmDialog(null, jPanel, "BUSCAR FICHA", JOptionPane.YES_NO_OPTION) == 0){
            paciente = nombreApellidoPacienteMap.get(txtAutoComplete.getText());
            mapObjectToFields(paciente);

            tablaHistorial.setModel(new CitaTableModel(citaController.getAllByPaciente(paciente.getNumeroFicha())));
        }
    }

    private void generateView(){
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(15);
        this.setLayout(gridLayout);

        this.add(getNorth());
        this.add(getSouth());

        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private JPanel getNorth(){
        JPanel jPanelNorth = new JPanel();

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);

        jPanelNorth.setLayout(borderLayout);

        JPanel jPanelLeft = new JPanel();
        jPanelLeft.setBorder(new TitledBorder("DATOS DEL PACIENTE"));
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        jPanelLeft.setLayout(gridLayout);

        jPanelLeft.add(lblNumeroFicha);
        jPanelLeft.add(txtNumeroFicha);
        jPanelLeft.add(lblCedula);
        jPanelLeft.add(txtCedula);
        jPanelLeft.add(lblApellidos);
        jPanelLeft.add(txtApellidos);
        jPanelLeft.add(lblNombre);
        jPanelLeft.add(txtNombres);
        jPanelLeft.add(lblEdad);
        jPanelLeft.add(txtEdad);
        jPanelLeft.add(lblGenero);
        jPanelLeft.add(txtGenero);
        jPanelLeft.add(lblResidencia);
        jPanelLeft.add(txtResidencia);
        jPanelLeft.add(lblProcedencia);
        jPanelLeft.add(txtProcedencia);

        JPanel jPanelRight = new JPanel();
        jPanelRight.setBorder(new TitledBorder("COMANDOS"));
        GridLayout gridLayout1 = new GridLayout(6, 1);
        gridLayout1.setVgap(5);
        jPanelRight.setLayout(gridLayout1);

        jPanelRight.add(btnBuscar);
        jPanelRight.add(btnNuevo);
        jPanelRight.add(btnGuardar);
        jPanelRight.add(btnCancelar);
        jPanelRight.add(btnImprimir);
        jPanelRight.add(btnSalir);

        jPanelNorth.add(jPanelLeft, BorderLayout.CENTER);
        jPanelNorth.add(jPanelRight, BorderLayout.EAST);

        return jPanelNorth;
    }

    private JPanel getSouth(){
        tabbedPane = new JTabbedPane();
        jScrollPane = new JScrollPane(txtHistorial, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel pnl1 = new JPanel();
        pnl1.setLayout(new GridLayout(1, 2));
        pnl1.add(jScrollPane);

        tablaHistorial = new JTable(new CitaTableModel());

        tablaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                var point = e.getPoint();
                var row = table.rowAtPoint(point);
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    var pacienteNombres =
                            pacienteController.getRecordById(table.getModel().getValueAt(row, 0)).getNombresApellidos();
                    var fecha = String.valueOf(table.getModel().getValueAt(row, 2));
                    var anamnesis = String.valueOf(table.getModel().getValueAt(row,3));
                    var receta = String.valueOf(table.getModel().getValueAt(row, 4));
                    var diagnostico = String.valueOf(table.getModel().getValueAt(row, 5));
                    var examenes = String.valueOf(table.getModel().getValueAt(row, 6));

                    txtHistorial.setText(String.format("""
                            PACIENTE:
                            %1$s
                            
                            FECHA:
                            %2$s
                            
                            ANAMNESIS:
                            %3$s
                            
                            RECETA:
                            %4$s
                            
                            DIAGNOSTICO:
                            %5$s
                            
                            EXAMENES:
                            %6$s
                            """, pacienteNombres, fecha, anamnesis, receta, diagnostico, examenes));
                    Cita cita = citaController.getRecordById(table.getModel().getValueAt(row, 0));
                    mapObjectToFields(cita);
                    txtAnamnesis.setText(cita.getAnamnesis());
                    txtReceta.setText(cita.getReceta());
                }
            }
        });

        pnl1.add(new JScrollPane(tablaHistorial));
        tabbedPane.add("HISTORIAL", pnl1);

        JPanel pnl2 = new JPanel();
        pnl2.setBorder(new EmptyBorder(10, 10, 10,10));
        GridLayout gridLayout = new GridLayout();
        gridLayout.setVgap(5);
        gridLayout.setHgap(5);
        pnl2.setLayout(gridLayout);

        JPanel subpnl2_1 = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        subpnl2_1.setLayout(borderLayout);

        JPanel pnl2_1 = new JPanel(new GridLayout(1 ,2));
        pnl2_1.add(lblAnamnesis);

        jScrollPaneAnamnesis = new JScrollPane(txtAnamnesis, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pnl2_1.add(jScrollPaneAnamnesis);

        JPanel subpnl2_1_1 = new JPanel();
        GridLayout gridLayout1 = new GridLayout(2, 2);
        gridLayout1.setVgap(5);
        gridLayout1.setHgap(5);
        subpnl2_1_1.setLayout(gridLayout1);

        subpnl2_1_1.add(lblExamenes);
        subpnl2_1_1.add(txtExamenes);
        subpnl2_1_1.add(lblDiagnostico);
        subpnl2_1_1.add(txtDiagnostico);

        subpnl2_1.add(pnl2_1, BorderLayout.CENTER);
        subpnl2_1.add(subpnl2_1_1, BorderLayout.SOUTH);

        pnl2.add(subpnl2_1);

        JPanel subpnl2_2 = new JPanel();
        subpnl2_2.setLayout(new VerticalLayout());
        subpnl2_2.add(lblReceta);
        jScrollPaneReceta = new JScrollPane(txtReceta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        subpnl2_2.add(jScrollPaneReceta);
        pnl2.add(subpnl2_2);

        tabbedPane.add("NUEVA CONSULTA", pnl2);

        JPanel jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout());

        jPanelSouth.add(tabbedPane);
        return jPanelSouth;
    }

    private void mapObjectToFields(Object citaParam){
        for(Field field : this.getClass().getDeclaredFields()){
            if(field.getType().getName().contains("JTextField")){
                for(Field field1 : citaParam.getClass().getDeclaredFields()){
                    if(field.getName().toUpperCase().contains(field1.getName().toUpperCase())){
                        try {
                            JTextField jtf = (JTextField) field.get(this);
                            var field2 = citaParam.getClass().getDeclaredField(field1.getName());
                            field2.setAccessible(true);
                            jtf.setText(String.valueOf(field2.get(citaParam)));
                            break;
                        }catch (Exception e){
                            System.out.println("ERROR IN REFLECTION: " + e);
                        }
                    }
                }
            }
        }
    }

    private Cita mapFieldsToObject(){
        if(!Objects.isNull(paciente)) {
            var cita = new Cita();
            cita.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            cita.setNumeroFicha(paciente.getNumeroFicha());
            cita.setAnamnesis(txtAnamnesis.getText());
            cita.setReceta(txtReceta.getText());
            cita.setDiagnostico(txtDiagnostico.getText());
            cita.setExamenes(txtExamenes.getText());

            cita.setPaciente(paciente);

            return cita;
        }else {
            JOptionPane.showMessageDialog(null, "Debe buscar a un paciente antes de generar una cita.", "ERROR AL " +
                            "CREAR CITA", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

}
