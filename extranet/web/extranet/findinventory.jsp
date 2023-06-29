<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet"%>
<%@ page import="com.owd.extranet.servlet.InventoryFinder"%>
<%@ page import="com.owd.extranet.servlet.InventoryManager" %>
<%
      com.owd.extranet.servlet.InventoryFinder finder = (com.owd.extranet.servlet.InventoryFinder)request.getAttribute(com.owd.extranet.servlet.InventoryManager.kCurrentFinder);

%>              
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath %>?<%=InventoryManager.kParamInvMgrAction%>=<%=InventoryManager.kParamInvExploreAction%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kInvMgrName %>" />

    <fontx SIZE=-1>
Check the boxes for the fields you want to search on and enter the values to use. When you are done, click on the Find button below. The results displayed will be inventory items (SKUs) that match all the criteria you select. To show all SKUs, uncheck all the checkboxes and click the Find button.<P>
    
<INPUT TYPE=CHECKBOX NAME=<%= finder.kSKUSearchFlag %> VALUE=1 <%= (finder.isSKUSearch()?"CHECKED":"") %>>&nbsp;SKU&nbsp;<SELECT NAME=<%= InventoryFinder.kSKUSearchType %>>
<OPTION VALUE=1 <%= (finder.getSKUType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getSKUType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kSKUSearchValue %> VALUE="<%= (finder.isSKUSearch()?finder.getSKU():"") %>">
<P>  

<INPUT TYPE=CHECKBOX NAME=<%= finder.kTitleSearchFlag %> VALUE=1 <%= (finder.isTitleSearch()?"CHECKED":"") %>>&nbsp;Title (short description)&nbsp;<SELECT NAME=<%= finder.kTitleSearchType %>>
<OPTION VALUE=1 <%= (finder.getTitleType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getTitleType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kTitleSearchValue %> VALUE="<%= (finder.isTitleSearch()?finder.getTitle():"") %>">
<P>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kCountSearchFlag %> VALUE=1 <%= (finder.isCountSearch()?"CHECKED":"") %>>&nbsp;Count (# in stock)&nbsp;<SELECT NAME=<%= finder.kCountSearchType %>>
<OPTION VALUE=1 <%= (finder.getCountType()==1?"SELECTED":"") %>>=
<OPTION VALUE=3 <%= (finder.getCountType()==3?"SELECTED":"") %>>>
<OPTION VALUE=4 <%= (finder.getCountType()==4?"SELECTED":"") %>><
</SELECT>&nbsp;<INPUT NAME=<%= finder.kCountSearchValue %> VALUE="<%= (finder.isCountSearch()?finder.getCount():"") %>">
<P>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kSupplierSearchFlag %> VALUE=1 <%= (finder.isSupplierSearch()?"CHECKED":"") %>>&nbsp;Supplier Name&nbsp;<SELECT NAME=<%= finder.kSupplierSearchType %>>
<OPTION VALUE=1 <%= (finder.getSupplierType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getSupplierType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kSupplierSearchValue %> VALUE="<%= (finder.isSupplierSearch()?finder.getSupplier():"") %>"><P>

<INPUT TYPE=CHECKBOX NAME=<%= finder.kBackSearchFlag %> VALUE=1 <%= (finder.isBackSearch()?"CHECKED":"") %>>&nbsp;Backorder Status&nbsp;<SELECT NAME=<%= finder.kBackSearchType %>>
<OPTION VALUE=1 <%= (finder.getBackType()==1?"SELECTED":"") %>>Backordered
<OPTION VALUE=2 <%= (finder.getBackType()==2?"SELECTED":"") %>>Not Backordered
</SELECT><P>


<INPUT TYPE=CHECKBOX NAME=<%= finder.kInvIDSearchFlag %> VALUE=1 <%= (finder.isInvIDSearch()?"CHECKED":"") %>>&nbsp;Inventory&nbsp;ID&nbsp;<SELECT NAME=<%= finder.kInvIDSearchType %>>
<OPTION VALUE=1 <%= (finder.getInvIDType()==1?"SELECTED":"") %>>is
</SELECT>&nbsp;<INPUT NAME=<%= finder.kInvIDSearchValue %> VALUE="<%= (finder.isInvIDSearch()?finder.getInvID():"") %>"><P>


<INPUT TYPE=CHECKBOX NAME=<%= finder.kInactiveSearchFlag %> VALUE=1 <%= (finder.isInactiveSearch()?"CHECKED":"") %>>&nbsp;Search Inactive SKUs&nbsp;<P>

        <INPUT TYPE=CHECKBOX NAME=<%= finder.kCaseQtySearchFlag %> VALUE=1 <%= (finder.isCaseQtySearch()?"CHECKED":"") %>>&nbsp;Has Case Qty&nbsp;<P>

<INPUT TYPE=SUBMIT NAME=FIND VALUE=Find>
</FORM>
<%= finder.showResults(request,response) %>

   