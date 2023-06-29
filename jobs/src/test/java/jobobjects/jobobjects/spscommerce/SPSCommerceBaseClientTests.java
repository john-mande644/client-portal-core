package jobobjects.jobobjects.spscommerce;

import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient;
import groovy.util.Node;
import groovy.util.XmlParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by danny on 2/29/2020.
 */
public class SPSCommerceBaseClientTests {


    @Test
    public void futureDateTest(){
        String s = "<Order>\n" +
                "  <Header>\n" +
                "    <OrderHeader>\n" +
                "      <TradingPartnerId>\n" +
                "        BPRALLJUSTBRAND\n" +
                "      </TradingPartnerId>\n" +
                "      <PurchaseOrderNumber>\n" +
                "        009833334\n" +
                "      </PurchaseOrderNumber>\n" +
                "      <TsetPurposeCode>\n" +
                "        00\n" +
                "      </TsetPurposeCode>\n" +
                "      <PurchaseOrderTypeCode>\n" +
                "        SA\n" +
                "      </PurchaseOrderTypeCode>\n" +
                "      <PurchaseOrderDate>\n" +
                "        2020-02-13\n" +
                "      </PurchaseOrderDate>\n" +
                "      <Vendor>\n" +
                "        675595\n" +
                "      </Vendor>\n" +
                "    </OrderHeader>\n" +
                "    <Date>\n" +
                "      <DateTimeQualifier1>\n" +
                "        010\n" +
                "      </DateTimeQualifier1>\n" +
                "      <Date1>\n" +
                "        2020-08-01\n" +
                "      </Date1>\n" +
                "    </Date>\n" +
                "    <Date>\n" +
                "      <DateTimeQualifier1>\n" +
                "        001\n" +
                "      </DateTimeQualifier1>\n" +
                "      <Date1>\n" +
                "        2020-09-01\n" +
                "      </Date1>\n" +
                "    </Date>\n" +
                "    <Contact>\n" +
                "      <ContactTypeCode>\n" +
                "        BD\n" +
                "      </ContactTypeCode>\n" +
                "      <PrimaryPhone>\n" +
                "        406-453-7666\n" +
                "      </PrimaryPhone>\n" +
                "    </Contact>\n" +
                "    <Address>\n" +
                "      <AddressTypeCode>\n" +
                "        ST\n" +
                "      </AddressTypeCode>\n" +
                "      <LocationCodeQualifier>\n" +
                "        92\n" +
                "      </LocationCodeQualifier>\n" +
                "      <AddressLocationNumber>\n" +
                "        070\n" +
                "      </AddressLocationNumber>\n" +
                "      <AddressName>\n" +
                "        GREAT FALLS SCHEELS\n" +
                "      </AddressName>\n" +
                "      <Address1>\n" +
                "        1200 10TH AVE S STE #92\n" +
                "      </Address1>\n" +
                "      <City>\n" +
                "        GREAT FALLS\n" +
                "      </City>\n" +
                "      <State>\n" +
                "        MT\n" +
                "      </State>\n" +
                "      <PostalCode>\n" +
                "        59405\n" +
                "      </PostalCode>\n" +
                "    </Address>\n" +
                "    <FOBRelatedInstruction>\n" +
                "      <FOBPayCode>\n" +
                "        DF\n" +
                "      </FOBPayCode>\n" +
                "    </FOBRelatedInstruction>\n" +
                "    <CarrierInformation>\n" +
                "      <CarrierRouting>\n" +
                "        See Vendor Compliance Manual\n" +
                "      </CarrierRouting>\n" +
                "    </CarrierInformation>\n" +
                "  </Header>\n" +
                "  <LineItems>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          1\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          AH-SK-AA-03\n" +
                "        </BuyerPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          675595807442\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          4\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          25\n" +
                "        </PurchasePrice>\n" +
                "        <ExtendedItemTotal>\n" +
                "          100\n" +
                "        </ExtendedItemTotal>\n" +
                "      </OrderLine>\n" +
                "      <PriceInformation>\n" +
                "        <PriceTypeIDCode>\n" +
                "          RTL\n" +
                "        </PriceTypeIDCode>\n" +
                "        <UnitPrice>\n" +
                "          49.99\n" +
                "        </UnitPrice>\n" +
                "      </PriceInformation>\n" +
                "      <ProductOrItemDescription>\n" +
                "        <ItemDescriptionType>\n" +
                "          08\n" +
                "        </ItemDescriptionType>\n" +
                "        <ProductDescription>\n" +
                "          SOCK HEATD BATTRY WOOL-S/M/BK\n" +
                "        </ProductDescription>\n" +
                "      </ProductOrItemDescription>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          2\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          AH-SK-AA-03\n" +
                "        </BuyerPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          675595807459\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          8\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          25\n" +
                "        </PurchasePrice>\n" +
                "        <ExtendedItemTotal>\n" +
                "          200\n" +
                "        </ExtendedItemTotal>\n" +
                "      </OrderLine>\n" +
                "      <PriceInformation>\n" +
                "        <PriceTypeIDCode>\n" +
                "          RTL\n" +
                "        </PriceTypeIDCode>\n" +
                "        <UnitPrice>\n" +
                "          49.99\n" +
                "        </UnitPrice>\n" +
                "      </PriceInformation>\n" +
                "      <ProductOrItemDescription>\n" +
                "        <ItemDescriptionType>\n" +
                "          08\n" +
                "        </ItemDescriptionType>\n" +
                "        <ProductDescription>\n" +
                "          SOCK HEATD BATTRY WOOL-L/XL/BK\n" +
                "        </ProductDescription>\n" +
                "      </ProductOrItemDescription>\n" +
                "    </LineItem>\n" +
                "    <LineItem>\n" +
                "      <OrderLine>\n" +
                "        <LineSequenceNumber>\n" +
                "          3\n" +
                "        </LineSequenceNumber>\n" +
                "        <BuyerPartNumber>\n" +
                "          AH-SK-AA-03\n" +
                "        </BuyerPartNumber>\n" +
                "        <ConsumerPackageCode>\n" +
                "          675595807466\n" +
                "        </ConsumerPackageCode>\n" +
                "        <OrderQty>\n" +
                "          2\n" +
                "        </OrderQty>\n" +
                "        <OrderQtyUOM>\n" +
                "          EA\n" +
                "        </OrderQtyUOM>\n" +
                "        <PurchasePrice>\n" +
                "          25\n" +
                "        </PurchasePrice>\n" +
                "        <ExtendedItemTotal>\n" +
                "          50\n" +
                "        </ExtendedItemTotal>\n" +
                "      </OrderLine>\n" +
                "      <PriceInformation>\n" +
                "        <PriceTypeIDCode>\n" +
                "          RTL\n" +
                "        </PriceTypeIDCode>\n" +
                "        <UnitPrice>\n" +
                "          49.99\n" +
                "        </UnitPrice>\n" +
                "      </PriceInformation>\n" +
                "      <ProductOrItemDescription>\n" +
                "        <ItemDescriptionType>\n" +
                "          08\n" +
                "        </ItemDescriptionType>\n" +
                "        <ProductDescription>\n" +
                "          SOCK HEATD BATTRY WOOL-2X/BK\n" +
                "        </ProductDescription>\n" +
                "      </ProductOrItemDescription>\n" +
                "    </LineItem>\n" +
                "  </LineItems>\n" +
                "  <Summary>\n" +
                "    <TotalLineItemNumber>\n" +
                "      3\n" +
                "    </TotalLineItemNumber>\n" +
                "  </Summary>\n" +
                "</Order>\n";

        SPSCommerceBaseClient client = new SPSCommerceBaseClient();
       try {
               XmlParser parser = new XmlParser();
               parser.setTrimWhitespace(true);
           Node podata = parser.parseText(s);
           client.checkForFutureShipDate(podata);
           assertTrue(client.getIsFutureShip());
           System.out.println(client.getFutureShipDate());
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
