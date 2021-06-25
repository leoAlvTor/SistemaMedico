package view;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        var mainFrame = new MainFrame();
        var img = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/view/assets/icon.png")));
        mainFrame.setIconImage(img.getImage());
    }
}
