<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<title>OWD Problem Form</title>
<link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">
<body>
<div id=header>
    <html:link action="problemForm">Return</html:link>
    <br>
    SUBMITTED!!!!!!!!!!!!!!!!!!!!!!!!!
</div>
<div id=problemForm>

<pre>
From: ${problemFormForm.dfrom}

To: ${problemFormForm.dto}

${problemFormForm.dbody}
</pre>
   </div>

</body>
</html>