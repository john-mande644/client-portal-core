package com.owd.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;


public class PRNReader {
private final static Logger log =  LogManager.getLogger();

    Vector columns = new Vector();

    public int rowCount = 0;

    public int columnCount = 0;

    Vector data = new Vector();


    public PRNReader(BufferedReader reader, boolean firstLine, int[] fieldLengths) throws IOException {

        String line = "";

        int totalLength = 0;

        for (int i = 0; i < fieldLengths.length; i++) {

            totalLength += fieldLengths[i];

        }

        while ((line = reader.readLine()) != null) {

            int chars = 0;

            if (line.length() == totalLength) {

                if (firstLine) {

                    //get column names

                    while (line.length() != chars) {

                        for (int i = 0; i < fieldLengths.length; i++) {

                            columns.addElement(line.substring(0, fieldLengths[i]).trim());

                            line = line.substring(fieldLengths[i]);

                            columnCount++;

                        }

                    }


                    firstLine = false;

                } else {

                    Vector row = new Vector();

                    //parse line

                    for (int i = 0; i < fieldLengths.length; i++) {

                        row.addElement(line.substring(0, fieldLengths[i]));

                        line = line.substring(fieldLengths[i]);

                    }


                    data.addElement(row);

                    rowCount++;

                }

            }


        }

    }


    public float getFloatValue(int column, int row, float valDefault) {

        float val = valDefault;

        try {

            String theVal = ((String) ((Vector) data.elementAt(row)).elementAt(column)).trim();

            theVal = theVal.replace('$', ' ').trim();

            val = new Float(theVal).floatValue();

        } catch (Exception ex) {//////////log.debug("illegal access to CSVReader at row:"+row+" col:"+column);

            return valDefault;

        }


        return val;

    }


    public int getIntValue(int column, int row, int valDefault) {

        int val = valDefault;

        try {

            val = new Integer(((String) ((Vector) data.elementAt(row)).elementAt(column)).trim()).intValue();

        } catch (Exception ex) {//////////log.debug("illegal access to CSVReader at row:"+row+" col:"+column);

            return valDefault;

        }


        return val;

    }


    public String getStrValue(int column, int row, String valDefault) {

        String val = null;

        try {

            val = ((String) ((Vector) data.elementAt(row)).elementAt(column));

        } catch (Exception ex) {

            //////////log.debug("illegal access to CSVReader at row:"+row+" col:"+column);

            return valDefault;

        }

        if (val == null)

            return valDefault;


        return val.trim().replace('"', ' ');

    }


    public String getColumn(int index) {

        String val = null;

        try {

            val = ((String) columns.elementAt(index));

        } catch (Exception ex) {

            return "";

        }

        if (val == null)

            return "";

        return val.trim();

    }


    public int getRowCount() {

        return data.size();

    }


    public int getRowSize(int rowIndex) throws Exception {

        return ((Vector) data.elementAt(rowIndex)).size();

    }


    public String getRawRow(int rowIndex) {

        StringBuffer sb = new StringBuffer();


        Vector rowdata = ((Vector) data.elementAt(rowIndex));

        if (rowdata != null) {

            sb.append("row " + rowIndex + ":" + OWDUtilities.vectorToString(rowdata));

        }


        return sb.toString();

    }

}
