/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Mar 9, 2010
 * Time: 3:30:07 PM
 * To change this template use File | Settings | File Templates.
 */

import java.awt.print.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2006
 * Time: 12:23:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class OWDPrinterJob extends PrinterJob {

    public static PrinterJob getPrinterJob() {

        PrinterJob pjx = java.awt.print.PrinterJob.getPrinterJob();
        OWDPrinterJob opj = new OWDPrinterJob();
        opj.setPj(pjx);

        return opj;

    }

    public static javax.print.PrintService[] lookupPrintServices() {
        return java.awt.print.PrinterJob.lookupPrintServices();
    }

    public static javax.print.StreamPrintServiceFactory[] lookupStreamPrintServices(java.lang.String string) {

        return java.awt.print.PrinterJob.lookupStreamPrintServices(string);
    }



    public javax.print.PrintService getPrintService() {
        return pj.getPrintService();
    }

    public void setPrintService(javax.print.PrintService printService) throws java.awt.print.PrinterException {
        pj.setPrintService(printService);
    }

    public boolean printDialog(javax.print.attribute.PrintRequestAttributeSet printRequestAttributeSet) throws java.awt.HeadlessException {

        return pj.printDialog(printRequestAttributeSet);
    }


    public java.awt.print.PageFormat pageDialog(javax.print.attribute.PrintRequestAttributeSet printRequestAttributeSet) throws java.awt.HeadlessException {

        return pj.pageDialog(printRequestAttributeSet);
    }


    public java.awt.print.PageFormat defaultPage() {
        return pj.defaultPage();
    }


    public void print(javax.print.attribute.PrintRequestAttributeSet printRequestAttributeSet) throws java.awt.print.PrinterException {
        pj.print(printRequestAttributeSet);
    }


    public PrinterJob getPj() {
        return pj;
    }

    public void setPj(PrinterJob pj) {
        this.pj = pj;
    }

    PrinterJob pj = null;


    public void setPrintable(Printable printable) {
        pj.setPrintable(printable);
    }

    public void setPrintable(Printable printable, PageFormat pageFormat) {
        pj.setPrintable(printable, pageFormat);
    }

    public void setPageable(Pageable pageable) throws NullPointerException {
        pj.setPageable(pageable);
    }

    public boolean printDialog() throws HeadlessException {
        return pj.printDialog();
    }

    public PageFormat pageDialog(PageFormat pageFormat) throws HeadlessException {
        return pj.pageDialog(pageFormat);
    }

    public PageFormat defaultPage(PageFormat pageFormat) {
        return pj.defaultPage(pageFormat);
    }

    public PageFormat validatePage(PageFormat pageFormat) {
        return pj.validatePage(pageFormat);
    }

    public void print() throws PrinterException {

        try {
            System.out.println("Printing OWD PrinterJob");
            pj.print();
            if(pj.isCancelled())
            {
                throw new Exception("Printing cancelled!");
            }

        } catch (PrinterException ex) {
            setPrintingException(ex);
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {

            setPrintingException(ex);
            ex.printStackTrace();
        } catch (Throwable ex) {

            setPrintingException(new Exception(ex.getMessage(),ex));
            ex.printStackTrace();
        }

    }

    public Exception getPrintingException() {
        return printingException;
    }

    public void setPrintingException(Exception printingException) {
        this.printingException = printingException;
    }

    Exception printingException = null;


    public void setCopies(int i) {
        pj.setCopies(i);
    }

    public int getCopies() {
        return pj.getCopies();
    }

    public String getUserName() {
        return pj.getUserName();
    }

    public void setJobName(String string) {
        pj.setJobName(string);
    }

    public String getJobName() {
        return pj.getJobName();
    }

    public void cancel() {
        pj.cancel();
    }

    public boolean isCancelled() {
        return pj.isCancelled();  //To change body of implemented methods use File | Settings | File Templates.
    }
}

