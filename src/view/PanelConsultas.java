package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.CitaController;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelConsultas extends JPanel {

    private JLabel lblNumeroFicha, lblFecha, lblCodigo, lblApellidos, lblNombre, lblEdad,
    lblGenero, lblResidencia, lblProcedencia, lblAnamnesis, lblExamenes, lblDiagnostico, lblReceta;

    private JTabbedPane tabbedPane;

    private JTextField txtNumeroFicha, txtFecha, txtCodigo, txtApellido, txtNombre, txtEdad,
    txtGenero, txtResidencia, txtProcedencia, txtExamen, txtDiagnostico;

    private JTextArea txtReceta, txtHistorial, txtAnamnesis;
    private JScrollPane jScrollPane, jScrollPaneReceta, jScrollPaneAnamnesis;

    private JButton btnNuevo, btnGuardar, btnCancelar, btnImprimir, btnSalir, btnBuscar;

    public CitaController citaController;

    public PanelConsultas(){
        citaController = new CitaController();

        initLabels();
        initTextFields();
        initButtons();
        generateView();

        //repintar(this.getComponents());
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
        lblCodigo = new JLabel("CEDULA:");
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
        txtCodigo = new JTextField();
        txtApellido = new JTextField();
        txtNombre = new JTextField();
        txtEdad = new JTextField();
        txtGenero = new JTextField();
        txtResidencia = new JTextField();
        txtProcedencia = new JTextField();

        txtAnamnesis = new JTextArea();
        txtExamen = new JTextField();
        txtDiagnostico = new JTextField();
        txtReceta = new JTextArea(10, 0);
        txtHistorial = new JTextArea();
    }

    private void initButtons(){
        btnNuevo = new JButton("NUEVO");
        btnGuardar = new JButton("GUARDAR");
        btnCancelar = new JButton("CANCELAR");
        btnSalir = new JButton("SALIR");
        btnBuscar = new JButton("BUSCAR");
        btnImprimir = new JButton("IMPRIMIR");
    }

    private void asignarFunciones(){
        btnNuevo.addActionListener(e -> nuevoRegistro());
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnCancelar.addActionListener(e -> cancelarRegistro());
        btnImprimir.addActionListener(e -> imprimirRegistro());
        btnSalir.addActionListener(e -> salir());
        btnBuscar.addActionListener(e -> buscarRegistro());
    }

    private void nuevoRegistro(){

    }

    private void guardarRegistro(){

    }

    private void cancelarRegistro(){

    }

    private void imprimirRegistro(){

    }

    private void salir(){

    }

    private void buscarRegistro(){

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
        jPanelLeft.add(lblCodigo);
        jPanelLeft.add(txtCodigo);
        jPanelLeft.add(lblApellidos);
        jPanelLeft.add(txtApellido);
        jPanelLeft.add(lblNombre);
        jPanelLeft.add(txtNombre);
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
        pnl1.add(new JTable());
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
        subpnl2_1_1.add(txtExamen);
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
}
