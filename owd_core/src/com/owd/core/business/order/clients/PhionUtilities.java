package com.owd.core.business.order.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 19, 2005
 * Time: 2:37:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhionUtilities extends KitItemUtilities {
private final static Logger log =  LogManager.getLogger();

    public Map getKitMap() {
        return kitMap;

    }


    private static PhionUtilities me;

    public synchronized static PhionUtilities getInstance() {
        if (me == null) {
            me = new PhionUtilities();
        }
        return me;
    }

    public PhionUtilities() {
        if (kitMap == null || kitMap.size() < 1) {
            kitMap = new TreeMap();

          /*  addKitDefinition("COMPPK-PW", "PHBKT", 1);
            addKitDefinition("COMPPK-PW", "ALKDTX", 1);
            addKitDefinition("COMPPK-PW", "ALKCL", 1);
            addKitDefinition("COMPPK-PW", "SLVG04", 1);
            addKitDefinition("COMPPK-PW", "ALKGR", 1);
            addKitDefinition("COMPPK-PW", "ALKBL", 1);
            addKitDefinition("COMPPK-PW", "ALKIND", 1);
            addKitDefinition("COMPPK-PW", "ALKVIO", 1);
            addKitDefinition("COMPPK-PW", "ALKRDLQ", 1);
            addKitDefinition("COMPPK-PW", "ALKORA", 1);
            addKitDefinition("COMPPK-PW", "ALKGLD", 1);
            addKitDefinition("COMPPK-PW", "PHP75", 1);
            addKitDefinition("COMPPK-PW", "BOTL", 1);
            addKitDefinition("COMPPK-PW", "ACAL", 1);
            addKitDefinition("COMPPK-PW", "INST100", 1);*/
            addKitDefinition("PHMIRPK-PW", "PHBKT", 1);
            addKitDefinition("PHMIRPK-PW", "ALKDTX", 1);
            addKitDefinition("PHMIRPK-PW", "ALKCL", 1);
            addKitDefinition("PHMIRPK-PW", "ALKGR", 1);
            addKitDefinition("PHMIRPK-PW", "ALKBL", 1);
            addKitDefinition("PHMIRPK-PW", "ALKIND", 1);
            addKitDefinition("PHMIRPK-PW", "ALKVIO", 1);
            addKitDefinition("PHMIRPK-PW", "ALKORA", 1);
            addKitDefinition("PHMIRPK-PW", "BOTL", 1);
            addKitDefinition("PHMIRPK-PW", "PHP75", 1);
            addKitDefinition("PHMIRPK-PW", "PHMB", 1);
            addKitDefinition("PHMIRPK-PW", "INST200", 1);
           /* addKitDefinition("ALKPK-PW", "PHBKT", 1);
            addKitDefinition("ALKPK-PW", "ALKGR", 1);
            addKitDefinition("ALKPK-PW", "ALKBL", 1);
            addKitDefinition("ALKPK-PW", "ALKIND", 1);
            addKitDefinition("ALKPK-PW", "ALKVIO", 1);
            addKitDefinition("ALKPK-PW", "ALKORA", 1);
            addKitDefinition("ALKPK-PW", "ALKGLD", 1);
            addKitDefinition("ALKPK-PW", "PHP75", 1);
            addKitDefinition("ALKPK-PW", "BOTL", 1);
            addKitDefinition("ALKPK-PW", "INST300", 1);*/
      /*      addKitDefinition("PHMAINPK-PW", "PHBKT", 1);
            addKitDefinition("PHMAINPK-PW", "ALKGR", 1);
            addKitDefinition("PHMAINPK-PW", "ALKBL", 1);
            addKitDefinition("PHMAINPK-PW", "ALKVIO", 1);
            addKitDefinition("PHMAINPK-PW", "PHP75", 1);
            addKitDefinition("PHMAINPK-PW", "INST400", 1);*/
          /*  addKitDefinition("BASPK-PW", "PHBKT", 1);
            addKitDefinition("BASPK-PW", "ALKGR", 1);
            addKitDefinition("BASPK-PW", "ALKBL", 1);
            addKitDefinition("BASPK-PW", "INST600", 1);*/
            addKitDefinition("DETOXPK", "PHBKT", 1);
            addKitDefinition("DETOXPK", "ALKDTX", 1);
            addKitDefinition("DETOXPK", "ALKCL", 1);
            addKitDefinition("DETOXPK", "INST500", 1);
            addKitDefinition("COLONPK", "ALKCL", 1);
            addKitDefinition("COLONPK", "ALKORA", 1);
            addKitDefinition("COLONPK", "ALKGLD", 1);
            addKitDefinition("10030", "PHBKT", 6);
            addKitDefinition("11002", "Alk180", 6);
            addKitDefinition("11003", "ALKGR", 6);
            addKitDefinition("12002", "ALKBL", 6);
            addKitDefinition("13002", "ALKCL", 6);
           /* addKitDefinition("COMPPK-CP", "PHBKT", 1);
            addKitDefinition("COMPPK-CP", "ALKDTX", 1);
            addKitDefinition("COMPPK-CP", "ALKCL", 1);
            addKitDefinition("COMPPK-CP", "SLVG04", 1);
            addKitDefinition("COMPPK-CP", "ALK180", 1);
            addKitDefinition("COMPPK-CP", "ALKBL", 1);
            addKitDefinition("COMPPK-CP", "ALKIND", 1);
            addKitDefinition("COMPPK-CP", "ALKVIO", 1);
            addKitDefinition("COMPPK-CP", "ALKRDLQ", 1);
            addKitDefinition("COMPPK-CP", "ALKORA", 1);
            addKitDefinition("COMPPK-CP", "ALKGLD", 1);
            addKitDefinition("COMPPK-CP", "PHP75", 1);
            addKitDefinition("COMPPK-CP", "BOTL", 1);
            addKitDefinition("COMPPK-CP", "ACAL", 1);
            addKitDefinition("COMPPK-CP", "INST100", 1);*/
            addKitDefinition("PHMIRPK-CP", "PHBKT", 1);
            addKitDefinition("PHMIRPK-CP", "ALKDTX", 1);
            addKitDefinition("PHMIRPK-CP", "ALKCL", 1);
            addKitDefinition("PHMIRPK-CP", "ALK180", 1);
            addKitDefinition("PHMIRPK-CP", "ALKBL", 1);
            addKitDefinition("PHMIRPK-CP", "ALKIND", 1);
            addKitDefinition("PHMIRPK-CP", "ALKVIO", 1);
            addKitDefinition("PHMIRPK-CP", "ALKORA", 1);
            addKitDefinition("PHMIRPK-CP", "BOTL", 1);
            addKitDefinition("PHMIRPK-CP", "PHP75", 1);
            addKitDefinition("PHMIRPK-CP", "PHMB", 1);
            addKitDefinition("PHMIRPK-CP", "INST200", 1);
          /*  addKitDefinition("ALKPK-CP", "PHBKT", 1);
            addKitDefinition("ALKPK-CP", "ALK180", 1);
            addKitDefinition("ALKPK-CP", "ALKBL", 1);
            addKitDefinition("ALKPK-CP", "ALKIND", 1);
            addKitDefinition("ALKPK-CP", "ALKVIO", 1);
            addKitDefinition("ALKPK-CP", "ALKORA", 1);
            addKitDefinition("ALKPK-CP", "ALKGLD", 1);
            addKitDefinition("ALKPK-CP", "PHP75", 1);
            addKitDefinition("ALKPK-CP", "BOTL", 1);
            addKitDefinition("ALKPK-CP", "INST300", 1);*/
        /*    addKitDefinition("PHMAINPK-CP", "PHBKT", 1);
            addKitDefinition("PHMAINPK-CP", "ALK180", 1);
            addKitDefinition("PHMAINPK-CP", "ALKBL", 1);
            addKitDefinition("PHMAINPK-CP", "ALKVIO", 1);
            addKitDefinition("PHMAINPK-CP", "PHP75", 1);
            addKitDefinition("PHMAINPK-CP", "INST400", 1);*/
         /*   addKitDefinition("BASPK-CP", "PHBKT", 1);
            addKitDefinition("BASPK-CP", "ALK180", 1);
            addKitDefinition("BASPK-CP", "ALKBL", 1);
            addKitDefinition("BASPK-CP", "INST600", 1);*/
            addKitDefinition("TRLPK1", "PHBKT", 3);
            addKitDefinition("TRLPK1", "ALK180", 3);
            addKitDefinition("TRLPK1", "ALKBL", 3);
            addKitDefinition("TRLPK1", "ALKCL", 3);
            addKitDefinition("TRLPK1", "PHP75 ", 3);
            addKitDefinition("TRLPK2", "PHBKT", 6);
            addKitDefinition("TRLPK2", "ALK180", 6);
            addKitDefinition("TRLPK2", "ALKBL", 6);
            addKitDefinition("TRLPK2", "ALKCL", 6);
            addKitDefinition("TRLPK2", "PHP75", 12);
            addKitDefinition("PHP75-3", "PHP75", 3);
            addKitDefinition("10002H", "PHP75", 6);

            addKitDefinition("TRL1905", "PHBKT", 6);
            addKitDefinition("TRL1905", "ALKGR", 6);
            addKitDefinition("TRL1905", "ALKBL", 6);
            addKitDefinition("TRL1905", "ALKCL", 6);
            addKitDefinition("TRL1905", "10002C", 1);
            addKitDefinition("TRL1905-CP", "PHBKT", 6);
            addKitDefinition("TRL1905-CP", "ALK180", 6);
            addKitDefinition("TRL1905-CP", "ALKBL", 6);
            addKitDefinition("TRL1905-CP", "ALKCL", 6);
            addKitDefinition("TRL1905-CP", "10002C", 1);
            addKitDefinition("TRL2905", "PHBKT", 6);
            addKitDefinition("TRL2905", "ALKGR", 6);
            addKitDefinition("TRL2905", "ALKBL", 6);
            addKitDefinition("TRL2905", "ALKCL", 6);
            addKitDefinition("TRL2905", "10002C", 1);
            addKitDefinition("TRL2905", "ALKRDLQ", 3);
            addKitDefinition("TRL2905", "ALKORA", 3);
            addKitDefinition("TRL2905", "ALKGLD", 3);
            addKitDefinition("TRL2905", "ALKIND", 3);
            addKitDefinition("TRL2905", "ALKVIO", 3);
            addKitDefinition("TRL2905", "ALKDTX", 3);
            addKitDefinition("TRL2905-CP", "PHBKT", 6);
            addKitDefinition("TRL2905-CP", "ALK180", 6);
            addKitDefinition("TRL2905-CP", "ALKBL", 6);
            addKitDefinition("TRL2905-CP", "ALKCL", 6);
            addKitDefinition("TRL2905-CP", "10002C", 1);
            addKitDefinition("TRL2905-CP", "ALKRDLQ", 3);
            addKitDefinition("TRL2905-CP", "ALKORA", 3);
            addKitDefinition("TRL2905-CP", "ALKGLD", 3);
            addKitDefinition("TRL2905-CP", "ALKIND", 3);
            addKitDefinition("TRL2905-CP", "ALKVIO", 3);
            addKitDefinition("TRL2905-CP", "ALKDTX", 3);
            addKitDefinition("PHBKT3", "PHBKT", 3);
            addKitDefinition("PHBKT3", "CATR", 1);


            addKitDefinition("WGHTLOSSPK", "PHBKT", 1);

            addKitDefinition("WGHTLOSSPK", "ALKGR", 1);

            addKitDefinition("WGHTLOSSPK", "ALKBL", 1);
            addKitDefinition("WGHTLOSSPK", "ALKGLD", 1);
            addKitDefinition("WGHTLOSSPK", "PHP75", 1);
            addKitDefinition("WGHTLOSSPK", "BOTL", 1);
            addKitDefinition("WGHTLOSSPK", "INST700", 1);

            addKitDefinition("WGHTLOSSPK-S", "PHBKT", 1);

            addKitDefinition("WGHTLOSSPK-S", "ALKGR", 1);

            addKitDefinition("WGHTLOSSPK-S", "ALKBL", 1);
            addKitDefinition("WGHTLOSSPK-S", "ALKGLD", 1);
            addKitDefinition("WGHTLOSSPK-S", "PHP75", 1);
            addKitDefinition("WGHTLOSSPK-S", "BOTL", 1);
            addKitDefinition("WGHTLOSSPK-S", "INST700", 1);


            addKitDefinition("ALK180-4", "ALK180", 4);
            addKitDefinition("ALKGR-4", "ALKGR", 4);

            addKitDefinition("ALKGRCAP-4", "ALK180", 4);

            addKitDefinition("BASPKSPEC-PW", "BASPK-PW", 1);
            addKitDefinition("BASPKSPEC-PW", "Botl", 1);

            addKitDefinition("BASPKSPEC-CP", "BASPK-CP", 1);
            addKitDefinition("BASPKSPEC-CP", "Botl", 1);


            addKitDefinition("TUCKPGM", "TUCK", 1);
            addKitDefinition("TUCKPGM", "PHP75", 1);
            addKitDefinition("TUCKPGM", "Botl", 1);
            addKitDefinition("TUCKPGM", "AA-EBK", 1);
            //removed per Frank 3/29/09
         //   addKitDefinition("TUMMYTK", "CL300V", 1);
        //    addKitDefinition("TUMMYTK", "GO240O", 1);
        //    addKitDefinition("TUMMYTK", "OR60", 1);
         //   addKitDefinition("TUMMYTK", "BOTL", 1);


            addKitDefinition("REFRSHR1","BL180",3);
addKitDefinition("REFRSHR1","BL270T",3);
addKitDefinition("REFRSHR1","BSTKIT",3);
addKitDefinition("REFRSHR1","CL240",3);
addKitDefinition("REFRSHR1","CL300V",3);
addKitDefinition("REFRSHR1","DE90",3);
addKitDefinition("REFRSHR1","GO240O",3);
addKitDefinition("REFRSHR1","GR180",3);
addKitDefinition("REFRSHR1","GR210T",3);
addKitDefinition("REFRSHR1","GR453T",3);
addKitDefinition("REFRSHR1","IN90",3);
addKitDefinition("REFRSHR1","OR60",3);
addKitDefinition("REFRSHR1","RE90",3);
addKitDefinition("REFRSHR1","SI4",3);
addKitDefinition("REFRSHR1","VI90",3);
addKitDefinition("REFRSHR2","BL180",2);
addKitDefinition("REFRSHR2","BL270T",2);
addKitDefinition("REFRSHR2","BSTKIT",2);
addKitDefinition("REFRSHR2","CL240",2);
addKitDefinition("REFRSHR2","CL300V",2);
addKitDefinition("REFRSHR2","DE90",2);
addKitDefinition("REFRSHR2","GO240O",2);
addKitDefinition("REFRSHR2","GR180",2);
addKitDefinition("REFRSHR2","GR210T",2);
addKitDefinition("REFRSHR2","GR453T",2);
addKitDefinition("REFRSHR2","IN90",2);
addKitDefinition("REFRSHR2","OR60",2);
addKitDefinition("REFRSHR2","RE90",2);
addKitDefinition("REFRSHR2","SI4",2);
addKitDefinition("REFRSHR2","VI90",2);
addKitDefinition("REFRSHR3","BL180",2);
addKitDefinition("REFRSHR3","BSTKIT",2);
addKitDefinition("REFRSHR3","CL240",2);
addKitDefinition("REFRSHR3","DE90",2);
addKitDefinition("REFRSHR3","GR180",2);
addKitDefinition("REFRSHR3","GR210T",2);
addKitDefinition("REFRSHR3","IN90",2);
addKitDefinition("REFRSHR3","RE90",2);
addKitDefinition("REFRSHR3","VI90",2);

            addKitDefinition("PROMO100", "PHBKT", 1);
            addKitDefinition("PROMO100", "ALKCL", 1);
            addKitDefinition("PROMO100", "ALKDTX", 1);
            addKitDefinition("PROMO100", "PHPAKIT", 1);

                 addKitDefinition("PROMO104", "GR453T", 3);
                   addKitDefinition("PROMO103", "GR210T", 3);
                   addKitDefinition("PROMO102", "GR180", 3);

            addKitDefinition("PROMO107", "BSTKIT",2);
            addKitDefinition("PROMO107", "DE90",1);
            addKitDefinition("PROMO107", "CL300V",1);
            addKitDefinition("PROMO107", "SI4",1);
            addKitDefinition("PROMO107", "GR210T",2);
            addKitDefinition("PROMO107", "BL270T",1);
            addKitDefinition("PROMO107", "IN90",1);
            addKitDefinition("PROMO107", "VI90",1);
            addKitDefinition("PROMO107", "RE90",1);
            addKitDefinition("PROMO107", "OR60",1);
            addKitDefinition("PROMO107", "GO240O",1);
            addKitDefinition("PROMO107", "PHP75",1);
            addKitDefinition("PROMO107", "BOTL",1);
            addKitDefinition("PROMO107", "ACAL",1);
            addKitDefinition("PROMO107", "PICOMP",1);

           
            addKitDefinition("PROMO108","BSTKIT", 2);
            addKitDefinition("PROMO108","DE90", 1);
            addKitDefinition("PROMO108","CL300V", 1);
            addKitDefinition("PROMO108","SI4", 1);
            addKitDefinition("PROMO108","GR180", 2);
            addKitDefinition("PROMO108","BL270T", 1);
            addKitDefinition("PROMO108","IN90", 1);
            addKitDefinition("PROMO108","VI90", 1);
            addKitDefinition("PROMO108","RE90", 1);
            addKitDefinition("PROMO108","OR60", 1);
            addKitDefinition("PROMO108","GO240O", 1);
            addKitDefinition("PROMO108","PHP75", 1);
            addKitDefinition("PROMO108","BOTL", 1);
            addKitDefinition("PROMO108","ACAL", 1);
            addKitDefinition("PROMO108","PICOMP", 1);

         

            
addKitDefinition("6BL180","BL180", 6);
addKitDefinition("6BL270T","BL270T", 6);
addKitDefinition("6BSTKIT","BSTKIT", 6);
addKitDefinition("6CL240","CL240", 6);
addKitDefinition("6CL300V","CL300V", 6);
addKitDefinition("6DE90","DE90", 6);
addKitDefinition("6GO240O","GO240O", 6);
addKitDefinition("6GR180","GR180", 6);
addKitDefinition("6GR210T","GR210T", 6);
addKitDefinition("6GR453T","GR453T", 6);
addKitDefinition("6IN90","IN90", 6);
addKitDefinition("6OR60","OR60", 6);
addKitDefinition("6RE90","RE90", 6);
addKitDefinition("6SI4","SI4", 6);
addKitDefinition("6VI90","VI90", 6);

          addKitDefinition("FATFLSHP","GR210T", 1);
          addKitDefinition("FATFLSHP","DE90", 1);
          addKitDefinition("FATFLSHP","IN90", 1);
          addKitDefinition("FATFLSHP","BSTKIT", 1);
          addKitDefinition("FATFLSHP","BOTL", 1);

          addKitDefinition("FATFLSHC","GR180", 1);
          addKitDefinition("FATFLSHC","DE90", 1);
          addKitDefinition("FATFLSHC","IN90", 1);
          addKitDefinition("FATFLSHC","BSTKIT", 1);
          addKitDefinition("FATFLSHC","BOTL", 1);

          addKitDefinition("PROMO113","GR210T",1);
          addKitDefinition("PROMO113","BL270T",1);
          addKitDefinition("PROMO113","BSTKIT",1);

          addKitDefinition("ENRGYDRNKP","GR210T",1);
          addKitDefinition("ENRGYDRNKP","BL270T",1);
          addKitDefinition("ENRGYDRNKP","BSTKIT",1);

          addKitDefinition("PROMO114","GR180",1);
          addKitDefinition("PROMO114","BL180",1);
          addKitDefinition("PROMO114","BSTKIT",1);

          addKitDefinition("ENRGYDRNKC","GR180",1);
          addKitDefinition("ENRGYDRNKC","BL270T",1);
          addKitDefinition("ENRGYDRNKC","BSTKIT",1);


          /*

alkgrcap-4 = (4) alk180
baspkspec-pw = (1) BASPK-PW, (1) botl
baspkspec-cp = (1) BASPK-CP, (1) botl


The 3 new SKUs - alkgrcap-4, baspkspec-pw and baspkspec-cp have all been added to the inventory system.

Summary
An order of 1 alkgrcap-4 would translate to 4 units of ALK180 being shipped
An order of 1 baspkspec-pw would translate to 1 BASPK-PW and 1 BOTL being shipped.
An order of 1 baspkspec-cp would translate to 1 BASPK-CP and 1 BOTL being shipped.


alk180-4 = (4) alk180
1 alk180-4 = four alk180

alkgr-4 = (4) alkgr
1 alkgr-4 = four alkgr


   TRL1905 =
   (6) PHBKT
   (6) ALKGR
   (6) ALKBL
   (6) ALKCL
   (1) 10002C



   TRL1905-CP =
   (6) PHBKT
   (6) ALK180
   (6) ALKBL
   (6) ALKCL
   (1) 10002C


   TRL2905 =
   (6) PHBKT
   (6) ALKGR
   (6) ALKBL
   (6) ALKCL
   (1) 10002C
   (3) ALKRDLQ
   (3) ALKORA
   (3) ALKGLD
   (3) ALKIND
   (3) ALKVIO
   (3) ALKDTX




   TRL2905-CP =
   (6) PHBKT
   (6) ALK180
   (6) ALKBL
   (6) ALKCL
   (1) 10002C
   (3) ALKRDLQ
   (3) ALKORA
   (3) ALKGLD
   (3) ALKIND
   (3) ALKVIO
   (3) ALKDTX




            */
/*

            9/12/05
            14002 (Alkalive D-Tox - Case of 6 Bottles) = (6) ALKDTX
15002 (Alkalive Indigo - Case of 6 Bottles) = (6) ALKIND
16002 (Alkalive Violet - Case of 6 Bottles) = (6) ALKVIO
17002 (Alkalive Red - Case of 6 Bottles) = (6) ALKRDLQ
18002 (Alkalive Orange - Case of 6 Bottles) = (6) ALKORA
19002 (Alkalive Gold - Case of 6 Bottles) = (6) ALKGLD
20002 (Silvagen - Case of 6 Bottles) = (6) SLVG04
*/

            addKitDefinition("14002", "ALKDTX", 6);
            addKitDefinition("15002", "ALKIND", 6);
            addKitDefinition("16002", "ALKVIO", 6);
            addKitDefinition("17002", "ALKRDLQ", 6);
            addKitDefinition("18002", "ALKORA", 6);
            addKitDefinition("19002", "ALKGLD", 6);
            addKitDefinition("20002", "SLVG04", 6);
      //adder per Frank 11-17-2006
            addKitDefinition("SLVG04-SP", "SLVG04",6);

            addKitDefinition("ALKINDSPEC", "ALKIND", 4);

            addKitDefinition("CLEANSWEEP", "ALKDTX", 1);
            addKitDefinition("CLEANSWEEP", "ALKVIO", 1);
            addKitDefinition("CLEANSWEEP", "ALKGR", 1);
            addKitDefinition("CLEANSWEEP", "PHPAKIT", 1);
            addKitDefinition("CLEANSWEEP", "Botl", 1);

              addKitDefinition("PROMO101", "BL180", 1);
            addKitDefinition("PROMO101", "RE90", 1);
            addKitDefinition("PROMO101", "BSTKIT", 1);
            addKitDefinition("PROMO101", "BOTL", 1);


              addKitDefinition("PROMO105", "GR210T", 1);
            addKitDefinition("PROMO105", "IN90", 2);
            addKitDefinition("PROMO105", "BSTKIT", 1);
            addKitDefinition("PROMO105", "SI4", 1);

              addKitDefinition("PROMO106", "GR180", 1);
            addKitDefinition("PROMO106", "IN90", 2);
            addKitDefinition("PROMO106", "BSTKIT", 1);
            addKitDefinition("PROMO106", "SI4", 1);

            addKitDefinition("PROMO109", "BSTKIT", 3);
            addKitDefinition("PROMO110", "BSTKIT", 6);
            addKitDefinition("PROMO111", "GR453T", 1);
            addKitDefinition("PROMO112", "GR180", 2);

        }

    }

    protected Map kitMap = null;


    public static void main(String[] args) {
        PhionUtilities pu = PhionUtilities.getInstance();

        log.debug(pu.getKitMap());
    }
}
