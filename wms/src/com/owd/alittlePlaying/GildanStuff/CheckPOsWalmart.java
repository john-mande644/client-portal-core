package com.owd.alittlePlaying.GildanStuff;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 11/28/2016.
 */
public class CheckPOsWalmart {

    private static List<String> missingPO = new ArrayList<String>();
    private static List<String> canceledPO = new ArrayList<String>();

    public static void main(String[] args){
        List<String> l = new ArrayList<String>();
        l.add("4576970850776");
        l.add("4576970851052");
        l.add("2576970857511");
        l.add("1576971330329");
        l.add("1577011638919");
        l.add("2577062134397");
        l.add("1577082833903");
        l.add("3577174522371");
        l.add("4577174542018");
        l.add("2577174514198");
        l.add("3577174468136");
        l.add("3577174511721");
        l.add("3577174568078");
        l.add("2577174495894");
        l.add("2577164300474");
        l.add("4577174635824");
        l.add("4577174594302");
        l.add("4577174525530");
        l.add("3577174548874");
        l.add("1577175067723");
        l.add("4577174593068");
        l.add("4577174491366");
        l.add("1577175108569");
        l.add("4577174625305");
        l.add("3577174512783");
        l.add("1577175064841");
        l.add("4577174614624");
        l.add("3577174567936");
        l.add("4577174618088");
        l.add("1577174989551");
        l.add("2577174645385");
        l.add("1577175013526");
        l.add("3577174648030");
        l.add("2577174604092");
        l.add("1577175091170");
        l.add("4577174532116");
        l.add("2577174519527");
        l.add("1577174984227");
        l.add("4577174566555");
        l.add("4577174531598");
        l.add("4577174543971");
        l.add("4577174572229");
        l.add("2577174635054");
        l.add("1577175049820");
        l.add("2577174535285");
        l.add("4577174635800");
        l.add("4577174603292");
        l.add("1577174973505");
        l.add("2577174618137");
        l.add("2577174509307");
        l.add("3577174523721");
        l.add("1577175032177");
        l.add("4577174543854");
        l.add("1577175063066");
        l.add("1577175032509");
        l.add("3577174513341");
        l.add("1577175045491");
        l.add("4577174568500");
        l.add("2577174580262");
        l.add("2577174605887");
        l.add("1577175109599");
        l.add("3577174607737");
        l.add("4577174611228");
        l.add("3577174552404");
        l.add("3577174645751");
        l.add("2577174588620");
        l.add("1577174959654");
        l.add("3577174607077");
        l.add("1577174977817");
        l.add("4577174588717");
        l.add("2577174617067");
        l.add("1577174997869");
        l.add("4577174604400");
        l.add("3577174585359");
        l.add("2577174541001");
        l.add("2577174508249");
        l.add("4577174564941");
        l.add("4577174517520");
        l.add("2577174632344");
        l.add("3577174634530");
        l.add("1577174981678");
        l.add("3577174695335");
        l.add("1577175035747");
        l.add("4577174568859");
        l.add("3577174581161");
        l.add("1577175063212");
        l.add("4577174478343");
        l.add("1577175028836");
        l.add("3577174549606");
        l.add("2577174622896");
        l.add("3577174518676");
        l.add("4577174525418");
        l.add("1577175072685");
        l.add("4577174590290");
        l.add("3577174524624");
        l.add("2577174533190");
        l.add("1577175051566");
        l.add("2577174487766");
        l.add("4577174480973");
        l.add("3577174689208");
        l.add("1577174991252");
        l.add("4577174521555");
        l.add("2577174552737");
        l.add("1577175053938");
        l.add("2577174545928");
        l.add("4577174578592");
        l.add("4577174622433");
        l.add("4577174600420");
        l.add("4577174593686");
        l.add("1577174992485");
        l.add("4577174509195");
        l.add("4577174522841");
        l.add("2577174595473");
        l.add("2577174540557");
        l.add("3577174565400");
        l.add("2577174617270");
        l.add("2577174540962");
        l.add("1577174985273");
        l.add("2577174591628");
        l.add("4577174626400");
        l.add("3577174576613");
        l.add("3577174592594");
        l.add("2577174587029");
        l.add("3577174641576");
        l.add("1577175109601");
        l.add("3577174548097");
        l.add("2577174632651");
        l.add("2577174507300");
        l.add("2577174586644");
        l.add("3577174573083");
        l.add("3577174555158");
        l.add("3577174557279");
        l.add("1577175029340");
        l.add("2577174568679");
        l.add("2577174618293");
        l.add("2577174631598");
        l.add("4577174537279");
        l.add("2577174571535");
        l.add("3577174555867");
        l.add("3577174641416");
        l.add("3577174587008");
        l.add("2577174534372");
        l.add("3577174559377");
        l.add("4577174544959");
        l.add("2577174544117");
        l.add("3577174551939");
        l.add("2577174620460");
        l.add("3577174647504");
        l.add("1577175038821");
        l.add("4577174573105");
        l.add("2577174544949");
        l.add("4577174507533");
        l.add("2577174519943");
        l.add("1577175031052");
        l.add("1577174994616");
        l.add("3577174515314");
        l.add("2577174502311");
        l.add("2577174528979");
        l.add("3577174565016");
        l.add("3577174654940");
        l.add("2577174503644");
        l.add("3577174510845");
        l.add("4577174482859");
        l.add("3577174588845");
        l.add("2577174511815");
        l.add("1577175117599");
        l.add("3577174666708");
        l.add("4577174520724");
        l.add("4577174523125");
        l.add("2577174578850");
        l.add("1577174972103");
        l.add("3577174640568");
        l.add("1577175105126");
        l.add("4577174542019");
        l.add("3577174518710");
        l.add("2577174552430");
        l.add("4577174523815");
        l.add("4577174634695");
        l.add("1577174977051");
        l.add("3577174663788");
        l.add("1577175015067");
        l.add("2577174537611");
        l.add("1577175110349");
        l.add("4577174553724");
        l.add("2577174619550");
        l.add("3577174523316");
        l.add("3577174531486");
        l.add("3577174545756");
        l.add("1577174963559");
        l.add("1577175043421");
        l.add("4577174549953");
        l.add("2577174531843");
        l.add("3577174567176");
        l.add("2577174540889");
        l.add("1577175035666");
        l.add("2577174609379");
        l.add("4577174607903");
        l.add("1577174959889");
        l.add("2577174507955");
        l.add("3577174686719");
        l.add("2577174562078");
        l.add("3577174581018");
        l.add("3577174523390");
        l.add("3577174642971");
        l.add("4577174621876");
        l.add("3577174512786");
        l.add("2577174543226");
        l.add("2577174517168");
        l.add("631445161");
        l.add("2577174562133");
        l.add("3577174602286");
        l.add("4577174543128");
        l.add("1577175043037");
        l.add("3577174607507");
        l.add("3577174598737");
        l.add("4577174617164");
        l.add("2577174628757");
        l.add("2577174517285");
        l.add("1577174983866");
        l.add("3577174596399");
        l.add("1577174974325");
        l.add("4577174478175");
        l.add("4577174497362");
        l.add("2577174506110");
        l.add("3577174547874");
        l.add("2577174642347");
        l.add("3577174638811");
        l.add("2577174538474");
        l.add("3577174523786");
        l.add("3577174608819");
        l.add("4577174480282");
        l.add("2577174489459");
        l.add("4577174548907");
        l.add("1577175033533");
        l.add("2577174531596");
        l.add("3577174631187");
        l.add("2577174580513");
        l.add("1577175017308");
        l.add("3577174659953");
        l.add("1577175100101");
        l.add("1577174968876");
        l.add("3577174628408");
        l.add("2577174629022");
        l.add("2577174593223");
        l.add("4577174511020");
        l.add("3577174566545");
        l.add("3577174587671");
        l.add("1577175114422");
        l.add("1577175040355");
        l.add("2577174487112");
        l.add("2577174528949");
        l.add("1577175102567");
        l.add("3577174651890");
        l.add("2577174553697");
        l.add("4577174543625");
        l.add("3577174543532");
        l.add("4577174550107");
        l.add("2577174631502");
        l.add("3577174521670");
        l.add("4577174582266");
        l.add("4577174629990");
        l.add("2577174601709");
        l.add("2577174583570");
        l.add("3577174539836");
        l.add("1577174976210");
        l.add("4577174592634");
        l.add("2577174552933");
        l.add("3577174536722");
        l.add("4577174617839");
        l.add("1577174991063");
        l.add("3577174651990");
        l.add("1577174964253");
        l.add("3577174519051");
        l.add("1577175032370");
        l.add("3577174556317");
        l.add("1577174976433");
        l.add("4577174527518");
        l.add("4577174518807");
        l.add("3577174511681");
        l.add("4577174558401");
        l.add("3577174638100");
        l.add("2577174563667");
        l.add("3577174690254");
        l.add("2577174521215");
        l.add("3577174548207");
        l.add("3577174586631");
        l.add("3577174617912");
        l.add("4577174537630");
        l.add("2577174500695");
        l.add("1577175040399");
        l.add("3577174559200");
        l.add("4577174574329");
        l.add("4577174583727");
        l.add("3577174519107");
        l.add("4577174484064");
        l.add("1577175113177");
        l.add("4577174586943");
        l.add("4577174579529");
        l.add("1577175107348");
        l.add("1577174963316");
        l.add("4577174617888");
        l.add("2577174512932");
        l.add("3577174538162");
        l.add("1577174958428");
        l.add("4577174501391");
        l.add("2577174645523");
        l.add("2577174643691");
        l.add("3577174551794");
        l.add("3577174632786");
        l.add("4577174599408");
        l.add("3577174508729");
        l.add("3577174530160");
        l.add("2577174526205");
        l.add("1577175094338");
        l.add("2577174529302");
        l.add("1577175072872");
        l.add("4577174632086");
        l.add("1577175019435");
        l.add("2577174560412");
        l.add("1577174983306");
        l.add("3577174612936");
        l.add("3577174595641");
        l.add("3577174515618");
        l.add("4577174560175");
        l.add("2577174584450");
        l.add("3577174517403");
        l.add("3577174575094");
        l.add("1577175063152");
        l.add("4577174481271");
        l.add("4577174551048");
        l.add("3577174575824");
        l.add("3577174543091");
        l.add("2577174614981");
        l.add("2577174495645");
        l.add("1577174962862");
        l.add("4577174568702");
        l.add("3577174514393");
        l.add("1577175116660");
        l.add("4577174487668");
        l.add("4577174578031");
        l.add("3577174531979");
        l.add("4577174603866");
        l.add("2577174549345");
        l.add("4577174485800");
        l.add("3577174535081");
        l.add("2577174642990");
        l.add("1577175080165");
        l.add("4577174517841");
        l.add("1577175098195");
        l.add("2577174566042");
        l.add("1577174994942");
        l.add("3577174519448");
        l.add("3577174581596");
        l.add("4577174536939");
        l.add("1577175117239");
        l.add("4577174552178");
        l.add("2577174531937");
        l.add("4577174585888");
        l.add("1577175095481");
        l.add("2577174620457");
        l.add("1577175068221");
        l.add("1577175006142");
        l.add("1577175004398");
        l.add("2577174521667");
        l.add("2577174614410");
        l.add("1577175047585");
        l.add("1577174975576");
        l.add("4577174595466");
        l.add("3577174539091");
        l.add("3577174599363");
        l.add("1577175106588");
        l.add("4577174586345");



        for(String s:l){
            checkPo(s);
        }
    System.out.println(l.size() + " : " + missingPO.size());
    System.out.println(missingPO);
        System.out.println(canceledPO);
    }


    public static void checkPo(String po){

       try{
        String sql = "select order_id from owd_order where client_fkey = 471 and po_num = :po and is_void = 0";
           Query q = HibernateSession.currentSession().createSQLQuery(sql);
           q.setParameter("po",po);
           List l = q.list();
            System.out.println(po);
           System.out.println(l.size());
           if(l.size()>0){

           }else{
                sql = "select order_id from owd_order where client_fkey = 471 and po_num = :po and is_void = 1";
                q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setParameter("po",po);
                l = q.list();
               System.out.println(po);
               System.out.println(l.size());
               if(l.size()>0){
                   if(!canceledPO.contains(po)){
                       canceledPO.add(po);
                   }

               }else{
                   if(!canceledPO.contains(po)) {
                       missingPO.add(po);
                   }
               }
           }


       }catch (Exception e){
           e.printStackTrace();
       }



    }



}
