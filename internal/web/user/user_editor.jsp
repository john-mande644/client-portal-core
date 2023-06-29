<%@ page import="java.util.Iterator,
                 com.owd.core.business.user.UserManager,
                 com.owd.web.internal.user.UserHome,
                 com.owd.hibernate.generated.OwdUser,
                 java.util.Set,
                 java.util.TreeSet,
                 java.util.List,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 com.owd.hibernate.generated.OwdGroup" %>
<%

    try {
        OwdUser user = (OwdUser) request.getSession(true).getAttribute("user.currentuser");

%>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kUserAreaName, (user.getId() == null ? " Create New User" : "Editing User \"" + user.getName() + "\" (" + user.getLogin() + ")"), request);

%>
<script><!--
     function validateForm() {

     if (!(validateField("user[name]","Please enter a name for this user!")))
     {
        return false;
     }else if (!(validateField("user[email]","Please enter an email address for this user!")))
     {

        return false;
     }else if (!(validateField("user[login]","Please enter an email address for this user!")))
     {

        return false;
     }else if (!(validateField("user[password]","Please enter an email address for this user!")))
     {

        return false;
     }else
     {
        return true;
     }

     }
     //-->
</script>
<jsp:include page="/includes/owdtop.jsp"/>
<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="admin" href="/internal/user/edit">Cancel and go back</a>

        <% if (user.getId() != null) {
        %>

        | <a class="admin"
             href="http://stewart.owd.com:8080/internal/user/edit?act=user-delete&user_id=<%= user.getId()%>"
             onclick="return confirm('Are you sure you want to delete <%= user.getName()+" ("+user.getLogin()+")"%>?\nNote: There is no Undo.');">Delete
        this user</a>
        <% } %>
    </h3>
</div>

<form method="GET" action="/internal/user/edit">
    <input type="HIDDEN" name="<%= UserHome.kParamAdminAction %>" value="create-save">

    <div class="latemilestones" style="display: <%= request.getAttribute("errormessage")==null?"none":"block"%>;">
        <h1>Changes Not Saved!</h1>
        <ul>
            <li>
                <%= request.getAttribute("errormessage")%>
            </li>
        </ul>
    </div>


    <div id="basic_form">
        <p class="blockintro">Essential user information - all fields are required</p>

        <div class="block">
            <dl>
                <dt class="required"><label for="user[name]">User Name:</label></dt>
                <dd>

                    <input type="text" size="25" maxlength="30" id="user[name]" name="user[name]"
                           value="<%= user.getName() %>"/>

                </dd>
                <dt class="required"><label for="user[login]">Login:</label></dt>
                <dd>

                    <input type="text" size="25" maxlength="50" id="user[login]" name="user[login]"
                           value="<%= user.getLogin() %>"/>

                </dd>
                <dt class="required"><label for="user[password]">Password:</label></dt>
                <dd>

                    <input type="text" size="25" maxlength="50" id="user[password]" name="user[password]"
                           value="<%= user.getPassword() %>"/>

                </dd>
                <dt class="required"><label for="user[email]">Email:</label></dt>
                <dd>

                    <input type="text" size="25" maxlength="100" id="user[email]" name="user[email]"
                           value="<%= user.getEmail() %>"/>

                </dd>

            </dl>
        </div>
    </div>


    <div class="FormSubBlock">
        <h2>Data Access</h2>

        <p style="margin: 0 50% 5px 3px; font-size: 10px; color: #666;">
            Choose <B>All Clients</B> for internal employee access; otherwise, choose the appropriate client. Checking
            <B>Administrator</B> will allow this user to create and edit other user logins for the specific client (or
            "All Clients") indicated in the popup menu.

        </p>

        <p style="margin: 15px 0px 0px 15px; font-weight: bold; font-size: 11px;">
            Allow access to: <SELECT
                NAME=clientFkey><%= UserHome.getClientSelector("" + user.getClientFkey(), "INTERNAL USER (Access All Clients)", "0") %></SELECT>
            &nbsp;&nbsp;<input type="CHECKBOX" name="isClientAdmin" value="1" <%=user.isIsClientAdmin()?"CHECKED":""%>>
            <B>&nbsp;Administrator</B>
        </p>

    </div>


    <div class="FormSubBlock">
        <table cellspacing="0">
            <tr>

                <th colspan="2">Group Membership</th>
            </tr>
            <tr>
                <td colspan="2">
                    <p style="margin: 0px; font-size: 10px; color: #666;">This user will have access to the areas
                        checked off below:</p>
                </td>
            </tr>
            <tr>

                <td>

                    <p class="Checkbox">
                        <input class="firm" type="checkbox"
                               id="grp_extranet" name="grp_extranet"
                               value="1" <%= user.isIsActive()?"CHECKED":"" %>>
                        <label for="grp_extranet">General Extranet</label>
                    </p>
                </td>

            </tr>
        </table>
    </div>

    <div style="background: #ffffff; border-top: 1px solid #ccc; padding: 10px 15px;
                     margin-top: 25px;">
        <input type="image" src="/internal/images/b-save_changes.gif" alt="Save changes"
               onClick="return validateForm();"/>
    </div>


</form>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}     %>

<jsp:include page="/includes/owdbottom.jsp"/>
</body>
</html>