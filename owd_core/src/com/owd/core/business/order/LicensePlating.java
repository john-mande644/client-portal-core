package com.owd.core.business.order;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdId;
import org.apache.xpath.operations.Mod;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class LicensePlating {

    private static int MAX = (36*36*36*36)-1;
    private static OwdId LPN;
    private static String MODULE_NAME = "License_plate";
    private static String[] stringArray = {
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z"
    };
    public static String numberToBarcode(int num) {


        String output = "";
        int firstValue = Integer.valueOf(num/(36*36*36));
        int secondValue = Integer.valueOf((num/36/36)%36);
        int thirdValue = Integer.valueOf((num%(36*36))/36);
        int forthValue = Integer.valueOf(num%36);

//        System.out.println("firstValue = " + firstValue);
//        System.out.println("secondValue = " + secondValue);
//        System.out.println("thirdValue = " + thirdValue);
//        System.out.println("forthValue = " + forthValue);

        String firstDigit = stringArray[firstValue];
        String secondDigit = stringArray[secondValue];
        String thirdDigit = stringArray[thirdValue];
        String forthDigit = stringArray[forthValue];
        output = firstDigit + secondDigit + thirdDigit + forthDigit;
        return output;
    }

    static int barcodeToNumber(String input){
        int output = 0;
        int firstDigit = 0;
        int secondDigit = 0;
        int thirdDigit = 0;
        int forthDigit = 0;
        int found = 0;
        for(int x=0; x < 36 && found < 4; x++){
            if(input.charAt(0) == stringArray[x].charAt(0)){
                firstDigit = x;
                found++;
            }
            if(input.charAt(1) == stringArray[x].charAt(0)){
                secondDigit = x;
                found++;
            }
            if(input.charAt(2) == stringArray[x].charAt(0)){
                thirdDigit = x;
                found++;
            }
            if(input.charAt(3) == stringArray[x].charAt(0)){
                forthDigit = x;
                found++;
            }
        }
        output = (firstDigit*36*36*36) +
                (secondDigit* 36*36) +
                (thirdDigit * 36) +
                forthDigit;
        return output;
    }

    public static synchronized String getNextLPN(){
        try {
            if (LPN == null) {
                Criteria criteria = HibernateSession.currentSession().createCriteria(OwdId.class);
                criteria.add(Restrictions.eq("module", MODULE_NAME));
                List results = criteria.list();
                if(results.size()> 0){
                    LPN = (OwdId) results.get(0);
                }else {
                    LPN = new OwdId();
                    LPN.setModule(MODULE_NAME);
                    LPN.setNextId(0);
                }
            }
            String barcode = numberToBarcode(LPN.getNextId());

            setNextBarcode(LPN);
            return barcode;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static synchronized void setNextBarcode(OwdId id) {
        try {
            if (id.getNextId() == MAX) {
                id.setNextId(0);
            } else {
                id.setNextId(id.getNextId()+1);
            }
            HibernateSession.currentSession().saveOrUpdate(id);
            HibUtils.commit(HibernateSession.currentSession());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        for(int i = 0; i < 3; i++){
            System.out.println(getNextLPN());
        }
    }
}
