package com.owd.dc.receive;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.WMSUtils;
import com.owd.core.managers.LotManager;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.inventory.LabelUtilities;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.dc.inventorycount.wInventoryUtilities;
import com.owd.dc.receive.beans.countInfoBean;
import com.owd.dc.receive.beans.receivedCountBean;
import com.owd.dc.setup.buttons;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danny on 7/17/2015.
 */
public class receiveAction extends ActionSupport {
    private String employeeId;
    private String employeeName;
    private String asnId;
    private String receiveId;
    private String clientName;
    private String asnDescription;
    private String scannedSkuId;
    private String skuId;
    private String receiveItemId;
    private String weight;
    private String length;
    private String width;
    private String height;
    private String count;
    private String action;
    private String damaged;
    private String notes;
    private String doLabelPrint;
    private invBean inventoryBean;
    private countInfoBean countedInfo;
    private List<receivedCountBean> countList;
    private InputStream inputStream;
    private Integer printQty;
    private String packingSlip;
    private String packages;
    private String minutes;
    private String pallets;
    private String lotCode ="";
    private String upcCode ="";
    private String asnNotes;
    private String asnCreatedDate;
    private String rcvNotes;



    public String loadASNSearch(){
        return "success";
    }
    public String loadASNSearchableItems(){



        inputStream =  IOUtils.toInputStream(receiveUtilities.getAsnSearchDataTablesJson(WMSUtils.getFacilityFromContext(ActionContext.getContext())));



        return "success";
    }

    public String loadSearch(){
        return "success";
    }
    public String loadSearchableItems(){



       inputStream =  IOUtils.toInputStream(receiveUtilities.getAsnItemsJson(asnId));



        return "success";
    }

    public String execute() {
        //todo check timeclock id, name
        try {
            for (Cookie c : ServletActionContext.getRequest().getCookies()) {
                if (c.getName().equals("tcid")) {

                    employeeId = c.getValue();
                    System.out.println("Setting empoleeid to " + c.getValue());
                }
                if (c.getName().equals("tcname")) {

                    employeeName = c.getValue();
                    System.out.println("Setting name to " + c.getValue());
                }


            }


        } catch (Exception e) {
            addActionError(e.getMessage());

        }

        return "success";
    }


    public String loadAsn() {
        try {
            //Check to see if there is already a receive started.
            //if so load that and continue


            //if not create new receive from ASN id and pass to mark in building
            Receive rcv = null;
            if (asnId.length() == 0) {
                throw new Exception("Please enter valid asnId");
            }

            rcv = receiveUtilities.getReceive(asnId, WMSUtils.getFacilityFromContext(ActionContext.getContext()));

            if (rcv == null) {
                throw new Exception("Unable to load ASN please try again");
            }
            receiveId = rcv.getId().toString();

            if (rcv.getReceiveBy().equals("")) {
                rcv.setReceiveBy(employeeName);
                receiveUtilities.saveObject(HibernateSession.currentSession(), rcv);
                //send to mark in building page
                return "mark";
            }
            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, rcv.getClientFkey());
            clientName = client.getCompanyName();
            asnDescription = rcv.getAsn().getClientRef();
            asnNotes = rcv.getAsn().getNotes();
            rcvNotes = rcv.getNotes();
            asnCreatedDate = rcv.getAsn().getCreatedDate().toString();
            //load sku's
            countList = receiveUtilities.getReceivedItems(rcv);


        } catch (Exception e) {
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }


    public String loadSku() {
        //check to see if sku entered is in this receive.
        //If it is, then load info and proceed.
        try{
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(receiveId));
            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, rcv.getClientFkey());
            clientName = client.getCompanyName();
            asnDescription = rcv.getAsn().getClientRef();
            countList = receiveUtilities.getReceivedItems(rcv);
            //check for valid sku if upc is scanned
            if(""==scannedSkuId) throw new Exception("No sku entered!!");
            String sku = upcBarcodeUtilities.getSku(scannedSkuId, rcv.getClientFkey());
            if( sku.equals("NA")){
                throw new Exception("Barcode not assigned to anything, please enter Id number");
            }
            if (sku.equals("multi")){
                throw new Exception("Multiple sku's for barcode found please enter id");
            }
            if(sku.equals(scannedSkuId)){
                System.out.println("barcode not scanned we nay need to label.");
                doLabelPrint = "yes";
            } else{
                doLabelPrint ="no";
            }
            skuId = sku;
            if (!wInventoryUtilities.verifyClientSku(rcv.getClientFkey(), sku)) {
                throw new Exception("Sku entered is not for current client doing receive for");
            }

            //this call will check if the sku exists in the receive and load receive Item ID
            receiveItemId = receiveUtilities.getReceiveItemIdFromInvId(receiveId, skuId);
            rcvNotes = receiveUtilities.getReceiveItemFromInvId(receiveId,skuId).getNotes();
            inventoryBean =  receiveUtilities.loadInventoryBean(sku, WMSUtils.getFacilityFromContext(ActionContext.getContext()));
            countedInfo = receiveUtilities.getCountedItem(receiveItemId, employeeId);

            Collection<AsnItem> asnItems = rcv.getAsn().getAsnItems();
            for (Iterator iterator = asnItems.iterator(); iterator.hasNext();){
                AsnItem asnItem = (AsnItem) iterator.next();
                if(asnItem.getInventoryFkey() == Integer.parseInt(inventoryBean.getInventoryId())){
                    asnNotes = asnItem.getNote();
                }
            }

        } catch (Exception e) {


            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }

    public String getSkuCount() {
        try {
            try {
                Integer.parseInt(count);

            } catch (NumberFormatException ex) {
                inventoryBean = receiveUtilities.loadInventoryBean(skuId, WMSUtils.getFacilityFromContext(ActionContext.getContext()));
                countedInfo = receiveUtilities.getCountedItem(receiveItemId, employeeId);
                addActionError("Please enter valid number");
                return "error";
            }
            System.out.println("WE have this a many counted " + count);
            if(upcCode.length()>2){
                String barcodeType = upcBarcodeUtilities.barcodeCheck(upcCode);
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,Integer.parseInt(skuId));
                if(barcodeType.equals("UPC")){
                    inv.setUpcCode(upcCode);
                    HibernateSession.currentSession().save(inv);
                    HibUtils.commit(HibernateSession.currentSession());
                    addActionMessage("Assigned "+ upcCode + " to UPC");
                }else if (barcodeType.equals("ISBN")){
                    inv.setIsbnCode(upcCode);
                    HibernateSession.currentSession().save(inv);
                    HibUtils.commit(HibernateSession.currentSession());
                    addActionMessage("Assigned "+ upcCode + " to ISBN");
                }else{
                    inventoryBean = receiveUtilities.loadInventoryBean(skuId, WMSUtils.getFacilityFromContext(ActionContext.getContext()));
                    countedInfo = receiveUtilities.getCountedItem(receiveItemId, employeeId);

                    addActionError("You have entered an invalid UPC/ISBN code. Please try again");
                    return "error";
                }
            }


        } catch (Exception e) {
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }

    public String getSkuDamaged() {
        try {

            try {
                if (damaged.equals("")) damaged = "0";

                Integer.decode(damaged);
            } catch (NumberFormatException nex) {
                // invBean ib = receivingUtilities.loadInventoryBean(rcvForm.getInvId());
                //request.setAttribute("ib", ib);
                addActionError("Damaged quanity must be numeric");
                return "error";
            }

            if (receiveUtilities.needMeasurementsForReceiveItem(skuId)) {
                System.out.println("in need Measurements");
                weight = (receiveUtilities.getSkuWeight(skuId));
                width = (receiveUtilities.getSkuWidth(skuId));
                length = (receiveUtilities.getSkuLength(skuId));
                height = (receiveUtilities.getSkuHeight(skuId));
                return "weigh";
            } else {
                weight = (receiveUtilities.getSkuWeight(skuId));
                width = (receiveUtilities.getSkuWidth(skuId));
                length = (receiveUtilities.getSkuLength(skuId));
                height = (receiveUtilities.getSkuHeight(skuId));
            }
            if(LotManager.isInventoryIdLotControlled(Integer.parseInt(skuId)))   {
                return "lotControlled";
            }


        } catch (Exception e) {
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }

    public String getSkuWeight() {
        try {
            try {
                // Float.parseFloat(rcvForm.getWeight());
                if(Float.parseFloat(weight)<=0){
                    throw new Exception("Weight Must be greater than 0");
                }
                if ((Float.parseFloat(length) <= 0)) {

                    throw new Exception("Length Must be greater than 0");
                }
                if ((Float.parseFloat(width) <= 0)) {

                    throw new Exception("Width Must be greater than 0");
                }
                if ((Float.parseFloat(height) <= 0)) {

                    throw new Exception("Height Must be greater than 0");
                }

                weight = (Float.parseFloat(weight) + "");
                length = (Float.parseFloat(length) + "");
                width = (Float.parseFloat(width) + "");
                height = (Float.parseFloat(height) + "");
                //Integer.decode(rcvForm.getDamaged());
                LOG.debug("This is the sku to check for lot controlled " + skuId);
                if(LotManager.isInventoryIdLotControlled(Integer.parseInt(skuId)))   {
                   return "lotControlled";
                }
            } catch (NumberFormatException nex) {
                // invBean ib = receivingUtilities.loadInventoryBean(rcvForm.getInvId());
                //request.setAttribute("ib", ib);
                addActionError("Weight/Length/Width/Height must be numeric");
                return "error";
            }

        } catch (Exception e) {
            addActionError(e.getMessage());
            return "error";
        }

        return "success";
    }
    public String getLots(){

        try{
            if(length==null || width == null || height == null){
                weight = (receiveUtilities.getSkuWeight(skuId));
                width = (receiveUtilities.getSkuWidth(skuId));
                length = (receiveUtilities.getSkuLength(skuId));
                height = (receiveUtilities.getSkuHeight(skuId));
            }
            if(lotCode.length()>0){
                if(!LotManager.isLotValueValidForInventoryId(lotCode,Integer.parseInt(skuId))){
                    LOG.debug("This is not a valid lot code: " + lotCode+" for this Id: "+skuId);
                    throw new Exception("This is not a valid lot code: "+lotCode+" for this id");
                }
            } else{
                throw new Exception("lotCode cannot be empty");
            }


        } catch (Exception e){
            addActionError(e.getMessage());
            return "error";
        }

      return "success";
    }

    public String verify() {
        try {
            System.out.println("In the verify");
            if (null == notes) {
                notes = "";
            }
            System.out.println(2);
            System.out.println(employeeId);
            System.out.println(employeeName);
            ReceiveItem ri = (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class, Integer.valueOf(receiveItemId));

            if (!skuId.equals(ri.getInventoryFkey() + "")) {
                System.out.println("BAD BAD BAD");
                addActionError("There was an error, the sku you are working on was not saved");
                return "reset";
            }
            //check for serial numbers required



            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(skuId));
            if (inv.getRequireSerialNumbers() == 1) {
                return "serial";
            }

           //todo also update lot code

            receiveUtilities.updateReceiveItem(ri, employeeId, employeeName, count, damaged, notes, weight, length, width, height,lotCode);
            if(doLabelPrint.equals("yes")){
                return "printing";
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }

    public String printSmallLabel() {
        String message = "printed label";
        try {
            if (printQty > 100) printQty = 100;
            buttons btn = new buttons();
            LabelUtilities.barcodeLabel(skuId, printQty, btn.getSmallPrinter(employeeName), ServletActionContext.getRequest().getServerName());
        } catch (Exception e) {
            e.printStackTrace();
            message = "error";
        }
        inputStream = new ByteArrayInputStream(message.getBytes());


        return "success";
    }

    public String printLargeLabel() {
        String message = "printed label";
        try {
            if (printQty > 100) printQty = 100;
            buttons btn = new buttons();
            LabelUtilities.printLargeInventoryLabel(skuId, printQty, btn.getPrinter(employeeName), ServletActionContext.getRequest().getServerName());
        } catch (Exception e) {
            e.printStackTrace();
            message = "error";
        }
        inputStream = new ByteArrayInputStream(message.getBytes());


        return "success";
    }

    public String printTags() {

        String message = "printed label";
        try {
            if (printQty > 100) printQty = 100;

            LabelUtilities.printPalletTag(HandheldUtilities.loadInv(new Integer(skuId)), printQty, HandheldUtilities.getPalletTagPrinterForUser(employeeName));


        } catch (Exception e) {
            e.printStackTrace();
            message = "error";
        }
        inputStream = new ByteArrayInputStream(message.getBytes());


        return "success";


    }
    public String doneWithLabels(){
        return "success";
    }
    public String complete(){

       try{
          Receive r = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.parseInt(receiveId));
if(r.getEndTimestamp().getTime()-r.getStartTimestamp().getTime()>0){
    System.out.println("Loading current values");
    minutes = ((r.getEndTimestamp().getTime()-r.getStartTimestamp().getTime())/60000)+"";
    packages = r.getCartonCount().toString();
    pallets = r.getPalletCount().toString();
    if(r.getIsPackSlipFound()==1){
        if(r.getIsPackSlipMatch()==1){
            packingSlip="match";
        }else{
            packingSlip="nomatch";
        }
    }else{
        packingSlip="no";
    }


}


       }catch(Exception e){
           e.printStackTrace();
           addActionError(e.getMessage());
       }

        return "success";
    }

    public String saveFinished(){
        try{
            if(packages.equals("")) packages = "0";
            if(pallets.equals("")) pallets="0";
            if(minutes.equals("")) minutes="0";
            if(packingSlip.equals("")) throw new Exception("Please select packing slip info");
            if(minutes.equals("0") || (minutes.contains("-"))) throw new Exception("You must specify minutes greater than 0");
            if(pallets.equals("0") && packages.equals("0")){
                throw new Exception("You must enter in either packages or pallets");
            }

            Receive r = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.parseInt(receiveId));
           // r.setBilledMinutes(Integer.parseInt(minutes));
            r.setReceiveBy(employeeName);

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(r.getStartTimestamp().getTime() + (Integer.parseInt(minutes) * 60000));
            System.out.println(c.getTime());
            r.setEndTimestamp(c.getTime());



            r.setCartonCount(Integer.parseInt(packages));
            r.setPalletCount(Integer.parseInt(pallets));
            if(packingSlip.equals("match")){
                r.setIsPackSlipFound(1);
                r.setIsPackSlipMatch(1);

            }
            if(packingSlip.equals("nomatch")){
                r.setIsPackSlipFound(1);
                r.setIsPackSlipMatch(0);
            }
            if(packingSlip.equals("no")){
                r.setIsPackSlipFound(0);
                r.setIsPackSlipMatch(0);
            }
            HibernateSession.currentSession().save(r);
            HibUtils.commit(HibernateSession.currentSession());

                   }catch (Exception e){
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }





    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<receivedCountBean> getCountList() {
        return countList;
    }

    public void setCountList(List<receivedCountBean> countList) {
        this.countList = countList;
    }

    public String getScannedSkuId() {
        return scannedSkuId;
    }

    public void setScannedSkuId(String scannedSkuId) {
        this.scannedSkuId = scannedSkuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getReceiveItemId() {
        return receiveItemId;
    }

    public void setReceiveItemId(String receiveItemId) {
        this.receiveItemId = receiveItemId;
    }

    public String getAsnDescription() {
        return asnDescription;
    }

    public void setAsnDescription(String asnDescription) {
        this.asnDescription = asnDescription;
    }

    public String getDoLabelPrint() {
        return doLabelPrint;
    }

    public void setDoLabelPrint(String doLabelPrint) {
        this.doLabelPrint = doLabelPrint;
    }

    public invBean getInventoryBean() {
        return inventoryBean;
    }

    public void setInventoryBean(invBean inventoryBean) {
        this.inventoryBean = inventoryBean;
    }

    public countInfoBean getCountedInfo() {
        return countedInfo;
    }

    public void setCountedInfo(countInfoBean countedInfo) {
        this.countedInfo = countedInfo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Integer getPrintQty() {
        return printQty;
    }

    public void setPrintQty(Integer printQty) {
        this.printQty = printQty;
    }

    public String getPackingSlip() {
        return packingSlip;
    }

    public void setPackingSlip(String packingSlip) {
        this.packingSlip = packingSlip;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getPallets() {
        return pallets;
    }

    public void setPallets(String pallets) {
        this.pallets = pallets;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public String getAsnNotes() {
        return asnNotes;
    }

    public void setAsnNotes(String asnNotes) {
        this.asnNotes = asnNotes;
    }

    public String getAsnCreatedDate() {
        return asnCreatedDate;
    }

    public void setAsnCreatedDate(String asnCreatedDate) {
        this.asnCreatedDate = asnCreatedDate;
    }

    public String getRcvNotes() {
        return rcvNotes;
    }

    public void setRcvNotes(String rcvNotes) {
        this.rcvNotes = rcvNotes;
    }
}
