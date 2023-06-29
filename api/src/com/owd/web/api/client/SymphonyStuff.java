package com.owd.web.api.client;

import com.owd.core.Mailer;
import com.owd.core.business.order.LineItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by stewartbuskirk1 on 7/9/15.
 */
public class SymphonyStuff {
private final static Logger log =  LogManager.getLogger();


    static List<String> exoPromoList = Arrays.asList("BENJAMINMORGAN",
            "CASEYHOWELL",
            "JADAMLATHAM",
            "HOLLYKEITH",
            "AUSTINHOWARD",
            "AARONSKOGEN",
            "ARLENWORK",
            "THEODOREMANTON",
            "MEGWORDEN",
            "IGORUGOREC",
            "STEPHENWESTING",
            "JOELMEADOR",
            "KATHERINEACURTIS",
            "PATRICKKEPLER",
            "BRYANHOUSEL",
            "ANDREWROSS",
            "ROBERTMAYER",
            "BRIANMITCHELL",
            "MICHAELGREINER",
            "LINDAMEYER",
            "JOSEPHMEISSNER",
            "CASEYHOWELL",
            "GABRIELMAGANA",
            "DAVIDMEIER",
            "RICHARDCRILL",
            "DANIELCARLSON",
            "PAVELMARGOLIN",
            "JOHNTINDALL",
            "MOLLYRYANFISHER",
            "JEFFREYSMITH",
            "MICHAELPETERS",
            "JAMESDUFFY",
            "ERINRUPPERT",
            "PATRICKHOWK",
            "NICOLEBLAN",
            "TUSHARSAXENA",
            "VICTORIABROUHARD",
            "MARITESTERGREEN",
            "WESTALLDEANE",
            "HENRYSVENDBLAD",
            "LAURENJUPITER",
            "DANAMARCHESSAULT",
            "BONNIEWILSON",
            "TOBYHOPPER",
            "MATTHEWMARTIN",
            "DANAHIGHFILL",
            "KAYLAWESTBROOK",
            "SETHGOLDMAN",
            "ALLISTAIRPINSOF",
            "DKKITEME",
            "MATTHEWCRANE",
            "TANNISLICKLITER",
            "PETERASAURO",
            "PAC-FIT",
            "JENNIFERMARSCH",
            "HEATHERPASCHALL",
            "KIRKTSIRIGOTAKIS",
            "BRIANWILLIAMS",
            "GEORGEAUSTIN",
            "DESTRYROPER",
            "CHADARMSTRONG",
            "DKKITEME",
            "CYNTHIALEBERTHON",
            "GREGLOMBARDI",
            "NICKGLASS",
            "JEAN-SEBASTIENPELLETIER",
            "NICHOLASHAECHLER",
            "KYLEDONNELLY",
            "DAVIDFRIEDLANDER",
            "TIMOTHYCHEN",
            "RICHARDLILES",
            "MADISONFRIEL",
            "ZACH",
            "SARAARN",
            "JASONTRIPLETT",
            "ADAMHARITAN",
            "KATHLEENCOOPER",
            "MIKEWEYAND",
            "DENISEBRETON",
            "JAKESLAVISH",
            "REBECCACARTER",
            "NOLANWEHR",
            "SARAHPENDERGRAPH",
            "ROYROBINSON",
            "MICHAELPAPPALARDO",
            "JOHNTEETER",
            "SAMANTHALEITAO",
            "LUCINDAMCOHEN",
            "WARRENHILL",
            "AMANDAMORIN",
            "DANIELOBRIEN",
            "SARABARNETT",
            "CHARLESGALLAGHER",
            "CHELSEAFETCH",
            "RANDALLCREASEY",
            "ZACKSEGEL",
            "JENNIFERHERSHNER",
            "JENNIFERRICE",
            "FAVIOGUERRERO",
            "JOHNDOWNING",
            "CHRISTOPHERSZETO",
            "WANDEEHUMPHREYS",
            "TARAANDRUS",
            "MICHELLEBELL",
            "TYLERBRILES",
            "REYPEREZ",
            "ADAMHAGGERTY",
            "JORDANTASKER",
            "JAYALTON",
            "LORAWOLKE",
            "BRIANKRIESEL",
            "AARONRICHARDSON",
            "JOHNPATRICKGRIFFIN",
            "DENISEBARNES",
            "KYLIETRIOLO",
            "ERROLSILVA",
            "KYLIENUCKOLS",
            "JOHNDOBYNS",
            "JADELMENARD",
            "CARRIEAUSTIN",
            "RICHARDLOKITES",
            "PATRICKSMITH",
            "RISAWECHSLER",
            "LYNELLEMIYATA",
            "JOSHTYLER",
            "SARAHBOLLINGER",
            "STEPHANIESCOTT",
            "JOEGUTH",
            "ADAMTHOMPSON",
            "JEREMYGOLLEHON",
            "BRENTONCOOPER",
            "JULYEWHITENER",
            "ZOEBUSENHART",
            "ROBKOVACS",
            "XIAOFENGLI",
            "DAVIDWOODY",
            "JOHNPARKER",
            "AARUNITHAKUR",
            "JACQUELINEJANET",
            "DAVIDLAUGHLIN",
            "JAYPETRONIS",
            "RANDYRAMIREZ",
            "ALMAMCLEMORE",
            "MARKSWEARINGEN",
            "MEGANFERGUSON",
            "MELISSANEWBOUND",
            "NATHANKILCHRIST",
            "KOMRONKASNAVIA",
            "NATHANGALER",
            "BILLTRAMMEL@GMAIL.COM",
            "AYLONPESSO",
            "ROBERTLUNA",
            "ROBERTLEVN",
            "KALEMLEONARD",
            "RAJCHAWLA",
            "JIMANDERSON",
            "ANDREWJANKOWSKI",
            "NICKDEMING",
            "DARYLSCHEPART",
            "ALEXSAVARD",
            "GRANTINGLEBRET",
            "MARYBENNETTBRACALENTE",
            "ERICMCANLY",
            "KELSEYHACKEM",
            "ALLANMISNER",
            "MIKEEATON",
            "ALLISONCHALLIS",
            "CASEYNASH",
            "TAMZINB.SMITH",
            "ADRIANFRIDAY",
            "TOMNELSON",
            "PETERBRUNO",
            "ASHLEYGLINKA",
            "JACOBSINVESTMENTS,INC.ATTNJEREMYPOMERANTZW-302",
            "THOMASGREENHALGH",
            "WENYEH",
            "VANESSAARNOLD",
            "JESSECERRATO",
            "JORDANDARR",
            "COLINMCCARTHY",
            "BRIANAUSTIN",
            "KATHERINETOMOKOSPEAKS",
            "CHRISPUSINELLI",
            "JAMIEBALESTERI",
            "JOEDYKES",
            "TEDDYMCKEON",
            "KARINHIGHFIELD",
            "ROBERTJAKUBIK",
            "PETRASANKARI",
            "JENNIFERREDMON",
            "JASONGEMBICKI",
            "EMERALDCHURCH[SCC]",
            "WILLIAMCBENTON",
            "MARTYMARTINEZ",
            "RACHELVARELA",
            "JAMESBARBETT",
            "JOHNCAPPELLO",
            "JAMESCLARK",
            "AMANDASOLORZA",
            "JOHNSANDERSON",
            "JAMESTODD",
            "SEANTITLE",
            "JILLLEWIS",
            "AUSTINSTOLTZFUS",
            "FREDRICKHAUGEN",
            "MATTNICOLETTI",
            "DOMINIQUECHANELLECANCIO",
            "GABISOUZA",
            "STEPHANIEBITTLE",
            "COLETTEMARKS",
            "STACYSHAW",
            "MICHAELVODOPICH",
            "DANIELFRASCELLAJR",
            "SHELBYSMITH",
            "AMANDAMORIN",
            "RICKGARZA",
            "TIMMATTISON",
            "ALEXANDERRATLIFF",
            "MICAHWRIGHT",
            "JOSHUASTERNIN",
            "ERICLONGCHAMP",
            "CHADHARLAND",
            "A.ELAINEFREDRICKSON",
            "CRISTORRES",
            "LEIFALBERTSON",
            "RICHARDKANE",
            "SANDRAMELENDEZ",
            "CHRISSAUER",
            "MICHAELKENNEY",
            "TIMOTHYHOLST",
            "DEANNASARKAR",
            "GREGORYBAXTER",
            "BENJAMINGILLENWATER",
            "JEREMYCOBLE",
            "ANTHONYFLINT",
            "AMYBUTRYMOWICZ",
            "SCOTTOLSCHANSKY",
            "ROLANDLAZARTE",
            "ANTHONYYOUNG",
            "ZACHBYRNE",
            "KAITLINHOLLIDAY",
            "MICHAELLAMPE",
            "EFOLEY",
            "DOUGKNIPPLE",
            "STEVENLUIBRAND",
            "KEVINRIORDAN",
            "PAULBURT",
            "DEREKARNDT",
            "MONTROBERTS",
            "ERICKLEIN",
            "RILEY",
            "KYLESULLIVAN",
            "DANROWE",
            "CHARLENECARTER",
            "ANTONIAFTHENAKIS",
            "AGNIESZKABOURRET",
            "JAKESLAVISH",
            "CHRISVANMETTER",
            "KRISTENDUNN",
            "BENJAMINMILLINA",
            "DAVIDALLENIBSEN",
            "SUNNYDIAZ",
            "TODDFUNK",
            "JAMESEATON",
            "KEITHROWE",
            "LORINALEAKERN",
            "RAYLENESOLHEIM",
            "NOLANWEHR",
            "ANNAHBENNIE",
            "SCOTTHARDENBERGH",
            "PHILIPSANESKI",
            "MICHAELNAHOURAII",
            "STEVENCHEREKOS",
            "VIRGINIAMCCLURE",
            "KEITHWALTERS",
            "RICARDORENECKE",
            "LINDAMEYER",
            "JUANFERNANDEZ-BARQUIN",
            "STEPHANIERHODES",
            "KALINDOWNING",
            "JACQUIEHALLIGAN",
            "JAMESCLEMENT",
            "NATASHALYNWIER",
            "BRANDONCUMMINGS",
            "SETH",
            "CASEYBERGSCHNEIDER",
            "MRJUSTINBAUGHER",
            "SPYROSMANIATOPOULOS",
            "BRYANTPHAM",
            "ANDYFRAZER",
            "BETHTURNER",
            "EXO,INC:KAITLINHOLLIDAY",
            "BRAYDNKOPAS",
            "ERICLISSNER",
            "AARONSAOUD",
            "KYLELEWIS",
            "DYLANTONKIN",
            "WAYNELIU",
            "GRAYRILEY",
            "GABRIELLEMIRIPLEISCH",
            "NEILMACNAUGHTON",
            "BABARAFARREN",
            "ELIZABETHSAGER",
            "RYANVAHEY",
            "MICHAELJOHNSON",
            "CHARLESGALLAGHER",
            "SHEILANYHAGEN",
            "GLENDAHASSELLKRUGER",
            "SCHONECKSHOAF",
            "HEATHEROKUDA",
            "PERRYGREENLEE",
            "DAVIDWARD",
            "KYLE.RODERICK@GMAIL.COM",
            "STEPHANIERHODES",
            "ISADOREBUDNICK",
            "EFRÉNOROZCODELAGARZA",
            "SUSANWILLIAMS",
            "LESLIEMONTGOMERY",
            "SCOTTBARR",
            "KERRYENGKILTERRA",
            "UWESTAHLSCHMIDT",
            "JAMESRCROWDER",
            "KYLIETRIOLO",
            "EMILYHILL",
            "STEVEWISSER",
            "JARRETTJENSEN",
            "TONYPATERNITE",
            "KRISTENCONNER",
            "LAURENPRINGLE",
            "BRYANWRIGHT",
            "NATHANKRAUS",
            "JORDANMONKS",
            "LYNNEHARDEY",
            "ISHAANVASUDEVA",
            "GABRIELMUCH",
            "ZEKIKAVGACI",
            "JENNIFERGATTO",
            "STEVENCAI",
            "JAMESWCARTER,II",
            "GAILWESTPHALEN",
            "TREVORLPRICE",
            "NATEDEAN",
            "BRIANLAROCCA",
            "GAILLOVETT",
            "MELANIELITTON",
            "DAVIDKRYSL",
            "MATTHEWO'CONNOR",
            "KOICHIROODA",
            "KARENRSTAATS",
            "KEVINONEILL",
            "MICHAELSTUBBART",
            "CEDRICEVELEIGH",
            "JASONROSENCRANTZ",
            "RICHARDSIEGMEISTER",
            "MARKPEREZ",
            "JULIEDRECKMANN",
            "AMYWHEELER",
            "ABYMATHEW",
            "BILLTRAMMEL",
            "EMILYKENDALL",
            "BRIANFUNK",
            "HEIDICYR",
            "JENNIEMARSHIANO",
            "KIMBERLYMCCORMICK",
            "AMANDAMORIN",
            "EVANHEFLEY",
            "SIRIDKHALSA",
            "PAULJOHNSON",
            "RONWUDARCZYK",
            "NICHOLASWALKER",
            "JASONTUTTLE",
            "ALLENRSANDICO",
            "AUDREYWOMACK",
            "NATEBLANCHARD",
            "KALINDOWNING",
            "DIANABAYLESS",
            "STEPHENEMOND",
            "SAMMILKMAN",
            "AMBERLVACHON",
            "RICHARDSALOME",
            "RYANHUFSCHMID",
            "ROBELIA",
            "PATRICKMURPHY",
            "HEIDIGEERNAERT",
            "DAVIDRECOR",
            "PAULJOHNSON",
            "ANTHONYPREADER",
            "ARICCOADY",
            "AMIRHABOOSHEH",
            "REGINAJENSEN",
            "KEVINLEFFERS",
            "RANDYRAMIREZ",
            "CASEYHOWELL",
            "TANIALEWIS",
            "MATTHEWKRASNERMAN",
            "GAYLERUSSELL",
            "PAULJOHNSON",
            "BILLTRAMMEL@GMAIL.COM",
            "KARENVAUGHAN",
            "JAKEBOWLES",
            "MYRNAELMONT",
            "NATHANDUFFY",
            "REBECCAEVANS");

     static  List<String> FijiOnHoldStateValues = Arrays.asList("AK", "HI", "ALASKA", "HAWAII");

    public static  boolean isFijiHeldStateOrder(Order order) {


        String testState = order.getShippingAddress().getState().toUpperCase()+"";
        if(testState.contains(" ")) {
            testState = testState.split(" ")[0];
        }
        if ("fijiwater".equals(order.getGroupName()) && (order.getShippingInfo().carr_service.contains("USPS") || FijiOnHoldStateValues.contains(testState))) {
            return true;
        }
        return false;
    }

    public static void applySpecialRules(Order order) throws Exception {


        applyExoPromotion(order);

       if(order.group_name.equals("kendallandkylie") || order.group_name.equals("jbrand")  || order.group_name.equals("kravejerky") || order.group_name.equals("rockflowerpaper") )
       {
           order.setForceFedexTaxBillingToConsignee(1);
       }

        if(order.group_name.equals("fijiwater") )
        {
            if (isFijiHeldStateOrder(order)) {
                order.is_future_ship = 1;

                Vector vex = new Vector();
                vex.add("fops@symphonycommerce.com");
                vex.add("jordan.bass@fijiwater.com");
                vex.add("alexandria.vaughan@fijiwater.com");
                vex.add("Tanya.Bailey@fijiwater.com");

                Mailer.sendMail("OWD Notification of Held Order for Fiji (Symphony Shipment ID: " + order.order_refnum + ")", "\r\nThe indicated shipment was placed on hold at OWD due to an AK or HI shipping address or USPS ship method (possible military address).",
                        vex.toArray(),
                        "donotreply@owd.com");
            }

            if ((order.getQuantityForSKU("P206032") + order.getQuantityForSKU("P224574") + getDSWaterOriginalSkuQuantity(order)) >= 25) {
                order.getShippingInfo().setShipOptions("LTL", "Prepaid", "");
                order.isWorkOrder = true;
                order.is_future_ship = 1;
                order.addNote("Symphony LTL/Special Handling Order Placed On Hold\r\n" +
                        "Order held due to LTL ship method - hold for associated work order");


            }

            if(order.getShippingInfo().carr_service.equals("LTL")
                    && (order.getBillingAddress().company_name.startsWith("9")  || order.getBillingAddress().company_name.startsWith("5") )
                    && order.getBillingAddress().company_name.toUpperCase().contains("VARVATOS"))
            {
                 order.getShippingInfo().setShipOptions("FedEx Ground","Prepaid","");
                order.isWorkOrder = false;
                order.is_future_ship = 0;
            }else   if(order.getShippingInfo().carr_service.equals("LTL")
                    && order.getShippingContact().getName().toUpperCase().contains("AUBREY")
                    && order.getShippingContact().getName().toUpperCase().contains("GRAHAM")
                    && order.getShippingAddress().zip.toUpperCase().startsWith("91302"))
            {
                order.getShippingInfo().setShipOptions("FedEx Ground","Prepaid","");
                order.isWorkOrder = false;
                order.is_future_ship = 0;
            } else   if(order.getShippingInfo().carr_service.equals("LTL")
                    && order.getShippingContact().getName().toUpperCase().contains("LAURA")
                    && order.getShippingContact().getName().toUpperCase().contains("COURTNEY")
                    && order.getShippingAddress().zip.toUpperCase().startsWith("06776"))
            {
                order.getShippingInfo().setShipOptions("FedEx Ground","Prepaid","");
                order.isWorkOrder = false;
                order.is_future_ship = 0;
            }  else   if(!(order.getShippingInfo().carr_service.equals("LTL"))
                    && order.getShippingContact().getName().toUpperCase().contains("MIKE")
                    && order.getShippingContact().getName().toUpperCase().contains("VENNETTI")
                    && order.getShippingAddress().zip.toUpperCase().startsWith("60467"))
            {
                order.getShippingInfo().setShipOptions("LTL", "Prepaid", "");
                order.isWorkOrder = true;
                order.is_future_ship = 1;
                order.addNote("Symphony LTL/Special Handling Order Placed On Hold\r\n" +
                        "Order held due to LTL ship method - hold for associated work order");


            }


            if(order.withinDateRange("2016-01-24","2016-02-09")) {
                for (LineItem li : (Vector<LineItem>) order.skuList) {
                    if ("P395570".equals(li.client_part_no) && li.quantity_request > 1) {
                        li.quantity_request = 1;
                    }
                }
            }
        }


        if(order.group_name.equals("diamondcandles") && "wholesale".equals(order.getTagValue("ORDERTYPE")) && isFirstDiamondWholesaleOrder(order))
        {

            order.addInsertItemIfAvailable("P228183",1);
            order.setPackingInstructions("Place one unit of P228183 inside, on the bottom, of any one of the full cases (cases of 20) in the order"+"\r\n\r\n"+order.packInstructions);
        }


    }

    private static boolean isFirstDiamondWholesaleOrder(Order order) throws Exception {

        boolean isFirst = true;
        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select count(distinct(order_id)) from owd_order join owd_order_ship_info s on order_id=order_fkey " +
                    "where is_void=0  and " +
                    "ship_company_name=? and " +
                    "ship_address_one=? and group_name in ('diamondcandles','G128Sdiamondcandles')");
            ps.setString(1,order.getShippingAddress().company_name);
            ps.setString(2,order.getShippingAddress().address_one);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if(rs.getInt(1)>0)
                {
                    isFirst = false;
                }

            }
            rs.close();
            ps.close();
        }catch(Exception ex)
            {

            }



        return isFirst;
    }
    public static void applyExoPromotion(Order order) throws Exception {
        log.debug(exoPromoList);

        if ("exoprotein".equals(order.getGroupName())) {
            String test = order.getBillingContact().getName();

            test = test.toUpperCase();
            test = test.replaceAll(" ", "");
            if (exoPromoList.contains(test)) {
                order.addInsertItemIfAvailable("P228453", 1);
            }
        }
    }

    public static int getDSWaterOriginalSkuQuantity(Order order) {
        long cases = order.getQuantityForSKU("P206030");
        cases += order.getQuantityForSKU("P206031");
        cases += order.getQuantityForSKU("P206029");

        return (int) cases;
    }


    public static void main(String[] args) throws Exception {
        String test;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,9309480);
        test = order.getBillFirstName()+" "+order.getBillLastName() + order.getBillAddressOne() + order.getBillCity();

        test = test.toUpperCase();
        test = test.replaceAll(" ", "");
        if (exoPromoList.contains(test))    {
            log.debug("Match");
        }                               else
        {
            log.debug("No Match");
        }
    }

}
