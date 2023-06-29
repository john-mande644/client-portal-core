package com.owd.dc.manifest.api;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Copies;
import javax.print.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 18, 2008
 * Time: 2:53:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintGif {

    public static void main(String args[]) {

            /* Use the pre-defined flavor for a GIF from an InputStream */
            DocFlavor flavor = DocFlavor.INPUT_STREAM.GIF;

            /* Specify the type of the output stream */
            String psMimeType = DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType();

            /* Locate factory which can export a GIF image stream as Postscript */
            StreamPrintServiceFactory[] factories =
                StreamPrintServiceFactory.lookupStreamPrintServiceFactories(
                        flavor, psMimeType);
            if (factories.length == 0) {
                System.err.println("No suitable factories");
                System.exit(0);
            }

            try {
                /* Load the file */
                FileInputStream fis = new FileInputStream("/testpdf.gif");
                /* Create a file for the exported postscript */
                String filename = "newfile.ps";
                FileOutputStream fos = new FileOutputStream(filename);

                /* Create a Stream printer for Postscript */
                StreamPrintService sps = factories[0].getPrintService(fos);

                /* Create and call a Print Job for the GIF image */
                DocPrintJob pj = sps.createPrintJob();

                PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
                aset.add(new Copies(2));


                Doc doc = new SimpleDoc(fis, flavor, null);

                pj.print(doc, aset);
                fos.close();

            } catch (PrintException pe) {
                System.out.println(pe);
            } catch (Exception ie) {
                System.out.println(ie);
            }

    }
        public static void main2(String args[]) {

            /* Use the pre-defined flavor for a GIF from an InputStream */
            DocFlavor flavor = DocFlavor.INPUT_STREAM.GIF;

            /* Create a set which specifies how the job is to be printed */
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(MediaSizeName.NA_LETTER);
            aset.add(new Copies(1));

            /* Locate print services which can print a GIF in the manner specified */
            PrintService pservices =
                PrintServiceLookup.lookupDefaultPrintService();

            if (pservices != null) {
                /* Create a Print Job */
                DocPrintJob printJob = pservices.createPrintJob();

                /* Create a Doc implementation to pass the print data */
                Doc doc = new InputStreamDoc("/testpdf.gif", flavor);

                /* Print the doc as specified */
                try {
                    printJob.print(doc, aset);
                } catch (PrintException e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("No suitable printers");
            }
        }


  static   class InputStreamDoc implements Doc {
        private String filename;
        private DocFlavor docFlavor;
        private InputStream stream;

        public InputStreamDoc(String name, DocFlavor flavor) {
            filename = name;
            docFlavor = flavor;
        }

        public DocFlavor getDocFlavor() {
            return docFlavor;
        }

        /* No attributes attached to this Doc - mainly useful for MultiDoc */
        public DocAttributeSet getAttributes() {
            return null;
        }

        /* Since the data is to be supplied as an InputStream delegate to
        * getStreamForBytes().
        */
        public Object getPrintData() throws IOException {
            return getStreamForBytes();
        }

        /* Not possible to return a GIF as text */
        public Reader getReaderForText()
            throws UnsupportedEncodingException, IOException {
           return null;
        }

        /* Return the print data as an InputStream.
        * Always return the same instance.
        */
        public InputStream getStreamForBytes() throws IOException {
            synchronized(this) {
                if (stream == null) {
                    stream = new FileInputStream(filename);
                }
                return stream;
            }
        }
    }

}
