 <%         
	com.owd.web.servlet.SupplierFinder finder = null;
 	try         
	{    
  	 	finder = (com.owd.web.servlet.SupplierFinder)request.getAttribute(com.owd.web.servlet.SupplierManager.kCurrentFinder);
  	}catch(ClassCastException ex)
  	{
  		finder = new com.owd.web.servlet.SupplierFinder();
  	}
                         if(finder == null)
                          		finder = new com.owd.web.servlet.SupplierFinder();
%>             

<%= finder.showResults(request,response) %>

    