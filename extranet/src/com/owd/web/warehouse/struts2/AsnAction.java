package com.owd.web.warehouse.struts2;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AsnAction extends ActionSupport{


    private Receive rcv;
    private Asn asn;
    private String created_on;
    private String created_by;
    private String start_date;
    private String end_date;
    private String receiveId;
    private String posted;
    private String asnFound;
    private String packSlipFound;
    private String packSlipMatch;
    private int asnId;
    private OwdClient client;
    private Collection<ReceiveItem> items;
    private ArrayList<LineItem> lineItems = new ArrayList<>();
    private ArrayList<Scandoc> imagePacks = new ArrayList<>();

    public String display(){

        System.out.println("display(receiveId) of AsnAction");
        try {
            System.out.println("pre-search of AsnAction");
            rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(receiveId));
            asn = rcv.getAsn();
            posted = parseYesNo(rcv.getIsPosted());
            asnFound = parseYesNo(rcv.getIsAsnFound());
            packSlipFound = parseYesNo(rcv.getIsPackSlipFound());
            packSlipMatch = parseYesNo(rcv.getIsPackSlipMatch());
            System.out.println("post-search of AsnAction");
            created_on = rcv.getCreatedOn().toString();
            created_by = rcv.getCreatedBy();
            start_date = rcv.getStartTimestamp().toString();
            end_date = rcv.getEndTimestamp().toString();
            client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.valueOf(rcv.getClientFkey()));
            System.out.println("Client ID: " + rcv.getClientFkey());
            System.out.println("Client: " + client.getCompanyName());
            asnId = rcv.getAsn().getId();
            items = rcv.getReceiveItems();
            for(Iterator <ReceiveItem> iterator = items.iterator(); iterator.hasNext();){
                System.out.println("for loop 1");
                LineItem lineItem = new LineItem();
                System.out.println("for loop 2");
                lineItem.load(iterator.next());
                System.out.println("for loop 3");
                lineItems.add(lineItem);
                System.out.println("for loop 4");
            }
            Criteria criteria = HibernateSession.currentSession().createCriteria(Scandoc.class);
            List<Scandoc> res = criteria.add(Restrictions.eq("scan_id", asnId)).list();
            for(int i = 0; res.size() > i; i++){
                imagePacks.add(res.get(i));
                System.out.println("Scandoc: " + res.get(1).getName());
            }
            System.out.println("item count: " + lineItems.size());
        }catch(Exception e){
            System.out.println(e.toString());
            addActionError(e.getMessage());
            return "error";
        }
        return "success";
    }

    private String parseYesNo(int val){
        if(val == 1) return "yes";
        return "no";
    }

    class LineItem {

        private Integer damaged;
        private Integer qtyReceived;
        private String notes;
        private String description;
        private String inventoryNum;

        public void load(ReceiveItem item){
            try {
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(item.getInventoryFkey()));
                description = inv.getDescription();
                inventoryNum = inv.getInventoryNum();
            }
            catch(Exception ex){
                System.out.println(ex.toString());
            }
            damaged = item.getQtyDamage();
            qtyReceived = item.getQtyReceive();
            notes = item.getNotes();
            System.out.println(inventoryNum + " : "
                    + damaged.toString() + " : "
                    + description + " : "
                    + qtyReceived.toString() + " : "
                    + notes);
        }

        public Integer getDamaged() {
            return damaged;
        }

        public void setDamaged(Integer damaged) {
            this.damaged = damaged;
        }

        public Integer getQtyReceived() {
            return qtyReceived;
        }

        public void setQtyReceived(Integer qtyReceived) {
            this.qtyReceived = qtyReceived;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getInventoryNum() {
            return inventoryNum;
        }

        public void setInventoryNum(String inventoryNum) {
            this.inventoryNum = inventoryNum;
        }
    }

    public Receive getRcv() {
        return rcv;
    }

    public void setRcv(Receive rcv) {
        this.rcv = rcv;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public Asn getAsn() {
        return asn;
    }

    public void setAsn(Asn asn) {
        this.asn = asn;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public OwdClient getClient() {
        return client;
    }

    public void setClient(OwdClient client) {
        this.client = client;
    }

    public String getAsnFound() {
        return asnFound;
    }

    public void setAsnFound(String asnFound) {
        this.asnFound = asnFound;
    }

    public String getPackSlipFound() {
        return packSlipFound;
    }

    public void setPackSlipFound(String packSlipFound) {
        this.packSlipFound = packSlipFound;
    }

    public String getPackSlipMatch() {
        return packSlipMatch;
    }

    public void setPackSlipMatch(String packSlipMatch) {
        this.packSlipMatch = packSlipMatch;
    }

    public int getAsnId() {
        return asnId;
    }

    public void setAsnId(int asnId) {
        this.asnId = asnId;
    }

    public Collection<ReceiveItem> getItems() {
        return items;
    }

    public void setItems(Collection<ReceiveItem> items) {
        this.items = items;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineitems) {
        this.lineItems = lineitems;
    }

    public ArrayList<Scandoc> getImagePacks() {
        return imagePacks;
    }

    public void setImagePacks(ArrayList<Scandoc> imagePacks) {
        this.imagePacks = imagePacks;
    }
}
