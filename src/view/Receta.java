package view;

import com.formdev.flatlaf.FlatLightLaf;
import model.Paciente;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Receta extends JFrame {

    private JTextArea jTextAreaAnamnesis;
    private JTextArea jTextAreaExamenes;
    private JTextArea jTextAreaDiagnostico;
    private JTextArea jTextAreaTratamiento;

    private JTextArea jTextAreaReceta;

    private String paciente;
    private Object[] objects;
    public Receta(JPanel parentReference, String paciente, JButton btnGuardarReceta, Object ... objects){
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        this.paciente = paciente;
        this.objects = objects;
        FlatLightLaf.setup();
        initVariables(paciente, objects);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(15);
        this.setLayout(borderLayout);
        JPanel mainPanel = generateMainJPanel();
        this.add(getPanelInformacion(), BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        JButton jButton = new JButton("Crear Receta");
        jButton.addActionListener(e -> {
            ((JTextArea)objects[0]).setText(jTextAreaAnamnesis.getText());
            ((JTextField)objects[1]).setText(jTextAreaExamenes.getText());
            ((JTextField)objects[2]).setText(jTextAreaDiagnostico.getText());
            ((JTextArea)objects[3]).setText(jTextAreaReceta.getText());
            int totalLenght = paciente.length() + " Fecha: ".length() + getDate().length();
            String spaces = paciente;
            for (int i = totalLenght; i < 120; i++) {
                spaces += " ";
            }
            ((JTextArea)objects[4]).setText(spaces + "Fecha: " + getDate() + "\n\n");
            ((JTextField)objects[6]).setText(jTextAreaTratamiento.getText());
            JOptionPane.showMessageDialog(null, "Se ha creado la receta correctamente.\nPuede proceder a imprimir.",
                    "Receta Lista",
                    JOptionPane.INFORMATION_MESSAGE);
            parentReference.getTopLevelAncestor().setVisible(true);
            this.setVisible(false);
            btnGuardarReceta.doClick();
            this.dispose();
        });

        parentReference.setVisible(true);
        jButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        jButton.setBackground(new Color(167, 199, 185));

        GridLayout gridLayout = new GridLayout(1, 2);
        gridLayout.setHgap(15);
        JPanel panelBoton = new JPanel(gridLayout);
        panelBoton.setBorder(new EmptyBorder(0, 10, 10, 10));
        panelBoton.add(jButton);

        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        btnLimpiar.setBackground(new Color(167, 199, 185));
        btnLimpiar.addActionListener(e -> {
            limpiar();
        });
        panelBoton.add(btnLimpiar);
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        btnCancelar.setBackground(new Color(167, 199, 185));
        btnCancelar.addActionListener(e -> {
            parentReference.getTopLevelAncestor().setVisible(true);
            this.dispose();
        });
        panelBoton.add(btnCancelar);

        this.add(panelBoton, BorderLayout.SOUTH);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getComps(mainPanel);
        repaintComponents();
    }

    private JPanel getPanelInformacion(){
        JLabel jLabelNombre = new JLabel("NOMBRE:");
        jLabelNombre.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        JLabel jLabelDireccion = new JLabel("DIRECCION:");
        jLabelDireccion.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        JLabel jLabelProcedencia = new JLabel("PROCEDENCIA:");
        jLabelProcedencia.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        JLabel lblNombre = new JLabel(this.objPaciente.getNombresApellidos());
        lblNombre.setFont(jLabelNombre.getFont());

        JLabel lblDireccion = new JLabel(this.objPaciente.getDireccion());
        lblDireccion.setFont(jLabelNombre.getFont());

        JLabel lblProcedencia = new JLabel(this.objPaciente.getProcedencia());
        lblProcedencia.setFont(lblDireccion.getFont());

        JPanel jPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(3, 2);
        gridLayout.setVgap(5);
        jPanel.setLayout(gridLayout);

        jPanel.add(jLabelNombre);
        jPanel.add(lblNombre);

        jPanel.add(jLabelDireccion);
        jPanel.add(lblDireccion);

        jPanel.add(jLabelProcedencia);
        jPanel.add(lblProcedencia);

        jPanel.setBackground(new Color(167, 199, 185));

        JPanel superPanel = new JPanel(new GridLayout(1, 2));
        superPanel.add(jPanel);

        JPanel dummypanel = new JPanel();
        dummypanel.setBackground(new Color(167, 199, 185));
        superPanel.add(dummypanel);

        return superPanel;
    }

    private java.util.List<JPanel> panels = new ArrayList<>();
    private void getComps(JPanel panel){
        for(Component c : panel.getComponents())
            if(c.getClass().getName().contains("javax.swing.JPanel")) {
                panels.add((JPanel) (c));
                c.setBackground(new Color(167, 199, 185));
                getComps((JPanel) (c));
            }
    }

    private void repaintComponents(){
        for(JPanel panel : panels)
            for(Component c : panel.getComponents()) {
                if(!c.getClass().getName().contains("javax.swing.JTextField"))
                    c.setBackground(new Color(167, 199, 185));
                if(c.getClass().getName().contains("JButton")) {
                    c.setBackground(new Color(5, 132, 102));
                    c.setForeground(Color.white);
                    continue;
                }
                c.setForeground(Color.BLACK);
            }
    }

    public void limpiar(){
        jTextAreaAnamnesis.setText("");
        jTextAreaExamenes.setText("");
        jTextAreaDiagnostico.setText("");
        jTextAreaReceta.setText("");
        jTextAreaAnamnesis.requestFocus();
    }

    private Paciente objPaciente;
    private void initVariables(String paciente, Object ... objects){
        jTextAreaAnamnesis = new JTextArea(4, 10);
        jTextAreaExamenes = new JTextArea(4, 10);
        jTextAreaDiagnostico = new JTextArea(4, 10);
        jTextAreaTratamiento = new JTextArea(4, 10);

        jTextAreaReceta = new JTextArea();
        jTextAreaReceta.setLineWrap(true);
        jTextAreaReceta.setWrapStyleWord(true);

        jTextAreaAnamnesis.setText(((JTextArea) objects[0]).getText());
        jTextAreaExamenes.setText(((JTextField) objects[1]).getText());
        jTextAreaDiagnostico.setText(((JTextField) objects[2]).getText());
        jTextAreaReceta.setText(((JTextArea) objects[3]).getText());

        objPaciente = (Paciente) objects[5];
        jTextAreaTratamiento.setText(((JTextField)objects[6]).getText());
    }

    private String getDate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    private JPanel generateMainJPanel(){
        JPanel jPanel = new JPanel(new GridLayout(1, 2));

        GridLayout gridLayout = new GridLayout(1, 1);
        gridLayout.setHgap(25);
        JPanel jPanelNorth = new JPanel(gridLayout);

        jPanelNorth.add(getJPanelAnamnesis());
        jPanel.add(jPanelNorth);

        jPanel.add(getJPanelReceta());
        jPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        return jPanel;
    }

    private JPanel getJPanelAnamnesis(){
        var panelIzquierda = new JPanel();

        var anamnesis = getGenericJPanel(new JLabel("Historia clinica Anamnesis"), jTextAreaAnamnesis,
                new Dimension(200, 300));
        var examenes = getGenericJPanel(new JLabel("Examenes"), jTextAreaExamenes, new Dimension(200, 100));
        var diagnostico = getGenericJPanel(new JLabel("Diagnostico"), jTextAreaDiagnostico, new Dimension(200, 100));
        var tratamiento = getGenericJPanel(new JLabel("Tratamiento"), jTextAreaTratamiento, new Dimension(200, 200));
        JPanel[] panels = new JPanel[]{anamnesis, examenes, diagnostico, tratamiento};
        LayoutManager layoutManager = new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS);
        panelIzquierda.setLayout(layoutManager);
        var boxes = new Box[4];
        for (var i = 0; i < boxes.length; i++) {
            boxes[i] = Box.createHorizontalBox();
            boxes[i].createGlue();
            panelIzquierda.add(boxes[i]);
            boxes[i].add(panels[i]);
        }

        return panelIzquierda;
    }

    private JPanel getJPanelExamenes(){
        return getGenericJPanel(new JLabel("Examenes"), jTextAreaExamenes, null);
    }

    private JPanel getJPanelDiagnostico(){
        return getGenericJPanel(new JLabel("Diagnostico"), jTextAreaDiagnostico, null);
    }

    private JPanel getJPanelReceta(){
        return getGenericJPanel(new JLabel("Receta"), jTextAreaReceta, null);
    }

    private JPanel getGenericJPanel(JLabel label, JTextArea textArea, Dimension preferedSize){
        label.setBackground(new Color(5, 132, 102));
        textArea.setLineWrap(true);
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        JPanel panel = new JPanel(borderLayout);

        JScrollPane jScrollPane = new JScrollPane(textArea);

        panel.add(label, BorderLayout.NORTH);
        panel.add(jScrollPane, BorderLayout.CENTER);
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        if(preferedSize!=null)
            panel.setPreferredSize(preferedSize);
        return panel;
    }

}
