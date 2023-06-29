package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Vector;

public class DelimitedReader {
private final static Logger log =  LogManager.getLogger();

    Vector columns = new Vector();
    public int rowCount = 0;
    public int columnCount = 0;
    Vector data = new Vector();

    private char delimiter = 0;

    public DelimitedReader() {
    }

    public DelimitedReader(char colSeparator, BufferedReader reader, boolean firstLine) throws IOException {
        delimiter = colSeparator;

        StreamTokenizer breaker = new StreamTokenizer(reader);
        breaker.resetSyntax();

        if (delimiter != '\t')
            breaker.wordChars('\t', '\t');

        if (delimiter != ',')
            breaker.wordChars(',', ',');

        breaker.wordChars(32, 254);


        breaker.ordinaryChar(delimiter);

        //if(delimiter == ',')
        breaker.ordinaryChar('"');

        //breaker.eolIsSignificant(true);



        int c = 0;
        int pushed = 0;
        boolean resetSeparator = true;
        Vector record = new Vector();
        StringBuffer quoteStr = new StringBuffer();
        boolean skipNext = false;

        breaker.eolIsSignificant(true);

        out:
       while (true) {
           if (skipNext) {
               skipNext = false;
           } else {
               c = breaker.nextToken();

           }
           ////////////log.debug(breaker.sval);

           if (breaker.ttype == 13) breaker.ttype = StreamTokenizer.TT_EOL;
           if (breaker.ttype == 10) breaker.ttype = StreamTokenizer.TT_EOL;

           if (breaker.ttype == delimiter) {

               if (pushed == 1) {

                   quoteStr.append(delimiter);

               } else {
                   //we have an ordinary delimiter
                   if (!resetSeparator) {
                       //if the last token was a delimiter
                       record.addElement("");

                   }
               }
               resetSeparator = false;
           } else {
               switch (breaker.ttype) {
                   case StreamTokenizer.TT_EOL:
//////////log.debug("got line");
                       resetSeparator = true;
                       if (pushed == 1) {

                           quoteStr.append("\r\n");

                       } else {

                           if (!firstLine) {
                               if (record.size() > 0) {
////////////log.debug("adding record");
                                   data.addElement(record);
                                   rowCount++;
                               }
                               record = new Vector();
                           } else {
////////////log.debug("resetting first line");
                               firstLine = false;
                               record = new Vector();
                           }
                       }
                       break;
                   case StreamTokenizer.TT_EOF:
//////////log.debug("got eof");
                       resetSeparator = true;
                       if (pushed == 1) {

                           quoteStr.append("\r\n");

                       } else {

                           if (!firstLine) {
                               if (record.size() > 0) {
                                   data.addElement(record);
                                   rowCount++;
                               }
                               record = new Vector();
                           } else {
                               firstLine = false;
                               record = new Vector();
                           }
                       }
                       break out;
                   case StreamTokenizer.TT_NUMBER:
                       resetSeparator = true;
                       break;
                   case StreamTokenizer.TT_WORD:

                       resetSeparator = true;
                       if (pushed == 1) {

                           quoteStr.append(breaker.sval);

                       } else {
                           if (firstLine) {
                               columns.addElement(breaker.sval);
                               columnCount++;
                           } else {
//////////log.debug("got word::"+breaker.sval);
                               record.addElement(breaker.sval);
                           }
                       }
                       break;
                   case '"':

                       //if (delimiter != '\t')
                       //{

                       resetSeparator = true;
                       if (pushed == 0) {
//////////log.debug("::::::::::::::::::::::::::::::::::::::::::::::start push");


                           if (null != breaker.sval) {
                               //	quoteStr.append(breaker.sval);
                           }
                           pushed = 1;


                       } else {

//////////log.debug("::::::::::::::::::::::::::::::::::::::::::::::end push");
                           c = breaker.nextToken();

                           if (c == '"') {
                               quoteStr.append("\'\'");
                               //skip double quotes
                               //check

                               /*
                                           c = breaker.nextToken();
                                           if(c!=delimiter)
                                           {

                                               skipNext = true;
                                               //////////log.debug("::"+c);
                                               if(c==13 || c==10 || c==-1)
                                               {

                                                   if(firstLine)
                                                   {
                                                       columns.addElement(quoteStr.toString());
                                                       columnCount++;
                                                   }else{
                                                       record.addElement(quoteStr.toString());
                                                   }

                                                   quoteStr = new StringBuffer();
                                                   pushed = 0;
                                               }

                                           }else
                                           {
                                               skipNext = true;
                                               if(firstLine)
                                               {
                                                   columns.addElement(quoteStr.toString());
                                                   columnCount++;
                                               }else{
                                                   record.addElement(quoteStr.toString());
                                               }

                                               quoteStr = new StringBuffer();
                                               pushed = 0;
                                           }
                               */
                               //endcheck
                           } else {
                               if (c != delimiter) {

                                   skipNext = true;
                                   //////////log.debug("::"+c);
                                   if (c == 13 || c == 10 || c == -1) {
                                       if (firstLine) {
                                           columns.addElement(quoteStr.toString());
                                           columnCount++;
                                       } else {
                                           record.addElement(quoteStr.toString());
                                       }

                                       quoteStr = new StringBuffer();
                                       pushed = 0;
                                   }
                               } else {
                                   skipNext = true;
                                   if (firstLine) {
                                       columns.addElement(quoteStr.toString());
                                       columnCount++;
                                   } else {
                                       record.addElement(quoteStr.toString());
                                   }

                                   quoteStr = new StringBuffer();
                                   pushed = 0;
                               }
                           }

                       }
                       //}

                       break;
                   default:
//////////log.debug("got bad char::"+c+"::"+breaker.ttype);
                       resetSeparator = true;
                       break;
               }
           }
       }

    }

    public float getFloatValue(int column, int row, float valDefault) {
        float val = valDefault;
        try {
            String theVal = ((String) ((Vector) data.elementAt(row)).elementAt(column)).trim();
            theVal = theVal.replace('$', ' ').trim();
            theVal = theVal.replaceAll(" ","");
            val = new Float(theVal).floatValue();
        } catch (Exception ex) {//////////log.debug("illegal access to CSVReader at row:"+row+" col:"+column);
            return valDefault;
        }

        return val;
    }

    public int getIntValue(int column, int row, int valDefault) {
        int val = valDefault;
        try {
            String theVal = ((String) ((Vector) data.elementAt(row)).elementAt(column)).trim();
             theVal = theVal.replaceAll(" ","");
            val = new Integer(theVal).intValue();
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

        val = val.replace('"', ' ');
        val = val.trim();
        if (val.equalsIgnoreCase("null"))
            val = "";
        return val;
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
            //sb.append("row "+rowIndex+":"+OWDUtilities.vectorToString(rowdata));
            sb.append("row " + rowIndex + ":" + vectorToString(rowdata));

        }

        return sb.toString();
    }

    private String vectorToString(Vector vec) {


        if (vec == null)
            return "";

        StringBuffer sb = new StringBuffer();

        if (vec.size() > 0)
            sb.append("\n::" + vec.elementAt(0).toString());

        for (int i = 1; i < vec.size(); i++) {
            sb.append("\n::" + vec.elementAt(i).toString());
        }

        return sb.toString();
    }

}
