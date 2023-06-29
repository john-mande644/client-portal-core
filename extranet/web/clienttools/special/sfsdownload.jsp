<%@ page import="com.owd.core.business.order.clients.SFSDownloadImporter"%>
<HTML>
Checking for new SFS orders...

<%

	SFSDownloadImporter importer2 =  new SFSDownloadImporter();
	importer2.checkForOrders("143");


%>
Done!
</HTML>