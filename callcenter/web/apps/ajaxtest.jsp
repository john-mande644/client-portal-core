<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script type="text/javascript" src="<html:rewrite page="/js/Ajax.js"/>"></script>

<span id="name">${ajaxForm.name}</span>
<html:form action="ajax">
    <html:text property="name" onchange="retrieveURL('/callcenter/do/ajax?ask=COMMAND_NAME_1','ajaxForm');"/>
</html:form>