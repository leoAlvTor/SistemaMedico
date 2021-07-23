package view;

import com.formdev.flatlaf.FlatLightLaf;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Receta extends JFrame {

    private JTextArea jTextAreaAnamnesis;
    private JTextArea jTextAreaExamenes;
    private JTextArea jTextAreaDiagnostico;
    private JTextArea jTextAreaReceta;

    public Receta(JPanel parentReference, String paciente, Object ... objects){
        FlatLightLaf.setup();
        initVariables(paciente, objects);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(15);
        this.setLayout(borderLayout);
        JPanel mainPanel = generateMainJPanel();
        this.add(mainPanel, BorderLayout.CENTER);

        JButton jButton = new JButton("Crear Receta");
        jButton.addActionListener(e -> {
            ((JTextArea)objects[0]).setText(jTextAreaAnamnesis.getText());
            ((JTextField)objects[1]).setText(jTextAreaExamenes.getText());
            ((JTextField)objects[2]).setText(jTextAreaDiagnostico.getText());
            ((JTextArea)objects[3]).setText(jTextAreaReceta.getText());
            JOptionPane.showMessageDialog(null, "Se ha creado la receta correctamente.\nPuede proceder a imprimir.",
                    "Receta Lista",
                    JOptionPane.INFORMATION_MESSAGE);
            parentReference.getTopLevelAncestor().setVisible(true);
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

        this.add(panelBoton, BorderLayout.SOUTH);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getComps(mainPanel);
        repaintComponents();
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

    private void initVariables(String paciente, Object ... objects){
        jTextAreaAnamnesis = new JTextArea(20, 10);
        jTextAreaExamenes = new JTextArea(20, 10);
        jTextAreaDiagnostico = new JTextArea(20, 10);
        jTextAreaReceta = new JTextArea();

        jTextAreaAnamnesis.setText(((JTextArea) objects[0]).getText());
        jTextAreaExamenes.setText(((JTextField) objects[1]).getText());
        jTextAreaDiagnostico.setText(((JTextField) objects[2]).getText());
        jTextAreaReceta.setText(((JTextArea) objects[3]).getText());

        if(!jTextAreaReceta.getText().contains(paciente + "\nFecha: " + getDate()))
            jTextAreaReceta.setText(paciente + "\nFecha: " + getDate() + "\n\n" + jTextAreaReceta.getText());
    }

    private String getDate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    private JPanel generateMainJPanel(){
        JPanel jPanel = new JPanel(new BorderLayout());

        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.setHgap(25);
        JPanel jPanelNorth = new JPanel(gridLayout);

        jPanelNorth.add(getJPanelAnamnesis());
        jPanelNorth.add(getJPanelExamenes());
        jPanelNorth.add(getJPanelDiagnostico());
        jPanel.add(jPanelNorth, BorderLayout.NORTH);

        jPanel.add(getJPanelReceta(), BorderLayout.CENTER);
        jPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        return jPanel;
    }

    private JPanel getJPanelAnamnesis(){
        return getGenericJPanel(new JLabel("Anamnesis"), jTextAreaAnamnesis);
    }

    private JPanel getJPanelExamenes(){
        return getGenericJPanel(new JLabel("Examenes"), jTextAreaExamenes);
    }

    private JPanel getJPanelDiagnostico(){
        return getGenericJPanel(new JLabel("Diagnostico"), jTextAreaDiagnostico);
    }

    private JPanel getJPanelReceta(){
        return getGenericJPanel(new JLabel("Receta"), jTextAreaReceta);
    }

    private JPanel getGenericJPanel(JLabel label, JTextArea textArea){
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
        return panel;
    }

}
