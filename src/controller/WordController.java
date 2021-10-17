package controller;

import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class WordController {
    public WordController(String textToWrite){
        createDocument(textToWrite);
    }

    private void createDocument(String textToWrite){

        try(XWPFDocument document = new XWPFDocument()){
            XWPFParagraph p1 = document.createParagraph();
            p1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run = p1.createRun();
            Font font = new java.awt.Font("Calibri", Font.PLAIN, 14);
            run.setFontFamily(font.getFamily());
            run.setFontSize(font.getSize());
            if(!textToWrite.contains("\n")) {
                run.setText(textToWrite);
                for (int i = 0; i < 4; i++) {
                    run.addBreak();
                }
            }else{
                for (int i = 0; i < 4; i++) {
                    run.addBreak();
                }
                String[] lines = textToWrite.split("\n");
                run.setText(lines[0], 0);

                for (int i = 1; i < lines.length; i++) {
                    run.addBreak();
                    run.setText(lines[i]);
                }
            }
            try(FileOutputStream outputStream = new FileOutputStream("documento.docx")){
                document.write(outputStream);

                Desktop.getDesktop().print(new File("documento.docx"));
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){

        }

    }

}
