package com.owd.web.internal.zplutils;

import javax.mail.internet.MimeUtility;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.DeflaterOutputStream;

/**
 * Utilities to create a zpl file
 */
public class ZPLLabelCreationUtil {
    /**
     * creates a label in the ZPL format from the given parameters
     * @param poNum PO number
     * @param sscc serialized shipping container code
     * @param ctnQty carton quantity
     * @param ctn carton number and total number of cartons, formatted like this: 1 of 2
     * @param shipTo item recipient
     * @param address1 address line 1 (123 first st.)
     * @param address2 address line 2 (eg apt. 233)
     * @param address3 address line 3 (city, state, postal code)
     * @param countryOfOrigin country of origin
     * @param itemDesc item description
     * @param itemNum item number
     * @param partialText "Partial carton" if the carton is partial, blank otherwise
     * @param vendorId "id of vendor
     * @param vendorName "name of vendor
     * @return
     */
    public static String getZPLLabel(String poNum, String sscc, String ctnQty, String ctn, String shipTo, String address1,
                                     String address2, String address3, String countryOfOrigin, String itemDesc, String itemNum, String partialText, String vendorName, String vendorId) {

        //format the itemNum
        itemNum = insertIntoString(itemNum, ">5", 1);
        itemNum = insertIntoString(itemNum, ">6", 9);
        itemNum = insertIntoString(itemNum, ">5", 13);

        //trim oversized description
        if (itemDesc.length() > 30) {
            itemDesc = itemDesc.substring(0 ,29);
        }

        String template = getTemplateString();
        template = template.replace(":carton_qty_img:", textToZ64CompressedImage(ctnQty));
        template = template.replace(":ship_to_img:", textToZ64CompressedImage(shipTo));
        template = template.replace(":address1_img:", textToZ64CompressedImage(address1));
        template = template.replace(":address2_img:", textToZ64CompressedImage(address2));
        template = template.replace(":address3_img:", textToZ64CompressedImage(address3));
        template = template.replace(":po_number_img:", textToZ64CompressedImage(poNum));
        template = template.replace(":country_of_origin_img:", textToZ64CompressedImage(countryOfOrigin));
        template = template.replace(":vendor_id_img:", textToZ64CompressedImage(vendorId));
        template = template.replace(":vendor_name_img:", textToZ64CompressedImage(vendorName));
        template = template.replace(":sscc_img:", textToZ64CompressedImage(sscc, 10, -30));
        template = template.replace(":carton_num_img:", textToZ64CompressedImage(ctn));
        template = template.replace(":item_desc_img:", textToZ64CompressedImage(itemDesc));
        template = template.replace(":partial_img:", textToZ64CompressedImage(partialText));


        // non-image data
        template = template.replace(":item_num:", itemNum);
        template = template.replace(":sscc:", sscc);
        template = template.replace(":carton_qty:", ctnQty);

        return template;
    }

    /**
     * Gets the template zpl file the result is based on
     * @return
     */
    private static String getTemplateString() {
        return ZPLTemplate.template;
    }


    /**
     * Inserts a substring at the specified index
     * @param str the string to insert into
     * @param ins the string insert
     * @param position the index
     * @return
     */
    static String insertIntoString(String str, String ins, int position) {
        return str.substring(0, position) + ins + str.substring(position);
    }

    /**
     * Converts text to a Z64 format compressed image
     * @param input text to display
     * @return
     */
    public static String textToZ64CompressedImage(String input){
        return textToZ64CompressedImage(input, 0, 0);
    }

    /**
     * Converts text to a Z64 format compressed image
     * @param input text to display
     * @param xOffset x offset
     * @param yOffset y offset
     * @return
     */
    public static String textToZ64CompressedImage(String input, int xOffset, int yOffset){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2d = img.createGraphics();
        Font font = new Font(g2d.getFont().getName(), Font.BOLD, 22);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int height = fm.stringWidth(input) + 30;
        int width = fm.getHeight() + 60;

        img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        g2d = img.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        // our label is displayed horizontally, so rotate the image
        g2d.rotate(Math.PI/2);
        g2d.drawString(input, 30 + yOffset, -20 -xOffset);
        g2d.dispose();

        // LZ77 compression
        ByteArrayOutputStream compressedImage = new ByteArrayOutputStream();
        DataBufferByte dataBufferByte = (DataBufferByte)img.getRaster().getDataBuffer();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(compressedImage);
        try
        {
            deflaterOutputStream.write(dataBufferByte.getData(), 0, dataBufferByte.getData().length);
            deflaterOutputStream.finish();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return "I/O error";
        }

        // without compression
//          byte[] encodedBytesImage = Base64.getMimeEncoder().encode(dataBufferByte.getData());
        byte[] encodedBytesImage = new byte[0];
        try {
            encodedBytesImage = encodeAsBase64(compressedImage.toByteArray());
        } catch (Exception e) {
            System.out.println(e);
        }
        String crcString = getCRCHexString(encodedBytesImage);
        String bytesPerRow = String.valueOf(dataBufferByte.getData().length / height);
        int binaryByteCount = (width * height) / 8;
        String encodedAscii = "^GFA," + binaryByteCount + "," + binaryByteCount + "," + bytesPerRow + ",:Z64:" + new String(encodedBytesImage, StandardCharsets.US_ASCII) + ":" + crcString;

        try
        {
            compressedImage.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        return encodedAscii;
    }

    /**
     * encodes a byte array as base64
     * @param b
     * @return
     * @throws Exception
     */
    public static byte[] encodeAsBase64(byte[] b) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream b64os = MimeUtility.encode(baos, "base64");
        b64os.write(b);
        b64os.close();
        return baos.toByteArray();
    }

    /**
     * Converts a byte array into a hexadecimal string
     * @param bytes
     * @return
     */
    private static String getCRCHexString(byte[] bytes)
    {
        int crc = 0x0000;           // initial value
        int polynomial = 0x1021;    // 0001 0000 0010 0001  (0, 5, 12)
        for (byte b : bytes)
        {
            for (int i = 0; i < 8; i++)
            {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;

                if (c15 ^ bit)
                {
                    crc ^= polynomial;
                }
            }
        }

        crc &= 0xffff;
        return Integer.toHexString(crc);
    }

}
