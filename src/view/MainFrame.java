package view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to contain JPanels.
 */
public class MainFrame extends JFrame {

    // JPanel to contain Pacientes view.
    private final PanelPacientes panelPacientes;
    // JPanel to contain Consultas view.
    private final PanelConsultas panelConsultas;

    private JPanel panelMenu;

    private JPanel mainPanel;

    private JPanel panelSplash;

    public MainFrame(JFrame reference){

        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        reference.setVisible(false);
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.add(mainPanel);
        FlatLightLaf.setup();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(setMenuBar(), BorderLayout.NORTH);

        panelPacientes = new PanelPacientes(reference, this);
        panelConsultas = new PanelConsultas(reference, this);

        setPanelPacientes();

        this.setSize(800, 600);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setTitle("SISTEMA MEDICO");

        this.getComps(panelPacientes);
        this.getComps(panelConsultas);
        repaintComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        var img = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/view/assets/icon.png")));
        this.setIconImage(img.getImage());
    }

    private JPanel setMenuBar(){
        panelMenu = new JPanel();
        GridLayout grid = new GridLayout(1,2);
        grid.setHgap(10);
        panelMenu.setLayout(grid);

        JButton btnPacientes = new JButton("PACIENTES");
        btnPacientes.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        btnPacientes.addActionListener(e -> setPanelPacientes());

        JButton btnConsultas = new JButton("CONSULTAS");
        btnConsultas.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        btnConsultas.addActionListener(e -> setPanelConsultas());

        panelMenu.add(btnPacientes);
        panelMenu.add(btnConsultas);

        return panelMenu;
    }

    private void setPanelPacientes(){
        if(panelConsultas != null) {
            mainPanel.remove(panelConsultas);
            panelConsultas.nuevoRegistro();
        }
        mainPanel.add(panelPacientes, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private List<JPanel> panels = new ArrayList<>();

    private void getComps(JPanel panel){
        for(Component c : panel.getComponents())
            if(c.getClass().getName().contains("javax.swing.JPanel")) {
                panels.add((JPanel) (c));
                c.setBackground(new Color(167, 199, 185));
                getComps((JPanel) (c));
            }
    }

    public static void staticRepaint(JPanel panel){
        for(Component c : panel.getComponents()) {
            System.out.println(c.getClass().getName());
            c.setBackground(Color.ORANGE);
            c.setBackground(new Color(5, 132, 102));
        }
        panel.repaint();
        panel.revalidate();
    }

    private void repaintComponents(){
        for(JPanel panel : panels)
            for(Component c : panel.getComponents()) {

                if(!c.getClass().getName().contains("javax.swing.JTextField"))
                    c.setBackground(new Color(167, 199, 185));
                else
                    c.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));

                if(c.getClass().getName().contains("JButton")) {
                    c.setBackground(new Color(5, 132, 102));
                    c.setForeground(Color.white);
                    c.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
                    continue;
                }
                if(c.getClass().getName().contains("JLabel"))
                    c.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
                if(c.getClass().getName().contains("JComboBox"))
                    c.setBackground(Color.WHITE);

                c.setForeground(Color.BLACK);
            }
    }

    private void setPanelConsultas(){
        if(Objects.isNull(panelPacientes.pacienteSeleccionado))
            JOptionPane.showConfirmDialog(null, "No ha seleccionado a ningun paciente.", "", JOptionPane.PLAIN_MESSAGE);
        else
            panelConsultas.setPacienteCita(panelPacientes.pacienteSeleccionado);
        mainPanel.remove(panelPacientes);
        panelPacientes.nuevoRegistro(this.getComponents());
        panelConsultas.loadData();

        mainPanel.add(panelConsultas, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation);
    }

    public static JPanel pnlPortada(JFrame reference){
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(15);
        borderLayout.setHgap(15);

        JPanel panelSplash = new JPanel(borderLayout);
        panelSplash.setBackground(new Color(5, 132, 102));
        String htmlLabel = """
                <html>
                    
                    <div style='background-color: #058466'>
                    <h1 style='font-size: 35px; color: white;'>CONSULTORIO MÉDICO</h1><br>
                    <h1 style='font-size: 25px; color: white;'>Dr. Hugo Lucero Luzuriaga</h1>
                    <h2 style='font-size: 20px; color: white;'>Médico Salubrista - Master en Gerontologia</h2>
                    <h2 style='font-size: 17px; color: white;'>Atención especializada sobre la vejez y envejecimiento</h2>
                    <h3 style='color: white;'>Telf.: 2275278 / 2859413 - Cel.: 0995262196 - Email: blucerol@hotmail.com</h3>
                    </div>
                </html>
                """;
        JLabel labelTexto = new JLabel(htmlLabel);
        panelSplash.add(labelTexto, BorderLayout.WEST);
        String html2Label = """
                <html>
                    <img src='%1$s'/>
                </html>
                """.formatted(Main.class.getResource("/view/assets/old_couple.png"));
        JLabel label = new JLabel(html2Label);
        panelSplash.add(label, BorderLayout.EAST);

        GridLayout gridLayoutBotones = new GridLayout(1, 2);
        gridLayoutBotones.setHgap(15);

        JPanel panelBotones = new JPanel(gridLayoutBotones);

        JButton btnSalir = new JButton("SALIR");
        btnSalir.addActionListener(e -> System.exit(0));
        btnSalir.setFont(new Font(Font.DIALOG, Font.PLAIN, 22));

        JButton btnAbrir = new JButton("INICIAR EL SISTEMA");
        btnAbrir.addActionListener(e->{
            new MainFrame(reference);
        });
        btnAbrir.setFont(new Font(Font.DIALOG, Font.PLAIN, 22));

        panelBotones.add(btnAbrir);
        panelBotones.add(btnSalir);
        panelBotones.setPreferredSize(new Dimension(200, 100));
        panelBotones.setBackground(new Color(5, 132, 102));
        panelSplash.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelSplash.add(panelBotones, BorderLayout.SOUTH);

        return panelSplash;
    }
}
