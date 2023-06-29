package com.owd.extranet.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class InventoryFinder

{
private final static Logger log =  LogManager.getLogger();





	String[] tableColumnNames = {"","ID","Item&nbsp;(SKU)","Title","Available","Backordered","Price","Supplier"};

	String[] tableLinkStarts = {ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kInvMgrName+"&"+InventoryManager.kParamInvMgrAction+"="+InventoryManager.kParamInvEditAction+"&"+InventoryManager.kparamInvID+"=","","","","","","",""};

	String[] tableLinkEnds = {"Edit</A>","","","","","","",""};

	int[] tableLinkMids = {1,0,0,0,0,0,0,0};

	int[] tableRenders = {WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderMoney,WebTable.kRenderString};

	String[] tableColumnDefs = {"inventory_id","inventory_id","inventory_num","description","qty_on_hand","backordered","price","mfr_part_num","case_qty"};

	String tableFromStmt1 = "from vw_a1";


	

	public InventoryFinder()

	{

	

	}

	

	public static final String kBlank = "";

	

	private String sku = null;

	private String count = null;

	private String title = null;

	private String supplier = null;

	private String back = null;

	private String inactive = null;

	private String invID = null;

	private String caseqty = null;

	

	private int skuType = 1;

	private int countType = 1;

	private int titleType = 1;

	private int supplierType = 1;

	private int backType = 1;

	private int inactiveType = 1;

	private int invIDType = 1;

	

	private static final int kIsType = 1;

	private static final int kInType = 2;

	private static final int kGreaterThanType = 3;

	private static final int kLessThanType = 4;

	

	public static final String kSKUSearchFlag = "dosku";

	public static final String kSKUSearchValue = "skuvalue";

	public static final String kSKUSearchType = "skutype";



	public static final String kInvIDSearchFlag = "doiid";

	public static final String kInvIDSearchValue = "iidvalue";

	public static final String kInvIDSearchType = "iidtype";

	

	public static final String kBackSearchFlag = "doback";

	public static final String kBackSearchValue = "backvalue";

	public static final String kBackSearchType = "backtype";

	

	public static final String kCountSearchFlag = "docount";

	public static final String kCountSearchValue = "countvalue";

	public static final String kCountSearchType = "counttype";

	

	public static final String kTitleSearchFlag = "dotitle";

	public static final String kTitleSearchValue = "titlevalue";

	public static final String kTitleSearchType = "titletype";



	public static final String kInactiveSearchFlag = "doinactive";

	public static final String kInactiveSearchType = "inactivetype";

	public static final String kCaseQtySearchFlag = "docaseqty";

		

	public static final String kSupplierSearchFlag = "dosupplier";

	public static final String kSupplierSearchValue = "suppliervalue";

	public static final String kSupplierSearchType = "suppliertype";



	public int getTitleType()

	{

		return titleType;

	}



	public int getInvIDType()

	{

		return invIDType;

	}

	

	public int getBackType()

	{

		return backType;

	}

		

		public int getSupplierType()

	{

		return supplierType;

	}

	

		public int getCountType()

	{

		return countType;

	}	

	public int getSKUType()

	{

		return skuType;

	}



	public int getInactiveType()

	{

		return inactiveType;

	}

	

	public static InventoryFinder parseRequest(HttpServletRequest req, HttpServletResponse resp)

	{

		InventoryFinder finder = new InventoryFinder();

		

		if(ExtranetServlet.getIntegerParam(req,kSKUSearchFlag,0)==1)

		{

		//skuSearch

			finder.setSKU(ExtranetServlet.getStringParam(req,kSKUSearchValue,""),ExtranetServlet.getIntegerParam(req,kSKUSearchType,1));

		

		}

		

		if(ExtranetServlet.getIntegerParam(req,kCountSearchFlag,0)==1)

		{

		//countSearch

			finder.setCount(kBlank+ExtranetServlet.getIntegerParam(req,kCountSearchValue,0),ExtranetServlet.getIntegerParam(req,kCountSearchType,1));

		}		

		

		if(ExtranetServlet.getIntegerParam(req,kTitleSearchFlag,0)==1)

		{

		//titleSearch

			finder.setTitle(ExtranetServlet.getStringParam(req,kTitleSearchValue,""),ExtranetServlet.getIntegerParam(req,kTitleSearchType,1));

		

		}

		

		if(ExtranetServlet.getIntegerParam(req,kSupplierSearchFlag,0)==1)

		{

		//skuSearch

			finder.setSupplier(ExtranetServlet.getStringParam(req,kSupplierSearchValue,""),ExtranetServlet.getIntegerParam(req,kSupplierSearchType,1));

		

		}		



		if(ExtranetServlet.getIntegerParam(req,kBackSearchFlag,0)==1)

		{

		//skuSearch

			finder.setBack(ExtranetServlet.getStringParam(req,kBackSearchValue,""),ExtranetServlet.getIntegerParam(req,kBackSearchType,1));

		

		}		



		if(ExtranetServlet.getIntegerParam(req,kInvIDSearchFlag,0)==1)

		{

		//inventory id Search

			finder.setInvID(ExtranetServlet.getStringParam(req,kInvIDSearchValue,""),ExtranetServlet.getIntegerParam(req,kInvIDSearchType,1));

		

		}

		

		if(ExtranetServlet.getIntegerParam(req,kInactiveSearchFlag,0)==1)

		{

		//skuSearch

			finder.setInactive("",ExtranetServlet.getIntegerParam(req,kInactiveSearchType,1));

		

		}

		if(ExtranetServlet.getIntegerParam(req,kCaseQtySearchFlag,0)==1)

		{

			//skuSearch

			finder.setCaseQtySearch();



		}





		return finder;

	}

	

	public void setBack(String value, int findType)

	{

		if(value==null) return;

		

		back = value;

		backType =findType;

	}

		public void setSKU(String value, int findType)

	{

		if(value==null) return;

		

		sku = value;

		skuType =findType;

	}



		public void setInvID(String value, int findType)

	{

		if(value==null) return;

		

		invID = value;

		invIDType =findType;

	}

	public void setCaseQtySearch(){
		caseqty = "1";
	}

		public void setInactive(String value, int findType)

	{

		if(value==null) return;

		

		inactive = value;

		inactiveType =findType;

	}



	public void setCount(String value, int findType)

	{

		if(value==null) return;

		

		count = value;

		countType =findType;

	}



	public void setTitle(String value, int findType)

	{

		if(value==null) return;

		

		title = value;

		titleType =findType;

	}



	public void setSupplier(String value, int findType)

	{

		if(value==null) return;

		

		supplier = value;

		supplierType =findType;

	}



	public String getSKU()

	{

		if(isSKUSearch())

			return sku;

		else

			return kBlank;

	}



	public String getInvID()

	{

		if(isInvIDSearch())

			return invID;

		else

			return kBlank;

	}





	public String getBack()

	{

		if(isBackSearch())

			return back;

		else

			return kBlank;

	}

	

	public String getCount()

	{

		if(isCountSearch())

			return count;

		else

			return kBlank;

	}



	public String getTitle()

	{

		if(isTitleSearch())

			return title;

		else

			return kBlank;

	}



	public String getInactive()

	{

		if(isInactiveSearch())

			return inactive;

		else

			return kBlank;

	}

	

	public String getSupplier()

	{

		if(isSupplierSearch())

			return supplier;

		else

			return kBlank;

	}



	public boolean isBackSearch()

	{

		if(back == null)

			return false;

		else

			return true;

	}

		

	public boolean isSKUSearch()

	{

		if(sku == null)

			return false;

		else

			return true;

	}



	public boolean isInvIDSearch()

	{

		if(invID == null)

			return false;

		else

			return true;

	}

		

	public boolean isCountSearch()

	{

		if(count == null)

			return false;

		else

			return true;

	}



	public boolean isTitleSearch()

	{

		if(title == null)

			return false;

		else

			return true;

	}

	

	public boolean isSupplierSearch()

	{

		if(supplier == null)

			return false;

		else

			return true;

	}

	
	public boolean isCaseQtySearch(){
		if(caseqty == null){
			return false;
		}else{
			return  true;
		}
	}
	public boolean isInactiveSearch()

	{

		if(inactive == null)

			return false;

		else

			return true;

	}

	

		

	public String showResults(HttpServletRequest req,HttpServletResponse resp) throws IOException

	{

		

		

		WebTable table = new WebTable();

		

		table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableRenders,tableFromStmt1);

					

		table.addCriterium("client_fkey="+ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));

		

	//	table.setGroupBy("inventory_id");

		

		if(isSKUSearch())

		{

			switch(skuType)

			{

				case kIsType:

					table.addCriterium("inventory_num = \""+sku+"\"");

					break;

				case kInType:

					table.addCriterium("inventory_num like \"%"+sku+"%\"");

					break;

				case kGreaterThanType:

					table.addCriterium("inventory_num > \""+sku+"\"");

					break;

				case kLessThanType:

					table.addCriterium("inventory_num < \""+sku+"\"");

					break;

			

			default:

				table.addCriterium("inventory_num = \""+sku+"\"");

				skuType = kIsType;

			}

		}



		if(isCountSearch())

		{

			switch(countType)

			{

				case kIsType:

					table.addCriterium("(qty_on_hand = "+count+")");

					break;

				case kInType:

					table.addCriterium("(qty_on_hand = "+count+")");

					countType = kIsType;

					break;

				case kGreaterThanType:

					table.addCriterium("(qty_on_hand > "+count+")");

					break;

				case kLessThanType:

					table.addCriterium("(qty_on_hand < "+count+")");

					break;

			

			default:

				table.addCriterium("(qty_on_hand = "+count+")");

				countType = kIsType;

			}

		}



		if(isInvIDSearch())

		{

			

			try

			{

				double dtest = new Double(invID).doubleValue();

				table.addCriterium("inventory_id = "+invID);

			}catch(Exception ex)

			{

				table.addCriterium("inventory_id = 0");

			}

				

				invIDType = kIsType;

			

		}

		

		if(isTitleSearch())

		{

			switch(titleType)

			{

				case kIsType:

					table.addCriterium("description = \""+title+"\"");

					break;

				case kInType:

					table.addCriterium("description like \"%"+title+"%\"");

					break;

				case kGreaterThanType:

					table.addCriterium("description > \""+title+"\"");

					break;

				case kLessThanType:

					table.addCriterium("description < \""+title+"\"");

					break;

			

			default:

				table.addCriterium("description = \""+title+"\"");

				titleType = kIsType;

			}

		}



		if(isBackSearch())

		{

			switch(backType)

			{

				case kIsType:

					table.addCriterium("backordered>0");

					break;

				case kInType:


				table.addCriterium("backordered=0");
					break;

			

			default:

				table.addCriterium("backordered=0");
					

				backType = kIsType;

			}

			

		}



		if(isInactiveSearch())

		{

			

			table.addCriterium("is_active = 0");

		}else

		{

			table.addCriterium("is_active = 1");

		}

		if(isCaseQtySearch()){

			table.addCriterium("case_qty > 0");
		}


		

				

		if(isSupplierSearch())

		{

			switch(supplierType)

			{

				case kIsType:

					table.addCriterium("mfr_part_num = \""+supplier+"\"");

					break;

				case kInType:

					table.addCriterium("mfr_part_num like \"%"+supplier+"%\"");

					break;

				case kGreaterThanType:

					table.addCriterium("mfr_part_num > \""+supplier+"\"");

					break;

				case kLessThanType:

					table.addCriterium("mfr_part_num < \""+supplier+"\"");

					break;

			

			default:

				table.addCriterium("mfr_part_num = \""+supplier+"\"");

				supplierType = kIsType;

			}

		}

			

		table.setDescription("");

			//check for find criteria

		
		int gotono = ExtranetServlet.getIntegerParam(req,WebTable.kTableGotoPage,0);
		if(gotono!=0)
			table.setPageNum(gotono);
		else		
			table.setPageNum(ExtranetServlet.getIntegerParam(req,WebTable.kTableShowPage,1));

		table.setOrderCol(ExtranetServlet.getIntegerParam(req,WebTable.kTableSortColumn,-1));

		

		return table.getTable(req,resp);

		

	}









}
