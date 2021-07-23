package view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.util.*;

/**
 * Main class to start the application.
 */
public class Main {

    /**
     * Main method.
     * Starts a new JFrame.
     *
     * @param args Class arguments.
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();

        JFrame jFrame = new JFrame("Bienvenido");
        jFrame.add(MainFrame.pnlPortada(jFrame));
        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        //var img = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/view/assets/icon.png")));
        //mainFrame.setIconImage(img.getImage());

        /*
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();
        pjob.setPrintable(null, pf);

        if (pjob.printDialog()) {
          pjob.print();
        }
         */
    }
}
