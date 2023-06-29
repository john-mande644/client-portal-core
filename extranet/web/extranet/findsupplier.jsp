 <%         
	com.owd.extranet.servlet.SupplierFinder finder = null;
 	try         
	{    
  	 	finder = (com.owd.extranet.servlet.SupplierFinder)request.getAttribute(com.owd.extranet.servlet.SupplierManager.kCurrentFinder);
  	}catch(ClassCastException ex)
  	{
  		finder = new com.owd.extranet.servlet.SupplierFinder();
  	}
                         if(finder == null)
                          		finder = new com.owd.extranet.servlet.SupplierFinder();
%>             

<%= finder.showResults(request,response) %>

    