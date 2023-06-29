package com.owd.alittlePlaying.StringThings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by danny on 10/24/2018.
 */
public class regex {


    public static void main(String[] args){

        List<String> l = new ArrayList<>();
        l.add("102 CHRISTOPHER ST APT 5D ");
        l.add("10605 NE 20TH ST ");
        l.add("1106 OLD COACH RD ");
        l.add("113 CASTLE RD ");
        l.add("114 E RAILROAD ST ");
        l.add("1194 NORFOLK DR NW ");
        l.add("120 BURLINGTON ST ");
        l.add("1200 POST OAK BLVD APT 310");
        l.add("1240 SPRING CREEK DR ");
        l.add("1243 SAMS WAY ");
        l.add("133 PONY BARN RD ");
        l.add("1430 MAY AVE SE ");
        l.add("15107 MCKNEW RD ");
        l.add("152 MARGARET AVE ");
        l.add("1528 DRAKES CREEK RD ");
        l.add("16 NICKLAUS AVE ");
        l.add("1760 N DECATUR BLVD APT 11");
        l.add("181 MCGILL AVE NW ");
        l.add("18406 STONECREEK DR ");
        l.add("19009 E 50TH ST ");
        l.add("2004 MCGREGOR AVE ");
        l.add("205 BOULDER RIDGE RD ");
        l.add("20621 EAGLEWOOD FOREST DR ");
        l.add("21155 N 56TH ST APT 2061");
        l.add("2127 OCEOLA ST ");
        l.add("217 N PINE ST ");
        l.add("2256 E SHERRI DR ");
        l.add("23 BIRCH HILL RD ");
        l.add("25 S LIVINGSTON AVE STE B");
        l.add("252 WOOD ST ");
        l.add("25662 BIRCHLEAF CT ");
        l.add("2700 N HAYDEN RD APR 2104");
        l.add("2745 YALE ST ");
        l.add("28050 SW 60TH AVE ");
        l.add("2825 ESTHER BLVD ");
        l.add("2932 FILBERT ST APT 4");
        l.add("303 TALBOT DR ");
        l.add("3075 S PROSPECTOR CIR ");
        l.add("3096 W EMERY FOREST LN ");
        l.add("316 GLEN AVE ");
        l.add("327 TREME ST APT 412");
        l.add("3352 CLERENDON RD ");
        l.add("33U ROSE RITA TER ");
        l.add("3608 CREEKSIDE DR ");
        l.add("3703 PEACHTREE RD NE L1");
        l.add("4 BUTTERCUP DR ");
        l.add("4060 SHENANDOAH AVE # 1F");
        l.add("4230 SHERWOOD OAKS DR ");
        l.add("4250 ANATOLIA DR ");
        l.add("430 OGDEN ST ");
        l.add("449 OXFORD RD ");
        l.add("4544 42ND ST APT 1C");
        l.add("4634 HENRY AVE ");
        l.add("48 SAGAMORE ST ");
        l.add("498 ANDREW AVE ");
        l.add("5008 SUNRISE HILLS DR ");
        l.add("52 ALLAGASH TRL UNIT A");
        l.add("525K E MARKET ST # 119 ");
        l.add("559 E 55TH ST ");
        l.add("5875 E GILA RIDGE RD ");
        l.add("609 LAKE CIR ");
        l.add("720 NE 69TH ST APT 9S");
        l.add("7419 CAPSTAN DR ");
        l.add("8060 N 15TH DR ");
        l.add("8060 N 15TH DR ");
        l.add("825 10TH ST NW APT 1061");
        l.add("8569 YEARLING DR ");
        l.add("865 BUTTERCUP RD ");
        l.add("9 OVERMAN PL ");
        l.add("9109 MIDWAY DR ");


        Pattern p = Pattern.compile("[Pp](\\.*?)\\s{0,2}[Oo]\\.*?\\s{0,2}[Bb][Oo][Xx]");
      for(String s:l) {
          Matcher m = p.matcher(s);
          if (m.find()) {

              System.out.println("found somethign: "+s);
          }

      }

    }
}
