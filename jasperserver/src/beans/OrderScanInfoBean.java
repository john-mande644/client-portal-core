package beans;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/10/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderScanInfoBean extends ScanInfoBean {

    private String orderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFileName(){
        return  getOrderId()+ "_" + getDate() + "_" + getTime() + ".pdf";
    }
}
