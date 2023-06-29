package com.owd.jobs.archives;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.Inventory;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

//import com.sun.mail.pop3.*;





public class CUTImporter extends OWDStatefulJob
{
private final static Logger log =  LogManager.getLogger();

	CUTImportReport report = null;

	

	static final String[] orderColumns = {"INVOICE NUMBER",			//0

									"ACCOUNT NUMBER",

									"INVOICE DATE",

									"FIRST BILL-TO",

									"SECOND BILL-TO",

									"THIRD BILL-TO",			//5

									"FOURTH BILL-TO",

									"FIRST SHIP-TO",

									"SECOND SHIP-TO",

									"THIRD SHIP-TO",

									"FOURTH SHIP-TO",		//10

									"SHIP-VIA",

									"POSTAGE & HANDLING",

									"TEXT NOTE",

									"ORDER TAKER",

									"PHONE NUMBER",			//15

									"QUANTITY",

									"ITEM #",

									"DESCRIPTION",

									"UNIT PRICE",

									"DISCOUNT AMT.",			//20

									"EXTENSION",

									"GIFT",

									"INSURE AMT",				//23

									"KIT SKU"

									};



	private static final int kInOrder = 1;

	private static final int kNotInOrder = 0;

	String orderDesc = "";



	public void checkForOrders(String clientID)

	{

		

		try{

			Properties props = new Properties();

			props.put("mail.smtp.host", OWDConstants.SMTPServer);

			

			URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "cutorders@owd.com", "moneycut1");

			

			

			javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props,null);

			mailSession.setDebug(false);

			Store popStore = mailSession.getStore(url);

			

			popStore.connect();

			Folder inbox = popStore.getDefaultFolder();

			if (inbox == null) 

		   		 throw new MessagingException("No default folder");

		

			inbox = inbox.getFolder("INBOX");

			if (inbox == null)

		    		throw new MessagingException("Invalid folder");

		

			inbox.open(Folder.READ_WRITE);

			

			javax.mail.Message[] messages = {};

			int count = inbox.getMessageCount();

			//////log.debug("pop3 got "+count+" messages...");

			if(count >0)

				messages = inbox.getMessages();

				

			
			StringBuffer sbr = new StringBuffer();
			

			for(int i=0;i<messages.length;i++)

			{

				try

				{

					String subject = messages[i].getSubject();

					//log.debug("CUT:got message: "+subject);
//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");
						
					

					javax.mail.internet.MimeMessage message = (javax.mail.internet.MimeMessage) messages[i];
					//log.debug("got javax.mail.Message content="+message.getContentType());

//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");				

					

					javax.mail.Multipart mp = (javax.mail.Multipart)(((javax.mail.internet.MimeMessage)message).getContent()); 

					//log.debug("got message content");
//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");
					int parts = mp.getCount();


					//log.debug("got MimeMessage, partcount="+parts+", content-type="+mp.getContentType()+", subject="+subject);

//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");					

					for(int j=0;j< parts;j++)

					{
						//log.debug("getting message bodypart for part "+j);
						
						try
						{
						MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j); 

						//log.debug("got message bodypart for part "+j+" type:"+part.getContentType());

//	Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");					

						InputStream in = null;

						

						if (part.getContentType().indexOf("binhex")>=0)

						{

							in = new BinHex4InputStream(part.getInputStream());

							//log.debug(((BinHex4InputStream)in).getHeader());

						}else{

							in = part.getInputStream();

						}

						

						//log.debug("got inputstream");
//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");
						//log.debug("got part "+j+" , encoding="+part.getEncoding()+", content-type="+part.getContentType()+" , description="+part.getDescription()+" , disposition="+part.getDisposition()+", size="+part.getSize()+", name="+part.getFileName());

	//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");					


						  

						DelimitedReader orders = new TabReader(new BufferedReader( new InputStreamReader(in)),true);
for (int orderRow=0;orderRow < orders.getRowCount();orderRow++)

{
//log.debug("got cols="+orders.getRowSize(orderRow)+" for row "+orderRow);

for (int c=0;c < orders.getRowSize(orderRow);c++)

{
//log.debug(":"+orders.getStrValue(c,orderRow,"xxx")+":");

}

}

		//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");				

						//log.debug("got orders "+orders.getRowCount());
						
						//Mailer.sendMail("CUTImporter report",sbr.toString(),"noop@owd.com","webmaster@owd.com");
					//	if(true)
					//	throw new Exception("Done with test");

						report = new CUTImportReport(part.getFileName());

						

						Vector orderrefs = new Vector();

						for (int orderRow=0;orderRow < orders.getRowCount();orderRow++)

						{

							//log.debug("got cols="+orders.getRowSize(orderRow)+" for row "+orderRow);

							if(orders.getRowSize(orderRow)<2)

							{

								//blank row

							}else

							{

								if(orders.getRowSize(orderRow)<24)

								{

									report.addError("",orderRow+"",CUTImportReport.kErrBadColCount);

								}else

								{

									if(!(orderrefs.contains(orders.getStrValue(0,orderRow,""))))

									{

										orderrefs.addElement(orders.getStrValue(0,orderRow,""));

									}

									if(orders.getFloatValue(19,orderRow,0) > 0 || orders.getIntValue(16,orderRow,0)>0 || (!(orders.getStrValue(17,orderRow,".").equals(".")) && orders.getStrValue(17,orderRow,"").length() > 0))

									{

										report.itemsFound++;

									}

								}

							}

						}

						

						report.ordersFound = orderrefs.size();

						

						

						Order anOrder = new Order("56");

						orderDesc = "";

						int currentState = kNotInOrder;

						Vector orderItems = new Vector();

						Vector orderLines = new Vector();

						

						for (int orderRow=0;orderRow < orders.getRowCount();orderRow++)

						{

							if(orders.getRowSize(orderRow) > 5)

							{

							try{

							//log.debug("\nCUT import message "+i+", part "+j+" line "+orderRow+", cols="+orders.getRowSize(orderRow));

							

							if(orders.getStrValue(0,orderRow,"invalid").equals(anOrder.order_refnum) )

							{

									//add to current order

									//log.debug("adding to order");

									addOrderItem(anOrder,orderItems,orders,orderRow);



							}else{

								if(orderRow != 0)

								{

									//save current order

									//log.debug("saving to order");

									saveOrderItems(anOrder, orderItems);

									if(orderDesc.length() > 0 && anOrder.skuList.size() > 0)

									{

										((LineItem)anOrder.skuList.elementAt(anOrder.skuList.size()-1)).long_desc = ((LineItem)anOrder.skuList.elementAt(anOrder.skuList.size()-1)).long_desc +"\r\rNOTE:\r"+orderDesc;

						

									}

									String reference = anOrder.saveNewOrder(OrderUtilities.kHoldForPayment,false);

							

									if(anOrder.last_error.indexOf("reference already exists")>=0)

									{

										report.addError(anOrder.old_refnum,"N/A",CUTImportReport.kErrDuplicate);

										messages[i].setFlag(Flags.Flag.DELETED,true);

									}

									else

									{

										if(reference == null)

										{

											//order not saved

											//log.debug("order not saved");

											Mailer.sendMail("CUTImporter import order not saved("+anOrder.old_refnum+")",anOrder.last_error,"noop@owd.com","do-not-reply@owd.com");

										}else{

											report.ordersImported++;

											for (int s =0;s<anOrder.skuList.size();s++)

											{

												if(new Integer(((LineItem)anOrder.skuList.elementAt(s)).inventory_fkey).intValue() < 1)

												{

													report.addError(anOrder.order_refnum,"N/A",CUTImportReport.kErrSKUNotInInventory);

												}

												

												report.itemsImported++;

											}

											if(anOrder.is_future_ship == 1)

											{

												//order on hold

												//////////log.debug("order on hold");

										

											Mailer.sendMail("CUTImporter import order on hold("+anOrder.order_refnum+")",anOrder.hold_reason,"noop@owd.com","do-not-reply@owd.com");

											}

											if(anOrder.backorder_order_num != null)

											{

												//order on hold

												//////////log.debug("order backordered");

										

											Mailer.sendMail("CUTImporter import backordered (CUT:"+anOrder.order_refnum+")","OWD ref: ("+anOrder.orderNum+")","noop@owd.com","do-not-reply@owd.com");

											}

										}

									}

								}

									

									//start new order

									//log.debug("new order");

									orderItems = new Vector();

									orderLines = new Vector();

									

									anOrder = new Order("56");

									//anOrder.testing = true;

									orderDesc = "";

									anOrder.po_num = orders.getStrValue(1,orderRow,"");

									anOrder.actual_order_date = orders.getStrValue(2,orderRow,OWDUtilities.getSQLDateForToday());

									anOrder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

									anOrder.backorder_rule = OrderXMLDoc.kBackOrderAll;

									anOrder.order_refnum =  orders.getStrValue(0,orderRow,"");

										

									anOrder.setBillingAddress	(

																parseCUTAddress(

																	orders.getStrValue(3,orderRow,""),

																	orders.getStrValue(4,orderRow,""),

																	orders.getStrValue(5,orderRow,""),

																	orders.getStrValue(6,orderRow,"")

																)

															);

															

									anOrder.getShippingInfo().setShippingAddress	(

																				parseCUTAddress(

																					orders.getStrValue(7,orderRow,""),

																					orders.getStrValue(8,orderRow,""),

																					orders.getStrValue(9,orderRow,""),

																					orders.getStrValue(10,orderRow,"")

																				)

																			);								

									

									anOrder.setBillingContact(new com.owd.core.business.Contact(

																	orders.getStrValue(3,orderRow,""),

																	orders.getStrValue(15,orderRow,""),"","",""));

																	

									anOrder.getShippingInfo().setShippingContact(new com.owd.core.business.Contact(

																	orders.getStrValue(7,orderRow,""),

																	orders.getStrValue(15,orderRow,""),"","",""));							

									

									//shipping type

									String shipType = orders.getStrValue(11,orderRow,"");

									if(shipType.equals("Special Standard"))

										shipType = "USPS Package Services";

									else if(shipType.equals("First Class Priority"))

										shipType = "Priority Mail";

									else if(shipType.equals("Int\'l Express Mail"))

										shipType = "USPS Int\'l Express Mail";

									else if(shipType.equals("Air Other Sm Packet"))

										shipType = "Int\'l Letter-Post/Sm Packet Air";

									else if(shipType.equals("Intl. Surface Mail"))

										shipType = "USPS Int\'l Letter-Post Surface";

									else if(shipType.equals("Air Mail"))

										shipType = "USPS Int\'l Parcel Post Air";

									else

									{

										

									}

                                //log.debug("shiptype="+shipType);

									try{	

										anOrder.getShippingInfo().setShipOptions(shipType,"Prepaid","");

									}catch(Exception ex)

									{

										anOrder.is_future_ship = 1;

										report.addError(anOrder.order_refnum,orderRow+"",CUTImportReport.kErrShippingInvalid);

									}

									

									float insuredFor = orders.getFloatValue(23,orderRow,0);

									if(insuredFor > 0)

									{

										anOrder.getShippingInfo().ss_declared_value = "1";

										anOrder.getShippingInfo().declared_value = String.valueOf(insuredFor);

									}

									

									anOrder.getShippingInfo().comments = orders.getStrValue(13,orderRow,"");

									

									if("Y".equals(orders.getStrValue(22,orderRow,"")))

									{

										anOrder.is_gift = "1";

										anOrder.prt_invoice_reqd = 0;

										anOrder.prt_pack_reqd = 1;

										if(anOrder.getShippingInfo().comments.indexOf("Paid")<0)

											anOrder.gift_message = anOrder.getShippingInfo().comments;

									}else

									{

										anOrder.prt_invoice_reqd = 1;

										anOrder.prt_pack_reqd = 0;

									}

									anOrder.order_type="Web Download";

									anOrder.total_shipping_cost = orders.getFloatValue(12,orderRow,0);

									anOrder.ship_operator = orders.getStrValue(14,orderRow,"");

									

									anOrder.ship_call_date =  OWDUtilities.getSQLDateForToday();	

									

									addOrderItem(anOrder,orderItems,orders,orderRow);

									

							}

						

					}catch(Exception ex)

					{

					

						ex.printStackTrace();

						StringBuffer sb = new StringBuffer();

						String stamper = OWDUtilities.getSQLDateTimeForToday();

						sb.append("\nException: "+ex.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");

						//log.debug("CUTImporter "+sb);

						ex.printStackTrace();

						//rowErrors.append(orders.getRawRow(orderRow));

						

					}

						}

						} //for each line in orders file

						

						

						try{

								if(!("".equals(anOrder.order_refnum)))

								{

									//save current order

									//log.debug("saving to order");

									saveOrderItems(anOrder, orderItems);

									

									if(orderDesc.length() > 0 && anOrder.skuList.size() > 0)

									{

										((LineItem)anOrder.skuList.elementAt(anOrder.skuList.size()-1)).long_desc = ((LineItem)anOrder.skuList.elementAt(anOrder.skuList.size()-1)).long_desc +"\r\rNOTE:\r"+orderDesc;

						

									}
									
									if(anOrder.getShippingAddress().country.toUpperCase().indexOf("GHANA")>=0)
									{
										anOrder.is_future_ship=1;
									}
									
									if(anOrder.getShippingAddress().country.toUpperCase().indexOf("GUATEMALA")>=0)
									{
										anOrder.is_future_ship=1;
									}
									
									if(anOrder.getShippingAddress().country.toUpperCase().indexOf("GUYANA")>=0)
									{
										anOrder.is_future_ship=1;
									}

									String reference = anOrder.saveNewOrder(OrderUtilities.kHoldForPayment,false);

							

									if(anOrder.last_error.indexOf("reference already exists")>=0)

									{

										report.addError(anOrder.old_refnum,"N/A",CUTImportReport.kErrDuplicate);

										messages[i].setFlag(Flags.Flag.DELETED,true);

									}

									else

									{

										if(reference == null)

										{

											//order not saved

											//////////log.debug("order not saved");

											Mailer.sendMail("CUTImporter import order not saved (CUT:"+anOrder.old_refnum+")",anOrder.last_error,"noop@owd.com","noop@owd.com");

										}else{

											report.ordersImported++;

											

											for (int s =0;s<anOrder.skuList.size();s++)

											{

												if(new Integer(((LineItem)anOrder.skuList.elementAt(s)).inventory_fkey).intValue() < 1)

												{

													report.addError(anOrder.order_refnum,"N/A",CUTImportReport.kErrSKUNotInInventory);

													

												}

												report.itemsImported++;

											}

											if(anOrder.is_future_ship == 1)

											{

												//order on hold

												//////////log.debug("order on hold");

										

											Mailer.sendMail("CUTImporter import order on hold("+anOrder.order_refnum+")",anOrder.hold_reason,"noop@owd.com","noop@owd.com");

											}

											if(anOrder.backorder_order_num != null)

											{

												//order on hold

												//////////log.debug("order backordered");

										

											Mailer.sendMail("CUTImporter import backordered (CUT:"+anOrder.order_refnum+")","OWD ref: ("+anOrder.orderNum+")","noop@owd.com","do-not-reply@owd.com");

											}

										}

									}

									

								}

					}catch(Exception ex)

					{

					

						ex.printStackTrace();

						StringBuffer sb = new StringBuffer();

						String stamper = OWDUtilities.getSQLDateTimeForToday();

						sb.append("\nException: "+ex.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");

						//log.debug("CUTImporter stamper="+sb);

						ex.printStackTrace();

						Mailer.sendMail("CUTImporter import error at end",sb.toString(),"noop@owd.com","do-not-reply@owd.com");

					}

						String[] reportees = {"JBoeckman@tsl.org","parnold@tsl.org","sherry@owd.com"};
						
						

						report.sendReport(reportees);
}catch(Exception ex2)

				{
					ex2.printStackTrace();

					StringBuffer sb = new StringBuffer();

					String stamper = OWDUtilities.getSQLDateTimeForToday();

					sb.append("\nException: "+ex2.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");

					//////////log.debug("CUTImporter stamper="+stamper);

					ex2.printStackTrace();

					Mailer.sendMail("CUTImporter bodypart import error",sb.toString(),"noop@owd.com","do-not-reply@owd.com");
				}
					} //for each MIME part

				messages[i].setFlag(Flags.Flag.DELETED,true);

				}catch(Exception ex)

				{

					

					ex.printStackTrace();

					StringBuffer sb = new StringBuffer();

					String stamper = OWDUtilities.getSQLDateTimeForToday();

					sb.append("\nException: "+ex.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");

					//////////log.debug("CUTImporter stamper="+stamper);

					ex.printStackTrace();

					Mailer.sendMail("CUTImporter import error",sb.toString(),"noop@owd.com","do-not-reply@owd.com");

				}
				
				

			}

			

			

			inbox.close(true);

		

		

		}catch(NoSuchProviderException ex)

		{

			ex.printStackTrace();

					StringBuffer sb = new StringBuffer();

					String stamper = OWDUtilities.getSQLDateTimeForToday();

					sb.append("\nException: "+ex.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");

					//////////log.debug("CUTImporter stamper="+stamper);

					ex.printStackTrace();

					try{Mailer.sendMail("CUTImporter import error",sb.toString(),"noop@owd.com","do-not-reply@owd.com");
					}catch(Exception exm){exm.printStackTrace();}

		}catch(Exception ex)

		{

			ex.printStackTrace();

					StringBuffer sb = new StringBuffer();

					String stamper = OWDUtilities.getSQLDateTimeForToday();

					sb.append("\nException: "+ex.getMessage()+"\n\nStacktrace ID: "+stamper+"\n\n");

					//////////log.debug("CUTImporter stamper="+stamper);

					ex.printStackTrace();
try{
					Mailer.sendMail("CUTImporter import error",sb.toString(),"noop@owd.com","do-not-reply@owd.com");
}catch(Exception exm){exm.printStackTrace();}
		}

	}

	

	private void addOrderItem(Order iOrder, Vector items, DelimitedReader rows, int currentRow)

	{

		String[] itemData = {"","0","0","0","","","",""};

		

		if(items.size()>0)

		{

			//not first item in order

			if(rows.getStrValue(17,currentRow,"").equals("."))

			{

				//is a comment

				((String[])items.elementAt(items.size()-1))[4] = ((String[])items.elementAt(items.size()-1))[4]+" "+rows.getStrValue(18,currentRow,"");

				if(((String[])items.elementAt(items.size()-1))[4].length() > 253)

				{

					((String[])items.elementAt(items.size()-1))[7] = ((String[])items.elementAt(items.size()-1))[4];

					((String[])items.elementAt(items.size()-1))[4] = ((String[])items.elementAt(items.size()-1))[4].substring(0,253);

				}else{

					((String[])items.elementAt(items.size()-1))[7] = ((String[])items.elementAt(items.size()-1))[4];

				}

						

			}else

			{

				//not a comment

				itemData[0] = rows.getStrValue(17,currentRow,"");

				itemData[1] = rows.getStrValue(16,currentRow,"0");

				itemData[2] = rows.getStrValue(19,currentRow,"0");

				itemData[3] = rows.getStrValue(21,currentRow,"0");

				itemData[4] = rows.getStrValue(18,currentRow,"");

				itemData[6] = rows.getStrValue(24,currentRow,"");

				if(itemData[4].length()> 253)

				{

					itemData[7] = itemData[4];

					itemData[4] = itemData[4].substring(0,253);

				}else{

					itemData[7] = itemData[4];

				}

				items.addElement(itemData);

			}

		}else{

			//first item in order

			if((rows.getStrValue(17,currentRow,"").equals(".")))

			{

				//is a comment

				orderDesc = orderDesc+ rows.getStrValue(18,currentRow,"");

			}else

			{

				//not a comment

				itemData[0] = rows.getStrValue(17,currentRow,"");

				itemData[1] = rows.getStrValue(16,currentRow,"0");

				itemData[2] = rows.getStrValue(19,currentRow,"0");

				itemData[3] = rows.getStrValue(21,currentRow,"0");

				itemData[4] = rows.getStrValue(18,currentRow,"");

				itemData[6] = rows.getStrValue(24,currentRow,"");

				if(itemData[4].length()> 253)

				{

					itemData[7] = itemData[4];

					itemData[4] = itemData[4].substring(0,253);

				}else

				{

					itemData[7] = itemData[4];

				}
//log.debug("found item "+itemData[0]+","+itemData[1]+","+itemData[2]+","+itemData[3]+","+itemData[4]+","+itemData[5]+","+itemData[6]);
				items.addElement(itemData);

			}

		}

		

		if(rows.getIntValue(16,currentRow,1) < 1 & !(rows.getStrValue(17,currentRow,"").equals(".")))

		{

			report.addError(iOrder.order_refnum,currentRow+"",CUTImportReport.kErrSKUQuantityBad);

		}

		

		

		

		if((rows.getFloatValue(19,currentRow,0) >= 0.01) && rows.getStrValue(17,currentRow,".").equals("."))

		{

			report.addError(iOrder.order_refnum,currentRow+"",CUTImportReport.kErrSKUNotFound);

		}

		

	

	}



	private void saveOrderItems(Order iOrder, Vector items) throws Exception

	{

		for(int index=0;index<items.size();index++)

		{

			boolean tryagain = false;

			String[] itemData = (String[]) items.elementAt(index);

			//make sure kit SKU exists

			if(itemData[6].equals(itemData[0]) && itemData[0].length() > 0 && itemData[0].endsWith("-KIT"))

			{

				itemData[0] = "KIT-"+itemData[0].substring(0,itemData[0].indexOf("-KIT"));
				
				//it's a kit SKU

				Inventory item = null;

				if(LineItem.SKUExists("56",itemData[0]))

				{

					//we have the SKU already

					item = Inventory.dbloadByPart(itemData[0],"56");

				}else

				{

					//create Inventory item 

					item = new Inventory("56");

					item.inventory_num = itemData[0];

					item.description = itemData[4];

					item.price = new Float(itemData[2]).floatValue();

					

				}

				//verify inventory level (pseudo)

				java.sql.Connection cxn = null;

				try

				{

				

				cxn = ConnectionManager.getConnection();

				

				if(item.isNew())

					item.dbsave(cxn);

				

				item.addToAvailabilityAtFacility(cxn, new Integer(itemData[1]).intValue(), FacilitiesManager.getLocationCodeForClient(Integer.parseInt(iOrder.clientID)));

				

				cxn.commit();

				

				}catch(Exception ex)

				{

					if(ex.getMessage().indexOf("lock") >= 0)

					{

						tryagain=true;

					}else

					{

						report.addError(iOrder.order_refnum,itemData[6],"Couldn't update kit SKU");

					}

					

				}finally

				{

					try{cxn.close();}catch(Exception ex2){}

				}

				

			}

			if(!tryagain)

			{
			
				//fix to allow zero-value items
				try
				{
					float val = new Float(itemData[3]).floatValue();
					if (val == 0.00f)
					{
						itemData[3] = "-1";
					}
				
				}catch(Exception ex)
				{
					itemData[3] = "0.00";
				}
				
				//log.debug("trying to add item "+itemData[0]+","+itemData[1]+","+itemData[2]+","+itemData[3]+","+itemData[4]+","+itemData[5]+","+itemData[6]);
				iOrder.addLineItem(itemData[0],itemData[1],itemData[2],itemData[3],itemData[4],itemData[5],itemData[6]);

				if(iOrder.skuList.size()>0)

				{

					((LineItem)iOrder.skuList.elementAt(iOrder.skuList.size()-1)).long_desc = itemData[7];

				}

			}else

			{

				index--;

			}

		}

	}



	private static com.owd.core.business.Address parseCUTAddress(String line1, String line2, String line3, String line4)

	{

		com.owd.core.business.Address addr = new com.owd.core.business.Address();

		

		if(line4.indexOf(",")>= 0)

		{

			//work back

			addr.city = line4.substring(0,line4.indexOf(","));

			

			if (line4.indexOf(",") < (line4.length()-1))

				line4 = line4 = line4.substring(line4.indexOf(",")+1).trim();

			else

				line4 = line4.substring(line4.indexOf(",")).replace(',',' ').trim();

						

						

			if(line4.indexOf(",")>= 0)

			{

				addr.zip = line4.substring(0,line4.indexOf(",")).trim();

				if (line4.indexOf(",") < (line4.length()-1))

					addr.country  = line4.substring(line4.indexOf(",")+1).trim().toUpperCase();

				else

					addr.country  = "USA";

			}else

			{

				 if(line4.indexOf(" ")==2)

				{

					addr.state = line4.substring(0,line4.indexOf(" ")).trim();

					

				}else if(line4.length()>0)

				{

					addr.state = line4.trim();

				}

				

				if (line4 != null)

					line4 = line4.trim();

				

				if (line4.indexOf(" ") < (line4.length()-1))

					addr.zip = line4.substring(line4.indexOf(" ")+1).trim();

			}

			

			if (line2 != null)

				addr.address_one = line2;

			

			if (line3 != null)

				addr.address_two = line3;

			

		}else{

			if(line3.indexOf(",")>=0)

			{

				addr.city = line3.substring(0,line3.indexOf(","));

			

				if (line3.indexOf(",") < (line3.length()-1))

					line3 = line3.substring(line3.indexOf(",")+1).trim();

				else

					line3 =  line3.substring(line3.indexOf(",")).replace(',',' ').trim();

			

			

				if(line3.indexOf(",")>=0)

				{

					addr.state = line3.substring(0,line3.indexOf(",")).trim();

					if (line3.indexOf(",") < (line3.length()-1))

						line3 = line3 = line3.substring(line3.indexOf(",")+1).trim();

					else

						line3 = line3.substring(line3.indexOf(",")).replace(',',' ').trim();

				}else if(line3.indexOf(" ")==2)

				{

					addr.state = line3.substring(0,line3.indexOf(" "));

				}else if(line3.length()>0)

				{

					addr.state = line3.trim();

				}

			

			}

			

			if (line3 != null)

				line3 = line3.trim();

				

			if (line3.indexOf(" ") < (line3.length()-1))

				addr.zip = line3.substring(line3.indexOf(" ")+1).trim();

			

			if (line2 != null)

				addr.address_one = line2.trim();

			

			if (line4.length() > 1)

			{

				addr.country = line4.toUpperCase().trim();

			}	

		}

		

		return addr;

	}



    public void internalExecute() {
           checkForOrders("56");

    }

	static public void main(String[] args)

	{

		
/*
		//log.debug(parseCUTAddress("Consuelo Mouzquiz","Rio  San Lorenzo 408 Col. del Valle","Garza Garcia , Nuevo Leon,","Mexico"));

		//log.debug(parseCUTAddress("Bo Maas","Groen van Prinstererstraat 15b","Rotterdam,","Netherlands"));

		//log.debug(parseCUTAddress("Edmonton Tsl","%Pieter Graafland","1605-8610 Jasper Ave.","Edmonton, AB  T5H 3S5, Canada"));

		//log.debug(parseCUTAddress("Reflections - Bookshop & Resource Ctr","Ruby T Ong/15F Siki Center","23 Jardine's Bazaar","Causeway Bay,, Hong Kong"));

		//log.debug(parseCUTAddress("V M DeLoach","1400 Crete Court","Unit C","Lafayette, CO  80026"));

		//log.debug(parseCUTAddress("Michael  Gary","204 Pine Hill Rd.","Sterling, CT  06377",""));


*/
run();

		

		

	}

	

public class CUTImportReport

{

	

	final static public String kErrBadColCount = "Wrong number of columns in row";

	final static public String kErrSKUNotFound = "No SKU was found";

	final static public String kErrSKUNotInInventory = "SKU was not found in inventory";

	final static public String kErrShippingInvalid = "Shipping type invalid";

	final static public String kErrSKUQuantityBad = "SKU quantity < 1";

	final static public String kErrNoItemsInOrder = "No valid items in order";

	final static public String kErrOrderRefExists = "Order already imported";

	final static public String kErrDescTooLong = "SKU description > 255 characters";

	final static public String kErrDescNoSKU = "Description found w/o SKU";

	final static public String kErrUnknown = "An internal error occurred";

	final static public String kErrDuplicate = "Order already imported";

	

	public int ordersFound = 0;

	public int ordersImported = 0;

	public int itemsFound = 0;

	public int itemsImported = 0;

	

	Vector errors = new Vector();

	

	String sourceName = null;

	

	public CUTImportReport(String source)

	{

		sourceName = source;

	}

	

	public void addError( String orderRef,String rowNumber,String errorDesc)

	{

		errors.addElement(OWDUtilities.padRight(orderRef,16)+OWDUtilities.padRight(rowNumber,10)+errorDesc);

	}



	public void sendReport(String[] addressees)

	{

		StringBuffer sb = new StringBuffer();

		

		sb.append("Summit University Press Import Report\n\n");

		sb.append("Import filename: "+sourceName+"\n");

		sb.append("Imported at: "+OWDUtilities.getSQLDateTimeForToday()+"\n\n");

		sb.append("         Found in file-Imported\n");

		sb.append("         --------------------------------------\n");

		sb.append("Orders : "+ordersFound+"-"+ordersImported+"\n");

		sb.append("SKUs   : "+itemsFound+"-"+itemsImported+"\n\n");

		

		sb.append("ERRORS\n--------\n\n");

		for(int i=0;i<errors.size();i++)

		{

			sb.append((1+i)+". "+(String)errors.elementAt(i)+"\n");

		}

		

		if(sourceName != null && addressees != null)

		{

			//log.debug(sb);

			try{

			Mailer.sendMail("CUT Import Report",sb.toString(),addressees,"do-not-reply@owd.com");

			}catch( Exception ex)

			{

			

			}

		}

	}







}

	

}

