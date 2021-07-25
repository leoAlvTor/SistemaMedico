package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrinterController implements Printable {

    private String texto;
    public PrinterController(String prueba) {
        this.texto = prueba;
        var job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {

            }
        }
    }

    public int print(Graphics g, PageFormat pf, int page) {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        int x = 20;
        int y = 110;
        //g.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        for(String line : this.texto.split("\n"))
            g.drawString(line, x, y+=g.getFontMetrics().getHeight());
        return PAGE_EXISTS;
    }
}
