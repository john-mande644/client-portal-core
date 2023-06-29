package com.owd.dc.warehouse.vendorCompliance.labelsMaker.printingUtilities;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by danny on 10/23/2016.
 */
public class printToZebraNetworkPrinter {



    public static void sendToPrinter(String label, String printer, int qty) throws IOException{

        int i = 0;
        while (i<qty){
            sendToPrinter(label,printer);
            i++;

        }



    }


    private static void sendToPrinter(String label, String printer) throws IOException {

        Socket clientSocket = new Socket(printer, 9100);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(label);
        clientSocket.close();
    }

}
