package com.owd.core

import com.owd.core.managers.InventoryManager
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.*
import org.hibernate.Query

import java.sql.ResultSet

/**
 * Created by stewartbuskirk1 on 5/28/15.
 */
class TagUtilities {


    public static final String kEDIPurchaseOrder = "COM.OWD.EDI.850"
    public static final String kEDIPurchaseOrderAcknowledgement = "COM.OWD.EDI.855"
    public static final String kEDIAsn = "COM.OWD.EDI.856"
    public static final String kEDIInvoice = "COM.OWD.EDI.810"
    public static final String kVendorComplianceReference = "COM.OWD.COMPLIANCE"
    public static final String kVendorComplianceIDReference = "COM.OWD.COMPLIANCE.ID"
    public static final String kEDIAmazonARN = "COM.OWD.EDI.AMAZON.ARN"
    public static final String kSPReturn = "COM.OWD.RETURN.SMARTPOST"
    public static final String kMIReturn = "COM.OWD.RETURN.MAIL_INNOVATIONS"
    public static final String kReturnLabel = "COM.OWD.RETURN.LABEL"
    public static final String kEDIZapposDN = "COM.OWD.EDI.ZAPPOS.DN"
    public static final String kEDIDicksASIDN = "COM.OWD.EDI.DICKS.ASIDN"


    public static final Map<String, String> owdTagNames = new HashMap<String, String>();

    static {
        owdTagNames.put(kEDIPurchaseOrder, "EDI PO 850")
        owdTagNames.put(kEDIAsn, "EDI ASN 856")
        owdTagNames.put(kEDIPurchaseOrderAcknowledgement,"EDI ACK 855")
        owdTagNames.put(kEDIInvoice, "EDI Invoice 810")
        owdTagNames.put(kVendorComplianceReference, "Vendor Compliance Policy")
        owdTagNames.put(kVendorComplianceIDReference, "Vendor Compliance Policy ID")
        owdTagNames.put(kEDIAmazonARN,"Amazon ARN")
        owdTagNames.put(kSPReturn,"SmartPost Return")
        owdTagNames.put(kReturnLabel,"Return Label")
        owdTagNames.put(kMIReturn,"MailInnovations Return")
        owdTagNames.put(kEDIZapposDN, "Zappos DN")
        owdTagNames.put(kEDIDicksASIDN, "Dick's ASIDN")
    }


    public static String translateOwdTagLabel(String key) {
        if (owdTagNames.containsKey(key)) {
            return owdTagNames.get(key)
        } else {
            return key
        }
    }

    public static void deleteTag(int externalId, String tagType, String name){
        try{
            String sql = "delete from owd_tags where external_id = :externalId and type = :type and name = :name ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("externalId",externalId);
            q.setParameter("type",tagType);
            q.setParameter("name",name);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }

        }catch (Exception e){
            e.printStackTrace()
        }
    }

    public static synchronized Map<String, String> getTagMap(String tagType, int externalId) {
        TreeMap<String, String> tagMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        try {
            ResultSet rs = HibernateSession.getResultSet("select name,value " +
                    "from owd_tags where type='" + tagType + "' and " +
                    "external_id=" + externalId);

            while (rs.next()) {
                tagMap.put(rs.getString(1), rs.getString(2));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

        return tagMap;


    }

    public static void setInventoryTagForSku(int clientId, String sku, String tagName, String value)

    {
        println sku
        try {
            OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(clientId + "", sku);
            setInventoryTagForOwdInventory(item, tagName, value)
        }   catch (Exception ex) {
            println ex.getMessage()
        }


    }

    public static void setInventoryTagForInventoryId(int inventoryId, String tagName, String value) {
        OwdInventory item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, inventoryId)

        setInventoryTagForOwdInventory(item, tagName, value)

    }

    public static void setInventoryTagForOwdInventory(OwdInventory item, String tagName, String value) {
        InventoryTag tag = new InventoryTag()
        tag.setTagName(tagName.toUpperCase())
        tag.setTagValue(value)
        validateTag(tag)
        boolean isNewTag = true;
        for (InventoryTag oldTag : item.getTags()) {
            if (oldTag.getTagName().equalsIgnoreCase(tagName)) {
                oldTag.setTagValue(value)
                isNewTag = false
                tag = oldTag
                break
            }
        }
        if (isNewTag) {
            tag.setInventory(item)
            item.getTags().add(tag)
        }
        HibernateSession.currentSession().save(tag)

        HibernateSession.currentSession().saveOrUpdate(item)

        HibUtils.commit(HibernateSession.currentSession())

    }

    public static void setOrderTagForOwdOrder(OwdOrder order, String tagName, String value) {
        OrderTag tag = new OrderTag()
        boolean isNewTag = true;
        for (OrderTag oldTag : order.getTags()) {
            if (oldTag.getTagName().equalsIgnoreCase(tagName)) {
                oldTag.setTagValue(value)
                isNewTag = false
                tag = oldTag
                break
            }
        }
        if (isNewTag) {
            tag.tagName = tagName;
            tag.tagValue = value;
            tag.setOrder(order)
            order.getTags().add(tag)
        }
        HibernateSession.currentSession().save(tag)
        HibernateSession.currentSession().saveOrUpdate(order)

        HibUtils.commit(HibernateSession.currentSession())


    }

    public static void setOrderTag(int orderId, String tagName, String value) {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId)

        setOrderTagForOwdOrder(order, tagName, value)

    }

    public static void validateTag(OwdTag tag) throws Exception {

        if (tag.getTagName() == null) {
            throw new Exception("Custom value name cannot be null");
        }
        if (tag.getTagValue() == null) {
            throw new Exception("Custom value contents cannot be null");
        }
        if (tag.getTagName().getBytes().length > 254) {
            throw new Exception("Custom value name must be 254 characters or less");
        }
        if (tag.getTagValue().getBytes().length > 640000) {
            throw new Exception("Custom value contents must be 640000 characters or less");
        }
        Scanner scanner = new Scanner(tag.getTagName());
        String validationResult = scanner.findInLine("[^A-Z0-9_\\-\\.]+");
        if (validationResult != null) {
            // Invalid character found.
            throw new Exception("Invalid character found in custom value name: " + validationResult);
        }
        if (tag.getTagValue().trim().getBytes().length < 1) {
            throw new Exception("Custom value contents must contain at least one non-whitespace character");
        }


    }

    public static void main(String[] args) throws Exception {
        System.setProperty("com.owd.environment","dev");
        HibernateSession.currentSession();


     //   TagUtilities.setInventoryTagForSku(489, "P404302", "GIFTBAG349840", "1");
     //   TagUtilities.setInventoryTagForSku(489, "P404303", "GIFTBAG349840", "1");


    }
}
