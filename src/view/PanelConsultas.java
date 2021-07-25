package view;

import controller.AutoCompletion;
import controller.CitaController;
import controller.PacienteController;
import controller.PrinterController;
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
import java.awt.font.TextAttribute;
import java.lang.reflect.Field;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PanelConsultas extends JPanel {

    private JLabel lblNumeroFicha, lblFecha, lblCedula, lblApellidos, lblNombre, lblEdad,
    lblGenero, lblResidencia, lblProcedencia, lblAnamnesis, lblExamenes, lblDiagnostico, lblReceta;

    private JTabbedPane tabbedPane;

    private JTextField txtNumeroFicha, txtCedula, txtApellidos, txtNombres, txtEdad,
    txtGenero, txtResidencia, txtProcedencia, txtExamenes, txtDiagnostico;

    private JComboBox txtAutoComplete;

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
    private int citaSeleccionadaID = -1;

    protected JFrame reference, parentReference;

    public PanelConsultas(JFrame reference, JFrame parentReference){
        this.reference = reference;
        this.parentReference = parentReference;

        citaController = new CitaController();

        loadData();

        initLabels();
        initTextFields();
        initButtons();
        generateView();

    }

    public void loadData(){
        nombreApellidoPacienteMap =
                pacienteController.getAll().stream().collect(Collectors.toMap(Paciente::getNombresApellidos,
                        Function.identity(), (a, b) -> a));
        txtAutoComplete = new JComboBox(nombreApellidoPacienteMap.keySet().toArray());
        txtAutoComplete.setEditable(true);
        AutoCompletion.enable(txtAutoComplete);
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
        lblAnamnesis.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        lblExamenes = new JLabel("EXAMENES:");
        lblExamenes.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        lblDiagnostico = new JLabel("DIAGNOSTICO:");
        lblDiagnostico.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        lblReceta = new JLabel("RECETA:");
        lblReceta.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
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

    }

    private void initButtons(){
        btnNuevo = new JButton("NUEVO");
        btnGuardar = new JButton("GUARDAR");
        btnCancelar = new JButton("ELIMINAR");
        btnSalir = new JButton("SALIR");
        btnBuscar = new JButton("BUSCAR");
        btnImprimir = new JButton("IMPRIMIR");
        asignarFunciones();
    }

    private void asignarFunciones(){
        btnNuevo.addActionListener(e -> nuevoRegistro());
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnCancelar.addActionListener(e -> cancelarRegistro());
        btnImprimir.addActionListener(e -> imprimirRegistro());
        btnSalir.addActionListener(e -> salir());
        btnBuscar.addActionListener(e -> buscarRegistro());
    }

    public void nuevoRegistro(){
        txtNumeroFicha.setText("");
        txtCedula.setText("");
        txtApellidos.setText("");
        txtNombres.setText("");
        txtEdad.setText("");
        txtGenero.setText("");
        txtResidencia.setText("");
        txtProcedencia.setText("");

        txtHistorial.setText("");
        tablaHistorial.setModel(new CitaTableModel());
        tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(1));
        txtAnamnesis.setText("");
        txtExamenes.setText("");
        txtDiagnostico.setText("");
        txtReceta.setText("");
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
        imprimirRegistro();
        loadData();
        mapObjectToFields(paciente);
        tablaHistorial.setModel(new CitaTableModel(citaController.getAllByPaciente(paciente.getNumeroFicha())));
        tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(1));
    }

    private void cancelarRegistro(){
        if(citaSeleccionadaID == -1) {
            JOptionPane.showMessageDialog(null, "Debe selecionar un registro de la tabla antes de eliminarlo.",
                    "Error al eliminar",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(JOptionPane.showConfirmDialog(null, "Desea eliminar el registro #"+citaSeleccionadaID+"?",
                "Eliminar Registro", JOptionPane.YES_NO_OPTION) == 0) {
            if (citaController.deleteRecord(citaSeleccionadaID) > 0) {
                JOptionPane.showMessageDialog(null, "Se ha eliminado el registro correctamente.", "Se elimino el " +
                        "registro", JOptionPane.INFORMATION_MESSAGE);
                tablaHistorial.setModel(new CitaTableModel(citaController.getAllByPaciente(paciente.getNumeroFicha())));
                tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(1));
                loadData();
                nuevoRegistro();
                citaSeleccionadaID = -1;
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido eliminar el registro seleccionado.", "Error al " +
                        "eliminar una cita.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JTextArea textoCabecera = new JTextArea();
    private String breakLines(String texto){
        StringTokenizer tok = new StringTokenizer(texto, " ");
        StringBuilder output = new StringBuilder(texto.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken()+ " ";

            if (lineLen + word.length() > 120) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word);
            lineLen += word.length();
        }
        return output.toString();
    }

    private void imprimirRegistro(){
        if(!txtReceta.getText().equals("")){
            try {
                new PrinterController(textoCabecera.getText() + breakLines(txtReceta.getText()));
                JOptionPane.showMessageDialog(null, "Imprimiendo...", "Impresion en progreso", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "La receta esta vacia.", "No se pudo imprimir", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void salir(){
        this.citaController.closeConnection();
        this.pacienteController.closeConnection();
        parentReference.dispose();
        reference.setVisible(true);
        //System.exit(0);
    }

    private void buscarRegistro(){
        var jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 2));
        jPanel.add(new JLabel("INGRESE EL NOMBRE: "));
        jPanel.add(txtAutoComplete);
        if(JOptionPane.showConfirmDialog(null, jPanel, "BUSCAR FICHA", JOptionPane.PLAIN_MESSAGE) == 0){
            paciente = nombreApellidoPacienteMap.get(String.valueOf(txtAutoComplete.getSelectedItem()));
            mapObjectToFields(paciente);

            tablaHistorial.setModel(new CitaTableModel(citaController.getAllByPaciente(paciente.getNumeroFicha())));
            tablaHistorial.removeColumn(tablaHistorial.getColumnModel().getColumn(1));
        }
        loadData();
    }

    private void generateView(){
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(15);
        this.setLayout(gridLayout);

        this.add(getNorth());
        this.add(getSouth());

        this.setBorder(new EmptyBorder(10, 0, 10, 0));

        txtNumeroFicha.setEditable(false);
        txtApellidos.setEditable(false);
        txtEdad.setEditable(false);
        txtResidencia.setEditable(false);

        txtCedula.setEditable(false);
        txtNombres.setEditable(false);
        txtGenero.setEditable(false);
        txtProcedencia.setEditable(false);
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

        jPanelNorth.add(jPanelLeft, BorderLayout.CENTER);

        return jPanelNorth;
    }

    private JPanel getSouth(){
        tabbedPane = new JTabbedPane();
        jScrollPane = new JScrollPane(txtHistorial, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel pnl1 = new JPanel();
        pnl1.setLayout(new BorderLayout());
        pnl1.add(jScrollPane);

        tablaHistorial = new JTable(new CitaTableModel());
        tablaHistorial.setShowHorizontalLines(true);
        tablaHistorial.setShowVerticalLines(true);



        tablaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                var point = e.getPoint();
                var row = table.rowAtPoint(point);
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    citaSeleccionadaID = (int)table.getModel().getValueAt(row, 0);
                    var pacienteNombres =
                            pacienteController.getRecordById(table.getModel().getValueAt(row, 1)).getNombresApellidos();
                    var fecha = String.valueOf(table.getModel().getValueAt(row, 2));
                    var anamnesis = String.valueOf(table.getModel().getValueAt(row,3));
                    var receta = String.valueOf(table.getModel().getValueAt(row, 4));

                    txtAnamnesis.setText(anamnesis);
                    txtReceta.setText(receta);

                    tabbedPane.setSelectedIndex(1);

                    Cita cita = citaController.getRecordById(table.getModel().getValueAt(row, 0));
                    mapObjectToFields(cita);
                    txtAnamnesis.setText(cita.getAnamnesis());
                    txtReceta.setText(cita.getReceta());
                }
            }
        });

        pnl1.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        tabbedPane.add("HISTORIAL", pnl1);

        JPanel pnl2 = new JPanel();
        pnl2.setBackground(new Color(167, 199, 185));
        pnl2.setBorder(new EmptyBorder(10, 0, 10,0));
        GridLayout gridLayout = new GridLayout();
        gridLayout.setVgap(5);
        gridLayout.setHgap(5);
        pnl2.setLayout(gridLayout);

        JPanel subpnl2_1 = new JPanel();
        subpnl2_1.setBackground(new Color(167, 199, 185));
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        subpnl2_1.setLayout(borderLayout);

        JPanel pnl2_1 = new JPanel(new GridLayout(1 ,2));
        pnl2_1.setBackground(new Color(167, 199, 185));
        pnl2_1.add(lblAnamnesis);

        jScrollPaneAnamnesis = new JScrollPane(txtAnamnesis, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pnl2_1.add(jScrollPaneAnamnesis);

        JPanel subpnl2_1_1 = new JPanel();
        subpnl2_1_1.setBackground(new Color(167, 199, 185));
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
        subpnl2_2.setBackground(new Color(167, 199, 185));
        subpnl2_2.setLayout(new VerticalLayout());
        subpnl2_2.add(lblReceta);
        jScrollPaneReceta = new JScrollPane(txtReceta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        subpnl2_2.add(jScrollPaneReceta);
        pnl2.add(subpnl2_2);

        tabbedPane.add("NUEVA CONSULTA", new JPanel());
        tabbedPane.addChangeListener(e->{
            if(tabbedPane.getSelectedIndex()==1) {
                if(pacienteController.getRecordById(txtNumeroFicha.getText()) != null) {
                    tabbedPane.setSelectedIndex(0);
                    new Receta(this, txtNombres.getText() + " " + txtApellidos.getText(), btnGuardar, txtAnamnesis,
                            txtExamenes,
                            txtDiagnostico,
                            txtReceta, textoCabecera);

                    this.getTopLevelAncestor().setVisible(false);
                }else {
                    tabbedPane.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Debe buscar a un paciente antes de generar una nueva receta" +
                            ".", "No ha selecciona a ningun paciente", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel jPanelSouth = new JPanel();
        jPanelSouth.setBackground(new Color(167, 199, 185));
        jPanelSouth.setLayout(new BorderLayout());

        jPanelSouth.add(tabbedPane, BorderLayout.CENTER);

        JPanel jPanelRight = new JPanel();
        jPanelRight.setBorder(new TitledBorder("COMANDOS"));
        GridLayout gridLayout2 = new GridLayout(1, 6);
        jPanelRight.setLayout(gridLayout2);

        jPanelRight.add(btnBuscar);
        jPanelRight.add(btnNuevo);
        jPanelRight.add(btnGuardar);
        jPanelRight.add(btnCancelar);
        jPanelRight.add(btnImprimir);
        jPanelRight.add(btnSalir);

        jPanelSouth.add(jPanelRight, BorderLayout.SOUTH);

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
        if(citaParam.getClass().getName().toUpperCase().contains("PACIENTE"))
            this.txtEdad.setText(String.valueOf(((Paciente) citaParam).getEdad()));
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
