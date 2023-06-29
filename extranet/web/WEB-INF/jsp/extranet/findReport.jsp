<%           
	com.owd.web.servlet.ReportFinder finder = null;
 	try         
	{
  		finder = (com.owd.web.servlet.ReportFinder)request.getAttribute(com.owd.web.servlet.ReportsManager.kCurrentFinder);
  	}catch(ClassCastException ex)
  	{ 
  		finder = new com.owd.web.servlet.ReportFinder();
  	} 
                         if(finder == null)
                         		  finder = new com.owd.web.servlet.ReportFinder();
%>             

<%= finder.showResults(request,response) %>

   