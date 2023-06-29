<%@ page import="com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
    <title>XLoadTree Demo (WebFX)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="xtree.js"></script>
    <script type="text/javascript" src="xmlextras.js"></script>
    <script type="text/javascript" src="xloadtree.js"></script>
    <link type="text/css" rel="stylesheet" href="xtree.css"/>

    <style type="text/css">

        body {
            background: white;
            color: black;
        }

    </style>
</head>

<body>
<p><button onclick="clienttree.reload()">Reload Item</button></p>
<script type="text/javascript">

    /// XP Look
    webFXTreeConfig.rootIcon = "images/xp/folder.png";
    webFXTreeConfig.openRootIcon = "images/xp/openfolder.png";
    webFXTreeConfig.folderIcon = "images/xp/folder.png";
    webFXTreeConfig.openFolderIcon = "images/xp/openfolder.png";
    webFXTreeConfig.fileIcon = "images/xp/file.png";
    webFXTreeConfig.lMinusIcon = "images/xp/Lminus.png";
    webFXTreeConfig.lPlusIcon = "images/xp/Lplus.png";
    webFXTreeConfig.tMinusIcon = "images/xp/Tminus.png";
    webFXTreeConfig.tPlusIcon = "images/xp/Tplus.png";
    webFXTreeConfig.iIcon = "images/xp/I.png";
    webFXTreeConfig.lIcon = "images/xp/L.png";
    webFXTreeConfig.tIcon = "images/xp/T.png";

    <%
try
{
     int rootID = 0;

    %> var tree = new WebFXTree("Unshipped Orders");tree.setBehavior("explorer");<%
        %> tree.add(clienttree = new WebFXLoadTreeItem("By Client", "loadclients.jsp"));<%
        %> tree.add(new WebFXLoadTreeItem("By Ship Method", "loadmethods.jsp"));<%
        %> tree.add(new WebFXLoadTreeItem("By Ship Deadline", "loaddeadlines.jsp", ""));<%


    }   catch(Exception ex)
    {
              ex.printStackTrace();
    }   finally
    {
       HibernateSession.closeSession();
    }


%>

    document.write(tree);

</script>

</body>
</html>
