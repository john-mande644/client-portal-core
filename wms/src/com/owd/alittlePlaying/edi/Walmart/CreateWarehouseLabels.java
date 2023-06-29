package com.owd.alittlePlaying.edi.Walmart;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateWarehouseLabels {


    public static void main(String[] args){
        Map<String,String> m = new HashMap<>();
        m.put("1019454269","6094");
        m.put("1619205462","6024");
        m.put("1820132437","6023");
        m.put("1970591722","6048");
        m.put("2570112696","6025");
        m.put("2620053291","6031");
        m.put("3820152359","6038");
        m.put("4070053158","6012");
        m.put("4319364436","6019");
        m.put("4469983312","6069");
        m.put("4521400544","6092");
        m.put("4918599413","6039");
        m.put("5020053058","6018");
        m.put("5224404291","6017");
        m.put("5419604039","6027");
        m.put("5769863509","7034");
        m.put("5819863717","6036");
        m.put("6119115791","6037");
        m.put("6370092825","6030");
        m.put("6969863574","6006");
        m.put("7170092881","6043");
        m.put("7569354814","6068");
        m.put("7620462078","7026");
        m.put("7870621620","7039");
        m.put("8020931134","6009");
        m.put("8270043177","7045");
        m.put("8324843283","6026");
        m.put("9369424551","6080");
        m.put("9369444058","6035");
        m.put("9619424285","6016");
        m.put("9724244155","6070");
        m.put("9969823740","6066");

       // insertOrderDC(m,626);
        generateLabels(m,626);



    }

    public static void generateFile(String orderId,String po) throws Exception{

        StringBuffer sb = new StringBuffer();

        String sql = "SELECT \n" +
                "    dbo.owd_order.bill_first_name, \n" +
                "    dbo.owd_order.bill_last_name, \n" +
                "    dbo.owd_order.bill_address_one, \n" +
                "    dbo.owd_order.bill_state, \n" +
                "    dbo.owd_order.bill_zip, \n" +
                "    dbo.owd_line_item.inventory_num, \n" +
                "    dbo.owd_line_item.quantity_actual, \n" +
                "    convert(varchar,owd_tags_alias1.value) as WMIT, \n" +
                "    convert(varchar,owd_tags_alias2.value) as DC," +
                "bill_city\n" +
                "FROM \n" +
                "    dbo.owd_order \n" +
                "INNER JOIN \n" +
                "    dbo.owd_line_item \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_order.order_id = dbo.owd_line_item.order_fkey) \n" +
                "INNER JOIN \n" +
                "    dbo.owd_tags owd_tags_alias1 \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_line_item.inventory_id = owd_tags_alias1.external_id) \n" +
                "INNER JOIN \n" +
                "    dbo.owd_tags owd_tags_alias2 \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_order.order_id = owd_tags_alias2.external_id) \n" +
                "WHERE \n" +
                "    dbo.owd_order.order_id = :orderId ;";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId",orderId);
        List l = q.list();


        for(Object row :l){
            sb.append(getLabel(row));
        }


        try (PrintWriter out = new PrintWriter("justbrands\\"+po+".prn")) {
            out.println(sb.toString());
        }










    }

    public static String getLabel(Object row){
        Object[] data = (Object[]) row;
        int times = Integer.parseInt(data[6].toString());
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<times;i++) {

            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR4,4~SD15^JUS^LRN^CI0^XZ");
            sb.append("^XA");
            sb.append("^MMT");
            sb.append("^PW812");
            sb.append("^LL1218");
            sb.append("^LS0");
            sb.append("^FO6,256^GB797,0,4^FS");
            sb.append("^FO9,694^GB796,0,4^FS");
            sb.append("^FO3,473^GB796,0,4^FS");
            sb.append("^FO8,886^GB797,0,4^FS");
            sb.append("^FT468,381^A0N,28,28^FH\\^FDPRO:^FS");
            sb.append("^FT512,573^A0N,45,45^FB234,1,0,C^FH\\^FD2620053291 ^FS");
            sb.append("^FT572,517^A0N,28,28^FB106,1,0,C^FH\\^FDORDER #^FS");
            sb.append("^FT402,517^A0N,28,28^FB63,1,0,C^FH\\^FDDEPT^FS");
            sb.append("^FT402,551^A0N,28,28^FB63,1,0,C^FH\\^FD09^FS");
            sb.append("^FT65,664^A0N,39,38^FB284,1,0,C^FH\\^FDWMIT: ");
            sb.append(data[7].toString());
            sb.append("^FS");
            sb.append("^FT66,584^A0N,51,50^FB120,1,0,C^FH\\^FD0");
            sb.append(data[8].toString());
            sb.append("^FS");
            sb.append("^FT105,517^A0N,28,28^FB47,1,0,C^FH\\^FDDC#^FS");
            sb.append("^FT250,517^A0N,28,28^FB62,1,0,C^FH\\^FDTYPE^FS");
            sb.append("^FT250,551^A0N,28,28^FB62,1,0,C^FH\\^FD03^FS");
            sb.append("^FT24,298^A0N,28,28^FH\\^FDPOSTAL CODE^FS");
            sb.append("^FT466,298^A0N,28,28^FH\\^FDCARRIER^FS");
            sb.append("^FT324,241^A0N,28,28^FH\\^FD");
            sb.append(data[9].toString());
            sb.append(", ");
            sb.append(data[3].toString());
            sb.append(" ");
            sb.append(data[4].toString());
            sb.append("^FS");
            sb.append("^FT326,133^A0N,28,28^FH\\^FDShip To:^FS");
            sb.append("^FT324,170^A0N,28,28^FH\\^FDWal-Mart DC ");
            sb.append(data[8].toString());
            sb.append(" - ASM DIS^FS");
            sb.append("^FT326,205^A0N,28,28^FH\\^FD");
            sb.append(data[2].toString());
            sb.append("^FS");
            sb.append("^FT31,241^A0N,28,28^FH\\^FDWilmington, OH 45177^FS");
            sb.append("^FT36,133^A0N,28,28^FH\\^FDShip From:^FS");
            sb.append("^FT35,205^A0N,28,28^FH\\^FD2856 Old State Rte 73^FS");
            sb.append("^FT35,170^A0N,28,28^FH\\^FDJust Brands^FS");
            sb.append("^FO445,264^GB0,212,5^FS");
            sb.append("^FO311,97^GB0,158,7^FS");
            sb.append("^FT163,334^A0N,32,31^FH\\^FD(420)");
            sb.append(data[4].toString());
            sb.append("^FS");
            sb.append("^BY3,3,114^FT95,462^BCN,,N,N");
            sb.append("^FD>;>8420");
            sb.append(data[4].toString());
            sb.append("^FS");
            sb.append("^PQ1,0,1,Y^XZ");
        }

        return sb.toString();

    }


    public static void generateLabels(Map<String,String> items, int clientId){

        for(String po:items.keySet()){
            try {
                System.out.println("doing labels for " + po);
                String orderId = getOrderIdFromPO(po, clientId);
                generateFile(orderId,po);

            }catch (Exception e){
                e.printStackTrace();
            }



        }






    }

    public static void insertOrderDC(Map<String,String> items, int clientId){

        for(String po:items.keySet()){
        try {
            System.out.println("Adding dc for " + po);
            String orderId = getOrderIdFromPO(po, clientId);
            insertTagForDC(orderId, items.get(po));
        }catch (Exception e){
            e.printStackTrace();
        }



        }






    }

    public static void insertTagForDC(String orderId, String dc){
        String sql = "insert into owd_tags (external_id,type,name,value) values (:orderId,'ORDER','WMDC',:dc)";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            q.setParameter("dc",dc);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getOrderIdFromPO(String po, int clientId) throws Exception{
        String sql = "select order_id from owd_order where client_fkey = :clientId and po_num = :ponum and is_void = 0";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("ponum",po);
            return q.list().get(0).toString();


    }

}
