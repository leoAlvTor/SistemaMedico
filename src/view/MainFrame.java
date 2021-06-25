package view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final PanelPacientes panelPacientes;
    private final PanelConsultas panelConsultas;

    private JMenuItem itemPacientes, itemConsultas;

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

}
