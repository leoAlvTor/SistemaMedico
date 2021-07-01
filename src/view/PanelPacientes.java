package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import controller.PacienteController;
import model.Paciente;
import org.jdesktop.swingx.VerticalLayout;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.*;
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

    private JTextField autocomplete;

    private JTextArea txtAntecendetes;
    private JScrollPane jScrollPane;

    private JButton btnNuevo, btnGuardar, btnModificar, btnCancelar, btnSalir, btnBuscar;

    // DATA FROM DB
    private final PacienteController pacienteController;

    private Map<String, Paciente> nombreApellidoPacienteMap;

    public PanelPacientes(){
        pacienteController = new PacienteController();
        nombreApellidoPacienteMap = new HashMap<>();

        initLabels();
        initComboBox();
        initTextFields();
        initButtons();

        generateView();

        loadData();
    }

    private void loadData(){
        nombreApellidoPacienteMap =
                pacienteController.getAll().stream().collect(Collectors.toMap(Paciente::getNombresApellidos,
                Function.identity(), (a, b) -> a));

        AutoCompleteDecorator.decorate(autocomplete, nombreApellidoPacienteMap.keySet().stream().toList(),
                false);
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
        lblPesoKG = new JLabel("PESO KG:");
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
        txtNumeroFicha = new JTextField("");
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
        autocomplete = new JTextField(20);
    }

    private void initButtons(){
        btnNuevo = new JButton("NUEVO");
        btnGuardar = new JButton("GUARDAR");
        btnModificar = new JButton("MODIFICAR");
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");
        btnSalir = new JButton("SALIR");
        btnBuscar = new JButton("BUSCAR");

        Locale locale = new Locale("es", "ES");
        DatePickerSettings settings = new DatePickerSettings(locale);
        settings.setSizeTextFieldMinimumWidth(125);
        fechaNacimiento = new DatePicker(settings);
        fechaNacimiento.setDateToToday();

        asignarFunciones();
    }

    private void asignarFunciones(){
        btnNuevo.addActionListener(e -> nuevoRegistro());
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnModificar.addActionListener(e -> modificarRegistro());
        btnBuscar.addActionListener(e-> buscarRegistro());
        btnCancelar.addActionListener(e -> cancelarRegistro());
        btnSalir.addActionListener(e -> salir());
        btnBuscar.addActionListener(e -> buscar());
    }

    private void nuevoRegistro(){

    }

    private void guardarRegistro(){

    }

    private void modificarRegistro(){

    }

    private void buscarRegistro(){
        var jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 2));
        jPanel.add(new JLabel("INGRESE EL NOMBRE: "));
        jPanel.add(autocomplete);
        int seleccted = JOptionPane.showConfirmDialog(null, jPanel, "Buscar Ficha",
                JOptionPane.YES_NO_OPTION);
        autocomplete.requestFocus();
        if(seleccted == 0){
            mapObjectToFields(nombreApellidoPacienteMap.get(autocomplete.getText()));
        }
    }

    private void cancelarRegistro(){

    }

    private void salir(){

    }

    private void buscar(){

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
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
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

        //panelCenter.add(lblAntecedentes);

        //jScrollPane = new JScrollPane(txtAntecendetes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //panelCenter.add(jScrollPane);
        panelCenter.add(lblPesoKG);
        panelCenter.add(txtPesoKG);
        panelCenter.add(lblTallaMT);
        panelCenter.add(txtTallaMT);
        panelCenter.add(lblTipoSangre);
        panelCenter.add(comboBoxTipoSange);

        panelCenter.setLayout(gridLayout);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel internalPanel = new JPanel(new VerticalLayout());
        internalPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),  new TitledBorder(
                "INFORMACION MEDICA")));
        internalPanel.add(panelCenter);
        return internalPanel;
    }

    private void mapObjectToFields(Paciente paciente){
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
        System.out.println("ANTES");
        LocalDate localDate = LocalDate.ofInstant(paciente.getFechaNacimientoAsDate().toInstant(), ZoneId.of("America" +
                "/Guayaquil"));
        fechaNacimiento.setDate(localDate);
        System.out.println(paciente.getFechaNacimiento());
        System.out.println("DESPUES");
        this.comboBoxEstadoCivil.setSelectedItem(paciente.getEstadoCivil().toUpperCase());
        this.comboBoxTipoSange.setSelectedItem(paciente.getGrupoSanguineo().replace(" ", "")
                .toLowerCase());

    }
}