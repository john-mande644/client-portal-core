package com.owd.dc.manifest.ExternalApis.deliverr;

import com.google.gson.Gson;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;
import com.owd.dc.manifest.ExternalApis.deliverr.classes.DeliverrRequest;
import com.owd.dc.manifest.ExternalApis.deliverr.classes.DeliverrResponse;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;

import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.manifest.tracking.TrackingBean;
import com.owd.dc.manifest.tracking.TrackingInfo;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.squareup.okhttp.*;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import static com.owd.core.business.order.Package.voidPackageShipment;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

public class DeliverrApi {
    private static OkHttpClient client = new OkHttpClient();
    private static String URL = "https://fulfillment.deliverr.com/v1/shipment/%s/label";
    private static String deleteURL = "https://fulfillment.deliverr.com/v1/shipment/:order_id/label/:label_id";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    protected final static Logger log = LogManager.getLogger();

    public static List<SHIPMENT> submitOrder(List<AMPConnectShipShipment> shipments, String facility) throws Exception{

        Gson gson = new Gson();
        List<SHIPMENT> shippedPackages = new ArrayList<SHIPMENT>();
        log.debug("logging through packages");
        int packageCount = 1;
        for(AMPConnectShipShipment ship: shipments){
            log.debug("package " + packageCount);

                DeliverrRequest req = new DeliverrRequest();
                String dimensions[] = ship.getValueAsString(ShipConfig.DIMENSION).split("x");

                req.setWidth(Double.parseDouble(dimensions[0]));
                req.setHeight(Double.parseDouble(dimensions[1]));
                req.setLength(Double.parseDouble(dimensions[2]));
                req.setWeight(Double.valueOf(ship.getValueAsString(ShipConfig.WEIGHT)));
                Query q = HibernateSession.currentSession().createSQLQuery("Select owd_inventory.inventory_num, package_line.pack_quantity, dbo.owd_order.order_refnum \n" +
                        "from package_line\n" +
                        "join owd_line_item\n" +
                        "on owd_line_item.line_item_id = package_line.owd_line_item_fkey\n" +
                        "join owd_inventory\n" +
                        "on owd_inventory.inventory_id = owd_line_item.inventory_id\n" +
                        "join package\n" +
                        "on package_line.package_fkey = package.id\n" +
                        "join dbo.package_order \n" +
                        "on dbo.package_order.id= dbo.package.package_order_fkey\n" +
                        "join dbo.owd_order\n" +
                        "on dbo.package_order.owd_order_fkey = dbo.owd_order.order_id\n" +
                        "where package.pack_barcode = :pack_barcode");
                q.setParameter("pack_barcode",ship.getValueAsString("PACKAGE_BARCODE"));
                List result = q.list();
                HashMap<String,Integer> items = new HashMap<String,Integer>();
                String order_refnum = "";
                for(Object obj : result){
                    Object[] data = (Object[]) obj;
                    items.put(data[0].toString(),Integer.parseInt(data[1].toString()));
                    order_refnum = data[2].toString();
                }
                req.setItems(items);
                log.debug("Getting Label for package " + ship.getValueAsString("PACKAGE_BARCODE"));
                log.debug(gson.toJson(req));
                String respStr = doPostRequest(gson.toJson(req),order_refnum);
                if(respStr== null || respStr.equals("")) return null;
                if(!respStr.startsWith("{")){
                    throw new Exception("Deliverr Label API Error: "+ respStr);
                }
                DeliverrResponse resp = gson.fromJson(respStr,DeliverrResponse.class);

            if(null != resp.getErrors()){
                throw new Exception("Deliverr Label API Error: "+resp.getErrors().get(0));
            }

                if(resp.getId()!= null && !resp.getId().equals("")){

                    TrackingInfo.postTrackingInfo(new TrackingBean(ship,resp,1,facility),"DLVR");
                    SHIPMENT shipment = new SHIPMENT();
                    LABELDATA ld = new LABELDATA();

                    ld.setValue(convert(resp.getContents(),resp.getCarrier().equalsIgnoreCase("usps")));

                    ld.setCopies_Needed("1");
                    shipment.getLABELDATA().add(ld);
                    shippedPackages.add(shipment);
                }

        }
        return shippedPackages;
    }

    public static Boolean cancelShipment(int orderId, String external_id, String barcode){
        Boolean success = true;
        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().get(OwdOrder.class,orderId);
            doDeleteRequest(order.getOrderRefnum(),external_id);
            String sql = "SELECT dbo.package.order_track_fkey\n" +
                    "FROM dbo.package\n" +
                    "WHERE dbo.package.pack_barcode = :barcode";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("barcode", barcode);
            List rs = q.list();
            if(rs.size() > 0) {
                String[] data =  (String[]) rs.get(0);
                voidPackageShipment(Integer.parseInt(data[0]), "DeliverrVoid");
            }else{
                throw new Exception("Could not get package from database to void");
            }
        }catch(Exception ex){
            success = false;
            ex.printStackTrace();
        }
        return success;
    }

    private static String doDeleteRequest(String external_id, String label_id) throws IOException{
        Request request = new Request.Builder()
                .url(deleteURL.replace(":order_id",external_id).replace(":label_id", label_id))
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static String doPostRequest(String json, String pack) throws IOException {
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        RequestBody body = RequestBody.create(JSON, json);
        long start = System.currentTimeMillis();
        log.debug("sending request to deliverr");
        Request request = new Request.Builder()
                .url(String.format(URL,pack))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        log.debug("received response from deliverr " + (System.currentTimeMillis()-start) + "ms");
        String respBody = response.body().string();
        log.debug(respBody);
        return respBody;
    }

    public static void  main(String[] args){
        try{
            log.debug(convert("H4sIAAAAAAAAAI1U22rbQBB9rr5ioNCnTdj7SikEZMlyTBLHRC5JStHgUvUCaQ2JoZCv78yuVEdpHiqvdZk5Z3Z2z5G65hba/fZhD7uvsIVf/W+4337u748ev/f9Putuy+6yuutuP/Jt1l2cSSHHS5Z1zZXTUmgH3WKmjRTKSWG7puXMISEpkeshUUpVOEF/gnhC07VuOMMcooTIyYljIwOGuHwZpyOlQmHHlOKRkjwTtyCHJl3eNbFHISqecnndbo6qi7JtYX2+GFp7TsjNhPCBcFftplzMYV0u61cISk6nKDfN5WswMy3cH613j/vtt37YNy5UqJcLyrpqqXOuRVvsKLSJ+1tQqZDHUoKCZ0gF0Tl0Bp3ks5ZoPdoCnY5Bi7pGa9BWaFWMRAyB7QxtAKw0lvOxbUdTDFKyYKm8bdAFtBZdg7YealMxLpki6l++0aFrblbUruEFCCkuDg0bE4dG42LDNXqFfo6+wGDRNxhS3PCjnqOrUHvSOCIJoxnPgIrBnukQF0kXU6BRaALXNjo2Vi2jeak/K7R9tr5ZCLyV19TT5fbHff8FmofdTyhUcHpc0oFi9JRyszmB4rjQsHtKfp6i3RQdIfDfR/aGypkgTEjlcuUP0kd7rlvYXJfV+XK1gLcwX489kNGMTCTr5TDzNO54JXV7tly/kvOc21ydDFuXTDgClBrYUVpP0oaptGQT79BXUSfP96QWjSRh1DyOJLt10UukXB4dNWc+G8xwasCo6GU72rZCV8b76O4EGxS3PAazZDDWLZI1yO3kV4jPFXXi0ch4dsyit4TpOp7Np3dwcA4tG2jfc++ybnZnhDmmT+KsWglF4q7Sjz9N9en709xq6aV39jQvjJRKS6WUlcY6ayy95uObYtzfDVeSX+9n0jIRmAlMBeYCkyGxM/46/wFlDBjKyAUAAA==",true));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param rawLabel
     * @return
     */
    public static String convert(String rawLabel,boolean doLabelFix)
    {
        try
        {
            GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(parseBase64Binary(rawLabel)));
            String b64 = new String(org.apache.commons.net.util.Base64.encodeBase64(IOUtils.toByteArray(is)),StandardCharsets.US_ASCII);

//             return new String(org.apache.commons.net.util.Base64.encodeBase64(IOUtils.toByteArray(is)),StandardCharsets.US_ASCII);

            // ==============================
            // Sean 2020/5/18 case 796631
            if(doLabelFix) {
                b64 = ZPL_to_Base64(ZPL_findAndReplace(Base64_to_ZPL(b64), "BY3", "BY2"));
            }
            return b64;

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "fail";
    }

    /**
     * Sean 2020/5/18 case 796631
     * @param b64 Base64 label string
     * @return
     */
    public static String Base64_to_ZPL(String b64){
        byte[] data = org.apache.commons.net.util.Base64.decodeBase64(b64);
        String ZPL = new String(data, StandardCharsets.UTF_8);
//        log.debug("ZPL   : " + ZPL);
        return ZPL;
    }

    /**
     * Sean 2020/5/18 case 796631
     * @param ZPL label string
     * @return Base64 label string
     */
    public static String ZPL_to_Base64 (String ZPL) {
        String b64 = org.apache.commons.net.util.Base64.encodeBase64String(ZPL.getBytes());
//        log.debug("Base64: " + b64);
        return b64;
    }

    /**
     * Sean 2020/5/18 case 796631
     * @param
     * @return
     */
    public static String ZPL_findAndReplace (String ZPL, String target, String relace){
        String updatedZPL = ZPL.replace(target, relace);
        log.debug("ZPL   : "+updatedZPL);
        return updatedZPL;
    }
}
