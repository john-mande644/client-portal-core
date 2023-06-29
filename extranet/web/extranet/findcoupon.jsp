<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%
    com.owd.extranet.servlet.CouponFinder finder = null;
     try
    {
          finder = (com.owd.extranet.servlet.CouponFinder)request.getAttribute(com.owd.extranet.servlet.CouponManager.kCurrentFinder);
      }catch(ClassCastException ex)
      {
          finder = new com.owd.extranet.servlet.CouponFinder();
      }
                         if(finder == null)
                                   finder = new com.owd.extranet.servlet.CouponFinder();
%>             
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath %>?<%=com.owd.extranet.servlet.CouponManager.kParamCouponMgrAction%>=<%=com.owd.extranet.servlet.CouponManager.kParamCouponExploreAction%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kCouponsMgrName %>" />

    <fontx SIZE=-1>
<HR><P> 
   
<INPUT TYPE=CHECKBOX NAME=<%= finder.kNameSearchFlag %> VALUE=1 <%= (finder.isNameSearch()?"CHECKED":"") %>>&nbsp;Promotion Name&nbsp;<SELECT NAME=<%= finder.kNameSearchType %>>
<OPTION VALUE=1 <%= (finder.getNameType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getNameType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kNameSearchValue %> VALUE="<%= (finder.isNameSearch()?finder.getName():"") %>">
<P>  

<INPUT TYPE=CHECKBOX NAME=<%= finder.kIssuedSearchFlag %> VALUE=1 <%= (finder.isIssuedSearch()?"CHECKED":"") %>>&nbsp;Issued To&nbsp;<SELECT NAME=<%= finder.kIssuedSearchType %>>
<OPTION VALUE=1 <%= (finder.getIssuedType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getIssuedType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kIssuedSearchValue %> VALUE="<%= (finder.isIssuedSearch()?finder.getIssued():"") %>">
<P>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kCodeSearchFlag %> VALUE=1 <%= (finder.isCodeSearch()?"CHECKED":"") %>>&nbsp;Coupon Code&nbsp;<SELECT NAME=<%= finder.kCodeSearchType %>>
<OPTION VALUE=1 <%= (finder.getCodeType()==1?"SELECTED":"") %>>is
<OPTION VALUE=3 <%= (finder.getCodeType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kCodeSearchValue %> VALUE="<%= (finder.isCodeSearch()?finder.getCode():"") %>">
<P>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kSKUSearchFlag %> VALUE=1 <%= (finder.isSKUSearch()?"CHECKED":"") %>>&nbsp;SKU Value&nbsp;<SELECT NAME=<%= finder.kSKUSearchType %>>
<OPTION VALUE=1 <%= (finder.getSKUType()==1?"SELECTED":"") %>>is
<OPTION VALUE=3 <%= (finder.getSKUType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kSKUSearchValue %> VALUE="<%= (finder.isSKUSearch()?finder.getSKU():"") %>">
<P>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kValidSearchFlag %> VALUE=1 <%= (finder.isValidSearch()?"CHECKED":"") %>>&nbsp;Current Status&nbsp;<SELECT NAME=<%= finder.kValidSearchType %>>
<OPTION VALUE=1 <%= (finder.getValidType()==1?"SELECTED":"") %>>Active
<OPTION VALUE=2 <%= (finder.getValidType()==2?"SELECTED":"") %>>Expired or Used
</SELECT><P>
<INPUT TYPE=SUBMIT NAME="<%= finder.kSubmitType %>" VALUE="Find">&nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME="<%= finder.kSubmitType %>" VALUE="Show All">
</FORM>
<%= finder.showResults(request,response) %>

   