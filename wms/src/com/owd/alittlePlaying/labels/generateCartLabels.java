package com.owd.alittlePlaying.labels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by danny on 11/14/2019.
 */
public class generateCartLabels {


    public static void main(String[] args){

        int start = 1;
        int stop = 30;
        int qty = 4;

        while(start<=stop){
            addToLabels(start+"",qty);
            start++;
        }

        System.out.println(labels.toString());
        File file = new File("labels.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(labels.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static StringBuilder labels = new StringBuilder();

    public static void addToLabels(String id, int qty){

        StringBuilder sb = new StringBuilder();

        while(qty>=1) {
            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^BY4,3,160^FT577,55^BCR,,Y,N");
            sb.append("^FD>://cart-");
            sb.append(id);
            sb.append("^FS");
            sb.append("^BY196,196^FT135,877^BXR,14,200,0,0,1,~");
            sb.append("^FH\\^FD//cart-");

            sb.append(id);
            sb.append("^FS");
            sb.append("^FT151,49^A0R,245,252^FH\\^FDcart-");

            sb.append(id);
            sb.append("^FS");
            sb.append("^PQ1,0,1,Y^XZ\r\n");
            qty--;

        }

        labels.append(sb.toString());




    }
}
