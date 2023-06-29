package com.owd.core.managers;

import net.sf.jasperreports.engine.JasperReport;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;

/**
 * Created by danny on 11/18/2018.
 */
public class JasperReportPrintManagerTest {


    @Test
    public void getCompiledCommInvoiceJasperReporttest(){

        try{
            JasperReport report = JasperReportPrintManager.getCompiledCommInvoiceJasperReport();
        }catch ( Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getCompiledReturnTemplateReportTest(){


        try {
            JasperReport report = JasperReportPrintManager.getCompiledReturnTemplateReport();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void getReturnTemplateReportTest(){

        try {
            File test = new File("");
            System.out.println(test.toPath());

            File label = new File("src\\test\\java\\com\\owd\\core\\managers\\helperFiles\\SmartReturnTestLabel.png");
            byte[] image = Files.readAllBytes(label.toPath());
            System.out.println(image.length);

          //  ImageIcon icon = new ImageIcon(image);


            byte[] report =  JasperReportPrintManager.getReturnTemplatePdf("25605850", ImageIO.read(new ByteArrayInputStream(image)));

           OutputStream out = new FileOutputStream("c:/temp/return.pdf");
            out.write(report);

            out.close();



        }catch (Exception e){
            e.printStackTrace();
        }



    }
    @Test
    public void getReturnTemplateReportMergeTest(){

        try {
            File test = new File("");
            System.out.println(test.toPath());

            File label = new File("src\\test\\java\\com\\owd\\core\\managers\\helperFiles\\SmartReturnTestLabel.png");
            byte[] image = Files.readAllBytes(label.toPath());
            System.out.println(image.length);

            //  ImageIcon icon = new ImageIcon(image);


            byte[] report =  JasperReportPrintManager.getReturnTemplatePdf("25605850", ImageIO.read(new ByteArrayInputStream(image)));

            OutputStream out = new FileOutputStream("c:/temp/return.pdf");
            out.write(report);

            out.close();
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            mergerUtility.addSource(new ByteArrayInputStream(report));
            mergerUtility.addSource(new ByteArrayInputStream(report));
            mergerUtility.setDestinationFileName("C:/temp/return2.pdf");
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());




        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
