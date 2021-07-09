package view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Class to contain JPanels.
 */
public class MainFrame extends JFrame {

    // JPanel to contain Pacientes view.
    private final PanelPacientes panelPacientes;
    // JPanel to contain Consultas view.
    private final PanelConsultas panelConsultas;

    private JMenuItem itemPacientes;
    private JMenuItem itemConsultas;

    public MainFrame(){
        UIManager.put("MenuItem.selectionBackground",new Color(29, 108, 245));
        UIManager.put("MenuItem.selectionForeground",Color.white);

        FlatLightLaf.install();
        this.setLayout(new BorderLayout());
        this.add(setMenuBar(), BorderLayout.NORTH);
        this.setSize(800, 600);
        this.setResizable(true);
        this.setVisible(true);
        this.setTitle("SISTEMA MEDICO");

        panelPacientes = new PanelPacientes();
        panelConsultas = new PanelConsultas();

        setPanelPacientes();

        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private JMenuBar setMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        itemPacientes = new JMenuItem("PACIENTES");
        itemConsultas = new JMenuItem("CONSULTAS");

        itemPacientes.addActionListener(e -> setPanelPacientes());
        itemConsultas.addActionListener(e -> setPanelConsultas());

        menuBar.add(itemPacientes);
        menuBar.add(itemConsultas);

        return menuBar;
    }

    private void setPanelPacientes(){
        //this.itemConsultas.setBackground(null);
        this.itemPacientes.setBackground(Color.ORANGE);
        this.remove(panelConsultas);
        this.add(panelPacientes, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void setPanelConsultas(){
        //this.itemPacientes.setBackground(null);
        this.itemConsultas.setBackground(Color.ORANGE);
        this.remove(panelPacientes);
        this.add(panelConsultas, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void disposeConnections(){
        panelPacientes.pacienteController.closeConnection();
        panelConsultas.citaController.closeConnection();
    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation);
    }
}
