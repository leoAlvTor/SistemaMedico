package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import controller.PacienteController;
import model.Paciente;
import model.PacienteTableModel;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PanelPacientes extends JPanel {

    private JLabel lblNumeroFicha, lblCedula, lblApellidos, lblNombres,
    lblProcedencia, lblResidencia, lblDireccion, lblTelefono, lblCelular,
    lblGenero, lblFechaNacimiento, lblEdad, lblEstadoCivil,
            lblAntecedentes, lblPesoKG, lblTallaMT, lblTipoSangre;

    private JComboBox<String> comboBoxGenero, comboBoxEstadoCivil, comboBoxTipoSange;

    private JTextField txtNumeroFicha, txtCedula, txtApellidos, txtNombres,
    txtProcedencia, txtResidencia, txtDireccion, txtTelefono, txtCelular,
    txtEdad, txtPesoKG, txtTallaMT;

    private DatePicker fechaNacimiento;

    private JComboBox<String> autocomplete = new JComboBox<>();

    private JButton btnNuevo, btnGuardar, btnModificar, btnCancelar, btnSalir, btnBuscar;

    // DATA FROM DB
    public final PacienteController pacienteController;

    private Map<String, Paciente> nombreApellidoPacienteMap;
    private List<Paciente> pacientes;
    private JTable tablePaciente;

    public Paciente pacienteSeleccionado;

    protected JFrame reference, parentReference;
    public PanelPacientes(JFrame reference, JFrame parentReference){
        this.reference = reference;
        this.parentReference = parentReference;

        pacienteController = new PacienteController();
        nombreApellidoPacienteMap = new HashMap<>();
        loadData();
        initLabels();
        initComboBox();
        initTextFields();
        initButtons();
        generateView();
    }

    JScrollPane jScrollPanePaciente;
    private void loadData(){
        nombreApellidoPacienteMap =
                pacienteController.getAll().stream().collect(Collectors.toMap(Paciente::getNombresApellidos,
                Function.identity(), (a, b) -> a));
        this.pacientes = nombreApellidoPacienteMap.values().stream().toList();
        this.pacientes = this.pacientes.stream()
                .sorted(Comparator.comparing(Paciente::getApellidos)).collect(Collectors.toList());

        this.tablePaciente = new JTable(new PacienteTableModel(pacientes));
        this.tablePaciente.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        tablePaciente.setShowHorizontalLines(true);
        tablePaciente.setShowVerticalLines(true);

        tablePaciente.removeColumn(tablePaciente.getColumnModel().getColumn(0));
        tablePaciente.removeColumn(tablePaciente.getColumnModel().getColumn(0));
        jScrollPanePaciente= new JScrollPane(tablePaciente, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void initLabels(){
        lblNumeroFicha = new JLabel("N' FICHA:");
        lblCedula = new JLabel("CEDULA:");
        lblApellidos = new JLabel("APELLIDOS:");
        lblNombres = new JLabel("NOMBRES:");
        lblProcedencia = new JLabel("PROCEDENCIA:");
        lblResidencia = new JLabel("RESIDENCIA:");
        lblDireccion = new JLabel("DIRECCION:");
        lblTelefono = new JLabel("TELEFONO:");
        lblCelular = new JLabel("CELULAR:");
        lblGenero = new JLabel("GENERO:");
        lblFechaNacimiento = new JLabel("FECHA NACIMIENTO:");
        lblEdad = new JLabel("EDAD:");
        lblEstadoCivil = new JLabel("ESTADO CIVIL:");
        lblPesoKG = new JLabel("PESO EN LIBRAS:");
        lblTallaMT = new JLabel("TALLA MT:");
        lblTipoSangre = new JLabel("TIPO SANGRE:");
    }

    private void initComboBox(){
        comboBoxGenero = new JComboBox<>(new String[]{"MASCULINO", "FEMENINO"});
        comboBoxEstadoCivil = new JComboBox<>(new String[]{"SOLTERO", "CASADO", "VIUDO",
        "DIVORCIADO", "UNION LIBRE"});
        comboBoxTipoSange = new JComboBox<>(new String[]{"O+", "O-", "A+", "A-", "B+", "B-",
        "AB+", "AB-", "NINGUNO"});
    }

    private void initTextFields(){
        txtNumeroFicha = new JTextField(pacienteController.getNextIndex());
        txtNumeroFicha.setEditable(false);
        txtCedula = new JTextField("");
        txtApellidos = new JTextField("");
        txtNombres = new JTextField("");
        txtProcedencia = new JTextField("");
        txtResidencia = new JTextField("");
        txtDireccion = new JTextField("");
        txtTelefono = new JTextField("");
        txtCelular = new JTextField("");
        txtEdad = new JTextField("");
        txtPesoKG = new JTextField("");
        txtTallaMT = new JTextField("");
    }

    private void initButtons(){
        btnNuevo = new JButton("NUEVO");
        btnGuardar = new JButton("GUARDAR");
        btnModificar = new JButton("MODIFICAR");
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("ELIMINAR");
        btnSalir = new JButton("SALIR");

        Locale locale = new Locale("es", "ES");
        DatePickerSettings settings = new DatePickerSettings(locale);
        settings.setSizeTextFieldMinimumWidth(125);
        fechaNacimiento = new DatePicker(settings);

        fechaNacimiento.addDateChangeListener(dateChangeEvent -> {
            try {
                txtEdad.setText(Paciente.getAgeFromLocal(fechaNacimiento.getDate()));
            }catch (Exception e){
                System.out.println("ERROR WHILE PARSING DATE");
            }
        });

        fechaNacimiento.setDateToToday();

        asignarFunciones();
    }

    private void asignarFunciones(){
        btnNuevo.addActionListener(e -> nuevoRegistro(this.getComponents()));
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnModificar.addActionListener(e -> modificarRegistro());
        btnBuscar.addActionListener(e-> buscarRegistro());
        btnCancelar.addActionListener(e -> cancelarRegistro());
        btnSalir.addActionListener(e -> salir());
    }

    public void nuevoRegistro(Component[] components){
        txtNumeroFicha.setText(pacienteController.getNextIndex());
        txtCedula.setText("");
        txtApellidos.setText("");
        txtNombres.setText("");
        txtProcedencia.setText("");
        txtResidencia.setText("");
        comboBoxGenero.setSelectedIndex(0);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtEdad.setText("");
        comboBoxEstadoCivil.setSelectedItem(0);
        txtPesoKG.setText("");
        txtTallaMT.setText("");
        fechaNacimiento.setDateToToday();
        comboBoxTipoSange.setSelectedIndex(0);
        txtCedula.requestFocus();
    }

    private boolean verificarDatos(Paciente paciente){
        if(!paciente.verifyID()){
            JOptionPane.showMessageDialog(null, "La cedula ingresada es incorrecta.", "Error al crear el registro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(!pacienteController.verifyIfIDExists(paciente.getCedula())){
            JOptionPane.showMessageDialog(null, "La cedula ingresada ya existe.", "Error al crear el registro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(!pacienteController.verifyIfNameAndLastNameExists(paciente.getNombres(), paciente.getApellidos())){
            JOptionPane.showMessageDialog(null, "Los nombres y apellidos ingresados ya existen.", "Error al crear el " +
                    "registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            return true;
        }
    }

    private void guardarRegistro(){
        var paciente = mapFieldsToObject();

        if(pacienteController.createRecord(paciente.toList()).intValue() > 0){
            JOptionPane.showMessageDialog(null, "Se ha guardado el registro correctamente.",
                    "REGISTRO CREADO", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Error al crear el nuevo registro.", "ERROR AL CREAR",
                    JOptionPane.ERROR_MESSAGE);
        }
        loadData();
    }

    private void modificarRegistro(){
        var paciente = mapFieldsToObject();
        if(!paciente.verifyID()){
            JOptionPane.showMessageDialog(null, "La cedula ingresada es incorrecta.", "Error al crear el registro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(pacienteController.updateRecord(paciente.getNumeroFicha(), paciente.toList()) > 0){
            JOptionPane.showMessageDialog(null, "Se ha actualizado el registro correctamente.", "REGISTRO ACTUALIZADO",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro.", "ERROR AL MODIFICAR",
                    JOptionPane.ERROR_MESSAGE);
        }
        loadData();
    }

    private void buscarRegistro(){
        var jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        var jPanelBusqueda = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("INGRESE EL NOMBRE:");
        JTextField txtBusqueda = new JTextField();
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                tablePaciente.setModel(new PacienteTableModel(pacientes.parallelStream().filter(f-> f.getApellidos().toUpperCase().contains(txtBusqueda.getText().toUpperCase())).collect(Collectors.toList())));
                tablePaciente.removeColumn(tablePaciente.getColumnModel().getColumn(0));
                tablePaciente.removeColumn(tablePaciente.getColumnModel().getColumn(0));
            }
        });

        tablePaciente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pacienteSeleccionado =
                        pacienteController.getRecordById(tablePaciente.getModel().getValueAt(tablePaciente.getSelectedRow(), 0));
                mapObjectToFields(pacienteSeleccionado);
            }
        });

        jPanelBusqueda.add(lbl, BorderLayout.WEST);
        jPanelBusqueda.add(txtBusqueda, BorderLayout.CENTER);

        jPanel.add(jPanelBusqueda, BorderLayout.NORTH);

        jPanel.add(jScrollPanePaciente, BorderLayout.CENTER);
        JOptionPane.showConfirmDialog(null, jPanel, "Buscar Ficha", JOptionPane.DEFAULT_OPTION);
    }

    private void cancelarRegistro(){
        if(pacienteController.deleteRecord(txtNumeroFicha.getText()) > 0){
            JOptionPane.showMessageDialog(null, "Se ha eliminado el registro correctamente.", "REGISTRO ELIMINADO",
                    JOptionPane.INFORMATION_MESSAGE);
        }else
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro.", "ERROR AL ELIMINAR", JOptionPane.ERROR_MESSAGE);
        loadData();
    }

    private void salir(){
        this.pacienteController.closeConnection();
        parentReference.dispose();
        reference.setVisible(true);
    }

    private void generateView(){
        JPanel panelCenter = new JPanel();
        BorderLayout layoutPrimario = new BorderLayout();
        layoutPrimario.setVgap(10);
        this.setLayout(layoutPrimario);

        panelCenter.setLayout(new GridLayout(1, 2));
        panelCenter.add(getNorth());
        panelCenter.add(getCenter());
        this.add(panelCenter);
        this.add(getSouth(), BorderLayout.SOUTH);
        this.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    private JPanel getSouth(){
        JPanel panelSouth = new JPanel();
        FlowLayout gridLayoutButtons = new FlowLayout();
        //gridLayoutButtons.setColumns(5);
        gridLayoutButtons.setHgap(5);
        panelSouth.setLayout(gridLayoutButtons);
        panelSouth.add("btnNuevo", btnNuevo);
        panelSouth.add("btnGuardar", btnGuardar);
        panelSouth.add("btnModificar", btnModificar);
        panelSouth.add("btnBuscar", btnBuscar);
        panelSouth.add("btnCancelar", btnCancelar);
        panelSouth.add("btnSalir", btnSalir);

        panelSouth.setBorder(new TitledBorder("COMANDOS"));

        return panelSouth;
    }

    private JPanel getNorth(){
        JPanel jPanelNorth = new JPanel();
        GridLayout gridLayoutComponent = new GridLayout();

        gridLayoutComponent.setColumns(2);
        gridLayoutComponent.setRows(13);
        gridLayoutComponent.setHgap(-1);
        gridLayoutComponent.setVgap(5);
        jPanelNorth.setLayout(gridLayoutComponent);
        jPanelNorth.add( lblNumeroFicha);
        jPanelNorth.add(txtNumeroFicha);
        jPanelNorth.add(lblCedula);
        jPanelNorth.add(txtCedula);
        jPanelNorth.add(lblApellidos);
        jPanelNorth.add(txtApellidos);
        jPanelNorth.add(lblNombres);
        jPanelNorth.add(txtNombres);
        jPanelNorth.add(lblProcedencia);
        jPanelNorth.add(txtProcedencia);
        jPanelNorth.add(lblResidencia);
        jPanelNorth.add(txtResidencia);
        jPanelNorth.add(lblGenero);
        jPanelNorth.add( comboBoxGenero);
        jPanelNorth.add(lblDireccion);
        jPanelNorth.add(txtDireccion);
        jPanelNorth.add(lblTelefono);
        jPanelNorth.add(txtTelefono);
        jPanelNorth.add(lblCelular);
        jPanelNorth.add(txtCelular);
        jPanelNorth.add(lblFechaNacimiento);
        jPanelNorth.add(fechaNacimiento);
        jPanelNorth.add(lblEdad);
        jPanelNorth.add(txtEdad);
        jPanelNorth.add(lblEstadoCivil);
        jPanelNorth.add(comboBoxEstadoCivil);

        jPanelNorth.setBorder(new TitledBorder("DATOS DEL PACIENTE"));
        return jPanelNorth;
    }

    private JPanel getCenter(){
        JPanel panelCenter = new JPanel();
        GridLayout gridLayout = new GridLayout();
        gridLayout.setVgap(10);
        gridLayout.setColumns(2);
        gridLayout.setRows(4);

        panelCenter.add(lblPesoKG);
        panelCenter.add(txtPesoKG);
        panelCenter.add(lblTallaMT);
        panelCenter.add(txtTallaMT);
        panelCenter.add(lblTipoSangre);
        panelCenter.add(comboBoxTipoSange);

        panelCenter.setLayout(gridLayout);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel internalPanel = new JPanel(new VerticalLayout());
        internalPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 0, 10, 0),  new TitledBorder(
                "INFORMACION MEDICA")));
        internalPanel.add(panelCenter);
        return internalPanel;
    }

    private void mapObjectToFields(Paciente paciente){
        if(Objects.isNull(paciente)){
            JOptionPane.showMessageDialog(null, "No se ha encontrado ningun paciente.", "Error al buscar paciente",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for(Field field : this.getClass().getDeclaredFields()){
            if(field.getType().getName().contains("JTextField")){
                for(Field field1 : paciente.getClass().getDeclaredFields()){
                    if(field.getName().toUpperCase().contains(field1.getName().toUpperCase())){
                        try {
                            JTextField jtf = (JTextField) field.get(this);
                            var field2 = paciente.getClass().getDeclaredField(field1.getName());
                            field2.setAccessible(true);
                            jtf.setText(String.valueOf(field2.get(paciente)));
                            break;
                        }catch (Exception e){
                            System.out.println("ERROR IN REFLECTION: " + e);
                        }
                    }
                }
            }
        }
        LocalDate localDate = LocalDate.ofInstant(paciente.getFechaNacimientoAsDate().toInstant(), ZoneId.of("America" +
                "/Guayaquil"));
        fechaNacimiento.setDate(localDate);
        this.comboBoxEstadoCivil.setSelectedItem(paciente.getEstadoCivil().toUpperCase());
        this.comboBoxTipoSange.setSelectedItem(paciente.getGrupoSanguineo().replace(" ", "")
                .toUpperCase());

        this.txtEdad.setText(String.valueOf(paciente.getEdad()));
    }

    private Paciente mapFieldsToObject(){
        var paciente = new Paciente();
        for(Field field : this.getClass().getDeclaredFields()){
            if(field.getType().getName().contains("JTextField")){
                for(Field fieldPaciente : paciente.getClass().getDeclaredFields()){
                    if(field.getName().toUpperCase().contains(fieldPaciente.getName().toUpperCase())){
                        try{
                            var reflectedField = paciente.getClass().getDeclaredField(fieldPaciente.getName());
                            reflectedField.setAccessible(true);
                            if(fieldPaciente.getType().getName().contains("double")){
                                reflectedField.set(paciente, Double.valueOf(((JTextField)field.get(this)).getText()));
                            }else if(fieldPaciente.getType().getName().contains("int")){
                                reflectedField.set(paciente, Integer.valueOf(((JTextField)field.get(this)).getText()));
                            }else{
                                reflectedField.set(paciente, ((JTextField)field.get(this)).getText());
                            }
                            break;
                        }catch (Exception e){
                            System.out.println("ERROR: Reflection error: " + e);
                        }
                    }
                }
            }
        }
        paciente.setGenero(String.valueOf(this.comboBoxGenero.getSelectedItem()));
        paciente.setFechaFromLocal(this.fechaNacimiento.getDate());
        paciente.setEstadoCivil(String.valueOf(this.comboBoxEstadoCivil.getSelectedItem()));
        paciente.setGrupoSanguineo(String.valueOf(this.comboBoxTipoSange.getSelectedItem()));

        return paciente;
    }

}