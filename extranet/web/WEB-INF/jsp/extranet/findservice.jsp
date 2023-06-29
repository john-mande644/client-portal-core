<%             
	com.owd.web.servlet.ServiceFinder finder = null;
 	try          
	{
  		finder = (com.owd.web.servlet.ServiceFinder)request.getAttribute(com.owd.web.servlet.ServiceManager.kCurrentFinder);
  	}catch(ClassCastException ex)
  	{ 
  		finder = new com.owd.web.servlet.ServiceFinder();
  	} 
                         if(finder == null)
                         	  finder = new com.owd.web.servlet.ServiceFinder();
%>            
<FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>?<%=com.owd.web.servlet.ServiceManager.kParamSvcMgrAction%>=<%=com.owd.web.servlet.ServiceManager.kParamSvcExploreAction%>">
<fontx SIZE=-1>
<HR><P>
   
<INPUT TYPE=CHECKBOX NAME=<%= finder.kNameSearchFlag %> VALUE=1 <%= (finder.isNameSearch()?"CHECKED":"") %>>&nbsp;Service Name&nbsp;<SELECT NAME=<%= finder.kNameSearchType %>>
<OPTION VALUE=1 <%= (finder.getNameType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getNameType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kNameSearchValue %> VALUE="<%= (finder.isNameSearch()?finder.getName():"") %>">
<P>  

<INPUT TYPE=SUBMIT NAME="<%= finder.kSubmitType %>" VALUE="Find">&nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME="<%= finder.kSubmitType %>" VALUE="Show All">
</FORM>
<%= finder.showResults(request,response) %>

   