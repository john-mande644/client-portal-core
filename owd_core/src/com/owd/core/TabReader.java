package com.owd.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;


public class TabReader extends DelimitedReader {
private final static Logger log =  LogManager.getLogger();


    public TabReader(BufferedReader reader, boolean firstLine) throws IOException {

        super('\t', reader, firstLine);

    }


    static public void main(String[] args) {

        //////log.debug("139="+OWDUtilities.encryptData("139"));

        ////////log.debug("RbiPhHuODWEfEJDNUu6ZcAI=="+OWDUtilities.decryptData("RbiPhHuODWEfEJDNUu6ZcAI="));

        /*	////////log.debug("55="+OWDUtilities.decryptData("3IJSA1QCbYFkXxb9sJA0tQM="));



            float price = (float)9.00;

            float tax = (float)0.74;

            ////////log.debug("pct="+tax/price);

            ////////log.debug("roundpct="+OWDUtilities.roundFloat(tax/price));

            ////////log.debug("round4pct="+OWDUtilities.roundFloat(tax/price,4));



            String desc = "First Aid for the <br> soul";

            if(desc.toUpperCase().indexOf("<BR>") > 0)

            {

                String desc2 = (desc+" ").substring(0,desc.toUpperCase().indexOf("<BR>"));

                desc = desc2+(desc+" ").substring(desc.toUpperCase().indexOf("<BR>")+4);

            }

            ////////log.debug(desc+"\n\n\n\n");



            StringBuffer sb1 = new StringBuffer();



            for (int t=20;t<127;t++)

            {

                sb1.append(new Character((char)t));

                ////////log.debug(sb1.toString());

            }







            try{

            String tester = "quoted internal 2-qt\ttest2\t\"John the Beloved-I \"\"Initiations of the Sacred Heart\"\"\"\t\"3\"\ttest4\n"

                        +"quoted internal 1-qt\ttest2\t\"te\"st\t3\"\ttest4\n"

                        +"no-qt\ttest2\ttest3\ttest4\n";



            BufferedReader reader = new BufferedReader(new StringReader(tester));



            TabReader tr = new TabReader(reader,false);

            ////////log.debug("TabReader Test");

            ////////log.debug("rows = "+tr.getRowCount());

            for(int i = 0;i<tr.getRowCount();i++)

            {

                ////////log.debug("row "+i+": "+tr.getRowSize(i)+" cols:");

                for(int j=0;j<tr.getRowSize(i);j++)

                {

                    ////////log.debug("   col "+j+" = ::"+tr.getStrValue(j,i,"bad"));

                }



            }





            tester = "quoted internal 2-qt,test2,\"te\"\"st,3\",test4\n"

                    +"quoted internal 1-qt,test2,\"te\"st3\",test4\n"

                    +"\"no-qt\",\"test2\",\'test3\',\"test4\"";



            reader = new BufferedReader(new StringReader(tester));



            CSVReader cr = new CSVReader(reader,false);

            ////////log.debug("CSVReader Test");

            ////////log.debug("rows = "+cr.getRowCount());

            for(int i = 0;i<cr.getRowCount();i++)

            {

                ////////log.debug("row "+i+": "+cr.getRowSize(i)+" cols:");

                for(int j=0;j<cr.getRowSize(i);j++)

                {

                    ////////log.debug("   col "+j+" = ::"+cr.getStrValue(j,i,"bad"));

                }



            }





            }catch(Exception ex)

            {

                ex.printStackTrace();

            }

        */

    }

}
