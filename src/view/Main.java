package view;

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
        var mainFrame = new MainFrame();
        var img = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/view/assets/icon.png")));
        mainFrame.setIconImage(img.getImage());
    }
}
